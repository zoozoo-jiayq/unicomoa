package cn.com.qytx.cbb.affairs.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.platform.base.service.BaseService;

public interface IAffairsBody extends BaseService<AffairsBody>,Serializable{
	
	/**
	 * 通过类型和标题查询提醒事物是否存在
	 * @param smsType
	 * @param contextInfo
	 * @return
	 */
	public AffairsBody findByInfo(String smsType,String contextInfo);

	
	 /**
     * 保存事务提醒信息
     */
    public AffairsBody saveOrUpdate(AffairsBody affairsBody);
    /**
     * 保存事务提醒信息
     */
    public void saveOrUpdate(List<AffairsBody> bodyList);
    /**
     * 发送提醒接口
     * 包括发在线提醒，发短信，发推送
     * @param reminderInfo 存储发送信息对象
     * @return
     * @throws IOException 
     * @throws HttpException 
     */
    public void sendReminder(ReminderInfo reminderInfo)throws HttpException,IOException;
    /**
     * 删除相应模块下面的提醒
     * @Title: deleteReminder   
     * @param moduleName
     * @param recordId
     */
    public void deleteReminder(String moduleName,String recordId);
    
	public void deleteReminders(String moduleName, String recordIds);
}
