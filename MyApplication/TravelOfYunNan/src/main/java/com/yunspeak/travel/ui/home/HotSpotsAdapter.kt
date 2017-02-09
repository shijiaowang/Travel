package com.yunspeak.travel.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yunspeak.travel.R
import com.yunspeak.travel.bean.HomeBean
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter

/**
 * Created by wangyang on 2016/7/6 0006.
 */
class HotSpotsAdapter(mDatas: List<HomeBean.DataBean.DestinationBean>, mContext: Context) : BaseRecycleViewAdapter<HomeBean.DataBean.DestinationBean>(mDatas, mContext) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSpotsHolder {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_home_hot_spots, parent, false)
        return HotSpotsHolder(inflate)
    }

}
