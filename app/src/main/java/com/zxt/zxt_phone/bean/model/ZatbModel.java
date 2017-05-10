package com.zxt.zxt_phone.bean.model;

import java.util.List;

/**
 * Created by hkc on 2017/5/10.
 */

public class ZatbModel {

    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 5
     * pioneerUrl : /api/Html/pion.html
     * Data : [{"id":1,"title":"1名学生被骗3800元","content":"29日，某学生在QQ上收到一条其亲戚发送的急需借钱信息，并发送了一个支付宝链接。该同学点击进入后，直接通过支付宝将钱汇入指定账户，后发现被骗。","time":"2015/1/29 0:00:00"},{"id":5,"title":"2名学生分别被骗1900元、2000元","content":"29日傍晚，校园发生2起冒充香港、新加坡籍华人（一般为两人配合）主动与学生搭讪，并以需要帮助为由，实施诈骗。犯罪嫌疑人穿着时尚，打扮新潮，在校园内选择单独行走的学生，谎称自己是香港、新加坡籍华人，以来访友或游玩为由，问路问事，用粤语主动搭讪。在骗得学生信任后，嫌疑人会谎称身上没有现金、只有其他币种、钱包被盗、银行卡被锁等，无法维持生计，开始向学生借少量现金，并答应以劣质物资为抵押，许诺还钱。学生出于好心，认为钱不多，有物资抵押，于是信以为真，借钱给对方。嫌疑人得逞后，又会以钱不够为由，问学生是否有银行卡，卡内有多少钱，能不能借用银行卡，打电话让人转账到卡上，叫学生查询，然后取现给他。学生查到转账记录后，认为钱已到账（一般到账的钱会比对方要求取现的钱多几倍），于是提现给对方。得逞后，嫌疑人便迅速离开","time":"2015/1/29 0:00:00"},{"id":2,"title":"14名学生各被盗手机1部，累计价值超过万元","content":"30日下午，某社团在南区公寓运动场组织群体活动，4名学生将各自手机放在一个书包内，置于运动场旁边，无人看守，导致被盗。参与活动的人员，未发现嫌疑人任何线索。","time":"2015/1/30 0:00:00"},{"id":4,"title":"多名学生报名参加英语、演讲等教育咨询公司，交纳较高培训费","content":"培训机构抓住学生涉世不深，急于增强个人素质心理，利用本校高年级学生，深入学生寝室，游说低年级学生参加社团及相关组织，收取少量会员费。入会后，公司会组织各类群体活动，统一会员思想，逐步推荐各类培训，收取较高培训费用，一般为3980元、4980元。同时，公司会以提成为诱惑，鼓励各会员到校内进行宣传，招收其他同学加入团队，参加各级培训","time":"2015/2/15 0:00:00"},{"id":3,"title":"1名学生被盗手机1部，价值600元","content":"28日中午，某学生在南区食堂排队打饭时，放在外衣口袋的手机被盗。","time":"2015/2/28 0:00:00"}]
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
         * title : 1名学生被骗3800元
         * content : 29日，某学生在QQ上收到一条其亲戚发送的急需借钱信息，并发送了一个支付宝链接。该同学点击进入后，直接通过支付宝将钱汇入指定账户，后发现被骗。
         * time : 2015/1/29 0:00:00
         */

        private int id;
        private String title;
        private String content;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
