package org.sunney.zookeeper.help.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SunneyZhou 
 * @description 用于标注需要Zookeeper服务的类，watcher={}返回此类里需要watch的字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WatcherDescription {
	String[] watcher();
}
