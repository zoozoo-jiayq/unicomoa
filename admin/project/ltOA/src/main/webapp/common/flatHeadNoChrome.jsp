<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path+"/" ;
    String serverPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort();
    request.setAttribute("ctx", basePath);
    request.setAttribute("serverPath", serverPath);
    request.setAttribute("version", "3.1");
    response.setHeader("Cache-Control","no-cache"); //HTTP 1.1    
    response.setHeader("Pragma","no-cache"); //HTTP 1.0    
    response.setDateHeader ("Expires", 0); //prevents caching at the proxy server   
    
  response.setHeader("Pragma","No-cache");  
 response.setHeader("Cache-Control","no-cache");  


%>
<%-- <jsp:include page="/common/report.jsp"/> --%>
<meta http-equiv="pragma" content="no-cache" />
 <%@ page import="cn.com.qytx.platform.org.domain.UserInfo"%>
<%
	String menuStyle = "";
	UserInfo userInfo = (UserInfo) session.getAttribute("adminUser");
	if(userInfo != null){
		Integer skinAndMenu = userInfo.getSkinLogo();
		if(skinAndMenu != null){
			char c = (""+skinAndMenu).charAt(1);
					if('2'==c){
						menuStyle = "left";
					}
					if('3'==c){
						menuStyle = "fix";
					} 
					if('1'==c){
						menuStyle = "default";
					}
		}else{
			menuStyle = "default";
		}
	}else{
		menuStyle = "default";
	}
	if(menuStyle == "fix"){%>
	<link href="${ctx }flat/css/fasten.css" rel="stylesheet" type="text/css" />
<%} 
%>
<script type="text/javascript">
	var menuStyle="<%=menuStyle%>";
</script>

 <!-- 右侧树 -->
 <!-- 换肤 -->
<% 
Integer skin = userInfo.getSkinLogo();
char skinStr = (skin+"").charAt(0);
	if(skinStr == '1'){%>
 	 <link  href="" rel="stylesheet" type="text/css" />
 	 <script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=default"></script>
	<%}  else if(skinStr == '2'){ %>
	 <link href="${ctx}flat/skins/green/green.css" rel="stylesheet" type="text/css" />
  	 <script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=green"></script>
	<%}  else if(skinStr == '3'){ %>
	<link href="${ctx}flat/skins/blue/blue.css" rel="stylesheet" type="text/css" />
  	 <script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=blue"></script>
<%}  else if(skinStr == '4'){ %>
	<link href="${ctx}flat/skins/brand/brand.css" rel="stylesheet" type="text/css" />
  	 <script type="text/javascript" src="${ctx }flat/plugins/dialog/artDialog.js?skin=brand"></script>
<%} %>
 
<link href="${ctx}flat/plugins/datatable/skins/datatable_page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}js/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.lock.js"></script>
<script type="text/javascript" src="${ctx}js/common/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}js/common/json2.js"></script>
<script type="text/javascript" src="${ctx}js/common/selected.js"></script>
<script type="text/javascript" src="${ctx}js/common/textareaLen.js"></script>
<script type="text/javascript" src="${ctx}js/common/blur.js"></script>
<script type="text/javascript" src="${ctx}js/common/const.js"></script>
<script type="text/javascript" src="${ctx}js/common/map.js"></script>
<script type="text/javascript" src="${ctx}js/common/hashmap.js"></script>
<script type="text/javascript" src="${ctx}flat/js/common.js"></script>
<!-- 表单验证 开始 -->
<script type="text/javascript" src="${ctx}js/common/validate_form.js"></script>
<script type="text/javascript" src="${ctx}js/common/showError.js"></script>
<script type="text/javascript" src="${ctx}js/logined/js_lang_cn.js"></script>
<!-- 表单验证 结束 -->

<!--  dialog -->
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog.js?skin=default"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/artDialog_alert.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/dialog/iframeTools.js"></script>
<script type="text/javascript" src="${ctx}js/common/artClose.js"></script>
<!--  dialog end -->
<!-- 富文本编辑器 -->
<script type="text/javascript" src="${ctx}/plugins/ckeditor/ckeditor.js"></script>
<!-- 日历控件 -->
<script type="text/javascript" src="${ctx}plugins/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
<!-- 表格 -->
<script type="text/javascript" src="${ctx}plugins/datatable/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}plugins/datatable/selecedForDatatablePagination.js"></script>
<!-- 自动补全 -->
<script type="text/javascript" src="${ctx }/flat/plugins/autoComplete/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/peopleTree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}flat/plugins/tree/skins/jquery.ztree.all-3.2.min.js"></script>
<script type="text/javascript" src="${ctx}js/common/treeNode.js"></script>
<script type="text/javascript" src="${ctx }/flat/js/async.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}plugins/upload/jquery.uploadify.min.js?r=<%=System.currentTimeMillis()%>"></script>

<!-- UEDITOR富文本编辑器 -->
<script type="text/javascript">
	window.UEDITOR_HOME_URL = "${ctx}/flat/plugins/ueditor/";
</script>
<script type="text/javascript" src="${ctx }/flat/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="${ctx }/flat/plugins/ueditor/ueditor.all.min.js"></script>

<script type="text/javascript" src="${ctx}flat/js/qytx-cbb-v1.0.js"></script>
<script type="text/javascript">
	var basePath = "${ctx}";
	var serverPath = "${serverPath}";
	var _cssType = "flat";  //扁平化
	 jQuery("html").bind("click",function(){
		 $(window.parent.document).find(".operate span").each(function(){
			 if($(this).hasClass("on")){
					 $(this).removeClass("on");
				 }			
		 });
		 if($(window.parent.document)){
		 	$(window.parent.document).find(".menu").hide();  
		 	$(window.parent.document).find(".hideDiv").hide();  
         }
         if($(window.parent.parent.document)){
	         $(window.parent.parent.document).find(".menu").hide();
	         $(window.parent.document).find(".hideDiv").hide();  
         }
         if($(window.parent.parent.parent.document)){
	         $(window.parent.parent.parent.document).find(".menu").hide();
	         $(window.parent.document).find(".hideDiv").hide();  
         }
         if($(window.top.document)){
	         $(window.top.document).find("#div_menu").hide();
	         $(window.parent.document).find(".hideDiv").hide();  
         }
        });  
	 $(document).ready(function() {
		 $("body").keydown(function(e){
		  	if(e.which==8){
			  	var type = e.target.type;
			  	if(type == "textarea" || type == "text" || type == "password"){
			  		if($(e.target).attr("readonly")){
			  			return false;
			  		}
			  		return true;
			  	}else{
			  		return false;
			  	}
		  	} 
		  	return true;
		  });
		
	  });
	  
	  if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}


</script>

<% 
	if( skinStr == '1'){ %>
 	<%}  else if(skinStr == '2'){ %>
 	<script>
 		window.onload=function(){
 			$("body").addClass("green_skin");
 		};
 	</script>
 	<%}  else if(skinStr == '3'){ %>
 	<script>
 		window.onload=function(){
 			$("body").addClass("blue_skin");
 		};
 	</script>
 <%} %>
