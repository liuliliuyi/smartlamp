package com.parking.smarthome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.parking.model.NBData;
import com.parking.smarthome.App.CtrBroadcastData;

/**
 * Hello world!
 *
 */
public class App1 




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
    	
         ArrayList<NBData> datas = new ArrayList<NBData>();
    	ArrayList<String> qq1 = new ArrayList<String>();
    	
    	qq1.add("A9  01  02  01  F7  03  14  EE  02  53  0A  64  41  03  4C  27  40  97  53  00");
        qq1.add("A9  01  02  02  71  17  26  91  0D  11  07  81  19  06  4C  2A  C3  05  07  62");
        qq1.add("AA  01  02  01  F7  03  14  EE  02  57  0A  64  41  03  4C  16  07  04  49  00");
        qq1.add("AA  01  02  02  89  17  26  DA  0D  11  07  CD  19  06  4C  2A  43  05  07  62");
        qq1.add("AB  01  02  01  F7  03  14  EE  02  53  0A  64  41  03  4C  27  40  97  53  00");
        qq1.add("AB  01  02  02  7E  17  26  89  0D  11  07  81  19  06  4C  2A  C3  05  07  62");
       
        if(qq1.size()>1){
		 // 排序(冒泡算法) 将list进行升序排列
		 for(int i = 0; i < qq1.size(); i++){
	            // 内层循环控制每轮比较次数
	            for(int j = 0; j < qq1.size()- i - 1; j++){
               if (qq1.get(i).toString()
						.replaceAll(" ", "").substring(0,2).equals(qq1.get(j).toString()
								.replaceAll(" ", "").substring(0,2))&&"01".equals(qq1.get(i).toString()
										.replaceAll(" ", "").substring(6,8))&&"02".equals(qq1.get(j).toString()
												.replaceAll(" ", "").substring(6,8))) {
               	
   				 String getsntext = qq1.get(i).toString()
					.replaceAll(" ", "").substring(8)
					+ qq1.get(j).toString().replaceAll(" ", "")
							.substring(8);
               	CtrBroadcastData ctrData =getsn(getsntext);
   				NBData bean = new NBData();
   				bean.setHeader(ctrData.header);
   				bean.setSn(ctrData.sn);
   				bean.setImei(ctrData.imei);
   				bean.setCsq(ctrData.csq);
   				bean.setPvVoltage(ctrData.pvVoltage);
   				bean.setBatVoltage(ctrData.batVoltage);
   				bean.setNbStage(ctrData.nbStage);
   				bean.setUploadInterval(ctrData.uploadInterval);
   				bean.setGroupNum(ctrData.groupNum);
   				bean.setCheckSum(ctrData.checkSum);
   				bean.setVersion(ctrData.version);
   				bean.setBatType(ctrData.batType);
   				bean.setBatNum(ctrData.batNum);
   				bean.setSnStaus("0");
   				datas.add(bean);
               
               
               
               }
           }
       }
        }
        int a=datas.size();
       System.out.print("datas的大小为："+String.valueOf(a));
    }
    
    
	public static CtrBroadcastData getsn(String test) {
		
		byte[] key = { 6, 1, 5, 78, 2, 9, 2, 5, 0, 3, 8, 1, 6, 3, 2, 7, 2,
				2, 11, 2, 6, 23, 7, 9, 1, 5, 76, 9, 3, 5, 7, 98 };
		String tempStr = test.replaceAll(" ", "");
		// 转换成byte数组
		byte[] rawData = App.GetByteArray(tempStr);
		// 解密
		byte[] plainData = App.DecryptOrEncrypt(rawData, key, 0);
		// 解码
		CtrBroadcastData ctrData = App1.DecodeBroadcastData(plainData);
		// return SN = ctrData.sn;
		return ctrData;

	}
}
