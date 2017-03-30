package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/3/30.
 * powered by company
 */

public class GzrzListModel {

    /**
     * totalRow : 1
     * pageCurrent : 1
     * list : [{"areaId":22,"blogContent":"","blogId":1,"blogName":"2017/3/30","blogType":"1,2,3","editBlogDate":"2017-03-30 09:00:00","gridId":7,"gridStaffId":16}]
     */

    private int totalRow;
    private int pageCurrent;
    private List<ListNewsModel> list;

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

    public List<ListNewsModel> getList() {
        return list;
    }

    public void setList(List<ListNewsModel> list) {
        this.list = list;
    }

    public static class ListNewsModel {
        /**
         * areaId : 22
         * blogContent :
         * blogId : 1
         * blogName : 2017/3/30
         * blogType : 1,2,3
         * editBlogDate : 2017-03-30 09:00:00
         * gridId : 7
         * gridStaffId : 16
         */

        private int areaId;
        private String blogContent;
        private int blogId;
        private String blogName;
        private String blogType;
        private String editBlogDate;
        private int gridId;
        private int gridStaffId;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getBlogContent() {
            return blogContent;
        }

        public void setBlogContent(String blogContent) {
            this.blogContent = blogContent;
        }

        public int getBlogId() {
            return blogId;
        }

        public void setBlogId(int blogId) {
            this.blogId = blogId;
        }

        public String getBlogName() {
            return blogName;
        }

        public void setBlogName(String blogName) {
            this.blogName = blogName;
        }

        public String getBlogType() {
            return blogType;
        }

        public void setBlogType(String blogType) {
            this.blogType = blogType;
        }

        public String getEditBlogDate() {
            return editBlogDate;
        }

        public void setEditBlogDate(String editBlogDate) {
            this.editBlogDate = editBlogDate;
        }

        public int getGridId() {
            return gridId;
        }

        public void setGridId(int gridId) {
            this.gridId = gridId;
        }

        public int getGridStaffId() {
            return gridStaffId;
        }

        public void setGridStaffId(int gridStaffId) {
            this.gridStaffId = gridStaffId;
        }
    }
}
