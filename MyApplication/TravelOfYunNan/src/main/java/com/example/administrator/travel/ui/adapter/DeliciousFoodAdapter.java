package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Delicious;
import com.example.administrator.travel.ui.activity.DeliciousDetailActivity;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class DeliciousFoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Delicious> mDatas;

    public DeliciousFoodAdapter(Context mContext, List<Delicious> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_activity_delicious_food, parent, false);
        return new DeliciousHolder(root);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {



    }


    @Override
    public int getItemCount() {
        return 20;
    }

   public class DeliciousHolder extends RecyclerView.ViewHolder {



       public DeliciousHolder(View itemView) {
            super(itemView);


              itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mContext.startActivity(new Intent(mContext, DeliciousDetailActivity.class));
                   }
               });

        }
    }
}
