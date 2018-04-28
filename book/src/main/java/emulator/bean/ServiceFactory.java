package emulator.bean;

import emulator.bean.req.SecretKeyRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceFactory {

	public static Service req(String transCode) {

		Service service = new Service();

		Header header = new Header();
		header.setExBranchNo("360001");
		header.setExSerial(System.currentTimeMillis() + "");
		header.setBranchno("080001");
		header.setTransDate(curDate());
		header.setTransTime(curTime());
		header.setTransCode(transCode);
		header.setFileFlag("0");
		header.setDirection("0");
		service.setHeader(header);

		Body body = new Body();
		SecretKeyRequest request = new SecretKeyRequest();
		request.setOperType("xxx");
		body.setRequest(request);
		service.setBody(body);
		return service;
	}

	public static Service resp(String transCode) {
		Service service = new Service();

		Header header = new Header();
		header.setExBranchNo("360001");
		header.setExSerial(System.currentTimeMillis() + "");
		header.setBranchno("080001");
		header.setTransDate(curDate());
		header.setTransTime(curTime());
		header.setTransCode(transCode);
		header.setFileFlag("0");
		header.setDirection("0");
		service.setHeader(header);

		Body body = new Body();
		SecretKeyRequest request = new SecretKeyRequest();
		request.setOperType("xxx");
		body.setRequest(request);
		service.setBody(body);
		return service;
	}

	private static String curDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}

	private static String curTime() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(new Date());
	}

}
