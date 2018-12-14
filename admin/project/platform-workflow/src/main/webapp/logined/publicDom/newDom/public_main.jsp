<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/publicDom" prefix="dom"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="publicDomHead.jsp"></jsp:include>

<script type="text/javascript">
	function resizeIframe() {
     var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
     var cHeight = wHeight-95 ;
     $('#iframeCustomForm').height(cHeight);
     $('#ntkoframe').height(cHeight);
      $('#iframehistoryform').height(cHeight);
     // $("#liform").click();
};
$(window).resize(function() {
			resizeIframe();
		});
$(document).ready(function($) {
	resizeIframe();
});

/*
 * 全局变量，如果点击过正文标签，为true,否则为false,只有值为true的时候才需要保存正文，否则不保存
 */
var docflag = false;

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OA办公系统-公文</title>
</head>
<body >
<input type="hidden" id="instanceId" value="${instanceId}" />
<input type="hidden" id="taskId" value="${taskId}"/>
<input type="hidden" id="taskName" value="${task.activityName}"/>
<input type="hidden" id="documentExtId" value="${documentExtId}"/>
<input type="hidden" id="history" value="${history}"/>
<input type="hidden" id="source" value="${source}"/>
<input type="hidden" id="menu" value="${menu}"/>
<div class="header_hd">
    <div class="operate">
           <div class="tab">
                <ul>
                  <li id="libaseinfo" class="current"><em class="sw"></em><a href="javascript:void();">基本信息</a></li>
                  <li id="liform"><em class="sw"></em><a href="javascript:void();" id="shouwenOrFawen">收文单</a></li>
                  <c:if test="${tabsIsShow.doc==1 }">
                  	<li id="lidoc" ><em class="zw"></em><a href="javascript:void();">正文</a></li>
                  </c:if>
                  <li id="liattach"><em class="fj"></em><a href="javascript:void();">附件<span id="attachSize"></span></a></li>
                  <c:if test="${tabsIsShow.pdf ==1 }">
                  	<li id="lipdf" ><em class="bs"></em><a href="javascript:void();">版式文件</a></li>
                  </c:if>
                  <c:if test="${tabsIsShow.history_form_div ==1 }">
                  	<li id="lihistoryform" ><em class="bs"></em><a href="javascript:void();">原始发文单</a></li>
                  </c:if>
                </ul>
          </div>
          <div class="btn">
            <jsp:include page="jsp_options.jsp"/>
          </div>
    </div>
 </div>
<div class="content_hd">
	<!-- 基本信息 -->
	<div class="tabContent" style="display:block" id="baseInfo_div">
		<jsp:include page="jsp_baseInfo.jsp"></jsp:include>
	</div>
	<!-- 收/发文单 -->
	<div class="tabContent"  id="customForm_div" style="display:none;" >
		<jsp:include page="jsp_customForm.jsp"></jsp:include>
	</div>
	<c:if test="${tabsIsShow.doc==1 }">
	<!-- 正文 -->
		<div class="tabContent" id="doc_div" >
			<jsp:include page="jsp_doc.jsp"/>
		</div>
	</c:if>
	<!-- 附件 -->
	<div class="tabContent" id="attach_div" style="display:none;" >
		<jsp:include page="jsp_attach.jsp"/>
	</div>
	<c:if test="${tabsIsShow.pdf == 1 }">
		<!-- 版式文件 -->
		<div class="tabContent" id="pdf_div"  >
			<jsp:include page="jsp_pdf.jsp"></jsp:include>
		</div>
	</c:if>
	<c:if test="${tabsIsShow.history_form_div == 1 }">
		<!--历史发文单-->
		<div id="history_form_div" class="tabContent"  >
			<iframe id="iframehistoryform" src="${ctx}/logined/publicDom/newDom/jsp_historyForm.jsp?historyDispatchInstanceId=${historyDispatchInstanceId }&historyDispatchDocumentExtId=${historyDispatchDocumentExtId}" class="iframeresize"  border="0" frameBorder="no" frameSpacing="0" marginWidth="0" marginHeight="0" style="width: 100%;display:block; overflow:hidden;" ></iframe>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="${ctx}/js/logined/publicDom/newDom/controlPageElement.js"></script>
</body>
</html>