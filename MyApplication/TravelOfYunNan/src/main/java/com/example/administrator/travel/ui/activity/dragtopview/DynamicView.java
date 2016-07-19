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
import android.widget.TextView;

import com.example.administrator.travel.R;

import github.chenupt.multiplemodel.BaseItemModel;

/**
 * Created by chenupt@gmail.com on 2015/1/18.
 * Description TODO
 */
public class DynamicView extends BaseItemModel<String> {


    public DynamicView(Context context) {
        super(context);
        onFinishInflate();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.item_fragment_dynamic, this, true);

    }

    @Override
    public void bindView() {

    }
}
