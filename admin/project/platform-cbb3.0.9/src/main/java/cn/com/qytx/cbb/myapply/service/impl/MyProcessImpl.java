package cn.com.qytx.cbb.myapply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.myapply.dao.MyProcessDao;
import cn.com.qytx.cbb.myapply.domain.MyProcessed;
import cn.com.qytx.cbb.myapply.service.IMyProcessed;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service
@Transactional
public class MyProcessImpl extends BaseServiceImpl<MyProcessed> implements IMyProcessed{
    @Autowired
    MyProcessDao myProcessDao;
	
	@Override
	public Page<MyProcessed> findListByUserId(Pageable pageable,Integer userId,String moduleCode) {
		return myProcessDao.findListByUserId(pageable, userId,moduleCode);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void del(String instanceIds, String moduleCode) {
		myProcessDao.del(instanceIds,moduleCode);
	}

	@Override
	public List<MyProcessed> findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return myProcessDao.findByInstanceId(instanceId);
	}

}
