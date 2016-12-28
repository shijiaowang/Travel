package com.yunspeak.travel.ui.me.myappoint.withmeselect;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.MyWithMeSelectBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.yunspeak.travel.ui.me.myappoint.memberdetail.MemberDetailActivity;
import com.yunspeak.travel.utils.CalendarUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/15 0015.
 */
class MyWithMeHolder extends BaseRecycleViewHolder<MyWithMeSelectBean.DataBean> {
    @BindView(R.id.rl_bulletin_board)
    RelativeLayout mRlBulletinBoard;
    @BindView(R.id.rl_member_detail)  RelativeLayout mRlMemberDetail;
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    //common start
    @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
    @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
    @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
    @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
    @BindView(R.id.tv_line) TextView mTvLine;
    //Common end
    @BindView(R.id.code) TextView mTvCode;
    @BindView(R.id.bv_enter_people)View mBvEnterPeople;
    @BindView(R.id.bv_bulletin_number)View mBvBulletinNumber;
    @BindView(R.id.tv_appointing)TextView mTvAppointing;
    @BindView(R.id.bt_select)Button mBtSelect;
    public MyWithMeHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(final int position, final MyWithMeSelectBean.DataBean datas, final Context mContext) {

        FrescoUtils.displayNormal(mIvIcon, datas.getTravel_img());
        mTvPrice.setText(datas.getTotal_price());
        mTvHaveNumber.setText("已有: " + datas.getNow_people() + "人");
        mTvPlanNumber.setText("计划: " + datas.getMax_people() + "人");
        mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
        mTvDayAndNight.setText(FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getEnd_time()));
        mTvLine.setText(datas.getRoutes());
        mBvEnterPeople.setVisibility(stringIsEmpty(datas.getNow_people())?View.GONE:View.VISIBLE);
        mBvBulletinNumber.setVisibility(stringIsEmpty(datas.getBulletin())?View.GONE:View.VISIBLE);
        mTvCode.setText(datas.getId_code());
        mTvAppointing.setText(getDesTextByState(datas.getState(),mContext));
        mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBvEnterPeople.setVisibility(View.GONE);
                MemberDetailActivity.start(mContext,datas.getId(),"1");
            }
        });
       mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBvBulletinNumber.setVisibility(View.GONE);
                startTarget(datas,BulletinBoardActivity.class,mContext);
            }
        });
        final String status = datas.getStatus();
        String des;
        int bg;
        if (status.equals("1")){
            des="已选择";
           mBtSelect.setClickable(false);
            bg=R.drawable.fragment_find_search_bg;
        }else if (status.equals("2")){
            des="已拒绝";
            mBtSelect.setClickable(false);
            bg=R.drawable.number_bg;
        }else {
            des="选择";
            mBtSelect.setClickable(true);
            bg=R.drawable.activity_my_appoint_chat_bg;
        }
        mBtSelect.setText(des);
        mBtSelect.setBackgroundResource(bg);
        mBtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.getIs_boss().equals("1")){
                    ToastUtils.showToast("您不是创建者，不能做出该操作。");
                    return;
                }
                if (!status.equals("3"))return;
                EnterAppointDialog.showCommonDialog(mContext,"选择约伴", "确定", "当您选择当前推送约伴的同时也就意味着您将拒绝其他团长推送来的约伴！", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        selectAppoint(datas,position,mContext);
                    }
                });

            }
        });



    }

    private boolean stringIsEmpty(String text) {
        if (StringUtils.isEmpty(text) || text.equalsIgnoreCase("0"))return true;
        return false;
    }

    /**
     * 选择推送约伴
     * @param datas
     * @param position
     */
    private void selectAppoint(MyWithMeSelectBean.DataBean datas, int position,Context mContext) {
        Map<String, String> agresMap = MapUtils.Build().addKey().addUserId().addId(datas.getId()).end();
        MyWithMeSelectEvent event = new MyWithMeSelectEvent();
        event.setPosition(position);
        XEventUtils.postUseCommonBackJson(IVariable.AGREE_WITH_ME_SELECT,agresMap, BaseToolBarActivity.TYPE_UPDATE, event);
    }
    private void startTarget(MyWithMeSelectBean.DataBean datas,Class clazz,Context mContext) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra(IVariable.DATA,datas.getId());
        mContext.startActivity(intent);
    }
    /**
     * 通过state获取描述字符串
     * @param state
     * @return
     */
    private String getDesTextByState(String state,Context mContext){
        String desText="约伴订单";
        try {
            String[] stringArray = mContext.getResources().getStringArray(R.array.together_appoint);
            int i = Integer.parseInt(state)-1;
            desText=stringArray[i];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desText;
    }
}