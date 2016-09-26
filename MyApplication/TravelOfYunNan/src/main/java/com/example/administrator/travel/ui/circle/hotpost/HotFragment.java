package com.example.administrator.travel.ui.circle.hotpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.PostActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class HotFragment extends LoadBaseFragment implements XListView.IXListViewListener {
     private XListView mLvCircleHot;
    private CircleHotAdapter circleHotAdapter;
    private int count=0;
    private List<HotPostBean.DataBean> hotPostBeanData;


    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLvCircleHot = (XListView) View.inflate(getContext(), R.layout.fragment_circle_hot, null);
    }

    @Override
    protected View initView() {
        return mLvCircleHot;
    }



    @Override
    protected void initListener() {
        mLvCircleHot.setPullLoadEnable(true);
        mLvCircleHot.setPullRefreshEnable(true);
        mLvCircleHot.setXListViewListener(this);
        mLvCircleHot.setRefreshTime(getTime());
       mLvCircleHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra(IVariable.FORUM_ID,hotPostBeanData.get(position-1).getId());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onLoad(int type) {
        count=type==LOAD_MORE?getListSize(hotPostBeanData):0;
        Map<String, String> hotMap = MapUtils.Build().addKey(getContext()).addUserId().addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.HOT_POST,hotMap,type,new HotEvent());
    }
    @Subscribe
   public void onEvent(HotEvent event){
        loadEnd(mLvCircleHot);
        if (event.isSuccess()){
            try {
                dealData(event);
                setState(LoadingPage.ResultState.STATE_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                setState(LoadingPage.ResultState.STATE_ERROR);
            }
        }else {
            ToastUtils.showToast(event.getMessage());
            setState(LoadingPage.ResultState.STATE_ERROR);
        }

   }

    private void dealData(HotEvent event) {
        HotPostBean hotPostBean = GsonUtils.getObject(event.getResult(), HotPostBean.class);
        List<HotPostBean.DataBean> hotPostBeanData = hotPostBean.getData();
        if (hotPostBeanData==null)return;
        if (this.hotPostBeanData ==null) {
            this.hotPostBeanData = hotPostBeanData;
            circleHotAdapter = new CircleHotAdapter(getContext(), this.hotPostBeanData);
            mLvCircleHot.setAdapter(circleHotAdapter);
        }else {
            if (event.getType() == LOAD_MORE) {
                this.hotPostBeanData.addAll(hotPostBeanData);
                circleHotAdapter.notifyDataSetChanged();
            }else if (event.getType()==REFRESH){
                this.hotPostBeanData = hotPostBeanData;
                circleHotAdapter.notifyDataSetChanged();
            }
        }
    }


}
