<!--
  Created by IntelliJ IDEA.
  User: lilipo
  Date: 2016/12/28
  Time: 11:29
  To change this template use File | Settings | File Templates.
-->
<%--
  Created by IntelliJ IDEA.
  User: lilipo
  Date: 2016/12/27
  Time: 11:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/taglibs.jsp"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>绩效考核</title>
    <link rel="stylesheet" type="text/css"
          href="${ctx}/logined/appraisal/plugins/uploadify.css" />
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/js/staff/leadership_assessment_details.js?version=${version}"></script>


</head>
<body>
<input type="hidden" id="tid" value="${param.tid}">
<input type="hidden" id="userId" value="${param.userId}">
<input type="hidden" id="view_type" value="${param.view_type}">
<!-- 右侧内容模块 -->
<div class="iframe_right_content">
    <div class="navigation_bar">
        <p>当前位置：
            <a href="#">首页</a>
            <span id="dh"> </span>
        </p>
        <a href="javascript:void(0);" onclick="goback()" class="back_btn fr">>> 返回</a>
    </div>
    <div class="kh_score_box">
        <div class="pr">
            <p class="kh_score_title" id="khmc"></p>
        </div>
        <div class="kh_score_detail">
            <ul class="kh_detail_list">
                <li>
                    <span class="kh_detail_list_title">考核人员：</span>
                    <span class="kh_detail_list_content" id="userName"></span>
                </li>
                <li>
                    <span class="kh_detail_list_title">部门：</span>
                    <span class="kh_detail_list_content" id="groupName"></span>
                </li>
                <li>
                    <span class="kh_detail_list_title">职务：</span>
                    <span class="kh_detail_list_content" id="job"></span>
                </li>
            </ul>
            <ul class="khinfo_list">
                <li>
                    <span class="khinfo_list_title">自述自评：</span>
                    <p class="khinfo_list_detail" id="wdsz"></p>
                </li>
                <li>
                    <span class="khinfo_list_title">述职文件：</span>
                    <p id="file" class="khinfo_list_detail">

                    </p>
                </li>
                <li>
                    <span class="khinfo_list_title">考核项：</span>
                   
                </li>
            </ul>
            <div class="pdl30">
            	 <table class="score_table">
                      <thead>
                      <tr>
                          <th class="wd115">指标分类</th>
                          <th class="wd165">指标名称</th>
                          <th class="wd400">指标描述</th>
                          <th class="wd100">分值</th>
                          <th class="wd80">打分</th>
                      </tr>
                      </thead>
                      <tbody id="table">

                      </tbody>
                  </table>
            </div>
            <p class="kh_total">总计：<span id="total"></span></p>
        </div>
    </div>
</div>
</body>
</html>

