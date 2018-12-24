package com.unicomoa.unicomoa.workplan;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.unicomoa.unicomoa.base.BaseController;
import com.unicomoa.unicomoa.base.Constant;
import com.unicomoa.unicomoa.utils.DateUtils;
import com.unicomoa.unicomoa.utils.Variant;

@RestController
@RequestMapping("/workplan")
public class WorkplanController extends BaseController {

	@Resource
	private WorkPlanService workPlanService;
	
	@RequestMapping("/list")
	public Object mylist(int userId,String selectedDate) {
		Sort sort = new Sort(Direction.DESC,"id");
		List<WorkPlan> plans = workPlanService.findByCreaterIDAndDayStr(userId, selectedDate,sort);
		return SUCCESS(plans);
	}
	
	@RequestMapping("/detail")
	public Object detail(int id) {
		WorkPlan plan = workPlanService.findById(id).get();
		return SUCCESS(plan);
	}
	
	@RequestMapping("/add")
	public Object add(@RequestBody Map<String,Object> request) throws ParseException {
		WorkPlan wp = new WorkPlan();
		wp.setPlanType(Constant.WORK_PLAN_TYPE.get(request.get("planType")));
		wp.setContent(Variant.valueOf(request.get("content")).stringValue(""));
		boolean oneDay = Variant.valueOf(request.get("oneDay")).booleanValue(false);
		wp.setOneDay(oneDay?1:0);
		if(!oneDay) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
			String startDate = request.get("beginDate")+" "+request.get("beginTime");
			String endDate = request.get("endDate")+" "+request.get("endTime");
			wp.setStartTime(sdf.parse(startDate));
			wp.setEndTime(sdf.parse(endDate));
		}else {
			wp.setStartTime(new Date(System.currentTimeMillis()));
			wp.setEndTime(new Date(System.currentTimeMillis()));
		}
		wp.setAddr(Variant.valueOf(request.get("addr")).stringValue(""));
		wp.setTarget(Variant.valueOf(request.get("target")).intValue(0));
		List canyurenIds = Variant.valueOf(request.get("canyurenIds")).listValue();
		List canyurennames = Variant.valueOf(request.get("canyurennames")).listValue();
		if(canyurenIds!=null && canyurenIds.size()>0) {
			List<Map<String,String>> list = new ArrayList<>();
			for(int i=0; i<canyurenIds.size(); i++) {
				Map<String,String> canyuren = new HashMap<String,String>();
				canyuren.put("id", canyurenIds.get(i).toString());
				canyuren.put("name", canyurennames.get(i).toString());
				list.add(canyuren);
			}
			String str = new Gson().toJson(list);
			wp.setCanyuren(str);
		}
		wp.setCreaterId(Variant.valueOf(request.get("createrId")).intValue(0));
		wp.setCreaterName(Variant.valueOf(request.get("createrName")).stringValue());
		wp.setCreateTime(new Timestamp(System.currentTimeMillis()));
		wp.setDayStr(DateUtils.date2Str(new Date(System.currentTimeMillis()), "yyyy-MM-dd"));
		workPlanService.save(wp);
		return SUCCESS();
	}
}
