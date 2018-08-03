package com.dai.daicommon.service.presenter;

import android.view.View;

import com.dai.daicommon.service.view.MyView;

/**
 * 用于网络的请求以及数据的获取
 */
public interface Presenter {
    void onCreate();

    void onStop();

    void attachView(MyView view);
}
