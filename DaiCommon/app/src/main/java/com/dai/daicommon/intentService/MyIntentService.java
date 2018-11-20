package com.dai.daicommon.intentService;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Service执行在主线程，所以使用IntentService=Service+HandlerThread
 */
public class MyIntentService extends IntentService {
    public MyIntentService(String name) {
        super(name);
    }
    public MyIntentService() {
        super("MyIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if ("uploadPhotoAction".equals(action)) {
                String path = intent.getStringExtra("path");
                try {
                    //模拟上传耗时
                    Thread.sleep(3000);
                    Intent it = new Intent("receivePhotoAction");
                    it.putExtra("complete", path);
                    //如果使用LocalBroadcastManager注册接收，这里必须使用它发送广播
                    LocalBroadcastManager.getInstance(this).sendBroadcast(it);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
