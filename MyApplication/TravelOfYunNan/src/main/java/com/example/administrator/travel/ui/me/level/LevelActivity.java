package com.example.administrator.travel.ui.me.level;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/17.
 * 等级
 */
public class LevelActivity extends BaseNetWorkActivity<LevelEvent> {

    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.tv_user_nick_name)
    TextView tvUserNickName;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.rv_level)
    RecyclerView rvLevel;
    @BindView(R.id.rv_express)
    RecyclerView rvExpress;



    @Override
    protected void initEvent() {

    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.USER_LEVEL;
    }

    @Override
    protected void onSuccess(LevelEvent levelEvent) {
        LevelUserBean levelBean = GsonUtils.getObject(levelEvent.getResult(), LevelUserBean.class);
        LevelUserBean.DataBean.UserBean user = levelBean.getData().getUser();
        initUser(user);
        List<LevelUserBean.DataBean.LevelBean> level = levelBean.getData().getLevel();
        rvLevel.setAdapter(new LevelAdapter(level,this));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvLevel.setLayoutManager(linearLayoutManager);
        List<LevelUserBean.DataBean.LevelDescBean> levelDesc = levelBean.getData().getLevel_desc();
        rvExpress.setAdapter(new ExpressAdapter(levelDesc,this));
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        rvExpress.setLayoutManager(linearLayoutManager1);
    }

    /**
     * 初始化用户信息
     *
     * @param user
     */
    private void initUser(LevelUserBean.DataBean.UserBean user) {
        FrescoUtils.displayIcon(ivIcon, user.getUser_img());
        tvProgress.setText(user.getLevel_score() + "/" + user.getLevel_max_score());
        pbProgress.setMax(Integer.parseInt(user.getLevel_max_score()));
        pbProgress.setProgress(Integer.parseInt(user.getLevel_score()));
        tvUserNickName.setText("用户名:" + user.getNick_name());
        tvLevel.setText("Lv." + user.getLevel() + ":");

    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_level;
    }

    @Override
    protected String initTitle() {
        return "等级";
    }



}
