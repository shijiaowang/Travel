package com.example.administrator.travel.ui.appoint.popwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.travel.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20 0020.
 */
public class SpinnerPopAdapter extends BaseAdapter {

    private List<SpinnerBean> strings;
    private Context context;

    public SpinnerPopAdapter(List<SpinnerBean> strings, Context context) {
        this.strings = strings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public SpinnerBean getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StringHolder stringHolder;
        if (convertView==null){
            stringHolder=new StringHolder();
            convertView=View.inflate(context, R.layout.item_pop_spinner,null);
            stringHolder.mTvText= ((TextView) convertView.findViewById(R.id.tv_type));
            convertView.setTag(stringHolder);
        }else {
            stringHolder=((StringHolder) convertView.getTag());
        }
        stringHolder.mTvText.setText(strings.get(position).getType());
        return convertView;
    }
    public static class StringHolder{
        private TextView mTvText;
    }
}
