package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleDetail;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.CircleDetailAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/8 0008.
 * 圈子
 */
public class CircleDetailActivity extends LoadingBarBaseActivity implements View.OnClickListener {


    private TextView mTvCreatePost;//创建帖子按钮
    @ViewInject(R.id.lv_post)
    private ToShowAllListView mLvPost;//帖子列表
    @ViewInject(R.id.iv_post_bg)
    private ImageView mIvPostBg;//帖子背景
    @ViewInject(R.id.iv_post_icon)
    private ImageView mIvPostIcon;//帖子小头像
    @ViewInject(R.id.tv_post_number)
    private TextView mTvPostNumber;//帖子数
    @ViewInject(R.id.tv_follow_number)
    private TextView mTvFollowNumber;//关注数
    @ViewInject(R.id.tv_des)
    private TextView mTvDes;//描述
    @ViewInject(R.id.tv_circle_name)
    private TextView mTvCircleName;//圈子名称
    private int loadPage = 0;//网络加载的页数
    private String cId;
    private boolean isFirst=true;//只有第一次才设置圈子名字和图片
    private List<CircleDetail.DataBean.BodyBean> postList=new ArrayList<>();
    private CircleDetailAdapter circleDetailAdapter;


    @Override
    protected void initContentView() {
        cId = getIntent().getStringExtra(IVariable.C_ID);
        getmVsRightIcon().inflate();
        mTvCreatePost = FontsIconUtil.findIconFontsById(R.id.tv_ok, this);
        mTvCreatePost.setText(getResources().getString(R.string.activity_circle_create_post_font_icon));//设置创建帖子按钮

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_circle_detail;
    }

    @Override
    protected void initEvent() {
        mTvCreatePost.setOnClickListener(this);
        mLvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CircleDetailActivity.this, PostActivity.class));
            }
        });
    }

    @Override
    protected void onLoad() {
        setIsProgress(true);
        Map<String, String> stringMap = MapUtils.Build().addKey(this).add(IVariable.C_ID, cId).add(IVariable.PAGE_SIZE, "3").add(IVariable.PAGE, loadPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_CIRCLE_POST, stringMap, IVariable.FIRST_REQ);
    }

    @Override
    protected void initViewData() {

        String cName = getIntent().getStringExtra(IVariable.C_NAME);
        changeTitle(cName);

    }

    @Override
    protected String setTitleName() {
        return "圈子详情";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_ok:
                startActivity(new Intent(CircleDetailActivity.this, CreatePostActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }


    public void onEvent(HttpEvent event) {
        setIsProgress(false);
        if (event.isSuccess()) {
            dealData(event);

        } else {
            ToastUtils.showToast(event.getMessage());
            setIsError(true);
        }
    }

    private void dealData(HttpEvent event) {
        if (event.getResult()==null){
            return;
        }
        CircleDetail circle = GsonUtils.getObject(event.getResult(), CircleDetail.class);
        CircleDetail.DataBean.HeadBean head = circle.getData().getHead();
        if (isFirst){
         isFirst=false;
            x.image().bind(mIvPostBg,head.getCircle_img());
            x.image().bind(mIvPostIcon, head.getCircle_ico());
            mTvCircleName.setText(head.getCname());
            mTvDes.setText("简介："+head.getTitle());

        }
        mTvFollowNumber.setText(head.getCount_follow());
        mTvPostNumber.setText(head.getCount_forum());
        List<CircleDetail.DataBean.BodyBean> body = circle.getData().getBody();
        if (!postList.contains(body)) {
            postList.addAll(body);
            if (circleDetailAdapter==null) {
                circleDetailAdapter = new CircleDetailAdapter(this, postList);
                mLvPost.setAdapter(circleDetailAdapter);
            }else {
                circleDetailAdapter.notifyData(postList);
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }
}
