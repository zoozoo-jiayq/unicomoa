package cn.com.qytx.cbb.myapply.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.myapply.dao.MyWaitProcessDao;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyWaitProcess;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service("myWaitProcessService")
@Transactional
public class MyWaitProcessImpl extends BaseServiceImpl<MyWaitProcess> implements IMyWaitProcess{
    
	@Autowired
	MyWaitProcessDao myWaitProcessDao;
	
	@Override
	public Page<MyWaitProcess> findListByUserId(Pageable pageable,Integer userId,String moduleCode) {
		return myWaitProcessDao.findListByUserId(pageable, userId,moduleCode);
	}

	@Override
	public MyWaitProcess findByModuleCodeAndInstanceId(ModuleCode code,
			String instanceId, Integer approver) {
		// TODO Auto-generated method stub、
		return myWaitProcessDao.findByModuleCodeInstanceId(code, instanceId, approver);
	}
	public void del(String instanceIds, String moduleCode){
		myWaitProcessDao.del(instanceIds,moduleCode);
	}
	
	@Override
	public int myWaitCount(Integer userId){
		return  myWaitProcessDao.myWaitCount(userId);
	}
	
	/**
	 * 功能：获得指定模块下面待处理数量
	 * @param userId
	 * @param moduleCode
	 * @return
	 */
	public int myWaitModuleCount(Integer userId, String moduleCode){
		return myWaitProcessDao.myWaitModuleCount(userId, moduleCode);
	}

	@Override
	public MyWaitProcess saveOrUpdate(MyWaitProcess entity) {
		// TODO Auto-generated method stub
		return super.saveOrUpdate(entity);
	}
	
	@Override
    public List<MyWaitProcess> saveList(Iterable<MyWaitProcess> list) {
        return super.saveOrUpdate(list);
    }
	
	/**
	 * 功能：获得指定模块下面指定流程的审批人
	 * @param instanceIds
	 * @param moduleCode
	 * @return
	 */
	public List<Integer> getProcesserByInstanceIds(String instanceIds,String moduleCode){
		return myWaitProcessDao.getProcesserByInstanceIds(instanceIds, moduleCode);
	}

	@Override
	public List<MyWaitProcess> findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return myWaitProcessDao.findByInstanceId(instanceId);
	}
	
	
}
