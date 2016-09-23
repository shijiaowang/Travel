package com.example.administrator.travel.ui.appoint.aite;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.view.FastQueryIndex;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/1 0001.
 *
 * @联系人列表
 */
public class AiteActivity extends LoadingBarBaseActivity {
    private List<AiteFollow> followAndFans;
    private List<AiteFollow> mSelectPeople;
    private TextView mTvOk;
    @ViewInject(R.id.lv_follow_people)
    private ListView mLvFollowPeople;
    private AiteAdapter adapter;
    @ViewInject(R.id.fqi_index)
    private FastQueryIndex mFqiIndex;
    private List<String> indexList;
    private TextView mTvSearch;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_aite;
    }

    @Override
    protected void initEvent() {
        init();
        mFqiIndex.setOnItemClickListener(new FastQueryIndex.OnItemClickListener() {
            @Override
            public void onClickWord(char c) {
                queryAndSmooth(c);
            }
        });
        mTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                if (mSelectPeople!=null) {
                    intent.putExtra(IVariable.DATA, (Serializable) mSelectPeople);
                }
                setResult(RESULT_CODE,intent);
                finish();
            }
        });
        mLvFollowPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectPeople==null)mSelectPeople=new ArrayList<>();
                AiteFollow followAndFan = followAndFans.get(position);
                if (followAndFan.isChecked()) {
                    mSelectPeople.remove(followAndFan);
                } else {
                    mSelectPeople.add(followAndFan);
                }
                followAndFan.setIsChecked(!followAndFan.isChecked());
                mTvOk.setText(getString(R.string.sure_number,mSelectPeople.size()));
                adapter.notifyData(followAndFans);
            }

        });
    }

    @Override
    protected void onLoad() {
        Map<String, String> aiteMap = MapUtils.Build().addKey(this).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.GET_FOLLOW_USER,aiteMap,0,new AiteEvent());
    }

    private void init() {
        try {
            mSelectPeople = (List<AiteFollow>) getIntent().getSerializableExtra(IVariable.DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTvOk = getmTvRightIcon();
        mTvOk.setText("确定");
        mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);
    }

    /**
     * 查询并且滑动到需要的地方
     *
     * @param c
     */
    private void queryAndSmooth(char c) {
        if (c == '*') {
            mLvFollowPeople.setSelection(0);
            return;
        }
        for (int i = 0; i < followAndFans.size(); i++) {
            AiteFollow aiteFollow = followAndFans.get(i);
            if (c == '#') {
                if (aiteFollow.getIndexChar() >= '{') {//与之前的bean对象有关
                    mLvFollowPeople.setSelection(i);
                    break;
                }
            }
            if (aiteFollow.getIndexChar() == c) {
                mLvFollowPeople.setSelection(i);
                break;
            }
        }

    }

    @Override
    protected Activity initViewData() {

        return this;
    }

    private void initDataAndSort(List<Follow> data) {
        followAndFans = new ArrayList<>();

        for (Follow follow:data) {
            AiteFollow aiteFollow=null;
            if (mSelectPeople!=null){
                aiteFollow = changeOldData(follow);
            }
            if (aiteFollow==null){
                aiteFollow = new AiteFollow();
                aiteFollow.setFollow(follow);
            }
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

    /**
     * 如果再次进入邀请好友，就将之前的旧数据替换为最新获取的数据，并且将选中状态赋值给新数据
     * @param follow
     * @return
     */
    private AiteFollow changeOldData(Follow follow) {
        AiteFollow aiteFollow=null;
        for (AiteFollow aiteFollow1:mSelectPeople){
            if (aiteFollow1.equalsFollow(follow)){
                aiteFollow1.setFollow(follow);
                aiteFollow=aiteFollow1;
            }
        }
        return aiteFollow;
    }

    @Subscribe
    public void onEvent(AiteEvent event){
        setIsProgress(false);
         if (event.isSuccess()){
             dealData(event);
         }else {
             ToastUtils.showToast(event.getMessage());
             setIsError(true);
         }
    }

    private void dealData(AiteEvent event) {
        AiteBean aiteBean = GsonUtils.getObject(event.getResult(), AiteBean.class);
        List<Follow> data = aiteBean.getData();
        if (data==null || data.size()==0){
            ToastUtils.showToast("您尚未关注任何人！");
        }else {
            initDataAndSort(data);
            initIndexQuery();
            adapter = new AiteAdapter(this, followAndFans);
            mLvFollowPeople.setAdapter(adapter);
        }

    }

    private void initIndexQuery() {
        indexList = new ArrayList<>();
        indexList.add("*");//第一个默认
        //排好序后获得有序索引
        for (AiteFollow aiteFollow : followAndFans) {
            String index;
            char indexChar = aiteFollow.getIndexChar();
            if (indexChar >= '{') {//与之前的排序设置有关，bean对象中的
                index = "#";
            } else {
                index = String.valueOf(indexChar);
            }

            if (!indexList.contains(index)) {
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
    public float getAlpha() {
        return 1f;
    }
}
