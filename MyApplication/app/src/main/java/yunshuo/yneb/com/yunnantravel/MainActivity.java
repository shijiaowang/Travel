package yunshuo.yneb.com.yunnantravel;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import net.robinx.lib.blur.utils.BlurUtils;
import net.robinx.lib.blur.widget.BlurDrawable;


public class MainActivity extends FragmentActivity {

	private ImageView mIvImage;
	private VideoView viewById;
	private VideoView mVv;
	private RelativeLayout mRl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mVv = (VideoView) findViewById(R.id.vv);
		mRl = (RelativeLayout) findViewById(R.id.tv_bg);
		Uri parse = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.entrance_video);
		mVv.setVideoURI(parse);
		mVv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mVv.start();
			}
		});
		mVv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVv.start();
				BlurDrawable blurDrawable = new BlurDrawable(MainActivity.this);
				blurDrawable.setDrawOffset(0,50);
				blurDrawable.setCornerRadius(10);
				blurDrawable.setBlurRadius(25);
				blurDrawable.setOverlayColor(Color.parseColor("#64ffffff"));
				mRl.setBackgroundDrawable(blurDrawable);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(15);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						ViewCompat.postInvalidateOnAnimation(mRl);
					}
				}).start();
			}
		});

	}
}
