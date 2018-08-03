package com.dai.daicommon.service.presenter;

import com.dai.daicommon.service.manager.DataManager;
import com.dai.daicommon.service.view.MyView;

import rx.subscriptions.CompositeSubscription;

public class BasePresenter implements Presenter{
    protected DataManager manager;
    protected CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate() {
        manager = new DataManager();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(MyView view) {

    }
}
