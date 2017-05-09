package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hkc on 2017/5/9.
 */

public class listYybsModel implements Serializable{

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 11
     * Data : [{"id":"3","genre":"教育","Data":[{"id":"26","genre":"开展面向社会的爱国主义宣传教育活动"},{"id":"28","genre":"普通高中学业水平考试成绩查询"}]},{"id":"4","genre":"社保"},{"id":"5","genre":"就业"},{"id":"6","genre":"医疗"},{"id":"7","genre":"住房"},{"id":"8","genre":"交通"},{"id":"9","genre":"婚育收养"},{"id":"10","genre":"公共事业"},{"id":"11","genre":"证件办理"},{"id":"12","genre":"职业资格"},{"id":"13","genre":"民族宗教","Data":[{"id":"305","genre":"其他固定宗教活动处所名录"}]}]
     */

    private int Status;
    private String Message;
    private int CountNum;
    private List<DataBeanX> Data;

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

    public List<DataBeanX> getData() {
        return Data;
    }

    public void setData(List<DataBeanX> Data) {
        this.Data = Data;
    }

    public static class DataBeanX implements Serializable{
        /**
         * id : 3
         * genre : 教育
         * Data : [{"id":"26","genre":"开展面向社会的爱国主义宣传教育活动"},{"id":"28","genre":"普通高中学业水平考试成绩查询"}]
         */

        private String id;
        private String genre;
        private List<DataBean> Data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGenre() {
            return genre;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public List<DataBean> getData() {
            return Data;
        }

        public void setData(List<DataBean> Data) {
            this.Data = Data;
        }

        public static class DataBean implements Serializable{
            /**
             * id : 26
             * genre : 开展面向社会的爱国主义宣传教育活动
             */

            private String id;
            private String genre;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getGenre() {
                return genre;
            }

            public void setGenre(String genre) {
                this.genre = genre;
            }
        }
    }
}
