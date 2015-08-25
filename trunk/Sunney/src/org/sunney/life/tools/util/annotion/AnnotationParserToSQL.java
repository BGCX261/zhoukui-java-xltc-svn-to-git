package org.sunney.life.tools.util.annotion;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class AnnotationParserToSQL {
	public static String parseClassToSQL(Class clazz) {
		StringBuilder sbuilder = new StringBuilder("CREATE TABLE ");
		java.util.List<String> primaryKeys = new ArrayList<String>();
		DbTable dbTable = (DbTable) clazz.getAnnotation(DbTable.class);
		if (dbTable == null) {
			try {
				throw new Exception("----!");
			} catch (Exception e) {
				return null;
			}
		}
		sbuilder.append(dbTable.name().equals("") ? clazz.getSimpleName().toLowerCase() : dbTable.name()).append("{ \n");
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] anns = field.getDeclaredAnnotations();
			if (containTransparent(anns)) {
				continue;
			}
			for (Annotation ann : anns) {
				if (ann instanceof FieldType) {
					FieldType fieldtype = (FieldType) ann;
					sbuilder.append(fieldtype.name().equals("") ? field.getName().toLowerCase() : fieldtype.name()).append(" ").append(fieldtype.type()).append(" ");
					Constraints constraints = fieldtype.constraints();
					if (constraints != null) {
						if (constraints.primaryKey()) {
							primaryKeys.add(fieldtype.name().equals("") ? field.getName().toLowerCase() : fieldtype.name());
						}
						if (constraints.unique()) {
							sbuilder.append(" unique ");
						}
						if (!constraints.allowNull()) {
							sbuilder.append(" not NULL ");
						}
						if (constraints.autoIncrement()) {
							sbuilder.append(" AUTOINCREMENT ");
						}
					}
					sbuilder.append(",\n");
				}
			}

		}
		sbuilder.append("primary key (");

		for (String key : primaryKeys) {
			sbuilder.append(key).append(",");
		}
		sbuilder.deleteCharAt(sbuilder.toString().length() - 1);
		sbuilder.append(")\n);");
		return sbuilder.toString();
	}

	private static boolean containTransparent(Annotation[] anns) {
		for (Annotation ann : anns)
			if (ann instanceof Transparent)
				return true;

		return false;
	}

}
