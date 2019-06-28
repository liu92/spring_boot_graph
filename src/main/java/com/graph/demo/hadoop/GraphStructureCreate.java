package com.graph.demo.hadoop;// Copyright 2019 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.core.schema.Mapping;

import static org.janusgraph.core.Cardinality.*;
import static org.janusgraph.core.Multiplicity.*;


public class GraphStructureCreate {

    public static void main(String[] args) {
        JanusGraph graph = JanusGraphFactory.open("");

        JanusGraphManagement mgmt = graph.openManagement();

        /**
         * 定义顶点
         * 人
         */
        VertexLabel person = mgmt.makeVertexLabel("person").make();//顾客
        VertexLabel salesman = mgmt.makeVertexLabel("salesman").make();//业务员
        VertexLabel Approver = mgmt.makeVertexLabel("approver").make();//审批人


        /**
         * 边定义
         */

        /**
         * 朋友
         * oa_icbc_base_info.reltship1
         * 联系人1与主卡联系人关系 1:父子,2:母子,3:兄弟姐妹,4:亲属,5:夫妻,6:同学,7:同乡,8:朋友,9:同事
         * oa_icbc_user_info.relation_ship
         * 关系类型 1:配偶,2:父母,3:子女,4:兄弟姐妹,5:亲戚,6:同学,7:同乡,8:朋友,9:同事
         */
        EdgeLabel friend = mgmt.makeEdgeLabel("friend").multiplicity(MULTI).make();//朋友

        EdgeLabel mother = mgmt.makeEdgeLabel("mother").multiplicity(MULTI).make();//母

        EdgeLabel father = mgmt.makeEdgeLabel("father").multiplicity(MULTI).make();//父

        EdgeLabel brotherOrSister = mgmt.makeEdgeLabel("brother_or_sister").multiplicity(MULTI).make();//兄弟姐妹

        EdgeLabel couple = mgmt.makeEdgeLabel("couple").multiplicity(MULTI).make();//夫妻

        EdgeLabel classmate = mgmt.makeEdgeLabel("classmate").multiplicity(MULTI).make();//同学

        EdgeLabel fellowTownsman = mgmt.makeEdgeLabel("fellow_townsman").multiplicity(MULTI).make();//同乡

        EdgeLabel colleague = mgmt.makeEdgeLabel("colleague").multiplicity(MULTI).make();//同事

        EdgeLabel homeVisit = mgmt.makeEdgeLabel("home_visit").multiplicity(MULTI).make();//家访

        /**
         * oa_icbc_user_info.user_relationship
         * 与申请人关系 1:财产共有人 2:担保关系人 3:共同申请人 4:共同偿债人
         */
        EdgeLabel ownerProperty = mgmt.makeEdgeLabel("owner_property").multiplicity(MULTI).make();// 财产共有人

        EdgeLabel guarantor = mgmt.makeEdgeLabel("guarantor").multiplicity(MULTI).make();// 担保关系人

        EdgeLabel jointApplicant = mgmt.makeEdgeLabel("joint_applicant").multiplicity(MULTI).make();// 共同申请人

        EdgeLabel jointDebtor = mgmt.makeEdgeLabel("joint_debtor").multiplicity(MULTI).make();// 共同偿债人


        /**
         *属性定义
         */

        /**
         * oa_loan_requests.customer_name  客户姓名
         * oa_loan_requests.ex_customer_name  改签客户姓名
         * oa_loan_requests.contact_name1  联系人1姓名
         * oa_loan_requests.contact_name2  联系人2姓名
         * oa_loan_requests.couple_name  配偶姓名
         * oa_loan_requests.guarantor_name  担保人姓名
         * oa_loan_requests.pg_user_ame  评估员姓名
         * oa_loan_requests.car_seller_name  前车主姓名
         * oa_icbc_base_info.user_name  主借款人姓名
         * oa_icbc_base_info.chnsname  权属人姓名
         * oa_icbc_base_info.bailname  保证人名称
         * oa_icbc_base_info.reltname1  联系人1姓名
         * oa_icbc_user_info.user_name  姓名
         * crm_no_repay_manage_transfer.主贷人姓名  主贷人姓名
         * crm_tm_loan.PRIMARY_LENDER_NAME  主贷人姓名
         * crm_tm_loan.原主贷人姓名  原主贷人姓名
         * crm_tm_loan.原主贷人姓名  户主姓名
         * crm_dy_elec_record.主贷人姓名  主贷人姓名
         * crm_ecel_manage.主贷人姓名  主贷人姓名
         * crm_ecel_manage.原主贷人姓名  原主贷人姓名
         * crm_elec_payment_register.主贷人姓名  主贷人姓名
         * crm_home_visit_form.主贷人姓名  主贷人姓名
         * crm_payoff.领取人收件人姓名  领取人收件人姓名
         * crm_payoff.主贷人姓名  主贷人姓名
         * crm_customer_loan.主贷人姓名  主贷人姓名
         * crm_customer_open_card.主贷人姓名  主贷人姓名
         * crm_recieve_car.主贷人姓名  主贷人姓名
         * crm_stop_elec.主贷人姓名  主贷人姓名
         * crm_complain_manage.主贷人姓名  主贷人姓名
         * crm_complain_manage.原主贷人姓名  原主贷人姓名
         * crm_commission_case.主贷人姓名  主贷人姓名
         * crm_no_repay_manage.主贷人姓名  主贷人姓名
         * crm_no_repay_list.主贷人姓名  主贷人姓名
         * crm_no_repay_list.原主贷人姓名  原主贷人姓名
         * crm_ovedue.主贷人姓名  主贷人姓名
         * crm_ovedue_compare_customer_loan.逾期表主贷人姓名  逾期表主贷人姓名
         * crm_ovedue_compare_customer_loan.客户贷款主贷人姓名  客户贷款主贷人姓名
         * crm_natural_payoff.领取人收件人姓名  领取人收件人姓名
         * crm_natural_payoff.主贷人姓名  主贷人姓名
         * crm_dispatch_order.主贷人姓名  主贷人姓名
         * crm_cancel_dispatch.主贷人姓名  主贷人姓名
         * crm_return_dispatch.主贷人姓名  主贷人姓名
         * crm_negotiation.主贷人姓名  主贷人姓名
         * crm_storage.主贷人姓名  主贷人姓名
         * crm_out_storage.主贷人姓名  主贷人姓名
         * crm_vehicle_disposal.主贷人姓名  主贷人姓名
         * crm_legal_support.联系人姓名  联系人姓名
         * crm_legal_support.主贷人姓名  主贷人姓名
         * crm_gps_monitoring.主贷人姓名  主贷人姓名
         *
         */
        PropertyKey name = mgmt.makePropertyKey("name").dataType(String.class).cardinality(SET).make();

        PropertyKey idCard = mgmt.makePropertyKey("id_card").dataType(String.class).cardinality(SINGLE).make();
        /**
         *
         * oa_loan_requests.couple_income  配偶月收入
         * oa_loan_requests.cust_income    本人月收入
         * oa_icbc_base_info.actual_loan_amount  关联人月收入
         * crm_home_visit_form.INCOME   家访单现月收入
         */
        PropertyKey income = mgmt.makePropertyKey("income").dataType(Double.class).cardinality(LIST).make();

        PropertyKey birthDate = mgmt.makePropertyKey("birth_date").dataType(Long.class).cardinality(SINGLE).make();
        /**
         * 婚姻状况
         * oa_loan_requests.married
         *
         */
        PropertyKey married = mgmt.makePropertyKey("married").dataType(Long.class).cardinality(SINGLE).make();

        /**
         * 民族
         * oa_loan_requests.nation
         *
         */
        PropertyKey nation = mgmt.makePropertyKey("nation").dataType(String.class).cardinality(SINGLE).make();
        /**
         * 是否本地人
         * oa_loan_requests.autochthon
         *
         */
        PropertyKey autochthon = mgmt.makePropertyKey("autochthon").dataType(Integer.class).cardinality(SINGLE).make();
        /**
         * 学历
         * oa_bank_credit_report.education
         */
        PropertyKey education = mgmt.makePropertyKey("education").dataType(String.class).cardinality(SINGLE).make();
        /**
         * 主借款人身份证签发机关
         * oa_icbc_base_info.issue_authority
         * oa_icbc_user_info.issue_authority
         */
        PropertyKey issue_authority = mgmt.makePropertyKey("issue_authority").dataType(String.class).cardinality(SINGLE).make();
       //**********************************************业务员和审批员*******************************************
        /**
         * 职位id
         *oa_user.post_id
         */
        PropertyKey post_id = mgmt.makePropertyKey("post_id").dataType(Long.class).cardinality(SINGLE).make();
        /**
         * 原职位
         *oa_user.original_post
         */
        PropertyKey original_post = mgmt.makePropertyKey("original_post").dataType(Long.class).cardinality(SINGLE).make();

        /**
         * 审批人名称
         * oa_flow_inst_task_audit.audit_user_name
         */
        PropertyKey audit_user_name = mgmt.makePropertyKey("audit_user_name").dataType(String.class).cardinality(SINGLE).make();

        /**
         * 审批人id
         */
        PropertyKey audit_user_id = mgmt.makePropertyKey("audit_user_id").dataType(String.class).cardinality(SINGLE).make();

        //***************************************家访边属性************************************************************//
        /**
         * 居住地址是否一致
         * t1_crm_home_visit_form.RESIDENTIAL_ADDRESS_SAME
         */
        PropertyKey residential_address_same = mgmt.makePropertyKey("residential_address_same").dataType(String.class).cardinality(SINGLE).make();
        /**
         * 工作单位是否一致
         * t1_crm_home_visit_form.WORKING_ADDRESS_SAME
         */
        PropertyKey working_address_same = mgmt.makePropertyKey("working_address_same").dataType(String.class).cardinality(SINGLE).make();

        /**
         * 保证人是否知晓责任
         * t1_crm_home_visit_form.GUARANTOR_KNOW_LIABILITY
         */
        PropertyKey guarantor_know_liability = mgmt.makePropertyKey("guarantor_know_liability").dataType(String.class).cardinality(SINGLE).make();
        /**
         * 客户收入来源
         * t1_crm_home_visit_form.CUSTOMER_REVENUE_SOURCE
         */
        PropertyKey customer_revenue_source = mgmt.makePropertyKey("customer_revenue_source").dataType(String.class).cardinality(SINGLE).make();

        /**
         * 家访日期
         * t1_crm_home_visit_form.DATA
         */
        PropertyKey date = mgmt.makePropertyKey("date").dataType(String.class).cardinality(SINGLE).make();

        /**
         * 家访id
         * t1_crm_home_visit_form.ID
         */
        PropertyKey visitId = mgmt.makePropertyKey("visit_id").dataType(Long.class).cardinality(SINGLE).make();

        //************************************ 关联人属性 *************************
        /**
         *  1:财产共有人 2:担保关系人 3:共同申请人 4:共同偿债人 的关联业务id
         * oa_icbc_user_infobusiness_id
         */
        PropertyKey businessd = mgmt.makePropertyKey("business_id").dataType(Long.class).cardinality(SINGLE).make();



        /**
         * 关系创建时间，其他边相关时间
         * oa_icbc_user_info.create_time
         */
        PropertyKey createTime = mgmt.makePropertyKey("create_time").dataType(Long.class).cardinality(SINGLE).make();

        /**
         * 图数据创建时间
         */
        PropertyKey dataCreateTime = mgmt.makePropertyKey("data_create_time").dataType(Long.class).cardinality(SINGLE).make();
        /**
         * 顶点边更新时间
         */
        PropertyKey updateTime = mgmt.makePropertyKey("update_time").dataType(Long.class).cardinality(SINGLE).make();
        /**
         *  图数据更新时间
         */
        PropertyKey DateUpdateTime = mgmt.makePropertyKey("date_update_time").dataType(Long.class).cardinality(SINGLE).make();


        /**
         * 索引定义
         */
        mgmt.buildIndex("search", Vertex.class).addKey(name, Mapping.STRING.asParameter()).buildMixedIndex("search");



        mgmt.commit();

    }
}
