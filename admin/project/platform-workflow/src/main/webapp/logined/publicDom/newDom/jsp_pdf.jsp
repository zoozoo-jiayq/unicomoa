
<!--展示版式文件，输入参数 instanceId(流程实例ID)，-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div class="formPage">
                          <div class="formbg">
                           <div class="content_form" id="pdfContent" >
                           </div>
                          </div>
                          </div>
<script type="text/javascript">
$.get("${ctx}/dom/public!getPdf.action?instanceId=${instanceId}&r="+Math.random(),function(dt){
	if(dt){
		$("#pdfContent").html(dt);
	}else{
		$("#pdfContent").html("<h3 class='none_title'><em></em><span>暂无数据!</span></h3>");
	}
});
</script>
