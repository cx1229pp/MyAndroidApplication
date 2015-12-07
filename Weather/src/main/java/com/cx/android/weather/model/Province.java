package com.cx.android.weather.model;

public class Province {
	private int level;
	private String name;
	private String provinceCode;
	private int childNodes;

	public Province(int level, String name, String provinceCode, int childNodes) {
		this.level = level;
		this.name = name;
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
