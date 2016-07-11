package yunshuo.yneb.com.yunnantravel;

import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView viewById = (ListView) findViewById(R.id.lv);
        viewById.setAdapter(new SingleAdapter());

    }
    class SingleAdapter extends BaseAdapter{
      List<SingleView> singleViews=new ArrayList<>();
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final SingleHolder holder;
           if (convertView==null){
               holder=new SingleHolder();
               convertView=View.inflate(MainActivity.this, R.layout.click, null);
               holder.singleView= (SingleView) convertView.findViewById(R.id.single_view);
               convertView.setTag(holder);
           }else {
               holder= (SingleHolder) convertView.getTag();
           }
            holder.singleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (singleViews.size() > 0) {
                        singleViews.get(0).setTitle("取消");
                        singleViews.remove(0);
                    }
                    holder.singleView.setTitle("选中");
                    singleViews.add(holder.singleView);


                }
            });
            return convertView;
        }
        private void add(SingleView singleView){
            singleViews.add(singleView);
        }
    }
    static class SingleHolder{
        SingleView singleView;
    }
}
