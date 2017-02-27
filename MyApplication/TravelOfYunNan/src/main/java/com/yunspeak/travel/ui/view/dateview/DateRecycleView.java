package com.yunspeak.travel.ui.view.dateview;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.MonthBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by wangyang on 2017/2/23.
 */

public class DateRecycleView extends RecyclerView {
    public  int startSelectDay=-1;//开始选择的日子
    public  int startSelectMonth=-1;//开始的月份
    public  int endSelectDay=-1;//结束日子
    public  int endSelectMonth=-1;//结束月份
    private int startMonth;
    private Calendar startCalender=null;
    private Calendar endCalender=null;

    public DateRecycleView(Context context) {
        this(context, null);
    }

    public DateRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 计算出剩下几个月的时间排序数组
     *
     * @param limit
     */
    public void init(int limit) {
        Calendar current = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Date date = new Date();
        current.setTime(date);
        end.setTime(date);
        end.add(Calendar.MONTH, 5);
        Calendar start = Calendar.getInstance();
        startMonth = start.get(Calendar.MONTH)+1;
        start.set(current.get(Calendar.YEAR), current.get(Calendar.MONTH), 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
        Calendar temp = Calendar.getInstance();
        final String[] monthTitles=new String[limit];
        final List<MonthBean> monthBeens=new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            MonthBean monthBean = new MonthBean();
            int startDayOfWeek = start.get(Calendar.DAY_OF_WEEK);
            int[] day = new int[49];
            int index = 0;
            if (startDayOfWeek != 1) {
                temp.setTime(start.getTime());
                //获取上个月的日历
                temp.add(Calendar.MONTH, -1);
                int actualMaximum = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int j = 1; j < startDayOfWeek; j++) {
                    day[index++] = actualMaximum - startDayOfWeek + 1 + j;
                }

            }
            monthBean.setStartIndex(startDayOfWeek == 1 ? 7 : startDayOfWeek + 6);
            //只有第一个月有过期时间
            monthBean.setCurrentDay(i == 0 ? current.get(Calendar.DAY_OF_MONTH) : 1);
            int currentMaxDay = start.getActualMaximum(Calendar.DAY_OF_MONTH);
            monthBean.setEndIndex(index + currentMaxDay + 7);
            monthBean.setCurrentMonth(start.get(Calendar.MONTH)+1);
            String des = simpleDateFormat.format(start.getTime());
            monthBean.setYearMonth(des);
            monthTitles[i]=des;
            monthBean.setCurrentYear(start.get(Calendar.YEAR));
            monthBean.setMaxDay(currentMaxDay);
            for (int k = 1; k <= currentMaxDay; k++) {
                day[index++] = k;
            }
            int[] realDayArray = new int[monthBean.getEndIndex()];
            System.arraycopy(day, 0, realDayArray, 7, monthBean.getEndIndex() - 7);
            monthBean.setDayIndex(realDayArray);
            monthBeens.add(monthBean);
            start.add(Calendar.MONTH, 1);
        }
        this.setAdapter(new DateRecycleViewAdapter(monthBeens,getContext()));
        this.setLayoutManager(new LinearLayoutManager(getContext()));
        this.addItemDecoration(new DateDecoration(monthTitles, getContext(), new DateDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (position<monthTitles.length && position>-1){
                    return monthTitles[position];
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (position<monthTitles.length && position>-1){
                    return monthTitles[position];
                }
                return "";
            }
        }));
    }

    /**
     * 是否需要重新
     * @return
     */
    public boolean isReset() {
        return startSelectDay!=-1 && endSelectDay!=-1;
    }
    public boolean isStart(){
        return startSelectDay==-1 && endSelectDay==-1;
    }

    class DateRecycleViewAdapter extends BaseRecycleViewAdapter<MonthBean> {


        public DateRecycleViewAdapter(List<MonthBean> mDatas, Context mContext) {
            super(mDatas, mContext);
        }

        @Override
        public BaseRecycleViewHolder<MonthBean> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DateRecycleHolder(inflateView(R.layout.item_date_view, null));
        }

        class DateRecycleHolder extends BaseRecycleViewHolder<MonthBean> {


            RecyclerView rvDateMonth;
            public DateRecycleHolder(View itemView) {
                super(itemView);
                rvDateMonth=((RecyclerView) itemView.findViewById(R.id.rv_date_month));
                rvDateMonth.addItemDecoration(new DateItemDistance());
            }

            @Override
            public void childBindView(int position, MonthBean data, Context mContext) {
                   rvDateMonth.setAdapter(new DayRecycleAdapter(data));
                   rvDateMonth.setLayoutManager(new GridLayoutManager(mContext,7));



            }
        }
    }
     class DayRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private MonthBean monthBean;

        String[] weekIndex={"日","一","二","三","四","五","六"};
        int[] dayIndex ;
        public DayRecycleAdapter(@NonNull MonthBean monthBean) {
            this.monthBean = monthBean;
            dayIndex= monthBean.getDayIndex();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DayRecycleHolder(inflate(parent.getContext(),R.layout.item_item_date_day,null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final DayRecycleHolder dayRecycleHolder = (DayRecycleHolder) holder;
            String dayNumber;

            int textColor;
            if (position<7){
                //前面的设置周一到周日
                dayNumber=weekIndex[position];
            }else {
                dayNumber= String.valueOf(dayIndex[position]);
            }
            textColor = getTextColor(position, dayRecycleHolder);

            //当天日期
            if (startMonth==monthBean.getCurrentMonth() && dayIndex[position]==monthBean.getCurrentDay() &&position>=monthBean.getCurrentDay()){
                dayNumber="今天";
            }


            dayRecycleHolder.tvDateDay.setTextColor(textColor);
            dayRecycleHolder.tvDateDay.setText(dayNumber);
            dayRecycleHolder.tvDateDay.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position<monthBean.getStartIndex() || monthBean.getCurrentMonth()<startSelectMonth
                            || (monthBean.getCurrentMonth()==startSelectMonth && position<startSelectDay)
                            ||(startMonth==monthBean.getCurrentMonth() && dayIndex[position]<monthBean.getCurrentDay())){
                        //不能点的日子如果遇到需要重置也重置一下
                        if (isReset()){
                            reset();
                            callBack();
                            getAdapter().notifyDataSetChanged();
                        }
                        return;
                    }

                    if (isReset()) {
                        reset();
                    } else if (isStart()){
                        startSelectDay=position;
                        startSelectMonth=monthBean.getCurrentMonth();
                        startCalender= Calendar.getInstance();
                        startCalender.set(monthBean.getCurrentYear(),monthBean.getCurrentMonth(),dayIndex[position]);
                    } else {
                        //开始和结束不能在同一天
                        if (startSelectDay==position && startSelectMonth==monthBean.getCurrentMonth()){
                            return;
                        }
                         endSelectDay=position;
                         endSelectMonth=monthBean.getCurrentMonth();
                        endCalender= Calendar.getInstance();
                        endCalender.set(monthBean.getCurrentYear(),monthBean.getCurrentMonth(),dayIndex[position]);
                    }
                    callBack();
                    getAdapter().notifyDataSetChanged();
                }
            });


        }

         /**
          * 渲染选择之后的颜色变化
          * @param position
          * @param dayRecycleHolder
          * @return
          */
         private int getTextColor(int position, DayRecycleHolder dayRecycleHolder) {
             int textColor;
             dayRecycleHolder.tvDateDay.setBackgroundColor(Color.TRANSPARENT);
             if (startSelectDay==position && startSelectMonth==monthBean.getCurrentMonth()){
                textColor= Color.WHITE;
                 dayRecycleHolder.tvDateDay.setBackgroundResource(R.drawable.green_circle);
            }else if (endSelectDay==position && endSelectMonth==monthBean.getCurrentMonth()){
                textColor= Color.parseColor("#5cd0c2");
                 dayRecycleHolder.tvDateDay.setBackgroundResource(R.drawable.white_circle);
            }else if ((position%7==0 || position%7==6) && position>monthBean.getStartIndex()){
                textColor= Color.parseColor("#ff7f6c");
            }else if (position>6 && position<monthBean.getStartIndex()){
                textColor= Color.parseColor("#bfbfbf");
            }else {
                 textColor= Color.parseColor("#595959");
             }
             /**
              * 选择了开始，开始之前的都变色  第一个月小于今天变色
              */
             if ((startMonth==monthBean.getCurrentMonth() && dayIndex[position]<monthBean.getCurrentDay())||monthBean.getCurrentMonth()<startSelectMonth || (monthBean.getCurrentMonth()==startSelectMonth && position<startSelectDay)){
                 if (position>6) {//week不需要变色
                     textColor = Color.parseColor("#bfbfbf");
                 }
             }
             /**
              * 选中的日期之间需要重新渲染颜色
              */
             if (position>6 && isInclude(position) && monthBean.getDayIndex()[position]<=position
                     ){
                     textColor = Color.WHITE;
                 dayRecycleHolder.llDateDay.setBackgroundColor(Color.parseColor("#505cd0c2"));

             }else if (endSelectMonth!=-1 && startSelectMonth!=-1){
                 if (startSelectMonth==monthBean.getCurrentMonth() && position==startSelectDay){
                     dayRecycleHolder.llDateDay.setBackgroundResource(R.drawable.date_start_color);
                 }else if (endSelectMonth==monthBean.getCurrentMonth() && position==endSelectDay){
                     dayRecycleHolder.llDateDay.setBackgroundResource(R.drawable.date_end_color);
                 }

             }else {
                 dayRecycleHolder.llDateDay.setBackgroundColor(Color.TRANSPARENT);
             }

             return textColor;
         }

         /**
          * 计算日子是否在选中之中
          * @param position
          * @return
          */
         private boolean isInclude(int position) {
             if (endSelectMonth==-1)return false;
             boolean include=false;
             int currentMonth = monthBean.getCurrentMonth();
             if (currentMonth==startSelectMonth ){
                 include=position>startSelectDay;
                 if (!include){
                     return false;
                 }
                 if (currentMonth==endSelectMonth){
                     include=position<endSelectDay;
                 }else if (currentMonth==endSelectMonth){
                     include=position<endSelectDay;
                 }
             }else if (currentMonth > startSelectMonth && currentMonth<endSelectMonth){
                 include=true;
             }else if (currentMonth==endSelectMonth){
                 include=position<endSelectDay;
             }
             return include;
         }

         @Override
        public int getItemCount() {
            return dayIndex==null?0:dayIndex.length;
        }
        class DayRecycleHolder extends RecyclerView.ViewHolder{
            private TextView tvDateDay;
            private LinearLayout llDateDay;
            public DayRecycleHolder(View itemView) {
                super(itemView);
                tvDateDay = (TextView) itemView.findViewById(R.id.tv_date_day);
                llDateDay = (LinearLayout) itemView.findViewById(R.id.ll_date_day);
            }
        }
    }

    private void callBack() {
        if (startCalender!=null && endCalender!=null){
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String start = simpleDateFormat.format(startCalender.getTime());
            String end = simpleDateFormat.format(endCalender.getTime());
            System.out.println(start+"-"+end);
        }
        if (selectListener!=null){
            selectListener.onCallBack(startCalender,endCalender);
        }
    }

    private void reset() {
        startSelectDay=-1;
        endSelectDay=-1;
        startSelectMonth=-1;
        endSelectMonth=-1;
        startCalender=null;
        endCalender=null;
        this.getAdapter().notifyDataSetChanged();
    }

    /**
     * 监听用户选择
     */
    private SelectListener selectListener;

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public interface SelectListener{
        void  onCallBack(Calendar in, Calendar out);
    }
}
