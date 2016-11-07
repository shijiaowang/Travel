package com.yunspeak.travel.ui.baseui;

import android.Manifest;
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
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.utils.BitmapUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.IOUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wangyang on 2016/10/28 0028.
 */

public abstract class BaseCutPhotoActivity<T extends HttpEvent> extends BaseNetWorkActivity<T> {
    protected static final int REQUEST_SELECT_PICTURE = 0x01;
    protected static final int TAKE_PHOTO = 0x02;
    protected String filename;
    protected String filenam2;
    protected static final String IMAGE_NAME = "CropImage";

    private Intent intent;//存放图片的uri
    private boolean needCrop=true;//默认需要裁剪
    private int currentCode=-1;

    public boolean isNeedCrop() {
        return needCrop;
    }

    public void setNeedCrop(boolean needCrop) {
        this.needCrop = needCrop;
    }


    /**
     * 弹出窗口
     */
    public void showPictureCutPop(View view) {
        // 获取弹出视图对象
        View viewPopup = View.inflate(this, R.layout.pop_cut_icon, null);
        // 创建 弹出窗口
        final PopupWindow window = new PopupWindow(viewPopup, DensityUtil.getScreenWidth(), DensityUtil.dip2px(151));
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        viewPopup.findViewById(R.id.tv_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
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
        window.showAtLocation(view, Gravity.TOP, 0, DensityUtil.getScreenHeight() - DensityUtil.dip2px(151));
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
        if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            currentCode=TAKE_PHOTO;
            startActivityForResult(intent, currentCode);
        }else {
            ToastUtils.showToast("SD卡不可用");
        }
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
            currentCode=REQUEST_SELECT_PICTURE;
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), currentCode);
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
        if (resultCode ==RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE ||requestCode == TAKE_PHOTO) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    if (!needCrop){
                        showImage(data);
                    }else {
                        startCropActivity(data.getData());
                    }
                } else {
                    Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }else if (resultCode==UCrop.RESULT_CHANGE){
            if (currentCode==TAKE_PHOTO){
                takePhoto();
            }else {
                pickFromGallery();
            }
        }
    }

    protected  void showImage(Intent data)  {

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
        options.setCompressionQuality(30);
        options.setMaxBitmapSize(400);//图片压缩
        options.setImageToCropBoundsAnimDuration(100);
        setOptions(options);

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
     * 子类修改选项
     * @param options
     */
    protected void setOptions(UCrop.Options options) {

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
            bmp.compress(Bitmap.CompressFormat.PNG, 60, fOut);
            flag = true;
            filename = url;
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    childDisplay("file://"+filename);
                }
            });


            bmp.recycle();
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

    protected abstract void childDisplay(String url);

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

                try {
                    Bitmap bitmap = null;
                    bitmap = BitmapUtils.getBitmapFormUri(this, resultUri,100);
                    if (bitmap == null) return;
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