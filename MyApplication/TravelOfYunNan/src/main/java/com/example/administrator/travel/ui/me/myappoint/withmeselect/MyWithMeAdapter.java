package com.example.administrator.travel.ui.me.myappoint.withmeselect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentPopClick;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.example.administrator.travel.ui.me.myappoint.memberdetail.MemberDetailActivity;
import com.example.administrator.travel.ui.view.BadgeView;
import com.example.administrator.travel.utils.CalendarUtils;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import java.util.Map;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class MyWithMeAdapter extends BaseRecycleViewAdapter<MyWithMeSelectBean.DataBean> {

    public MyWithMeAdapter(List<MyWithMeSelectBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.activity_my_appoint_selector, parent, false);
        return new MyWithMeSelectHolder(inflate);
    }
    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, final int position, MyWithMeSelectBean.DataBean t) {
        MyWithMeSelectHolder myWithMeSelectHolder = (MyWithMeSelectHolder) holder;
        final MyWithMeSelectBean.DataBean datas = mDatas.get(position);
        FrescoUtils.displayNormal(myWithMeSelectHolder.mIvIcon, datas.getTravel_img());
        myWithMeSelectHolder.mTvPrice.setText(datas.getTotal_price());
        myWithMeSelectHolder.mTvHaveNumber.setText("已有: " + datas.getNow_people() + "人");
        myWithMeSelectHolder.mTvPlanNumber.setText("计划: " + datas.getMax_people() + "人");
        myWithMeSelectHolder.mTvStartAndLong.setText(datas.getMeet_address() + "出发  " + CalendarUtils.getHowDayHowNight(datas.getStart_time() + "000", datas.getEnd_time() + "000"));
        myWithMeSelectHolder.mTvDayAndNight.setText(FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getStart_time()) + "至" + FormatDateUtils.FormatLongTime(IVariable.YMD, datas.getEnd_time()));
        myWithMeSelectHolder.mTvLine.setText(datas.getRoutes());
        myWithMeSelectHolder.mBvEnterPeople.setBadgeCount(datas.getNow_people());
        myWithMeSelectHolder.mBvBulletinNumber.setBadgeCount(datas.getBulletin());
        myWithMeSelectHolder.mTvCode.setText(datas.getId_code());
        myWithMeSelectHolder.mTvAppointing.setText(getDesTextByState(datas.getState()));
        myWithMeSelectHolder.mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTarget(datas, MemberDetailActivity.class);
            }
        });
        myWithMeSelectHolder.mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTarget(datas,BulletinBoardActivity.class);
            }
        });
        final String status = datas.getStatus();
        String des="";
        int bg;
        if (status.equals("1")){
            des="已选择";
            myWithMeSelectHolder.mBtSelect.setClickable(false);
            bg=R.drawable.fragment_find_search_bg;
        }else if (status.equals("2")){
            des="已拒绝";
            myWithMeSelectHolder.mBtSelect.setClickable(false);
            bg=R.drawable.number_bg;
        }else {
            des="选择";
            myWithMeSelectHolder.mBtSelect.setClickable(true);
            bg=R.drawable.activity_my_appoint_chat_bg;
        }
        myWithMeSelectHolder.mBtSelect.setText(des);
        myWithMeSelectHolder.mBtSelect.setBackgroundResource(bg);
        myWithMeSelectHolder.mBtSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!datas.getIs_boss().equals("1")){
                    ToastUtils.showToast("您不是创建者，不能做出该操作。");
                    return;
                }
                if (!status.equals("3"))return;
                EnterAppointDialog.showCommonDialog(mContext, "选择约伴", "确定", "当您选择当前推送约伴的同时也就意味着您将拒绝其他团长推送来的约伴！", new ParentPopClick() {
                    @Override
                    public void onClick() {
                        selectAppoint(datas, position);
                    }
                });

            }
        });



    }

    private void startTarget(MyWithMeSelectBean.DataBean datas,Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtra(IVariable.DATA,datas.getId());
        mContext.startActivity(intent);
    }

    /**
     * 选择推送约伴
     * @param datas
     * @param position
     */
    private void selectAppoint(MyWithMeSelectBean.DataBean datas, int position) {
        Map<String, String> agresMap = MapUtils.Build().addKey(mContext).addUserId().addId(datas.getId()).end();
        MyWithMeSelectEvent event = new MyWithMeSelectEvent();
        event.setPosition(position);
        XEventUtils.postUseCommonBackJson(IVariable.AGREE_WITH_ME_SELECT,agresMap, BaseToolBarActivity.TYPE_UPDATE, event);
    }

    /**
     * 通过state获取描述字符串
     * @param state
     * @return
     */
    private String getDesTextByState(String state){
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
      class MyWithMeSelectHolder extends BaseRecycleViewHolder {
          @BindView(R.id.rl_bulletin_board)  RelativeLayout mRlBulletinBoard;
          @BindView(R.id.rl_member_detail)  RelativeLayout mRlMemberDetail;
          @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
          @BindView(R.id.tv_price) TextView mTvPrice;
          //common start
          @BindView(R.id.tv_have_number) TextView mTvHaveNumber;
          @BindView(R.id.tv_plan_number) TextView mTvPlanNumber;
          @BindView(R.id.tv_start_and_long)TextView mTvStartAndLong;
          @BindView(R.id.tv_day_and_night) TextView mTvDayAndNight;
          @BindView(R.id.tv_line) TextView mTvLine;
          //Common end
          @BindView(R.id.code) TextView mTvCode;
          @BindView(R.id.bv_enter_people)BadgeView mBvEnterPeople;
          @BindView(R.id.bv_bulletin_number)BadgeView mBvBulletinNumber;
          @BindView(R.id.tv_appointing)TextView mTvAppointing;
          @BindView(R.id.bt_select)Button mBtSelect;
        public MyWithMeSelectHolder(View itemView) {
            super(itemView);
        }
    }
}
