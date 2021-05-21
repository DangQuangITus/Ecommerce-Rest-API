package com.nab.ecommerce.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;

public class GsonUtil {

  private static final Gson GSON;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    GSON = gsonBuilder.disableHtmlEscaping().create();
  }

  public GsonUtil() {
  }

  public static <E> E fromJson(String data, Type type) {
    return GSON.fromJson(data, type);
  }

  public static String toJsonString(Object obj) {
    return GSON.toJson(obj);
  }

  public static <T> T fromJsonString(String sJson, Class<T> t) {
    return GSON.fromJson(sJson, t);
  }
}
