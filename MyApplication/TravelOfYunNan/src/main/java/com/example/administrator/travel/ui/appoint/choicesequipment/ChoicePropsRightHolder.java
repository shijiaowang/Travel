package com.example.administrator.travel.ui.appoint.choicesequipment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.ToastUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class ChoicePropsRightHolder extends BaseHolder<ChoicePropBean.DataBean.ProplistsBean> {
    @ViewInject(R.id.tv_number)
    public TextView mTvNumber;
    @ViewInject(R.id.tv_select_number)
    public TextView mTvSelectNumber;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.iv_picture)
    private ImageView mIvPicture;
    @ViewInject(R.id.bt_reduce)
    public Button mBtReduce;
    @ViewInject(R.id.bt_add)
    public Button mBtAdd;

    public ChoicePropsRightHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(ChoicePropBean.DataBean.ProplistsBean datas, Context mContext, int position) {
        x.image().bind(mIvPicture,datas.getLogo_img(),getImageOptions(DensityUtil.dip2px(70),DensityUtil.dip2px(70)));
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
