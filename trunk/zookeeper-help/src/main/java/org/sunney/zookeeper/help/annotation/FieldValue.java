/**
 * 
 */
package org.sunney.zookeeper.help.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SunneyZhou
 * @description 为需要动态获取值的字段设置初始值
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldValue {
	String initValue() default "";
}
