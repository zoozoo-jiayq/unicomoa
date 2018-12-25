package com.unicomoa.unicomoa.workplan;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.unicomoa.unicomoa.base.BaseRepository;
import com.unicomoa.unicomoa.base.BaseServiceProxy;

@Service
public class CustomerService extends BaseServiceProxy<Customer>{

	@Resource
	private CustomerRepository customerRepository;
	
	@Override
	protected BaseRepository<Customer> getBaseRepository() {
		// TODO Auto-generated method stub
		return customerRepository;
	}

}
