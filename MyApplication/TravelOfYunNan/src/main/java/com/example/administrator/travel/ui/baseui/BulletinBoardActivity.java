package com.example.administrator.travel.ui.baseui;

import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.BulletinBoardAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/2 0002.
 * 公告栏
 */
public class BulletinBoardActivity extends BarBaseActivity {
   @ViewInject(R.id.lv_bulletin_board)
    private ListView mLvBulletinBoard;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_bulletin_board;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {

        mLvBulletinBoard.setAdapter(new BulletinBoardAdapter(this, null));
    }

    @Override
    protected String setTitleName() {
        return "公告板";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
}
