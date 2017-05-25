package com.zxt.zxt_phone.bean;

import com.zxt.zxt_phone.R;
import com.zxt.zxt_phone.bean.model.BsznModel;
import com.zxt.zxt_phone.bean.model.CommonModel;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.SimpleTitleTip;
import com.zxt.zxt_phone.view.customview.easytagdragview.bean.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class TipDataModel {

    //                    {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事", "办事指南"
//                                , "平安社区", "群团服务", "群攻平台", "精准帮扶"
//                                , "网格管理", "社区电商", "社区活动", "社区交流"
//                                , "家庭维修", "家教", "二手交易", "就业服务"
//                                , "智能家居", "快递代收", "公交路线", "充值缴费"
//                                , "家政保洁", "关于物业", "物业公告", "安全管理"
//                                , "故障报修", "纠纷调解", "评价调研"} ;

    private static String[] dragTips = {"社区概况", "党群风采", "诉求提交", "结果查询", "政策信息", "预约办事", "办事指南"};
    private static int[] dragTipsIcon = {R.drawable.ic_category_1, R.drawable.ic_category_2, R.drawable.ic_category_3,
            R.drawable.ic_category_4, R.drawable.ic_category_5, R.drawable.ic_category_6, R.drawable.ic_category_7};


    private static String[] addTips = {"平安社区", "群团服务", "群攻平台", "精准帮扶"
            , "网格管理", "社区电商", "社区活动", "社区交流"
            , "家庭维修", "家教", "二手交易", "就业服务"
            , "智能家居", "快递代收", "公交路线", "充值缴费"
            , "家政保洁", "关于物业", "物业公告", "安全管理"
            , "故障报修", "纠纷调解", "评价调研"};

    private static int[] addTipsIcon = {R.drawable.ic_category_8, R.drawable.ic_category_9, R.drawable.ic_category_10,
            R.drawable.ic_category_11, R.drawable.ic_category_12, R.drawable.ic_category_13, R.drawable.ic_category_14,
            R.drawable.ic_category_15, R.drawable.ic_category_16, R.drawable.ic_category_17,
            R.drawable.ic_category_18, R.drawable.ic_category_19, R.drawable.ic_category_20, R.drawable.ic_category_21,
            R.drawable.ic_category_22, R.drawable.ic_category_23, R.drawable.ic_category_24,
            R.drawable.ic_category_25, R.drawable.ic_category_26, R.drawable.ic_category_27, R.drawable.ic_category_28
            , R.drawable.ic_category_29, R.drawable.ic_category_30, R.drawable.ic_category_31};


    public static List<Tip> getDragTips() {
        List<Tip> result = new ArrayList<>();
        for (int i = 0; i < dragTips.length; i++) {
            String temp = dragTips[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setIcon(dragTipsIcon[i]);
            tip.setId(i);
            result.add(tip);
        }
        return result;
    }

    public static List<Tip> getAddTips() {
        List<Tip> result = new ArrayList<>();
        for (int i = 0; i < addTips.length; i++) {
            String temp = addTips[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setIcon(addTipsIcon[i]);
            tip.setId(i + dragTips.length);
            result.add(tip);
        }
        return result;
    }

    /**
     * 首页功能图标
     */
    private static String[] main_names = {"政策信息", "诉求提交", "结果查询", "办事指南", "社区活动",  "故障报修", "社区购物",  "全部分类"};
    private static int[] main_icon = {R.drawable.zw_zcxx, R.drawable.zw_sqtj, R.drawable.zw_jgcx,R.drawable.zw_bszn,
            R.drawable.sh_sqhd, R.drawable.sh_gzbx,R.drawable.sh_sqgw,R.drawable.all};

    public static List<BsznModel> getAll() {
        List<BsznModel> result = new ArrayList<>();
        for (int i = 0; i < main_names.length; i++) {
            BsznModel remenModel = new BsznModel();
            remenModel.setImage(main_icon[i]);
            remenModel.setText(main_names[i]);
            result.add(remenModel);
        }
        return result;
    }




    /**
     * 政务服务功能
     */
    private static String[] zw_names = {"政策信息", "诉求提交", "结果查询", "预约办事", "党群风采", "群团服务", "网格管理","办事指南"};
    private static int[] zw_icon = {R.drawable.zw_zcxx, R.drawable.zw_sqtj, R.drawable.zw_jgcx,
            R.drawable.zw_yybs, R.drawable.zw_dqfc, R.drawable.zw_qtfw,R.drawable.zw_wggl,R.drawable.zw_bszn};

    public static List<BsznModel> getGovernment() {
        List<BsznModel> result = new ArrayList<>();
        for (int i = 0; i < zw_names.length; i++) {
            BsznModel remenModel = new BsznModel();
            remenModel.setImage(zw_icon[i]);
            remenModel.setText(zw_names[i]);
            result.add(remenModel);
        }
        return result;
    }


    /**
     * 热门办事列表
     */
    private static String[] remen_names = {"社保办理", "证件办理", "资质认定", "就业招聘", "交通罚单", "学生转学"};
    private static int[] remen_icon = {R.drawable.bszn_sbbl, R.drawable.bszn_zjbl, R.drawable.bszn_zjrd,
            R.drawable.bszn_jyzp, R.drawable.bszn_jtfd, R.drawable.bszn_xszx,};

    public static List<CommonModel> getReMen() {
        List<CommonModel> result = new ArrayList<>();
        for (int i = 0; i < remen_names.length; i++) {
            CommonModel remenModel = new CommonModel();
            remenModel.setIcon(remen_icon[i]);
            remenModel.setName(remen_names[i]);
            result.add(remenModel);
        }
        return result;
    }


    /**
     * 生活功能
     */
    private static String[] life_names = {"社区活动", "故障报修", "家政服务", "充值缴费", };
    private static int[] life_icon = {R.drawable.sh_sqhd, R.drawable.sh_gzbx, R.drawable.sh_jzfw,
            R.drawable.sh_czjf  };

    public static List<CommonModel> getLife() {
        List<CommonModel> result = new ArrayList<>();
        for (int i = 0; i < life_names.length; i++) {
            CommonModel remenModel = new CommonModel();
            remenModel.setIcon(life_icon[i]);
            remenModel.setName(life_names[i]);
            result.add(remenModel);
        }
        return result;
    }


    private static String[] life_h_names = {"二手交易", "一键上门", "社区活动", };
    private static int[] life_h_icon = {R.drawable.life_1, R.drawable.life_2, R.drawable.life_3,
             };

    public static List<BsznModel> getLifeH() {
        List<BsznModel> result = new ArrayList<>();
        for (int i = 0; i < life_h_names.length; i++) {
            BsznModel remenModel = new BsznModel();
            remenModel.setImage(life_h_icon[i]);
            remenModel.setText(life_h_names[i]);
            result.add(remenModel);
        }
        return result;
    }





    /**
     * 电商功能列表
     */
    private static String[] shop_names = {"蔬菜水果", "肉禽奶蛋", "粮油调味", "酒水茶饮", "全部分类"};
    private static int[] shop_icon = {R.drawable.shop_scsg, R.drawable.shop_rqnd, R.drawable.shop_lytw,
            R.drawable.shop_jscy, R.drawable.all };

    public static List<CommonModel> getShop() {
        List<CommonModel> result = new ArrayList<>();
        for (int i = 0; i < shop_names.length; i++) {
            CommonModel remenModel = new CommonModel();
            remenModel.setIcon(shop_icon[i]);
            remenModel.setName(shop_names[i]);
            result.add(remenModel);
        }
        return result;
    }

}
