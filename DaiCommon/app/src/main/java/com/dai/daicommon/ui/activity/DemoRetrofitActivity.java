package com.dai.daicommon.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dai.daicommon.R;
import com.dai.daicommon.service.bean.Book;
import com.dai.daicommon.service.bean.GroupBook;
import com.dai.daicommon.service.bean.MyMultipleItem;
import com.dai.daicommon.service.bean.Person;
import com.dai.daicommon.service.presenter.BookPresenter;
import com.dai.daicommon.service.view.BookView;
import com.dai.daicommon.ui.activity.camera.CustomerCameraActivity;
import com.dai.daicommon.ui.activity.frament.FramentEnterActivity;
import com.dai.daicommon.ui.adapter.BookAdapter;
import com.dai.daicommon.ui.adapter.BookSectionAdapter;
import com.dai.daicommon.ui.adapter.DraggableAdapter;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络请求
 */
public class DemoRetrofitActivity extends Activity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private BookPresenter mBookPresenter = new BookPresenter();
    private LoadingLayout loadingLayout;
    private RecyclerView my_recycler;
    //基本布局适配器
    private BookAdapter bookAdapter;
    //分组适配器
    private BookSectionAdapter sectionAdapter;
    //可拖拽适配器
    private DraggableAdapter draggableAdapter;
    private List<Book.BooksBean> childBooks;
    //数据总数
    private int totalCount = 30;
    //当前数据数
    private int mCurrentCounter = 0;
    //请求数量
    private int requestCount = 10;
    //首次请求
    private boolean isFirst = true;
    //下拉刷新
    private SwipeRefreshLayout mRefreshLayout;
    //加载更多监听
    private BaseQuickAdapter.RequestLoadMoreListener requestMoreListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_view);
        loadingLayout = (LoadingLayout) findViewById(R.id.loading_layout);
        context = this;
        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pull_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        initRecycler();
    }
    //初始化Recycler
    public void initRecycler(){
        my_recycler = findViewById(R.id.my_recycler);
        my_recycler.setLayoutManager(new LinearLayoutManager(this));
        childBooks = new ArrayList<Book.BooksBean>();
        bookAdapter = new BookAdapter(R.layout.adapter_book,childBooks);
        // 没有数据的时候默认显示该布局
        bookAdapter.setEmptyView(View.inflate(context,R.layout.empty_view, null));
       //基础布局
        my_recycler.setAdapter(bookAdapter);

        /******可拖拽适配器********/
        draggableAdapter = new DraggableAdapter(childBooks);
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(draggableAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(my_recycler);
        // 开启拖拽
        draggableAdapter.enableDragItem(itemTouchHelper, R.id.img, true);
        draggableAdapter.setOnItemDragListener(new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.i("dai","拖拽中...");
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.i("dai","拖拽完成");
            }
        });
        // 开启滑动删除
        draggableAdapter.enableSwipeItem();
        draggableAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.i("dai","Start");
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.i("dai","是否删除都触发这个结束滑动操作");
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.i("dai","滑动删除完成");
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            }
        });
//        my_recycler.setAdapter(draggableAdapter);
        /**********设置分组样式***********/
        List<GroupBook> groupBooksList = new ArrayList<>();
        GroupBook gb = new GroupBook(true,"第一行");
        groupBooksList.add(gb);
        Book.BooksBean b = new Book.BooksBean();
        b.setTitle("111");
        GroupBook gb1 = new GroupBook(b);
        groupBooksList.add(gb1);
        Book.BooksBean b1 = new Book.BooksBean();
        b1.setTitle("222");
        GroupBook gb2 = new GroupBook(b1);
        groupBooksList.add(gb2);
        Book.BooksBean b2 = new Book.BooksBean();
        b2.setTitle("333");
        GroupBook gb3 = new GroupBook(b2);
        groupBooksList.add(gb3);
        GroupBook gb4 = new GroupBook(true,"第二行");
        groupBooksList.add(gb4);
        Book.BooksBean bbb = new Book.BooksBean();
        bbb.setTitle("4444");
        GroupBook ggg = new GroupBook(bbb);
        groupBooksList.add(ggg);
        sectionAdapter = new BookSectionAdapter(R.layout.adapter_section_item,R.layout.adapter_head,groupBooksList);
//        my_recycler.setAdapter(sectionAdapter);

        /*************多样式布局***************/
        List<MyMultipleItem> datas02 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                datas02.add(new MyMultipleItem(MyMultipleItem.FIRST_TYPE, new Person("ZERO",i+"岁")));
            } else if (i % 7 == 0) {
                datas02.add(new MyMultipleItem(MyMultipleItem.SECOND_TYPE, new Person("FIRST",i+"岁")));
            } else {
                datas02.add(new MyMultipleItem(MyMultipleItem.NORMAL_TYPE,new Person("张三","18岁")));
            }
        }
//        my_recycler.setAdapter(new MultipleItemAdapter(datas02));

        //列表单击
        bookAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(context, childBooks.get(position).getTitle()+childBooks.get(position).getPrice(), Toast.LENGTH_SHORT).show();
            }
        });
        //列表中子控件单击
        bookAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId() == R.id.img){
                    Toast.makeText(context, childBooks.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, childBooks.get(position).getPrice(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //添加加载list动画
        bookAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        requestMoreListener = new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                my_recycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //成功获取更多数据
                        mBookPresenter.getSearchBooks("西游记", null, requestCount, 10);
                        requestCount += 10;
                    }
                }, 200);
            }
        };
        //设置是否开启上滑加载更多
        bookAdapter.setEnableLoadMore(true);
        // 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
//        bookAdapter.setPreLoadNumber(5);
        //设置自定义加载更多布局
        bookAdapter.setLoadMoreView(new CustomLoadMoreView());
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
            //list添加头部
            case R.id.btn_add_head:
                View headView = View.inflate(context, R.layout.head_view, null);
                bookAdapter.addHeaderView(headView);
                break;
            //list添加底部
            case R.id.btn_add_footer:
                bookAdapter.addFooterView(View.inflate(context, R.layout.footer_view, null));
                break;
            //跳转Frament的Demo
            case R.id.btn_frament:
                startActivity(new Intent(DemoRetrofitActivity.this, FramentEnterActivity.class));
                break;
            //拍照,图片选择
            case R.id.btn_camera:
                startActivity(new Intent(DemoRetrofitActivity.this, CustomerCameraActivity.class));
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
            //这里需要给数据添加值而不能在这里赋值
            if(isFirst){
                isFirst = false;
                childBooks.clear();
                //首次或刷新后设置加载更多监听，刷新后必须再次设置
                bookAdapter.setOnLoadMoreListener(requestMoreListener, my_recycler);
            }
            //请求完成，隐藏下拉刷新UI
            mRefreshLayout.setRefreshing(false);
            childBooks.addAll(mBook.getBooks());
            mCurrentCounter = bookAdapter.getData().size();
            bookAdapter.notifyDataSetChanged();
            draggableAdapter.notifyDataSetChanged();
            if (mCurrentCounter >= totalCount) {
                //数据全部加载完毕
                bookAdapter.loadMoreEnd();
            }else{
                bookAdapter.loadMoreComplete();
            }
//            sectionAdapter.notifyDataSetChanged();
//            loadingLayout.setStatus(LoadingLayout.Empty);//无数据
//            loadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            loadingLayout.setStatus(LoadingLayout.Success);//加载成功
        }

        @Override
        public void onError(String result) {
            //获取更多数据失败
            bookAdapter.loadMoreFail();
            loadingLayout.setStatus(LoadingLayout.Error);//错误
        }
    };

    @Override
    public void onRefresh() {
        //下拉刷新
        isFirst = true;
        mBookPresenter.getSearchBooks("西游记", null, 0, 10);
    }

    //自定义上滑加载更多布局
    public final class CustomLoadMoreView extends LoadMoreView {

        @Override public int getLayoutId() {
            return R.layout.view_load_more;
        }

        /**
         * 如果返回true，数据全部加载完毕后会隐藏加载更多
         * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
         */
        @Override public boolean isLoadEndGone() {
            return true;
        }

        @Override protected int getLoadingViewId() {
            return R.id.load_more_loading_view;
        }

        @Override protected int getLoadFailViewId() {
            return R.id.load_more_load_fail_view;
        }

        /**
         * isLoadEndGone()为true，可以返回0
         * isLoadEndGone()为false，不能返回0
         */
        @Override protected int getLoadEndViewId() {
            return 0;
        }
    }
}
