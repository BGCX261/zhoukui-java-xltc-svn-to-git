package org.sunney.util.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebSeriviceUtil {

	/**
	 * 获取远程WebService应用的数据列表
	 * 
	 * @param SERVICE_NS
	 *            命名空间如：http://WebXml.com.cn/
	 * @param SERVICE_URL
	 *            WebService地址如：http://webservice.webxml.com.cn/WebServices/
	 *            WeatherWS.asmx
	 * @param method
	 *            WebService方法如：getRegionProvince
	 * @return 数据列表
	 */
	public static List<String> getServiceDataList(String SERVICE_NS, String SERVICE_URL, String method) {
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject soapObject = new SoapObject(SERVICE_NS, method);
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		try {
			// 调用Web Service
			ht.call(SERVICE_NS + method, envelope);
			if (envelope.getResponse() != null) {
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(method + "Result");
				// 解析服务器响应的SOAP消息。
				return parseDataList(detail);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<String> parseDataList(SoapObject detail) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < detail.getPropertyCount(); i++) {
			// 解析出每个省份
			result.add(detail.getProperty(i).toString().split(",")[0]);
		}
		return result;
	}
}
