package com.example.administrator.travel.ui.me.myappoint;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.global.ParentPopClick;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.appoint.dialog.EnterAppointDialog;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.ui.me.bulltetinboard.BulletinBoardActivity;
import com.example.administrator.travel.ui.me.myappoint.chat.chatsetting.ChatSettingActivity;
import com.example.administrator.travel.ui.me.myappoint.memberdetail.MemberDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.myappoint.withmeselect.MyWithMeSelectActivity;
import com.example.administrator.travel.ui.me.ordercenter.orders.confirmorders.ConfirmOrdersActivity;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.XEventUtils;


import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class MyAppointAdapter extends TravelBaseAdapter<Object> {
    private String title;
    private String content;


    public MyAppointAdapter(Context mContext, List<Object> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    public int getViewTypeCount() {//需要加上加载更多和刷新
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(0) instanceof MyAppointTogetherBean.DataBean) {
            return TYPE_POST_NORMAL;
        } else {
            return TYPE_POST_USER;
        }
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final Object item, final int position) {
        if (item == null) {
            return;
        }
        if (baseHolder instanceof MyAppointingWithMeHolder) {
            final MyAppointWithMeBean.DataBean dataBean = (MyAppointWithMeBean.DataBean) item;
            MyAppointingWithMeHolder myAppointingWithMeHolder = (MyAppointingWithMeHolder) baseHolder;
            myAppointingWithMeHolder.mLlEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyWithMeSelectActivity.class);
                    intent.putExtra(IVariable.ID, dataBean.getId());
                    mContext.startActivity(intent);
                }
            });
        }
        //启动公告板页面
        if (baseHolder instanceof MyAppointTogetherHolder) {
            final MyAppointTogetherBean.DataBean item1 = (MyAppointTogetherBean.DataBean) item;
            final String id = item1.getId();
            final MyAppointTogetherHolder myAppointTogetherHolder = (MyAppointTogetherHolder) baseHolder;
            myAppointTogetherHolder.mRlBulletinBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BulletinBoardActivity.class);
                    intent.putExtra(IVariable.DATA, id);
                    mContext.startActivity(intent);
                }
            });
            myAppointTogetherHolder.mBtChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatSettingActivity.start(mContext,item1.getId());
                }
            });
            myAppointTogetherHolder.mBtStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String type = initType(myAppointTogetherHolder.payStates);
                    if (StringUtils.isEmpty(type))return;
                    if (myAppointTogetherHolder.payStates==9){
                          String content="这真是一次不错的旅行啊";
                        Map<String, String> discussMap = MapUtils.Build().addKey(mContext).addUserId().addtId(item1.getId()).addContent(content).end();
                        MyAppointEvent myAppointEvent=new MyAppointEvent();
                        myAppointEvent.setPosition(position);
                        XEventUtils.postUseCommonBackJson(IVariable.DISCUSS_APPOINT,discussMap,BaseToolBarActivity.TYPE_DISCUSS,myAppointEvent);
                    }else {
                        EnterAppointDialog.showCommonDialog(mContext, title, "确定", content, new ParentPopClick() {
                            @Override
                            public void onClick(int t) {
                                changeAppoint(item1, position, myAppointTogetherHolder,type);
                            }
                        });
                    }

                }
            });

            myAppointTogetherHolder.mBtPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String orderType = item1.getOrder_type();
                    if (StringUtils.isEmpty(orderType)) return;
                    Intent intent = new Intent(mContext, ConfirmOrdersActivity.class);
                    intent.putExtra(IVariable.TYPE, orderType);
                    intent.putExtra(IVariable.ID, item1.getOrder_id());
                    mContext.startActivity(intent);
                }
            });
            myAppointTogetherHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EnterAppointDialog.showCommonDialog(mContext, "删除约伴", "确认", "注意！此操作将删除您的约伴！请慎重选择！", new ParentPopClick() {
                        @Override
                        public void onClick(int t) {
                            Map<String, String> deleteMap = MapUtils.Build().addKey(mContext).addUserId().addtId(item1.getId()).end();
                            MyAppointEvent myAppointEvent = new MyAppointEvent();
                            myAppointEvent.setPosition(position);
                            XEventUtils.postUseCommonBackJson(IVariable.DELETE_APPOINT, deleteMap, BaseToolBarActivity.TYPE_DELETE, myAppointEvent);
                        }
                    });
                }
            });
            myAppointTogetherHolder.mRlMemberDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MemberDetailActivity.class);
                    intent.putExtra(IVariable.DATA, id);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void changeAppoint(MyAppointTogetherBean.DataBean item1, int position, MyAppointTogetherHolder myAppointTogetherHolder, String type) {
        Map<String, String> end = MapUtils.Build().addKey(mContext).addUserId().addtId(item1.getId()).addType(type).end();
        MyAppointEvent myAppointEvent=new MyAppointEvent();
        myAppointEvent.setPosition(position);
        myAppointEvent.setPayStatus(myAppointTogetherHolder.payStates);
        XEventUtils.postUseCommonBackJson(IVariable.CHANGE_APPOINT,end, BaseToolBarActivity.TYPE_CHANGE,myAppointEvent);
    }

    private String initType(int payStates) {
        switch (payStates) {
            case 3:
                title="确认出发";
                content="确认出发后将停止招募小伙伴。";
               return "1";
            case 7:
                title="即刻启程";
                content="开始您的约伴行程。";
                return "2";
            case 8:
                title="完成约伴";
                content="结束约伴行程。";
                return "3";
            case 9:
                return "9";
        }
        return "";
    }


    @Override
    protected BaseHolder initHolder(int position) {
        int itemViewType = getItemViewType(0);
        if (itemViewType == TYPE_POST_NORMAL) {
            return new MyAppointTogetherHolder(mContext);
        } else {
            return new MyAppointingWithMeHolder(mContext);
        }
    }
}
