package com.dai.daicommon.ui.frament;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dai.daicommon.R;


/**
 * Created by YoKeyword on 16/2/7.
 */
public class BaseBackFragment extends MySupportFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.mipmap.add_tick_btn_green);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }
}
