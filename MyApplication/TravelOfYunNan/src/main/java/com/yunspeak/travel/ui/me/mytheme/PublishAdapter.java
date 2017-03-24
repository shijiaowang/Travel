package com.yunspeak.travel.ui.me.mytheme;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.View;

import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.adapter.CommonRecycleViewAdapter;
import com.yunspeak.travel.ui.me.mytheme.model.PublishModel;
import com.yunspeak.travel.BR;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2017/3/24.
 */

public class PublishAdapter extends CommonRecycleViewAdapter<PublishModel> {
    public PublishAdapter(List<PublishModel> datas, int brId, int layoutId) {
        super(datas, brId, layoutId);
    }

    @Override
    protected void onBind(ViewDataBinding binding) {
        binding.setVariable(BR.publishAdapter,this);
    }
    public void onDeleteClick(View view,PublishModel publishModel){
        final int indexOf = datas.indexOf(publishModel);
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addRId(publishModel.getR_id()+"").addType(publishModel.getType()).end();
        HttpClient.getInstance().postData(IRequestUrl.DELETE_PUBLISH, end, new INetworkCallBack<TravelsObject>() {
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
