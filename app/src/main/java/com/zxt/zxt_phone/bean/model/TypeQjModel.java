package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/9.
 */

public class TypeQjModel {

    /**
     * statusCode : 200
     * data : [{"leaves_type_id":1,"leaves_type_name":"事假"},{"leaves_type_id":2,"leaves_type_name":"病假"},{"leaves_type_id":3,"leaves_type_name":"婚假"},{"leaves_type_id":4,"leaves_type_name":"产假"},{"leaves_type_id":5,"leaves_type_name":"陪产假"},{"leaves_type_id":6,"leaves_type_name":"路途假"},{"leaves_type_id":7,"leaves_type_name":"其他"}]
     */

    private int statusCode;
    private List<DataBean> data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * leaves_type_id : 1
         * leaves_type_name : 事假
         */

        private int leaves_type_id;
        private String leaves_type_name;

        public int getLeaves_type_id() {
            return leaves_type_id;
        }

        public void setLeaves_type_id(int leaves_type_id) {
            this.leaves_type_id = leaves_type_id;
        }

        public String getLeaves_type_name() {
            return leaves_type_name;
        }

        public void setLeaves_type_name(String leaves_type_name) {
            this.leaves_type_name = leaves_type_name;
        }
    }
}
