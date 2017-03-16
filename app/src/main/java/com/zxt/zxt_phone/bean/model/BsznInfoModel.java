package com.zxt.zxt_phone.bean.model;

/**
 * Created by hyz on 2017/3/12.
 * powered by company
 */

public class BsznInfoModel {

    private int image;
    private String title;
    private  String tv_all;
    private String tv_sp;
    private String tv_fw;

    public int getImage() {
        return image;
    }

    public BsznInfoModel setImage(int image) {
        this.image = image;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BsznInfoModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTv_all() {
        return tv_all;
    }

    public BsznInfoModel setTv_all(String tv_all) {
        this.tv_all = tv_all;
        return this;
    }

    public String getTv_sp() {
        return tv_sp;
    }

    public BsznInfoModel setTv_sp(String tv_sp) {
        this.tv_sp = tv_sp;
        return this;
    }

    public String getTv_fw() {
        return tv_fw;
    }

    public BsznInfoModel setTv_fw(String tv_fw) {
        this.tv_fw = tv_fw;
        return this;
    }
}
