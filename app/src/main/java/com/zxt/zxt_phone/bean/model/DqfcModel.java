package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hyz on 2017/4/7.
 * powered by company
 */

public class DqfcModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 6
     * Data : [{"id":1,"title":"党员在小区里义务巡逻","CoverImg":"http://192.168.1.222:8099/Government/image/9.jpg","shzt":""},{"id":2,"title":"党员志愿者带领小区文化队伍开展国防教育宣传活动","CoverImg":"http://192.168.1.222:8099/Government/image/10.jpg","shzt":"1"},{"id":3,"title":"社区党员参与\u201d相约星期四，谈谈烦心事\u201c活动，在小区收集群众意见","CoverImg":"http://192.168.1.222:8099/Government/image/11.jpg","shzt":"1"},{"id":4,"title":"社区开展廉政文化进小区","CoverImg":"http://192.168.1.222:8099/Government/image/12.jpg","shzt":"0"},{"id":5,"title":"社区主题党日\u2014\u2014民主评议党员","CoverImg":"http://192.168.1.222:8099/Government/image/13.jpg","shzt":"0"},{"id":6,"title":"主题党日\u2014\u2014党员开展洁净家园活动","CoverImg":"http://192.168.1.222:8099/Government/image/14.jpg","shzt":"0"}]
     */

    private int Status;
    private String Message;
    private int CountNum;
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

    public int getCountNum() {
        return CountNum;
    }

    public void setCountNum(int CountNum) {
        this.CountNum = CountNum;
    }

    public List<DataNewsModel> getData() {
        return Data;
    }

    public void setData(List<DataNewsModel> Data) {
        this.Data = Data;
    }

    public static class DataNewsModel {
        /**
         * id : 1
         * title : 党员在小区里义务巡逻
         * CoverImg : http://192.168.1.222:8099/Government/image/9.jpg
         * shzt :
         */

        private int id;
        private String title;
        private String CoverImg;
        private String shzt;

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

        public String getShzt() {
            return shzt;
        }

        public void setShzt(String shzt) {
            this.shzt = shzt;
        }
    }
}
