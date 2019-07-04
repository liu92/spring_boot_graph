package com.graph.demo.example;

import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;

/**
 * @ClassName DataModelCreat
 * @Description TODO
 * @Author liuwanlin
 * @Date 2019/7/3 16:27
 **/
public class DataModelCreat {
    public static void main(String[] args) {
            //使用配置文件来连接到相应服务器上的存储数据库
            JanusGraph graph = JanusGraphFactory
                    .open("D:\\space\\spring_boot_graph\\src\\main\\resources\\jgex-cql.properties");
            //Create Schema
            JanusGraphManagement mgmt  = graph.openManagement();

            /**
             *t1_crm_customer_open_card.信用卡卡号      客户开卡表
             *t1_crm_customer_bill.信用卡卡号           信用卡卡号
             *t1_crm_stop_elec.信用卡卡号               停止电催表
             *t1_crm_commission_case.信用卡卡号         委案表
             *t1_crm_ovedue.信用卡卡号                  逾期表
             *t1_crm_natural_payoff.信用卡卡号          自然结清表
             *t1_crm_legal_support.信用卡卡号           法务支持
             *t1_crm_tm_loan.信用卡卡号                 腾铭贷款合同
             *t1_crm_elec_payment_register.信用卡卡号   电催回款登记
             */

            /**
             * 定义银行顶点属性
             */
            VertexLabel bankCard = mgmt.makeVertexLabel("bank_card").make();
            /**
             * 设置银行卡属性 卡号
             */
            PropertyKey credit_card_number = mgmt.makePropertyKey("credit_card_number").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             *开类型
             */
            PropertyKey bankCardTye = mgmt.makePropertyKey("bank_card_type").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             * 设置银行卡属性 编码
             */
            PropertyKey bankCode = mgmt.makePropertyKey("bank_code").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             * 信用卡卡表上的营销代码   t1_oa_icbc_base_info
             */
            PropertyKey dscode = mgmt.makePropertyKey("dscode").dataType(String.class).cardinality(Cardinality.SINGLE).make();

            /**
             *
             * t1_crm_ovedue.银行预留电话 逾期表
             */
            PropertyKey bank_reserve_phone = mgmt.makePropertyKey("bank_reserve_phone").dataType(String.class).cardinality(Cardinality.SINGLE).make();


            /**
             * 设置银行卡边. 手机
             * 属性紧急联系人手机号
             */
            EdgeLabel e_contact_phone = mgmt.makeEdgeLabel("e_contact_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * 联系电话
             * t1_oa_loan_requests.联系人电话1       贷款申请表
             * t1_oa_loan_requests.联系电话2
             */
            EdgeLabel contact_phone = mgmt.makeEdgeLabel("contact_phone").multiplicity(Multiplicity.MULTI).make();

            /**
             * 银行卡相关边 （公司、单位）
             * 打款账号
             * t1_crm_no_repay_manage.客户打款账号  未回款管理表
             */
            EdgeLabel pay_account = mgmt.makeEdgeLabel("pay_account").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_elec_payment_register.打款说明    电催回款登记表
             */
            EdgeLabel payment_instructions = mgmt.makeEdgeLabel("payment_instructions").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_payoff.邮寄地址                   结清表
             */
            EdgeLabel express_address = mgmt.makeEdgeLabel("express_address").multiplicity(Multiplicity.MULTI).make();




		/*
		t1_crm_tm_loan.主贷人手机号码1                腾铭贷款合同
		t1_crm_tm_loan.主贷人手机号码2
		t1_crm_tm_loan.业务员手机号码
		t1_crm_tm_loan.紧急联系人手机号码
		t1_crm_tm_loan.原主贷人手机号码
		t1_crm_tm_loan.配偶共同还款人手机号码
		t1_crm_tm_loan.担保人手机号码
		t1_crm_tm_loan.紧急联系人2手机号码

		t1_crm_ecel_manage.手机号码1                  电催管理
		t1_crm_ecel_manage.手机号码2
		t1_crm_ecel_manage.紧急联系人手机号码
        t1_crm_ecel_manage.原主贷人手机号码
        t1_crm_ecel_manage.配偶共同还款人手机号码
        t1_crm_ecel_manage.担保人手机号码
        t1_crm_ecel_manage.紧急联系人2手机号码

        t1_crm_payoff.领取人收件人手机号码                结清表
		t1_crm_payoff.主贷人手机号码
		t1_crm_payoff.主贷人手机号码2

		t1_crm_customer_loan.业务员手机号码              客户贷款表

		t1_crm_complain_manage.手机号码1                投诉处理
		t1_crm_complain_manage.手机号码2
		t1_crm_complain_manage.紧急联系人手机号码
		t1_crm_complain_manage.原主贷人手机号码
		t1_crm_complain_manage.配偶共同还款人手机号码
		t1_crm_complain_manage.担保人手机号码
		t1_crm_complain_manage.紧急联系人2手机号码

		t1_crm_no_repay_manage.主贷人手机号码1            未回款管理
		t1_crm_no_repay_manage.主贷人手机号码2
        t1_crm_no_repay_manage.业务员手机号码
		t1_crm_no_repay_manage.原主贷人手机号码
		t1_crm_no_repay_manage.配偶共同还款人手机号码
		t1_crm_no_repay_manage.担保人手机号码
		t1_crm_no_repay_manage.紧急联系人1手机号码
		t1_crm_no_repay_manage.紧急联系人2手机号码


		t1_crm_natural_payoff.领取人收件人手机号码          自然结清表
		t1_crm_natural_payoff.主贷人手机号码
		t1_crm_natural_payoff.主贷人手机号码2

        t1_crm_gps_monitoring.主贷人手机号码1              GPS日常监控
		t1_crm_gps_monitoring.主贷人手机号码2

		t1_oa_loan_requests.担保人手机号                   贷款申请

		t1_oa_user.mobile                               【新】用户表

		t1_oa_score_client.mobile                        FICO征信表
		*/

            /**
             * 定义手机顶点
             */
            VertexLabel mobile_phone = mgmt.makeVertexLabel("mobile_phone").make();
            /**
             * 定义顶点属性 手机号
             */
            PropertyKey phone_number = mgmt.makePropertyKey("phone_number").dataType(Integer.class).cardinality(Cardinality.SET).make();
            /**
             * 手机设备token
             * t1_oa_user.device_token  新用户表
             */
            PropertyKey device_token = mgmt.makePropertyKey("device_token").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             * 手机设备系统
             * t1_oa_user.device_os
             */
            PropertyKey device_os = mgmt.makePropertyKey("device_os").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             * 手机设备系统版本
             * t1_oa_user.device_os_version
             */
            PropertyKey device_os_version = mgmt.makePropertyKey("device_os_version").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            //运营商 、IMEI2 、IMEI1


            /**
             * 定义手机相关边属性
             * t1_crm_tm_loan.紧急联系人手机号码
             */
            EdgeLabel e_contact_phone1 = mgmt.makeEdgeLabel("e_contact_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_tm_loan.紧急联系人2手机号码
             */
            EdgeLabel e_contact_phone2 = mgmt.makeEdgeLabel("e_contact_phone2").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_payoff.主贷人手机号码1
             */
            EdgeLabel primary_lender_phone1 = mgmt.makeEdgeLabel("primary_lender_phone1").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_payoff.主贷人手机号2
             */
            EdgeLabel primary_lender_phone2 = mgmt.makeEdgeLabel("primary_lender_phone2").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_customer_loan.业务员手机号码
             */
            EdgeLabel salesman_phone = mgmt.makeEdgeLabel("salesman_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_tm_loan.原主贷人手机号码
             */
            EdgeLabel o_lender_phone = mgmt.makeEdgeLabel("o_lender_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_tm_loan.配偶共同还款人手机号码
             */
            EdgeLabel spouse_common_phone = mgmt.makeEdgeLabel("spouse_common_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_tm_loan.担保人手机号码
             */
            EdgeLabel guarantor_phone = mgmt.makeEdgeLabel("guarantor_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_payoff.领取人收件人手机号码
             */
            EdgeLabel recipients_phone = mgmt.makeEdgeLabel("recipients_phone").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_complain_manage.主贷人单位电话
             */
            EdgeLabel primary_lender_company_phone = mgmt.makeEdgeLabel("primary_lender_company_phone").multiplicity(Multiplicity.MULTI).make();



		/*
		t1_crm_tm_loan.主贷人身份证号码                           腾铭贷款合同表
		t1_crm_tm_loan.原主贷人身份证号码
		t1_crm_dy_elec_record.主贷人身份证号码                    迪扬电催记录表
		t1_crm_ecel_manage.身份证号码                            电催管理表
		t1_crm_ecel_manage.原主贷人身份证号码
		t1_crm_elec_payment_register.主贷人身份证号码             电催回款登记表
		t1_crm_home_visit_form.主贷人身份证号码                   家访单表
		t1_crm_payoff.主贷人身份证号码                            结清表
		t1_crm_customer_loan.主贷人身份证号码                     客户贷款表
		t1_crm_customer_open_card.主贷人身份证号码                客户开卡表
		t1_crm_recieve_car.主贷人身份证号码                       收车表
		t1_crm_stop_elec.主贷人身份证号码                         停止电催表
		t1_crm_complain_manage.主贷人身份证号码                   投诉处理
		t1_crm_complain_manage.原主贷人身份证号码
		t1_crm_commission_case.主贷人身份证号码                   委案表
		t1_crm_no_repay_manage.主贷人身份证号码                   未回款管理
		t1_crm_no_repay_manage.原主贷人身份证号码
		t1_crm_ovedue.主贷人身份证号码                            逾期表
		t1_crm_natural_payoff.主贷人身份证号码                    自然结清表
		t1_crm_dispatch_order.主贷人身份证号码                    派单信息表
		t1_crm_cancel_dispatch.主贷人身份证号码                   取消派单表
		t1_crm_return_dispatch.主贷人身份证号码                   派单退回表
		t1_crm_negotiation.主贷人身份证号码                       谈判表
		t1_crm_storage.主贷人身份证号码                           入库表
		t1_crm_out_storage.主贷人身份证号码                       出库单
		t1_crm_vehicle_disposal.主贷人身份证号码                  车辆处置
		t1_crm_legal_support.主贷人身份证号码                     法务支持
		t1_crm_gps_monitoring.主贷人身份证号码                    GPS日常监控

		t1_oa_loan_requests.guarantor_credential(担保人身份证)   贷款申请
		t1_oa_loan_requests.car_seller_credential(前车主身份证号)

		t1_oa_score_client.IDCARD                             FICO征信表
        */

            /**
             * 定义证件顶点
             */
            VertexLabel certificates = mgmt.makeVertexLabel("certificates").make();
            /**
             * 证件类型 0:身份证 1:护照 2:军官证 3:士兵证 4:回乡证 5:临时身份证 6:户口本 7:其他 9:警官证
             * 定义顶点属性
             */
            /**
             * t1_oa_icbc_base_info.证件号码   工行e分期基础信息表
             */
            PropertyKey certificates_number = mgmt.makePropertyKey("certificates_number").dataType(String.class).cardinality(Cardinality.SINGLE).make();
            /**
             * t1_oa_icbc_base_info.证件类型
             */
            PropertyKey certificates_type = mgmt.makePropertyKey("certificates_type").dataType(String.class).cardinality(Cardinality.SINGLE).make();

            /**
             * t1_oa_user.身份证有效期开始时间  新用户表
             */
            PropertyKey credential_start_date = mgmt.makePropertyKey("credential_start_date").dataType(Long.class).cardinality(Cardinality.SINGLE).make();
            /**
             * t1_oa_user.身份证过期时间
             */
            PropertyKey credential_expire_date = mgmt.makePropertyKey("credential_expire_date").dataType(Long.class).cardinality(Cardinality.SINGLE).make();


            /**
             *  定义边
             *
             * t1_crm_tm_loan.主贷人身份证号码     腾铭贷款合同
             */
            EdgeLabel id_card = mgmt.makeEdgeLabel("id_card").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_crm_recieve_car.驾驶证         收车表
             */
            EdgeLabel driver_license = mgmt.makeEdgeLabel("driver_license").multiplicity(Multiplicity.MULTI).make();



            /**
             定义建筑顶点
             （公司/住宅 地址）
             */
            VertexLabel building = mgmt.makeVertexLabel("building").make();
            /**
             定义顶点属性
             省
             */
            PropertyKey name = mgmt.makePropertyKey("province").dataType(String.class).cardinality(Cardinality.SET).make();
            /**
             * 城市
             */
            PropertyKey address = mgmt.makePropertyKey("city").dataType(String.class).cardinality(Cardinality.SET).make();
            /**
             * 地区
             */
            PropertyKey area = mgmt.makePropertyKey("area").dataType(String.class).cardinality(Cardinality.SET).make();


            /**
             *定义边
             *t1_oa_company.address(公司地址)                收款公司（垫款公司主体）
             * 办公地址
             */
            EdgeLabel office_address = mgmt.makeEdgeLabel("office_address").multiplicity(Multiplicity.MULTI).make();

            /**
             * 户籍地址
             * t1_oa_loan_requests.户籍地址                  贷款申请表
             */
            EdgeLabel permanent_address = mgmt.makeEdgeLabel("permanent_address").multiplicity(Multiplicity.MULTI).make();
            /**
             * t1_oa_icbc_base_info.haddress(住宅地址)       工行e分期基础信息表
             */
            EdgeLabel residential_address = mgmt.makeEdgeLabel("residential_address").multiplicity(Multiplicity.MULTI).make();


        }
}
