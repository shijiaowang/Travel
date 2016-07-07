package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Travels;

import java.util.List;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class TravelsAdapter extends BaseAdapter {
    private Context mContex;
    private List<Travels> mDatas;

    public TravelsAdapter(Context mContex, List<Travels> mDatas) {
        this.mContex = mContex;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        if (mDatas==null){
            return 5;
        }
        return mDatas.size();
    }

    @Override
    public Travels getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TravelsHolder travelsHolder;
        if (convertView==null){
            convertView=View.inflate(mContex, R.layout.itme_fragment_home_travels,null);
            travelsHolder=new TravelsHolder();
            travelsHolder.picture= ((ImageView) convertView.findViewById(R.id.iv_travels));
            convertView.setTag(travelsHolder);
        }else {
            travelsHolder= (TravelsHolder) convertView.getTag();
        }

        return convertView;
    }
    static class TravelsHolder{
        ImageView picture;
    }
}
