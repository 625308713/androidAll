package com.dai.daicommon.service.presenter;

import com.dai.daicommon.service.bean.VersionBean;
import com.dai.daicommon.service.view.MyView;
import com.dai.daicommon.service.view.VersionView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VersionPresenter extends BasePresenter{
    private VersionView versionView;
    private VersionBean versionBean;
    @Override
    public void attachView(MyView view) {
        versionView = (VersionView)view;
    }
    public void getVersionInfo(){
        mCompositeSubscription.add(manager.getVersionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VersionBean>() {
                    @Override
                    public void onCompleted() {
                        if (versionBean != null){
                            versionView.onSuccess(versionBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        versionView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(VersionBean vb) {
                        versionBean = vb;
                    }
                })
        );
    }
}
