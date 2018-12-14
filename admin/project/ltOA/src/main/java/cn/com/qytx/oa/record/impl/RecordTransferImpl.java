package cn.com.qytx.oa.record.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.RecordTransferDao;
import cn.com.qytx.oa.record.dao.UserRecordDao;
import cn.com.qytx.oa.record.domain.RecordTransfer;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.oa.record.service.IRecordTransfer;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;

@Service("recordTransferImpl")
@Transactional
public class RecordTransferImpl extends BaseServiceImpl<RecordTransfer> implements IRecordTransfer {
	private static final long serialVersionUID = 8537935890885165582L;

	@Resource(name="recordTransferDao")
	private RecordTransferDao recordTransferDao;
	
	@Resource(name="userDao")
	private UserDao<UserInfo> userDao;
	
	@Autowired
    private UserRecordDao userRecordDao;
	
	
	@Override
	public Page<RecordTransfer> findRecordTransferPage(Pageable page,
			Integer userId) {
		return recordTransferDao.findRecordTransferPage(page,userId);
	}

	@Override
	public void addOrUpdateTransfer(RecordTransfer rt,UserRecord userRecord) {
		UserInfo userInfo = userDao.findOne(rt.getUserInfo().getUserId());
		if(rt.getId()!=null){
			if(rt.getIsLast().intValue()==1){//最后一条,更新部门 和 角色
				userInfo.setGroupId(rt.getPostGroupId());
			}
		}else{
			recordTransferDao.updateTransferLast(rt.getCompanyId());
			userInfo.setGroupId(rt.getPostGroupId());//更新部门 和 角色
		}
		//保存调动记录
		recordTransferDao.saveOrUpdate(rt);
		userDao.saveOrUpdate(userInfo);
		userRecordDao.saveOrUpdate(userRecord);
	}


}
