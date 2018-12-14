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

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.record.domain.RecordRelation;
import cn.com.qytx.oa.record.service.IRecordRelation;
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
 * 创建日期: 2016年12月20日
 * 修改日期: 2016年12月20日
 * 修改列表: 
 */
public class RecordRelationAction extends BaseActionSupport {

	/**
	 * 描述含义
	 */
	private static final long serialVersionUID = 5698186613890266363L;
	
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	@Autowired 
	private IRecordRelation recordRelationService; 
	@Autowired
	private IDict dictService;
	
	private Integer userId;
	
	/**
	 * 手机端当前登录用户id
	 */
	private Integer loginUserNow;
	
	@Autowired
	private IUser userService;
	private RecordRelation recordRelation; 
	
	//type:1跳转到详情页面 2 跳转到编辑页面 3//是添加
    private Integer type;
    
	/**
	 * 工作Id
	 */
	private Integer recordRelationId;
	
	
	
	/**
	 * 
	 * 功能：分页查询关系列表
	 */
	public void findlistRelation(){
		UserInfo user=null;
		if(loginUserNow!=null){
			user=userService.findOne(loginUserNow);
		}else{
			user=getLoginUser();
		}
		
		try {
			if(user!=null){
				recordRelation.setCompanyId(user.getCompanyId());
			    Order order = new Order(Direction.DESC, "createTime");
	            Sort s = new Sort(order);
	            Pageable pageable = getPageable(s);
			    Page<RecordRelation> pageInfo = recordRelationService.findList(pageable, recordRelation);
			    List<RecordRelation> list=pageInfo.getContent();
			    List<Map<String,Object>> recordRelationList=analyzeResult(list, pageable);
		        ajaxPage(pageInfo, recordRelationList);
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
	public List<Map<String,Object>> analyzeResult(List<RecordRelation> list,Pageable pageable){
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>(); 
		Map<Integer,String> mapRelation=findDictRelation();
		if(list!=null && list.size()>0){
			int i = 1;
			if(pageable!=null){
    			i = pageable.getPageNumber() * pageable.getPageSize() + 1;
    		}
			for(RecordRelation rl:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("no",i);
				map.put("id",rl.getId());
				map.put("name",rl.getUserInfo().getUserName());
				map.put("memberName",rl.getMemberName());//成员姓名
				map.put("relation",rl.getRelation()!=null?mapRelation.get(rl.getRelation()):"--");
				map.put("occupation", StringUtils.isNotBlank(rl.getOccupation())?rl.getOccupation():"--");
				map.put("personalPhone", StringUtils.isNotBlank(rl.getPersonalPhone())?rl.getPersonalPhone():"--");
				mapList.add(map);
				i++;
			}
		}
		return mapList;
	}
	/**
	 * 
	 * 功能：获得关系数据字典MAP集合
	 * @return
	 */
	public Map<Integer,String> findDictRelation(){
         List<Dict> listRelation=dictService.findAllList("family_relation", 1);
         Map<Integer,String> map = new HashMap<Integer, String>();
         if(listRelation!=null&&listRelation.size()>0){
        	 for(Dict d:listRelation){
        		 map.put(d.getValue(),d.getName());
        	 }
         }
         return map;
	}
	
	/**
	 * 
	 * 功能：获得政治面貌数据字典MAP集合
	 * @return
	 */
	public Map<Integer,String> findDictPoliticalStatus(){
         List<Dict> listPoliticalStatus=dictService.findAllList("political_status", 1);
         Map<Integer,String> map = new HashMap<Integer, String>();
         if(listPoliticalStatus!=null&&listPoliticalStatus.size()>0){
        	 for(Dict d:listPoliticalStatus){
        		 map.put(d.getValue(),d.getName());
        	 }
         }
         return map;
	}
	
	/**
	 * 
	 * 功能：修改或新增繁殖信息
	 */
	public void saveOrUpdateRelation(){
		UserInfo user=getLoginUser();
		try {
			if(user!=null){
				if(recordRelation.getId()!=null){//修改
					RecordRelation rl=recordRelationService.findOne(recordRelation.getId());
					rl.setMemberName(recordRelation.getMemberName());
					rl.setRelation(recordRelation.getRelation());
					rl.setBirthDate(recordRelation.getBirthDate());
					rl.setPoliticalStatus(recordRelation.getPoliticalStatus());
					rl.setOccupation(recordRelation.getOccupation());
					rl.setDuties(recordRelation.getDuties());
					rl.setPersonalPhone(recordRelation.getPersonalPhone());
					rl.setHomePhone(recordRelation.getHomePhone());
					rl.setWorkTelephone(recordRelation.getWorkTelephone());
					rl.setWorkUnit(recordRelation.getWorkUnit());
					rl.setUnitAddress(recordRelation.getUnitAddress());
					rl.setHomeAddress(recordRelation.getHomeAddress());
					rl.setRemark(recordRelation.getRemark());
					rl.setAttment(recordRelation.getAttment());
					rl.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					rl.setUpdateUserId(user.getUserId());
					rl.setCompanyId(user.getCompanyId());
					rl.setIsDelete(0);
					recordRelationService.saveOrUpdate(rl);
					ajax(2);//成功
				}else{
					recordRelation.setCreateUserId(user.getCreateUserId());
					recordRelation.setIsDelete(0);
					recordRelation.setCompanyId(user.getCompanyId());
					recordRelation.setCreateTime(new Timestamp(System.currentTimeMillis()));
					recordRelationService.saveOrUpdate(recordRelation);
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
	public String findRelationDetail(){
		UserInfo user =null;
		if(loginUserNow!=null){
			user=userService.findOne(loginUserNow);
		}else{
			user=getLoginUser();
		}
		
		if(user!=null){
			if(type==3){
				if(userId!=null){
					UserInfo userOne=userService.findOne(userId);
					recordRelation =new RecordRelation();
					recordRelation.setUserInfo(userOne);
				}
			}else{
				recordRelation=recordRelationService.findOne(recordRelationId);
				Map<Integer,String> mapRelation=findDictRelation();
				Map<Integer,String> mapPoliticalStatus=findDictPoliticalStatus();
				if(recordRelation.getRelation()!=null){
					recordRelation.setRelationStr(mapRelation.get(recordRelation.getRelation()));
				}else{
					recordRelation.setRelationStr("--");
				}
				if(recordRelation.getPoliticalStatus()!=null){
					recordRelation.setPoliticalStatusStr(mapPoliticalStatus.get(recordRelation.getPoliticalStatus()));
				}else{
					recordRelation.setPoliticalStatusStr("--");
				}
				String attment = recordRelation.getAttment();
				if(StringUtils.isNotBlank(attment)){
					List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
					if(attachList!=null&&!attachList.isEmpty()){
						for(Attachment att:attachList){
							att.setAttacthSuffix(getExtension(att.getAttachName()));
						}
					}
					recordRelation.setAttachmentList(attachList);		
				}
			}
		}
		if (type==2) {
			return "toWap";
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
	/**
	 * @return the recordRelation
	 */
	public RecordRelation getRecordRelation() {
		return recordRelation;
	}
	/**
	 * @param recordRelation the recordRelation to set
	 */
	public void setRecordRelation(RecordRelation recordRelation) {
		this.recordRelation = recordRelation;
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
	 * @return the recordRelationId
	 */
	public Integer getRecordRelationId() {
		return recordRelationId;
	}
	/**
	 * @param recordRelationId the recordRelationId to set
	 */
	public void setRecordRelationId(Integer recordRelationId) {
		this.recordRelationId = recordRelationId;
	}
	public Integer getLoginUserNow() {
		return loginUserNow;
	}
	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}
    
    
	
}
