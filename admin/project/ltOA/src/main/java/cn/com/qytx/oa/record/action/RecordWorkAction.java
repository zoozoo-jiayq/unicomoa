/**
 * 
 */
package cn.com.qytx.oa.record.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.oa.record.service.IRecordWork;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能:
 * 版本: 1.0
 * 开发人员: 王刚刚
 * 创建日期: 2016年12月19日
 * 修改日期: 2016年12月19日
 * 修改列表: 
 */
public class RecordWorkAction extends BaseActionSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 3137493826278587974L;
	
	@Autowired
	private IRecordWork recordWorkService;
	@Autowired
	private IUser userService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	/**
	 * 工作Id
	 */
	private Integer recordWorkId;
	
	private RecordWork recordWork;
	/**
	 * 当前手机端登录的用户id
	 */
	private Integer loginUserNow;
	
	//type:1跳转到详情页面 2 跳转到编辑页面
	private Integer type;
	
	
	private Integer userId;
	/**
	 * 
	 * 功能：分页查询工作列表
	 */
	public void findlistByPage(){
		UserInfo user=null;
		if(loginUserNow!=null){
			user=userService.findOne(loginUserNow);
		}else{
			user=getLoginUser();
		}
		try {
			if(user!=null){
				recordWork.setCompanyId(user.getCompanyId());
			    Order order = new Order(Direction.DESC, "createTime");
	            Sort s = new Sort(order);
	            Pageable pageable = getPageable(s);
			    Page<RecordWork> pageInfo = recordWorkService.findList(pageable, recordWork);
			    List<RecordWork> list=pageInfo.getContent();
			    List<Map<String,Object>> recordWorkList=analyzeResult(list, pageable);
		        ajaxPage(pageInfo, recordWorkList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 封装数据
	 * 功能：
	 * @param list
	 * @param pageable
	 * @return
	 */
	public List<Map<String,Object>> analyzeResult(List<RecordWork> list,Pageable pageable){
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>(); 
		if(list!=null && list.size()>0){
			int i = 1;
			if(pageable!=null){
    			i = pageable.getPageNumber() * pageable.getPageSize() + 1;
    		}
			for(RecordWork rw:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("no",i);
				map.put("id",rw.getId());
				map.put("name",rw.getUserInfo().getUserName());
				map.put("workUnit",rw.getWorkUnit());//工作单位
				map.put("industryCategory",StringUtils.isNotBlank(rw.getIndustryCategory())?rw.getIndustryCategory():"--");//行业类别
				map.put("position", StringUtils.isNotBlank(rw.getPosition())?rw.getPosition():"--");//职务
				map.put("reterence", StringUtils.isNotBlank(rw.getReterence())?rw.getReterence():"--");//证明人
				mapList.add(map);
				i++;
			}
		}
		return mapList;
	}
	
	/**
	 * 
	 * 功能：修改或新增繁殖信息
	 */
	public void saveOrUpdateWork(){
		UserInfo user=getLoginUser();
		try {
			if(user!=null){
				if(recordWork.getId()!=null){//修改
					RecordWork rw=recordWorkService.findOne(recordWork.getId());
					rw.setPosition(recordWork.getPosition());
					rw.setDepartment(recordWork.getDepartment());
					rw.setReterence(recordWork.getReterence());
					rw.setStartDate(recordWork.getStartDate());
					rw.setEndDate(recordWork.getEndDate());
					rw.setIndustryCategory(recordWork.getIndustryCategory());
					rw.setWorkUnit(recordWork.getWorkUnit());
					rw.setJobContent(recordWork.getJobContent());
					rw.setLeavingReasons(recordWork.getLeavingReasons());
					rw.setRemark(recordWork.getRemark());
					rw.setAttment(recordWork.getAttment());
					rw.setAchievement(recordWork.getAchievement());
					rw.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					rw.setUpdateUserId(user.getUserId());
					rw.setCompanyId(user.getCompanyId());
					rw.setIsDelete(0);
					recordWorkService.saveOrUpdate(rw);
					ajax(2);//成功
				}else{
					recordWork.setCreateUserId(user.getCreateUserId());
					recordWork.setIsDelete(0);
					recordWork.setCompanyId(user.getCompanyId());
					recordWork.setCreateTime(new Timestamp(System.currentTimeMillis()));
					recordWorkService.saveOrUpdate(recordWork);
					ajax(1);//成功
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			ajax(0);//失败
		}
}
	
	/**
	 * 
	 * 功能：通过id 查找工作详情
	 * @return
	 */
	public String findDetail(){
		UserInfo user =null;
		if(loginUserNow!=null){
			user=userService.findOne(loginUserNow);
		}else{
			user=getLoginUser();
		}
		if(user!=null){
			recordWork=recordWorkService.findOne(recordWorkId);
			String attment = recordWork.getAttment();
			if(StringUtils.isNotBlank(attment)){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
				if(attachList!=null&&!attachList.isEmpty()){
					for(Attachment att:attachList){
						att.setAttacthSuffix(getExtension(att.getAttachName()));
					}
				}
				recordWork.setAttachmentList(attachList);		
			}
		}
		if(type==2){
			return "wapDetail";
		}
		if(type==1){
			return "toDetail";
		}else{
			return "toUpdate";
		}
		
	}
	
	  /**
     * 得到文件的扩展名,得不到返回空
     */
    private static String getExtension(String fileName)
    {
        if ((fileName != null) && (fileName.length() > 0))
        {
            int i = fileName.lastIndexOf('.');

            if ((i > -1) && (i < fileName.length() - 1))
            {
                return fileName.substring(i + 1);
            }
        }
        return "";
    }
	
	/**
	 * 
	 * 功能：跳转到添加页面
	 * @return
	 */
	public String toAddView(){
		if(userId!=null){
			UserInfo user=userService.findOne(userId);
		    recordWork =new RecordWork();
			recordWork.setUserInfo(user);
		}
		return "success";
	}
	
	
	/**
	 * @return the recordWorkService
	 */
	public IRecordWork getRecordWorkService() {
		return recordWorkService;
	}
	/**
	 * @param recordWorkService the recordWorkService to set
	 */
	public void setRecordWorkService(IRecordWork recordWorkService) {
		this.recordWorkService = recordWorkService;
	}
	/**
	 * @return the recordWorkId
	 */
	public Integer getRecordWorkId() {
		return recordWorkId;
	}
	/**
	 * @param recordWorkId the recordWorkId to set
	 */
	public void setRecordWorkId(Integer recordWorkId) {
		this.recordWorkId = recordWorkId;
	}
	/**
	 * @return the recordWork
	 */
	public RecordWork getRecordWork() {
		return recordWork;
	}
	/**
	 * @param recordWork the recordWork to set
	 */
	public void setRecordWork(RecordWork recordWork) {
		this.recordWork = recordWork;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLoginUserNow() {
		return loginUserNow;
	}
	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}
    
	
}
