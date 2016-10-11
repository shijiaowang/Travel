package com.example.administrator.travel.ui.me.bulltetinboard;

import android.app.Activity;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewActivity;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.MapUtils;

import java.util.List;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 公告栏
 */
public class BulletinBoardActivity extends BaseRecycleViewActivity<BulletinBoardEvent,BulletinBoardBean,BulletinBoardBean.DataBean> {;
    private String tId;



    @Override
    protected Activity initDataAndRegisterEventBus() {
        return this;
    }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        tId = getIntent().getStringExtra(IVariable.DATA);
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
