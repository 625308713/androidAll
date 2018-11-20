package com.dai.daicommon.service.presenter;

import com.dai.daicommon.service.bean.Person;
import com.dai.daicommon.service.view.MyView;
import com.dai.daicommon.service.view.PersonView;

import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PersonPresenter extends BasePresenter{
    private PersonView personView;
    private Person person;

    @Override
    public void attachView(MyView view) {
        personView = (PersonView)view;
    }


    public void testMethod(Map<String, String> map){
        mCompositeSubscription.add(manager.testMethod(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Person>() {
                    @Override
                    public void onCompleted() {
                        if (person != null){
                            personView.onSuccess(person);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        personView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Person p) {
                        person = p;
                    }
                })
        );
    }
}
