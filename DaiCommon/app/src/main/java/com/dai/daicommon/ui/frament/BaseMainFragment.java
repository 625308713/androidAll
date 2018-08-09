package com.dai.daicommon.ui.frament;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dai.daicommon.R;


/**
 * Created by YoKeyword on 16/2/3.
 */
public class BaseMainFragment extends MySupportFragment {

    //调用宿主Activity中的方法
    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOpenDraweListener != null) {
                    mOpenDraweListener.onOpenDrawer();
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化回调,宿主Activity实现了该接口
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }
}
