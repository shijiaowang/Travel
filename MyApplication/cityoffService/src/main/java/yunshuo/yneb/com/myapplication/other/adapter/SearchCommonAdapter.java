package yunshuo.yneb.com.myapplication.other.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.other.bean.SearchUserBean;

/**
 * Created by wangyang on 2016/8/22 0022.
 */
public class SearchCommonAdapter extends BaseRecycleViewAdapter<SearchUserBean.DataBean> {



    public SearchCommonAdapter(List<SearchUserBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public BaseRecycleViewHolder<SearchUserBean.DataBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchCommonHolder(inflateView(R.layout.item_activity_home_search_1,parent));
    }
}
