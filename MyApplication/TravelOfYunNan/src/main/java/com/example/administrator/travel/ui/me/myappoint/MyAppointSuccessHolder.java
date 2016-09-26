package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.MyAppoint;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 成功约伴
 */
public class MyAppointSuccessHolder extends BaseHolder<MyAppoint> {
   @BindView(R.id.rl_bulletin_board) public RelativeLayout mRlBulletinBoard;
    @BindView(R.id.rl_member_detail) public RelativeLayout mRlMemberDetail;

    public MyAppointSuccessHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(MyAppoint datas, Context mContext, int position) {

    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_my_appoint_success);
        return inflate;
    }
}
