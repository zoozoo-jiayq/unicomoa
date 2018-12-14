<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>调整考核结果</title>
    <jsp:include page="../../common/taglibs.jsp"/>
    <script type="text/javascript" src="${ctx}/logined/appraisal/plugins/qytx-cbb-v1.0.js"></script>
    <script type="text/javascript" src="${ctx}/logined/appraisal/js/admin/adjustment.js?version=${version}"></script>
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/artDialog.js?skin=blue"></script> 
    <script type="text/javascript" src="${ctx}logined/appraisal/plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
</head>
<body>
<!-- 外面的div是模拟的alert弹框，开发不需要复制外层div -->
<input type="hidden" id="khry" value="${param.khry}">
<input type="hidden" id="tid" value="${param.tid}">
<div class="alert_container" style="width:770px;">
    <ul class="khinfo_list">
        <li>
            <span class="khinfo_list_title">姓名：</span>
            <p class="khinfo_list_detail" id="p_name"></p>
        </li>
        <li>
            <span class="khinfo_list_title">部门：</span>
            <p class="khinfo_list_detail" id="p_group"></p>
        </li>
        <li>
            <span class="khinfo_list_title">原始得分：</span>
            <p class="khinfo_list_detail" id="p_ysdf"></p>
        </li>
        <li>
            <span class="khinfo_list_title">最终得分：</span>
            <p class="khinfo_list_detail" id="p_zzdf"></p>
        </li>
        <li>
            <span class="khinfo_list_title">评级：</span>
            <p class="khinfo_list_detail" id="p_pj"></p>
        </li>
        <li>
            <span class="khinfo_list_title">调整分值：</span>
            <div class="pr wd200">
                <input id="in_tzfz" type="text" maxlength="5" class="dafen" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" onblur="checktzfz(this.value)"  >
                <span class="score_unit">分</span>
            </div>
        </li>
        <li>
            <span class="khinfo_list_title">调整原因：</span>
            <textarea id="text_tzyy" maxlength="300" class="creat_list_txt" placeholder="请输入调整原因"></textarea>
        </li>
    </ul>
</div>
</body>
</html>