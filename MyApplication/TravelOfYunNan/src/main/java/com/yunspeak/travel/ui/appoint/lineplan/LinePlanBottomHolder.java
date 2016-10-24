package com.yunspeak.travel.ui.appoint.lineplan;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.utils.JsonUtils;

import org.json.JSONObject;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/5 0005.
 */
public class LinePlanBottomHolder extends BaseHolder<LineBean> {
    @BindView(R.id.tv_end_add) TextView mTvEndAdd;


    public LinePlanBottomHolder(Context context) {
        super(context);

    }

    @Override
    protected void initItemDatas(LineBean datas, Context mContext, int position) {
        try{
            JSONObject basecJsonObject = JsonUtils.getBasecJsonObject();
            mTvEndAdd.setText(basecJsonObject.getString(IVariable.OVER_ADDRESS));
        }catch (Exception e){
            e.printStackTrace();
            mTvEndAdd.setText(mContext.getString(R.string.activity_my_album_add));
        }

    }


    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_line_plan_bottom);
    }
}
