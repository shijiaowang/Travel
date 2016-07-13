package yunshuo.yneb.com.yunnantravel;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<LLTest> list =new ArrayList();
    private ImageView iv_url;
    private ImageLoader instance;
    private TextView tv_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = ImageLoader.getInstance();
        instance.init(ImageLoaderConfiguration.createDefault(MainActivity.this));
        iv_url = (ImageView) findViewById(R.id.iv_url);
        tv_text = (TextView) findViewById(R.id.tv_text);


    }

    @Override
    protected void onStart() {
        super.onStart();
        String url="http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfa9e17a9a1b08f8c5494ee7b01.jpg";
        instance.displayImage(url,iv_url, DisplayImageOptions.createSimple(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                tv_text.setVisibility(View.VISIBLE);
            }


            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                tv_text.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                tv_text.setVisibility(View.GONE);
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String s, View view, int i, int i1) {
                tv_text.setText(i/i1+"%");
            }
        });
    }
}
