/**
 * 
 */
package com.example.paypro.manager;

import java.lang.reflect.Type;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Tarang
 *
 */
public class JSONHandler {
	
	private static final JSONHandler instance = new JSONHandler();
	
	private JSONHandler() {
		
	}

	public static JSONHandler getInstance() {
		return instance;
	}
	
	public String converToJSON(Object obj) {
		System.out.println("-------" + obj);
		Gson gson = new Gson();
		String json = gson.toJson(obj);
		return json;
	}
	
	public Object convertToJavaUnderscoreCase(String json,  Class<?> classOfT) {
		System.out.println("-------" + json);
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		Object obj = gson.fromJson(json, classOfT);
		return obj;
	}
	
	public Object convertToJavaUnderscoreCase(String json,  Type type) {
		System.out.println("-------" + json);
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		Object obj = gson.fromJson(json, type);
		System.out.println(obj);
		return obj;
	}
	
	public Object convertToJava(String json,  Class<?> classOfT) {

		System.out.println("-------" + json);
		Gson gson = new Gson();
		Object obj = gson.fromJson(json, classOfT);
		return obj;
	
	}
	
	public Object convertToJava(String json,  Type type) {
		System.out.println("-------" + json);
		Gson gson = new Gson();
		Object obj = gson.fromJson(json, type);
		System.out.println(obj);
		return obj;
	
		
	}
}
