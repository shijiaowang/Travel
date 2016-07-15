package yunshuo.yneb.com.yunnantravel;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ViewGroup;
import android.view.ViewParent;


import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<LLTest> list =new ArrayList();
    private ViewPager vp_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      vp_pager = (ViewPager) findViewById(R.id.vp_pager);
        ViewGroup.LayoutParams layoutParams = vp_pager.getLayoutParams();
        layoutParams.height=4207;
        //System.out.println(layoutParams.height+"");
        vp_pager.setLayoutParams(layoutParams);
        vp_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new TestFragment();
            }

            @Override
            public int getCount() {
                return 3;
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();
        int height = vp_pager.getLayoutParams().height;
        System.out.println("height"+height);
    }
}
