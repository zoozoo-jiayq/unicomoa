package cn.com.qytx.cbb.jbpmApp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;

import cn.com.qytx.cbb.jbpmApp.domain.AttenceVo;
import cn.com.qytx.cbb.jbpmApp.service.IWorkflowLeaveService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：请假流程统计
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2017年4月12日
 * 修改日期：2017年4月12日	
 */
public class WorkflowLeaveAction extends BaseActionSupport{
	private static final long serialVersionUID = 8695207501080682827L;
	
	@Resource(name="workflowLeaveImpl")
	private IWorkflowLeaveService workflowLeaveService;
	
	private Integer state;
	
	private Integer groupId;
	
	public String leaveList(){
        UserInfo user =getLoginUser();
        if(user!=null){
        	Page<AttenceVo> pageInfo = workflowLeaveService.getPageList(getPageable(), state, groupId);
        	int pageNumber =getPageable().getPageNumber();
        	if(getPageable().getPageNumber()==0){
        		pageNumber=1;
        	}
        	List<AttenceVo> list = pageInfo.getContent();
        	List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (list != null)
            {
                // 获取序号
            	
                int i = (pageNumber - 1) * this.getIDisplayLength() + 1;
                for (AttenceVo attenceVo : list)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 序号
                    map.put("no", i);

                    // 人员名称
                    map.put("userName", attenceVo.getUserName());

                    // 
                    map.put("groupName", attenceVo.getGroupName());

                    Integer state = attenceVo.getState();
                    String stateStr="";
                    if(state==0){
                    	stateStr="到岗";
                    }else{
                    	stateStr="未到岗";
                    }
                    map.put("stateStr", stateStr);
                    mapList.add(map);
                    i++;
                }
            }
            this.ajaxPage(pageInfo, mapList);
        }
		
		
		return null;
	}

	

	
  /**
    * 获取在职人数/到岗人数/出勤率
    * @return
    */
	public void findMapNum(){
		UserInfo user= getLoginUser();
		if(user!=null){
			Map<String,Object> map = workflowLeaveService.findMapNum(user.getCompanyId(),state,groupId);
		   Gson json =new Gson();
		   ajax(json.toJson(map));
		}
	}


public Integer getState() {
	return state;
}

public void setState(Integer state) {
	this.state = state;
}

public Integer getGroupId() {
	return groupId;
}

public void setGroupId(Integer groupId) {
	this.groupId = groupId;
}
 
	
	
}
