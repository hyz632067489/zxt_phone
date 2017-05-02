package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/4/28.
 */

public class WyggModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 11
     * Data : [{"id":4,"Aid":2,"Atitle":"鲁能区3栋李某拾到手机一部","Atime":"2017/6/3"},{"id":6,"Aid":2,"Atitle":"鲁能社区7栋唐某某见义勇为拯救溺水儿童","Atime":"2017/6/4"},{"id":20,"Aid":1,"Atitle":"鲁能社区3栋在4月20日将停水","Atime":"2017/04/02"},{"id":21,"Aid":2,"Atitle":"渝北区将在4月20号日大面积停水半天","Atime":"2017/04/20"},{"id":22,"Aid":1,"Atitle":"渝北区将在4月20号日大面积停水半天","Atime":"2017/04/20"},{"id":23,"Aid":1,"Atitle":"渝北区将在4月20号日大面积停水半天","Atime":"2017/04/20"},{"id":24,"Aid":1,"Atitle":"渝北区将在4月20号日大面积停水半天","Atime":"2017/04/20"},{"id":25,"Aid":1,"Atitle":"超负荷的说法吧","Atime":"2017/04/02"},{"id":26,"Aid":1,"Atitle":"①6突然退货","Atime":"2017/05/12"},{"id":27,"Aid":1,"Atitle":"JGRTDGVB","Atime":"2017/04/02"}]
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
         * id : 4
         * Aid : 2
         * Atitle : 鲁能区3栋李某拾到手机一部
         * Atime : 2017/6/3
         */

        private int id;
        private int Aid;
        private String Atitle;
        private String Atime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAid() {
            return Aid;
        }

        public void setAid(int Aid) {
            this.Aid = Aid;
        }

        public String getAtitle() {
            return Atitle;
        }

        public void setAtitle(String Atitle) {
            this.Atitle = Atitle;
        }

        public String getAtime() {
            return Atime;
        }

        public void setAtime(String Atime) {
            this.Atime = Atime;
        }
    }
}
