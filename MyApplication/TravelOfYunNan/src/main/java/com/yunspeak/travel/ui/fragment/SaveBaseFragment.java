package com.yunspeak.travel.ui.fragment;

import android.text.TextUtils;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomePageDataBean;
import com.yunspeak.travel.db.HomeDataDao;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.view.StatusView;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import simpledao.cityoff.com.easydao.BaseDaoFactory;

/**
 * Created by wangyang on 2017/3/9.
 * 需要缓存数据的页面集成此 fragment
 * 目前只有主页的几个页面需要把数据存储在数据库
 */

public abstract class SaveBaseFragment<T extends TravelsObject> extends BaseLoadingFragment{
    HomeDataDao daoHelper = BaseDaoFactory.getInstance().getUserDao(HomeDataDao.class, HomePageDataBean.class,true, GlobalUtils.getUserInfo().getId());
    private String TAG=this.getClass().getSimpleName();
    @Override
    protected void childLoad() {
        HttpClient.getInstance().getDataDealErrorAuto(statusView,getTInstance(), new Consumer<T>() {
            @Override
            public void accept(@NonNull T data) throws Exception {
                if (data!=null) {
                    HomePageDataBean query = daoHelper.query(new HomePageDataBean(TAG, ""));
                    HomePageDataBean content =new HomePageDataBean(TAG,GsonUtils.getJson(data));
                    daoHelper.updateOrInsert(query,content);
                    receiveData(data);
                }
            }
        },initParams(),initUrl());
    }

    protected abstract void receiveData(T data);

    protected abstract Map<String,String> initParams();

    protected abstract String initUrl();

    @Override
    protected void initOptions() {
        super.initOptions();
        statusView.setOnErrorBackListener(new StatusView.OnErrorBackListener() {
            @Override
            public boolean onErrorBack(Throwable throwable) {
                return onError(throwable);
            }
        });
    }

    protected  boolean onError(Throwable throwable) {
        ToastUtils.showToast(!NetworkUtils.isNetworkConnected()?getString(com.yunspeak.travel.R.string.network_isnot_available):throwable.getMessage());
        //查看数据库是否存有数据，有就显示缓存，没有就显示错误页面
        HomePageDataBean query = daoHelper.query(new HomePageDataBean(TAG, ""));
        if (query==null || TextUtils.isEmpty(query.getPageContent())){
            return false;//继续自动处理需要显示的页面
        }else {
            T t = GsonUtils.getObject(query.getPageContent(), getTInstance());
            if (t==null){
                return false;
            }
            receiveData(t);
            statusView.showSuccessView();
            return true;
        }
    }


    /**
     * 实例化 T
     *
     * @return T的 class类型
     */
    public Class<T> getTInstance() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class<T>) pt.getActualTypeArguments()[0];
    }
}
