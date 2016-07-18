package com.example.administrator.travel.ui.adapter;



import android.content.Context;

import com.example.administrator.travel.bean.EditAlbum;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.EditAlbumNormalHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
public class EditAlbumAdapter extends TravelBaseAdapter<EditAlbum>{
    private static final int NORMAL=1;
    private static final int LAST=2;
    public EditAlbumAdapter(Context mContext, List<EditAlbum> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas!=null && position==mDatas.size()-1){
            return LAST;
        }
        return NORMAL;
    }

    @Override
    protected int testDataSize() {
        return 12;
    }

    @Override
    protected void initListener(BaseHolder baseHolder, EditAlbum item) {

    }

    @Override
    protected BaseHolder initHolder(int position) {
        if (getItemViewType(position)==NORMAL){
            return new EditAlbumNormalHolder(mContext);
        }
        return new EditAlbumNormalHolder(mContext);
    }
}
