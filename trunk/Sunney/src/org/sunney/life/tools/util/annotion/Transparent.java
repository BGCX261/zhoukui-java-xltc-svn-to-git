package org.sunney.life.tools.util.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 字段是否透明
 *     如果透明则不再反射处理
 * @author 周奎-
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transparent {
}
