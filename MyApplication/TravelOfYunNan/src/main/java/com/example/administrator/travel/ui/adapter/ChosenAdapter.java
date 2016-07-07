package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Chosen;
import com.example.administrator.travel.ui.activity.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 主页精选
 */
public class ChosenAdapter extends BaseAdapter {
    private static final int MAX_CHILD = 4;//最多展示4个
    private Context mContext;
    private List<Chosen> mDatas;

    public ChosenAdapter(Context mContext, List<Chosen> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas==null){
            return MAX_CHILD;
        }
        if (mDatas.size() > MAX_CHILD) {
            return MAX_CHILD;
        }
        return mDatas.size();
    }

    @Override
    public Chosen getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChosenHolder chosenHolder;
        if (convertView==null){
            chosenHolder=new ChosenHolder();
            convertView=View.inflate(mContext,R.layout.item_fragment_home_chosen,null);
            chosenHolder.mIvChosenPicture= (ImageView) convertView.findViewById(R.id.iv_chosen_picture);
            chosenHolder.mTvChosenContent= (TextView) convertView.findViewById(R.id.tv_chosen_text);
            convertView.setTag(chosenHolder);
        }else {
            chosenHolder= (ChosenHolder) convertView.getTag();
        }
        return convertView;
    }
    static class ChosenHolder{
        TextView mTvChosenContent;
        ImageView mIvChosenPicture;
    }
}
