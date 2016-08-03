package com.example.administrator.travel.ui.activity;


import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AiteFollow;
import com.example.administrator.travel.ui.adapter.AiteAdapter;
import com.example.administrator.travel.ui.view.FastQueryIndex;
import com.example.administrator.travel.utils.FontsIconUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1 0001.
 * @联系人列表
 */
public class AiteActivity  extends BarBaseActivity {
    private String[] names={"1","麻辣鸡丝","刘","关羽","张","奇怪","和","阿","只","有","你","我","他","1","A","a","1","阿","只","有","你","我","他","1","A","阿","只","有","你","我","他","1","A"};
    private List<AiteFollow> followAndFans;

    private TextView mTvOk;
    private ListView mLvFollowPeople;
    private AiteAdapter adapter;
    private int selectPosition=0;
    private FastQueryIndex mFqiIndex;
    private List<String> indexList;
    private TextView mTvSearch;


    @Override
    protected void initContentView() {
        ViewStub rightText = getmVsRightIcon();
        rightText.setLayoutResource(R.layout.activity_right_common_text);
        rightText.inflate();
        mTvOk = (TextView) findViewById(R.id.tv_ok);
        mTvOk.setText("确定");
        mLvFollowPeople = (ListView) findViewById(R.id.lv_follow_people);
        mFqiIndex = (FastQueryIndex) findViewById(R.id.fqi_index);
        mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_aite;
    }

    @Override
    protected void initEvent() {
        mFqiIndex.setOnItemClickListener(new FastQueryIndex.OnItemClickListener() {
            @Override
            public void onClickWord(char c) {
                queryAndSmooth(c);
            }
        });
      mLvFollowPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              AiteFollow followAndFan = followAndFans.get(position);
              if (followAndFan.isChecked()) {
                  followAndFan.setIsChecked(false);
                  if (selectPosition > 0) {
                      selectPosition--;
                      mTvOk.setText("确定(" + selectPosition + ")");
                  }
              } else {
                  selectPosition++;
                  mTvOk.setText("确定(" + selectPosition + ")");
                  followAndFan.setIsChecked(true);
              }
              adapter.notifyData(followAndFans);
          }

      });
    }

    /**
     * 查询并且滑动到需要的地方
     * @param c
     */
    private void queryAndSmooth(char c) {
        if (c=='*'){
            mLvFollowPeople.setSelection(0);
            return;
        }
        for (int i=0;i<followAndFans.size();i++){
            AiteFollow aiteFollow = followAndFans.get(i);
            if (c=='#'){
                if (aiteFollow.getIndexChar()>='{'){//与之前的bean对象有关
                    mLvFollowPeople.setSelection(i);
                    break;
                }
            }
            if (aiteFollow.getIndexChar()==c){
                mLvFollowPeople.setSelection(i);
                break;
            }
        }

    }

    @Override
    protected void initViewData() {
        initDataAndSort();
        initIndexQuery();
        adapter = new AiteAdapter(this, followAndFans);
        mLvFollowPeople.setAdapter(adapter);
    }

    private void initDataAndSort() {
        followAndFans=new ArrayList<>();
        for (int i=0;i<names.length;i++) {
            AiteFollow aiteFollow = new AiteFollow();
            aiteFollow.setNikeName(names[i]);
            followAndFans.add(aiteFollow);
        }
        Collections.sort(followAndFans, new Comparator<AiteFollow>() {
            @Override
            public int compare(AiteFollow lhs, AiteFollow rhs) {
                char firstIndex = lhs.getIndexChar();
                char secondIndex = rhs.getIndexChar();
                return firstIndex - secondIndex;
            }
        });
    }

    private void initIndexQuery() {
        indexList = new ArrayList<>();
        indexList.add("*");//第一个默认
        //排好序后获得有序索引
        for (AiteFollow aiteFollow:followAndFans){
            String index;
            char indexChar = aiteFollow.getIndexChar();
            if (indexChar>='{'){//与之前的排序设置有关，bean对象中的
                index="#";
            }else {
                index=String.valueOf(indexChar);
            }

            if (!indexList.contains(index)){
                indexList.add(index);
            }
        }
        //完毕后，初始化所以列表
        mFqiIndex.initWordIndex(indexList);
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
}
