package com.example.administrator.travel.ui.activity;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.PostDetail;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.Map;


/**
 * 帖子
 */

public class PostActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_post_detail)
    private ListView mLvPostDetail;
    @ViewInject(R.id.tv_icon)
    private ImageView mTvUserIcon;
    @ViewInject(R.id.tv_nick_name)
    private TextView mTvNickName;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_type)
    private TextView mTvType;
    @ViewInject(R.id.tv_content)
    private TextView mTvContent;
    @ViewInject(R.id.lv_post_image)
    private ToShowAllListView mLvPostImage;
    private String forum_id;
    private int currentPage = 0;
    private boolean isFirst=true;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_post;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onLoad() {
        if (forum_id == null) return;
        Map<String, String> postMap = MapUtils.Build().addKey(this).add(IVariable.FORUM_ID, "24").add(IVariable.USER_ID, GlobalUtils.getUserInfo().getId()).add(IVariable.PAGE_SIZE, "4").add(IVariable.PAGE, currentPage + "").end();
        XEventUtils.getUseCommonBackJson(IVariable.POST_DETAIL,postMap,0);
    }

    @Override
    protected void initViewData() {
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
    }

    @Override
    protected String setTitleName() {
        return "帖子详情";
    }


    @Override
    public float getAlpha() {
        return 1;
    }
    public void onEvent(HttpEvent event){
        if (event.isSuccess()){
            dealData(event);
        }else {
            ToastUtils.showToast(event.getMessage());
        }

    }

    private void dealData(HttpEvent event) {
        LogUtils.e(event.getResult());
        PostDetail object = GsonUtils.getObject(event.getResult(), PostDetail.class);
        if (isFirst){
            isFirst=false;
            PostDetail.DataBean.ForumBean forum = object.getData().getForum();
            mTvNickName.setText(forum.getNick_name());
            mTvTime.setText(FormatDateUtils.FormatLongTime("YYYY-MM-dd HH:mm",forum.getTime()));
            mTvContent.setText(forum.getContent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerEventBus(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterEventBus(this);
    }
}
