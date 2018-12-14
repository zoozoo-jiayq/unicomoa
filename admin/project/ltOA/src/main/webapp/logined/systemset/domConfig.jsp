<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>通用设置</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }/flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<style>
label.radio{ margin-right:50px;}
.inputTable th{ width:126px;}
.content_form p{ line-height:25px;}
</style>
<script type="text/javascript" src="${ctx }/js/logined/systemset/domConfig.js"></script>
</head>
<body>
<form action="${ctx }/a/b/saveDom.action" method="post">
<div class="formPage">
<!-- 初始化隐藏域 -->
<input type="hidden" id="g1" value="${sysConfig.dom_gather_register }" />
<input type="hidden" id="g2" value="${sysConfig.dom_gather_approve }" />
<input type="hidden" id="g3" value="${sysConfig.force_read }" />
<input type="hidden" id="g4" value="${sysConfig.dom_gather_zip }" />
<input type="hidden" id="d1" value="${sysConfig.dom_dis_nigao }" />
<input type="hidden" id="d2" value="${sysConfig.dom_dis_hegao }" />
<input type="hidden" id="d3" value="${sysConfig.dom_dis_red }" />
<input type="hidden" id="d4" value="${sysConfig.dom_dis_zip }" />
<input type="hidden" id="bumenzhuanlan" value="${sysConfig.bumenzhuanlan}"/>
<input type="hidden" id="c1" value="${sysConfig.approve_widget}"/>
<input type="hidden" id="c2" value="${sysConfig.reader_widget}"/>
<input type="hidden" id="c3" value="${sysConfig.approve_comment}"/>
<input type="hidden" id="c4" value="${sysConfig.notice_update_password}"/>

<!-- 提交隐藏域 -->
<input type="hidden" name="config.dom_gather_register" />
<input type="hidden" name="config.dom_gather_approve" />
<input type="hidden" name="config.force_read" />
<input type="hidden" name="config.dom_gather_zip" />
<input type="hidden" name="config.dom_dis_nigao" />
<input type="hidden" name="config.dom_dis_hegao" />
<input type="hidden" name="config.dom_dis_red" />
<input type="hidden" name="config.dom_dis_zip" />
<input type="hidden" name="config.bumenzhuanlan"/>
<input type="hidden" name="config.approve_widget"/>
<input type="hidden" name="config.reader_widget"/>
<input type="hidden" name="config.approve_comment"/>
<input type="hidden" name="config.notice_update_password"/>
  <div class="formbg">
    <div class="big_title">收文操作设置</div>
    <div class="content_form">
       <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable" >
        <tr>
            <th style="width:130px"><label>收文登记</label></th>
            <td>
                  <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_register" disabled="disabled"  checked="checked" value="转领导批阅"/>转领导批阅
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_register" disabled="disabled" checked="checked" value="转收文分发"/>转收文分发
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_register" value="转阅读"/>转阅读
                </label>
             </td>
             </tr>
                     <tr>
            <th style="width:130px"><label>领导批阅</label></th>
            <td>
                  <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_approve" disabled="disabled" checked="checked" value="转领导批阅"/>转领导批阅
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_approve" disabled="disabled"  checked="checked" value="转收文分发"/>转收文分发
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_gather_approve" value="转阅读"/>转阅读
                </label>
             </td>
             </tr>
                   <tr>
            <th style="width:130px"><label>是否强制签阅</label></th>
            <td>
                  <label class="radio">
                    <input type="radio" name="configs.force_read" checked="checked" value="1"/>强制签阅
                </label>
                <label class="radio">
                    <input type="radio" name="configs.force_read" value="2"/>非强制签阅
                </label>
             </td>
             </tr>
                     <tr>
            <th style="width:130px"><label>归档设置</label></th>
            <td>
                  <label class="radio">
                    <input type="radio" name="configs.dom_gather_zip" checked="checked" value="1"/>手动归档
                </label>
                <label class="radio">
                    <input type="radio" name="configs.dom_gather_zip" value="2"/>自动归档
                </label>
             </td>
             </tr>
             </table>
    </div>
    <div class="big_title">发文操作设置</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable"  >
        <tr>
            <th style="width:130px"><label>发文拟稿</label></th>
            <td>
                  <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_nigao" disabled="disabled"  checked="checked" value="转核稿"/>转核稿
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_nigao"  value="转盖章"/>转盖章
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_nigao" value="转发文分发"/>转发文分发
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_nigao" value="转分发"/>转分发
                </label>
             </td>
             </tr>
                     <tr>
            <th style="width:130px"><label>发文核稿</label></th>
            <td>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_hegao" disabled="disabled"  checked="checked" value="转核稿"/>转核稿
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_hegao" disabled="disabled"  checked="checked"   value="转盖章"/>转盖章
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_hegao" disabled="disabled" value="转发文分发"/>转发文分发
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_hegao" value="转分发"/>转分发
                </label>
             </td>
             </tr>
                     <tr>
            <th style="width:130px"><label>套红盖章</label></th>
            <td>
                                   <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_red" disabled="disabled"  checked="checked" value="转核稿"/>转核稿
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_red" disabled="disabled"  checked="checked"  value="转发文分发"/>转发文分发
                </label>
                <label class="radio">
                    <input type="checkbox" name="configs.dom_dis_red" value="转分发"/>转分发
                </label>
             </td>
             </tr>
                     <tr>
            <th style="width:130px"><label>归档设置</label></th>
            <td>
                  <label class="radio">
                    <input type="radio" name="configs.dom_dis_zip" checked="checked" value="1"/>手动归档
                </label>
                <label class="radio">
                    <input type="radio" name="configs.dom_dis_zip" value="2"/>自动归档
                </label>
             </td>
             </tr>
             </table>
    </div>
    
    <div class="big_title">部门专栏设置</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable"  >
        <tr id="userDiv" >
			<th><label>栏目列表</label></th>
			<td colspan="3">
				<textarea class="readOnlyTextarea" style="width:90%" rows="5" id="bumenzhuanlan_name" name="config.bumenzhuanlan_name" readonly="readonly">${sysConfig.bumenzhuanlan_name}</textarea>
				<span class="addMember">
				<a class="icon_add" href="javascript:void(0);" onclick="selectBumen();">添加</a>
				<a class="icon_clear" href="javascript:void(0);" onclick="clearBumen()">清空</a>
				</span>
			</td>
		</tr>
             </table>
    </div>
        <div class="big_title">邮箱设置</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable"  >
        <tr id="userDiv" >
			<th><label>邮箱初始化</label></th>
			<td colspan="3">
				<input type="button" value="执行" id="defaultEmail"/>
				&nbsp;&nbsp;&nbsp;<font style="color:red">初始化所有用户的邮箱，解决人员直接导入数据库中出现的邮箱访问不了的问题</font>
			</td>
		</tr>
             </table>
     </div>
       <div class="big_title">工作流设置</div>
    <div class="content_form">
      <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable"  >
         <tr>
            <th style="width:130px"><label>审批人信息</label></th>
            <td>
                <label class="radio">
                    <input type="radio" name="configs.approve_widget" checked="checked" value="1"/>是
                </label>
                <label class="radio">
                    <input type="radio" name="configs.approve_widget" value="2"/>否
                </label><em class="gray_9">（审批控件输入内容为空时是否显示）</em>
            </td>
          </tr>
           <tr>
            <th style="width:130px"><label>阅读控件排序规则</label></th>
            <td>
                <label class="radio">
                    <input type="radio" name="configs.reader_widget" checked="checked" value="1"/>按级别
                </label>
                <label class="radio">
                    <input type="radio" name="configs.reader_widget" value="2"/>按时间
                </label>
            </td>
          </tr>
          <tr >
            <th style="width:130px"><label>审批意见</label></th>
            <td>
                <label class="radio">
                    <input type="radio" name="configs.approve_comment" checked="checked" value="1"/>显示
                </label>
                <label class="radio">
                    <input type="radio" name="configs.approve_comment" value="2"/>隐藏
                </label><em class="gray_9">（审批人审批工作流时是否需要填写意见）</em>
            </td>
          </tr>
          <tr>
            <th style="width:130px"><label>是否提醒修改默认密码</label></th>
            <td>
                <label class="radio">
                    <input type="radio" name="configs.notice_update_password" checked="checked" value="1"/>是
                </label>
                <label class="radio">
                    <input type="radio" name="configs.notice_update_password" value="2"/>否
                </label>
            </td>
          </tr>
        </table>
     </div>
        <div class="buttonArea">
            <input type="button" id="sure" class="formButton_green" value="确定" />
        </div>
    </div>
    
  </div>
</div>
</form>
</body>
</html>