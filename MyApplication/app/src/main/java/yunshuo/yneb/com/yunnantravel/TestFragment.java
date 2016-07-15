package yunshuo.yneb.com.yunnantravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class TestFragment extends Fragment {

    private View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = View.inflate(getActivity(), R.layout.te_view, null);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView viewById = (ListView) inflate.findViewById(R.id.lv_list);
        viewById.setAdapter(new MyAdapter());
        int i = ViewUtil.setListViewHeightBasedOnChildren1(viewById);
        System.out.println("list的高度为"+i);
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=View.inflate(getContext(),R.layout.item,null);
            }
            return convertView;
        }
    }
}
