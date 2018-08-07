package com.dai.daicommon.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dai.daicommon.R;
import com.dai.daicommon.service.bean.Book;

import java.util.List;

/**
 * 拖拽适配器
 */
public class DraggableAdapter extends BaseItemDraggableAdapter<Book.BooksBean, BaseViewHolder> {

    public DraggableAdapter(List<Book.BooksBean> data) {
        super(R.layout.adapter_book, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Book.BooksBean item) {
        helper.setText(R.id.tv,item.getTitle())
                .addOnClickListener(R.id.img)
                .addOnClickListener(R.id.tv);//设置子空间单击
        Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.img));
    }
}
