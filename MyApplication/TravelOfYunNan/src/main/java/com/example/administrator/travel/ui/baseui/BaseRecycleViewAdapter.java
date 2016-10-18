package com.example.administrator.travel.ui.baseui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.utils.LogUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/8 0008.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseRecycleViewHolder<T>> {
    public List<T> mDatas;
    public Context mContext;

    public BaseRecycleViewAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }
    public void notifiyData(List list){
        if (list==null)return;
        mDatas=list;
        this.notifyDataSetChanged();
    }
    public View inflateView(int res,ViewGroup parent){
       return LayoutInflater.from(mContext).inflate(R.layout.item_activity_custom_destination, parent, false);
    }


    @Override
    public void onBindViewHolder(final BaseRecycleViewHolder holder, int position) {
        try {
            holder.itemView.setTag(mDatas.get(position));
            if (itemClickListener!=null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = mDatas.indexOf(holder.itemView.getTag());
                        itemClickListener.onItemClick(tag);
                    }
                });
            }
            holder.childBindView(position,mDatas.get(position),mContext);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("抛异常啦");
        }
    }
    private OnItemClickListener itemClickListener;

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
