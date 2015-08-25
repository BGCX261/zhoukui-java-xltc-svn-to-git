package org.sunney.util.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebServiceUtil {
	// 定义Web Service的命名空间
	static final String SERVICE_NS = "http://WebXml.com.cn/";
	// 定义Web Service提供服务的URL
	static final String SERVICE_URL = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";

	// 调用远程Web Service获取省份列表
	public static List<String> getProvinceList() {
		// 调用的方法
		String methodName = "getRegionProvince";
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 实例化SoapObject对象
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		try {
			// 调用Web Service
			ht.call(SERVICE_NS + methodName, envelope);
			if (envelope.getResponse() != null) {
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				// 解析服务器响应的SOAP消息。
				return parseProvinceOrCity(detail);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 根据省份获取城市列表
	public static List<String> getCityListByProvince(String province) {
		// 调用的方法
		String methodName = "getSupportCityString";
		// 创建HttpTransportSE传输对象
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		// 实例化SoapObject对象
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		// 添加一个请求参数
		soapObject.addProperty("theRegionCode", province);
		// 使用SOAP1.1协议创建Envelop对象
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		try {
			// 调用Web Service
			ht.call(SERVICE_NS + methodName, envelope);
			if (envelope.getResponse() != null) {
				// 获取服务器响应返回的SOAP消息
				SoapObject result = (SoapObject) envelope.bodyIn;
				SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
				// 解析服务器响应的SOAP消息。
				return parseProvinceOrCity(detail);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static List<String> parseProvinceOrCity(SoapObject detail) {
		ArrayList<String> result = new ArrayList<String>();
		String[] tests;
		for (int i = 0; i < detail.getPropertyCount(); i++) {
			// 解析出每个省份
			tests = detail.getProperty(i).toString().split(",");
			result.add(detail.getProperty(i).toString().split(",")[0]);
		}
		return result;
	}

	public static SoapObject getWeatherByCity(String cityName) {
		String methodName = "getWeather";
		HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("theCityCode", cityName);
		envelope.bodyOut = soapObject;
		// 设置与.Net提供的Web Service保持较好的兼容性
		envelope.dotNet = true;
		try {
			ht.call(SERVICE_NS + methodName, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			SoapObject detail = (SoapObject) result.getProperty(methodName + "Result");
			return detail;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		/**/
		List<String> provs = getProvinceList();
		for (String prov : provs) {
			System.out.println(prov);
		}
		
		/**/
		List<String> citys = getCityListByProvince("河南");
		for (String city : citys) {
			System.out.println("   " + city);
		}
		
		/*
		 */
		 SoapObject detail = WebServiceUtil.getWeatherByCity("深圳");
		for(int i=0;i<detail.getPropertyCount();i++){
			System.out.println("i = " + i + ": " + detail.getPropertyAsString(i));
		}
		
	}
}
