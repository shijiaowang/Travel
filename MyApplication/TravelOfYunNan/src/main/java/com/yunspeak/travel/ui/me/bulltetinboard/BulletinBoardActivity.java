package com.yunspeak.travel.ui.me.bulltetinboard;


import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.utils.MapUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 公告栏
 */
public class BulletinBoardActivity extends BaseRecycleViewActivity<BulletinBoardEvent,BulletinBoardBean,BulletinBoardBean.DataBean> {;


    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        String tId = getIntent().getStringExtra(IVariable.DATA);
         builder.addtId(tId);
    }
    @Override
    protected String initUrl() {
        return IVariable.BULLETIN_BOARD;
    }



    @Override
    protected BaseRecycleViewAdapter initAdapter(List<BulletinBoardBean.DataBean> mDatas) {
        return new BulletinBoardAdapter(mDatas,this);
    }



    @Override
    protected String initTitle() {
        return "公告板";
    }
}
