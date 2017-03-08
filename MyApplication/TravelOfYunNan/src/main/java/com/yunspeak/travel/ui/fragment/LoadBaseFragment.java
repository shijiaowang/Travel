package com.yunspeak.travel.ui.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HomePageDataBean;
import com.yunspeak.travel.db.HomeDataDao;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.ui.view.LoadingPage;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.DbManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import simpledao.cityoff.com.easydao.BaseDaoFactory;


/**
 * Created by wangyang on 2016/8/3 0003.
 */
public abstract class  LoadBaseFragment<T extends HttpEvent> extends Fragment implements IState{

    private  final String TAG=this.getClass().getSimpleName();
    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;
    protected View inflate;
    protected boolean isSuccessed=false;
    private boolean isVisible=false;
    private boolean isPrepared=false;
    protected boolean isFirst=true;
    private boolean isSaveData=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("当前Fragment为:"+getClass().getSimpleName());
        inflate = View.inflate(getContext(),initResLayout(), null);
        ButterKnife.bind(this, inflate);
        registerEventBus(this);
    }

    protected abstract int initResLayout();

    /**
     * 实例化 T
     *
     * @return
     */
    public T getTInstance() {

        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class c = (Class<T>) pt.getActualTypeArguments()[0];
            Constructor constructor = c.getConstructor();
            T e = (T) constructor.newInstance();
            return e;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());

    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(getContext()) {
            @Override
            public View onCreateSuccessView() {
                return  inflate;
            }

            @Override
            public void onLoad(int type) {
                LoadBaseFragment.this.isFirst=true;
                LoadBaseFragment.this.load(type);
            }
            /**
             * 子类中修改
             * @return
             */
            @Override
            public ResultState changeState() {

                return LoadBaseFragment.this.getCurrentState();
            }
        };
        isPrepared = true;
        initListener();
        loadData(TYPE_REFRESH);
        return loadingPage;

    }

    private void load(int type) {
        if (isVisible && isPrepared && isFirst) {
            onLoad(type);
            isFirst=false;
        }
    }

    /**
     * Fragment数据的懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            load(TYPE_REFRESH);
        }else {
            isVisible=false;
        }
    }



    @Subscribe
    public void onEvent(T t){
        if (!t.getClass().getSimpleName().equals(getTInstance().getClass().getSimpleName())){
            return;
        }
                if (t.isSuccess()) {
                    if (!isSuccessed && t.getCode()==2&& !isUseChildEmpty()){
                        setState(LoadingPage.ResultState.STATE_EMPTY);
                        return;
                    }
                    try {
                        onSuccess(t);
                        doSuccess(t);
                        saveData(t);
                        LogUtils.e("baseFragment加载成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        onFail(t);
                    }
                } else {
                    ToastUtils.showToast(t.getMessage());
                    onFail(t);
                }
            }

    /**
     * 第一次获取到成功数据缓存
     */
    private void saveData(T event) {
        if(!isSaveData){
            HomeDataDao userDao = BaseDaoFactory.getInstance().getUserDao(HomeDataDao.class, HomePageDataBean.class, true, UserUtils.getUserInfo().getId());
            HomePageDataBean homePageDataBean=new HomePageDataBean(TAG,event.getResult());
            HomePageDataBean queryPageDataBean=new HomePageDataBean(TAG,"");
            userDao.updateOrInsert(queryPageDataBean,homePageDataBean);
            isSaveData=true;
        }
    }

    protected void doSuccess(T t) {
        isSuccessed = true;
        setState(LoadingPage.ResultState.STATE_SUCCESS);
    }

    /**
     * 是否使用孩子的空页面
     * @return
     */
    protected boolean isUseChildEmpty() {
        return false;
    }


    public abstract void onSuccess(T t);

    protected  void onFail(T event){
        if (!isSuccessed){
            if (!NetworkUtils.isNetworkConnected()){//没有网络，加载数据库数据
               loadSqlData(event);
            }else {
                setState(LoadingPage.ResultState.STATE_ERROR);
            }
            isFirst=true;
        }
    }

    /**
     * 加载数据库数据
     * @param event 事件
     */
    protected void loadSqlData(T event) {
        event.setCode(1);
        event.setIsSuccess(true);
        HomeDataDao userDao = BaseDaoFactory.getInstance().getUserDao(HomeDataDao.class, HomePageDataBean.class, true, UserUtils.getUserInfo().getId());
        HomePageDataBean queryPageDataBean=new HomePageDataBean(TAG,"");
        HomePageDataBean query = userDao.query(queryPageDataBean);
        String data=query==null?"":query.getPageContent();
        if (!StringUtils.isEmpty(data)){
            event.setResult(data);
            setState(LoadingPage.ResultState.STATE_SUCCESS);
            onSuccess(event);//读取缓存数据
        }else {
            setState(LoadingPage.ResultState.STATE_ERROR);//继续设置错误页面
        }
    }


    protected int getListSize(List list){
       if (list==null)return 0;
       return list.size();
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterEventBus(this);


    }

    public void registerEventBus(Fragment f) {
        if (!EventBus.getDefault().isRegistered(f)) {
            EventBus.getDefault().register(f);
        }

    }

    public void unregisterEventBus(Fragment f) {
        if (EventBus.getDefault().isRegistered(f)) {
            EventBus.getDefault().unregister(f);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    public void loadData(int type) {
        if (loadingPage != null) {
            loadingPage.loadData(type);
        }
    }




    /**
     * 初始化监听
     */
    protected abstract void initListener();



    protected void onLoad(int type) {
        if (type==TYPE_REFRESH){
            isSuccessed=false;
            if (!isFirst) {
                setLoading();
            }
        }
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        childAdd(builder,type);
        Map<String, String> baseMap = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(),baseMap,type,getTInstance());
    }

    protected abstract String initUrl();

    protected abstract void childAdd(MapUtils.Builder builder, int type);

    /**
     * 设置读取状态
     *
     * @param state
     */
    public void setState(LoadingPage.ResultState state) {
        currentState = state;
        afterLoadData();
    }

    /**
     * 获取网络状态
     */
    public LoadingPage.ResultState getCurrentState() {
        return currentState;
    }

    /**
     * eventbus返回时调用
     */
    public void afterLoadData() {
        if (loadingPage != null) {
            loadingPage.afterLoadData();
        }
    }

    protected String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        }
    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }
    public void canSmoothInNetScroll(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager){
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
    protected void setLoading(){
        loadingPage.setLoading(true);
    }
}
