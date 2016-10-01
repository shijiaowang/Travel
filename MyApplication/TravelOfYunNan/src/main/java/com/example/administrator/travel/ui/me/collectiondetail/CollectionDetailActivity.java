package com.example.administrator.travel.ui.me.collectiondetail;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentBean;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndRefreshBaseActivity;
import com.example.administrator.travel.ui.me.mycollection.MyCollectionActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;



import org.xutils.view.annotation.ViewInject;

import java.util.List;


/**
 * Created by wangyang on 2016/8/14.
 *收藏详情
 */
public class CollectionDetailActivity extends LoadAndRefreshBaseActivity<CollectionDetailEvent,OtherBean,Object> {
   @ViewInject(R.id.lv_collection_detail)
    private XListView mLvCollectionDetail;
    private String name;
    private int collectionType;
    private CollectionDetailAdapter collectionDetailAdapter;

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
    }
    @Override
    protected int setContentLayout() {
        return R.layout.activity_collection_deatil;
    }

    @Override
    protected void initEvent() {
        collectionType = getIntent().getIntExtra(MyCollectionActivity.COLLECTION_TYPE, -1);
        initXListView(mLvCollectionDetail,true,true);
        init();
    }

    @Override
    public XListView setXListView() {
        return mLvCollectionDetail;
    }


    @Override
    protected String initUrl() {
        return IVariable.MY_COLLECTION_DETAIL;
    }

    private void init() {
        setName(collectionType);
    }

    @Override
    protected Activity initViewData() {

        changeTitle(name);
        return this;
    }



    @Override
    protected String setTitleName() {
        return "我的收藏";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected TravelBaseAdapter initAdapter(List<Object> httpData) {
        collectionDetailAdapter = new CollectionDetailAdapter(this, httpData, collectionType + "");
        return collectionDetailAdapter;
    }

    @Override
    protected void doOtherSuccessData(CollectionDetailEvent collectionDetailEvent) {
        getHttpData().remove(collectionDetailEvent.getPosition());
        collectionDetailAdapter.notifyDataSetChanged();
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
}
