package com.nab.ecommerce.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;

public abstract class BaseEntity extends Object implements Serializable {

  static Gson gson;

  static {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    gson = gsonBuilder.disableHtmlEscaping().create();
  }

  public String toJsonString() {
    return gson.toJson(this);
  }
}