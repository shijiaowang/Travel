package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.message.PushAgent;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyang on 2016/10/12 0012.
 */

public abstract class BaseChangeBarColorActivity<T extends HttpEvent> extends AppCompatActivity implements IState, SwipeRefreshLayout.OnRefreshListener {
    protected  boolean isFirstInflate =true;
    protected  SwipeRefreshLayout mSwipeContainer;
    protected  AppBarLayout mAppBarLayout;
    protected  CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected  TextView mTvTitle;
    protected  Toolbar mToolbar;
    protected  ImageView mIvPageError;
    protected  ProgressBar mPbLoading;
    protected  ViewStub mVsContent;
    protected  View childView;
    protected ViewStub mVsHeader;
    protected ViewStub mVsTab;
    private CoordinatorLayout mCoorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_toolbar_color);

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mIvPageError = (ImageView) findViewById(R.id.page_error);
        mCoorLayout = (CoordinatorLayout) findViewById(R.id.coor_layout);
        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        mVsTab = (ViewStub) findViewById(R.id.vs_tab);
        mTvTitle = (TextView) findViewById(R.id.tv_bar_name);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVsContent = (ViewStub) findViewById(R.id.vs_content);
        mVsHeader = (ViewStub) findViewById(R.id.vs_header);
        //设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        //设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.otherTitleBg));
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayShowCustomEnabled(true);
        }
        SystemBarHelper.setHeightAndPadding(this, mToolbar);
        SystemBarHelper.immersiveStatusBar(this);

        initHeader();
        mVsContent.setLayoutResource(initContent());
        childView = mVsContent.inflate();
        mTvTitle.setText(initTitle());
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                appBarStateChange(state);
                if (state==State.EXPANDED){
                    mSwipeContainer.setEnabled(true);
                    mTvTitle.setVisibility(View.GONE);
                }else{
                    if (mSwipeContainer.isRefreshing()){
                        mSwipeContainer.setRefreshing(false);
                    }
                    mTvTitle.setVisibility(View.VISIBLE);
                   mSwipeContainer.setEnabled(false);
                }
            }
        });
        mSwipeContainer.setOnRefreshListener(this);
        initListener();
        onLoad(TYPE_REFRESH);
        PushAgent.getInstance(this).onAppStart();

    }

    protected void appBarStateChange(AppBarStateChangeListener.State state) {

    }


    /**
     * 加载头布局
     */
    protected abstract void initHeader();

    /**
     * 初始化内容
     * @return
     */
    protected abstract int initContent();
    /**
     * 初始化标题
     */
    protected abstract String initTitle();

    /**
     * 监听
     */
    protected abstract void initListener();
    protected abstract String initUrl();
    @Override
    protected void onDestroy() {
        super.onDestroy();
       if (EventBus.getDefault().isRegistered(this)){
           EventBus.getDefault().unregister(this);
       }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                otherOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void otherOptionsItemSelected(MenuItem item) {

    }


    /**
     * 处理公共的网络参数请求
     *
     * @param type
     */
    protected void onLoad(int type) {
        MapUtils.Builder builder = MapUtils.Build().addKey(this).addUserId();
        childAdd(builder,type);
        Map<String, String> baseMap = builder.end();
        XEventUtils.getUseCommonBackJson(initUrl(),baseMap,type,getTInstance());
    }

    @Subscribe
    public void onEvent(T t){
        if (!t.getClass().getSimpleName().equals(getTInstance().getClass().getSimpleName())){
            return;
        }
        setIsProgress(false);
        mSwipeContainer.setRefreshing(false);
        if (t.isSuccess()){
            try {
                isFirstInflate =false;
                mCoorLayout.setVisibility(View.VISIBLE);//读取完毕，展示主页
                onSuccess(t);
            }catch (Exception e){
                e.printStackTrace();
                onFail(t);
            }
        }else {
            ToastUtils.showToast(t.getMessage());
            onFail(t);
        }

    }

    /**
     * 处理成功数据
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 错误处理
     * @param t
     */
    protected void onFail(T t) {
        if (isFirstInflate){
            setErrorPage(true);//如果初次加载显示错误页
        }
    }
    protected void childAdd(MapUtils.Builder builder, int type) {

    }
    /**
     * 重新读取网络
     */
    @OnClick(R.id.page_error)
    protected void onReLoad() {
        setIsProgress(true,true);
        onLoad(TYPE_REFRESH);
    }
    /**
     * 网络加载错误
     * @param isShow
     */
    protected void setErrorPage(boolean isShow) {
        mIvPageError.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mCoorLayout.setVisibility(View.GONE);//隐藏主布局
    }

    /**
     * 加载进度条
     *
     * @param isShow 显示还是隐藏
     */
    protected void setIsProgress(boolean isShow) {
        setIsProgress(isShow, false);
    }

    /**
     * 展示加载进度条
     *
     * @param isShow        显示还是隐藏
     * @param needHideOther 显示时是否需要隐藏其他布局
     */
    protected void setIsProgress(boolean isShow, boolean needHideOther) {
        if (needHideOther) {
            mIvPageError.setVisibility(View.GONE);
            mCoorLayout.setVisibility(View.GONE);
        }
        mPbLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    protected void  setSuccess(){
        mCoorLayout.setVisibility(View.VISIBLE);
        mIvPageError.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.GONE);
    }

    /**
     * 获取集合大小
     *
     * @param list
     * @return
     */
    protected int getListSize(List list) {
        if (list == null) return 0;
        return list.size();
    }

    /**
     * 设置按钮背景
     *
     * @param button
     * @param b
     */
    protected void btIsClick(Button button, boolean b) {
        if (b) {
            button.setBackgroundResource(R.drawable.fragment_find_search_bg);
            button.setClickable(b);
        } else {
            button.setBackgroundResource(R.drawable.button_bg_un_click);
            button.setClickable(b);
        }
    }

    protected String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 设置 错误信息
     *
     * @param request
     * @param errorMessage
     */
    protected void requestAndSetErrorMessage(EditText request, String errorMessage) {
        request.requestFocus();
        String message = "<font color=#5cd0c2>" + errorMessage + "</font>";
        request.setError(Html.fromHtml(message));
    }



    /**
     * 测量listview的高度
     *
     * @param mListView
     * @return
     */
    protected int measureHeight(ListView mListView) {
        // get ListView adapter
        ListAdapter adapter = mListView.getAdapter();
        if (null == adapter) {
            return 0;
        }

        int totalHeight = 0;

        for (int i = 0, len = adapter.getCount(); i < len; i++) {
            View item = adapter.getView(i, null, mListView);
            if (null == item) continue;
            // measure each item width and height
            item.measure(0, 0);
            // calculate all height
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = mListView.getLayoutParams();

        if (null == params) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        // calculate ListView height
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));

        mListView.setLayoutParams(params);

        return params.height;
    }
    /**
     * 隐藏软键盘
     * @param view
     */
    public void hideSoftWore(EditText view){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    /**
     * 判断集合是否为空
     * @param list
     * @return
     */
    public boolean ListIsEmpty(List list){
        if (list ==null || list.size()==0){
            return true;
        }
        return false;
    }






    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseChangeBarColorActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    public  void setFcouse(View view,boolean b){
        view.setClickable(b);
        view.setFocusableInTouchMode(b);
        view.setFocusable(b);
    }
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }
}
