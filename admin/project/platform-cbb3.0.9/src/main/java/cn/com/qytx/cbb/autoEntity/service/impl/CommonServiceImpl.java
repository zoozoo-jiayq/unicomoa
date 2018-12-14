package cn.com.qytx.cbb.autoEntity.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.autoCreateEntity.dao.CommonDao;
import cn.com.qytx.cbb.autoEntity.service.CommonService;

@Transactional
@Service
public class CommonServiceImpl  implements
		CommonService {

	@Resource
	private CommonDao commonDao;

	@Override
	public Object findOne(Class c, int id) {
		// TODO Auto-generated method stub
		return commonDao.findOne(c, id);
	}
}
