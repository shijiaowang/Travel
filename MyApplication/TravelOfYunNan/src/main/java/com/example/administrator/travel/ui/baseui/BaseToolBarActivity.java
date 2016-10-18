package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IState;
import com.example.administrator.travel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/10/7 0007.
 * Toolbar基类
 */

public abstract class BaseToolBarActivity extends AppCompatActivity implements IState {
    public static final int TRAFFIC_TYPE =0;//交通方式
    public static final int SEX_TYPE=1;//性别赛选
    public static final int AUTH_TYPE=2;//认证筛选

    FrameLayout mFlContent;
    protected  TextView mTvTitle;
    protected Toolbar mToolbar;
    ImageView mIvPageError;//展示错误页面
    ProgressBar mPbLoading;//加载中
    protected LayoutInflater inflater;
    protected View childView;
    protected MenuItem item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_toolbar);
        inflater = LayoutInflater.from(this);
        mIvPageError = (ImageView) findViewById(R.id.page_error);
        mPbLoading = (ProgressBar) findViewById(R.id.pb_loading);
        mPbLoading.setVisibility(View.GONE);
        mTvTitle = (TextView) findViewById(R.id.tv_appbar_title);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        childView = inflater.inflate(initLayoutRes(), mFlContent, false);
        mFlContent.addView(childView);
        ButterKnife.bind(this);
        initOptions();
        mTvTitle.setText(initTitle());

    }

    public TextView getmTvTitle() {
        return mTvTitle;
    }


    /**

     * 初始化布局文件
     *
     * @return
     */
    protected abstract int initLayoutRes();

    /**
     * 初始化监听等操作
     */
    protected abstract void initOptions();



    /**
     * 初始化标题
     *
     * @return
     */
    protected abstract String initTitle();

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

    /**
     * 其他按钮
     *
     * @param item
     */
    protected void otherOptionsItemSelected(MenuItem item) {

    }



    /**
     * 网络加载错误
     * @param isShow
     */
    protected void setErrorPage(boolean isShow) {
        mIvPageError.setVisibility(isShow ? View.VISIBLE : View.GONE);
        childView.setVisibility(View.GONE);//隐藏主布局
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
            childView.setVisibility(View.GONE);
        }
        mPbLoading.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
    protected void  setSuccess(){
        childView.setVisibility(View.VISIBLE);
        mIvPageError.setVisibility(View.GONE);
        mPbLoading.setVisibility(View.GONE);
    }


    /**
     * 注册EventBus
     *
     * @param activity
     */
    protected void registerEventBus(Activity activity) {
        if (!EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().register(activity);
        }
    }

    /**
     * 解除EventBus
     *
     * @param activity
     */
    protected void unregisterEventBus(Activity activity) {
        if (EventBus.getDefault().isRegistered(activity)) {
            EventBus.getDefault().unregister(activity);
        }
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
                            ActivityCompat.requestPermissions(BaseToolBarActivity.this,
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_appoint_menu,menu);
        item = menu.findItem(R.id.action_history);
        item.setTitle(initRightText());
        return true;
    }

    protected String initRightText() {
        return "";
    }
   public void canSmoothInNetScroll(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager){
       linearLayoutManager.setSmoothScrollbarEnabled(true);
       linearLayoutManager.setAutoMeasureEnabled(true);
       recyclerView.setHasFixedSize(true);
       recyclerView.setNestedScrollingEnabled(false);
   }
}
