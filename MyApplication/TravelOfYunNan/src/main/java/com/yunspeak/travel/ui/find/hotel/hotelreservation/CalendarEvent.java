package com.yunspeak.travel.ui.find.hotel.hotelreservation;

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
}
