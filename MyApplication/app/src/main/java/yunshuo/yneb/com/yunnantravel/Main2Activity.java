package yunshuo.yneb.com.yunnantravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EventBus.getDefault().register(this);
        startActivity(new Intent(this, Main22Activity.class));
    }
    @Subscribe
    public void onEvent(Event0 event) {
        System.out.println("输出了数字+++");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        System.out.println("销毁了");
    }

}
