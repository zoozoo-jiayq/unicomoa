<%--
  Created by IntelliJ IDEA.
  User: lilipo
  Date: 2017/1/4
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${ctx}/logined/appraisal/js/notify.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/logined/appraisal/plugins/qytx-cbb-v1.0.js"></script>
<div class="dc_notice pr">
    <p class="dc_notice_title">通知公告</p>
    <ul id="gg_ul" class="notice_list">

    </ul>
    <div class="jxkh_page_box">
        <span id="lastPage"  class="up_page fl">上一页</span>
        <ul class="page_num fl">
            <li id="nowPage" class="active"></li>
        </ul>
        <span id="nextPage"   class="down_page fr">下一页</span>
    </div>
</div> 
