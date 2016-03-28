package com.cx.android.weather.data.model;

public class City {
	private int level;
	private String name;
	private String cityCode;
	private String provinceCode;
	private int childNodes;
	
	public City(int level, String name, String cityCode, String provinceCode,
			int childNodes) {
		this.level = level;
		this.name = name;
		this.cityCode = cityCode;
		this.provinceCode = provinceCode;
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
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public int getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(int childNodes) {
		this.childNodes = childNodes;
	}
}
