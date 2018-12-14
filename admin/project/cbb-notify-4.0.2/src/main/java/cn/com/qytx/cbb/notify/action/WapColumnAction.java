package cn.com.qytx.cbb.notify.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.service.IPlatformParameter;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.cbb.notify.service.IWapNotifyView;
import cn.com.qytx.cbb.notify.vo.TbColumnSetting;
import cn.com.qytx.cbb.systemSet.domain.SysConfig;
import cn.com.qytx.cbb.systemSet.service.SysConfigService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.utils.JsonUtils;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:手机端部门专栏列表
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2014年8月6日
 * 修改日期: 2014年8月6日
 * 修改列表:
 */
public class WapColumnAction extends BaseActionSupport{
	
	private static final long serialVersionUID = 4024410660048171499L;
	@Resource(name = "platformParameterService")
	private IPlatformParameter platformParameterService;
	@Resource(name = "wapNotifyService")
	private IWapNotify wapNotifyService;
	@Resource(name = "wapNotifyViewImpl")
	private IWapNotifyView wapNotifyViewService;
	
	@Autowired
	private IGroup groupService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	private int columnId;
	private int userId;
	private int notifyType;
	private String subject;
	private Integer notifyId;
	private String attmentIds;
	private String groupId;
	/**
	 * 部门专栏列表
	 * @return
	 */
	public String clmViewList(){
		Sort sort = new Sort(new Sort.Order(Direction.DESC,"isTop"),new Sort.Order(Direction.DESC,"createDate"));
        TbColumnSetting tbColumnSetting = (TbColumnSetting) platformParameterService.findEntityByInstenceId(columnId);
        Page<Notify> pageInfo = wapNotifyService.clmViewList(getPageable(sort),subject,userId,notifyType,columnId,groupId);
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        List<Notify> list = pageInfo.getContent();
        if(list!=null && list.size() > 0){
        	 String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort()+getRequest().getContextPath()+"/";
        	for(Notify notify:list){
        		Map<String,Object> map = new HashMap<String, Object>();
        		map.put("notifyId",notify.getId());
        		map.put("createTime",DateTimeUtil.dateToString(notify.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        		map.put("subject",notify.getSubject());
        		map.put("content",delHTMLTag(notify.getContent()));
        		map.put("createUserName",notify.getCreateUser().getUserName());
        		map.put("notifyType",notify.getNotifyType());
        		map.put("isTop",notify.getIsTop());
        		if(wapNotifyViewService.isReadNotifyView(userId,notify.getId())){
        			map.put("isSignCheck",1);
        		}else{
        			map.put("isSignCheck",0);
        		}
        		if(!"".equals(notify.getImages())){
        			String[] imageId = notify.getImages().split(",");
        			if(imageId[0]!=null &&!"".equals(imageId[0])){
        				map.put("image",basePath+"/"+"filemanager/htmlPreview.action?attachId="+imageId[0]+"&_clientType=wap");
        			}else{
        				map.put("image",basePath+"/"+"filemanager/htmlPreview.action?attachId="+imageId[1]+"&_clientType=wap");
        			}
        		}else{
        			map.put("image","");
        		}
        		map.put("attmentIds",notify.getAttment());
        		dataList.add(map);
        	}
        }
	    Map<String,Object> returnMap = new HashMap<String,Object>();    
	    returnMap.put("showImage",tbColumnSetting.getShowImage());
	    returnMap.put("mapList",dataList);
	    ajax("100||"+JsonUtils.generJsonString(returnMap));
		return null;
	}

	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; 
        String regEx_html="<[^>]+>";  
        
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); 
        
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll("");  
        
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll("");  

       return htmlStr.trim(); 
    }

	
	/**
	 * 获得栏目专栏栏目列表
	 */
	public void getGroupColumn(){
		String groupIds = sysConfigService.getSysConfig().get(SysConfig.BUMENZHUANLAN);
		List<GroupInfo> groupList = new ArrayList<GroupInfo>();
		if(groupIds.length()>=1){
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
		ajax("100||"+JsonUtils.generJsonString(groupList));
	}
	
	
	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Integer getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Integer notifyId) {
		this.notifyId = notifyId;
	}
	public String getAttmentIds() {
		return attmentIds;
	}
	public void setAttmentIds(String attmentIds) {
		this.attmentIds = attmentIds;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	} 
	
}
