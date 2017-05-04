package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/4.
 */

public class BsznListModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 22
     * Data : [{"id":5,"pid":1,"category":"办理证件","pic":"/UpLoadFiles/image/20170419/20170419143223_3992.png","Enabled":"1","rank":1},{"id":4,"pid":1,"category":"医疗","pic":"/UpLoadFiles/image/20170419/20170419143223_2892.png","Enabled":"1","rank":2},{"id":7,"pid":1,"category":"婚育收养","pic":"/UpLoadFiles/image/20170419/20170419143223_4292.png","Enabled":"1","rank":3},{"id":8,"pid":1,"category":"公共事业","pic":"/UpLoadFiles/image/20170419/20170419143223_5792.png","Enabled":"1","rank":4},{"id":6,"pid":1,"category":"交通","pic":"/UpLoadFiles/image/20170419/20170419143223_4292.png","Enabled":"0","rank":5},{"id":3,"pid":1,"category":"就业","pic":"/UpLoadFiles/image/20170419/20170419151814_6752.png","Enabled":"1","rank":6},{"id":23,"pid":1,"category":"住房","pic":"/UpLoadFiles/image/20170419/20170419143223_1692.png","Enabled":"1","rank":7},{"id":24,"pid":1,"category":"社保","pic":"/UpLoadFiles/image/20170419/20170419143223_2292.png","Enabled":"1","rank":8},{"id":10,"pid":1,"category":"民族宗教","pic":"/UpLoadFiles/image/20170419/20170419143223_6492.png","Enabled":"0","rank":9},{"id":9,"pid":1,"category":"职业资格","pic":"/UpLoadFiles/image/20170419/20170419143223_6892.png","Enabled":"0","rank":10},{"id":11,"pid":2,"category":"企业开办","pic":"/UpLoadFiles/image/20170419/20170419143223_7892.png","Enabled":"1","rank":11},{"id":13,"pid":2,"category":"经营纳税","pic":"/UpLoadFiles/image/20170419/20170419143223_8892.png","Enabled":"1","rank":12},{"id":20,"pid":2,"category":"人力资源","pic":"/UpLoadFiles/image/20170419/20170419143224_4092.png","Enabled":"1","rank":13},{"id":14,"pid":2,"category":"招商引资","pic":"/UpLoadFiles/image/20170419/20170419143223_9892.png","Enabled":"0","rank":14},{"id":15,"pid":2,"category":"行业准营","pic":"/UpLoadFiles/image/20170419/20170419151220_2082.png","Enabled":"0","rank":15},{"id":16,"pid":2,"category":"设立变更","pic":"/UpLoadFiles/image/20170419/20170419143224_1292.png","Enabled":"1","rank":16},{"id":17,"pid":2,"category":"对外贸易","pic":"/UpLoadFiles/image/20170419/20170419143224_1692.png","Enabled":"0","rank":17},{"id":18,"pid":2,"category":"质量校检","pic":"/UpLoadFiles/image/20170419/20170419143224_2492.png","Enabled":"1","rank":18},{"id":19,"pid":2,"category":"安全生产","pic":"/UpLoadFiles/image/20170419/20170419143224_3092.png","Enabled":"0","rank":19},{"id":12,"pid":2,"category":"资质认定","pic":"/UpLoadFiles/image/20170419/20170419143223_8292.png","Enabled":"1","rank":20},{"id":21,"pid":2,"category":"土地房产","pic":"/UpLoadFiles/image/20170419/20170419151608_8962.png","Enabled":"0","rank":21},{"id":22,"pid":2,"category":"工程建设","pic":"/UpLoadFiles/image/20170419/20170419151608_9362.png","Enabled":"0","rank":22}]
     */

    private int Status;
    private String Message;
    private int CountNum;
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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 5
         * pid : 1
         * category : 办理证件
         * pic : /UpLoadFiles/image/20170419/20170419143223_3992.png
         * Enabled : 1
         * rank : 1
         */

        private int id;
        private int pid;
        private String category;
        private String pic;
        private String Enabled;
        private int rank;

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

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getEnabled() {
            return Enabled;
        }

        public void setEnabled(String Enabled) {
            this.Enabled = Enabled;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
