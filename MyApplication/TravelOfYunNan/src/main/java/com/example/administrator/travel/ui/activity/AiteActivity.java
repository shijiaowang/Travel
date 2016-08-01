package com.example.administrator.travel.ui.activity;

import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AiteFollow;
import com.example.administrator.travel.ui.adapter.AiteAdapter;
import com.example.administrator.travel.utils.LogUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 * @联系人列表
 */
public class AiteActivity  extends BarBaseActivity {
    private String[] names={"1","麻辣鸡丝","刘","关羽","张","奇怪","和","阿","只","有","你","我","他","1","A","a","1"};
    private List<AiteFollow> followAndFans;

    private TextView mTvOk;
    private ListView mLvFollowPeople;
    private AiteAdapter adapter;
    private int selectPosition=0;


    @Override
    protected void initContentView() {
        ViewStub rightText = getmVsRightIcon();
        rightText.setLayoutResource(R.layout.activity_create_post_right_text);
        rightText.inflate();
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvOk.setText("确定");
        mLvFollowPeople = (ListView) findViewById(R.id.lv_follow_people);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_aite;
    }

    @Override
    protected void initEvent() {
      mLvFollowPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              AiteFollow followAndFan = followAndFans.get(position);
              if (followAndFan.isChecked()) {
                  followAndFan.setIsChecked(false);
                  if (selectPosition>0){
                      selectPosition--;
                      mTvOk.setText("确定("+selectPosition+")");
                  }
              }else {
                  selectPosition++;
                  mTvOk.setText("确定("+selectPosition+")");
                  followAndFan.setIsChecked(true);
              }
              adapter.notifyData(followAndFans);
          }

      });
    }

    @Override
    protected void initViewData() {
        followAndFans=new ArrayList<>();
        for (int i=0;i<names.length;i++) {
            AiteFollow aiteFollow = new AiteFollow();
            aiteFollow.setNikeName(names[i]);
            followAndFans.add(aiteFollow);
        }
        Collections.sort(followAndFans, new Comparator<AiteFollow>() {
            @Override
            public int compare(AiteFollow lhs, AiteFollow rhs){
                char first = lhs.getNikeName().charAt(0);
                char second = rhs.getNikeName().charAt(0);
                char firstCase = Pinyin.toPinyin(first).charAt(0);
                char secondCase=Pinyin.toPinyin(second).charAt(0);
                char firstIndex=isLowCaseAndChangeBigCase(firstCase);
                char secondIndex=isLowCaseAndChangeBigCase(secondCase);
                return firstIndex-secondIndex;
            }
        });
        for (AiteFollow aiteFollow:followAndFans){
            LogUtils.e(aiteFollow.getNikeName());
        }
        adapter = new AiteAdapter(this, followAndFans);
        mLvFollowPeople.setAdapter(adapter);
    }

    @Override
    protected String setTitleName() {
        return "关注的人";
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }

    @Override
    public float getAlpha() {
        return 1f;
    }
    /**
     * 小写转换为大写
     * @param c
     */
    public char isLowCaseAndChangeBigCase(char c) {
        char heightA='A';
        char a = 'a';
        char z = 'z';
        if (a <= c && c <= z) {
            String string = Character.toString(c);
            c = string.toUpperCase().charAt(0);
        }
        /**
         * 将数字和特殊字符放在最后
         */
        if (c < heightA) {
            c = '{';
        }
        return c;
    }


}
