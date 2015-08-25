package org.sunney.ztest;

import org.sunney.life.tools.util.annotion.Constraints;
import org.sunney.life.tools.util.annotion.DbTable;
import org.sunney.life.tools.util.annotion.FieldType;

@DbTable(name = "city")
public class City {
	@FieldType(name = "id", type = "INTEGER", constraints = @Constraints(primaryKey = true, autoIncrement = true))
	private int id;

	@FieldType(name = "code", type = "TEXT", constraints = @Constraints(unique = true))
	private String code;// ���б���

	@FieldType(name = "name", type = "TEXT", constraints = @Constraints(unique = true))
	private String name;// �������

	@FieldType(name = "province", type = "TEXT")
	private String province;// ʡ�ݱ���

	public City(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public City(String code, String name, String province) {
		super();
		this.code = code;
		this.name = name;
		this.province = province;
	}

	public int getId() {
		ClassLoader cl;
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Override
	public String toString() {
		return "City [code=" + code + ", name=" + name + ", province=" + province + "]";
	}

}
