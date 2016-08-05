package com.example.administrator.travel.ui.fragment.circlefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.CircleActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.adapter.CircleNavRightAdapter;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.utils.CircleUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UIUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class TestFragment extends LoadBaseFragment {

    private ListView mLvLeftNav;
    private ListView mLvRightNav;
    private RelativeLayout mRlPost;
    private int preCircleNavLeftPosition = -1;
    private List<Circle.DataBean.CircleLeftBean> leftList;
    private List<CircleNavRight.RightCircle> rightList;

    private CircleNavLeftAdapter circleNavLeftAdapter;
    private CircleNavRightAdapter circleNavRightAdapter;
    private String cid;
    private View root;
    private int prePosition=-1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = UIUtils.inflate(R.layout.fragment_circle_navigation);
    }

    private void firstReq() {
        Map<String, String> map = MapUtils.Build().addKey(getContext()).add("user_id", "1").end();
        XEventUtils.postUseCommonBackJson(IVariable.FIRST_CIRCLE_URL, map, IVariable.FIRST_REQ_CIRCLE);
    }

    @Override
    protected void initLoad() {
        loadData();
    }

    @Override
    protected void initContentView() {
        mLvLeftNav = (ListView) root.findViewById(R.id.lv_left_nav);
        mLvRightNav = (ListView) root.findViewById(R.id.lv_right_nav);
    }

    @Override
    protected void initListener() {
        mLvLeftNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prePosition==position){
                    return;
                }
                prePosition=position;
                cid = leftList.get(position).getCid();
                selectNavLeft(position);
                loadData();
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

    private void normalReq(String cid) {
        MapUtils.Builder builder = MapUtils.Build().addKey(getContext()).add("cid", cid);
        if (cid.equals("1")){//再次获取关注
            builder.add("user_id","1");
        }
        Map<String, String> map =builder.end();
        XEventUtils.postUseCommonBackJson(IVariable.NORMAL_CIRCLE_URL, map, IVariable.NORMAL_REQ_CIRCLE);
    }

    private boolean isFirst=true;
    @Override
    protected void onLoad() {
        if (isFirst) {
            isFirst=false;
            firstReq();
        }else {
            normalReq(cid);
        }
    }

    @Override
    protected View initView() {
        return  root;
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
            } else {
                CircleNavRight circleNavRight = GsonUtils.getObject(event.getResult(), CircleNavRight.class);
                circleNavRightAdapter.notifyData(circleNavRight.getData());
            }
            setState(LoadingPage.ResultState.STATE_SUCCESS);
        } else {
            ToastUtils.showToast(getContext(), event.getMessage());
            setState(LoadingPage.ResultState.STATE_ERROR);
        }

        //通知自定义view去显示正确读取后界面
        afterLoadData();
    }

    private void firstReq(HttpEvent event) {
        Circle circle = GsonUtils.getObject(event.getResult(), Circle.class);
        leftList = circle.getData().getCircle_left();
        rightList = CircleUtils.circleRightBean2RightCircleList(circle.getData().getCircle_right());
        setListData();
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
