package com.yunspeak.travel.ui.find.findcommon;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.widget.EaseChatInputMenu;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.FindLastReply;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentBean;
import com.yunspeak.travel.ui.baseui.BaseBarChangeColorActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CommonClickLikeBean;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DiscussCommonAdapter;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/11/1 0001.
 * 发现公共详情处理
 */

public abstract class BaseFindDetailActivity<T extends HttpEvent,E extends ParentBean> extends BaseBarChangeColorActivity<DetailCommonEvent,E,TravelReplyBean> {
    protected boolean isFirst=true;
    protected String haveNextPage="-1";
    public String tId;
    protected String tName;
    private EaseChatInputMenu inputMenu;
    private String pid="0";
    protected String isCollect;

    @Override
    protected BaseRecycleViewAdapter initAdapter(List<TravelReplyBean> mDatas) {
        return new DiscussCommonAdapter(mDatas,this,detailType());
    }

    /**
     * 那个孩子的详情
     * @return
     */
    protected abstract String detailType();

    @Override
    protected void onSuccess(DetailCommonEvent detailCommonEvent) {

        switch (detailCommonEvent.getType()){
            case TYPE_LIKE_DISCUSS:
                ToastUtils.showToast("点赞成功");
                dealClickData(detailCommonEvent);
                break;
            case TYPE_DISCUSS:
                ToastUtils.showToast("评论成功");
                dealReplyData(detailCommonEvent);
                break;
            case TYPE_CANCEL_COLLECTION:
                ToastUtils.showToast("取消收藏成功");
                isCollect=isFalse;
                item.setTitle("收藏");
                break;
            case TYPE_COLLECTION:
                ToastUtils.showToast("收藏成功");
                isCollect=isTrue;
                item.setTitle("已收藏");
                break;
            default:
                if (isFirst){
                    initHeader(detailCommonEvent);
                    isFirst=false;
                }
                super.onSuccess(detailCommonEvent);
                break;
        }

    }

    @Override
    protected void onFail(DetailCommonEvent detailCommonEvent) {
        switch (detailCommonEvent.getType()){
            case TYPE_LIKE_DISCUSS:
            case TYPE_DISCUSS:
            case TYPE_CANCEL_COLLECTION:
            case TYPE_COLLECTION:
                ToastUtils.showToast(detailCommonEvent.getMessage());

            default:
                super.onFail(detailCommonEvent);
                break;
        }
        }



    @Override
    protected boolean isChangeData() {
        return true;
    }

    private void dealClickData(DetailCommonEvent event) {
        CommonClickLikeBean commonClickLikeBean = GsonUtils.getObject(event.getResult(), CommonClickLikeBean.class);
        if (commonClickLikeBean == null) return;
        TravelReplyBean travelReplyBean = mDatas.get(event.getClickPosition());
        travelReplyBean.setIs_like("1");
        travelReplyBean.setLike_count(commonClickLikeBean.getData().getCount_like());
        mAdapter.notifyItemChanged(event.getClickPosition());
    }
    @Override
    protected boolean isUserChild() {
        return true;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        tId = getIntent().getStringExtra(IVariable.T_ID);
        tName = getIntent().getStringExtra(IVariable.NAME);
        vsFooter.setLayoutResource(R.layout.input);
        vsFooter.inflate();
        inputMenu = (EaseChatInputMenu)findViewById(R.id.input);
        inputMenu.init(null);
        inputMenu.setFindUse();
        inputMenu.setChatInputMenuListener(new EaseChatInputMenu.ChatInputMenuListener() {
            @Override
            public void onSendMessage(String content) {
                sendMessage(content,pid);
                pid="0";
                inputMenu.setHint("");

            }

            @Override
            public void onBigExpressionClicked(EaseEmojicon emojicon) {

            }

            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void sendMessage(String content,String pid) {
        Map<String, String> destinationMap = MapUtils.Build().addKey().addFId(tId).addUserId().
                addContent(content).addPId(pid).add(IVariable.TYPE,detailType()).
                addNextPage(haveNextPage).addCount(mDatas==null?0:mDatas.size()).
                end();
        XEventUtils.postUseCommonBackJson(IVariable.FIND_REPLY_DISCUSS, destinationMap, TYPE_DISCUSS, new DetailCommonEvent());
    }

    @Override
    public void onItemClick(int position) {
        String nickName = mDatas.get(position).getNick_name();
        inputMenu.setHint("回复:"+nickName);
        pid = mDatas.get(position).getId();
    }

    private void dealReplyData(DetailCommonEvent event) {
        if (haveNextPage.equals("0")) {
            try {
                FindLastReply findLastReply = GsonUtils.getObject(event.getResult(), FindLastReply.class);
                List<TravelReplyBean> data = findLastReply.getData();
                if (mDatas==null){
                    mDatas=data;
                }else {
                    mDatas.addAll(data);
                }
                mAdapter.notifiyData(mDatas);
            } catch (Exception e) {
                LogUtils.e("评论返回数据时出现异常");
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (StringUtils.isEmpty(isCollect))return;
        item.setTitle(isCollect.equals(isTrue) ? "已收藏" : "收藏");
        String url = isCollect.equals(isTrue) ? IVariable.CANCEL_COMMON_COLLECTION : IVariable.COLLECTION;
        int type=isCollect.equals(isTrue)?TYPE_CANCEL_COLLECTION:TYPE_COLLECTION;
        Map<String, String> collectionMap = MapUtils.Build().addKey().addUserId().addType(getType()).addId(tId).end();
        XEventUtils.postUseCommonBackJson(url, collectionMap, type, new DetailCommonEvent());
    }

    /**
     * 获取type
     * @return
     */
    private String getType() {
        if (detailType().equals(IVariable.TYPE_DESTINATION)){
            return "2";
        }else if (detailType().equals(IVariable.TYPE_TRAVELS)){
            return "4";
        }
        return "6";//美食，其他
    }

    /**
     * 初始化头部数据
     * @param detailCommonEvent
     */
    protected abstract void initHeader(DetailCommonEvent detailCommonEvent);

    @Override
    protected String initTitle() {
        return StringUtils.isEmpty(tName)?"详情":tName;
    }
}
