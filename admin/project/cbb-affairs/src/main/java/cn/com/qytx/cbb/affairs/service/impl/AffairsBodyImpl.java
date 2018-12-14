package cn.com.qytx.cbb.affairs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.dao.AffairsBodyDao;
import cn.com.qytx.cbb.affairs.dao.AffairsDao;
import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.domain.ReminderInfo;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.affairs.service.IPushMobile;
import cn.com.qytx.cbb.affairs.service.ISMS;
import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.StringUtil;
import cn.com.qytx.platform.utils.ThreadPoolUtils;

@Service
@Transactional
public class AffairsBodyImpl extends BaseServiceImpl<AffairsBody> implements IAffairsBody{
	
	@Autowired
	private AffairsBodyDao affairsBodyDao;
	
	@Autowired
	private AffairsDao affairsDao;
	
	@Autowired
	private IUser userDao;
	
	@Resource
	ISMS smsService;
	
	@Resource
	IPushMobile pushMobileService;
	
	
	@Transactional(readOnly=true)
	public AffairsBody findByInfo(String smsType, String contextInfo) {
		return affairsBodyDao.findByInfo(smsType,contextInfo);
	}
	
	 /**
     * 保存事务提醒信息
     */
	@Override
    public AffairsBody saveOrUpdate(AffairsBody affairsBody)
    {
        // 首先保存事务提醒实体类
    	if(affairsBody!=null){
        UserInfo userInfo =userDao.findOne(affairsBody.getCreateUserInfo().getUserId());
    	
        affairsBody.setCreateUserInfo(userInfo);
        affairsBody.setFromUserInfo(userInfo);
    	}
       AffairsBody  result =  affairsBodyDao.saveOrUpdate(affairsBody);
        if (null != affairsBody)
        {
            // 根据被提醒人分解成多个对象
            List<Affairs> affairsList = decomposeAffairsBody(affairsBody);
            // 保存被提醒信息
            for (Affairs affairs : affairsList)
            {
            	affairsDao.saveOrUpdate(affairs);
            }
        }

        return result;
    }
	/**
     * 保存事务提醒信息
     */
	@Override
    public void saveOrUpdate(List<AffairsBody> bodyList)
    {
		bodyList=affairsBodyDao.saveOrUpdate(bodyList);
		List<Affairs> affairsList=new ArrayList<Affairs>();
		for(AffairsBody body:bodyList){
			// 根据被提醒人分解成多个对象
			if(body!=null){
				List<Affairs> list = decomposeAffairsBody(body);
				affairsList.addAll(list);
			}
		}
		affairsDao.saveOrUpdate(affairsList);
    }

    public List<Affairs> decomposeAffairsBody(AffairsBody affairsBody)
    {
        List<Affairs> affairsList = new ArrayList<Affairs>();
        String toIds = affairsBody.getToIds();
        Affairs affairs;
        if (!StringUtil.isEmpty(toIds))
        {
            String[] toIdsArr = toIds.split(CbbConst.USERID_SEGMENTATION);
            for (String toId : toIdsArr)
            {
                if (!StringUtil.isEmpty(toId))
                {
                    affairs = new Affairs();
                    affairs.setCreateTime(affairsBody.getCreateTime());
                    affairs.setCreateUserInfo(affairsBody.getCreateUserInfo());
                    affairs.setAffairsBody(affairsBody);
                    affairs.setRemindFlag(MessageConst.SENDED);
                    affairs.setIsDelete(CbbConst.NOT_DELETE);
                    affairs.setCompanyId(affairsBody.getCompanyId());

                    // 设置接收人信息
                    int userId = Integer.parseInt(toId);
                    if (userId == affairsBody.getCreateUserInfo().getUserId())
                    {
                        affairs.setToUserInfo(affairsBody.getCreateUserInfo());
                    }
                    else
                    {
                        UserInfo toUserInfo = new UserInfo();
                        toUserInfo.setUserId(Integer.parseInt(toId));
                        affairs.setToUserInfo(toUserInfo);
                    }
                    affairs.setRemindTime(affairsBody.getSendTime());
                    if(affairsBody.getSendTime().getTime()<System.currentTimeMillis()){
                    	affairs.setRemindFlag(1);
                    }
                    affairsList.add(affairs);
                }
            }
        }
        return affairsList;
    }

	public void sendReminder(ReminderInfo reminderInfo) {
		ThreadPoolUtils.getInstance().getThreadPool().execute(new Thread(new ThreadSendMsg(reminderInfo)));
	}

	@Override
	public void deleteReminder(String moduleName, String recordId) {
		List<AffairsBody> list=affairsBodyDao.findAffairsBodyByRecordId(moduleName, recordId);
		if(list!=null&&list.size()>0){
			for(AffairsBody body:list){
				affairsBodyDao.delete(body.getId(), true);
				affairsDao.deleteAffairsByBodyId(body.getId()+"");
			}
		}
	}
	
	public void deleteReminders(String moduleName, String recordIds) {
		if(StringUtils.isNotBlank(recordIds))
		{
			if(recordIds.startsWith(","))
			{
				recordIds=recordIds.substring(1,recordIds.length());
			}
			if(recordIds.endsWith(","))
			{
				recordIds=recordIds.substring(0,recordIds.length()-1);
			}
		}
		if(StringUtils.isNotBlank(recordIds)){
			affairsDao.deleteAffairsByRecordIds(moduleName,recordIds);
			affairsBodyDao.deleteByRecordIds(moduleName,recordIds);
		}
	}
	
}
