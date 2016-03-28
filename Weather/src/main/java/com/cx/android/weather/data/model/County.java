package com.cx.android.weather.data.model;

public class County {
	private int level;
	private String name;
	private String countyCode;
	private String cityCode;
	private int childNodes;
	
	public County(int level, String name, String countyCode, String cityCode,
			int childNodes) {
		this.level = level;
		this.name = name;
		this.countyCode = countyCode;
		this.cityCode = cityCode;
		this.childNodes = childNodes;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public int getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(int childNodes) {
		this.childNodes = childNodes;
	}
}
