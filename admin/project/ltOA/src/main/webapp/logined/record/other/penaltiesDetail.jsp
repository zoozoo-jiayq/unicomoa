<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>人事档案奖罚-详情</title>
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
        <div class="big_title">奖惩详情</div>
        <div class="content_form">
            <div class="small_title">基本信息</div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0"
                   class="inputTable">
                <tr>
                    <th><label>姓名：</label></th>
                    <td>${recordPenalties.userInfo.userName}</td>
                    <th><label>奖罚项目：</label></th>
                    <td>${empty recordPenalties.project?"--":recordPenalties.project}</td>
                </tr>
                <tr>
                    <th><label>奖罚日期：</label></th>
                    <td><fmt:formatDate value="${recordPenalties.penaltiesDate}" pattern="yyyy-MM-dd"/></td>
                    <th><label>工资月份：</label></th>
                    <td>${empty recordPenalties.wagesMonth?"--":recordPenalties.wagesMonth}</td>
                </tr>
                <tr>
                    <th><label>奖罚属性：</label></th>
                    <td>${recordPenalties.nature eq 0 ? "奖励":"惩罚"}</td>
                    <th><label>奖罚金额(元)：</label></th>
                    <td>
                        <c:if test="${recordPenalties.penalties_money==null}">
                        <p>--<p>
                        </c:if>
                        <fmt:formatNumber value="${recordPenalties.penalties_money}" pattern="#0.00"/>
                    </td>
                </tr>
                <tr>
                    <th><label>登记日期：</label></th>
                    <td><fmt:formatDate value="${recordPenalties.createTime}" pattern="yyyy-MM-dd"/></td>
                </tr>
                <tr>
                    <th>备注：</th>
                    <td colspan="3">${empty recordPenalties.remark?"--":recordPenalties.remark}</td>
                </tr>
                <tr>
                    <th>奖罚说明：</th>
                    <td colspan="3">
                        <div class="detailsContent">
                            <p style="word-wrap:break-word; overflow:hidden;">${empty recordPenalties.explain?"--":recordPenalties.explain}</p>
                        </div>
                    </td>
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
                                <c:forEach items="${recordPenalties.attachmentList }" var="attach">
                                    <li><div class="icon"><em class="${attach.attacthSuffix}"></em></div><div class="txt" style="width:700px"><p>${attach.attachName}</p><p>
                                        <a style="cursor:pointer"  onclick="viewFile_record(${attach.id});">预览</a></p>
                                        <p><a  href="javascript:void(0);"  onclick="downFileById(${attach.id},this);">下载</a></p>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty recordPenalties.attachmentList }">无</c:if>
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