package org.sunney.life.tools.util.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * �ֶ��Ƿ�͸��
 *     ���͸�����ٷ��䴦��
 * @author �ܿ�-
 *
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transparent {
}
