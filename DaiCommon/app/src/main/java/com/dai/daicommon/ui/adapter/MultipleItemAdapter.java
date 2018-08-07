package com.dai.daicommon.ui.adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dai.daicommon.R;
import com.dai.daicommon.service.bean.MyMultipleItem;

import java.util.List;

/**
 * 多布局适配器
 */
public class MultipleItemAdapter extends BaseMultiItemQuickAdapter<MyMultipleItem, BaseViewHolder> {
    public MultipleItemAdapter(List data) {
        super(data);
        //必须绑定type和layout的关系
        addItemType(MyMultipleItem.FIRST_TYPE, R.layout.item_expand_lv0);
        addItemType(MyMultipleItem.SECOND_TYPE, R.layout.item_expand_lv1);
        addItemType(MyMultipleItem.NORMAL_TYPE, R.layout.item_expand_end);

    }

    @Override
    protected void convert(BaseViewHolder helper, MyMultipleItem item) {
        switch (helper.getItemViewType()) {
            case MyMultipleItem.FIRST_TYPE:
                if(item.getData() != null){
                    helper.setText(R.id.zero_tv, item.getData().getName());
                }
                break;
            case MyMultipleItem.SECOND_TYPE:
                if(item.getData() != null){
                    helper.setImageResource(R.id.secont_img, R.mipmap.add_tick_btn_green);
                }
                break;
            case MyMultipleItem.NORMAL_TYPE:
                if(item.getData() != null){
                    helper.setImageResource(R.id.end_img, R.mipmap.ic_launcher)
                            .setText(R.id.tv_name, item.getData().getName())
                            .setText(R.id.tv_age, item.getData().getAge());
                }
                break;
        }
    }

}
