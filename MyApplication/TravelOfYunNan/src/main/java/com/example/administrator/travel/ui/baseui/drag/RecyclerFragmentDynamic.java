package com.example.administrator.travel.ui.baseui.drag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.DragEvent;

import org.greenrobot.eventbus.EventBus;

import github.chenupt.dragtoplayout.Logger;
import github.chenupt.multiplemodel.recycler.ModelRecyclerAdapter;

/**
 * @ClassName:
 * @Description:
 * @author bohe
 * @date 2016/4/28 14:01
 */
public class RecyclerFragmentDynamic extends RecyclerBaseFragment {

    private static String TAG = RecyclerFragmentDynamic.class.getSimpleName();
    private RecyclerView recyclerView;

    public static RecyclerFragmentDynamic newInstance(String name) {
        RecyclerFragmentDynamic fragment = new RecyclerFragmentDynamic();
        TAG = name;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_other_dynamic, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.lv_dynamic);
        // init recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ModelRecyclerAdapter adapter = new ModelRecyclerAdapter(getActivity(), DataService.getInstance().getModelManager());
        adapter.setList(DataService.getInstance().getList());
        recyclerView.setAdapter(adapter);
        // set data source
        adapter.notifyDataSetChanged();

        // TODO: 设置recyclerView，方便Ay中对recyclerView进行控制
        setRecyclerView(recyclerView);
        // attach top listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Logger.e("TAG= ", TAG + ", getShouldDelegateTouch= " + getShouldDelegateTouch());
                EventBus.getDefault().post(new DragEvent(0, getShouldDelegateTouch()));
            }
        });

    }
}


