package com.dai.daicommon.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dai.daicommon.R;
import com.jph.takephoto.model.TImage;

import java.io.File;
import java.util.List;

public class ImageAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {
    public ImageAdapter(int layoutResId, @Nullable List<TImage> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, TImage item) {
        Glide.with(mContext).load(new File(item.getCompressPath())).crossFade().into((ImageView) helper.getView(R.id.img));
    }
}
