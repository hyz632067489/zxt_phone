package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/4.
 */

public class ZyzfwModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 3
     * Url : /api/Html/pion.html
     * Data : [{"id":7,"title":"开展扶贫帮困献爱心活动11","CoverImg":"/UpLoadFiles/image/20170414/20170414153207_5011.png","time":"2017/5/9 0:00:00"},{"id":3,"title":"鲁西社区","CoverImg":"/UpLoadFiles/image/20170410/20170410190643_9733.png","time":"2016/12/12 0:00:00"},{"id":1,"title":"龙塔街道鲁能西路社区志愿者招募通知","CoverImg":"","time":"2015/12/2 0:00:00"}]
     */

    private int Status;
    private String Message;
    private int CountNum;
    private String Url;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 7
         * title : 开展扶贫帮困献爱心活动11
         * CoverImg : /UpLoadFiles/image/20170414/20170414153207_5011.png
         * time : 2017/5/9 0:00:00
         */

        private int id;
        private String title;
        private String CoverImg;
        private String time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverImg() {
            return CoverImg;
        }

        public void setCoverImg(String CoverImg) {
            this.CoverImg = CoverImg;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
