package com.dai.daicommon.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 常用工具类
 */
public class MyUtil {
    public static void showToast(Context context, String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
