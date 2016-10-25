package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import android.app.Activity;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.baseui.BaseXListViewActivity;
import com.yunspeak.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.ui.view.refreshview.XListView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;



import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by wangyang on 2016/8/14.
 *收藏详情
 */
public class CollectionDetailActivity extends BaseXListViewActivity<CollectionDetailEvent,OtherBean,Object> {
    private String name;
    private int collectionType;


    private void setName(int collectionType) {
        name = "收藏详情";
        switch (collectionType){
            case MyCollectionActivity.COLLECTION_ACTIVE:
                name ="收藏的活动";
                break;
            case MyCollectionActivity.COLLECTION_TRAVELS:
                name ="收藏的游记";
                break;
            case MyCollectionActivity.COLLECTION_DESTINATION:
                name ="收藏的目的地";
                break;
            case MyCollectionActivity.COLLECTION_OTHER:
                name ="其他";
                break;
            case MyCollectionActivity.COLLECTION_TEAM:
                name ="收藏的队伍";
                break;
            case MyCollectionActivity.COLLECTION_POST:
                name ="收藏的帖子";
                break;
        }
       getmTvTitle().setText(name);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        collectionType = getIntent().getIntExtra(MyCollectionActivity.COLLECTION_TYPE, -1);
        setMarginTop(10);
        setChildSpace(5);
        init();
    }



    @Override
    protected String initUrl() {
        return IVariable.MY_COLLECTION_DETAIL;
    }

    private void init() {
        setName(collectionType);
    }






    @Override
    protected TravelBaseAdapter initAdapter(List<Object> httpData) {
        return new CollectionDetailAdapter(this, httpData, collectionType + "");
    }

    @Override
    protected void doOtherSuccessData(CollectionDetailEvent collectionDetailEvent) {
        mDatas.remove(collectionDetailEvent.getPosition());
        adapter.notifyDataSetChanged();
        ToastUtils.showToast(collectionDetailEvent.getMessage());
    }

    @Override
    protected boolean isUserChild() {
        return true;
    }

    @Override
    protected Class<? extends ParentBean> useChildedBean() {
        switch (collectionType) {
            case MyCollectionActivity.COLLECTION_ACTIVE:
                return ActiveBean.class;
            case MyCollectionActivity.COLLECTION_TRAVELS:
                return TravelsBean.class;
            case MyCollectionActivity.COLLECTION_DESTINATION:
                return DestinationBean.class;

            case MyCollectionActivity.COLLECTION_OTHER:
                return OtherBean.class;
            case MyCollectionActivity.COLLECTION_TEAM:
                return TeamBean.class;
            case MyCollectionActivity.COLLECTION_POST:
                return PostBean.class;
            default:
                return PostBean.class;
        }

    }

    @Override
    protected void childAdd(MapUtils.Builder builder) {
       builder.addTypeId(collectionType+"");
    }

    @Override
    protected String initTitle() {
        return "我的收藏";
    }
}