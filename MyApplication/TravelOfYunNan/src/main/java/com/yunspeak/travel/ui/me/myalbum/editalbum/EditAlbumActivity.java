package com.yunspeak.travel.ui.me.myalbum.editalbum;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.AlbumSelectorActivity;
import com.yunspeak.travel.ui.baseui.BaseCropPhotoActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.ui.view.refreshview.XScrollView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 编辑相册  powered by wangyang
 */

public class EditAlbumActivity extends BaseCropPhotoActivity<EditAlbumEvent> implements View.OnClickListener {

    private static final boolean TYPE_EDIT_ALBUM =true;
    @ViewInject(R.id.rl_toggle)
    private RelativeLayout mRlToggle;

    @ViewInject(R.id.tv_photo)
    private TextView mTvPhoto;

    private TextView mTvMore;
    private boolean isEdit = false;
    private String id;
    private EditAlbumAdapter editAlbumAdapter;
    private List<EditAlbumBean.DataBean.BodyBean> body;
    private String name;
    private String des;
    private String title;
    private RelativeLayout mRlEditDes;
    private TextView mTvName;
    private SimpleDraweeView mIvCover;
    private TextView mTvSetCover;
    private EditText mEdSetName;
    private LinearLayout mLlDes;
    private EditText mEtDes;
    private TextView mTvCount;
    private TextView mTvDes;
    private ListView mLvPhoto;
    private int index;
    private List<String> pictureList;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_edit_album;
    }


    @Override
    protected void onLoad(int type) {
        id = getIntent().getStringExtra(IVariable.ID);
        if (StringUtils.isEmpty(id)) return;
        int count=type==TYPE_REFRESH?0:getListSize(body);
        Map<String, String> editMap = MapUtils.Build().addKey(this).addUserId().addId(id).addPageSize().addCount(count).end();
        XEventUtils.getUseCommonBackJson(IVariable.EDIT_ALBUM, editMap, TYPE_LOAD, new EditAlbumEvent());
    }

    @Override
    protected void initChildListener() {
        initXScrollView(false, true);
        XScrollView xScrollView = getxScrollView();
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_edit_album_content, null);
        if (inflate != null) {
            mRlEditDes = (RelativeLayout) inflate.findViewById(R.id.rl_edit_des);
            mTvName = (TextView) inflate.findViewById(R.id.tv_album_name);
            mIvCover = (SimpleDraweeView) inflate.findViewById(R.id.iv_cover);
            mTvSetCover = (TextView) inflate.findViewById(R.id.tv_set_cover);
            mEdSetName = (EditText) inflate.findViewById(R.id.ed_set_name);
            mLlDes = (LinearLayout) inflate.findViewById(R.id.ll_des);
            mEtDes = (EditText) inflate.findViewById(R.id.et_des);
            mTvCount = (TextView) inflate.findViewById(R.id.tv_count);
            mTvDes = (TextView) inflate.findViewById(R.id.tv_des);
            mLvPhoto = (ListView) inflate.findViewById(R.id.lv_photo);
            xScrollView.setView(inflate);
        }
        mTvMore = getmTvRightIcon();
        mTvMore.setText("编辑");
        mTvPhoto.setOnClickListener(this);
        mTvMore.setOnClickListener(this);
        setScrollListener(new ScrollListener() {
            @Override
            public void percent(float percent) {
                LogUtils.e(percent + "");
                percent = percent > 1 ? 1f : percent;
                if (percent <= 0.5) {
                    changeTitle("");
                } else {
                    changeTitle(title);
                }
                mTvName.setAlpha(1f - percent);
            }
        });
        mEtDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = getString(mEtDes).length();
                if (length == 0) {
                    mLlDes.setVisibility(View.VISIBLE);
                } else {
                    if (mLlDes.getVisibility() == View.VISIBLE) {
                        mLlDes.setVisibility(View.GONE);
                    }
                    if (length >= 80) {
                        ToastUtils.showToast("输入字数最大了。");
                        return;
                    }
                    mTvCount.setText(length + "/80");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvSetCover.setOnClickListener(this);


    }

    /**
     * 更新相册
     *
     * @param name
     * @param des
     */
    private void updateAlbum(String name, String des) {
        Map<String, String> updateMap = MapUtils.Build().addKey(this).addId(id).addUserId().addTitle(name).addContent(des).addPictureId(getPictureId()).end();
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
                builder.append(bodyBean.getId() + ",");
            }
        }
        return builder.toString();
    }
   public static void start(Context context,String id){
       Intent intent=new Intent(context,EditAlbumActivity.class);
       intent.putExtra(IVariable.ID,id);
       context.startActivity(intent);
   }
    private void changeViewShow(boolean isEdit) {
        mTvMore.setText(isEdit ? "保存" : "编辑");
        mRlToggle.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        mTvDes.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        mTvName.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        mEdSetName.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mRlEditDes.setVisibility(isEdit ? View.VISIBLE : View.GONE);
        mTvSetCover.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }


    private void dealData(EditAlbumEvent event) {


      if (event.getType()==TYPE_UPDATE){
           mTvName.setText(name);
           mTvDes.setText(des);
           changeViewShow(isEdit);
           if (editAlbumAdapter != null) {
               EditAlbumHolder.canDelete = false;
               editAlbumAdapter.notifyDataSetChanged();
           }
           ToastUtils.showToast(event.getMessage());
       }else if (event.getType()==TYPE_UP_FILE){
          ToastUtils.showToast("第"+index+"张上传成功。");
          index++;
          if (pictureList!=null && pictureList.size()-1>=index) {
              upPicture(pictureList.get(index));
          }
       }else {
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
        if (editAlbumAdapter==null) {
            mTvDes.setText(getString(R.string.kongge) + head.getContent());
            title = head.getTitle();
            mTvName.setText(title);
            x.image().bind(mIvCover, head.getBackground_img());
            body = data.getBody();
            if (body == null || body.size() == 0) return;
            editAlbumAdapter = new EditAlbumAdapter(this, body);
            mLvPhoto.setAdapter(editAlbumAdapter);
        }else {
            body.addAll(data.getBody());
            editAlbumAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected SimpleDraweeView childViewShow() {
        return mIvCover;
    }


    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "";
    }

    @Override
    protected void onSuccess(EditAlbumEvent event) {
        dealData(event);
    }

    @Override
    protected void onFail(EditAlbumEvent editAlbumEvent) {
        if (editAlbumEvent.getType()==TYPE_UP_FILE){
            ToastUtils.showToast("第"+index+"张上传失败。");
            index++;
            if (pictureList!=null && pictureList.size()-1>=index) {
                upPicture(pictureList.get(index));
            }
        }else {
            super.onFail(editAlbumEvent);
        }
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }
    @Subscribe
   public void onEvent(UpPhotoEvent event){
        pictureList = event.getList();
        if (pictureList==null || pictureList.size()==0)return;
        index = 0;
        upPicture(pictureList.get(index));

    }

    private void upPicture(String s) {
        Map<String, String> upAlbum = MapUtils.Build().addKey(this).addUserId().addId(id).end();
        List<String> files=new ArrayList<>();
        files.add(s);
        XEventUtils.postFileCommonBackJson(IVariable.ADD_ALBUM_PHOTO,upAlbum,files,TYPE_UP_FILE,new EditAlbumEvent());
        ToastUtils.showToast("正在上传第"+index+"张图片");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_photo:
                Intent intent = new Intent(this, AlbumSelectorActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_set_cover:
                hideSoftWore(mEtDes);
                showPictureCutPop(mRlToggle);
                break;
            case R.id.tv_right_icon:
                changeEdit();
                break;
        }
    }

    private void changeEdit() {
        isEdit = !isEdit;
        if (isEdit) {
            mEdSetName.setText(mTvName.getText().toString().trim());
            mEtDes.setText(mTvDes.getText().toString().trim());
            if (editAlbumAdapter != null) {
                EditAlbumHolder.canDelete = true;
                editAlbumAdapter.notifyDataSetChanged();
            }
            changeViewShow(isEdit);
        } else {
            name = mEdSetName.getText().toString().trim();
            if (StringUtils.isEmpty(name)) {
                ToastUtils.showToast("相册名不能为空");
                isEdit = !isEdit;
                return;
            }
            des = mEtDes.getText().toString().trim();
            updateAlbum(name, des);
        }
    }

    @Override
    protected boolean isXScrollView() {
        return true;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }



}

