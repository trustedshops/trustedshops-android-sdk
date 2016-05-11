package com.trustedshops.trustbadgeexample.util;

import com.google.gson.Gson;

/**
 * Created by trumga2 on 5/9/16.
 */
public class JsonUtil {

    public static <T> String toJson(T object) {
        if (object == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T fromJson(Class<T> _class, String json) {
        if (json == null || _class == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, _class);
    }
}
