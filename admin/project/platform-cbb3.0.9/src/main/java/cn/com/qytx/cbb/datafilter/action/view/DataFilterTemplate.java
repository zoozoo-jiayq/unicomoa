package cn.com.qytx.cbb.datafilter.action.view;

import java.util.List;

import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.security.domain.DataFilter;

/**
 * 数据权限视图模板
 * @author jiayongqiang
 *
 */
public class DataFilterTemplate {

	public static String getMainTemplate(String appContext){
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		sb.append("<title>数据权限</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style=\"margin:100px auto; width:500px\">");
		sb.append("<form action='"+appContext+"/datafilter/manager.action'>");
		sb.append("<input type='text' class='formText' name='moduleClassName' style='width:300px'/><input type='submit' class='formButton_green' value='查询' />");
		sb.append("</form>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	/**
	 * @param dataFilterlist
	 * @return
	 */
	public static String getDataFilterlist(String appContext,List<DataFilter> dataFilterlist,List<UserInfo> userlist,List<GroupInfo> glist,List<RoleInfo> rlist,List<GroupInfo> forklist){
		StringBuffer sb = new StringBuffer("");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		sb.append("<title>数据权限</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style=\"margin:100px auto; width:800px\">");
		sb.append("<div style='margin:10px;border-width:1px'>");
		sb.append("<form action='"+appContext+"/datafilter/add.action'>");
		sb.append("<label>选择范围：</label><select name='scope' onchange='showScope(this);'><option value='all'>全部</option>"
				+ "<option value='user'>人员</option>"
				+ "<option value='group'>部门</option>"
				+ "<option value='role'>角色</option>"
				+ "<option value='isForkGroup'>分支机构</option>"
				+ "</select>");
		sb.append(generateUserSelectHtml(userlist));
		sb.append(generateGroupSelectHtml("groupSelect", glist));
		sb.append(generateGroupSelectHtml("isForkGroupSelect", forklist));
		sb.append(generateRoleSelectHtml(rlist));
		sb.append("<br/>");
		sb.append("<label>数据权限：</label>");
		sb.append("<textarea style='width:400px;height:50px;' name='customCondition'></textarea>");
		sb.append("<br/>");
		sb.append("<input type='submit' value='新增' />");
		sb.append("</form>");
		sb.append("</div>");
		sb.append("<table border='1px'>");
		sb.append("<tr>");
		sb.append("<th>模块</th><th>范围</th><th>权限</th><th>操作</th>");
		sb.append("</tr>");
		if(dataFilterlist!= null){
			for(int i=0; i<dataFilterlist.size(); i++){
				DataFilter df = dataFilterlist.get(i);
				sb.append("<tr>");
					sb.append("<td>").append(df.getModelClassName()).append("</td>");
					sb.append("<td>").append(df.getRelationId()).append("</td>");
					sb.append("<td>").append(df.getConditionJpql()).append("</td>");
					sb.append("<td>").append("<a>删除</a>").append("</td>");
				sb.append("</tr>");
			}
		}
		sb.append("</table>");
		sb.append("</div>");
		sb.append("<script type='text/javascript'>function showScope(obj){if(obj.value=='all'){"
				+ "hideAll();}else if(obj.value=='user'){"
				+ "hideAll();document.getElementById('userSelect').style.display='none';}else if(obj.value=='group'){"
				+ "hideAll();document.getElementById('groupSelect').style.display='none';}else if(obj.value=='role'){"
				+ "hideAll();document.getElementById('roleSelect').style.display='none';}else if(obj.value=='isForkGroup'){"
				+ "hideAll();document.getElementById('isForkGroupSelect').style.display='none';}"
				+ "}");
		sb.append("function hideAll(){alert(1);document.getElementById('userSelect').style.display='none';"
				+ "document.getElementById('groupSelect').style.display='none';"
				+ "document.getElementById('roleSelect').style.display='none';"
				+ "document.getElementById('isForkGroupSelect').style.display='none';}");
		sb.append("</script>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}
	
	private static String generateUserSelectHtml(List<UserInfo> userlist){
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name='userSelect' id='userSelect' style='display:none'>");
		for(int i=0; i<userlist.size(); i++){
			sb.append("<option value='"+userlist.get(i).getUserId()+"'>"+userlist.get(i).getUserName()+"</optioin>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	private static String generateGroupSelectHtml(String selectName,List<GroupInfo> glist){
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name='"+selectName+"' id='"+selectName+"' style='display:none'>");
		for(int i=0; i<glist.size(); i++){
			sb.append("<option value='"+glist.get(i).getGroupId()+"'>"+glist.get(i).getGroupName()+"</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	
	private static String generateRoleSelectHtml(List<RoleInfo> rolelist){
		StringBuffer sb = new StringBuffer("");
		sb.append("<select name='roleSelect' id='roleSelect' style='display:none'>");
		for(int i=0; i<rolelist.size(); i++){
			sb.append("<option value='"+rolelist.get(i).getRoleId()+"'>"+rolelist.get(i).getRoleName()+"</option>");
		}
		return sb.toString();
	}
	
}
