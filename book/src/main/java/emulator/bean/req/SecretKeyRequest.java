package emulator.bean.req;

import emulator.bean.Request;

/**
 * 
 * @author lh
 *
 */
public class SecretKeyRequest extends Request {
	
	private String operType;
	private String reserve1;
	private String reserve2;
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	@Override
	public String toString() {
		return "SecretKeyRequest [operType=" + operType + ", reserve1="
				+ reserve1 + ", reserve2=" + reserve2 + "]";
	}
	
	
	
	

}
