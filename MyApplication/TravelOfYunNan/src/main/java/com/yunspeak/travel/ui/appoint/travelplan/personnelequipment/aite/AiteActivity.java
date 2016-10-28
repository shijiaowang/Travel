package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.aite;


import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseNetWorkActivity;
import com.yunspeak.travel.ui.view.FastQueryIndex;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ToastUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/1 0001.
 *
 * @联系人列表
 */
public class AiteActivity extends BaseNetWorkActivity<AiteEvent> {
    @BindView(R.id.tv_search) FontsIconTextView tvSearch;
    @BindView(R.id.rl_search) RelativeLayout rlSearch;
    @BindView(R.id.lv_follow_people) ListView lvFollowPeople;
    @BindView(R.id.fqi_index) FastQueryIndex fqiIndex;
    private List<AiteFollow> followAndFans;
    private List<AiteFollow> mSelectPeople;
    private AiteAdapter adapter;
    private List<String> indexList;


    @Override
    protected void initEvent() {
        init();
        fqiIndex.setOnItemClickListener(new FastQueryIndex.OnItemClickListener() {
            @Override
            public void onClickWord(char c) {
                queryAndSmooth(c);
            }
        });
        lvFollowPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mSelectPeople == null) mSelectPeople = new ArrayList<>();
                AiteFollow followAndFan = followAndFans.get(position);
                if (followAndFan.isChecked()) {
                    mSelectPeople.remove(followAndFan);
                } else {
                    mSelectPeople.add(followAndFan);
                }
                followAndFan.setIsChecked(!followAndFan.isChecked());
                item.setTitle(getString(R.string.sure_number, mSelectPeople.size()));
                adapter.notifyData(followAndFans);
            }

        });
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        super.otherOptionsItemSelected(item);
        Intent intent = new Intent();
        if (mSelectPeople != null) {
            intent.putExtra(IVariable.DATA, (Serializable) mSelectPeople);
        }
        setResult(RESULT_CODE, intent);
        finish();

    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return IVariable.GET_FOLLOW_USER;
    }

    private void init() {
        try {
            mSelectPeople = (List<AiteFollow>) getIntent().getSerializableExtra(IVariable.DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String initRightText() {
        return "确定";
    }

    /**
     * 查询并且滑动到需要的地方
     *
     * @param c
     */
    private void queryAndSmooth(char c) {
        if (c == '*') {
            lvFollowPeople.setSelection(0);
            return;
        }
        for (int i = 0; i < followAndFans.size(); i++) {
            AiteFollow aiteFollow = followAndFans.get(i);
            if (c == '#') {
                if (aiteFollow.getIndexChar() >= '{') {//与之前的bean对象有关
                    lvFollowPeople.setSelection(i);
                    break;
                }
            }
            if (aiteFollow.getIndexChar() == c) {
                lvFollowPeople.setSelection(i);
                break;
            }
        }

    }


    private void initDataAndSort(List<Follow> data) {
        followAndFans = new ArrayList<>();

        for (Follow follow : data) {
            AiteFollow aiteFollow = null;
            if (mSelectPeople != null) {
                aiteFollow = changeOldData(follow);
            }
            if (aiteFollow == null) {
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
     *
     * @param follow
     * @return
     */
    private AiteFollow changeOldData(Follow follow) {
        AiteFollow aiteFollow = null;
        for (AiteFollow aiteFollow1 : mSelectPeople) {
            if (aiteFollow1.equalsFollow(follow)) {
                aiteFollow1.setFollow(follow);
                aiteFollow = aiteFollow1;
            }
        }
        return aiteFollow;
    }


    private void dealData(AiteEvent event) {
        AiteBean aiteBean = GsonUtils.getObject(event.getResult(), AiteBean.class);
        List<Follow> data = aiteBean.getData();
        if (data == null || data.size() == 0) {
            ToastUtils.showToast("您尚未关注任何人！");
        } else {
            initDataAndSort(data);
            initIndexQuery();
            adapter = new AiteAdapter(this, followAndFans);
            lvFollowPeople.setAdapter(adapter);
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
        fqiIndex.initWordIndex(indexList);
    }


    @Override
    protected void onSuccess(AiteEvent aiteEvent) {
        dealData(aiteEvent);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_aite;
    }

    @Override
    protected String initTitle() {
        return "关注的人";
    }
}
