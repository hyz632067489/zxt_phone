package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/10.
 */

public class WgrxModel {


    /**
     * statusCode : 200
     * data : {"totalRow":3,"pageCurrent":1,"list":[{"gridStaffId":16,"gridStaffName":"张杰","gridStaffScope":"fhf","gridStaffSex":"男","gridStaffTel":"15625462578"},{"gridStaffId":2,"gridStaffName":"张靖宇","gridStaffScope":"鲁能西路第一网格2-3栋","gridStaffSex":"男","gridStaffTel":"15830660892"},{"gridStaffId":1,"gridStaffName":"张可路","gridStaffScope":"鲁能西路第一网格1-4栋","gridStaffSex":"男","gridStaffTel":"15630660892"}]}
     */

    private int statusCode;
    private DataBean data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * totalRow : 3
         * pageCurrent : 1
         * list : [{"gridStaffId":16,"gridStaffName":"张杰","gridStaffScope":"fhf","gridStaffSex":"男","gridStaffTel":"15625462578"},{"gridStaffId":2,"gridStaffName":"张靖宇","gridStaffScope":"鲁能西路第一网格2-3栋","gridStaffSex":"男","gridStaffTel":"15830660892"},{"gridStaffId":1,"gridStaffName":"张可路","gridStaffScope":"鲁能西路第一网格1-4栋","gridStaffSex":"男","gridStaffTel":"15630660892"}]
         */

        private int totalRow;
        private int pageCurrent;
        private List<ListBean> list;

        public int getTotalRow() {
            return totalRow;
        }

        public void setTotalRow(int totalRow) {
            this.totalRow = totalRow;
        }

        public int getPageCurrent() {
            return pageCurrent;
        }

        public void setPageCurrent(int pageCurrent) {
            this.pageCurrent = pageCurrent;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * gridStaffId : 16
             * gridStaffName : 张杰
             * gridStaffScope : fhf
             * gridStaffSex : 男
             * gridStaffTel : 15625462578
             */

            private int gridStaffId;
            private String gridStaffName;
            private String gridStaffScope;
            private String gridStaffSex;
            private String gridStaffTel;

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

            public String getGridStaffScope() {
                return gridStaffScope;
            }

            public void setGridStaffScope(String gridStaffScope) {
                this.gridStaffScope = gridStaffScope;
            }

            public String getGridStaffSex() {
                return gridStaffSex;
            }

            public void setGridStaffSex(String gridStaffSex) {
                this.gridStaffSex = gridStaffSex;
            }

            public String getGridStaffTel() {
                return gridStaffTel;
            }

            public void setGridStaffTel(String gridStaffTel) {
                this.gridStaffTel = gridStaffTel;
            }
        }
    }
}
