package com.dai.daicommon.ui.frament;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.daicommon.R;
import com.dai.daicommon.listener.OnItemClickListener;
import com.dai.daicommon.ui.adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;



public class PagerChildFragment extends MySupportFragment {
    private static final String ARG_FROM = "arg_from";

    private int mFrom;

    private RecyclerView mRecy;
    private PagerAdapter mAdapter;

    public static PagerChildFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        //传值 在onCreat中获取
        PagerChildFragment fragment = new PagerChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);

        mAdapter = new PagerAdapter(_mActivity);
        LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(manager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (getParentFragment() instanceof DiscoverFragment) {
                    ((DiscoverFragment) getParentFragment()).start(CycleFragment.newInstance(1));
                }
            }
        });

        mRecy.post(new Runnable() {
            @Override
            public void run() {
                // Init Datas
                List<String> items = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    String item;
                    if (mFrom == 0) {
                        item = getString(R.string.recommend) + " " + i;
                    } else if (mFrom == 1) {
                        item = getString(R.string.hot) +" " + i;
                    } else {
                        item = getString(R.string.favorite) +" " + i;
                    }
                    items.add(item);
                }
                mAdapter.setDatas(items);
            }
        });
    }
}
