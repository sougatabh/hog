package com.sougata;

import com.google.gson.JsonObject;

/**
 * Created by sougata on 8/28/14.
 */
public abstract class Util {
    private static final String SPACE = " ";
    public static boolean startWithIgnoreCase(String matcher,String src){
        String srcs[] = src.split(SPACE);
        return srcs[0].equalsIgnoreCase(matcher);

    }

    public static String responseException(Exception exception){
        String jsonResponse = null;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","error");
        jsonObject.addProperty("message",exception.getMessage());
        jsonResponse = jsonObject.toString();
        return jsonResponse;

    }

    public static String responseMessage(String message){
        String jsonResponse = null;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status","error");
        jsonObject.addProperty("message",message);
        jsonResponse = jsonObject.toString();
        return jsonResponse;

    }
}
