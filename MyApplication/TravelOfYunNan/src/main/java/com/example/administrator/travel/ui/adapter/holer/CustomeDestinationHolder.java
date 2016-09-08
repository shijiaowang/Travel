package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.ShowAllTextView;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class CustomeDestinationHolder extends BaseHolder {
    @ViewInject(R.id.iv_spot)
    private ImageView mIvSpot;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_show)
    private ShowAllTextView mTvShow;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;
    @ViewInject(R.id.tv_select)
    private TextView mTvSelect;
    @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;
    public CustomeDestinationHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        mTvShow.setContent("收到回复后视镜哈哈国家还干啥啊啊啊啊啊啊是水水水水是水水水水是奥会更好固话费购房和公安恢复欧恢复购房或购房或给【of好【复活个【 偶很反感欧哈否后过欧复合弓地方话袋盖一花覅过后if个几个");
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_custom_destination);
    }
}
