package org.sunney.life.tools.util.annotion;

public enum Table {
	CITY("org.sunney.life.tools.util.annotion.City"), 
	PROVINCE("org.sunney.life.tools.vo.Province"), 
	WEATHER("org.sunney.life.tools.vo.Weather"), 
	WEATHERCARE("org.sunney.life.tools.vo.WeatherCare");

	private String table;

	private Table(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}
}
