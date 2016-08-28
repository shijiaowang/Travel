package com.example.administrator.travel.event;

/**
 * Created by android on 2016/8/28.
 */
public class DragEvent {
    private boolean touch;

    private int index;

    public DragEvent(int index, boolean touch) {
        this.index = index;
        this.touch = touch;

    }

    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }



    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }
}
