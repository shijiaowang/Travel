package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
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

import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteActivity;
import com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite.AiteFollow;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.internal.framed.Variant;

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
   @BindView(R.id.rv_aite)
   RecyclerView mRvAite;
    private List<AiteFollow> mSelectPeople;
    private GridView mGvPhoto;
    private ViewStub mVsPhoto;
    private ViewStub mVsEmoji;
    private EaseEmojiconMenu mEsEmoj;
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
    private AitePeopleAdapter aitePeopleAdapter;

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
            mEsEmoj = (EaseEmojiconMenu) findViewById(R.id.ease_emoji);
            List<EaseEmojiconGroupEntity> emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
            emojiconGroupList.add(new EaseEmojiconGroupEntity(com.hyphenate.easeui.R.drawable.ee_1,  Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            mEsEmoj.init(emojiconGroupList,4);
            mEsEmoj.setOnEmojiChangeListener(new CreatePostEmojiListenerListener());
        } else {
            mEsEmoj.setVisibility(mEsEmoj.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

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
        if (mEsEmoj != null && mEsEmoj.getVisibility() == View.VISIBLE) {
            mEsEmoj.setVisibility(View.GONE);
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
        if (mEsEmoj != null && mEsEmoj.getVisibility() == View.VISIBLE) {
            mEsEmoj.setVisibility(View.GONE);
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==REQ_CODE &&resultCode==RESULT_CODE){
           try {
               mSelectPeople= (List<AiteFollow>) data.getSerializableExtra(IVariable.DATA);
               if (mSelectPeople==null || mSelectPeople.size()==0){
                   mRvAite.setVisibility(View.GONE);
                   return;
               }
               aitePeopleAdapter = new AitePeopleAdapter(mSelectPeople, this);
               LinearLayoutManager gridLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
               mRvAite.setAdapter(aitePeopleAdapter);
               mRvAite.setLayoutManager(gridLayoutManager);
               mRvAite.setVisibility(View.VISIBLE);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
    @Subscribe
    public void onEvent(DeleteAiteEvent deleteAiteEvent){
        mSelectPeople.remove(deleteAiteEvent.getPosition());
        aitePeopleAdapter.notifyItemRemoved(deleteAiteEvent.getPosition());
        if (mSelectPeople.size()==0){
            mRvAite.setVisibility(View.GONE);
        }
    }

    /**
     * 创建帖子
     * @param content
     */
    private void createPost(String content) {
        isCreateing = true;
        Map<String, String> createPostMap;
        String url=IVariable.DISCUSS_POST;
        String inform="";
        if (mSelectPeople!=null){
            StringBuilder stringBuilder=new StringBuilder();
            for (AiteFollow aiteFollow:mSelectPeople){
                stringBuilder.append(aiteFollow.getFollow().getId()+",");
            }
            String string = stringBuilder.toString();
            if (!StringUtils.isEmptyNotNull(string)){
                String newString = string.substring(0, string.length()-1);
                inform=newString;
            }
        }
        if (currentType==CREATE_POST) {
            url=IVariable.CIRCLE_CREATE_POST;
            String title = mEtTitle.getText().toString().trim();
            createPostMap = MapUtils.Build().addKey().addCId(cId).addContent(content).addTitle(title).addUserId().addInform(inform).end();
        }else {
            createPostMap=MapUtils.Build().addKey().addFroumId(forumId).addContent(content).addCId(cId).addPId(pId).addUserId().addRUserId(rUserId).addInform(inform).end();
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
        String hint = getIntent().getStringExtra(IVariable.DATA);
        if (!StringUtils.isEmptyNotNull(hint))mEtContent.setHint("回复："+hint);
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
        if (isCreateing)return;
        String content = mEtContent.getText().toString().trim();
        if (currentType==CREATE_POST&&StringUtils.isEmptyNotNull(content)) {
            ToastUtils.showToast("请输入内容");
        }else if (currentType==REPLY_POST && StringUtils.isEmptyNotNull(content) && getListSize(mSelectPeople)==0 && getListSize(pictures)==0){
            ToastUtils.showToast("请输入其中一项内容，文字或图片或@的人");
        } else {
            setIsProgress(true,false);
            createPost(content);
        }

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
                ToastUtils.showToast("回复成功");
                EventBus.getDefault().post(new ReplyEvent());
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
            EventBus.getDefault().post(new CreateEvent());
            ToastUtils.showToast("创建成功");
            finish();
    }

    @Override
    protected void onFail(CreatePostEvent createPostEvent) {
        isCreateing=false;
        setIsProgress(false);
    }
    public static void start(Context context,String cid,int pictureSize,int type,String forumId,String rid,String pid,String hint){
        Intent intent = new Intent(context, CreatePostActivity.class);
        intent.putExtra(IVariable.C_ID,cid);
        intent.putExtra(IVariable.PAGE_SIZE,pictureSize);
        intent.putExtra(IVariable.TYPE,type);
        intent.putExtra(IVariable.FORUM_ID,forumId);
        intent.putExtra(IVariable.R_USER_ID,rid);
        intent.putExtra(IVariable.PID,pid);
        intent.putExtra(IVariable.DATA,hint);
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
    class CreatePostEmojiListenerListener implements EaseEmojiconMenu.OnEmojiChangeListener{

        @Override
        public void onDeleteImageClicked() {
            boolean conentFocus = mEtContent.hasFocus();
            EditText focusText=conentFocus?mEtContent:mEtTitle;
            if (!TextUtils.isEmpty(focusText.getText())) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                focusText.dispatchKeyEvent(event);
            }
        }

        @Override
        public void onExpressionClicked(EaseEmojicon emojicon) {
            String emojiText = emojicon.getEmojiText();
            if (StringUtils.isEmpty(emojiText))return;
            boolean conentFocus = mEtContent.hasFocus();
            EditText focusText=conentFocus?mEtContent:mEtTitle;
            focusText.append(EaseSmileUtils.getSmiledText(CreatePostActivity.this,emojiText));
        }
    }
}
