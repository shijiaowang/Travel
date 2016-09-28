package com.example.administrator.travel.ui.me.editalbum;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.EditAlbum;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class EditAlbumHolder extends BaseHolder<EditAlbumBean.DataBean.BodyBean> {
    public static boolean canDelete=false;
    private  int size=0;



    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_delete) TextView mTvDelete;
    public EditAlbumHolder(Context context, int size) {
        super(context);
        this.size = size;
    }
    public void setSize(int size){
        this.size=size;
    }

    @Override
    protected void initItemDatas(EditAlbumBean.DataBean.BodyBean datas, Context mContext, int position) {
        x.image().bind(mIvImage,datas.getPath());
        mTvNumber.setText((position+1)+"/"+size);
        mTvDelete.setVisibility(canDelete?View.VISIBLE:View.GONE);

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_edit_album);
    }

}
