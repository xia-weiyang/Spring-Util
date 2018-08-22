package com.jiushig.springutil;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class GsonUtil {

    private static Gson gson = null;

    private static JsonParser jsonParser = null;

    /**
     * 获得gson
     *
     * @return
     */
    public static Gson get() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }

    public static JsonParser getJsonParser() {
        if (jsonParser == null) {
            jsonParser = new JsonParser();
        }
        return jsonParser;
    }
}
