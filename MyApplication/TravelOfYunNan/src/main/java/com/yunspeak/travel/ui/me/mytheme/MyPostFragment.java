package com.yunspeak.travel.ui.me.mytheme;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadAndPullBaseFragment;
import com.yunspeak.travel.utils.ToastUtils;
import java.util.List;
/**
 * Created by wangyang on 2016/8/3 0003.
 * 我的帖子
 */
public class MyPostFragment extends LoadAndPullBaseFragment<MyPostEvent,MyPostBean,Object> {

    @Override
    protected BaseRecycleViewAdapter<Object> initAdapter(List<Object> httpData) {
        return new ThemeCommonAdapter(httpData,getContext());
    }
    @Override
    protected void initListener() {
        super.initListener();
        changeMargin(3,6);
    }

    @Override
    public void onSuccess(MyPostEvent myPostEvent) {
        switch (myPostEvent.getType()){
            case TYPE_DELETE:
                ToastUtils.showToast("删除成功");
                mDatas.remove(myPostEvent.getPosition());
                mAdapter.notifyItemRemoved(myPostEvent.getPosition());
                break;
            default:
                super.onSuccess(myPostEvent);
                break;
        }

    }

    @Override
    protected String initUrl() {
        return IVariable.THEME_MY_POST;
    }
}
