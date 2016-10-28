package com.yunspeak.travel.ui.appoint.travelplan.lineplan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/9/12 0012.
 */
public class LineBean implements Serializable {
    private String time;
    private String date;//时间戳
    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    private List<Destination> destinations=new ArrayList<>();

    public LineBean(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public static class Destination{
        private String id;
        private String name;

        public Destination(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
