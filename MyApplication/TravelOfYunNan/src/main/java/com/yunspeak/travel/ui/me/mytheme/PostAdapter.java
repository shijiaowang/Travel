package com.yunspeak.travel.ui.me.mytheme;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.yunspeak.travel.BR;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.me.mytheme.model.PostModel;
import com.yunspeak.travel.ui.me.mytheme.model.PublishModel;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/24.
 */

public class PostAdapter extends CommonRecycleViewAdapter<PostModel> {
    public PostAdapter(List<PostModel> datas, int brId, int layoutId) {
        super(datas, brId, layoutId);
    }

    @Override
    protected void onBind(ViewDataBinding binding) {
        binding.setVariable(BR.postAdapter,this);
    }
    public void onDeleteClick(View linearLayout,PostModel postModel){
        if (datas==null || datas.size()==0)return;
        final int indexOf = datas.indexOf(postModel);
        Map<String, String> end = MapUtils.Build().addKey().addFroumId(postModel.getId()).addUserId().end();
        HttpClient.getInstance().postData(IRequestUrl.DELETE_POST, end, new INetworkCallBack<TravelsObject>() {
            @Override
            public void accept(@NonNull TravelsObject travelsObject) throws Exception {
                datas.remove(indexOf);
                notifyDataSetChanged();
            }
            @Override
            public void error(Throwable throwable) {
                ToastUtils.showToast(throwable.getMessage());
            }
        });

    }
}
