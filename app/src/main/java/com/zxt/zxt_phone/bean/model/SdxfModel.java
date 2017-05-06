package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/5.
 */

public class SdxfModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 5
     * pioneerUrl : /api/Html/pion.html
     * Data : [{"id":1,"pid":5,"CoverImg":"/UpLoadFiles/image/20170411/20170411192556_2720.png","title":"优秀党小组长\u2014万应勤","intro":"作为鲁能西路社区第五党小组长的她，积极参与社区建设...","shzt":""},{"id":2,"pid":5,"CoverImg":"/UpLoadFiles/image/20170411/20170411192615_3610.png","title":"优秀党员\u2014彭巩固","intro":"他是社区的一名党员，也是一名书法爱好者...","shzt":""},{"id":3,"pid":5,"CoverImg":"/UpLoadFiles/image/20170411/20170411192634_3010.png,/UpLoadFiles/image/20170411/20170411192641_5630.png","title":"最赞党员志愿者\u2014\u2014李天永、余从竹","intro":"们是社区一名普通的党员，也是社区的一...","shzt":""},{"id":4,"pid":5,"CoverImg":"/UpLoadFiles/image/20170411/20170411192719_0030.png","title":"最美网格员\u2014\u2014彭国清","intro":"她是鲁能星城五街区的一名热心网格管家。每周二是彭国清的固定...","shzt":""},{"id":5,"pid":5,"CoverImg":"/UpLoadFiles/image/20170411/20170411192745_6450.png","title":"最美家庭\u2014\u2014杨玲","intro":"她的家和千千千万万普通家庭一样，没有什么轰轰烈烈...","shzt":""}]
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
         * id : 1
         * pid : 5
         * CoverImg : /UpLoadFiles/image/20170411/20170411192556_2720.png
         * title : 优秀党小组长—万应勤
         * intro : 作为鲁能西路社区第五党小组长的她，积极参与社区建设...
         * shzt :
         */

        private int id;
        private int pid;
        private String CoverImg;
        private String title;
        private String intro;
        private String shzt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getCoverImg() {
            return CoverImg;
        }

        public void setCoverImg(String CoverImg) {
            this.CoverImg = CoverImg;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getShzt() {
            return shzt;
        }

        public void setShzt(String shzt) {
            this.shzt = shzt;
        }
    }
}
