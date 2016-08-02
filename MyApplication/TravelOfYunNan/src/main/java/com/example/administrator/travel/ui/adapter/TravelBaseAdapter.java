package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 * BaseAdapter封装
 */
abstract class TravelBaseAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected List<T> mDatas;
    protected int testSize=0;
    public static final int TYPE_POST_OP=0;//帖子开头
    public static final int TYPE_POST_NORMAL=1;//帖子文字
    public static final int TYPE_POST_IMG=2;//帖子附带图片

    public TravelBaseAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }


    @Override
    public int getCount() {
        if (mDatas==null){
            return testDataSize();
        }
        return mDatas.size();
    }
    public void notifyData(List<T> mDatas){
        if (mDatas==null){
            return;
        }
        this.mDatas=mDatas;
        notifyDataSetChanged();
    }

    protected abstract int testDataSize();//测试使用的数据大小

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder baseHolder;
        if (convertView==null || convertView.getTag()==null){
            baseHolder=initHolder(position);
            convertView=baseHolder.getRootView();
        }else {
            baseHolder= (BaseHolder) convertView.getTag();
        }
        if (mDatas!=null) {
            baseHolder.setDatas(getItem(position));
            initListener(baseHolder, getItem(position));
        }else {
            initListener(baseHolder, null);//暂时传，没有数据
        }

        return convertView;
    }

    protected abstract void initListener(BaseHolder baseHolder, T item);

    /**
     * 具体需要的holder
     * @return
     * @param position
     */
    protected abstract BaseHolder initHolder(int position);


}
