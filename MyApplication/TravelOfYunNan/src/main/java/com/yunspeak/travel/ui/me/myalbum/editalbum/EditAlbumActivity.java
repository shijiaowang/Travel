package com.yunspeak.travel.ui.me.myalbum.editalbum;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.EditAlbumBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.popwindow.AppointDetailMorePop;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.photopreview.CirclePreviewActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.me.myappoint.withmeselect.TopDecoration;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 编辑相册  powered by wangyang
 */
public class EditAlbumActivity extends BaseCutPhotoActivity<EditAlbumEvent> implements View.OnClickListener, OnLoadMoreListener {
    public static boolean canDelete=false;
    @BindView(R.id.rl_toggle)
    RelativeLayout mRlToggle;
    @BindView(R.id.tv_photo)
    TextView mTvPhoto;
    @BindView(R.id.iv_cover)
    SimpleDraweeView ivCover;
    @BindView(R.id.tv_album_name)
    TextView tvAlbumName;
    @BindView(R.id.ed_set_name)
    EditText edSetName;
    @BindView(R.id.tv_set_cover)
    TextView tvSetCover;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.et_des)
    EditText etDes;
    @BindView(R.id.ll_des)
    LinearLayout llDes;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rl_edit_des)
    RelativeLayout rlEditDes;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.swipe_target)
    NestedScrollView swipeTarget;
    @BindView(R.id.swipe_container)
    SwipeToLoadLayout swipeContainer;
    private boolean isEdit = false;
    private String id;
    private EditAlbumAdapter editAlbumAdapter;
    private List<EditAlbumBean.DataBean.BodyBean> body;
    private String name;
    private String des;
    private String title;
    private int index;
    private List<String> pictureList;
    private TextView mTvCurrent;
    private ProgressBar mPbProgress;
    private Dialog dialog;
    private boolean isSuccess;
    private Callback.Cancelable cancelable;
    private boolean isCancel;

    @Override
    protected boolean isChangeBarColor() {
        return true;
    }

    @Override
    protected void initEvent() {
        mToolbar.setBackgroundColor(Color.argb(0, 92, 208, 194));
        View footView = inflater.inflate(R.layout.layout_google_footer, swipeContainer, false);
        swipeContainer.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        swipeContainer.setLoadMoreFooterView(footView);
        swipeContainer.setOnLoadMoreListener(this);
        id = getIntent().getStringExtra(IVariable.ID);
        swipeTarget.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float absOffset = scrollY / 300f;
                absOffset = absOffset > 1 ? 1 : absOffset;
                if (absOffset <= 0.5) {
                    mTvTitle.setText("");
                } else {
                    mTvTitle.setText(title);
                }
                mToolbar.setBackgroundColor(Color.argb((int) (absOffset * 255), 92, 208, 194));
                tvAlbumName.setAlpha(1f - absOffset);

            }
        });
        etDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = getString(etDes).length();
                if (length == 0) {
                    llDes.setVisibility(View.VISIBLE);
                } else {
                    if (llDes.getVisibility() == View.VISIBLE) {
                        llDes.setVisibility(View.GONE);
                    }
                    if (length >= 80) {
                        ToastUtils.showToast("输入字数最大了。");
                        return;
                    }
                    tvCount.setText(length + "/80");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvSetCover.setOnClickListener(this);
        mTvPhoto.setOnClickListener(this);
    }

    @Override
    protected String initRightText() {
        return "更多";
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        int count = type == TYPE_REFRESH ? 0 : getListSize(body);
        builder.addId(id).addPageSize().addCount(count).end();
    }
    @Override
    protected void userResultSize(UCrop uCrop, int width, int height) {
        super.userResultSize(uCrop, 2000, 1000);
    }
    @Override
    protected String initUrl() {
        return IVariable.EDIT_ALBUM;
    }

    /**
     * 更新相册
     *
     * @param name
     * @param des
     */
    private void updateAlbum(String name, String des) {
        Map<String, String> updateMap = MapUtils.Build().addKey().addId(id).addUserId().addTitle(name).addContent(des).addPictureId(getPictureId()).end();
        List<String> files = new ArrayList<>();
        files.add(filename);
        XEventUtils.postFileCommonBackJson(IVariable.UPDATE_ALBUM, updateMap, files, TYPE_UPDATE, new EditAlbumEvent());
    }

    /**
     * 获取图片id
     *
     * @return
     */
    private String getPictureId() {
        if (body == null || body.size() == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (EditAlbumBean.DataBean.BodyBean bodyBean : body) {
            if (body.indexOf(bodyBean) == body.size() - 1) {
                builder.append(bodyBean.getId());
            } else {
                builder.append(bodyBean.getId()).append(",");
            }
        }
        return builder.toString();
    }

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, EditAlbumActivity.class);
        intent.putExtra(IVariable.ID, id);
        context.startActivity(intent);
    }

    private void changeViewShow(boolean isEdit) {
        item.setTitle(isEdit ? "保存" : "编辑");
        mRlToggle.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        tvDes.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        tvAlbumName.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        edSetName.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        rlEditDes.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        tvSetCover.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }


    private void dealData(EditAlbumEvent event) {

        if (event.getType() == TYPE_UPDATE) {
            tvAlbumName.setText(name);
            tvDes.setText(des);
            changeViewShow(isEdit);
            if (editAlbumAdapter != null) {
                canDelete = false;
                editAlbumAdapter.notifyDataSetChanged();
            }
            ToastUtils.showToast(event.getMessage());
        } else if (event.getType() == TYPE_UP_FILE) {
            index++;
            if (pictureList != null && pictureList.size() - 1 >= index) {
                upPicture(pictureList.get(index));
            } else if (dialog != null) {
                isSuccess = true;
                onLoad(TYPE_LOAD);
                dialog.dismiss();
            }
        } else {
            dealLoadData(event);
        }


    }

    /**
     * 读取加载数据进入时
     *
     * @param event
     */
    private void dealLoadData(EditAlbumEvent event) {
        EditAlbumBean editAlbumBean = GsonUtils.getObject(event.getResult(), EditAlbumBean.class);
        EditAlbumBean.DataBean data = editAlbumBean.getData();
        if (data == null) return;
        EditAlbumBean.DataBean.HeadBean head = data.getHead();
        if (editAlbumAdapter == null) {
            tvDes.setText(getString(R.string.kongge) + head.getContent());
            title = head.getTitle();
            tvAlbumName.setText(title);
            FrescoUtils.displayNormal(ivCover, head.getBackground_img(), 600, 450,R.drawable.normal_2_1);
            body = data.getBody();
            if (body == null || body.size() == 0) return;
            editAlbumAdapter = new EditAlbumAdapter(body, this);
            rvPhoto.setAdapter(editAlbumAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setSmoothScrollbarEnabled(false);
            rvPhoto.setNestedScrollingEnabled(false);
            rvPhoto.addItemDecoration(new TopDecoration(10));
            rvPhoto.setHasFixedSize(true);
            rvPhoto.setLayoutManager(linearLayoutManager);

        } else if (event.getType()==TYPE_LOAD){
            body.addAll(data.getBody());
            editAlbumAdapter.notifyDataSetChanged();
        }else if (event.getType()==TYPE_REFRESH){
            body=data.getBody();
            editAlbumAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onSuccess(EditAlbumEvent event) {
        switch (event.getType()) {
            case TYPE_DELETE:
                body.remove(event.getPosition());
                editAlbumAdapter.notifyItemRemoved(event.getPosition());
                editAlbumAdapter.notifyDataSetChanged();
                break;
            case TYPE_LOAD:
                dealLoadData(event);
                swipeContainer.setLoadingMore(false);
                break;
            case TYPE_DELETE2:
                ToastUtils.showToast("删除成功");
                finish();
                break;
            case TYPE_OTHER:
                if (getListSize(body)==0)return;//点击事件
                List<String> imageList=new ArrayList<>();
                for (EditAlbumBean.DataBean.BodyBean bodyBean:body){
                    imageList.add(bodyBean.getPath());
                }
                CirclePreviewActivity.start(this,imageList,event.getPosition());
                break;
            default:
                dealData(event);
                break;

        }

    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (isEdit){//如果是编辑状态，启用保存
            changeEdit();
            return;
        }
        AppointDetailMorePop.showEdit(this, new ParentPopClick() {
            @Override
            public void onClick(int type) {
                if (type==1){
                    changeEdit();
                }else if (type==2){
                    EnterAppointDialog.showCommonDialog(EditAlbumActivity.this, "删除相册", "确认", "是否删除当前相册?", new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            Map<String, String> end = MapUtils.Build().addKey().addUserId().addId(id).end();
                            XEventUtils.postUseCommonBackJson(IVariable.DELETE_ALBUM,end,TYPE_DELETE2,new EditAlbumEvent());
                        }
                    });
                }
            }
        },mToolbar);

    }

    @Override
    protected void onFail(EditAlbumEvent editAlbumEvent) {
        swipeContainer.setLoadingMore(false);
        if (editAlbumEvent.getType() == TYPE_UP_FILE) {
            index++;
            ToastUtils.showToast("第"+index+"张上传失败。");

            if (pictureList != null && pictureList.size() - 1 >= index) {
                upPicture(pictureList.get(index));
            }
        } else {
            super.onFail(editAlbumEvent);
        }
    }


    @Subscribe
    public void onEvent(UpPhotoEvent event) {
        pictureList = event.getList();
        if (pictureList == null || pictureList.size() == 0) return;
        index = 0;
        GlobalValue.mSelectImages=null;
        upPicture();
        upPicture(pictureList.get(index));

    }

    private void upPicture(String s) {
        if (isCancel)return;
        Map<String, String> upAlbum = MapUtils.Build().addKey().addUserId().addId(id).end();
        List<String> files = new ArrayList<>();
        files.add(s);
        cancelable = XEventUtils.postFileCommonBackJson(IVariable.ADD_ALBUM_PHOTO, upAlbum, files, TYPE_UP_FILE, new EditAlbumEvent());
        if (mTvCurrent != null && mPbProgress != null) {
            String head="正在上传图片";
            String current=(index+1)+"";
            SpannableStringBuilder spannable = new SpannableStringBuilder(head+current+"/"+getListSize(pictureList));
            spannable.setSpan(new ClickText(),head.length(), head.length()+current.length()
                        , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mTvCurrent.setText(spannable);
            mPbProgress.setMax(pictureList.size());
            mPbProgress.setProgress((index + 1));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_photo:
                Intent intent = new Intent(this, AlbumSelectorActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_set_cover:
                hideSoftWore(etDes);
                showPictureCutPop(mRlToggle);
                break;

        }
    }

    private void changeEdit() {
        isEdit = !isEdit;
        if (isEdit) {
            edSetName.setText(tvAlbumName.getText().toString().trim());
            tvDes.setText(tvDes.getText().toString().trim());
            if (editAlbumAdapter != null) {
                canDelete = true;
                editAlbumAdapter.notifyDataSetChanged();
            }
            changeViewShow(isEdit);
        } else {
            name = edSetName.getText().toString().trim();
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showToast("相册名不能为空");
                isEdit = !isEdit;
                return;
            }
            des = etDes.getText().toString().trim();
            updateAlbum(name, des);
            item.setTitle("更多");
        }
    }


    @Override
    protected void childDisplay(String url, String filename) {
        FrescoUtils.displayNormal(ivCover, url, 600, 450);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_edit_album;
    }

    @Override
    protected String initTitle() {
        return "";
    }

    @Override
    public void onLoadMore() {
        onLoad(TYPE_LOAD);
    }

    public void upPicture() {
        isSuccess = false;
        isCancel=false;
        //创建视图
        View dialogView = View.inflate(this, R.layout.dialog_up_progress, null);
        mPbProgress = (ProgressBar) dialogView.findViewById(R.id.pb_progress);
        mTvCurrent = (TextView) dialogView.findViewById(R.id.tv_current);
        dialog = new Dialog(this, R.style.noTitleDialog);

        dialogView.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (isSuccess) {
                    dialog.dismiss();
                    ToastUtils.showToast("上传完毕");
                } else {
                    EnterAppointDialog.showCommonDialog(EditAlbumActivity.this, "取消上传", "确定", "是否取消上传剩余的图片？", new ParentPopClick() {
                        @Override
                        public void onClick(int type) {
                            isSuccess = true;
                            if (cancelable!=null){
                                cancelable.cancel();
                                isCancel=true;
                                index=0;
                                pictureList=null;
                            }
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        float width = getResources().getDimension(R.dimen.x285);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogView, params);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow(); //得到对话框
        if (window != null) {
            window.setGravity(Gravity.CENTER);
        }
        dialog.show();
    }

    class ClickText extends ClickableSpan {


        @Override
        public void onClick(View widget) {

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor("#5cd0c2"));
            ds.setUnderlineText(false);//是否有下划线
        }
    }
}

