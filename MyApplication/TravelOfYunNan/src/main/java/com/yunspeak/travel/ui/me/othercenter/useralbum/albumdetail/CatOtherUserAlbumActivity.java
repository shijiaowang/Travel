package com.yunspeak.travel.ui.me.othercenter.useralbum.albumdetail;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CatOtherUserBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.AppBarStateChangeListener;
import com.yunspeak.travel.ui.baseui.BaseChangeColorRecycleActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;

import java.util.ArrayList;
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
    private List<String> list;


    @Override
    protected void initHeader() {
        mVsHeader.setLayoutResource(R.layout.activity_cat_other_user_album_header);
        mVsHeader.inflate();
        id = getIntent().getStringExtra(IVariable.ID);

    }

    @Override
    protected void appBarStateChange(AppBarStateChangeListener.State state) {
       if (state== AppBarStateChangeListener.State.COLLAPSED){
           mSwipe.setLoadMoreEnabled(true);

       }else {
           mSwipe.setLoadMoreEnabled(false);
       }
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
    protected void onChildItemClick(int position) {
        super.onChildItemClick(position);
        if (mDatas==null || mDatas.size()==0)return;
        CirclePreviewActivity.start(this,list,position);
    }

    @Override
    protected void onSuccess(CatOtherUserEvent catOtherUserEvent) {
        super.onSuccess(catOtherUserEvent);
        switch (catOtherUserEvent.getType()){
            case TYPE_REFRESH:
                CatOtherUserBean catOtherUserBean = GsonUtils.getObject(catOtherUserEvent.getResult(), CatOtherUserBean.class);
                CatOtherUserBean.DataBean.HeadBean head = catOtherUserBean.getData().getHead();
                FrescoUtils.displayNormal(ivCover,head.getBackground_img(),1000,500);
                tvUser.setText(head.getTitle());
                tvTitle.setText(head.getContent());
            case TYPE_LOAD:
                list = new ArrayList<>();
                for (CatOtherUserBean.DataBean.BodyBean bodyBean:mDatas){
                   list.add(bodyBean.getPath());
                }
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
