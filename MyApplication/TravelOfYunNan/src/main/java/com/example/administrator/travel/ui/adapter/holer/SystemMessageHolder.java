package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SystemMessage;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.TypefaceUtis;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessageHolder extends BaseHolder<SystemMessage>{
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    private TextView mTvCursor;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_content)
    private TextView mTvContent;
    @ViewInject(R.id.tv_action)
    private TextView mTvAction;
    private TextView mTvIcon;

    public SystemMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SystemMessage datas, Context mContext) {

        mTvContent.setText(datas.getContent());
        mTvAction.setText(datas.getTitle());
        mTvTime.setText(datas.getTime());
        mTvCursor.setText(datas.getCursorContent());
        mTvIcon.setBackgroundResource(datas.getIconBg());
       if (!datas.isLast()){
          mTvIcon.setText(datas.getIconName());
       }
        if (datas.getNumber()<=0){
             mTvNumber.setVisibility(View.GONE);

        } else {
            mTvNumber.setVisibility(View.VISIBLE);
            mTvNumber.setText(datas.getNumber()+"");
        }
    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_fragment_message_center);
        mTvIcon=FontsIconUtil.findIconFontsById(R.id.tv_icon,mContext,inflate);
        mTvCursor=FontsIconUtil.findIconFontsById(R.id.tv_cursor,mContext,inflate);


        return inflate;
    }
}
