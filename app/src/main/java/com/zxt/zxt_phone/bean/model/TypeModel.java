package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/8.
 * 书记信箱
 */
public class TypeModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * Data : [{"id":1,"content":"建议"},{"id":2,"content":"纠纷"},{"id":3,"content":"举报"},{"id":4,"content":"投诉"},{"id":5,"content":"其他"}]
     */

    private int Status;
    private String Message;
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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 1
         * content : 建议
         */

        private int id;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
