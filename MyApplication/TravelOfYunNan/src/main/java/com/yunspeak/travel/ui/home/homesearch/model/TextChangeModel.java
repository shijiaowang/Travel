package com.yunspeak.travel.ui.home.homesearch.model;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.ObservableField;

import com.yunspeak.travel.ui.view.SearchView;

/**
 * Created by wangyang on 2017/4/5.
 * 设置edittext 双向绑定
 */

public class TextChangeModel {
    public ObservableField<String> text =new ObservableField<>("");

    @BindingAdapter("text")
    public static void setChangeText(SearchView searchView,String text){
        if (!searchView.getTrim().equals(text)) {
            searchView.setText(text);
        }
    }
    @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
    public static String getChangeText(SearchView view) {
        return view.getTrim();
    }

    @BindingAdapter(value = {"textChangeListener", "textAttrChanged"}, requireAll = false)
    public static void setOnLoadingMoreListener(final SearchView view,
                                                final SearchView.TextChangeListener listener,
                                                final InverseBindingListener loadingAttrChanged) {

        SearchView.TextChangeListener newValue = new SearchView.TextChangeListener() {

            @Override
            public void onTextChange(String text) {
                if (listener != null) {
                    listener.onTextChange(text);
                }
                if (loadingAttrChanged != null) {
                    loadingAttrChanged.onChange();
                }
            }


        };
        view.setTextChangeListener(newValue);
    }
    public SearchView.TextChangeListener onTextChangeListener() {
        return textChangeListener;
    }
    SearchView.TextChangeListener textChangeListener = new SearchView.TextChangeListener() {
        @Override
        public void onTextChange(String changeText) {
            if (!text.get().equals(changeText)) {
                text.set(changeText);
            }
        }
    };
}
