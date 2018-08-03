package com.dai.daicommon.service.manager;

import android.content.Context;

import com.dai.daicommon.service.RetrofitHelper;
import com.dai.daicommon.service.RetrofitService;
import com.dai.daicommon.service.bean.Book;

import rx.Observable;

/**
 * 方便的调用RetrofitService 中定义的方法
 */

public class DataManager {
    private RetrofitService mRetrofitService;
    public DataManager(){
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
    }
    public Observable<Book> getSearchBooks(String name, String tag, int start, int count){
        return mRetrofitService.getSearchBook(name,tag,start,count);
    }

}
