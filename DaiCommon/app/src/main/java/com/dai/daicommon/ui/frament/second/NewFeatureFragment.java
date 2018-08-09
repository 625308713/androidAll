package com.dai.daicommon.ui.frament.second;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dai.daicommon.R;
import com.dai.daicommon.ui.frament.BaseBackFragment;
import com.dai.daicommon.ui.frament.CycleFragment;
import com.dai.daicommon.ui.frament.ViewFragment;


/**
 * 该类是展示 1.0 版本新特性：拓展事务 extraTransaction()
 * <p>
 * Created by YoKey on 16/11/25.
 */
public class NewFeatureFragment extends BaseBackFragment2 {
    public static NewFeatureFragment newInstance() {
        return new NewFeatureFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wechat_fragment_new_feature, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbarNav(toolbar);
        toolbar.setTitle("NewFeatures");

        // 自定义动画启动一个Fragment，并且不隐藏自己
        view.findViewById(R.id.btn_start_dont_hide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraTransaction()
                        .setCustomAnimations(R.anim.v_fragment_enter, 0, 0, R.anim.v_fragment_exit)
                        .startDontHideSelf(ViewFragment.newInstance());
            }
        });

        // 自定义动画启动一个Fragment
        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extraTransaction()
//                        .setTag("CustomTag")
//                        . ...
                        .setCustomAnimations(R.anim.v_fragment_enter, R.anim.v_fragment_pop_exit,
                                R.anim.v_fragment_pop_enter, R.anim.v_fragment_exit)
                        .start(CycleFragment.newInstance(0));
            }
        });

        return attachToSwipeBack(view);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }
}
