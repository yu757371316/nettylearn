package emulator.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Dom4JUtil {

	/**
	 * 取得报文头元素内容
	 * @param xml
	 * @param key
	 * @return
	 */
	public static String header(String xml, String key){
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element e = document.getRootElement().element("Header").element(key);
			if(e!=null){
				return e.getTextTrim();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";
	}



	/**
	 * 取得报文头部分内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> header(String xml) {
		Map<String, String> headMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历header部分
			for (Iterator hd = rootElement.element("Header").elementIterator(); hd
					.hasNext();) {
				Element e = (Element) hd.next();
				headMap.put(e.getName(), e.getTextTrim());
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return headMap;
	}


	/**
	 * 更新报文头部分内容
	 * @param xml
	 * @return
	 */
	public static String modifyHeader(String xml, Map<String, String> headNodes) {

		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			Element headElement = rootElement.element("Header");
			//遍历子节点
			if(headElement!=null ){
				if(headElement!=null && !headElement.elements().isEmpty()){
					for (Iterator it = headElement.elementIterator(); it.hasNext();) {
						Element e = (Element) it.next();
						Iterator<String> nit = headNodes.keySet().iterator();
						while (nit.hasNext()) {
							String name = nit.next();
							if(e.getName().equalsIgnoreCase(name)){
								e.setText(headNodes.get(name));
							}
						}

					}
				}
				return document.asXML();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return xml;
	}

	/**
	 * 删除节点属性
	 * @param xml
	 * @param nodeName
	 * @param attrKey
	 * @return
	 */
	public static String removeAttribute(String xml, String nodeName, String attrKey){
		String res = xml;
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			Element e = null;
			if("Header".equals(nodeName)){
				e = rootElement.element("Header");
			}else if("request".equals(nodeName) || "response".equals(nodeName)){
				e = rootElement.element("Body").element(nodeName);
			}
			if(e!=null)	e.remove(e.attribute(attrKey));

			res = document.asXML();//document.getRootElement().asXML();

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}

		return res;
	}


	/**
	 * 添加节点属性
	 * @param xml
	 * @param nodeName
	 * @param attribute
	 * @return
	 */
	public static String addAttribute(String xml, String nodeName, String attrKey,String attrVal){
		String res = xml;
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			Element e = null;
			if("Header".equals(nodeName)){
				e = rootElement.element("Header");
			}else if("request".equals(nodeName) || "response".equals(nodeName)){
				e = rootElement.element("Body").element(nodeName);
			}
			if(e!=null)	e.addAttribute(attrKey, attrVal);
			res = document.asXML();

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}

		return res;
	}


	/**
	 * 取得报文请求内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> request(String xml) {
		Map<String, String> reqMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历request部分
			Element reqElement = rootElement.element("Body").element("request");

			if(reqElement!=null && !reqElement.elements().isEmpty()){
				for (Iterator it = reqElement.elementIterator(); it.hasNext();) {
					Element e = (Element) it.next();
					reqMap.put(e.getName(), e.getTextTrim());
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return reqMap;
	}

	/**
	 * 取得报文请求内容
	 * @param xml
	 * @param key
	 * @return
	 */
	public static String request(String xml, String key) {
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			Element e = rootElement.element("Body").element("request").element(key);
			if(e!=null){
				return e.getTextTrim();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 取得报文响应内容
	 * @param xml
	 * @return
	 */
	public static Map<String, String> response(String xml) {
		Map<String, String> respMap = new HashMap<String, String>();
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历response部分
			Element respElement = rootElement.element("Body").element("response");
			if(respElement!=null && !respElement.elements().isEmpty()){
				for (Iterator it = respElement.elementIterator(); it
						.hasNext();) {
					Element e = (Element) it.next();
					respMap.put(e.getName(), e.getTextTrim());
				}
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return respMap;
	}

	/**
	 * 取得报文响应内容
	 * @param xml
	 * @return
	 */
	public static String response(String xml, String key) {
		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历response部分
			Element e = rootElement.element("Body").element("response").element(key);
			if(e!=null ){
				return e.getTextTrim();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 更新报文响应内容
	 * @param xml
	 * @return
	 */
	public static String modifyResponse(String xml, Map<String, String> cnodes) {

		// 1、将传入的字符串流化
		InputStream in = null;
		try {
			in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			SAXReader reader = new SAXReader();
			// 2、将流转成document对象
			Document document = reader.read(in);
			Element rootElement = document.getRootElement(); // 拿到根节点
			//遍历response部分
			Element respElement = rootElement.element("Body").element("response");
			if(respElement!=null ){
				//return respEle.getTextTrim();
				if(respElement!=null && !respElement.elements().isEmpty()){
					for (Iterator it = respElement.elementIterator(); it.hasNext();) {
						Element e = (Element) it.next();

						Iterator<String> nit = cnodes.keySet().iterator();
						while (nit.hasNext()) {
							String name = nit.next();
							if(e.getName().equalsIgnoreCase(name)){
								e.setText(cnodes.get(name));
							}
						}

					}
				}
				return document.asXML();
			}

		} catch (UnsupportedEncodingException e) {
			System.out.println("流操作异常");
			e.printStackTrace();
		} catch (DocumentException e) {
			System.out.println("Document对象操作异常");
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("流关闭异常");
				e.printStackTrace();
			}
		}
		return xml;
	}


	public static void main(String[] args) throws Exception {}
}
