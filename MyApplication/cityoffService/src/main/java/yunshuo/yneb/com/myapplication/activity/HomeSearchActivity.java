package yunshuo.yneb.com.myapplication.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import yunshuo.yneb.com.myapplication.IVariable;
import yunshuo.yneb.com.myapplication.R;
import yunshuo.yneb.com.myapplication.other.adapter.SearchCommonAdapter;
import yunshuo.yneb.com.myapplication.other.bean.SearchUserBean;
import yunshuo.yneb.com.myapplication.other.event.HomeSearchEvent;
import yunshuo.yneb.com.myapplication.other.utils.GsonUtils;
import yunshuo.yneb.com.myapplication.other.utils.MapUtils;
import yunshuo.yneb.com.myapplication.other.utils.StringUtils;
import yunshuo.yneb.com.myapplication.other.utils.ToastUtils;
import yunshuo.yneb.com.myapplication.other.utils.XEventUtils;
import yunshuo.yneb.com.myapplication.other.view.FontsIconTextView;

/**
 * Created by wangyang on 2016/8/22 0022.
 * 首页搜索
 */
public class HomeSearchActivity extends BaseEventBusActivity<HomeSearchEvent> implements View.OnClickListener {
    EditText etSearch;
    FontsIconTextView tvSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvUser;
    List<SearchUserBean.DataBean> dataBeen=new ArrayList<>();
    private SearchCommonAdapter searchCommonAdapter;


    @Override
    protected int initLayoutRes() {
        return R.layout.home_search;
    }


    @Override
    protected void onSuccess(HomeSearchEvent homeSearchEvent) {
        setIsProgress(false);
        if (homeSearchEvent.getCode()==2){
            ToastUtils.showToast("没有发现用户");
        }else {
            SearchUserBean object = GsonUtils.getObject(homeSearchEvent.getResult(), SearchUserBean.class);
            SearchUserBean.DataBean data = object.getData();
            if (StringUtils.isEmpty(data.getId())){
                ToastUtils.showToast("没有发现用户");
                return;
            }
             dataBeen.clear();
             dataBeen.add(data);
            if (searchCommonAdapter==null) {
                searchCommonAdapter = new SearchCommonAdapter(dataBeen, this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                rvUser.setAdapter(searchCommonAdapter);
                rvUser.setLayoutManager(linearLayoutManager);
            }
            else {
                searchCommonAdapter.notifyDataSetChanged();

            }
        }

    }

    @Override
    protected void onFail(HomeSearchEvent homeSearchEvent) {
        super.onFail(homeSearchEvent);
        ToastUtils.showToast("没有发现用户");
    }

    @Override
    protected void initEvent() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_search, mToolbar,false);
        tvSearch = (FontsIconTextView) inflate.findViewById(R.id.tv_search);
        etSearch = (EditText) inflate.findViewById(R.id.et_search);
        mToolbar.addView(inflate);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //一般输入法或搜狗输入法点击搜索按键
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    //这里调用搜索方法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0); //强制隐藏键盘
                    search();
                    return true;
                }
                return false;
            }
        });
        tvSearch.setOnClickListener(this);
    }

    @Override
    protected String initTitle() {
        mTvTitle.setVisibility(View.GONE);
        return "";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                search();
                break;
        }
    }

    private void search() {
        if (StringUtils.isEmpty(getString(etSearch))){
            ToastUtils.showToast("请输入搜索的内容");
        }
        Map<String, String> user_id = MapUtils.Build().addKey().add("user_id", getString(etSearch)).end();
        XEventUtils.postUseCommonBackJson(IVariable.SEARCH_BY_ID,user_id,0,new HomeSearchEvent());
        setIsProgress(true);
    }

}
