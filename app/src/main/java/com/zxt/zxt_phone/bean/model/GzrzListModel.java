package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyz on 2017/3/30.
 * powered by company
 */

public class GzrzListModel  {


    /**
     * totalRow : 4
     * pageCurrent : 1
     * list : [{"areaId":22,"blogContent":"ceshi neirong","blogId":23,"blogName":"2017-4-5","blogPic":"images/app/blog/20170405133122280.jpg;images/app/blog/20170405133122369.jpg;","blogType":"1,4","editBlogDate":"2017-04-05 13:35:54","gridId":7,"gridStaffApp":{"gridStaffId":8,"gridStaffName":"张海峰"}},{"areaId":22,"blogContent":"ceshi neirong","blogId":22,"blogName":"2017-4-5","blogPic":"images/app/blog/20170405133122280.jpg;images/app/blog/20170405133122369.jpg;","blogType":"1,4","editBlogDate":"2017-04-05 13:34:42","gridId":7,"gridStaffApp":{"gridStaffId":16,"gridStaffName":"zs"}},{"areaId":22,"blogContent":"各位德克士的铁粉们，\r\n这可不是一个简单的倒计时！\r\n只要能够玩转这个口诀，\r\n那么一大波不可思议的福利，\r\n就都是你的了！","blogId":2,"blogName":"207-3-31","blogPic":"images/user/default.png","blogType":"2,3","editBlogDate":"2017-03-31 16:42:00","gridId":7,"gridStaffApp":{"gridStaffId":16,"gridStaffName":"zs"}},{"areaId":22,"blogContent":"到intel的官方网站下载windows 7 x64的F6驱动，也就是在Windows  7安装的过程中可以提前加入的驱动，到安装程序选择或管理分区的步骤时可以加入","blogId":1,"blogName":"2017-3-30","blogPic":"images/user/default.png","blogType":"1,2,3","editBlogDate":"2017-03-30 09:00:00","gridId":7,"gridStaffApp":{"gridStaffId":16,"gridStaffName":"zs"}}]
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

    public static class ListNewsModel implements Serializable{


        /**
         * areaId : 22
         * blogContent : ceshi neirong
         * blogId : 23
         * blogName : 2017-4-5
         * blogPic : images/app/blog/20170405133122280.jpg;images/app/blog/20170405133122369.jpg;
         * blogType : 1,4
         * editBlogDate : 2017-04-05 13:35:54
         * gridId : 7
         * gridStaffApp : {"gridStaffId":8,"gridStaffName":"张海峰"}
         */

        private int areaId;
        private String blogContent;
        private int blogId;
        private String blogName;
        private String blogPic;
        private String blogType;
        private String editBlogDate;
        private int gridId;
        private GridStaffAppNewsModel gridStaffApp;

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

        public GridStaffAppNewsModel getGridStaffApp() {
            return gridStaffApp;
        }

        public void setGridStaffApp(GridStaffAppNewsModel gridStaffApp) {
            this.gridStaffApp = gridStaffApp;
        }

        public static class GridStaffAppNewsModel {
            /**
             * gridStaffId : 8
             * gridStaffName : 张海峰
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
