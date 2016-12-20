package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ChoicePropBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/31 0031.
 *
 * 道具选择
 */
public class ChoicePropsRightHolder extends BaseHolder<ChoicePropBean.DataBean.ProplistsBean> {
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_select_number) TextView mTvSelectNumber;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.iv_picture) SimpleDraweeView mIvPicture;
    @BindView(R.id.bt_reduce) Button mBtReduce;
    @BindView(R.id.bt_add)  Button mBtAdd;

    public ChoicePropsRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ChoicePropBean.DataBean.ProplistsBean datas, Context mContext, int position) {
        FrescoUtils.displayNormal(mIvPicture,datas.getLogo_img());
        mTvName.setText(datas.getName());
        mTvNumber.setText("库存："+datas.getNumber());
        if (GlobalValue.mPropSelects!=null && GlobalValue.mPropSelects.containsKey(datas.getId())){//如果已经选中过
            int number = GlobalValue.mPropSelects.get(datas.getId()).getNumber();
            try{
                int total = Integer.parseInt(datas.getNumber());
                if (number>total){
                    //如果刷新之前有人取走了，将改变大小
                    ToastUtils.showToast("对不起，您所选择的"+datas.getName()+"道具由于库存不足无法提供足够数量！");
                    number=total;
                }
                if (total==0){//如果没有库存则直接移除
                    GlobalValue.mPropSelects.remove(datas.getId());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            mTvSelectNumber.setText(String.valueOf(number));
            try {
                int saveNumber = Integer.parseInt(datas.getNumber());
                int current = saveNumber - number;
                current=current<0?0:current;//预防多次请求后有道具已经被租用出去的情况
                mTvNumber.setText("库存："+current);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }else {
            mTvSelectNumber.setText("0");
        }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_choice_equ_right);
    }
}
