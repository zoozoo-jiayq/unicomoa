package com.unicomoa.unicomoa.workplan;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unicomoa.unicomoa.base.BaseController;

@Controller
public class CustomerController extends BaseController {

	@Resource
	private CustomerService customerService;
	@Resource
	private VisitorMydService mydService;
	@Resource
	private WorkPlanService workPlanService;
	@Resource
	private WorkPlanProgressService progressService;
	
	//营销二维码
	@RequestMapping("/sale/index")
	public Object index(ModelMap model,int id) {
		model.addAttribute("id", id);
		return "/index";
	}
	
	@RequestMapping(value="/sale/save")
	public Object save(Customer customer) {
		customerService.save(customer);
		workPlanService.updateTarget(customer.getWorkPlanId());
		return "/success";
	}
	
	//客户评价二维码
	@RequestMapping("/visit/index")
	public Object visitindex(ModelMap model,int id) {
		model.addAttribute("id", id);
		return "/visitindex";
	}
	
	@RequestMapping("/visit/save")
	public Object visitsave(VisitorMyd myd) {
		WorkPlanProgress progress = progressService.findById(myd.getProgressId()).get();
		myd.setWorkPlanId(progress.getWorkPlanId());
		mydService.save(myd);
		return "/success";
	}
}
