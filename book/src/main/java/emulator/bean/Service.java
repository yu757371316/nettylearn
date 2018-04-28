package emulator.bean;

import java.io.Serializable;

public class Service implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private Header header;
	private Body body;
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "Service [header=" + header + ", body=" + body + "]";
	}


}
