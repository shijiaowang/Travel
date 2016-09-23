package com.example.administrator.travel.ui.circle.circlenav;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleNavRight;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.event.NavLeftEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.CircleDetailActivity;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.adapter.CircleNavLeftAdapter;
import com.example.administrator.travel.ui.adapter.CircleNavRightAdapter;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.utils.CircleUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.UIUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class NavLeftFragment extends LoadBaseFragment {
    @BindView(R.id.lv_left_nav)  ListView mLvLeftNav;
    @BindView(R.id.lv_right_nav) ListView mLvRightNav;
    private RelativeLayout mRlPost;
    private int preCircleNavLeftPosition = -1;
    private List<Circle.DataBean.CircleLeftBean> leftList;
    private List<CircleNavRight.RightCircle> rightList;

    private CircleNavLeftAdapter circleNavLeftAdapter;
    private CircleNavRightAdapter circleNavRightAdapter;
    private String cid;
    private View root;
    private int prePosition = -1;
    private Callback.Cancelable useCommonBackJson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = View.inflate(getContext(), R.layout.fragment_circle_navigation, null);
        ButterKnife.bind(this,root);
    }


    @Override
    protected Fragment registerEvent() {
        return this;
    }

    private void firstReq() {
        if (GlobalUtils.getUserInfo() == null) {
            //// TODO: 2016/8/19 0019 让用户去重新登录
            return;
        }
        Map<String, String> map = MapUtils.Build().addKey(getContext()).add("user_id", GlobalUtils.getUserInfo().getId()).end();
        useCommonBackJson = XEventUtils.getUseCommonBackJson(IVariable.FIRST_CIRCLE_URL, map, IVariable.FIRST_REQ,new NavLeftEvent());
    }



    @Override
    protected void initListener() {
        mLvLeftNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.e("正在点击导航" + position);
                // TODO: 2016/8/23 0023 需要做取消请求
                if (prePosition == position) {//重复点击或者加载中不让继续
                    return;
                }
                if (useCommonBackJson != null) {
                    useCommonBackJson.cancel();
                    useCommonBackJson = null;
                    LogUtils.e("取消啦");//如果点击过快就取消之前的
                }
                prePosition = position;
                if (leftList != null) {
                    cid = leftList.get(position).getCid();
                    selectNavLeft(position);
                    onLoad();
                }
            }
        });
        mLvRightNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
                Intent intent = new Intent(getActivity(), CircleDetailActivity.class);
                intent.putExtra(IVariable.C_ID, rightList.get(position).getCid());
                intent.putExtra(IVariable.C_NAME, rightList.get(position).getCname());
                ActivityCompat.startActivity(getActivity(), intent, compat.toBundle());
            }
        });
    }

    private void normalReq(String cid) {
        MapUtils.Builder builder = MapUtils.Build().addKey(getContext()).add("cid", cid);
        if (cid != null && cid.equals("1")) {//再次获取关注
            builder.add("user_id", GlobalUtils.getUserInfo().getId());
        }
        Map<String, String> map = builder.end();
        useCommonBackJson = XEventUtils.getUseCommonBackJson(IVariable.NORMAL_CIRCLE_URL, map, IVariable.NORMAL_REQ,new NavLeftEvent());
    }

    private boolean isFirst = true;

    @Override
    protected void onLoad() {
        LogUtils.e("圈子加载数据页面开始加载了");
        if (isFirst) {
            isFirst = false;
            firstReq();
            LogUtils.e("发送了第一次请求");
        } else {
            normalReq(cid);
        }
    }

    @Override
    protected View initView() {
        return root;
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

    @Subscribe
    public void onEvent(NavLeftEvent event) {
        LogUtils.e("圈子数据加载结束了");
        useCommonBackJson = null;
        if (event.isSuccess()) {
            if (event.getType() == IVariable.FIRST_REQ) {
                firstReq(event);//第一次请求
            } else {
                if (circleNavRightAdapter == null) {
                    firstReq();//第一次就没有进行加载，所以为空，在这里重新加载
                    return;
                }
                LogUtils.e(event.getResult());
                CircleNavRight circleNavRight = GsonUtils.getObject(event.getResult(), CircleNavRight.class);
                rightList = circleNavRight.getData();
                circleNavRightAdapter.notifyData(rightList);
            }
            setState(LoadingPage.ResultState.STATE_SUCCESS);
        } else {
            ToastUtils.showToast(event.getMessage());
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
}
