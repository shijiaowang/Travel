package com.yunspeak.travel.ui.circle.circlenav;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.circle.Circle;
import com.yunspeak.travel.event.NavLeftEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.fragment.LoadBaseFragment;
import com.yunspeak.travel.utils.CircleUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;

import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/7 0007.
 */
public class NavLeftFragment extends LoadBaseFragment<NavLeftEvent> {
    @BindView(R.id.lv_left_nav)  ListView mLvLeftNav;
    @BindView(R.id.lv_right_nav) ListView mLvRightNav;
    @BindView(R.id.rl_empty) RelativeLayout rlEmpty;
    @BindView(R.id.pb_load)
    ProgressBar pbLoading;
    private int preCircleNavLeftPosition = -1;
    private List<Circle.DataBean.CircleLeftBean> leftList;
    private List<CircleNavRight.RightCircle> rightList;

    private CircleNavLeftAdapter circleNavLeftAdapter;
    private CircleNavRightAdapter circleNavRightAdapter;
    private String cid;
    private int prePosition = -1;
    private Callback.Cancelable useCommonBackJson;


    @Override
    protected int initResLayout() {
        return R.layout.fragment_circle_navigation;
    }



    private void firstReq() {
        Map<String, String> map = MapUtils.Build().addKey().addUserId().end();
        useCommonBackJson = XEventUtils.getUseCommonBackJson(IVariable.FIRST_CIRCLE_URL, map, IVariable.FIRST_REQ,new NavLeftEvent());
    }

    @Override
    protected void initListener() {
        mLvLeftNav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (prePosition == position) {//重复点击或者加载中不让继续
                    return;
                }
                if (useCommonBackJson != null) {
                    useCommonBackJson.cancel();
                    useCommonBackJson = null;
                }
                prePosition = position;
                if (leftList != null) {
                    cid = leftList.get(position).getCid();
                    selectNavLeft(position);
                    onLoad(TYPE_REFRESH);
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
        MapUtils.Builder builder = MapUtils.Build().addKey().add("cid", cid);
        if (cid != null && cid.equals("1")) {//再次获取关注
            builder.add("user_id", GlobalUtils.getUserInfo().getId());
        }
        Map<String, String> map = builder.end();
        useCommonBackJson = XEventUtils.getUseCommonBackJson(IVariable.NORMAL_CIRCLE_URL, map, IVariable.NORMAL_REQ,new NavLeftEvent());
    }

    private boolean isFirst = true;

    @Override
    protected void onLoad(int type) {
        pbLoading.setVisibility(View.VISIBLE);
        if (isFirst) {
            isFirst = false;
            firstReq();
        } else {
            normalReq(cid);
        }
    }

    @Override
    protected String initUrl() {
        return null;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

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
    public void onSuccess(NavLeftEvent event) {
        pbLoading.setVisibility(View.GONE);
        useCommonBackJson = null;
            if (event.getType() == IVariable.FIRST_REQ) {
                firstReq(event);//第一次请求
            } else {
                CircleNavRight circleNavRight = GsonUtils.getObject(event.getResult(), CircleNavRight.class);
                rightList = circleNavRight.getData();
                if (rightList==null || rightList.size()==0) {
                    rlEmpty.setVisibility(View.VISIBLE);
                    if (circleNavRightAdapter!=null){
                        rightList.clear();
                        circleNavRightAdapter.notifyData(rightList);
                    }
                    return;
                }
                rlEmpty.setVisibility(View.GONE);
                if (circleNavRightAdapter==null){
                    mLvRightNav.setVisibility(View.VISIBLE);
                    circleNavRightAdapter = new CircleNavRightAdapter(getContext(), rightList);
                    mLvRightNav.setAdapter(circleNavRightAdapter);
                }else if (getListSize(rightList)==0){
                    rlEmpty.setVisibility(View.VISIBLE);
                    mLvRightNav.setVisibility(View.GONE);
                }else {
                    mLvRightNav.setVisibility(View.VISIBLE);
                    circleNavRightAdapter.notifyData(rightList);
                }
            }
    }
    @Subscribe
    public void onEvent(RefreshEvent refreshEvent){
        if (prePosition==0){
            firstReq();
        }
    }
    @Override
    protected void onFail(NavLeftEvent event) {
        super.onFail(event);
        if(event.getType()==IVariable.FIRST_REQ){
            isFirst=true;
        }
        pbLoading.setVisibility(View.GONE);
    }

    private void firstReq(NavLeftEvent event) {
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

            if (rightList.size()==0){
                rlEmpty.setVisibility(View.VISIBLE);
                mLvRightNav.setVisibility(View.GONE);
            }else {
                mLvRightNav.setVisibility(View.VISIBLE);
                circleNavRightAdapter = new CircleNavRightAdapter(getContext(), rightList);
                mLvRightNav.setAdapter(circleNavRightAdapter);
                rlEmpty.setVisibility(View.GONE);
            }
        }else {
            rlEmpty.setVisibility(View.VISIBLE);
            mLvRightNav.setVisibility(View.GONE);
        }

    }
}
