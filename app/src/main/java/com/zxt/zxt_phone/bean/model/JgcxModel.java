package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/3/17.
 * powered by company
 */

public class JgcxModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * Page : 1
     * PageCount : 17
     * Action :
     * CountNum : 161
     * NewsShowUrl : /api/Html/news_show.html
     * Data : [{"OpinionId":100584,"TVInfoId":"17","deptName":"天邻风景","deptId":788,"Title":"雨天提醒","UserName":"王洪","MobileNo":"18523998787","Address":"","EditDate":"2017-02-15","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"2017-02-15","AuditState":1,"AuditName":"已办结"},{"OpinionId":100582,"TVInfoId":"17","deptName":"天邻风景","deptId":788,"Title":"小区安全","UserName":"雷欣","MobileNo":"18580609797","Address":"","EditDate":"2017-02-14","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"2017-02-14","AuditState":1,"AuditName":"已办结"},{"OpinionId":100581,"TVInfoId":"17","deptName":"冉家坝社区","deptId":855,"Title":"关爱空巢老人","UserName":"朱月娇","MobileNo":"18580609090","Address":"","EditDate":"2017-02-14","OpinionClassName":"求助","OpinionClassId":"2","ReDate":"2017-02-15","AuditState":1,"AuditName":"已办结"},{"OpinionId":100580,"TVInfoId":"17","deptName":"天邻风景","deptId":788,"Title":"电梯","UserName":"颜泽慧","MobileNo":"15922533706","Address":"","EditDate":"2017-02-14","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"2017-02-14","AuditState":1,"AuditName":"已办结"},{"OpinionId":100579,"TVInfoId":"17","deptName":"冉家坝社区","deptId":855,"Title":"衣物回收","UserName":"罗妙","MobileNo":"13290064641","Address":"","EditDate":"2017-02-14","OpinionClassName":"求助","OpinionClassId":"2","ReDate":"2017-02-15","AuditState":1,"AuditName":"已办结"},{"OpinionId":100578,"TVInfoId":"17","deptName":"中海都市物业","deptId":862,"Title":"环境","UserName":"周杰","MobileNo":"18315248118","Address":"","EditDate":"2017-02-14","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"","AuditState":0,"AuditName":"已受理"},{"OpinionId":100577,"TVInfoId":"17","deptName":"冉家坝社区","deptId":855,"Title":"医保","UserName":"向桂民","MobileNo":"15223971277","Address":"","EditDate":"2017-02-14","OpinionClassName":"求助","OpinionClassId":"2","ReDate":"2017-02-15","AuditState":1,"AuditName":"已办结"},{"OpinionId":100547,"TVInfoId":"","deptName":"重庆市中海都市物业有限公司","deptId":862,"Title":"电梯问题","UserName":"魏元华","MobileNo":"13608355115","Address":"天邻风景10栋172","EditDate":"2016-11-17","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"2016-11-21","AuditState":1,"AuditName":"已办结"},{"OpinionId":100497,"TVInfoId":"","deptName":"重庆市中海都市物业有限公司","deptId":862,"Title":"小区水质不好","UserName":"范丽萍","MobileNo":"13320231283","Address":"天邻风景 19栋284","EditDate":"2016-11-17","OpinionClassName":"投诉","OpinionClassId":"3","ReDate":"","AuditState":0,"AuditName":"已受理"},{"OpinionId":100426,"TVInfoId":"17","deptName":"天邻风景","deptId":788,"Title":"点议","UserName":"李道亮","MobileNo":"13996302299","Address":"","EditDate":"2016-11-17","OpinionClassName":"建议","OpinionClassId":"1","ReDate":"2016-11-21","AuditState":1,"AuditName":"已办结"}]
     */

    private int Status;
    private String Message;
    private String Page;
    private String PageCount;
    private String Action;
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

    public String getAction() {
        return Action;
    }

    public void setAction(String Action) {
        this.Action = Action;
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
         * OpinionId : 100584
         * TVInfoId : 17
         * deptName : 天邻风景
         * deptId : 788
         * Title : 雨天提醒
         * UserName : 王洪
         * MobileNo : 18523998787
         * Address :
         * EditDate : 2017-02-15
         * OpinionClassName : 建议
         * OpinionClassId : 1
         * ReDate : 2017-02-15
         * AuditState : 1
         * AuditName : 已办结
         */

        private int OpinionId;
        private String TVInfoId;
        private String deptName;
        private int deptId;
        private String Title;
        private String UserName;
        private String MobileNo;
        private String Address;
        private String EditDate;
        private String OpinionClassName;
        private String OpinionClassId;
        private String ReDate;
        private int AuditState;
        private String AuditName;

        public int getOpinionId() {
            return OpinionId;
        }

        public void setOpinionId(int OpinionId) {
            this.OpinionId = OpinionId;
        }

        public String getTVInfoId() {
            return TVInfoId;
        }

        public void setTVInfoId(String TVInfoId) {
            this.TVInfoId = TVInfoId;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getEditDate() {
            return EditDate;
        }

        public void setEditDate(String EditDate) {
            this.EditDate = EditDate;
        }

        public String getOpinionClassName() {
            return OpinionClassName;
        }

        public void setOpinionClassName(String OpinionClassName) {
            this.OpinionClassName = OpinionClassName;
        }

        public String getOpinionClassId() {
            return OpinionClassId;
        }

        public void setOpinionClassId(String OpinionClassId) {
            this.OpinionClassId = OpinionClassId;
        }

        public String getReDate() {
            return ReDate;
        }

        public void setReDate(String ReDate) {
            this.ReDate = ReDate;
        }

        public int getAuditState() {
            return AuditState;
        }

        public void setAuditState(int AuditState) {
            this.AuditState = AuditState;
        }

        public String getAuditName() {
            return AuditName;
        }

        public void setAuditName(String AuditName) {
            this.AuditName = AuditName;
        }
    }
}
