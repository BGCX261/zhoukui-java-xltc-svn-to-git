package org.sunney.help.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * @author fisher
 * @description json工具包
 * @date 2011-8-9
 */
public class JsonUtil {
	private static final Log log = LogFactory.getLog(JsonUtil.class);

	/**
	 * @description 将java对象序列化为json字符串
	 * @param Object
	 * @return String
	 */

	public static final String serialize(Object o) {
		try {
			String jsonStr = JSON.toJSONString(o);
			log.debug("The jsonstr is:" + jsonStr);
			return jsonStr;
		} catch (Exception e) {
			log.debug("Serialize java obejct to jsonStr error!,caused by:", e);
			return null;
		}

	}
	
	/**
	 * @description 自定义序列化,当isInclude为true,序列化后的字符串
	 * 				只包含propertyFilter中与对象所对应的属性名,反之,
	 * 				则排除propertyFileter中得属性名
	 * 例如： User u=new User();
	 * 		 u.setName('hhh');
	 * 		 u.setId('123');
	 * 		 serialize(u,new String[]{'name'},true);
	 * 结果为:
	 * 		{"name":"hhh"}
	 * @param Object o 
	 * @param propertyFilter 属性过滤器
	 * @param isInclude 是否包含filter中得属性
	 * @return
	 */
	
	public final static String serialize(Object o, final String[] propertyFilter,
			final boolean isInclude) {
		try {
			PropertyFilter filter = new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					for (String property : propertyFilter) {
						if(name.equals(property)){
							return isInclude;
						}
					}
					return !isInclude;
				}
			};
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.getPropertyFilters().add(filter);
			serializer.write(o);
			String jsonStr=out.toString();
			log.debug("The jsonstr is:" + jsonStr);
			return jsonStr;
		} catch (Exception e) {
			log.debug("Serialize java obejct to jsonStr error!,caused by:", e);
			return null;
		}

	}

	/**
	 * @description 将json字符串反序列化成java对象
	 * @param jsonStr
	 * @param clazz
	 * @return Object
	 */

	public static final <T> Object deserialize(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseObject(jsonStr, clazz);
		} catch (Exception e) {
			log.debug("Deserialize jsonStr to Object error!,caused by:", e);
			return null;
		}
	}

	/**
	 * @description 将JSON字符串反序列化成List数组
	 * @param jsonStr
	 * @param clazz
	 * @return List
	 */

	public static final <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseArray(jsonStr, clazz);
		} catch (Exception e) {
			log.debug("Deserialize jsonStr to Object error!,caused by:", e);
			return null;
		}

	}

}
