package com.example.administrator.travel.ui.appoint.travelplan;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.ui.view.GradientTextView;
import com.example.administrator.travel.utils.BitmapUtils;
import com.example.administrator.travel.utils.IOUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by android on 2016/9/22.
 */
public  class TravelsPlanBaseActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.vs_content)
    private ViewStub mVsContent;
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @ViewInject(R.id.tv_start)
    private TextView mTvStart;
    @ViewInject(R.id.tv_end)
    private TextView mTvEnd;
    @ViewInject(R.id.tv_end_time)
    private TextView mTvEndTime;
    @ViewInject(R.id.tv_start_time)
    private TextView mTvStartTime;
    @ViewInject(R.id.tv_how_day)
    private TextView mTvHowDay;
    @ViewInject(R.id.tv_start_morning)
    private GradientTextView mTvStartMorning;
    @ViewInject(R.id.tv_start_night)
    private GradientTextView mTvStartNight;
    @ViewInject(R.id.tv_end_morning)
    private GradientTextView mTvEndMorning;
    @ViewInject(R.id.tv_end_night)
    private GradientTextView mTvEndNight;
    @ViewInject(R.id.rl_start_morning)
    private RelativeLayout mRlStartMorning;
    @ViewInject(R.id.rl_start_night)
    private RelativeLayout mRlStartNight;
    @ViewInject(R.id.rl_end_morning)
    private RelativeLayout mRlEndMorning;
    @ViewInject(R.id.rl_end_night)
    private RelativeLayout mRlEndNight;
    @ViewInject(R.id.tv_icon)
    private TextView mTvIcon;
    @ViewInject(R.id.rl_icon)
    private RelativeLayout mRlIcon;
    @ViewInject(R.id.iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.tv_delete)
    private TextView mTvDelete;
    private Intent intent;//存放图片的uri
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final String IMAGE_NAME = "CropImage";
    private String filename;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_plan_base;
    }

    @Override
    protected void initEvent() {
        mTvIcon.setOnClickListener(this);
       /* initChildLayoutRes();
        initChildEvent();*/
    }

   /* protected abstract void initChildLayoutRes();

    protected abstract void initChildEvent();*/


    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_icon:
                showPictureCutPop();
                break;
        }
    }

    /**
     * 弹出窗口
     */
    public void showPictureCutPop() {
        // 获取弹出视图对象
        View viewPopup = View.inflate(this, R.layout.pop_cut_icon, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(151));
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        viewPopup.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewPopup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
        viewPopup.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        // 响应 视图外的地方 点击关闭当前
        window.setOutsideTouchable(true);
        // 响应返回键的关闭
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        // 获取控件的坐标 x y
        window.showAtLocation(mBtNext, Gravity.TOP, 0, DensityUtil.getScreenHeight() - DensityUtil.dip2px(151));
    }

    /**
     * 相册选者图片
     */
    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }

    /**
     * 请求相关权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                }
                break;
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (intent==null)return;
                    handleCropResult(intent);
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    /**
     * 反馈错误信息
     * @param result
     */
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动裁剪页面
     * @param uri
     */
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = IMAGE_NAME+".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }
    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    /**
     * 配置常用属性
     * @param uCrop
     * @return
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.useSourceImageAspectRatio();
        options.setShowCropGrid(false);
        options.setShowCropFrame(false);
        options.setActiveWidgetColor(getResources().getColor(R.color.otherTitleBg));
        options.setToolbarColor(getResources().getColor(R.color.otherTitleBg));
        options.setStatusBarColor(getResources().getColor(R.color.otherTitleBg));
        options.setAspectRatioOptions(0,new AspectRatio("1",1,1));

        /*
        If you want to configure how gestures work for all UCropActivity tabs

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        * */

        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.

        options.setMaxBitmapSize(640);
        * */


       /*

        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧

        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);

        // Color palette
        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
		options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));

		// Aspect ratio options
        options.setAspectRatioOptions(1,
            new AspectRatio("WOW", 1, 2),
            new AspectRatio("MUCH", 3, 4),
            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
            new AspectRatio("SO", 16, 9),
            new AspectRatio("ASPECT", 1, 1));

       */

        return uCrop.withOptions(options);
    }
    /**
     * 将截图存在本地
     *
     * @param b
     */
    private void saveCroppedImage(final Bitmap b) {

        if (b != null) {
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    final String downloadsDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    final String saveName = System.currentTimeMillis() + "icon.jpg";
                    x.task().run(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                saveMyBitmap(b, downloadsDirectoryPath, saveName);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    ToastUtils.showToast("SD卡未挂载！");
                    filename = null;
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.toast_unexpected_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将bitmap写入文件，之后方便上传
     *
     * @param bmp
     * @param path
     * @param bitName
     * @return
     * @throws IOException
     */
    public boolean saveMyBitmap(Bitmap bmp, String path, String bitName) throws IOException {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        String url = path + "/" + bitName;
        File file = new File(url);
        boolean flag = false;
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            flag = true;
            filename = url;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (fOut != null) {
                fOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        IOUtils.close(fOut);
        return flag;
    }

    /**
     * 处理裁剪图片  保存压缩
     *
     * @param result
     */
    private void handleCropResult(@NonNull Intent result) {
        intent = result;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
           Uri resultUri = UCrop.getOutput(result);
            if (resultUri != null) {
                mRlIcon.setVisibility(View.VISIBLE);
                mTvIcon.setVisibility(View.GONE);
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapUtils.getBitmapFormUri(this, resultUri);
                    if (bitmap == null) return;
                    //将图片保存在本地
                    mIvIcon.setImageBitmap(bitmap);

                    mTvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mRlIcon.setVisibility(View.GONE);
                            mTvIcon.setVisibility(View.VISIBLE);
                        }
                    });
                    saveCroppedImage(bitmap);//保存图片到本地

                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast("未解析到图片");
                }

            } else {
                Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
