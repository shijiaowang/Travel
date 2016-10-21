package com.example.administrator.travel.ui.fragment;

import android.content.DialogInterface;
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

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.ui.view.LoadingPage;
import com.example.administrator.travel.ui.view.refreshview.XListView;
import com.example.administrator.travel.ui.view.refreshview.XScrollView;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;


/**
 * Created by wangyang on 2016/8/3 0003.
 */
public abstract class LoadBaseFragment<T extends HttpEvent> extends Fragment implements XListView.IXListViewListener{
    public static final int TYPE_FIRST = 0;
    public static final int TYPE_REFRESH = 1;
    public static final int TYPE_LOAD = 2;
    public static final int TYPE_CLICK_ZAN = 3;
    public static final int TYPE_REFRESH_BY_USER = 4;

    public LoadingPage.ResultState currentState;
    private LoadingPage loadingPage;
    protected View inflate;
    private boolean isSuccessed=false;
    private boolean isVisible=false;
    private boolean isPrepared=false;
    private boolean isFirst=true;


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
            isFirst=false;
            onLoad(type);
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
            LogUtils.e(this.getClass().getSimpleName());
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
                    try {
                        isSuccessed = true;
                        setState(LoadingPage.ResultState.STATE_SUCCESS);
                        onSuccess(t);
                        LogUtils.e("baseFragment加载成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.e("出现异常了");
                        onFail(t);
                    }
                } else {
                    ToastUtils.showToast(t.getMessage());
                    onFail(t);
                }
            }


    public abstract void onSuccess(T t);

    protected  void onFail(T event){
        if (!isSuccessed){
            isFirst=true;
            setState(LoadingPage.ResultState.STATE_ERROR);
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
        MapUtils.Builder builder = MapUtils.Build().addKey(getContext()).addUserId();
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

    protected void loadEnd(XListView xListView) {
        if (xListView==null)return;
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());
    }

    protected void loadEnd(XScrollView xListView) {
        if (xListView==null)return;
        xListView.stopLoadMore();
        xListView.stopRefresh();
        xListView.setRefreshTime(getTime());

    }
    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);

    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }

    /**
     * 初始化XlistView
     * @param listView
     * @param canPull 是否能下拉刷新
     * @param canLoadMore 是否可以LoadMore
     */
    protected void initXListView(XListView listView, boolean canPull, boolean canLoadMore){
        if (listView==null)return;
        listView.setPullLoadEnable(canLoadMore);
        listView.setPullRefreshEnable(canPull);
        listView.setXListViewListener(this);
        listView.setRefreshTime(getTime());
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
        AlertDialog mAlertDialog = builder.show();
    }
    public void canSmoothInNetScroll(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager){
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
