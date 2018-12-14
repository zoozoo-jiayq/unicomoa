<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

  
  <div class="steps">
<table cellspacing="0" cellpadding="0" border="0">
          <tbody>
               <tr>
                <c:forEach items="${workflowState }" var="node">
                    <c:if test='${node.state=="start"}'>
                        <td><p>发起流程</p></td>
                    </c:if>
                    <c:if test='${node.state=="before"}'>
                      <td><p <c:if test='${node.result=="撤销"}'>class="red"</c:if><c:if test='${node.result=="不同意"}'>class="red"</c:if> >${node.nodeName}</td>
                    </c:if>
                    <c:if test='${node.state=="now" }'>
                      <td><p class="orange">${node.nodeName}</p></td>
                    </c:if>
                    <c:if test='${node.state=="after" }'>
                        <td><p class="txtgray">${node.nodeName}</p></td>
                    </c:if>
                    <c:if test='${node.state=="endOk" }'>
                        <td><p class="end">${node.nodeName}</p></td>
                    </c:if>
                    <c:if test='${node.state=="endNo" }'>
                        <td><p class="txtgray end">${node.nodeName}</p></td>
                    </c:if>
                  </c:forEach>
               </tr>
               <tr>
                 <c:forEach items="${workflowState }" var="node" >
                    <c:if test='${node.state=="start"}'>
                          <td><p><em class="icon_green"></em><em class="arrow_green"></em></p></td>
                      </c:if>
                      <c:if test='${node.state=="before"}'>
                          <td><p><em  <c:if test='${node.result=="同意"}'>class="icon_green"</c:if> <c:if test='${node.result=="撤销"}'>class="icon_orange"</c:if><c:if test='${node.result=="不同意"}'>class="icon_red"</c:if>></em><em class="arrow_green"></em></p></td>
                      </c:if>
                      <c:if test='${node.state=="now" }'>
                          <td><p><em class="icon_orange"></em><em class="arrow_gray"></em></p></td>
                      </c:if>
                      <c:if test='${node.state=="after" }'>
                          <td><p><em class="icon_gray"></em><em class="arrow_gray"></em></p></td>
                      </c:if>
                      <c:if test='${node.state=="endOk" }'>
                          <td><p class="end"><em class="icon_green"></em></p></td>
                      </c:if>
                      <c:if test='${node.state=="endNo" }'>
                          <td><p class="end"><em class="icon_gray"></em></p></td>
                      </c:if>
                </c:forEach>
               </tr>
               <tr>
                  <c:forEach items="${workflowState }" var="node">
                      <c:if test='${node.state=="start"}'>
                          <td><p class="f12">${node.userName }</p><p class="f12">${node.formatTime }</p><p class="f12">提交</p></td>
                      </c:if>
                      <c:if test='${node.state=="before"}'>
                          <td><p <c:if test='${node.result=="同意"}'>class="f12"</c:if> <c:if test='${node.result=="撤销"}'>class="red f12"</c:if><c:if test='${node.result=="不同意"}'>class="red f12"</c:if>>${node.userName } </p><p <c:if test='${node.result=="同意"}'>class="f12"</c:if> <c:if test='${node.result=="撤销"}'>class="red f12"</c:if><c:if test='${node.result=="不同意"}'>class="red f12"</c:if>>${node.formatTime }</p><p <c:if test='${node.result=="同意"}'>class="f12"</c:if> <c:if test='${node.result=="撤销"}'>class="red f12"</c:if><c:if test='${node.result=="不同意"}'>class="red f12"</c:if>>${node.result }</p></td>
                      </c:if>
                      <c:if test='${node.state=="now" }'>
                          <td><p class="red f12">${node.userName }</p></td>
                      </c:if>
                      <c:if test='${node.state=="after" }'>
                          <td><p class="txtgray f12"></p></td>
                      </c:if>
                      <c:if test='${node.state=="endOk" }'>
                          <td><p class="f12 end">${node.userName } </p></td>
                      </c:if>
                      <c:if test='${node.state=="endNo" }'>
                          <td><p class="txtgray f12 end">${node.userName }</p></td>
                      </c:if>
                     
                </c:forEach>

               </tr>
          </tbody>
     </table> 

  </div>
<a href="#" class="look_ls" onclick="openHistoryShoaList()">查看流程</a>
