package yunshuo.yneb.com.yunnantravel;


import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class MainActivity extends FragmentActivity {

    private ImageView mIvImage;
    private VideoView viewById;
    private VideoView mVv;
    private RelativeLayout mRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        startActivity(new Intent(this, Main2Activity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Event1 s) {
        System.out.println("输出了字符串+++" );
    }
}
