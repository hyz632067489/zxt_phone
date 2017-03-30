package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/3/21.
 * powered by company
 */

public class DeptNameModel {


    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 16
     * Data : [{"Deptid":1,"DeptName":"物业","deptbh":"00.01"},{"Deptid":2,"DeptName":"社区","deptbh":"00.02"},{"Deptid":3,"DeptName":"街道","deptbh":"00.03"},{"Deptid":853,"DeptName":"区群团活中心","deptbh":"02.01.01"},{"Deptid":790,"DeptName":"总工会","deptbh":"02.01.02"},{"Deptid":778,"DeptName":"团区委","deptbh":"02.01.03"},{"Deptid":780,"DeptName":"区妇联","deptbh":"02.01.04"},{"Deptid":776,"DeptName":"区科协","deptbh":"02.01.05"},{"Deptid":777,"DeptName":"区工商联","deptbh":"02.01.06"},{"Deptid":784,"DeptName":"区残联","deptbh":"02.01.07"},{"Deptid":785,"DeptName":"区侨联","deptbh":"02.01.08"},{"Deptid":786,"DeptName":"区计生协会","deptbh":"02.01.09"},{"Deptid":787,"DeptName":"区社科联","deptbh":"02.01.10"},{"Deptid":736,"DeptName":"区文联","deptbh":"02.01.11"},{"Deptid":782,"DeptName":"区红十字会","deptbh":"02.01.12"},{"Deptid":783,"DeptName":"群团公益基金","deptbh":"02.01.13"}]
     */

    private int Status;
    private String Message;
    private String CountNum;
    private List<DataNewsModel> Data;

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

    public String getCountNum() {
        return CountNum;
    }

    public void setCountNum(String CountNum) {
        this.CountNum = CountNum;
    }

    public List<DataNewsModel> getData() {
        return Data;
    }

    public void setData(List<DataNewsModel> Data) {
        this.Data = Data;
    }

    public static class DataNewsModel {
        /**
         * Deptid : 1
         * DeptName : 物业
         * deptbh : 00.01
         */

        private int Deptid;
        private String DeptName;
        private String deptbh;

        public int getDeptid() {
            return Deptid;
        }

        public void setDeptid(int Deptid) {
            this.Deptid = Deptid;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }

        public String getDeptbh() {
            return deptbh;
        }

        public void setDeptbh(String deptbh) {
            this.deptbh = deptbh;
        }
    }
}
