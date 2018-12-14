package cn.com.qytx.cbb.jpush.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.jpush.dao.PushUserDao;
import cn.com.qytx.cbb.jpush.domain.PushUser;
import cn.com.qytx.cbb.jpush.service.IPushUser;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;

@Service("pushUserService")
@Transactional
public class PushUserImpl extends BaseServiceImpl<PushUser> implements IPushUser{
	@Resource(name="pushUserDao")
	private PushUserDao pushUserDao;
    @Resource(name="userDao")
    private UserDao<UserInfo> userDao;
	/**
	 * 
	 * 功能：添加发布人
	 * @param pushId
	 * @param userIds
	 * @return
	 */
	public int addPushUser(Integer pushId,String userIds){
		if(StringUtils.isNotEmpty(userIds)){
			String[] userIdArr=userIds.split(",");
			for (String userId : userIdArr) {
				    PushUser pushUser= new PushUser();
					pushUser.setPushId(pushId);
					pushUser.setUserType(1);
					pushUser.setUserId(Integer.parseInt(userId));
					UserInfo ui =  userDao.findOne(Integer.parseInt(userId));
					if(ui!=null){
						pushUser.setUserName(ui.getUserName());
						pushUserDao.saveOrUpdate(pushUser);
					}
					//	pushUserList.add(pushUser);
			}
		}
	    return 1;
	}
	/**
	 * 
	 * 功能：得到推荐人员
	 * @param pushId
	 * @return
	 */
	public String findUserNamesByPushId(Integer pushId){
		List<PushUser> nameList= (List<PushUser>) pushUserDao.findUserByPushId(pushId);
		String names="";
		for (PushUser pushUser : nameList) {
			if ("".equals(names)) {
				names+=pushUser.getUserName();
			}else {
				names=names+","+pushUser.getUserName();
			}
		}
		return names;
	}
	/**
	 * 
	 * 功能：得到推荐人员id
	 * @param pushId
	 * @return
	 */
	public String findUserIdsByPushId(Integer pushId){
		List<PushUser> idList= (List<PushUser>) pushUserDao.findUserByPushId(pushId);
		String ids="";
		for (PushUser pushUser : idList) {
			if ("".equals(ids)) {
				ids+=pushUser.getUserId();
			}else {
				ids=ids+","+pushUser.getUserId();
			}
		}
		return ids;
	}
	public PushUser findByPushIdAndUserId(Integer pushId,Integer userId){
		return pushUserDao.findByPushIdAndUserId(pushId, userId);
	}
}
