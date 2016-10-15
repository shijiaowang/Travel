package com.example.administrator.travel.ui.circle.circlenav.circledetail.post;

import android.content.Context;
import android.content.Intent;
import com.example.administrator.travel.R;
import com.example.administrator.travel.event.PostEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.MapUtils;
import java.util.List;


/**
 * 帖子
 */

public class PostActivity extends BaseRecycleViewActivity<PostEvent,PostDetailBean,Object> {

    private String forum_id;
  private boolean isFirst=true;

    @Override
    protected void initEvent() {
        super.initEvent();
        forum_id = getIntent().getStringExtra(IVariable.FORUM_ID);
        mVsFooter.setLayoutResource(R.layout.activity_post);
        mVsFooter.inflate();
    }
    public static void start(Context context,String forum_id){
        Intent intent=new Intent(context,PostActivity.class);
        intent.putExtra(IVariable.FORUM_ID,forum_id);
        context.startActivity(intent);
    }


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        super.childAdd(builder,type);
        builder.addFroumId(forum_id);
    }

    @Override
    protected String initUrl() {
        return IVariable.POST_DETAIL;
    }

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<Object> mDatas) {
        return new PostAdapter(mDatas,this);
    }


    @Override
    protected String initTitle() {
        return "帖子详情";
    }

    @Override
    protected boolean isChangeData() {
        return true;
    }

    /**
     * 孩子改变读取数据的顺序
     * @param loadDatas
     * @param parentBean
     * @param t
     * @return
     */
    @Override
    protected void childChangeData(List<Object> loadDatas, PostDetailBean parentBean, PostEvent t) {
        if (t.getType()==TYPE_REFRESH){
            loadDatas.add(parentBean.getData().getForum());
            loadDatas.addAll(parentBean.getData().getForum_reply());
        }else {
            loadDatas.addAll(parentBean.getData().getForum_reply());
        }
    }
}
