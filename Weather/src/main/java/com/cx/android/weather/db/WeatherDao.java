package com.cx.android.weather.db;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.cx.android.weather.model.City;
import com.cx.android.weather.model.County;
import com.cx.android.weather.model.Province;

public class WeatherDao {
	private static final String dbName = "weather.db";
	private static final int version = 1;
	private static WeatherDao dao;
	private SQLiteDatabase db;
	private static final String TABLE_PROVINCE = "province";
	private static final String TABLE_CITY = "city";
	private static final String TABLE_COUNTY = "county";
	private static final String TABLE_SELECTCITY = "selectCity";

	/**
	 * 私有构造方法，用于初始化数据库
	 * @param context
	 */
	private WeatherDao(Context context){
		WeatherOpenHelper openHelper = new WeatherOpenHelper(context, dbName, null, version);
		db = openHelper.getWritableDatabase();
	}

	/**
	 * 单列模式获取WeatherDao实例
	 * @param context
	 * @return
	 */
	public synchronized static  WeatherDao getInstance(Context context){
		if(dao == null){
			dao = new WeatherDao(context);
		}
		
		return dao;
	}

	/**
	 * 新增省份信息
	 * @param province
	 */
	public void addProvince(Province province){
		ContentValues values = new ContentValues();
		values.put("level", province.getLevel());
		values.put("name", province.getName());
		values.put("province_code", province.getProvinceCode());
		values.put("child_nodes", province.getChildNodes());
		db.insert(TABLE_PROVINCE, null, values);
	}

	/**
	 * 查询省份信息
	 * @return
	 */
	public List<Province> queryProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query(TABLE_PROVINCE, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String provinceCode = cursor.getString(cursor.getColumnIndex("province_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			Province province = new Province(level, name, provinceCode, childNodes);
			list.add(province);
		}
		
		return list;
	}

	/**
	 * 新增城市信息
	 * @param city
	 */
	public void addCity(City city){
		ContentValues values = new ContentValues();
		values.put("level", city.getLevel());
		values.put("name", city.getName());
		values.put("city_code", city.getCityCode());
		values.put("province_code", city.getProvinceCode());
		values.put("child_nodes", city.getChildNodes());
		
		db.insert(TABLE_CITY, null, values);
	}

	/**
	 * 查询省份下属城市信息
	 * @param provinceCode 省份编码
	 * @return 城市信息集合
	 */
	public List<City> queryCitys(String provinceCode){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query(TABLE_CITY, null, " province_code = ?", new String[]{provinceCode}, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String cityCode = cursor.getString(cursor.getColumnIndex("city_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			City city = new City(level, name, cityCode, provinceCode, childNodes);
			list.add(city);
		}
		
		return list;
	}

	/**
	 * 新增县级市信息
	 * @param county
	 */
	public void addCounty(County county){
		ContentValues values = new ContentValues();
		values.put("level", county.getLevel());
		values.put("name", county.getName());
		values.put("city_code", county.getCityCode());
		values.put("county_code", county.getCountyCode());
		values.put("child_nodes", county.getChildNodes());
		
		db.insert(TABLE_COUNTY, null, values);
	}

	/**
	 * 查询城市下属县级市信息
	 * @param cityCode 城市编码
	 * @return 县级市信息集合
	 */
	public List<County> queryCountys(String cityCode){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query(TABLE_COUNTY, null, " city_code = ?", new String[]{cityCode}, null, null, null);
		while(cursor.moveToNext()){
			int level = cursor.getInt(cursor.getColumnIndex("level"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String countyCode = cursor.getString(cursor.getColumnIndex("county_code"));
			int childNodes = cursor.getInt(cursor.getColumnIndex("child_nodes"));
			
			County county = new County(level, name, countyCode, cityCode, childNodes);
			list.add(county);
		}
		
		return list;
	}
	
	public void addSelectCity(String selectCityCode,String selectCityName){
		ContentValues values = new ContentValues();
		values.put("city_code", selectCityCode);
		values.put("city_name", selectCityName);
		
		db.insert(TABLE_SELECTCITY, null, values);
	}
	
	public List<String> querySelectCitys(){
		List<String> list = new ArrayList<String>();
		Cursor cursor = db.query(TABLE_SELECTCITY, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
			list.add(cityName);
		}
		
		return list;
	}

	/**
	 * 判断是否已选择该城市
	 * @param cityName 城市名称
	 * @return
	 */
	public boolean isSelectCity(String cityName){
		Cursor cursor = db.query(TABLE_SELECTCITY, null, "city_name = ?", new String[]{cityName}, null, null, null);
		return cursor.getCount() > 0;
	}

	public void deleteSelectCity(String cityName){
		db.delete(TABLE_SELECTCITY,"city_name = ?",new String[]{cityName});
	}
	
	public void closeDB(){
		if(db != null){
			try{
				db.close();
			}catch(Exception ignored){
			}
		}
	}
}
