package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/9.
 */

public class TypeYybsModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 2
     * Data : [{"id":1,"genre":"群众办事"},{"id":2,"genre":"企业办事"}]
     */

    private int Status;
    private String Message;
    private int CountNum;
    private List<DataBean> Data;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getCountNum() {
        return CountNum;
    }

    public void setCountNum(int CountNum) {
        this.CountNum = CountNum;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 1
         * genre : 群众办事
         */

        private int id;
        private String genre;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }
    }
}
