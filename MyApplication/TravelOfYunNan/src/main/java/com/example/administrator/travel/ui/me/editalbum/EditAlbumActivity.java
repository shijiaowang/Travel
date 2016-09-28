package com.example.administrator.travel.ui.me.editalbum;


import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.me.albumselector.AlbumSelectorActivity;
import com.example.administrator.travel.ui.baseui.BaseCropPhotoActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 编辑相册  powered by wangyang
 */

public class EditAlbumActivity extends BaseCropPhotoActivity implements View.OnClickListener {
    @ViewInject(R.id.iv_cover)
    private ImageView mIvCover;
    @ViewInject(R.id.tv_set_cover)
    private TextView mTvSetCover;
    @ViewInject(R.id.ed_set_name)
    private EditText mEdSetName;
    @ViewInject(R.id.ll_des)
    private LinearLayout mLlDes;
    @ViewInject(R.id.et_des)
    private EditText mEtDes;
    @ViewInject(R.id.tv_count)
    private TextView mTvCount;
    @ViewInject(R.id.tv_des)
    private TextView mTvDes;
    @ViewInject(R.id.lv_photo)
    private ListView mLvPhoto;
    @ViewInject(R.id.rl_toggle)
    private RelativeLayout mRlToggle;
    @ViewInject(R.id.rl_edit_des)
    private RelativeLayout mRlEditDes;
    @ViewInject(R.id.tv_photo)
    private TextView mTvPhoto;
    @ViewInject(R.id.tv_album_name)
    private TextView mTvName;
    private TextView mTvMore;
    private boolean isEdit=false;
    private String id;
    private EditAlbumAdapter editAlbumAdapter;
    private List<EditAlbumBean.DataBean.BodyBean> body;
    private String name;
    private String des;
    private String title;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_edit_album;
    }


    @Override
    protected void onLoad(int type) {
        id = getIntent().getStringExtra(IVariable.ID);
        if (StringUtils.isEmpty(id))return;
        Map<String, String> editMap = MapUtils.Build().addKey(this).addUserId().addId(id).end();
        XEventUtils.getUseCommonBackJson(IVariable.EDIT_ALBUM,editMap,TYPE_LOAD,new EditAlbumEvent());
    }

    @Override
    protected void initChildListener() {
        mTvMore = getmTvRightIcon();
        mTvMore.setText("编辑");
        mTvPhoto.setOnClickListener(this);
        mTvMore.setOnClickListener(this);
        setScrollListener(new ScrollListener() {
            @Override
            public void percent(float percent) {
                LogUtils.e(percent+"");
                percent=percent>1?1f:percent;
                if (percent<=0.5){
                    changeTitle("");
                }else {
                    changeTitle(title);
                }
                mTvName.setAlpha(1f-percent);
            }
        });
        mEtDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = getString(mEtDes).length();
                if (length==0){
                    mLlDes.setVisibility(View.VISIBLE);
                }else {
                    if (mLlDes.getVisibility()==View.VISIBLE) {
                        mLlDes.setVisibility(View.GONE);
                    }
                    if (length>=80){
                        ToastUtils.showToast("输入字数最大了。");
                        return;
                    }
                    mTvCount.setText(length+"/80");
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
     * @param name
     * @param des
     */
    private void updateAlbum(String name, String des) {
        Map<String, String> updateMap = MapUtils.Build().addKey(this).addId(id).addUserId().addTitle(name).addContent(des).addPictureId(getPictureId()).end();
        List<String> files=new ArrayList<>();
        files.add(filename);
        XEventUtils.postFileCommonBackJson(IVariable.UPDATE_ALBUM,updateMap,files,TYPE_UPDATE,new EditAlbumEvent());
    }

    /**
     * 获取图片id
     * @return
     */
    private String getPictureId() {
        if (body==null || body.size()==0){
            return "";
        }
        StringBuilder builder=new StringBuilder();
        for (EditAlbumBean.DataBean.BodyBean bodyBean:body){
            if (body.indexOf(bodyBean)==body.size()-1){
                builder.append(bodyBean.getId());
            }else {
                builder.append(bodyBean.getId() + ",");
            }
        }
        return builder.toString();
    }

    private void changeViewShow(boolean isEdit) {
        mTvMore.setText(isEdit?"保存":"编辑");
        mRlToggle.setVisibility(isEdit?View.GONE:View.VISIBLE);
        mTvDes.setVisibility(isEdit?View.GONE:View.VISIBLE);
        mTvName.setVisibility(isEdit?View.GONE:View.VISIBLE);
        mEdSetName.setVisibility(isEdit?View.VISIBLE:View.GONE);
        mRlEditDes.setVisibility(isEdit?View.VISIBLE:View.GONE);
        mTvSetCover.setVisibility(isEdit?View.VISIBLE:View.GONE);
    }
    @Subscribe
    public void onEvent(EditAlbumEvent event){
        setIsProgress(false);
        if (event.isSuccess()){
            try {
                dealData(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (event.getType()==TYPE_LOAD){
                setIsError(true);
            }
            ToastUtils.showToast(event.getMessage());
        }

  }

    private void dealData(EditAlbumEvent event) {
        switch (event.getType()){
            case TYPE_LOAD:
                dealLoadData(event);
                break;
            case TYPE_UPDATE:
                mTvName.setText(name);
                mTvDes.setText(des);
                changeViewShow(isEdit);
                if (editAlbumAdapter!=null) {
                    EditAlbumHolder.canDelete = false;
                    editAlbumAdapter.notifyDataSetChanged();
                }
                ToastUtils.showToast(event.getMessage());
                break;
        }
    }

    /**
     * 读取加载数据进入时
     * @param event
     */
    private void dealLoadData(EditAlbumEvent event) {
        EditAlbumBean editAlbumBean = GsonUtils.getObject(event.getResult(), EditAlbumBean.class);
        EditAlbumBean.DataBean data = editAlbumBean.getData();
        if (data==null)return;
        EditAlbumBean.DataBean.HeadBean head = data.getHead();
        mTvDes.setText(getString(R.string.kongge)+head.getContent());
        title = head.getTitle();
        mTvName.setText(title);
        x.image().bind(mIvCover,head.getBackground_img());
        body = data.getBody();
        if (body ==null || body.size()==0)return;
        editAlbumAdapter = new EditAlbumAdapter(this, body);
        mLvPhoto.setAdapter(editAlbumAdapter);
    }

    @Override
    protected ImageView childViewShow() {
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
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo:
                startActivity(new Intent(this, AlbumSelectorActivity.class));
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
        isEdit=!isEdit;
        if (isEdit){
            mEdSetName.setText(mTvName.getText().toString().trim());
            mEtDes.setText(mTvDes.getText().toString().trim());
            if (editAlbumAdapter!=null) {
                EditAlbumHolder.canDelete = true;
                editAlbumAdapter.notifyDataSetChanged();
            }
            changeViewShow(isEdit);
        }else {
            name = mEdSetName.getText().toString().trim();
            if (StringUtils.isEmpty(name)){
                ToastUtils.showToast("相册名不能为空");
                isEdit=!isEdit;
                return;
            }
            des = mEtDes.getText().toString().trim();
            updateAlbum(name, des);
        }
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }
}
