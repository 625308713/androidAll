package com.dai.daicommon.service.presenter;

import com.dai.daicommon.service.bean.Book;
import com.dai.daicommon.service.view.BookView;
import com.dai.daicommon.service.view.MyView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BookPresenter extends BasePresenter{
    private BookView mBookView;
    private Book mBook;

    @Override
    public void attachView(MyView view) {
        mBookView = (BookView)view;
    }
    public void getSearchBooks(String name,String tag,int start,int count){
        mCompositeSubscription.add(manager.getSearchBooks(name,tag,start,count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        if (mBook != null){
                            mBookView.onSuccess(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mBookView.onError("请求失败！！");
                    }

                    @Override
                    public void onNext(Book book) {
                        mBook = book;
                    }
                })
        );
    }
}
