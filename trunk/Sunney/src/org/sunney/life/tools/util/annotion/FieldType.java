package org.sunney.life.tools.util.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldType {
	String name() default "";
	String type() default "TEXT";
	Constraints constraints() default @Constraints;
}
