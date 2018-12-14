<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>可写字段</title>
<jsp:include page="../../common/flatHead.jsp" />
<link href="${ctx }flat/css/reset.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
<link href="${ctx }flat/css/myworks.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/logined/customJPDL/secretAble.js"></script>
</head>
<body>
	<form action="${ctx}/workflow/nodeManager!update.action" method="post">
	<input type="hidden" id="nodeId" name="node.id" value="${nodeId}"/>
    <input type="hidden" id="userIds" name="node.candidate" value="${node.candidate}"/>
    <input type="hidden" id="groupIds" name="node.depts" value="${node.depts}"/>
    <input type="hidden" id="roleIds" name="node.roles" value="${node.roles}"/>
    <input type="hidden" name="node.processAttribute.id" value="${node.processAttribute.id}"/>
    <input type="hidden" name="node.nodeName" value="${node.nodeName}" />
    <input type="hidden" name="node.writeableProperties" value="${node.writeableProperties}" id="writeableProperties"/>
    <input type="hidden" name="node.secretProperties" value="${node.secretProperties}" id="secretProperties"/>
    <input type="hidden" name="node.nodeType" value="${node.nodeType}"/>
    <input type="hidden" name="node.descri" value="${node.descri}"/>
    <input type="hidden" name="node.nodeOrder" value="${node.nodeOrder}"/>
    <input type="hidden" name="processAttributeId" value="${node.processAttribute.id}"/>
    <input type="hidden" name="node.isMydepCanAccept" value="${node.isMydepCanAccept}"/>
    <input type="hidden" name="type" value="${type}"/>


<div class="formPage">
  <div class="formbg">
      <div class="big_title">步骤：${node.nodeName } <em>编辑保密字段(保密字段对于本步骤主办人、经办人均为不可见)</em></div>
      <div class="bmzd">
          <div class="listbox fl">
             <h2>本步骤可写字段</h2>
				   <select  multiple="multiple" class="selelist" id="writeAbles" >
						<c:forEach items="${secretProperties}" var="formp">
						  <option value="${formp.propertyId}">${formp.propertyNameCh}</option>
						</c:forEach>			
				  </select>
              <p>
                <input type="button" id="allW" value="全选" />
              </p>   
          </div>
          <div class="btn_box fl">
             <a style="cursor:pointer;"  id="leftSelect" ><img src="${ctx }flat/images/icon/l_jt.png"></a>
             <a style="cursor:pointer;"    id="rightSelect" ><img src="${ctx }flat/images/icon/r_jt.png"></a>
          </div>
           <div class="listbox fl">
             <h2>备选字段</h2>
                     <select multiple="multiple" id="props" class="selelist"  >
								<c:forEach items="${formProperties}" var="fp">
									<option value="${fp.propertyId}">${fp.propertyNameCh}</option>
								</c:forEach>
						</select>
              <p>
                <input type="button" id="allProps"   value="全选" />
              </p>   
          </div>
          <div class="clear"></div>
          <div class="explain">注：点击条目时，可以组合CTRL或SHIFT键进行多选</div>
      </div>
  </div>
  <div class="buttonArea">
    <input type="button"  id="sure" class="formButton_green" value="保存" hidefocus="" />
    <input type="button"  onclick="window.history.back(); return false;"   value="取消" class="formButton_grey"  hidefocus=""  />
  </div>
</div>
  
</form>
</body>
</html>