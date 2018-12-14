package cn.com.qytx.cbb.notify.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.cbb.redpoint.utils.RedPointUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.rangecontrol.PushControl;
import cn.jpush.api.JPushClient;

/**
 * @author jiayongqiang
 * 公告对KXH提供的接口
 */
public class SyncNotifyForKXH extends BaseActionSupport {

	private Notify notify;
	
	private Integer companyId;
	
	private String ids;
	
	@Resource
	private INotify notifyService;
	@Autowired
	private PushPlatService pushPlatService;
	@Resource
	private IUser userService;
	@Resource
	private IWapNotify wapNotifyService;
	
	/**
	 * 创建公告服务
	 * @return
	 */
	public String createNotify(){
		notify.setReason("init");
		notify.setCreateDate(new Date());
		notify.setAuditer(notify.getCreateUser().getUserId());
		notifyService.save(notify);
		return null;
	}
	
	/**
	 * 广播公告服务
	 * @return
	 * @throws IOException 
	 */
	public String broadcaseNotify() throws IOException{
		Sort sort = new Sort(Direction.ASC,"id");
		setIDisplayStart(0);
		setIDisplayLength(1);
		List<Notify> list = notifyService.companyId(companyId).findAll("reason='init'", getPageable(sort)).getContent();
		if(list!=null && list.size()>0 && ids!=null){
			this.notify  = list.get(0);
			updatePublishInfos(notify, ids);
			String[] idsArr = ids.split(",");
			try{
				Properties prop =  new Properties();
				prop.load(getClass().getResourceAsStream("/application.properties"));
				JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
				for(String id:idsArr){
					if(StringUtils.isNotEmpty(id)){
						Map<String, Object> extra = new HashMap<String, Object>();
						extra.put("mt", "redPoint");
						extra.put("notifyUnReadCount", wapNotifyService.getNotifyUnReadCount(notify.getColumnId(), Integer.valueOf(id), notify.getCompanyId()));
						RedPointUtil.pushInfo(jpush, id, extra,companyId);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			createNotify();
		}
		return null;
	}
	
	private void updatePublishInfos(Notify notify,String ids){
		String idstr = notify.getPublishScopeUserIds();
		String unames = notify.getPublishScopeUserNames();
		String addNames = "";
		List<UserInfo> ulist = userService.findUsersByIds(ids);
		for(Iterator<UserInfo> it = ulist.iterator(); it.hasNext();){
			UserInfo u = it.next();
			addNames+=u.getUserName();
			if(it.hasNext()){
				addNames+=",";
			}
		}
		if(idstr==null){
			idstr = ids;
			unames = addNames;
		}else if(idstr.endsWith(",")){
			idstr+=ids;
			unames+=addNames;
		}else{
			idstr+=","+ids;
			unames+=","+addNames;
		}
		notify.setPublishScopeUserIds(idstr);
		notify.setPublishScopeUserNames(unames);
		notifyService.saveOrUpdate(notify);
	}

	private String checkTest(String tag){
    	if (PushControl.isTest()) {
			tag="test_"+tag;
    	}
    	return tag;
	}
	
	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
