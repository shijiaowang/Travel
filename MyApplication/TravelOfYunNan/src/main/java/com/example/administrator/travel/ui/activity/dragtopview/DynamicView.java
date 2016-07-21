/*
 * Copyright 2015 chenupt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.travel.ui.activity.dragtopview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Dynamic;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.LogUtils;

import java.util.List;

import github.chenupt.multiplemodel.BaseItemModel;
import github.chenupt.multiplemodel.ItemEntity;

/**
 * Created by chenupt@gmail.com on 2015/1/18.
 * Description TODO
 */
public class DynamicView extends BaseItemModel<Dynamic> {


    private TextView mTvType;

    public DynamicView(Context context) {
        super(context);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_dynamic, this, true);
        mTvType = (TextView) findViewById(R.id.tv_type);
        TextView  mTvType = FontsIconUtil.findIconFontsById(R.id.tv_icon,getContext(),this);

    }

    @Override
    public void bindView() {
        List<ItemEntity<Dynamic>> modelList = getModelList();
        ItemEntity<Dynamic> dynamicItemEntity = modelList.get(viewPosition);
        Dynamic content = dynamicItemEntity.getContent();
        String type = content.getType();
        mTvType.setText(type);
        mTvType.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("被点击了"+viewPosition);
            }
        });


    }
}
