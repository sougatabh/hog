package com.sougata;

/**
 * Created by sougata on 8/28/14.
 */
public abstract class Util {
    private static final String SPACE = " ";
    public static boolean startWithIgnoreCase(String matcher,String src){
        String srcs[] = src.split(SPACE);
        return srcs[0].equalsIgnoreCase(matcher);

    }
}
