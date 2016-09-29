package com.example.administrator.travel.ui.me.collectiondetail;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.mycollection.MyCollectionActivity;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/14.
 *收藏详情
 */
public class CollectionDetailActivity extends LoadingBarBaseActivity<CollectionDetailEvent> {
   @ViewInject(R.id.lv_collection_detail)
    private XListView mLvCollectionDetail;
    private String name;
    private int collectionType;
    private int count;
    private List mDatas;
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
        initXListView(mLvCollectionDetail,false,true);
        init();
    }

    @Override
    protected void onLoad(int type) {
        count=type==TYPE_REFRESH?0:getListSize(mDatas);
        Map<String, String> detailMap = MapUtils.Build().addKey(this).addUserId().addTypeId(collectionType + "").addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.MY_COLLECTION_DETAIL,detailMap,collectionType,new CollectionDetailEvent());

    }

    private void init() {

        setName(collectionType);
    }

    @Override
    protected Activity initViewData() {

        changeTitle(name);
        return this;
    }

    private void dealData(CollectionDetailEvent event) {
        switch (event.getType()){
            case MyCollectionActivity.COLLECTION_ACTIVE:
                 dealActiveData(event);
                break;
            case MyCollectionActivity.COLLECTION_TRAVELS:
                dealTravelsData(event);
                break;
            case MyCollectionActivity.COLLECTION_DESTINATION:
               dealDestination(event);

                break;
            case MyCollectionActivity.COLLECTION_OTHER:
                 dealOtherData(event);
                break;
            case MyCollectionActivity.COLLECTION_TEAM:
                dealTeamData(event);
                break;
            case MyCollectionActivity.COLLECTION_POST:
                dealPostData(event);
                break;
            case MyCollectionActivity.COLLECTION_CANCEL:
                mDatas.remove(event.getPosition());
                collectionDetailAdapter.notifyDataSetChanged();
                ToastUtils.showToast(event.getMessage());
                break;
        }
        if (event.getType()!=MyCollectionActivity.COLLECTION_CANCEL) {
            collectionDetailAdapter = new CollectionDetailAdapter(this, mDatas, collectionType + "");
            mLvCollectionDetail.setAdapter(collectionDetailAdapter);
        }
    }

    private void dealOtherData(CollectionDetailEvent event) {
        OtherBean object = GsonUtils.getObject(event.getResult(), OtherBean.class);
        mDatas=object.getData();
    }

    private void dealPostData(CollectionDetailEvent event) {
        PostBean object = GsonUtils.getObject(event.getResult(), PostBean.class);
        mDatas=object.getData();
    }

    private void dealTravelsData(CollectionDetailEvent event) {
        TravelsBean object = GsonUtils.getObject(event.getResult(), TravelsBean.class);
        mDatas=object.getData();
    }

    /**
     * 活动数据
     * @param event
     */
    private void dealActiveData(CollectionDetailEvent event) {
        ActiveBean object = GsonUtils.getObject(event.getResult(), ActiveBean.class);
        mDatas=object.getData();
    }

    /**
     * 目的地
     * @param event
     */
    private void dealDestination(CollectionDetailEvent event) {
        DestinationBean destinationBean = GsonUtils.getObject(event.getResult(), DestinationBean.class);
        mDatas = destinationBean.getData();
    }

    /**
     * 队伍
     * @param event
     */
    private void dealTeamData(CollectionDetailEvent event) {
        TeamBean teamBean = GsonUtils.getObject(event.getResult(), TeamBean.class);
        mDatas = teamBean.getData();
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
    protected void onSuccess(CollectionDetailEvent event) {
        dealData(event);
    }
}
