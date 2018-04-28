package emulator.bean;

import java.io.Serializable;

public class Body implements Serializable{

	private static final long serialVersionUID = 1L;
	private Request request;
	private Response response;
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "Body [request=" + request + ", response=" + response + "]";
	}
	
	
	
}
