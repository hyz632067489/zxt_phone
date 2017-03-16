package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/3/14.
 * powered by company
 */

public class NewsModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * Page : 1
     * PageCount : 1
     * CountNum : 7
     * NewsShowUrl : /api/Html/news_show.html
     * Data : [{"NewsId":1322,"DeptName":"重庆市","ImageIndex":"","Title":"渝北群团\u201c一网一微\u201d上线 有奖征集渝北发展良策","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-16","EditUserName":"管理员","istop":0},{"NewsId":1264,"DeptName":"龙塔街道","ImageIndex":"","Title":"居民医保的普通门诊费用如何报销？","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0},{"NewsId":1263,"DeptName":"龙塔街道","ImageIndex":"","Title":"城乡居民参保缴费后，从什么时候享受居民医保待遇？","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0},{"NewsId":1252,"DeptName":"龙塔街道","ImageIndex":"","Title":"新生儿医保待遇","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0},{"NewsId":1250,"DeptName":"龙塔街道","ImageIndex":"","Title":"住院","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0},{"NewsId":1248,"DeptName":"龙塔街道","ImageIndex":"","Title":"待遇标准","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0},{"NewsId":1234,"DeptName":"龙塔街道","ImageIndex":"","Title":"渝北区龙塔街道文化服务中心简介","ModuId":4,"ModuName":"渝北资讯","EditDate":"2016-11-02","EditUserName":"龙塔街道","istop":0}]
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
         * NewsId : 1322
         * DeptName : 重庆市
         * ImageIndex :
         * Title : 渝北群团“一网一微”上线 有奖征集渝北发展良策
         * ModuId : 4
         * ModuName : 渝北资讯
         * EditDate : 2016-11-16
         * EditUserName : 管理员
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
