package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/9.
 */

public class LinDaoModel {

    /**
     * statusCode : 200
     * data : [{"gridStaffId":1,"gridStaffName":"张可路","gridStaffScope":"","gridStaffSex":"","gridStaffTel":""},{"gridStaffId":2,"gridStaffName":"张靖宇","gridStaffScope":"","gridStaffSex":"","gridStaffTel":""},{"gridStaffId":16,"gridStaffName":"张杰","gridStaffScope":"","gridStaffSex":"","gridStaffTel":""}]
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
         * gridStaffId : 1
         * gridStaffName : 张可路
         * gridStaffScope :
         * gridStaffSex :
         * gridStaffTel :
         */

        private int gridStaffId;
        private String gridStaffName;

        public int getGridStaffId() {
            return gridStaffId;
        }

        public void setGridStaffId(int gridStaffId) {
            this.gridStaffId = gridStaffId;
        }

        public String getGridStaffName() {
            return gridStaffName;
        }

        public void setGridStaffName(String gridStaffName) {
            this.gridStaffName = gridStaffName;
        }
    }
}
