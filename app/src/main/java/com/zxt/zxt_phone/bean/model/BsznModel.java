package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;

/**
 * Created by hyz on 2017/3/11.
 * powered by company
 */

public class BsznModel implements Serializable{

    private  int image;
    private  String text;

    public BsznModel(){

    }
    public BsznModel(String text){
        this.text = text;

    }
    public BsznModel(String text,int image){
        this.text = text;
        this.image = image;
    }


    public int getImage() {
        return image;
    }

    public BsznModel setImage(int image) {
        this.image = image;
        return this;
    }

    public String getText() {
        return text;
    }

    public BsznModel setText(String text) {
        this.text = text;
        return this;
    }
}
