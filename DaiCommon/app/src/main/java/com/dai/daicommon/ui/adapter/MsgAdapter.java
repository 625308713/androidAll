package com.dai.daicommon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dai.daicommon.R;
import com.dai.daicommon.listener.OnItemClickListener2;
import com.dai.daicommon.service.bean.Msg;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YoKeyword on 16/6/30.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.VH> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Msg> mItems = new ArrayList<>();

    private OnItemClickListener2 mClickListener;

    public MsgAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addMsg(Msg bean) {
        mItems.add(bean);
        notifyItemInserted(mItems.size() - 1);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wechat_msg, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(holder.getAdapterPosition(), v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Msg item = mItems.get(position);

        holder.tvMsg.setText(item.message);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener2 listener) {
        mClickListener = listener;
    }

    class VH extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView tvMsg;

        public VH(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tvMsg = (TextView) itemView.findViewById(R.id.tv_msg);
        }
    }
}
