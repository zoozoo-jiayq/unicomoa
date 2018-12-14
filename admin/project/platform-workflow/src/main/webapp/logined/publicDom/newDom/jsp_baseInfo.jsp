<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <div class="formPage">
                          <div class="formbg">
                            <div class="content_form">
                                  <table width="100%" border="0" cellpadding="0" cellspacing="0"  class="inputTable">
                                <tr>
                                    <th><label>公文类型：</label></th>
                                    <td>${gongwenVars.firstLevel } >>${gongwenVars.domTypeName }</td>
                                </tr>		
                                
                                <tr>
                                  <th><label>密　　级：</label></th>
                                  <td>${gongwenVars.secretLevel }</td>
                          </tr>
                                <tr>
                                  <th><label>缓　　急：</label></th>
                                  <td>${gongwenVars.huanji }</td>
                          </tr>
                            <tr>
                                    <th><label>文　　号：</label></th>
                                    <td>${gongwenVars.wenhao }</td>
                                </tr>
                                <tr>
                                    <th><label>公文标题：</label></th>
                                    <td>${gongwenVars.title }</td>
                                </tr>
                                <tr>
                                    <th><label>发文单位：</label></th>
                                    <td>${gongwenVars.sourceDept }</td>
                                </tr>		
	</table>
                            </div>
                          </div>
               </div>