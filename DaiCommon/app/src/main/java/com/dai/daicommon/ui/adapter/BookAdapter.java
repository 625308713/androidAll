package com.dai.daicommon.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dai.daicommon.R;
import com.dai.daicommon.service.bean.Book;

import java.util.List;

public class BookAdapter extends BaseQuickAdapter<Book.BooksBean, BaseViewHolder> {
    public BookAdapter(int layoutResId, @Nullable List<Book.BooksBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Book.BooksBean item) {
        helper.setText(R.id.tv,item.getTitle());
        Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.img));
    }
}
