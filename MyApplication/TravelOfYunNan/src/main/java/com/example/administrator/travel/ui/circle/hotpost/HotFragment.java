package com.example.administrator.travel.ui.circle.hotpost;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.PostActivity;
import com.example.administrator.travel.ui.adapter.CircleHotAdapter;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.internal.framed.Variant;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class HotFragment extends LoadBaseFragment {
    @BindView(R.id.lv_circle_hot) ListView mLvCircleHot;
    private View root;


    @Override
    protected Fragment registerEvent() {
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = View.inflate(getContext(), R.layout.fragment_circle_hot, null);
        ButterKnife.bind(this,root);
    }

    @Override
    protected View initView() {
        return root;
    }



    @Override
    protected void initListener() {
        mLvCircleHot.setAdapter(new CircleHotAdapter(getActivity(),null));
     mLvCircleHot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(), PostActivity.class));
            }
        });
    }


    @Override
    protected void onLoad() {

    }
    @Subscribe
   public void onEvent(String strin){

   }

}
