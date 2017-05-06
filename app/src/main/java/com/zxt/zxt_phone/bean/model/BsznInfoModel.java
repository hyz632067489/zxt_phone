package com.zxt.zxt_phone.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hyz on 2017/3/12.
 * powered by company
 */

public class BsznInfoModel implements Serializable{


    /**
     * Status : 1
     * Message : 消息数据加载成功
     * CountNum : 5
     * Data : [{"Eid":14,"categoryid":5,"serve":"独生子女父母光荣证","procedures":"社区受理→街道审核办理","materials":"（一）夫妻双方身份证、户口簿原件及复印件（二）申请人一寸免冠近照一张（三）夫妻一方所在法人单位或者双方户籍地所在村（居）民委员会意见（四）再婚的还需提供离婚证及离婚协议书或者离婚判决书、调解书原件和复印件。","Responsible":"社事科","Cost":0,"number":"86812412","shzt":""},{"Eid":15,"categoryid":5,"serve":"再生育服务证","procedures":"社区受理→街道审核→区级办理","materials":"一）夫妻双方身份证原件及复印件。（二）结婚证原件及复印件。再婚的需提供是否共同育有子女的证明和离婚证及离婚协议书或者离婚判决书、调解书。（三）户口簿原件及复印件（包括户主页、人口增减页、申请人本人人口登记页及小孩登记页或者出生医学证明）。（四）夫妻双方婚育情况证明。户籍是本市的由户籍地的村民委员会、居民委员会或者法人单位出具婚育情况证明，户籍是市外的由户籍地的村民委员会、居民委员会和乡镇人民政府、街道办事处出具婚育情况证明。（五）女方一寸免冠近照2张。（六）以子女病残为由申请再生育的，应提供市或者区县（自治县）卫生计生委组织的病残儿医学鉴定结论，鉴定结论符合再生育条件。","Responsible":"社事科","Cost":0,"number":"86812412","shzt":""},{"Eid":19,"categoryid":5,"serve":"80岁以上老年人领取长寿营养补助费资格认证","procedures":"本人申请→社区调查初审→街道复核→区民政局审定","materials":"（1）户口本;（2）身份证","Responsible":"社事科","Cost":0,"number":"86812412","shzt":""},{"Eid":46,"categoryid":5,"serve":"就业失业登记证","procedures":"1.向社区居委会申请，并由居委会出具未就业证明；2.街道社保所办理","materials":"1、常住地居委会出具未就业证明或单位解除劳动合同；2、本人身份证、户口簿（主页、增减页、本人页）复印件；3、2寸登记照2张","Responsible":"社保所","Cost":0,"number":"86812337","shzt":"0"},{"Eid":49,"categoryid":5,"serve":"渝北区图书馆龙塔分馆一卡通读者证办理","procedures":"申请人领取一卡通申请表并填写→向图书馆管理员出示有效证件，缴纳所需办理读者证类型押金→图书管理员进入数字图书馆平台→流通服务→办证服务→申请人信息录入电脑并激活读者证","materials":"（1）居民身份证；（2）护照；（3）台胞回乡证；（4）军官证等有效证件，未领身份证的少儿可持户口簿或出生证办理。","Responsible":"文化服务中心","Cost":0,"number":"67559823","shzt":"0"}]
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

    public static class DataBean implements Serializable{
        /**
         * Eid : 14
         * categoryid : 5
         * serve : 独生子女父母光荣证
         * procedures : 社区受理→街道审核办理
         * materials : （一）夫妻双方身份证、户口簿原件及复印件（二）申请人一寸免冠近照一张（三）夫妻一方所在法人单位或者双方户籍地所在村（居）民委员会意见（四）再婚的还需提供离婚证及离婚协议书或者离婚判决书、调解书原件和复印件。
         * Responsible : 社事科
         * Cost : 0
         * number : 86812412
         * shzt :
         */

        private int Eid;
        private int categoryid;
        private String serve;
        private String procedures;
        private String materials;
        private String Responsible;
        private int Cost;
        private String number;
        private String shzt;

        public int getEid() {
            return Eid;
        }

        public void setEid(int Eid) {
            this.Eid = Eid;
        }

        public int getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }

        public String getServe() {
            return serve;
        }

        public void setServe(String serve) {
            this.serve = serve;
        }

        public String getProcedures() {
            return procedures;
        }

        public void setProcedures(String procedures) {
            this.procedures = procedures;
        }

        public String getMaterials() {
            return materials;
        }

        public void setMaterials(String materials) {
            this.materials = materials;
        }

        public String getResponsible() {
            return Responsible;
        }

        public void setResponsible(String Responsible) {
            this.Responsible = Responsible;
        }

        public int getCost() {
            return Cost;
        }

        public void setCost(int Cost) {
            this.Cost = Cost;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getShzt() {
            return shzt;
        }

        public void setShzt(String shzt) {
            this.shzt = shzt;
        }
    }
}
