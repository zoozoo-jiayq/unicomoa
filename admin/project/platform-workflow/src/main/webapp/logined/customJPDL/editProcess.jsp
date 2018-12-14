<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计流程--向导</title>
<jsp:include page="../../common/flatHead.jsp"/>
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/myworks.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<!--  <link href="${ctx }flat/plugins/datatable/skins/datatable_default.css" rel="stylesheet" type="text/css" />-->
<link href="${ctx }flat/plugins/Accormenus/skins/Accormenus_default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }flat/plugins/Accormenus/skins/jquery.Accormenus.js"></script>
<script type="text/javascript" src="${ctx }flat/js/base.js"></script>
<script type="text/javascript" src="${ctx }flat/js/placeholder.js"></script>
<script type="text/javascript" src="${ctx}js/logined/customJPDL/editProcess.js"></script>
</head>
<body>
	<input type="hidden" id="processId" value="${processAttribute.id}"/>
	<input type="hidden" id="processState" value="${processAttribute.processState}"/>
	<input type="hidden" id="formId" value="${processAttribute.formId}"/>
	<input type="hidden" id="type" value="${type }"/>
<div class="mt10">
		<div class="pageTitle"><em class="iconList mr10 ml10" style="font-size:14px">${processAttribute.processName} <span class="ml20 nor f12">该流程下共有<i id="instanceNum" class="f16 red">${processAttribute.instanceNum}</i>个工作.</span></em></div>
		<ul class="guide-block clearfix">
				<li id="flow" class="guide-block-item ">
						<dl>
								<dt>定义流程</dt>
								<dd class="triangle_1">
										<p>用于定义流程的基本属性</p>
										<p>可配置内容包括:<B>流程名称</B>，<B>表单</B></B>等 </p>
										<p><font color=#91271e>	
											温馨提醒：删除操作只能删除已停用的流程或者未发布的流程，对于已停用的流程请确保当前没有在用的流程实例
										</font></p>
										<UL class="guide-spec-btns clearfix">
												<li>
														<button id="flow_creat" class="guide-spec-btn " type=submit ><i class=creat></i>新增</button>
												</li>
												<li>
														<button id="flow_edit" class="guide-spec-btn " type=submit  ><i class=edit></i>编辑</button>
												</li>
												<!--
												<li>
														<button id=flow_import class="guide-spec-btn " type=submit onclick="javascript:parent.location.href='弹窗--系统设置--工作流设置--设计流程--导入流程.html'"><i class=import></i>导入</button>
												</li>
												<li>
														<button id=flow_export class="guide-spec-btn " type=submit ><i class=export></i>导出</button>
												</li>
											-->
												<li>
														<button id="flow_view" class="guide-spec-btn " type=submit ><i class=formView></i>预览表单</button>
												</li>
												<!--
												<li>
														<button id=flow_empty class="guide-spec-btn " type=submit ><i class=empty></i>清空</button>
												</li>
											-->
												<li>
														<button id="flow_delete" class="guide-spec-btn " type=submit ><i class=delete></i>删除</button>
												</li>
										</ul>
								</DD>
						</dl>
				</li>
				<li id=process class="guide-block-item" >
						<dl>
								<dt>设计流程</dt>
								<dd class="triangle_2">
										<p>用于设计流程在实际应用中的模型 </p>
										<p>可配置内容包括：<B>步骤基本属性</B>，<B>经办权限</B>，<B>字<br />段权限</B>，<B>条件设置</B>等 </p>
										<p><font color=#91271e>
											温馨提醒：请先定义流程步骤，否则将影响新建工作的使用
										</font></p>
										<ul class="guide-spec-btns clearfix">
												<li>
														<button id="process_designer" class="guide-spec-btn"  type=submit><i class="designer"></i>流程设计器</button>
												</li>
												<li>
														<button id="node_designer" class="guide-spec-btn"  type=submit><i class="designer"></i>编辑流程节点属性</button>
												</li>
												<li>
														<button id="process_flowCheck" class="guide-spec-btn"  type=submit ><i class="flowCheck"></i>校验</button>
												</li>
												<li>
														<button id="process_view" class="guide-spec-btn " type=submit ><i class=formView></i>预览流程</button>
												</li>
									
										</ul>
										<!--          <ul _tmplitem="2"  class='guide-common-btns clearfix'>                 </ul>  --></DD>
						</dl>
				</li>
				<li id="process" 
				     <c:if test='${(processAttribute.procsssDefinByJSON != null)&&(processAttribute.procsssDefinByJSON != "")}'>
				        class="guide-block-item" 
				  </c:if>
				    <c:if test='${(processAttribute.procsssDefinByJSON == null)||(processAttribute.procsssDefinByJSON == "")}'>
				        class="guide-block-item disabled"
				  </c:if> >
						<dl>
								<dt>流程管理</dt>
								<dd class="triangle_3">
										<p>用于管理已创建好的流程 </p>
										<p>可配置内容包括：<B>发布</B>，<B>停用</B>，<B>启用</B>等 </p>
										<c:if test='${(processAttribute.procsssDefinByJSON != null)&&(processAttribute.procsssDefinByJSON != "")}'>
										<p><font color=#91271e>
											该流程当前状态：<B>${processAttribute.processStateCN}</B>
										</font></p>
											<UL class="guide-spec-btns clearfix">
												
														<li>
																<button id="deploy" class="guide-spec-btn " type=submit ><i class="creat"></i>发布最新流程</button>
														</li>
									
													<c:if test="${processAttribute.processState == 2}">
													<li>
															<button id="stop" class="guide-spec-btn " type=submit ><i class="delete"></i>停用</button>
													</li>
													</c:if>
													<c:if test="${processAttribute.processState == 3}">
													<li>
															<button id="start" class="guide-spec-btn " type=submit ><i class="finish"></i>启用</button>
													</li>
													</c:if>
													<li>
													       <button id="exortProcessDefine" class="guide-spec-btn " type="submit" ><i class="export"></i>导出</button>
													</li>
											</ul>
										</c:if>	
										<!--          <ul _tmplitem="2"  class='guide-common-btns clearfix'>                 </ul>  --></DD>
						</dl>
				</li>
		</ul>
</div>
</body>
</html>