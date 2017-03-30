package com.zxt.zxt_phone.bean;

/**
 * Created by hyz on 2017/3/29.
 * powered by company
 */

public class PopBean {

    private String title;
    private int icon_res;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon_res() {
        return icon_res;
    }

    public void setIcon_res(int icon_res) {
        this.icon_res = icon_res;
    }

    public PopBean(String title, int icon_res) {
        this.title = title;
        this.icon_res = icon_res;
    }
}
