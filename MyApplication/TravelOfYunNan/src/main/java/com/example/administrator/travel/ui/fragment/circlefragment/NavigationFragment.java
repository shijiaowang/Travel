package com.example.administrator.travel.ui.fragment.circlefragment;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.CircleActivity;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.adapter.CircleNavRightAdapter;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.utils.CircleUtils;
import com.example.administrator.travel.utils.KeyUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.example.administrator.travel.utils.Xutils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class NavigationFragment extends BaseFragment {

    private ListView mLvLeftNav;
    private ListView mLvRightNav;
    private RelativeLayout mRlPost;
    private int preCircleNavLeftPosition = -1;
    private RequestQueue requestQueue;
    private List<Circle.DataBean.CircleLeftBean> leftList;
    private List<CircleNavRight.RightCircle> rightList;

    private CircleNavLeftAdapter circleNavLeftAdapter;
    private CircleNavRightAdapter circleNavRightAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_circle_navigation;
    }

    @Override
    protected void initView() {
        mLvLeftNav = (ListView) super.root.findViewById(R.id.lv_left_nav);
        mLvRightNav = (ListView) super.root.findViewById(R.id.lv_right_nav);
        requestQueue = Volley.newRequestQueue(getContext());


    }

    @Override
    protected void initData() {
         String commonUrl = IVariable.FIRST_CIRCLE_URL + "key" + "/" + KeyUtils.getKey(getContext()) + "/user_id/1";
        Map<String, String> commonMap = Xutils.getCommonMap(getContext());
        XEventUtils.getStringRequest(commonUrl, requestQueue, IVariable.FIRST_REQ_CIRCLE);


    }

    @Override
    protected void initListener() {
        mLvLeftNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cid = leftList.get(position).getCid();
                selectNavLeft(position);

                if (position==0){
                    String commonUrl = IVariable.FIRST_CIRCLE_URL + "key" + "/" + KeyUtils.getKey(getContext()) + "/user_id/1";
                    XEventUtils.getStringRequest(commonUrl, requestQueue, IVariable.FIRST_REQ_CIRCLE);
                }else {
                    String commonUrl = IVariable.NORMAL_CIRCLE_URL + "key" + "/" + KeyUtils.getKey(getContext()) + "/cid/";
                    XEventUtils.getStringRequest(commonUrl + cid, requestQueue, IVariable.NORMAL_REQ_CIRCLE);
                }
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
     *
     * @param
     */
    private void selectNavLeft(int position) {
        if (position == preCircleNavLeftPosition) {
            return;
        }
        if (preCircleNavLeftPosition != -1) {
            leftList.get(preCircleNavLeftPosition).isCheck = false;
        }
        leftList.get(position).isCheck = true;
        circleNavLeftAdapter.notifyDataSetChanged();
        preCircleNavLeftPosition = position;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    public void onEvent(HttpEvent event) {
        if (event.isSuccess()) {
            if (event.getType() == IVariable.FIRST_REQ_CIRCLE) {
                firstReq(event);//第一次请求
            }else {
                if (event.getCode() == IVariable.SUCCESS) {
                    String result = event.getResult();
                    Gson gson = new Gson();
                    CircleNavRight circle = gson.fromJson(result, CircleNavRight.class);
                    circleNavRightAdapter.notifyData(circle.getData());
                } else {
                    ToastUtils.showToast(getContext(), event.getMessage());

                }
            }


        } else {
            ToastUtils.showToast(getContext(), "网络错误");
        }
    }

    private void firstReq(HttpEvent event) {
        if (event.getCode() == IVariable.SUCCESS) {
            String result = event.getResult();
            Gson gson = new Gson();
            Circle circle = gson.fromJson(result, Circle.class);
            leftList = circle.getData().getCircle_left();
            rightList = CircleUtils.circleRightBean2RightCircleList(circle.getData().getCircle_right());
            setListData();
        } else {
            ToastUtils.showToast(getContext(), event.getMessage()+"key"+KeyUtils.getKey(getContext()));
            LogUtils.e("key" + KeyUtils.getKey(getContext()));
        }
    }

    /**
     * 初始设置数据
     */
    private void setListData() {
        if (leftList != null) {
            leftList.get(0).isCheck = true;//初始化默认设置第一个
            preCircleNavLeftPosition = 0;
            circleNavLeftAdapter = new CircleNavLeftAdapter(getContext(), leftList);
            mLvLeftNav.setAdapter(circleNavLeftAdapter);
        }
        if (rightList != null) {
            circleNavRightAdapter = new CircleNavRightAdapter(getContext(), rightList);
            mLvRightNav.setAdapter(circleNavRightAdapter);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
