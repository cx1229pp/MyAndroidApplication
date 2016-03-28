package com.cx.android.weather.data.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherOpenHelper extends SQLiteOpenHelper {
	private static final String sql_province = " create table province("+
											   " id integer primary key autoincrement,"+
											   " level integer,"+
											   " name text,"+
											   " province_code text,"+
											   " child_nodes integer)";
	
	private static final String sql_city = " create table city("+
										   " id integer primary key autoincrement,"+
										   " level integer,"+
										   " name text,"+
										   " city_code text,"+
										   " province_code text,"+
										   " child_nodes integer)";
	
	private static final String sql_county = " create table county("+
											 " id integer primary key autoincrement,"+
											 " level integer,"+
											 " name text,"+
											 " county_code text,"+
											 " city_code text,"+
											 " child_nodes integer)";
	
	private static final String sql_selectCity = " create table selectCity("+
												 " id integer primary key autoincrement,"+
												 " city_code text,"+
												 " city_name text)";
	
	public WeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public WeatherOpenHelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql_province);
		db.execSQL(sql_city);
		db.execSQL(sql_county);
		db.execSQL(sql_selectCity);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
