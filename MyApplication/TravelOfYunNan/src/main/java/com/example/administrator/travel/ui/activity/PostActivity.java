package com.example.administrator.travel.ui.activity;
import android.widget.ListView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.PostAdapter;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 帖子
 */

public class PostActivity extends BarBaseActivity {
    @ViewInject(R.id.lv_post_detail)
    private ListView mLvPostDetail;



    @Override
    protected int setContentLayout() {
        return R.layout.activity_post;
    }
    @Override
    protected void initEvent() {

    }
    @Override
    protected void initViewData() {
        List list=new ArrayList();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        mLvPostDetail.setAdapter(new PostAdapter(this, list));
    }

    @Override
    protected String setTitleName() {
        return "帖子详情";
    }


    @Override
    public float getAlpha() {
        return 1;
    }
}
