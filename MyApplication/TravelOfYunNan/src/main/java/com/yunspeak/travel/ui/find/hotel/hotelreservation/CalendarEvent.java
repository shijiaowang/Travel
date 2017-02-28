package com.yunspeak.travel.ui.find.hotel.hotelreservation;

import com.yunspeak.travel.utils.CalendarUtils;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by wangyang on 2017/2/27.
 */

public class CalendarEvent implements Serializable{
    private Calendar start;
    private Calendar end;

    public CalendarEvent(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }
    public boolean isEmpty(){
        return start!=null && end!=null;
    }
    public  int  getHowDay(){
        if (start==null || end==null){
            return 0;
        }
        return CalendarUtils.getHowDay(start.getTime().getTime()+"",end.getTime().getTime()+"")-1;
    }
}
