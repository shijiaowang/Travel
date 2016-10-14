package com.example.administrator.travel.ui.me.othercenter.useralbum.albumdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseChangeColorRecycleActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * 查看他人相册详情  wangyang
 */

public class CatOtherUserAlbumActivity extends BaseChangeColorRecycleActivity<CatOtherUserEvent, CatOtherUserBean, CatOtherUserBean.DataBean, CatOtherUserBean.DataBean.BodyBean> {


    @BindView(R.id.iv_cover) SimpleDraweeView ivCover;
    @BindView(R.id.tv_user) TextView tvUser;
    @BindView(R.id.tv_title) TextView tvTitle;
    private String id;


    @Override
    protected void initHeader() {
        mVsHeader.setLayoutResource(R.layout.activity_cat_other_user_album_header);
        mVsHeader.inflate();
        id = getIntent().getStringExtra(IVariable.ID);
    }

    @Override
    protected String initTitle() {
        return "相册详情";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addId(id);
    }

    @Override
    protected String initUrl() {
        return IVariable.CAT_USER_ALBUM_DETAIL;
    }

    @Override
    protected void initChildListener() {

    }

    @Override
    protected void onSuccess(CatOtherUserEvent catOtherUserEvent) {
        super.onSuccess(catOtherUserEvent);
        switch (catOtherUserEvent.getType()){
            case TYPE_REFRESH:
                CatOtherUserBean catOtherUserBean = GsonUtils.getObject(catOtherUserEvent.getResult(), CatOtherUserBean.class);
                CatOtherUserBean.DataBean.HeadBean head = catOtherUserBean.getData().getHead();
                FrescoUtils.displayNormal(ivCover,head.getCover_img());
                tvUser.setText(head.getTitle());
                tvTitle.setText(head.getContent());
                break;
        }
    }

    @Override
    protected BaseRecycleViewAdapter<CatOtherUserBean.DataBean.BodyBean> initAdapter(List<CatOtherUserBean.DataBean.BodyBean> mDatas) {
        return new CatOtherAlbumAdapter(mDatas,this);
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, CatOtherUserAlbumActivity.class);
        intent.putExtra(IVariable.ID, id);
        context.startActivity(intent);
    }



}
