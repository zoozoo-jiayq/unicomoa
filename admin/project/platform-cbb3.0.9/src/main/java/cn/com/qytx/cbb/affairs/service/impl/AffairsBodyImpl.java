package cn.com.qytx.cbb.affairs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import cn.com.qytx.cbb.consts.CbbConst;
import cn.com.qytx.cbb.consts.MessageConst;
import cn.com.qytx.cbb.util.StringUtil;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

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


    private List<Affairs> decomposeAffairsBody(AffairsBody affairsBody)
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
                    affairsList.add(affairs);
                }
            }
        }
        return affairsList;
    }

	public void sendReminder(ReminderInfo reminderInfo) {
		ThreadPoolUtils.getInstance().getThreadPool().execute(new Thread(new ThreadSendMsg(reminderInfo)));
	}
	
}
