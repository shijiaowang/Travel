package com.yunspeak.travel.ui.find.hotel.hotellist.hoteldetail.hotelorder;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2017/3/1.
 * 酒店订单联系人信息
 */

public class HotelOrderPeopleAdapter extends BaseRecycleViewAdapter<String> {
   private HashMap<Integer,String> contacts=new HashMap<>();

    public HotelOrderPeopleAdapter(List<String> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotelOrderPeopleHolder(inflateView(R.layout.item_hotel_order_contacts, parent));
    }

    class HotelOrderPeopleHolder extends BaseRecycleViewHolder<String> {
        @BindView(R.id.ed_hotel_contacts_name)
        EditText edHotelContactsName;
        @BindView(R.id.v_line)
        View view;
        public HotelOrderPeopleHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(final int position, String data, Context mContext) {
            view.setVisibility(position==0?View.GONE:View.VISIBLE);
            edHotelContactsName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   contacts.put(position,edHotelContactsName.getText().toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

}
