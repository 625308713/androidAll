package com.dai.daicommon.app;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.dai.daicommon.R;
import com.dai.daicommon.broadService.StartServiceReceiver;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.weavey.loading.lib.LoadingLayout;

/**
 * 始建于2018年8月2日
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化监听内存泄露工具
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        //动态注册网络的连接（包括wifi和移动网络）监听
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(new StartServiceReceiver(), filter);

        //初始化腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "9565c2adab", false);
        //初始化全局loading
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.add_tick_btn_green)
                .setEmptyImage(R.mipmap.add_circle_btn_gray)
                .setNoNetworkImage(R.mipmap.ic_launcher)
                .setAllTipTextColor(R.color.colorPrimary)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.colorAccent)
                .setReloadButtonWidthAndHeight(150,40);
    }

}
