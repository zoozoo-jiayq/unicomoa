package com.unicomoa.unicomoa.workplan;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unicomoa.unicomoa.base.BaseController;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

	@Resource
	private CustomerService customerService;
	@Resource
	private WorkPlanService workPlanService;
	
	@RequestMapping("/index")
	public Object index(ModelMap model,int id) {
		model.addAttribute("id", id);
		return "/index";
	}
	
	@RequestMapping(value="/save")
	public Object save(Customer customer) {
		customerService.save(customer);
		workPlanService.updateTarget(customer.getWorkPlanId());
		return "/success";
	}
}
