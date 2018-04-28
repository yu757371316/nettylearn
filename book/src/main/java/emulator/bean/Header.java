package emulator.bean;

import java.io.Serializable;

public class Header implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * 版本号, 默认值1.0
	 */
	private String version =  "1.0";
	
	/**
	 * 发起方机构编号，需申请
	 */
	private String exBranchNo;
	
	/**
	 * 发起方流水号
	 */
	private String exSerial;
	
	/**
	 * 接收方机构编号，默认080001 恒丰
	 */
	private String branchno = "080001";
	
	/**
	 *  接收方流水号，应答报文填充
	 */
	private String serialNo;
	
	/**
	 * 系统日期，发起方交易日期
	 */
	private String transDate;
	
	/**
	 * 系统时间，发起方交易时间
	 */
	private String transTime;
	
	/**
	 * 渠道编号，默认998
	 */
	private String channel = "998";
	
	/**
	 * 交易代码
	 */
	private String transCode;
	
	/**
	 * 文件标志，0:无文件;1:有文件
	 */
	private String fileFlag;
	
	/**
	 * 文件ID，文件标志位为1是必送
	 */
	private String fileId;
	
	/**
	 * 文件名称，文件标志位为1是必送
	 */
	private String fileName;
	
	/**
	 * 报文性质，0：请求数据;1：回执数据
	 */
	private String direction;
	
	/**
	 * 响应代码,应答报文填充
	 */
	private String respCode;
	
	/**
	 * 响应信息,应答报文填充
	 */
	private String respMesg;
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getExBranchNo() {
		return exBranchNo;
	}

	public void setExBranchNo(String exBranchNo) {
		this.exBranchNo = exBranchNo;
	}

	public String getExSerial() {
		return exSerial;
	}

	public void setExSerial(String exSerial) {
		this.exSerial = exSerial;
	}

	public String getBranchno() {
		return branchno;
	}

	public void setBranchno(String branchno) {
		this.branchno = branchno;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getFileFlag() {
		return fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMesg() {
		return respMesg;
	}

	public void setRespMesg(String respMesg) {
		this.respMesg = respMesg;
	}

	@Override
	public String toString() {
		return "Header [version=" + version + ", exBranchNo=" + exBranchNo
				+ ", exSerial=" + exSerial + ", branchno=" + branchno
				+ ", serialNo=" + serialNo + ", transDate=" + transDate
				+ ", transTime=" + transTime + ", channel=" + channel
				+ ", transCode=" + transCode + ", fileFlag=" + fileFlag
				+ ", fileId=" + fileId + ", fileName=" + fileName
				+ ", direction=" + direction + ", respCode=" + respCode
				+ ", respMesg=" + respMesg + "]";
	}
	
	
	
	
}
