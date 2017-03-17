package com.yunspeak.travel.ui.circle.hot.model;

import com.yunspeak.travel.ui.baseui.DataBindingSetRecycleAdapter;
import com.yunspeak.travel.ui.baseui.SetRecycleComponent;

/**
 * Created by wangyang on 2017/3/17.
 */

public class HotComponent implements SetRecycleComponent {
    HotRecycleModel hotRecycleModel= new HotRecycleModel();
    @Override
    public DataBindingSetRecycleAdapter getDataBindingSetRecycleAdapter() {
        return hotRecycleModel;
    }
}
