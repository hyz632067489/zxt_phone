package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyz on 2017/3/30.
 * powered by company
 */

public class GzrzListModel  {

    /**
     * totalRow : 2
     * pageCurrent : 1
     * list : [{"areaId":22,"blogContent":"各位德克士的铁粉们，\r\n这可不是一个简单的倒计时！\r\n只要能够玩转这个口诀，\r\n那么一大波不可思议的福利，\r\n就都是你的了！","blogId":2,"blogName":"207/3/31","blogPic":"images/user/default.png","blogType":"2,3","editBlogDate":"2017-03-31 16:42:00","gridId":7,"gridStaffId":16},{"areaId":22,"blogContent":"到intel的官方网站下载windows 7 x64的F6驱动，也就是在Windows  7安装的过程中可以提前加入的驱动，到安装程序选择或管理分区的步骤时可以加入","blogId":1,"blogName":"2017/3/30","blogPic":"images/user/default.png","blogType":"1,2,3","editBlogDate":"2017-03-30 09:00:00","gridId":7,"gridStaffId":16}]
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

    public static class ListBean implements Serializable{
        /**
         * areaId : 22
         * blogContent : 各位德克士的铁粉们，
         这可不是一个简单的倒计时！
         只要能够玩转这个口诀，
         那么一大波不可思议的福利，
         就都是你的了！
         * blogId : 2
         * blogName : 207/3/31
         * blogPic : images/user/default.png
         * blogType : 2,3
         * editBlogDate : 2017-03-31 16:42:00
         * gridId : 7
         * gridStaffId : 16
         */
        private static final long serialVersionUID = -7060210544600464481L;

        private int areaId;
        private String blogContent;
        private int blogId;
        private String blogName;
        private String blogPic;
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

        public int getGridStaffId() {
            return gridStaffId;
        }

        public void setGridStaffId(int gridStaffId) {
            this.gridStaffId = gridStaffId;
        }
    }
}
