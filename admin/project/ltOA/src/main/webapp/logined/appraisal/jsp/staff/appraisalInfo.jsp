<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="../../common/taglibs.jsp"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>考核信息</title>
    <script src="${ctx}/logined/appraisal/common/jxkh.js"></script>
      <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script>
      <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
    <script src="${ctx}/logined/appraisal/js/staff/appraisalInfo.js?version=${version}"></script>
</head>
<body>
<input type="hidden" id="tid" value="${param.tid}">
<input type="hidden" id="view_type" value="${param.type}">
<div class="iframe_right_content">
    <div class="navigation_bar">
        <p>当前位置：
            <a href="#">首页</a>
            <span> > 考核信息</span>
        </p>
    </div>
   <!--  <div class="kh_score_box"> -->
        <div class="kh_score_box">
            <div class="pr">
                <p class="kh_score_title" id="khmc"></p>
            </div>
            <div class="kh_score_detail">
                <ul class="khinfo_list">
                    <li>
                        <span class="khinfo_list_title">考核时间：</span>
                        <p id="khsj" class="khinfo_list_detail"></p>
                    </li>
                    <li>
                        <span class="khinfo_list_title">考核状态：</span>
                        <p class="khinfo_list_detail"><span id="khjd"></span></p>
                    </li>
                    <li>
                        <span class="khinfo_list_title">考核描述：</span>
                        <p id="ms" class="khinfo_list_detail"></p>
                    </li>
                    <li>
                        <span class="khinfo_list_title">考核人员：</span>
                        <p id="khryStr" class="khinfo_list_detail"></p>
                    </li>
                    <li>
                        <span class="khinfo_list_title">评分人员：</span>
                        <p id="dfryStr" class="khinfo_list_detail"></p>
                    </li>
                    <li>
                        <span class="khinfo_list_title">督查领导：</span>
                        <p id="shryStr" class="khinfo_list_detail"></p>
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
                              <th class="wd500">指标描述</th>
                              <th class="wd100">分值</th>
                          </tr>
                          </thead>
                          <tbody id="table">
                          </tbody>
                      </table>
                </div>
            </div>
        </div>
    <!-- </div> -->
    <div id="buttons" class="btn_container2">
        <span id="btn_delete" class="btn_inline btn_red" onclick="updateTask(7,'确认删除本次考核？',true)">删除本考核</span>
        <span id="btn_modify" class="btn_inline btn_white ml15" onclick="modifyTask()">修改考核</span>
        <span id="btn_start" class="btn_inline btn_white ml15" onclick="updateTask(2,'确认开始本次考核？',false)">开始考核</span>
        <span id="btn_end" class="btn_inline btn_white ml15" onclick="updateTask(3,'确认结束本次考核评分？',false)">结束评分</span>
        <span id="btn_apply" class="btn_inline btn_white ml15" onclick="updateTask(4,'确定要将考核结果提交给领导审批吗？',false)">申请审批</span>
    </div>
</div>

</body>
</html>