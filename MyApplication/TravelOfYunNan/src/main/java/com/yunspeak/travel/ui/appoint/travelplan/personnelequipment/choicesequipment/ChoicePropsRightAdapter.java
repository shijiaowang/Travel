package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.bean.ChoicePropBean;
import com.yunspeak.travel.bean.ChoicePropSelectBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wangyang on 2016/8/31 0031.
 */
public class ChoicePropsRightAdapter extends TravelBaseAdapter<ChoicePropBean.DataBean.ProplistsBean> {
    public ChoicePropsRightAdapter(Context mContext, List<ChoicePropBean.DataBean.ProplistsBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, final ChoicePropBean.DataBean.ProplistsBean item, int position) {
        final ChoicePropsRightHolder choicePropsRightHolder = (ChoicePropsRightHolder) baseHolder;
        choicePropsRightHolder.mBtReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListIsEmpty();
                if (GlobalValue.mPropSelects.containsKey(item.getId())){
                    ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(item.getId());
                    int number = choicePropSelectBean.getNumber() - 1;
                    getTextAndAdd(choicePropsRightHolder.mTvNumber, true);
                    number=number<0?0:number;
                    choicePropsRightHolder.mTvSelectNumber.setText(number + "");
                    if (number==0){
                        GlobalValue.mPropSelects.remove(item.getId());
                    }else {
                        choicePropSelectBean.setNumber(number);
                    }
                    sendEvent();
                }
            }
        });
        choicePropsRightHolder.mBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListIsEmpty();
                int number = 0;
                if (getSaveNumber(choicePropsRightHolder.mTvNumber) <= 0) {
                    ToastUtils.showToast("没有库存啦");
                    return;
                }
                if (GlobalValue.mPropSelects.containsKey(item.getId())) {
                    ChoicePropSelectBean choicePropSelectBean = GlobalValue.mPropSelects.get(item.getId());
                    number = choicePropSelectBean.getNumber() + 1;
                    choicePropSelectBean.setNumber(number);
                } else {
                    ChoicePropSelectBean choicePropSelectBean = new ChoicePropSelectBean();
                    choicePropSelectBean.setNumber(++number);
                    choicePropSelectBean.setId(item.getId());
                    choicePropSelectBean.setName(item.getName());
                    choicePropSelectBean.setContent(item.getContent());
                    GlobalValue.mPropSelects.put(item.getId(), choicePropSelectBean);
                }
                getTextAndAdd(choicePropsRightHolder.mTvNumber, false);
                choicePropsRightHolder.mTvSelectNumber.setText(String.valueOf(number));
                sendEvent();
            }
        });
    }

    /**
     * 通知更新页面
     */
    private void sendEvent() {
        ChoicePropEvent choicePropEvent=new ChoicePropEvent();
        choicePropEvent.setIsSelectEqu(true);
        choicePropEvent.setIsSuccess(true);
        EventBus.getDefault().post(choicePropEvent);
    }

    /**
     * 计算库存
     * @param mTvNumber
     * @param isAdd
     * @return
     */
    private void getTextAndAdd(TextView mTvNumber,boolean isAdd) {
        int number = getSaveNumber(mTvNumber);
        number = isAdd ? ++number : --number;
        mTvNumber.setText("库存："+number);
    }

    /**
     * 获取现有库存
     * @param mTvNumber
     * @return
     */
    private int getSaveNumber(TextView mTvNumber) {
        String textNumber = mTvNumber.getText().toString().trim();
        String substring = textNumber.substring(3, textNumber.length());
        return Integer.parseInt(substring);
    }

    private void checkListIsEmpty() {
        if (GlobalValue.mPropSelects==null){
            GlobalValue.mPropSelects=new HashMap<>();
        }
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new ChoicePropsRightHolder(mContext);
    }
}
