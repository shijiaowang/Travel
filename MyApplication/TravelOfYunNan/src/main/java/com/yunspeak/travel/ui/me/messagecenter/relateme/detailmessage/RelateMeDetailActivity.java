package com.yunspeak.travel.ui.me.messagecenter.relateme.detailmessage;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.messagecenter.MeCommonEvent;
import com.yunspeak.travel.ui.me.messagecenter.appointmessage.AppointMessageAdapter;
import com.yunspeak.travel.ui.me.messagecenter.relateme.RelateMeActivity;
import java.util.List;

/**
 * Created by wangyang on 2016/8/26 0026.
 *  与我相关消息
 */
public class RelateMeDetailActivity extends BaseRecycleViewActivity<MeCommonEvent,CommonMessageBean,CommonMessageBean.DataBean> {
    private int type;
    private String url;
    private String title;

    @Override
    protected void initEvent() {
        super.initEvent();
        type = getIntent().getIntExtra(IVariable.TYPE, -1);
        title = "与我相关";
        switch (type){
            case RelateMeActivity.TYPE_AITE:
                title ="@我的";
                url=IVariable.AITE_ME_MESSAGE;
                break;
            case RelateMeActivity.TYPE_DISCUSS:
                title ="评论我的";
                url=IVariable.REPLY_ME_MESSAGE;
                break;
            case RelateMeActivity.TYPE_ZAMBIA:
                title ="赞我的";
                url=IVariable.ZAN_ME_MESSAGE;
                break;
        }
         getmTvTitle().setText(title);
    }




    @Override
    protected String initUrl() {
        return url;
    }

    @Override
    protected BaseRecycleViewAdapter<CommonMessageBean.DataBean> initAdapter(List<CommonMessageBean.DataBean> httpData) {
        return new AppointMessageAdapter(httpData,this);
    }




    @Override
    protected String initTitle() {
        return "@我";
    }
}
