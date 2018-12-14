package cn.com.qytx.oa.productionSchedule.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.productionSchedule.domain.ProductionSchedule;
import cn.com.qytx.oa.productionSchedule.service.IProductionSchedule;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

public class ProductionScheduleAction extends BaseActionSupport{

	
	 /**
     * 序列号
     */
    private static final long serialVersionUID = -5088974549574219746L;
    
    
    private ProductionSchedule productionSchedule;
    
    @Autowired
    private IProductionSchedule productionScheduleService;
    @Autowired
    private IUser userService;
    
    private String startTime;
    
    private String endTime;
    
    private String startTimes;
    
    private Integer productionScheduleId;
    
    
    private String isRtime;
    
    
    /**
     *保存生产过程
     */
    public void productionScheduleSave(){
    	try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
    		UserInfo u = getLoginUser();
    		if(productionScheduleId!=null){
    			ProductionSchedule productionSchedulenew = productionScheduleService.findOne(productionScheduleId);
    			productionSchedulenew.setBothincome(productionSchedule.getBothincome());
    			productionSchedulenew.setCoalLoading(productionSchedule.getCoalLoading());
    			productionSchedulenew.setComparedPlan(productionSchedule.getComparedPlan());
    			productionSchedulenew.setDailyCarAssembly(productionSchedule.getDailyCarAssembly());
    			productionSchedulenew.setDeadLoad(productionSchedule.getDeadLoad());
    			productionSchedulenew.setDoorToDoor(productionSchedule.getDoorToDoor());
    			productionSchedulenew.setDoorToStand(productionSchedule.getDoorToStand());
    			productionSchedulenew.setGroceries(productionSchedule.getGroceries());
    			productionSchedulenew.setIncome(productionSchedule.getIncome());
    			productionSchedulenew.setLargeCoalMine(productionSchedule.getLargeCoalMine());
    			productionSchedulenew.setMiddle(productionSchedule.getMiddle());
    			productionSchedulenew.setSender(productionSchedule.getSender());
    			productionSchedulenew.setSendPersonPlan(productionSchedule.getSendPersonPlan());
    			productionSchedulenew.setSendPlan(productionSchedule.getSendPlan());
    			productionSchedulenew.setSendTons(productionSchedule.getSendTons());
    			productionSchedulenew.setSmallCoalMine(productionSchedule.getSmallCoalMine());
    			productionSchedulenew.setStandDoor(productionSchedule.getStandDoor());
    			productionSchedulenew.setStop(productionSchedule.getStop());
    			productionSchedulenew.setTransportSchedule(productionSchedule.getTransportSchedule());
    			productionSchedulenew.setUnload(productionSchedule.getUnload());
    			productionSchedulenew.setUseOfVehicles(productionSchedule.getUseOfVehicles());
    			productionSchedulenew.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    			productionSchedulenew.setrTime(new Timestamp(dateFormat.parse(startTimes).getTime()));
    			productionScheduleService.saveOrUpdate(productionSchedulenew);
    		}else{
    			productionSchedule.setCompanyId(u.getCompanyId());
        		productionSchedule.setIsDelete(0);
    			productionSchedule.setCreateTime(new Timestamp(System.currentTimeMillis()));
        		productionSchedule.setrTime(new Timestamp(dateFormat.parse(startTimes).getTime()));
        		productionScheduleService.saveOrUpdate(productionSchedule);
    		}
			ajax("1");
		} catch (Exception e) {
			e.printStackTrace();
			ajax("0");
			// TODO: handle exception
		}
    }
    
    /**
     * 删除生产过程
     */
    public void deleteproductionSchedule(){
    
    	if(productionScheduleId!=null){
    		try {
    			productionScheduleService.delete(productionScheduleId, false);
				ajax("success");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
    	}
    }
    
    
    /**
     * 报表统计
     */
    public void getTotelProduction(){
    	List<Map<String,Object>> mapList = productionScheduleService.getTotelProduction(startTime,endTime);
    	ajax(mapList);
    }
    
    /**
     * 查询详情
     * @return
     */
    public String updateProductionSchedule(){
    	if(productionScheduleId!=null){
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	productionSchedule = productionScheduleService.findOne(productionScheduleId);
    	if(productionSchedule.getrTime()!=null){
    		productionSchedule.setrTimeStr(dateFormat.format(productionSchedule.getrTime()));
    	}
    	}
    	return "success";
    }
    
    /**
     * 查询生产过程分页
     * @return
     */
    public String getProductionScheduleAll(){
    	
    	try {
    	   	 Page<ProductionSchedule> pageInfo = null;
    	     Sort sort = new Sort(new Sort.Order(Direction.DESC, "rTime"),new Sort.Order(Direction.DESC, "createTime"));
        	 pageInfo = productionScheduleService.findPageByVo(this.getPageable(sort), startTime, endTime);
        	 List<ProductionSchedule> productionScheduleList= pageInfo.getContent();
        	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	 List<Map<String, Object>> productionScheduleList1 = new ArrayList<Map<String,Object>>();
        	 if(productionScheduleList!=null&&productionScheduleList.size()>0){
        		 for (ProductionSchedule productionSchedule : productionScheduleList) {
        			    Map<String, Object> map = new HashMap<String, Object>();
        			    if(productionSchedule.getrTime()!=null){
        			    	map.put("creatTime", dateFormat.format(productionSchedule.getrTime()));
        			    }else{
        			    	map.put("creatTime","");
        			    }
        			    map.put("id", productionSchedule.getId());
        			    map.put("dailyCarAssembly", productionSchedule.getDailyCarAssembly()==null?"":productionSchedule.getDailyCarAssembly());
        			    map.put("stop", productionSchedule.getStop()==null?"":productionSchedule.getStop());
        			    map.put("transportSchedule", productionSchedule.getTransportSchedule()==null?"":productionSchedule.getTransportSchedule());
        			    map.put("coalLoading", productionSchedule.getCoalLoading()==null?"":productionSchedule.getCoalLoading());
    			        map.put("middle", productionSchedule.getMiddle()==null?"":productionSchedule.getMiddle());
    			        map.put("doorToStand", productionSchedule.getDoorToStand()==null?"":productionSchedule.getDoorToStand());
    			        map.put("Bothincome", productionSchedule.getBothincome()==null?"":productionSchedule.getBothincome());
    			        map.put("smallCoalMine", productionSchedule.getSmallCoalMine()==null?"":productionSchedule.getSmallCoalMine());
    			        map.put("largeCoalMine", productionSchedule.getLargeCoalMine()==null?"":productionSchedule.getLargeCoalMine());
    			        map.put("income", productionSchedule.getIncome()==null?"":productionSchedule.getIncome());
    			        map.put("sender", productionSchedule.getSender()==null?"":productionSchedule.getSender());
    			        map.put("standDoor", productionSchedule.getStandDoor()==null?"":productionSchedule.getStandDoor());
    			        map.put("groceries", productionSchedule.getGroceries()==null?"":productionSchedule.getGroceries());
    			        map.put("useOfVehicles", productionSchedule.getUseOfVehicles()==null?"": productionSchedule.getUseOfVehicles());
    			        map.put("deadLoad", productionSchedule.getDeadLoad()==null?"":productionSchedule.getDeadLoad());
    			        map.put("comparedPlan", productionSchedule.getComparedPlan()==null?"":productionSchedule.getComparedPlan());
    			        map.put("doorToDoor", productionSchedule.getDoorToDoor()==null?"":productionSchedule.getDoorToDoor());
    			        map.put("sendTons", productionSchedule.getSendTons()==null?"":productionSchedule.getSendTons());
    			        map.put("sendPlan", productionSchedule.getSendPlan()==null?"":productionSchedule.getSendPlan());
    			        map.put("unload", productionSchedule.getUnload()==null?"":productionSchedule.getUnload());
    			        map.put("sendPersonPlan", productionSchedule.getSendPersonPlan()==null?"":productionSchedule.getSendPersonPlan());
    			        productionScheduleList1.add(map);
        		 }
        	 }
        	   Map<String, Object> jsonMap = new HashMap<String, Object>();
               jsonMap.put("aaData", productionScheduleList1);
   		 this.ajaxPage(pageInfo, productionScheduleList1);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    	
    }

    
    /**
     * 判断是否存在
     */
    public void isRtime(){
    	ProductionSchedule productionSchedule =   productionScheduleService.getproductionSchedule(isRtime);
    	 if(productionSchedule!=null){
    		 ajax(productionSchedule);
    	 }
    }
    
    
    /**
     * 手机端分页
     * @return
     */
    public String getProductionScheduleMui(){
    	
    	try {
    	   	 Page<ProductionSchedule> pageInfo = null;
    	     Sort sort = new Sort(new Sort.Order(Direction.DESC, "rTime"),new Sort.Order(Direction.DESC, "createTime"));
        	 pageInfo = productionScheduleService.findPageByVo(this.getPageable(sort), startTime, endTime);
        	 List<ProductionSchedule> productionScheduleList= pageInfo.getContent();
        	  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        	 List<Map<String, Object>> productionScheduleList1 = new ArrayList<Map<String,Object>>();
        	 if(productionScheduleList!=null&&productionScheduleList.size()>0){
        		 for (ProductionSchedule productionSchedule : productionScheduleList) {
        			    Map<String, Object> map = new HashMap<String, Object>();
        			    if(productionSchedule.getrTime()!=null){
        			    	map.put("creatTime", dateFormat.format(productionSchedule.getrTime()));
        			    }else{
        			    	map.put("creatTime","");
        			    }
        			    map.put("id", productionSchedule.getId());
        			    map.put("dailyCarAssembly", productionSchedule.getDailyCarAssembly()==null?"":productionSchedule.getDailyCarAssembly());
        			    map.put("stop", productionSchedule.getStop()==null?"":productionSchedule.getStop());
        			    map.put("transportSchedule", productionSchedule.getTransportSchedule()==null?"":productionSchedule.getTransportSchedule());
        			    map.put("coalLoading", productionSchedule.getCoalLoading()==null?"":productionSchedule.getCoalLoading());
    			        map.put("middle", productionSchedule.getMiddle()==null?"":productionSchedule.getMiddle());
    			        map.put("doorToStand", productionSchedule.getDoorToStand()==null?"":productionSchedule.getDoorToStand());
    			        map.put("Bothincome", productionSchedule.getBothincome()==null?"":productionSchedule.getBothincome());
    			        map.put("smallCoalMine", productionSchedule.getSmallCoalMine()==null?"":productionSchedule.getSmallCoalMine());
    			        map.put("largeCoalMine", productionSchedule.getLargeCoalMine()==null?"":productionSchedule.getLargeCoalMine());
    			        map.put("income", productionSchedule.getIncome()==null?"":productionSchedule.getIncome());
    			        map.put("sender", productionSchedule.getSender()==null?"":productionSchedule.getSender());
    			        map.put("standDoor", productionSchedule.getStandDoor()==null?"":productionSchedule.getStandDoor());
    			        map.put("groceries", productionSchedule.getGroceries()==null?"":productionSchedule.getGroceries());
    			        map.put("useOfVehicles", productionSchedule.getUseOfVehicles()==null?"": productionSchedule.getUseOfVehicles());
    			        map.put("deadLoad", productionSchedule.getDeadLoad()==null?"":productionSchedule.getDeadLoad());
    			        map.put("comparedPlan", productionSchedule.getComparedPlan()==null?"":productionSchedule.getComparedPlan());
    			        map.put("doorToDoor", productionSchedule.getDoorToDoor()==null?"":productionSchedule.getDoorToDoor());
    			        map.put("sendTons", productionSchedule.getSendTons()==null?"":productionSchedule.getSendTons());
    			        map.put("sendPlan", productionSchedule.getSendPlan()==null?"":productionSchedule.getSendPlan());
    			        map.put("unload", productionSchedule.getUnload()==null?"":productionSchedule.getUnload());
    			        map.put("sendPersonPlan", productionSchedule.getSendPersonPlan()==null?"":productionSchedule.getSendPersonPlan());
    			        productionScheduleList1.add(map);
        		 }
        	 }
        	   Map<String, Object> jsonMap = new HashMap<String, Object>();
               jsonMap.put("aaData", productionScheduleList1);
        	   jsonMap.put("totalPage", pageInfo.getTotalPages()+"");
   		 ajax(jsonMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
    	
    }
    

	public ProductionSchedule getProductionSchedule() {
		return productionSchedule;
	}


	public void setProductionSchedule(ProductionSchedule productionSchedule) {
		this.productionSchedule = productionSchedule;
	}


	public IUser getUserService() {
		return userService;
	}


	public void setUserService(IUser userService) {
		this.userService = userService;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public String getEndTime() {
		return endTime;
	}


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public String getStartTimes() {
		return startTimes;
	}


	public void setStartTimes(String startTimes) {
		this.startTimes = startTimes;
	}


	public Integer getProductionScheduleId() {
		return productionScheduleId;
	}


	public void setProductionScheduleId(Integer productionScheduleId) {
		this.productionScheduleId = productionScheduleId;
	}

	public String getIsRtime() {
		return isRtime;
	}

	public void setIsRtime(String isRtime) {
		this.isRtime = isRtime;
	}





    
    
}
