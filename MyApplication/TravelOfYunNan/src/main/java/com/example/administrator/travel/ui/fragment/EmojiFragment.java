package com.example.administrator.travel.ui.fragment;

import android.widget.GridView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.EmojiAdapter;

/**
 * Created by android on 2016/7/31.
 */
public class EmojiFragment extends BaseFragment {

    private GridView mGvEmoji;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_emoji;
    }

    @Override
    protected void initView() {
        mGvEmoji = (GridView) root.findViewById(R.id.gv_emoji);
    }

    @Override
    protected void initData() {
       mGvEmoji.setAdapter(new EmojiAdapter(getContext(),null));
    }

    @Override
    protected void initListener() {

    }
}
