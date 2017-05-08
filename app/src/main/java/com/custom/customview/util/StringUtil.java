package com.custom.customview.util;


import com.custom.customview.application.GlobalConfig;

/**
 * Created by Jone on 17/4/21.
 */

public class StringUtil {

    public static String StringId(int id){
        return GlobalConfig.getContext().getString(id);
    }

    public static String StringId(int id,Object ...args){
        return GlobalConfig.getContext().getString(id,args);
    }
}
