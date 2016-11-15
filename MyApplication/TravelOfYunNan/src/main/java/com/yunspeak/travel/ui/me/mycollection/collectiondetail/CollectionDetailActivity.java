package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;


import java.util.List;


/**
 * Created by wangyang on 2016/8/14.
 *收藏详情
 */
public class CollectionDetailActivity extends BaseRecycleViewActivity<CollectionDetailEvent,OtherBean,Object> {
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
         changeMargin(5,10);
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
    protected BaseRecycleViewAdapter<Object> initAdapter(List<Object> httpData) {
        return new CollectionDetailAdapter(this, httpData, collectionType + "");
    }

    @Override
    protected void doOtherSuccessData(CollectionDetailEvent collectionDetailEvent) {
        mAdapter.notifyItemRemoved(collectionDetailEvent.getPosition());
        mDatas.remove(collectionDetailEvent.getPosition());
        ToastUtils.showToast(collectionDetailEvent.getMessage());
    }

    @Override
    protected boolean isUseChild() {
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
    protected void onSuccess(CollectionDetailEvent collectionDetailEvent) {
        switch (collectionDetailEvent.getType()){
            case TYPE_DELETE:
                ToastUtils.showToast("删除成功");
                mDatas.remove(collectionDetailEvent.getPosition());
                mAdapter.notifyItemRemoved(collectionDetailEvent.getPosition());
                if (mDatas.size()==0){
                    setIsEmpty();
                }
                break;
            default:
                super.onSuccess(collectionDetailEvent);
                break;
        }

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder, type);
        builder.addTypeId(collectionType+"");
    }

    @Override
    protected String initTitle() {
        return name;
    }
}
