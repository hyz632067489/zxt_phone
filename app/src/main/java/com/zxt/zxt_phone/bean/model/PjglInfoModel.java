package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/4/15.
 */

public class PjglInfoModel {


    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 1
     * Data : [{"id":"1","content":"一、投票事项：为了业主有一个更好的生活环境，现对小区中庭喷水池进行整修。二、预计工期：5月1号至5月20号 三、费用预期：3-3.5万元","deadline":"2017/5/15 0:00:00","pjlx":"2","Data":[{"id":"1","aname":"微.Home","count":"1"},{"id":"2","aname":"WEHOME","count":"1"},{"id":"3","aname":"微港湾","count":"0"},{"id":"4","aname":"心家","count":"0"},{"id":"5","aname":"远洋微社区","count":"0"},{"id":"6","aname":"蓝海之家","count":"0"},{"id":"7","aname":"远洋.生活邦","count":"0"},{"id":"8","aname":"远亲.近邻","count":"0"},{"id":"9","aname":"远\u201c杜\u201d重洋","count":"0"},{"id":"10","aname":"馨窝","count":"0"},{"id":"11","aname":"茅舍见闻","count":"0"}]}]
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

    public static class DataBeanX {
        /**
         * id : 1
         * content : 一、投票事项：为了业主有一个更好的生活环境，现对小区中庭喷水池进行整修。二、预计工期：5月1号至5月20号 三、费用预期：3-3.5万元
         * deadline : 2017/5/15 0:00:00
         * pjlx : 2
         * Data : [{"id":"1","aname":"微.Home","count":"1"},{"id":"2","aname":"WEHOME","count":"1"},{"id":"3","aname":"微港湾","count":"0"},{"id":"4","aname":"心家","count":"0"},{"id":"5","aname":"远洋微社区","count":"0"},{"id":"6","aname":"蓝海之家","count":"0"},{"id":"7","aname":"远洋.生活邦","count":"0"},{"id":"8","aname":"远亲.近邻","count":"0"},{"id":"9","aname":"远\u201c杜\u201d重洋","count":"0"},{"id":"10","aname":"馨窝","count":"0"},{"id":"11","aname":"茅舍见闻","count":"0"}]
         */

        private String id;
        private String content;
        private String deadline;
        private String pjlx;
        private List<DataBean> Data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDeadline() {
            return deadline;
        }

        public void setDeadline(String deadline) {
            this.deadline = deadline;
        }

        public String getPjlx() {
            return pjlx;
        }

        public void setPjlx(String pjlx) {
            this.pjlx = pjlx;
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
             * aname : 微.Home
             * count : 1
             */

            private String id;
            private String aname;
            private String count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAname() {
                return aname;
            }

            public void setAname(String aname) {
                this.aname = aname;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
