package cn.com.qytx.cbb.notify.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.qytx.cbb.notify.domain.Notify;
import cn.com.qytx.cbb.notify.domain.NotifyView;
import cn.com.qytx.cbb.notify.service.INotify;
import cn.com.qytx.cbb.notify.service.IWapNotify;
import cn.com.qytx.cbb.redpoint.utils.RedPointUtil;
import cn.com.qytx.platform.utils.spring.SpringUtil;
import cn.jpush.api.JPushClient;

/**
 * 功能: 公告获得红点相关信息并推送 
 * 版本: 1.0
 * 开发人员: zyf
 * 创建日期: 2015年3月19日
 * 修改日期: 2015年3月19日
 * 修改列表:
 */
public class NotifySendRPMSG implements Runnable {
	protected final static Logger LOGGER = LoggerFactory.getLogger(NotifySendRPMSG.class);
	
	private Object[] args;
	
	private String methodName;
	
	private String type;
	
	private Integer companyId;
	
	public NotifySendRPMSG(Object[] args,String methodName,String type,Integer companyId){
		this.args = args;
		this.methodName = methodName;
		this.type = type;
		this.companyId = companyId;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if(methodName.equals("save")||methodName.equals("update")){//新增修改
				Notify notify = (Notify) args[0];
				//处理未读数量变更
				dealNotify(notify);
			//新增修改 end
			}else if(methodName.equals("delByIds")){//公告删除
				String ids = args[0].toString();
				if(StringUtils.isNotEmpty(ids)){
					INotify notifyService = (INotify) SpringUtil.getBean("notifyService");
					String[] idsArr = ids.split(",");
					for(String notifyId:idsArr){
						Notify notify = notifyService.isDeleted().findOne(Integer.valueOf(notifyId));
						if(notify!=null){
							//处理未读数量变更
							dealNotify(notify);
						}
					}
				}
			//公告删除 end
			}else if(methodName.equals("saveNV")){//新增公告查看
				NotifyView notifyView = (NotifyView) args[0];
				Notify notify = (Notify) args[1];
				if(notifyView!=null){
					new Thread().sleep(1000);
					int userId = notifyView.getCreateUser().getUserId();
					Properties prop =  new Properties();
					prop.load(getClass().getResourceAsStream("/application.properties"));
					IWapNotify wapNotifyService = (IWapNotify) SpringUtil.getBean("wapNotifyService");
					JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
					Map<String, Object> extra = new HashMap<String, Object>();
					extra.put("mt", "redPoint");
					Integer columnId = notify.getColumnId();
					if( columnId ==  35){
						type = "notifyUnReadCount";
					}else if(columnId ==  808){
						type = "newsHospitalUnReadCount";
					}else if(columnId ==  814){
						type = "jobBriefUnReadCount";
					}else if(columnId ==  824){
						type = "superDocumentUnReadCount";
					}else if(columnId ==  830){
						type = "leaderSpeechUnReadCount";
					}
					extra.put(type, wapNotifyService.getNotifyUnReadCount(notify.getColumnId(), userId, notify.getCompanyId()));
					RedPointUtil.pushInfo(jpush, userId+"", extra,companyId);
				}
			//新增公告查看 end
			}else if(methodName.equals("approveNotify")){//公告审批
				Notify notify = (Notify) args[0];
				if(notify.getStatus()!=null && notify.getStatus().intValue() == 2){
					//处理未读数量变更
					dealNotify(notify);
				}
//				公告审批 end
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：处理未读数量变更
	 * @param notify
	 */
	private void dealNotify(Notify notify) throws Exception{
		Properties prop =  new Properties();
		prop.load(getClass().getResourceAsStream("/application.properties"));
		
		IWapNotify wapNotifyService = (IWapNotify) SpringUtil.getBean("wapNotifyService");
		if(notify.getStatus()!=null && notify.getStatus().intValue() == 2){//发布成功
			if(notify.getPublishScopeUserIds()!=null){
				JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
				String[] directionsArr=notify.getPublishScopeUserIds().toString().split(",");
				for (String direction : directionsArr) {
					if(StringUtils.isNotEmpty(direction)){
						Map<String, Object> extra = new HashMap<String, Object>();
						extra.put("mt", "redPoint");
						Integer columnId = notify.getColumnId();
						if( columnId ==  35){
							type = "notifyUnReadCount";
						}else if(columnId ==  808){
							type = "newsHospitalUnReadCount";
						}else if(columnId ==  814){
							type = "jobBriefUnReadCount";
						}else if(columnId ==  824){
							type = "superDocumentUnReadCount";
						}else if(columnId ==  830){
							type = "leaderSpeechUnReadCount";
						}
						extra.put(type, wapNotifyService.getNotifyUnReadCount(notify.getColumnId(), Integer.valueOf(direction), notify.getCompanyId()));
						RedPointUtil.pushInfo(jpush, direction, extra,companyId);
					}
				}
			}
		}
//		}else if(notify.getStatus()!=null && notify.getStatus().intValue() == 1){//提交审批
//			JPushClient jpush = new JPushClient(prop.getProperty("masterSecret"), prop.getProperty("appKey"));
//			String tag = notify.getAuditer().toString();
//			if (PushControl.isTest()) {
//				tag="test_"+tag;
//			}
//			Map<String, Object> extra = new HashMap<String, Object>();
//			extra.put("mt", "redPoint");
//			extra.put(type, wapNotifyService.getNotifyApproveCount(notify.getColumnId(), notify.getAuditer(), notify.getCompanyId()));
//			MessageResult mr= jpush.sendNotificationWithTag(sendNo, tag, "最新消息", "", 0, extra);
//			LOGGER.info(mr.toString());
	}

}
