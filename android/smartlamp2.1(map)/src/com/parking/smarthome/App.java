package com.parking.smarthome;

import java.util.Arrays;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
	/*
	 * 将Hex字符串转换为byte数组
	 */
	public static byte[] GetByteArray(String s) {
		int len=s.length()/2;
		byte[] res=new byte[len];
		
		for(int i=0;i<len;i++) {
			res[i]=(byte) ((Character.digit(s.charAt(2*i), 16) << 4)
                    + Character.digit(s.charAt(2*i+1), 16));
		}
		return res;
	}
	/*
	 * 将字节数组转换成Hex字符串
	 */
	public static String GetHexString(byte[] data) {
		byte[] temp=new byte[data.length*2];
		for(int i=0;i<data.length;i++) {
			temp[2*i+0]=(byte)((data[i]>>4)&0x0F);
			temp[2*i+1]=(byte)((data[i])&0x0F);
		}
		for(int i=0;i<temp.length;i++) {
			if(temp[i]>=10)temp[i]=(byte)((byte)'A'+temp[i]-10);
			else temp[i]=(byte)((byte)'0'+temp[i]);
		}
		return new String(temp);
	}
	
	/*
	 * 如果data长度大于key将循环使用key，加解密都用该函数,加解密从data的offset位置开始，
	 * 前面数据保持不变
	 */
	public static byte[] DecryptOrEncrypt(byte[] data,byte[] key,int offset) {
		byte[] res=new byte[data.length];
		for(int i=0;i<offset;i++) {
			res[i]=data[i];
		}
		int keyLen=key.length;
		for(int i=offset;i<data.length;i++){
			res[i]=(byte)(data[i]^key[(i-offset)%keyLen]);
		}
		return res;
	}
	public static class CtrBroadcastData{
		public String header;
		public String sn;
		public String imei;
		public int csq;
		public float pvVoltage;			//光板电压，单位V
		public float batVoltage;		//电池电压，单位V
		public int nbStage;				//NB初始阶段
		public int uploadInterval;		//上传间隔，单位s
		public int groupNum;			//组号
		public String checkSum;		//模式校验和
		public String version;				//版本号，只包含数组部分（如1.2.34），前面字符自行添加
		public int batType;				//电池类型，数字，根据需要自行对应
		public int batNum;				//电池串数
	}
	/*
	 * 解码广播数据
	 */
	public static CtrBroadcastData DecodeBroadcastData(byte[] data) {
		CtrBroadcastData res=new CtrBroadcastData();
		res.header=GetHexString(Arrays.copyOfRange(data, 0, 0+1));
		res.sn=GetHexString(Arrays.copyOfRange(data, 1, 1+5));
		res.imei=GetHexString(Arrays.copyOfRange(data, 6, 6+8));
		res.csq=data[14]&0xFF;
		res.pvVoltage=(((data[15]&0xFF)<<8)|(data[16]&0xFF))*0.001f;
		res.batVoltage=(((data[18]&0xFF)<<8)|(data[19]&0xFF))*0.001f;
		res.nbStage=data[20]&0xFF;
		res.uploadInterval=(data[21]&0xFF)*100;
		res.groupNum=data[22]&0xFF;
		res.checkSum=GetHexString(Arrays.copyOfRange(data, 23, 23+1));
		res.version=String.format("%d.%d.%d", ((data[24]>>4)&0x0F),((data[24])&0x0F),((data[25])&0xFF)) ;
		res.batType=(data[27]>>4)&0x0F;
		res.batNum=data[27]&0x0F;
		return res;
	}
	/*
	 * 获取用于闪灯的数据包，sn:闪灯地址，ch：通道
	 */
	public static byte[] GetBlinkDataPack(String sn,int ch) {
		byte[] res=new byte[20];
		Random random=new Random();
		res[0]=(byte)(random.nextInt()&0xFF);
		res[1]=0x05;
		res[2]=0x01;
		res[3]=0x01;
		byte[] snByte=GetByteArray(sn);
		for(int i=0;i<5;i++)res[4+i]=snByte[i];
		res[9]=(byte)(ch&0xFF);
		for(int i=0;i<10;i++)res[10+i]=0;
		return res;
		
	}
    public static void main( String[] args )
    {
    	byte[] key= {6, 1, 5,78, 2, 9, 2, 5, 0, 3, 8, 1, 6, 3, 2, 7, 2, 2,11, 2, 6,23, 7, 9,1, 5,76, 9, 3, 5, 7,98};
       String test="F7  03  14  EE  02  57  0A  64  41  03  4C  16  07  04  49  00  8F  17  26  E0  0D  11  07  CD  19  06  4C  2A  43  05  07  62";
       //去掉测试数据里面空格，如果没有可以不用处理
       String tempStr=test.replaceAll(" ", "");
       //转换成byte数组
       byte[] rawData=GetByteArray(tempStr);
       //解密
       byte[] plainData=DecryptOrEncrypt(rawData, key,0);
       //解码
       CtrBroadcastData ctrData=DecodeBroadcastData(plainData);
       //显示
       System.out.println(String.format("帧头:%s\r\nSN:%s\r\nIMEI:%s\r\nCSQ:%d\r\n光板电压:%f\r\n电池电压:%f\r\nNB初始阶段:%d\r\n上传间隔:%d秒\r\n组号:%d\r\n模式校验和:%s\r\n版本号:%s\r\n电池类型:%d\r\n电池串数:%d",
    		   ctrData.header, ctrData.sn,ctrData.imei,ctrData.csq,ctrData.pvVoltage,ctrData.batVoltage,ctrData.nbStage,ctrData.uploadInterval,ctrData.groupNum,
    		   ctrData.checkSum,ctrData.version,ctrData.batType,ctrData.batNum));
       
       //获取闪灯的数据包，长度20字节
       byte[] blkPack=GetBlinkDataPack(ctrData.sn, ctrData.groupNum);
       //用Hex字符串显示出来，仅仅为了显示
       System.out.println("加密前数据:"+GetHexString(blkPack));
       //对数据包进行加密
       byte[] packForSend=DecryptOrEncrypt(blkPack, key,4);
     //用Hex字符串显示出来，仅仅为了显示
       System.out.println("加密后数据:"+GetHexString(packForSend));
    }
}
