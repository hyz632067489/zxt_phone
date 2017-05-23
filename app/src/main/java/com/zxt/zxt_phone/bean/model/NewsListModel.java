package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/23.
 */

public class NewsListModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 3
     * pioneerUrl : /api/Html/pion.html
     * Data : [{"newsid":1300,"title":"渝北区总工会举办2016年工会专职工会干部培训班","EditDate":"2016/11/2 10:54:00"},{"newsid":1301,"title":"渝北区水利局工会开展防汛抢险拓展训练","EditDate":"2016/11/2 10:54:00"},{"newsid":1299,"title":"关于推进2016年度职工互助保险 参保工作的通知","EditDate":"2016/11/2 10:51:00"}]
     */

    private int Status;
    private String Message;
    private int CountNum;
    private String pioneerUrl;
    private List<DataBean> Data;

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

    public int getCountNum() {
        return CountNum;
    }

    public void setCountNum(int CountNum) {
        this.CountNum = CountNum;
    }

    public String getPioneerUrl() {
        return pioneerUrl;
    }

    public void setPioneerUrl(String pioneerUrl) {
        this.pioneerUrl = pioneerUrl;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * newsid : 1300
         * title : 渝北区总工会举办2016年工会专职工会干部培训班
         * EditDate : 2016/11/2 10:54:00
         */

        private int newsid;
        private String title;
        private String EditDate;

        private  String ModuName;

        public String getModuName() {
            return ModuName;
        }

        public void setModuName(String moduName) {
            ModuName = moduName;
        }

        public int getNewsid() {
            return newsid;
        }

        public void setNewsid(int newsid) {
            this.newsid = newsid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEditDate() {
            return EditDate;
        }

        public void setEditDate(String EditDate) {
            this.EditDate = EditDate;
        }
    }
}
