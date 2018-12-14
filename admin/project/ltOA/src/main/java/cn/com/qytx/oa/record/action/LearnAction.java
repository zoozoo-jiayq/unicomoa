package cn.com.qytx.oa.record.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.record.domain.RecordLearn;
import cn.com.qytx.oa.record.service.LearnService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
/***
 * 功能:个人学习信息处理
 * 版本:1.0
 * 开发人员: 李涛
 * 创建日期: 2016-12-19
 */
public class LearnAction extends BaseActionSupport{
    @Autowired
    private LearnService learnService;
    @Autowired
    private IDict dictService;
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Resource(name="userService")
	private IUser userService;
    /**
     * 用户ID
     */
    private Integer userId;
    private Integer learnId;
    private String eduLevel;
    private String eduQualifications;
    
    /**
     * 现在当前登录人的ID
     */
    
    private Integer loginUserNow;
    /**
     * 学习信息对象
     */
    private RecordLearn recordLearn;
    /**
     * 跳转入添加或修改学习信息页
     *
     * @return success 跳转入该条学习信息添加或修改页面jsp
     */
    public String tosave() {
		if(learnId!=null){
			recordLearn=this.learnService.findById(learnId);
			String attment = recordLearn.getAttment();
			if(StringUtils.isNotBlank(attment)){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
				recordLearn.setAttachmentList(attachList);		
			}
		}else{
			UserInfo userInfo = userService.findOne(userId);
			recordLearn = new RecordLearn();
			recordLearn.setUserInfo(userInfo);
		}
		return SUCCESS;
    }
    /**
     * 添加或修改学习信息
     *
     * @return 
     */
    public String save() {
		try{
			UserInfo userInfo = this.getLoginUser();
			if(userInfo!=null){
				if(learnId!=null){//修改
					RecordLearn oldlearn = this.learnService.findById(learnId);
					oldlearn.setAttment(recordLearn.getAttment());
					oldlearn.setMajor(recordLearn.getMajor());
					oldlearn.setEducation(recordLearn.getEducation());
					oldlearn.setDegree(recordLearn.getDegree());
					oldlearn.setStartDate(recordLearn.getStartDate());
					oldlearn.setEndDate(recordLearn.getEndDate());
					oldlearn.setClassCadre(recordLearn.getClassCadre());
					oldlearn.setRemark(recordLearn.getRemark());
					oldlearn.setReterence(recordLearn.getReterence());
					oldlearn.setSchool(recordLearn.getSchool());
					oldlearn.setSchoolAddress(recordLearn.getSchoolAddress());
					oldlearn.setAward(recordLearn.getAward());
					oldlearn.setCertificates(recordLearn.getCertificates());
					oldlearn.setUpdateUserId(userInfo.getUserId());
					oldlearn.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					this.learnService.save(oldlearn);
					ajax("2");
				}else{
					recordLearn.setCompanyId(userInfo.getCompanyId());
					recordLearn.setCreateTime(new Timestamp(System.currentTimeMillis()));
					recordLearn.setCreateUserId(userInfo.getUserId());
					recordLearn.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					recordLearn.setUpdateUserId(userInfo.getUserId());
					recordLearn.setIsDelete(0);
					this.learnService.save(recordLearn);
					ajax("1");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 根据id查学习信息
     *
     * @return success 跳转入该条学习信息的详情页面jsp
     */
    public String findById() {
    	recordLearn=this.learnService.findById(learnId);
    	List<Dict> dicts=this.dictService.findListByInfoType("edu_qualifications");
		for (Dict dict : dicts) {
			if (dict.getValue()==recordLearn.getEducation()) {
				eduLevel=dict.getName();
			}
		}
    	List<Dict> dicts2=this.dictService.findListByInfoType("edu_level");
		for (Dict dict : dicts2) {
			if (dict.getValue()==recordLearn.getDegree()) {
				eduQualifications=dict.getName();
			}
		}
		String attment = recordLearn.getAttment();
		if(StringUtils.isNotBlank(attment)){
			List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
			recordLearn.setAttachmentList(attachList);		
		}
    	return SUCCESS;
    }
    /**
     * 分页list列表数据
     *
     * @return 数据到页面
     */
    public String list() {
    	UserInfo userInfo =null;
    	if(loginUserNow!=null){
    		userInfo=userService.findOne(loginUserNow);
    	}else{
    		userInfo = this.getLoginUser();
    	}
    	
    	if(userInfo!=null){
		    Order order = new Order(Direction.DESC, "createTime");
            Sort s = new Sort(order);
            Pageable pageable = getPageable(s);
    	   	Page<RecordLearn> page=this.learnService.find(pageable, userId);
        	List<RecordLearn> list=page.getContent();
        	List arraylist=new ArrayList();
        	if(list!=null&&!list.isEmpty()){
	        	int no = this.getPageable().getPageNumber()*this.getPageable().getPageSize()+1;
	        	for (RecordLearn recordLearn : list) {
	        		Map map=new HashMap();
	        		map.put("no", no++);
	        		map.put("learnId",recordLearn.getId());
	        		map.put("userName",StringUtils.isNoneBlank(recordLearn.getUserInfo().getUserName())?recordLearn.getUserInfo().getUserName():"--");
	        		map.put("major",StringUtils.isNoneBlank(recordLearn.getMajor())?recordLearn.getMajor():"--");
	        		List<Dict> dicts=this.dictService.findListByInfoType("edu_qualifications");
	        		for (Dict dict : dicts) {
	    				if (dict.getValue()==recordLearn.getEducation()) {
	    		    		map.put("education",StringUtils.isNoneBlank(dict.getName())?dict.getName():"--");
	    				}
	    			}
	        		map.put("school",StringUtils.isNoneBlank(recordLearn.getSchool())?recordLearn.getSchool():"--");
	        		map.put("reterence",StringUtils.isNoneBlank(recordLearn.getReterence())?recordLearn.getReterence():"--");
	            	arraylist.add(map);
	    		}
        	}
        	this.ajaxPage(page,arraylist);
    	}
    	return null;
    }
    
	public LearnService getLearnService() {
		return learnService;
	}
	public void setLearnService(LearnService learnService) {
		this.learnService = learnService;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public RecordLearn getRecordLearn() {
		return recordLearn;
	}
	public void setRecordLearn(RecordLearn recordLearn) {
		this.recordLearn = recordLearn;
	}
	public IDict getDictService() {
		return dictService;
	}
	public void setDictService(IDict dictService) {
		this.dictService = dictService;
	}
	public Integer getLearnId() {
		return learnId;
	}
	public void setLearnId(Integer learnId) {
		this.learnId = learnId;
	}
	public String getEduLevel() {
		return eduLevel;
	}
	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}
	public String getEduQualifications() {
		return eduQualifications;
	}
	public void setEduQualifications(String eduQualifications) {
		this.eduQualifications = eduQualifications;
	}
	public Integer getLoginUserNow() {
		return loginUserNow;
	}
	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}
    
}
