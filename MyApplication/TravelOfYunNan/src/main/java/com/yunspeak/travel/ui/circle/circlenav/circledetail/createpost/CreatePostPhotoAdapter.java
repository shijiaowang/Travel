package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;

import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import java.util.List;

/**
 * Created by wangyang on 2016/7/31.
 */
public class CreatePostPhotoAdapter extends TravelBaseAdapter<String> {
    private static  final  int TYPE_NORMAL =0;
    private static final  int TYPE_ADD =1;
    private  int count;

    public CreatePostPhotoAdapter(Context mContext, List<String> mDatas,int count) {
        super(mContext, mDatas);
        this.count = count;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        if (super.getCount()<count){
            return super.getCount()+1;
        }
        return super.getCount();
    }

    @Override
    public String getItem(int position) {
        if (super.getCount()==0 || (position==getCount()-1 && super.getCount()<count)){
            return null;
        }
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getCount()-1 && super.getCount()<count)
        {
            return TYPE_ADD;
        }
        return TYPE_NORMAL;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, String item, int position) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        if (getItemViewType(position)==TYPE_ADD){
            return new CreatePostPhotoLastHolder(mContext,count);
        }
        return new CreatePostPhotoHolder(mContext);
    }
}
