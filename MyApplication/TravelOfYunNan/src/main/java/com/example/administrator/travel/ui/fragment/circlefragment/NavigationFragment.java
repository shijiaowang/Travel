package com.example.administrator.travel.ui.fragment.circlefragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavLeft;
import com.example.administrator.travel.ui.activity.CircleActivity;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.adapter.CircleNavRightAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class NavigationFragment extends BaseFragment {

    private ListView mLvLeftNav;
    private ListView mLvRightNav;
    private RelativeLayout mRlPost;
    private List<CircleNavLeft> lefts;
    private CircleNavLeftAdapter circleNavLeftAdapter;
    private int preCircleNavLeftPosition=-1;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle_navigation;
    }

    @Override
    protected void initView() {
        mLvLeftNav = (ListView) super.root.findViewById(R.id.lv_left_nav);
        mLvRightNav = (ListView) super.root.findViewById(R.id.lv_right_nav);


    }

    @Override
    protected void initData() {
        lefts = new ArrayList<>();
        for (int i=0;i<20;i++){
            CircleNavLeft circleNavLeft=new CircleNavLeft();
            circleNavLeft.setAdd("北京"+i);
            lefts.add(circleNavLeft);
        }
        circleNavLeftAdapter = new CircleNavLeftAdapter(getActivity(), lefts);
        mLvLeftNav.setAdapter(circleNavLeftAdapter);
        mLvRightNav.setAdapter(new CircleNavRightAdapter(getActivity(), null));

    }

    @Override
    protected void initListener() {
        mLvLeftNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2016/7/11 0011 根据position获取集合中的数据，重新加载右边集合
                selectNavLeft(position);

            }
        });
        mLvRightNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                Intent intent = new Intent(getActivity(), CircleActivity.class);
                ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
            }
        });
    }

    /**
     * 更改左边导航
     * @param position
     */
    private void selectNavLeft(int position) {
        if (position==preCircleNavLeftPosition){
            return;
        }
        if (preCircleNavLeftPosition!=-1){
            lefts.get(preCircleNavLeftPosition).setIsChecked(false);
        }
        lefts.get(position).setIsChecked(true);
        circleNavLeftAdapter.notifyDataSetChanged();
        preCircleNavLeftPosition=position;
    }


}
