package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyz on 2017/3/30.
 * powered by company
 */

public class GzrzListModel  {


    /**
     * statusCode : 200
     * data : {"totalRow":1,"pageCurrent":1,"list":[{"areaId":22,"blogContent":"dgdegfergrg","blogId":31,"blogName":"2017-4-15","blogPic":"images/app/blog/zs/20170415104301584.jpg;images/app/blog/zs/20170415104301706.jpg;","blogType":"1,3,4","editBlogDate":"2017-04-15 10:43:01","gridId":7,"gridStaffApp":{"gridStaffId":16,"gridStaffName":"张杰"}}]}
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
         * totalRow : 1
         * pageCurrent : 1
         * list : [{"areaId":22,"blogContent":"dgdegfergrg","blogId":31,"blogName":"2017-4-15","blogPic":"images/app/blog/zs/20170415104301584.jpg;images/app/blog/zs/20170415104301706.jpg;","blogType":"1,3,4","editBlogDate":"2017-04-15 10:43:01","gridId":7,"gridStaffApp":{"gridStaffId":16,"gridStaffName":"张杰"}}]
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

        public static class ListBean  implements Serializable{
            /**
             * areaId : 22
             * blogContent : dgdegfergrg
             * blogId : 31
             * blogName : 2017-4-15
             * blogPic : images/app/blog/zs/20170415104301584.jpg;images/app/blog/zs/20170415104301706.jpg;
             * blogType : 1,3,4
             * editBlogDate : 2017-04-15 10:43:01
             * gridId : 7
             * gridStaffApp : {"gridStaffId":16,"gridStaffName":"张杰"}
             */

            private int areaId;
            private String blogContent;
            private int blogId;
            private String blogName;
            private String blogPic;
            private String blogType;
            private String editBlogDate;
            private int gridId;
            private GridStaffAppBean gridStaffApp;

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

            public String getBlogPic() {
                return blogPic;
            }

            public void setBlogPic(String blogPic) {
                this.blogPic = blogPic;
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

            public GridStaffAppBean getGridStaffApp() {
                return gridStaffApp;
            }

            public void setGridStaffApp(GridStaffAppBean gridStaffApp) {
                this.gridStaffApp = gridStaffApp;
            }

            public static class GridStaffAppBean implements Serializable{
                /**
                 * gridStaffId : 16
                 * gridStaffName : 张杰
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
    }
}
