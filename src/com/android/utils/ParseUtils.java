package com.android.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class ParseUtils {
	private static Gson gson = new Gson();

	public static Object Gson2Object(String json, Type tp) {
		try {
			return gson.fromJson(json, tp);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String Object2Json(Object obj) {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
