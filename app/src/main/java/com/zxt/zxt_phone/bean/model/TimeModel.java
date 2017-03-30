package com.zxt.zxt_phone.bean.model;

/**
 * Created by hyz on 2017/3/23.
 * powered by company
 */

public class TimeModel {

    public String isCheck;
    private  int position;
    private  String time;


    public int getPosition(){
        return position;
    }
    public  TimeModel setPosition(int position){
        this.position = position;
        return this;
    }
    public String getTime() {
        return time;
    }

    public TimeModel setTime(String time) {
        this.time = time;
        return this;
    }
}
