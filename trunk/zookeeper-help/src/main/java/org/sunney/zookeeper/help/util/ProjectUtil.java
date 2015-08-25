package org.sunney.zookeeper.help.util;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * 
 * @author SunneyZhou
 * @description 系统帮助类
 */
public class ProjectUtil {
	public static String getProjectName() {
		return SystemUtils.USER_DIR.substring(SystemUtils.USER_DIR.lastIndexOf(File.separatorChar) + 1);
	}

	public static String getClassPackage(Object object) {
		Assert.assertNotNull("object[" + object + "]对象为空", object);
		String packageName = ClassUtils.getPackageName(object, null);
		Assert.assertNotNull(getClassShortName(object) + "类的包名为空！", packageName);
		return packageName;
	}

	public static String getClassShortName(Object object) {
		Assert.assertNotNull("object[" + object + "]对象为空", object);
		return ClassUtils.getShortClassName(object, null);
	}
}
