package com.yunspeak.travel.ui.baseui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.util.PathUtil;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.ui.me.myalbum.editalbum.albumselector.UpPhotoEvent;
import com.yunspeak.travel.utils.BitmapUtils;
import com.yunspeak.travel.utils.IOUtils;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by wangyang on 2016/10/28 0028.
 */

public abstract class BaseCutPhotoActivity<T extends HttpEvent> extends BaseNetWorkActivity<T> {
    protected static final int REQUEST_SELECT_PICTURE = 0x01;
    protected static final int TAKE_PHOTO = 0x02;
    protected String filename = "";
    protected static final String IMAGE_NAME = "CropImage";
    private Intent intent;//存放图片的uri
    private boolean needCrop = true;//默认需要裁剪
    private int currentCode = -1;
    private File cameraFile;

    public boolean isNeedCrop() {
        return needCrop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            cameraFile = ((File) savedInstanceState.getSerializable("file"));
            currentCode = savedInstanceState.getInt("code");
            filename = savedInstanceState.getString("filename");
        }
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
                window.dismiss();
            }
        });
        viewPopup.findViewById(R.id.tv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
                window.dismiss();
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("filename", filename);
        savedInstanceState.putInt("code", currentCode);
        savedInstanceState.putSerializable("file", cameraFile);
        super.onSaveInstanceState(savedInstanceState); //实现父类方法 放在最后 防止拍照后无法返回当前activity
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        String state = Environment.getExternalStorageState(); //拿到sdcard是否可用的状态码
        if (state.equals(Environment.MEDIA_MOUNTED)) {   //如果可用

            cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                    + System.currentTimeMillis() + ".jpg");
            //noinspection ResultOfMethodCallIgnored
            cameraFile.getParentFile().mkdirs();
            currentCode = TAKE_PHOTO;
            startActivityForResult(
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                    currentCode);
        } else {
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
            Intent intent;
            currentCode = REQUEST_SELECT_PICTURE;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image");
            } else {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            startActivityForResult(intent, currentCode);
            /*GlobalValue.size = 1;
            startActivity(new Intent(this, AlbumSelectorActivity.class));*/
        }
    }

    @Subscribe
    public void onEvent(UpPhotoEvent upPhotoEvent) {
        List<String> list = upPhotoEvent.getList();
        if (list == null || list.size() == 0) {
            ToastUtils.showToast("未接收到图片");
            return;
        }
        String path = list.get(0);
        if (needCrop) {
            startCropActivity(Uri.parse(path));
        } else {
            showImage(path);
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
                    if (intent == null) return;
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
            if (requestCode == TAKE_PHOTO) {
                if (cameraFile != null && cameraFile.exists()) {
                    if (!needCrop) {
                        showImage("file://" + cameraFile.getAbsolutePath());
                    } else {
                        startCropActivity(Uri.fromFile(cameraFile));
                    }
                }
            } else if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    if (!needCrop) {
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedUri, filePathColumn, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            if (picturePath == null || picturePath.equals("null")) {
                                ToastUtils.showToast("图片不存在");
                                return;
                            }
                            showImage("file://" + picturePath);
                        }
                    } else {
                        startCropActivity(data.getData());
                    }
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            } else {
                Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        } else if (resultCode == UCrop.RESULT_CHANGE) {
            if (currentCode == TAKE_PHOTO) {
                takePhoto();
            } else {
                pickFromGallery();
            }
        }
    }


    protected void showImage(String data) {

    }

    /**
     * 反馈错误信息
     *
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
     *
     * @param uri
     */
    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = IMAGE_NAME + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = advancedConfig(uCrop);

        userResultSize(uCrop, 300, 300);

    }

    protected void userResultSize(UCrop uCrop, int width, int height) {
        uCrop.withMaxResultSize(width, height).start(this);
    }
    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    /**
     * 配置常用属性
     *
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
        options.setAspectRatioOptions(0, new AspectRatio("1", 1, 1));
        options.setCompressionQuality(80);
        options.setMaxBitmapSize(800);//图片压缩
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
     *
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
                    final String downloadsDirectoryPath = getExternalStorageDirectory().getAbsolutePath();
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
        final File file = new File(url);
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
                    childDisplay("file://" + filename, filename);
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

    protected void childDisplay(String url, String filename) {

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

                try {
                    Bitmap bitmap = BitmapUtils.getBitmapFormUri(this, resultUri, 100);
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
