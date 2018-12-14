package cn.com.qytx.cbb.autoEntity.service.impl;

import org.springframework.stereotype.Service;

import cn.com.qytx.cbb.autoEntity.service.AutoCreateTableService;

@Service
public class DefaultCreateTableImpl implements AutoCreateTableService {

	@Override
	public void createTable(Object info) {
		// TODO Auto-generated method stub
		System.out.println("已创建表：Citys");
	}

}
