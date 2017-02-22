package com.yunspeak.travel.ui.find.hotel;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.CityNameBean;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.SlideCursorView;
import com.yunspeak.travel.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 酒店页面-预定酒店
 */
public class HotelIndexActivity extends BaseToolBarActivity {


    @BindView(R.id.tv_search)
    FontsIconTextView tvSearch;
    @BindView(R.id.et_search)
    AutoCompleteTextView etSearch;
    @BindView(R.id.rl_destination)
    RelativeLayout rlDestination;
    @BindView(R.id.rv_hotel_city_list)
    RecyclerView rvHotelCityList;
    @BindView(R.id.scv_hotel_city_index)
    SlideCursorView scvHotelCityIndex;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_hotel;
    }

    @Override
    protected void initOptions() {
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
        initCityList();


    }

    private void initCityList() {
        final List<CityNameBean> list = DBManager.queryAllCity();

        final Map<String,Integer> indexMap=new HashMap<>();
        final String[] name=new String[list.size()];
        for (int i=0;i<list.size();i++) {
            String index = list.get(i).getIndex();
            name[i]=list.get(i).getName();
            if (!indexMap.containsKey(index)){
                indexMap.put(index,i);
            }
        }
        final HotelIndexAdapter hotelIndexAdapter = new HotelIndexAdapter(list, this, new HotelIndexAdapter.LineCallBack() {
            @Override
            public boolean isNextGroup(int position) {
                if (position == list.size() - 1) return false;
                return !list.get(position).getIndex().equals(list.get(position + 1).getIndex());
            }
        });
        rvHotelCityList.setAdapter(hotelIndexAdapter);
        final LinearLayoutManager layout = new LinearLayoutManager(this);
        rvHotelCityList.setLayoutManager(layout);
        rvHotelCityList.addItemDecoration(new SectionDecoration(list, this, new SectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (position>0||position<list.size()-1){
                    return list.get(position).getIndex();
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (position>0||position<list.size()-1){
                    return list.get(position).getIndex();
                }
                return "";
            }
        }));
        scvHotelCityIndex.setWordSelectChangeListener(new SlideCursorView.WordSelectChangeListener() {
            @Override
            public void wordChange(char word) {
                Integer integer = indexMap.get(String.valueOf(word));
                if (integer==null || integer<0 || integer>list.size()-1){
                    return;
                }
                layout.scrollToPositionWithOffset(integer,0);

            }

            @Override
            public void wordCancel() {

            }
        });
        etSearch.setAdapter(new ArrayAdapter<String>(this,R.layout.item_array,name){ });
        etSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getAdapter().getItem(position);
                for (int i=0;i<name.length;i++){
                    if (item.equals(name[i])){
                        ToastUtils.showToast("您选择了"+i+"-"+item);
                    }
                }

            }
        });
    }

    private void search() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.select_city);
    }


}
