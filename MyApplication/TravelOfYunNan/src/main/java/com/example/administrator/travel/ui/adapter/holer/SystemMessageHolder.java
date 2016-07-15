package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.SystemMessage;
import com.example.administrator.travel.utils.TypefaceUtis;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class SystemMessageHolder extends BaseHolder<SystemMessage>{

    private TextView mTvNumber;
    private TextView mTvCursor;
    private TextView mTvTime;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvAction;
    private TextView mTvIcon;

    public SystemMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(SystemMessage datas, Context mContext) {

        mTvContent.setText(datas.getContent());
        mTvTitle.setText(datas.getTitle());
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
        View inflate = View.inflate(mContext, R.layout.item_fragment_message_center, null);
        mTvNumber = (TextView) inflate.findViewById(R.id.tv_number);
        mTvCursor = (TextView) inflate.findViewById(R.id.tv_cursor);
        mTvTime = (TextView) inflate.findViewById(R.id.tv_time);
        mTvTitle = (TextView) inflate.findViewById(R.id.tv_action);
        mTvContent = (TextView) inflate.findViewById(R.id.tv_content);
        mTvAction = (TextView) inflate.findViewById(R.id.tv_action);
        mTvIcon = (TextView) inflate.findViewById(R.id.tv_icon);

        mTvIcon.setTypeface(TypefaceUtis.getTypeface(mContext));
        mTvCursor.setTypeface(TypefaceUtis.getTypeface(mContext));

        return inflate;
    }
}
