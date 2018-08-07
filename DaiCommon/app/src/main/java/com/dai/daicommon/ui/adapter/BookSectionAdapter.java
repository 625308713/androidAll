package com.dai.daicommon.ui.adapter;


import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dai.daicommon.R;
import com.dai.daicommon.service.bean.GroupBook;

import java.util.List;

/**
 * 分组布局
 */
public class BookSectionAdapter extends BaseSectionQuickAdapter<GroupBook,BaseViewHolder> {

    public BookSectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    //head布局设置
    @Override
    protected void convertHead(BaseViewHolder helper, GroupBook item) {
        helper.setText(R.id.head_tv, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBook item) {
        helper.setText(R.id.section_tv, item.t.getTitle());

    }
}
