<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>考核信息</title>
    <script src="${ctx}/logined/appraisal/common/jxkh.js"></script>
    <script src="${ctx}/logined/appraisal/js/admin/add_assessment.js?version=${version}"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
</head>
<body>
<input type="hidden" id="vid" value="${param.vid}">
<input type="hidden" id="ck_vid" value="${param.ck_vid}">
<input type="hidden" id="type" value="${param.type}">
<jsp:include page="../header.jsp"/>
<div class="creat_container">
    <div class="crumbs">
        <ul>
            <li>
                <a href="#">1、填写考核信息</a>
                <a href="#" class="has_done">1、填写考核信息</a>
            </li>
            <li>
                <a href="#">2、考核表设置</a>
                <a href="#" class="has_done">2、考核表设置</a>
            </li>
            <li>
                <a href="#">3、设定考核关系</a>
                <a href="#" class="not_done">3、设定考核关系</a>
            </li>
            <li>
                <a href="#">4、设定分级标准</a>
                <a href="#" class="not_done">4、设定分级标准</a>
            </li>
        </ul>
    </div>
    <div class="btn_container3">
        <span class="btn2_inline btn_add mr20" onclick="addTr()">新增考核指标</span>
        <span class="btn2_inline btn_del" onclick="delTr('string')">删除考核指标</span>
    </div>
    <table class="report_table td_addpad mt10" cellspacing="0">
        <thead class="report_one">
        <tr>
            <th class="wd110">选择</th>
            <th>指标名称</th>
            <th>序号</th>
            <th class="wd200">指标分类</th>
            <th class="wd100">分值</th>
            <th>描述</th>
        </tr>
        </thead>
        <tbody id="table">
        </tbody>
    </table>
    <p class="kh_total text_left">合计：<span id="total">0</span>分</p>
    <div class="btn_container2">
        <span class="btn_inline btn_white" onclick="goBack()">上一步</span>
        <span class="btn_inline btn_gray ml15" onclick="reset()">重置</span>
        <span class="btn_inline btn_white ml15" onclick="post()">下一步</span>
    </div>
</div>
</body>
</html>