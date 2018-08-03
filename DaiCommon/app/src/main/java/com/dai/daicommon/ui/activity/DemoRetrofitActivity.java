package com.dai.daicommon.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.dai.daicommon.R;
import com.dai.daicommon.app.MyUtil;
import com.dai.daicommon.service.bean.Book;
import com.dai.daicommon.service.presenter.BookPresenter;
import com.dai.daicommon.service.view.BookView;
import com.dai.daicommon.ui.adapter.BookAdapter;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求
 */
public class DemoRetrofitActivity extends Activity implements View.OnClickListener{
    private Context context;
    private BookPresenter mBookPresenter = new BookPresenter();
    private LoadingLayout loadingLayout;
    private RecyclerView my_recycler;
    private BookAdapter bookAdapter;
    private List<Book.BooksBean> childBooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        context = this;
        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);
        initRecycler();
    }
    //初始化Recycler
    public void initRecycler(){
        my_recycler = findViewById(R.id.my_recycler);
        my_recycler.setLayoutManager(new LinearLayoutManager(this));
        childBooks = new ArrayList<Book.BooksBean>();
        bookAdapter = new BookAdapter(R.layout.adapter_book,childBooks);
        my_recycler.setAdapter(bookAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                loadingLayout.setStatus(LoadingLayout.Loading);//加载中
                mBookPresenter.getSearchBooks("西游记", null, 0, 10);
                break;
                //跳转到Glide的Activity
            case R.id.to_glide:
                startActivity(new Intent(DemoRetrofitActivity.this,DemoGlideActivity.class));
                break;
                //RxJava
            case R.id.to_rxjava:
                startActivity(new Intent(DemoRetrofitActivity.this,DemoRxAndroidActivity.class));
                break;
                //打开wifi
            case R.id.btn_wifi:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
                break;
                //打开移动流量
            case R.id.btn_mobile:
                WifiManager wifiManager2 = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiManager2.setWifiEnabled(false);
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mBookPresenter.onStop();
    }

    private BookView mBookView = new BookView() {
        @Override
        public void onSuccess(Book mBook) {
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            loadingLayout.setStatus(LoadingLayout.Error);//错误
//            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
            //这里需要给数据添加值而不能在这里赋值
            childBooks.clear();
            childBooks.addAll(mBook.getBooks());
            bookAdapter.notifyDataSetChanged();

        }

        @Override
        public void onError(String result) {
            MyUtil.showLog(context,result);
        }
    };
}
