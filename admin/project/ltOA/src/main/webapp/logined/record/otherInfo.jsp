<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <jsp:include page="../../common/flatHead.jsp"/>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>其他信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="${ctx}flat/css/reset.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}flat/css/desktop_icon.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/logined/record/otherInfo.js"></script>
</head>
<body>
<input type="hidden" value="${param.userId}" id="userId"/>
    <div id="portalSetting" style="height:100%;width: 100%">
        <div   id="appPageAll" style="height:100%;width: 100%">
            <div class="appPage" id="appPageDom" >
                <div   id="app_cate_list" style="height:500px">
                    <div class="bd" >
                       <ul>
                           <li  title="奖惩" style="height: 40px;" url="/logined/record/other/penaltiesList.jsp"> <em class="rsjc"></em>奖惩 </li>
                           <li  title="学习 " style="height: 40px;" url="/logined/record/other/learnList.jsp"> <em class="rsxx"></em>学习 </li>
                           <li  title="工作 " style="height: 40px;" url="/logined/record/other/workList.jsp"> <em class="rsgz"></em>工作 </li>
                           <li  title="关系 " style="height: 40px;" url="/logined/record/other/relationList.jsp"> <em class="rsgx"></em>关系 </li>
                           <li  title="调动 " style="height: 40px;" url="/logined/record/other/transferList.jsp"> <em class="rsdd"></em>调动 </li>
                           <li  title="职称" style="height: 40px;" url="/logined/record/other/titleList.jsp"> <em class="rszc"></em>职称</li>
                           <li  title="培训 " style="height: 40px;" url="/logined/record/other/trainingList.jsp"> <em class="rspx"></em>培训</li>
                           <li  title="离职 " style="height: 40px;" url="/logined/record/other/leaveList.jsp"> <em class="rslz"></em>离职</li>
                      </ul>
                    </div>
                </div>
                <div id="app_list_box" style="width: 860px">
                        <iframe class="iframeresize" id="page" name="page"  border="0" frameBorder="0" scrolling="auto" style="width: 100%; height:100%" src="${ctx }/logined/record/other/penaltiesList.jsp"></iframe>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>
</body>
</html>