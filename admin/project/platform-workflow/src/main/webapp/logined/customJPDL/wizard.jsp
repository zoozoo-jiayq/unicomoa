<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计流程--向导</title>
<jsp:include page="customJPDLHead.jsp"/>
<script type="text/javascript" src="${ctx}/js/logined/customJPDL/wizard.js"></script>
</head>
<body >
<input  type="hidden" id="categoryId"  value="${categoryId }"/>
<input type="hidden" id="type" value="${type }"/>
<div class="mt10">
		<ul class="guide-block clearfix" >
				<LI id=flow class="guide-block-item ">
						<DL>
								<DT>定义流程</DT>
								<DD class="triangle_1">
										<p>用于定义流程的基本属性</p>
										<p>可配置内容包括:<B>流程名称</B>，<B>表单</B>，<B>流程类型</B>等 </p>
										<p><FONT color=#2287cd></FONT></p>
										<UL class="guide-spec-btns clearfix">
												<LI>
														<BUTTON id="flow_creat" class="guide-spec-btn " type=submit ><I class=creat></I>新增</BUTTON>
												</LI>
												<LI>
                                                        <BUTTON id="flow_import" class="guide-spec-btn " type=submit ><I class=import></I>导入</BUTTON>
                                                </LI>
										</UL>
								</DD>
						</DL>
				</LI>
				<LI id=process class="guide-block-item disabled" >
						<DL>
								<DT>设计流程</DT>
								<DD class="triangle_2">
										<p>用于设计流程在实际应用中的模型 </p>
										<p>可配置内容包括：<B>经办权限</B>，<B>字<br />段权限</B>等 </p>
										<UL class="guide-spec-btns clearfix">
												
										</UL>
										<!--          <ul _tmplitem="2"  class='guide-common-btns clearfix'>                 </ul>  --></DD>
						</DL>
				</LI>
								<LI id=process class="guide-block-item disabled" >
						<DL>
								<DT>流程管理</DT>
								<DD class="triangle_3">
										<p>用于管理已创建好的流程 </p>
										<p>可配置内容包括：<B>发布</B>，<B>停用</B>，<B>删除</B>等 </p>
										<UL class="guide-spec-btns clearfix">
												
												
										</UL>
										<!--          <ul _tmplitem="2"  class='guide-common-btns clearfix'>                 </ul>  --></DD>
						</DL>
				</LI>
		</ul>
</div>
</body>
</html>