package com.dai.daicommon.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dai.daicommon.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 响应式编程RxJava RxAndroid
 */
public class DemoRxAndroidActivity extends Activity{
    private String TAG = getClass().getSimpleName();
    private Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_rxandroid);
        //被观察者
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
            .observeOn(AndroidSchedulers.mainThread())//observeOn是回调线程，设置在主线程
            .subscribeOn(Schedulers.io())//执行在io线程
//            .subscribe(new Observer<String>() {
//                @Override
//                public void onSubscribe(Disposable d) {
//                    mDisposable = d;
//                    Log.e(TAG,"onSubscribe");
//                }
//
//                @Override
//                public void onNext(String value) {
//                    if ("连载2".equals(value)){
//                        //取消订阅
//                        mDisposable.dispose();
//                        return;
//                    }
//                    Log.e(TAG,"onNext:"+value);
//                }
//
//                @Override
//                public void onError(Throwable e) {
//                    Log.e(TAG,"onError="+e.getMessage());
//                }
//
//                @Override
//                public void onComplete() {
//                    Log.e(TAG,"onComplete()");
//                }
//            });
        //如果只需要执行onNext
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String value) throws Exception {
                    Log.e(TAG,"onNext:"+value);
            }
        });
    }
}
