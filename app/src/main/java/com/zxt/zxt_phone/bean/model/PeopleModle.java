package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/8.
 * 书记信箱
 */
public class PeopleModle {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * Data : [{"id":1,"type":"街道书记","shzt":""},{"id":2,"type":"社区书记","shzt":""}]
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
         * type : 街道书记
         * shzt :
         */

        private int id;
        private String type;
        private String shzt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShzt() {
            return shzt;
        }

        public void setShzt(String shzt) {
            this.shzt = shzt;
        }
    }
}
