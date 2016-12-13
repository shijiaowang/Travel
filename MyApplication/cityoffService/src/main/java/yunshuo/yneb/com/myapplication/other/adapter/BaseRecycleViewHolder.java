package yunshuo.yneb.com.myapplication.other.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/15 0015.
 */
public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder{

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public abstract void childBindView(int position, T data, Context mContext);

}