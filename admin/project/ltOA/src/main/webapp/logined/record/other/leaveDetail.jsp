<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案离职-详情</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <jsp:include page="../../../common/flatHead.jsp"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/plugins/form/skins/form_default.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/plugins/annex/skins/annex_default.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
        .inputTable th{ width:100px;}
    </style>
    <script type="text/javascript"
            src="${ctx}/js/logined/record/commonUpload.js"></script>
    <script>
        function downFileById(id) {
            window.open(basePath + 'filemanager/downfile.action?attachmentId=' + id);
        }
    </script>
</head>
<body>
<div class="formPage" style="width: 700px">
    <div class="formbg">
        <div class="big_title">离职详情</div>
        <div class="content_form">
            <div class="small_title">基本信息</div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0"
                   class="inputTable">
                <tr>
                    <th><label>姓名：</label></th>
                    <td>${recordLeave.userInfo.userName}</td>
                    <th><label>担任职务：</label></th>
                    <td>${empty recordLeave.position?"--":recordLeave.position}</td>
                </tr>
                <tr>
                    <th><label>离职类型：</label></th>
                    <td>${empty recordLeave.leaveTypeString?"--":recordLeave.leaveTypeString}</td>
					<th><label>申请日期：</label></th>
                    <td>
                        ${empty recordLeave.applyDate?"--":""}
                        <fmt:formatDate value="${recordLeave.applyDate}" pattern="yyyy-MM-dd"/></td>
                    </td>
                </tr>
                <tr>
                    <th><label>拟离职日期：</label></th>
                    <td><fmt:formatDate value="${recordLeave.intendedToLeaveDate}" pattern="yyyy-MM-dd"/></td>
                    <th><label>实际离职日期：</label></th>
                    <td><fmt:formatDate value="${recordLeave.actualLeaveDate}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th><label>工资截止日期：</label></th>
                    <td><fmt:formatDate value="${recordLeave.wageCutoffDate}" pattern="yyyy-MM-dd"/></td>
                    <th><label>离职部门：</label></th>
                    <td>${empty recordLeave.leaveDepartmentName?"--":recordLeave.leaveDepartmentName}</td>                   
                </tr>
                <tr>
                    <th><label>当月薪资(元)：</label></th>
                    <td>${empty recordLeave.leaveTheMonthWage?"--":recordLeave.leaveTheMonthWage}</td>
                </tr>
                <tr>
                    <th>去向：</th>
                    <td colspan="3">
                        ${empty recordLeave.whereabouts?"--":recordLeave.whereabouts}
                    </td>
                </tr>
                <tr>
                    <th>离职手续办理：</th>
                    <td colspan="3">    ${empty recordLeave.resignationProcedure?"--":recordLeave.resignationProcedure}
                    </td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">${empty recordLeave.remark?"--":recordLeave.remark}</td>
                </tr>
                <tr>
                    <th><label>离职原因：</label></th>
                    <td> ${empty recordLeave.leaveReason?"--":recordLeave.leaveReason}</td>
                </tr>
                <tr>
                    <th><label>登记时间：</label></th>
                    <td><fmt:formatDate value="${recordLeave.createTime}" pattern="yyyy-MM-dd"/></td>
                </tr>
                </tbody>
            </table>
            <div class="small_title">附件</div>
            <table width="100%" cellspacing="0" cellpadding="0" border="0"
                   class="inputTable">
                <tbody>
                <tr>
                    <td colspan="3" id="fileAttachTd">
                        <div class="annex">
                            <ul id="attachmentList">
                                <c:forEach items="${recordLeave.attachmentList }" var="attach">
                                    <li><div class="icon"><em class="${attach.attacthSuffix}"></em></div><div class="txt" style="width:700px"><p>${attach.attachName}</p><p>
                                        <a style="cursor:pointer"  onclick="viewFile_record(${attach.id});">预览</a></p>
                                        <p><a  href="javascript:void(0);"  onclick="downFileById(${attach.id},this);">下载</a></p>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty recordLeave.attachmentList }">无</c:if>
                            </ul>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
        <!-- input div结束 -->
    </div>
</div>

</body>
</html>