package com.dai.daicommon.app;

import android.content.Context;
import android.widget.Toast;

import com.dai.daicommon.service.RetrofitService;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyUtil {
    public static void showLog(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }

    public static RetrofitService getRetrofitService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        return service;
    }
}
