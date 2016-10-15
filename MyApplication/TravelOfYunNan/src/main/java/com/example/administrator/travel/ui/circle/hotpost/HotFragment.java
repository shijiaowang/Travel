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
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.baseui.LoadAndPullBaseFragment;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/7 0007.
 * 热帖
 */
public class HotFragment extends LoadAndPullBaseFragment<HotEvent,HotPostBean,HotPostBean.DataBean> {

    @Override
    protected TravelBaseAdapter initAdapter(List<HotPostBean.DataBean> httpData) {
        return new CircleHotAdapter(getContext(),httpData);
    }


    @Override
    protected void initListener() {
      super.initListener();
       mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PostActivity.class);
                intent.putExtra(IVariable.FORUM_ID,mDatas.get(position-1).getId());
                startActivity(intent);
            }
        });
    }


    @Override
    protected String initUrl() {
        return IVariable.HOT_POST;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }
}
