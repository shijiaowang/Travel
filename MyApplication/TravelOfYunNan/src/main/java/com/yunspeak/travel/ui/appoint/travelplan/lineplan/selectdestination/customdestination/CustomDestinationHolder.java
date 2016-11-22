package com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.view.ShowAllTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/8 0008.
 */
public class CustomDestinationHolder extends BaseRecycleViewHolder<CustomDestinationBean.DataBean> {
    @BindView(R.id.iv_spot)
    SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_user)
    TextView mTvUser;
    @BindView(R.id.tv_show)
    ShowAllTextView mTvShow;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_select)
    TextView mTvSelect;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    private String deleteDataId = "";

    public CustomDestinationHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void childBindView(final int position, final CustomDestinationBean.DataBean datas, final Context mContext) {
        FrescoUtils.displayNormal(mIvSpot, datas.getLogo_img());
        mTvDelete.setVisibility(datas.getIs_del().equals("1") ? View.VISIBLE : View.GONE);
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getProvince() + datas.getCity() + datas.getAddress());
        mTvShow.setContent(datas.getContent());
        mTvUser.setText(datas.getUser_name());
        mTvSelect.setTextColor(GlobalValue.clickPosition == position ? mContext.getResources().getColor(R.color.Ffbf75) : mContext.getResources().getColor(R.color.colorb5b5b5));
        if (GlobalValue.mSelectSpot != null && GlobalValue.mSelectSpot.contains(datas.getId())) {
            itemView.setAlpha(0.3f);
            mTvSelect.setVisibility(View.GONE);
        } else {
            itemView.setAlpha(1.0f);
            mTvSelect.setVisibility(View.VISIBLE);
        }
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteDataId.equals(datas.getId())) {
                    return;
                }
                //记录删除的数据，避免重复删除
                deleteDataId = datas.getId();
                Map<String, String> deleteMap = MapUtils.Build().addKey().addUserId().addtId(deleteDataId).end();
                CustomDestinationEvent customDestinationEvent = new CustomDestinationEvent();
                customDestinationEvent.setDeletePosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.DELETE_CUSTOM_SPOT, deleteMap, IState.TYPE_DELETE, customDestinationEvent);

            }
        });
    }
}
