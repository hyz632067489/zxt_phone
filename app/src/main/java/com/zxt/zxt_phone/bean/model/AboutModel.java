package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/8.
 */

public class AboutModel {

    /**
     * Status : 0
     * Message : 没有数据
     * Data : [{"eid":1,"Ecompany":"重庆金科","Deptname":"鲁能5街区"}]
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
         * eid : 1
         * Ecompany : 重庆金科
         * Deptname : 鲁能5街区
         */

        private int eid;
        private String Ecompany;
        private String Deptname;

        public int getEid() {
            return eid;
        }

        public void setEid(int eid) {
            this.eid = eid;
        }

        public String getEcompany() {
            return Ecompany;
        }

        public void setEcompany(String Ecompany) {
            this.Ecompany = Ecompany;
        }

        public String getDeptname() {
            return Deptname;
        }

        public void setDeptname(String Deptname) {
            this.Deptname = Deptname;
        }
    }
}
