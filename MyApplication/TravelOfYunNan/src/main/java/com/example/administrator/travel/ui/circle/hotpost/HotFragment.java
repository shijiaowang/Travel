package com.example.administrator.travel.ui.circle.hotpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.circle.post.PostActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class HotFragment extends LoadBaseFragment<HotEvent> {
     private XListView mLvCircleHot;
    private CircleHotAdapter circleHotAdapter;
    private int count=0;
    private List<HotPostBean.DataBean> hotPostBeanData;


    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public Class<? extends HttpEvent> registerEventType() {
        return HotEvent.class;
    }

    @Override
    public void onSuccess(HotEvent event) {
                loadEnd(mLvCircleHot);
                dealData(event);
    }

    @Override
    protected void onFail(HotEvent event) {
        super.onFail(event);
        loadEnd(mLvCircleHot);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLvCircleHot = (XListView) inflate;
    }

    @Override
    protected int initResLayout() {
        return R.layout.fragment_circle_hot;
    }





    @Override
    protected void initListener() {
         initXListView(mLvCircleHot,true,true);
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
        count=type==TYPE_REFRESH?0:getListSize(hotPostBeanData);
        Map<String, String> hotMap = MapUtils.Build().addKey(getContext()).addUserId().addPageSize(10).addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.HOT_POST,hotMap,type,new HotEvent());
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
            if (event.getType() == TYPE_LOAD) {
                this.hotPostBeanData.addAll(hotPostBeanData);
                circleHotAdapter.notifyDataSetChanged();
            }else if (event.getType()==TYPE_REFRESH){
                this.hotPostBeanData = hotPostBeanData;
                circleHotAdapter.notifyDataSetChanged();
            }
        }
    }


}
