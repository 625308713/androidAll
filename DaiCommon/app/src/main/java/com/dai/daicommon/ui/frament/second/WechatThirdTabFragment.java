package com.dai.daicommon.ui.frament.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.daicommon.R;
import com.dai.daicommon.listener.OnItemClickListener2;
import com.dai.daicommon.service.bean.Article;
import com.dai.daicommon.ui.adapter.HomeAdapter;
import com.dai.daicommon.ui.adapter.HomeAdapter2;
import com.dai.daicommon.ui.frament.BaseMainFragment;
import com.dai.daicommon.ui.frament.DetailFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class WechatThirdTabFragment extends BaseMainFragment2 {
    private RecyclerView mRecy;
    private Toolbar mToolbar;
    private HomeAdapter2 mAdapter;
    private String[] mTitles;
    private String[] mContents;

    public static WechatThirdTabFragment newInstance() {

        Bundle args = new Bundle();

        WechatThirdTabFragment fragment = new WechatThirdTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment_tab_third, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mTitles = getResources().getStringArray(R.array.array_title);
        mContents = getResources().getStringArray(R.array.array_content);

        mToolbar.setTitle(R.string.more);
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAdapter = new HomeAdapter2(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener2() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                ((MainFragment) getParentFragment()).startBrotherFragment(DetailFragment2.newInstance(mAdapter.getItem(position).getTitle()));
                // 或者使用EventBus
//                EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(DetailFragment.newInstance(mAdapter.getItem(position).getTitle())));
            }
        });

        // Init Datas
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Article article = new Article(mTitles[i], mContents[i]);
            articleList.add(article);
        }
        mAdapter.setDatas(articleList);
    }
}
