package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/3/16.
 * powered by company
 */

public class MarqueeModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * Page : 1
     * PageCount : 60
     * CountNum : 358
     * NewsShowUrl : /api/Html/news_show.html
     * Data : [{"NewsId":1468,"DeptName":"鲁能5街区","ImageIndex":"","Title":"做好动物疫病免疫 防止重大动物疫病","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-03-13","EditUserName":"王玲","istop":0},{"NewsId":1467,"DeptName":"鲁能西路社区","ImageIndex":"","Title":"通 知","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-03-09","EditUserName":"佘天娟","istop":0},{"NewsId":1465,"DeptName":"鲁能5街区","ImageIndex":"","Title":"通知","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-03-02","EditUserName":"王玲","istop":0},{"NewsId":1463,"DeptName":"鲁能西路社区","ImageIndex":"","Title":"鲁能西路社区工作安全会议","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-02-20","EditUserName":"佘天娟","istop":0},{"NewsId":1461,"DeptName":"鲁能西路社区","ImageIndex":"","Title":"元宵节安全巡逻检查","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-02-13","EditUserName":"佘天娟","istop":0},{"NewsId":1460,"DeptName":"鲁能5街区","ImageIndex":"/UpLoadFiles/image/20170209/20170209154042_3101.jpg","Title":"鲁能西路社区开展爱国主义教育活动","ModuId":3,"ModuName":"惠民政策","EditDate":"2017-02-09","EditUserName":"王玲","istop":0}]
     */

    private int Status;
    private String Message;
    private String Page;
    private String PageCount;
    private String CountNum;
    private String NewsShowUrl;
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

    public String getPage() {
        return Page;
    }

    public void setPage(String Page) {
        this.Page = Page;
    }

    public String getPageCount() {
        return PageCount;
    }

    public void setPageCount(String PageCount) {
        this.PageCount = PageCount;
    }

    public String getCountNum() {
        return CountNum;
    }

    public void setCountNum(String CountNum) {
        this.CountNum = CountNum;
    }

    public String getNewsShowUrl() {
        return NewsShowUrl;
    }

    public void setNewsShowUrl(String NewsShowUrl) {
        this.NewsShowUrl = NewsShowUrl;
    }

    public List<DataNewsModel> getData() {
        return Data;
    }

    public void setData(List<DataNewsModel> Data) {
        this.Data = Data;
    }

    public static class DataNewsModel {
        /**
         * NewsId : 1468
         * DeptName : 鲁能5街区
         * ImageIndex :
         * Title : 做好动物疫病免疫 防止重大动物疫病
         * ModuId : 3
         * ModuName : 惠民政策
         * EditDate : 2017-03-13
         * EditUserName : 王玲
         * istop : 0
         */

        private int NewsId;
        private String DeptName;
        private String ImageIndex;
        private String Title;
        private int ModuId;
        private String ModuName;
        private String EditDate;
        private String EditUserName;
        private int istop;

        public int getNewsId() {
            return NewsId;
        }

        public void setNewsId(int NewsId) {
            this.NewsId = NewsId;
        }

        public String getDeptName() {
            return DeptName;
        }

        public void setDeptName(String DeptName) {
            this.DeptName = DeptName;
        }

        public String getImageIndex() {
            return ImageIndex;
        }

        public void setImageIndex(String ImageIndex) {
            this.ImageIndex = ImageIndex;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getModuId() {
            return ModuId;
        }

        public void setModuId(int ModuId) {
            this.ModuId = ModuId;
        }

        public String getModuName() {
            return ModuName;
        }

        public void setModuName(String ModuName) {
            this.ModuName = ModuName;
        }

        public String getEditDate() {
            return EditDate;
        }

        public void setEditDate(String EditDate) {
            this.EditDate = EditDate;
        }

        public String getEditUserName() {
            return EditUserName;
        }

        public void setEditUserName(String EditUserName) {
            this.EditUserName = EditUserName;
        }

        public int getIstop() {
            return istop;
        }

        public void setIstop(int istop) {
            this.istop = istop;
        }
    }
}
