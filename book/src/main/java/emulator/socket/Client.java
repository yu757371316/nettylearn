package emulator.socket;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {

	private static final String CHAR_SET = "UTF-8";

	public static void main(String args[]) throws Exception {
		// 为了简单起见，所有的异常都直接往外抛
		String host = "127.0.0.1"; // 要连接的服务端IP地址
		int port = 8899; // 要连接的服务端对应的监听端口
		// 与服务端建立连接
		Socket client = new Socket(host, port);
		// 建立连接后就可以往服务端写数据了
		Writer writer = new OutputStreamWriter(client.getOutputStream()
		 , CHAR_SET
		);
		String xml = FileUtils.readFileToString(new File("D:\\开户请求报文.xml"));
		writer.write(xml);
		writer.write("eof\n");
		writer.flush();
		// 写完以后进行读操作
		BufferedReader br = new BufferedReader(new InputStreamReader(
				client.getInputStream()
				, CHAR_SET
				));
		// 设置超时间为一分钟
		client.setSoTimeout(1 * 1000);
		StringBuffer sb = new StringBuffer();
		String temp;
		int index;
		try {
			while ((temp = br.readLine()) != null) {
				if ((index = temp.indexOf("eof")) != -1) {
					sb.append(temp.substring(0, index));
					break;
				}
				sb.append(temp);
			}
		} catch (SocketTimeoutException e) {
			System.out.println("数据读取超时。");
		}
		System.out.println("server callback: \n" + sb);
		writer.close();
		br.close();
		client.close();
	}
}
