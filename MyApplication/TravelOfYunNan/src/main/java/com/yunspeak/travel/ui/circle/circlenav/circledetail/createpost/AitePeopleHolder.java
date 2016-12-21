package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.AiteFollow;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/11/2 0002.
 */

public class AitePeopleHolder extends BaseRecycleViewHolder<AiteFollow> {
    @BindView(R.id.tv_aite)
    TextView tvAite;
    @BindView(R.id.tv_delete)
    FontsIconTextView tvDelete;
    public AitePeopleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(final int position, AiteFollow data, Context mContext) {
         tvAite.setText("@"+data.getNikeName());
         tvDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DeleteAiteEvent event = new DeleteAiteEvent(position);
                 EventBus.getDefault().post(event);
             }
         });
    }
}
