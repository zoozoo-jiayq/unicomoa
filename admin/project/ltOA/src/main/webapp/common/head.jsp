<link href="${ctx}css/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/lvMain.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/public.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/tree.css" rel="stylesheet" type="text/css" />
<link href="${ctx}css/new.css" rel="stylesheet" type="text/css" />
 <link rel="stylesheet" type="text/css" href="${ctx}plugins/upload/uploadify.css"/>
<link href="${ctx}plugins/ckeditor/sample.css"/>
<link rel="stylesheet" href="${ctx}plugins/tree/ztree/zTreeStyle/zTreeStyle.css" type="text/css"/>
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="bookmark" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="icon" type="image/x-icon" /> 
<link href="${ctx}images/favicon.ico" mce_href="favicon.ico" rel="shortcut icon" type="image/x-icon" /> 
<link href="${ctx}plugins/ckeditor/sample.css"/>
<link href="${ctx}plugins/datatable/table_style.css" rel="stylesheet"	type="text/css" />
<link href="${ctx}plugins/datatable/datatable_page.css" rel="stylesheet" type="text/css" />

  <!--  <link href="${ctx}css/green.css" rel="stylesheet" type="text/css" />-->
<script type="text/javascript" src="${ctx}/js/switchskin.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.lock.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}js/common/stringutil.js"></script>
<script type="text/javascript" src="${ctx}js/common/validate.js"></script>
<script type="text/javascript" src="${ctx}js/common/bookmark.js"></script>
<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
	UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
    java.util.Date today = new java.util.Date();
    java.text.SimpleDateFormat simp = new java.text.SimpleDateFormat("yyyy-MM-dd");
    String todayStr = simp.format(today);
    request.setAttribute("today", todayStr);
%>
<% Integer skin = userInfo.getSkinLogo();if(skin ==null || skin == 1){ %>
  		<link id="skinCss" href="" rel="stylesheet" type="text/css" />
  		<script type="text/javascript" src="${ctx}plugins/dialog/artDialog4.1.6/jquery.artDialog.js?skin=blue"></script>
  	<%}else { %>
  		<link id="skinCss" href="${ctx}skins/green/green.css" rel="stylesheet" type="text/css" />
  		<script type="text/javascript" src="${ctx}plugins/dialog/artDialog4.1.6/jquery.artDialog.js?skin=green"></script>
	<%} %>
<script type="text/javascript" src="${ctx}plugins/dialog/artDialog4.1.6/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/json2.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<script type="text/javascript" src="${ctx}plugins/tree/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}plugins/tab/tab.js"></script>
<script type="text/javascript" src="${ctx}js/common/stringbuilder.js"></script>
<script type="text/javascript" src="${ctx}js/base.js"></script>
<script type="text/javascript" src="${ctx}js/logined/js_lang_cn.js"></script>
<script type="text/javascript" src="${ctx}js/common/selected.js"></script>
<script type="text/javascript" src="${ctx}js/common/textareaLen.js"></script>
<script type="text/javascript" src="${ctx}js/common/blur.js"></script>
<script type="text/javascript" src="${ctx}js/common/const.js"></script>
<script type="text/javascript" src="${ctx}/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${ctx}js/logined/customForm/formEvent.js" ></script>
<script type="text/javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/common/jquery.loadmask.js"></script>
<script type="text/javascript" src="${ctx}js/common/map.js"></script>
  <script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
  <script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx }js/common/async.js"></script>
<script type="text/javascript" src="${ctx }js/common.js"></script>
<script type="text/javascript" src="${ctx}js/logined/cbb/affairShow/affairShow.js"></script>
<script type="text/javascript">
		  var basePath ="${ctx}";
		  var currentUserName = "${sessionScope.adminUser.userName }";
		  jQuery("html").bind("click",function(){
              $(window.parent.document).find(".menu").hide(); 
              $(window.parent.parent.document).find(".menu").hide();
              $(window.parent.parent.parent.document).find(".menu").hide();
              $(window.parent.document).find(".hideDiv").hide(); //
              $(window.parent.parent.document).find(".hideDiv").hide();
              $(window.parent.parent.parent.document).find(".hideDiv").hide();
              $(window.top.document).find("#div_menu").hide();
	      });  
		  var _today = "${today}";
		  
		  $(document).ready(function() {
			  $("textarea[readonly]").keydown(function(e) {
			        if(e.which==8){
			            return false;       
			        }
			  });
			  $("input[readonly]").keydown(function(e) {
			        if(e.which==8){
			            return false;       
			        }
			  });
		  });
</script>