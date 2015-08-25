package org.sunney.life.tools.util.annotion;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		for (Table t : Table.values()) {
			Class clazz = Class.forName(t.getTable());
			String sql = AnnotationParserToSQL.parseClassToSQL(clazz);
			System.out.println(sql);
		}
	}
}
