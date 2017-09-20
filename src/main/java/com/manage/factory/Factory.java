package com.manage.factory;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/5.
 */
public class Factory {
    private String formatDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Gson gson;
    public static Gson getGson(){
        if(gson==null){
            return new Gson();
        }
        else {
            return gson;
        }
    }
    public String getDate(){
        Date currentTime = new Date();
        return sdf.format(currentTime);
    }
}
