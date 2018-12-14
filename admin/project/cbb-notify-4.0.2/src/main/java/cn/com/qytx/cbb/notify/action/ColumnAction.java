package cn.com.qytx.cbb.notify.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.service.IGroup;

/**
 * 功能:部门专栏使用
 */
public class ColumnAction extends BaseActionSupport {
	private static final long serialVersionUID = 1288574756030082300L;
	
	@Autowired
	private IGroup groupService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 功能：得到部门专栏部门列表
	 */
	public void getGroupColumn(){
		String groupIds = sysConfigService.getSysConfig().get(SysConfig.BUMENZHUANLAN);
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		if(groupIds != null && groupIds.length()>=1){
			String[] groupIdsList = groupIds.split(",");
			if(groupIdsList.length>0){
				for(int i=0;i<groupIdsList.length;i++){
					GroupInfo groupInfo = groupService.findOne(Integer.valueOf(groupIdsList[i]));
					if(groupInfo!=null){
						groupList.add(groupInfo);
					}
				}
			}
		}
		ajax(groupList);
	}
}
