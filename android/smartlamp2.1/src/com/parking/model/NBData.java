package com.parking.model;

public class NBData {
    public String SnStaus;
	public String getSnStaus() {
		return SnStaus;
	}

	public void setSnStaus(String snStaus) {
		SnStaus = snStaus;
	}

	public String header;
	public String sn;
	public String imei;
	public int csq;
	public float pvVoltage; // 光板电压，单位V
	public float batVoltage; // 电池电压，单位V
	public int nbStage; // NB初始阶段
	public int uploadInterval; // 上传间隔，单位s
	public int groupNum; // 组号
	public String checkSum; // 模式校验和
	public String version; // 版本号，只包含数组部分（如1.2.34），前面字符自行添加
	public int batType; // 电池类型，数字，根据需要自行对应
	public int batNum; // 电池串数

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getCsq() {
		return csq;
	}

	public void setCsq(int csq) {
		this.csq = csq;
	}

	public float getPvVoltage() {
		return pvVoltage;
	}

	public void setPvVoltage(float pvVoltage) {
		this.pvVoltage = pvVoltage;
	}

	public float getBatVoltage() {
		return batVoltage;
	}

	public void setBatVoltage(float batVoltage) {
		this.batVoltage = batVoltage;
	}

	public int getNbStage() {
		return nbStage;
	}

	public void setNbStage(int nbStage) {
		this.nbStage = nbStage;
	}

	public int getUploadInterval() {
		return uploadInterval;
	}

	public void setUploadInterval(int uploadInterval) {
		this.uploadInterval = uploadInterval;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getBatType() {
		return batType;
	}

	public void setBatType(int batType) {
		this.batType = batType;
	}

	public int getBatNum() {
		return batNum;
	}

	public void setBatNum(int batNum) {
		this.batNum = batNum;
	}

	// 重写equals方法，用于判断连个Person对象是否相同
	@Override
	public boolean equals(Object obj) {
		NBData p = (NBData) obj;
		System.out.println("equals 方法被调用了,证明contains方法底层调用的是equals");
		return this.sn.equals(p.sn);
	}
}
