package cn.com.qytx.cbb.affairs.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.dao.AffairsDao;
import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.service.IAffairs;
import cn.com.qytx.cbb.affairs.vo.AffairsVo;
import cn.com.qytx.cbb.consts.CbbConst;
import cn.com.qytx.cbb.consts.MessageConst;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;

@Service
@Transactional
public class AffairsImpl extends BaseServiceImpl<Affairs> implements IAffairs{
	
	@Autowired
	private AffairsDao affairsDao;
	
	@Transactional(readOnly=true)
	public Affairs findById(Integer affairsId) {
		return affairsDao.findOne(affairsId);
	}

	@Transactional(readOnly=true)
	public Page<Affairs> findPageByUserId(Pageable pageable, Integer id,
			Integer remindFlag) {
		return affairsDao.findPageByUserId(pageable, id, remindFlag);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteBatch(Integer[] affairs) {
		 if (null != affairs){
            for (Integer affairId : affairs)
            {
                affairsDao.updateReadedAffairs(affairId, MessageConst.DELETED);
            }
        }
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateReadedAffairs(Integer[] affairs, Integer remindFlag) {
		 if (null != affairs && null != remindFlag)
	        {
	            for (Integer affairId : affairs)
	            {
	                affairsDao.updateReadedAffairs(affairId, remindFlag);
	            }
	        }
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateReceivedAffairs(Integer userId, Integer remindFlag) {
		affairsDao.updateReceivedAffairs(userId, remindFlag);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllReaded(Integer userId) {
		  affairsDao.deleteAllReaded(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllUnReaded(Integer userId) {
		affairsDao.deleteAllUnReaded(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllAffairs(Integer userId) {
		 affairsDao.deleteAllAffairs(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateAllReaded(Integer userId) {
		  affairsDao.updateAllReaded(userId);
	}

	@Transactional(readOnly=true)
	public Page<Affairs> findSendPageByUserId(Pageable pageable, Integer id) {
		  return affairsDao.findSendPageByUserId(pageable, id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteSendAffairs(Integer[] affairs) {
		 if (null != affairs)
	        {
	            for (Integer affairId : affairs)
	            {
	            	affairsDao.delete(affairId,false);
	            }
	        }
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllSendReaded(Integer id) {
		 affairsDao.deleteAllSendReaded(id);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllSendAffairs(Integer userId) {
		 affairsDao.deleteAllSendAffairs(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteToUserDeleted(Integer userId) {
		affairsDao.deleteToUserDeleted(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public boolean retransmissionAffairs(Integer affairsId, UserInfo userInfo) {
		Affairs affairs = affairsDao.findOne(affairsId);
        if (null == affairs)
        {
            return false;
        }

        // 新事务提醒
        Affairs newAffairs = new Affairs();
        newAffairs.setCreateUserInfo(affairs.getCreateUserInfo());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        newAffairs.setCreateTime(now);
        newAffairs.setIsDelete(CbbConst.NOT_DELETE);
        newAffairs.setCompanyId(userInfo.getCompanyId());
        newAffairs.setRemindFlag(MessageConst.SENDED);
        newAffairs.setRemindTime(now);
        newAffairs.setAffairsBody(affairs.getAffairsBody());
        newAffairs.setToUserInfo(affairs.getToUserInfo());
        affairsDao.saveOrUpdate(newAffairs);
        return true;
	}

	@Transactional(readOnly=true)
	public List<Affairs> getAllAffairsByVo(AffairsVo affairsVo) {
		 return affairsDao.getAllAffairsByVo(affairsVo);
	}

	@Transactional(readOnly=true)
	public List<Affairs> findAllByUserId(Integer userId, Integer remindFlag) {
		 return affairsDao.findAllByUserId(userId, remindFlag);
	}

	@Transactional(readOnly=true)
	public List<Affairs> findUnReadByUserId(Integer userId) {
		 return affairsDao.findUnReadByUserId(userId);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAffairsByVo(AffairsVo affairsVo) {
		List<Affairs> affairList = affairsDao.getAllAffairsByVo(affairsVo);
        if (null != affairList && !affairList.isEmpty())
        {
            affairsDao.deleteAffairsList(affairList);
        }
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateModuleReaded(Integer userId, String moduleName) {
		 affairsDao.updateModuleReaded(userId, moduleName);
	}

	@Transactional(readOnly=true)
	public int getNewAffairsNum(Integer userId) {
		  return affairsDao.getNewAffairsNum(userId);
	}

	@Transactional(readOnly=true)
	public List<Integer> findUnReadAffairsUser() {
		  return affairsDao.findUnReadAffairsUser();
	}

}
