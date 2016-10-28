package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteFollow;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.ui.fragment.EmojiFragment;
import com.yunspeak.travel.utils.DensityUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/29 0029.
 * 发表帖子
 */
public class CreatePostActivity extends BaseNetWorkActivity<CreatePostEvent> implements View.OnClickListener, View.OnFocusChangeListener {
    private static final int SHOW_PHOTO = 0;
    private static final int SHOW_EMOJI = 1;
    public  static final int CREATE_POST=3;
    public  static final int REPLY_POST=4;
    private int currentType=0;
   @BindView(R.id.tv_aite) TextView mTvAite;
   @BindView(R.id.tv_picture) TextView mTvPicture;
   @BindView(R.id.tv_emoji) TextView mTvEmoji;
   @BindView(R.id.et_title) EditText mEtTitle;
   @BindView(R.id.et_content) EditText mEtContent;
    private List<AiteFollow> mSelectPeople;
    private GridView mGvPhoto;
    private ViewStub mVsPhoto;
    private ViewStub mVsEmoji;
    private ViewPager mVpEmoji;
    private List<EmojiFragment> fragments;
    private RelativeLayout mRlEmoji;
    private LinearLayout mLlDot;
    private int mPointDistance;
    private int mFirstDotLeft;
    private View mVDot;
    private InputMethodManager imm;

    private int sendDelayTime = 0;//延迟发送广播时间，解决软键盘弹出情况下，再弹出表情包等等闪屏状况
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            removeCallbacksAndMessages(null);
            switch (msg.what) {
                case SHOW_PHOTO:
                    showPicture();
                    break;
                case SHOW_EMOJI:
                    showEmoji();
                    break;
            }
        }
    };
    private List<String> pictures;
    private CreatePostPhotoAdapter createPostPhotoAdapter;
    private String addFlag="add";
    private String cId;
    private boolean isCreateing=false;
    private int size;
    private String rUserId;
    private String pId;
    private String forumId;
    private String title;
    private String rigtText="";

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_post;
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected String initTitle() {
        return title;
    }

    /**
     * 初始化小圆点
     */
    private void initDot() {
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        mLlDot = (LinearLayout) findViewById(R.id.ll_indicator);
        for (int i = 0; i < fragments.size(); i++) {
            View view = new View(this);
            view.setBackgroundResource(R.drawable.dot_for_viewpager_indicator);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dipToPx(this, 6), DensityUtils.dipToPx(this, 6));
            if (i > 0) {
                params.leftMargin = DensityUtils.dipToPx(this, 11);
            }
            view.setLayoutParams(params);
            mLlDot.addView(view);
        }
        /**
         * 绘制完成回调
         */
        mLlDot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mPointDistance = mLlDot.getChildAt(1).getLeft() - mLlDot.getChildAt(0).getLeft();
                //获取第一个的左边
                mFirstDotLeft = mLlDot.getChildAt(0).getLeft();
                //初始化小圆点
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
                layoutParams.leftMargin = mFirstDotLeft;
                mVDot.setLayoutParams(layoutParams);
                //移除监听事件
                mLlDot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showSoft(mEtTitle);

    }

    private void showSoft(EditText mEtTitle) {
        mEtTitle.setFocusable(true);
        mEtTitle.requestFocus();
        //第一次进入强制显示软键盘
        if (!imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected String initRightText() {
        return rigtText;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_title:
            case R.id.et_content:
                hideEmojiOrPhoto();
                break;
            case R.id.tv_aite:
                Intent intent = new Intent(this, AiteActivity.class);
                intent.putExtra(IVariable.DATA, (Serializable) mSelectPeople);
                startActivityForResult(intent,REQ_CODE);
                break;
            default:
                hideSoftClick(v);
                break;

        }

    }

    private void hideSoftClick(View v) {
        sendDelayTime = 0;//初始化
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(mEtTitle.getWindowToken(), 0); //强制隐藏键盘
            sendDelayTime = 300;
        }
        switch (v.getId()) {
            case R.id.tv_picture:
                mHandler.sendEmptyMessageDelayed(SHOW_PHOTO, sendDelayTime);
                return;
            case R.id.tv_emoji:
                mHandler.sendEmptyMessageDelayed(SHOW_EMOJI, sendDelayTime);
                break;
        }
    }

    private void showEmoji() {
        if (mVsEmoji == null) {
            mVsEmoji = (ViewStub) findViewById(R.id.vs_emoji);
            mVsEmoji.inflate();
            mVpEmoji = (ViewPager) findViewById(R.id.vp_emoji);
            mRlEmoji = (RelativeLayout) findViewById(R.id.rl_emoji);
            fragments = new ArrayList<>();
            fragments.add(new EmojiFragment());
            fragments.add(new EmojiFragment());
            fragments.add(new EmojiFragment());
            fragments.add(new EmojiFragment());
            mVpEmoji.setAdapter(new FragmentEmojiAdapter(getSupportFragmentManager()));
            mVDot = findViewById(R.id.v_dot);
            initDot();

            mVpEmoji.setOnPageChangeListener(new EmojiOnPagerChangeListener());
        } else {
            mRlEmoji.setVisibility(mRlEmoji.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

        }
        if (mGvPhoto != null && mGvPhoto.getVisibility() == View.VISIBLE) {
            mGvPhoto.setVisibility(View.GONE);
        }
    }

    private void showPicture() {
        if (mVsPhoto == null) {
            mVsPhoto = (ViewStub) findViewById(R.id.vs_photo);
            mVsPhoto.inflate();
            mGvPhoto = (GridView) findViewById(R.id.gv_photo);
            pictures = new ArrayList<>();
            pictures.add(addFlag);
            createPostPhotoAdapter = new CreatePostPhotoAdapter(CreatePostActivity.this, pictures,GlobalValue.size);
            mGvPhoto.setAdapter(createPostPhotoAdapter);
            mGvPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (pictures==null ||pictures.size()==1 ||(pictures.size() != GlobalValue.size && position == pictures.size() - 1)) {
                        startSelectAlbum();
                    }
                }
            });
        } else {
            mGvPhoto.setVisibility(mGvPhoto.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        if (mRlEmoji != null && mRlEmoji.getVisibility() == View.VISIBLE) {
            mRlEmoji.setVisibility(View.GONE);
        }
    }

    /**
     * 打开相册
     */
    private void startSelectAlbum() {
        startActivity(new Intent(CreatePostActivity.this, AlbumSelectorActivity.class));
        GlobalValue.mSelectImages = pictures;
        GlobalValue.mSelectImages.remove(addFlag);
    }


    private void hideEmojiOrPhoto() {
        if (mRlEmoji != null && mRlEmoji.getVisibility() == View.VISIBLE) {
            mRlEmoji.setVisibility(View.GONE);
        }
        if (mGvPhoto != null && mGvPhoto.getVisibility() == View.VISIBLE) {
            mGvPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            hideEmojiOrPhoto();
        }
    }


    class FragmentEmojiAdapter extends FragmentPagerAdapter {

        public FragmentEmojiAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    //监听事件
    class EmojiOnPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //动态改变小红点的值
            float len = mPointDistance * positionOffset + mPointDistance * position;
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mVDot.getLayoutParams();
            layoutParams.leftMargin = (int) (len + mFirstDotLeft);
            //Utils.ShowToast(MainActivity.this,len+"");
            mVDot.setLayoutParams(layoutParams);


        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==REQ_CODE &&resultCode==RESULT_CODE){
           try {
               mSelectPeople= (List<AiteFollow>) data.getSerializableExtra(IVariable.DATA);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }

    /**
     * 创建帖子
     */
    private void createPost() {

        String content = mEtContent.getText().toString().trim();
        if (StringUtils.isEmpty(content)) {
            ToastUtils.showToast("请输入内容");
            return;
        }

        if (isCreateing)return;
        isCreateing = true;
        Map<String, String> createPostMap;
        String url=IVariable.DISCUSS_POST;
        if (currentType==CREATE_POST) {
            url=IVariable.CIRCLE_CREATE_POST;
            String inform="";
            String title = mEtTitle.getText().toString().trim();
            if (mSelectPeople!=null){
                StringBuilder stringBuilder=new StringBuilder();
                for (AiteFollow aiteFollow:mSelectPeople){
                    stringBuilder.append(aiteFollow.getFollow().getId()+",");
                }
                String string = stringBuilder.toString();
                if (!StringUtils.isEmpty(string)){
                    string.substring(string.length()-1,string.length());
                    inform=string;
                }
            }
            createPostMap = MapUtils.Build().addKey(this).addCId(cId).addContent(content).addTitle(title).addUserId().addInform(inform).end();
        }else {
            createPostMap=MapUtils.Build().addKey(this).addFroumId(forumId).addContent(content).addCId(cId).addPId(pId).addUserId().addRUserId(rUserId).end();
        }
        XEventUtils.postFileCommonBackJson(url,createPostMap,pictures,currentType,new CreatePostEvent());
    }

    @Override
    protected void initEvent() {
        cId = getIntent().getStringExtra(IVariable.C_ID);
        GlobalValue.size = getIntent().getIntExtra(IVariable.PAGE_SIZE,12);
        currentType = getIntent().getIntExtra(IVariable.TYPE,0);
        title = currentType==REPLY_POST?"回复帖子":"创建帖子";
        rigtText = currentType==REPLY_POST?"回复":"创建";
        rUserId = getIntent().getStringExtra(IVariable.R_USER_ID);
        pId = getIntent().getStringExtra(IVariable.PID);
        forumId = getIntent().getStringExtra(IVariable.FORUM_ID);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mTvPicture.setOnClickListener(this);
        mTvEmoji.setOnClickListener(this);
        mTvAite.setOnClickListener(this);
        mEtTitle.setOnClickListener(this);
        mEtContent.setOnClickListener(this);
        if (currentType==REPLY_POST){
            mEtTitle.setVisibility(View.GONE);
        }else {
            mEtTitle.setOnFocusChangeListener(this);
        }
        mEtContent.setOnFocusChangeListener(this);
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        setIsProgress(true,false);
        createPost();
    }
    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return "";
    }

    @Override
    protected void onSuccess(CreatePostEvent createPostEvent) {
        isCreateing=false;
        switch (createPostEvent.getType()){
            case CREATE_POST:
                dealData(createPostEvent);
                break;
            case REPLY_POST:
                finish();
                break;
        }

    }

    @Subscribe
    public void onEvent(UpPhotoEvent event) {
        pictures.clear();
        pictures.addAll(event.getList());
        if (pictures.size() != GlobalValue.size && !pictures.contains("add")) {
            pictures.add(addFlag);
        }
        createPostPhotoAdapter.notifyData(pictures);

    }

    private void dealData(CreatePostEvent event) {
            ToastUtils.showToast("创建成功");
            finish();
    }

    @Override
    protected void onFail(CreatePostEvent createPostEvent) {
        setIsProgress(false);
    }
    public static void start(Context context,String cid,int pictureSize,int type,String forumId,String rid,String pid){
        Intent intent = new Intent(context, CreatePostActivity.class);
        intent.putExtra(IVariable.C_ID,cid);
        intent.putExtra(IVariable.PAGE_SIZE,pictureSize);
        intent.putExtra(IVariable.TYPE,type);
        intent.putExtra(IVariable.FORUM_ID,forumId);
        intent.putExtra(IVariable.R_USER_ID,rid);
        intent.putExtra(IVariable.PID,pid);
        context.startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        GlobalValue.mSelectImages=null;
    }
}
