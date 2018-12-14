package cn.com.qytx.cbb.myapply.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.myapply.dao.MyStartedDao;
import cn.com.qytx.cbb.myapply.dao.MyWaitProcessDao;
import cn.com.qytx.cbb.myapply.domain.MyStarted;
import cn.com.qytx.cbb.myapply.domain.MyWaitProcess;
import cn.com.qytx.cbb.myapply.service.IMyStarted;
import cn.com.qytx.cbb.myapply.service.MyApplyService.ModuleCode;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service
@Transactional
public class MyStartedImpl extends BaseServiceImpl<MyStarted> implements IMyStarted{
    @Autowired
    MyStartedDao myStartedDao;
    @Autowired
    MyWaitProcessDao myWaitProcessDao;
    
	@Override
	public Page<MyStarted> findListByUserId(Pageable pageable,Integer userId,String moduleCode) {
		Page<MyStarted> r = myStartedDao.findListByUserId(pageable, userId,moduleCode);
		if(r!=null){
			List<MyStarted> list=r.getContent();
			if(list!=null&&list.size()>0){
				for(MyStarted myStarted:list){
					List<Map<String,String>> processerUser=new ArrayList<Map<String,String>>();
					List<MyWaitProcess> waitList=myWaitProcessDao.findByInstanceId(myStarted.getInstanceId());
					if(waitList!=null&&waitList.size()>0){
						for(MyWaitProcess myWaitProcess:waitList){
							Map<String,String> map=new HashMap<String,String>();
							map.put("userName", myWaitProcess.getProcesserName());
							map.put("userId", myWaitProcess.getProcesserId().toString());
							processerUser.add(map);
						}
					}
					myStarted.setProcesserUser(processerUser);
				}
			}
		}
		return r;
	}

	@Override
	public MyStarted findByInstanceId(ModuleCode code,String instanceId) {
		// TODO Auto-generated method stub
		return myStartedDao.findByModuleCodeAndInstanceId(code, instanceId);
	}
	
	@Override
	public MyStarted findByInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		return myStartedDao.findByModuleCodeAndInstanceId(instanceId);
	}
	
}
