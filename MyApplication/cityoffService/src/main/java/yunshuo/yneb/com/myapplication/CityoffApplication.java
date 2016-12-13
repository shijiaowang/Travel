package yunshuo.yneb.com.myapplication;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import org.xutils.x;

import yunshuo.yneb.com.myapplication.other.utils.UIUtils;

/**
 * Created by wangyang on 2016/12/13 0013.
 */

public class CityOffApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfigFactory.getImagePipelineConfig(this);
        Fresco.initialize(this,imagePipelineConfig);
        YunSpeakHelper.getInstance().init(this);
        UIUtils.init(this);
    }
}
