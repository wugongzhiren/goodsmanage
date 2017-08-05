package com.manage.factory;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/5.
 */
public class Factory {
    private static Gson gson;
    public static Gson getGson(){
        if(gson==null){
            return new Gson();
        }
        else {
            return gson;
        }
    }
}
