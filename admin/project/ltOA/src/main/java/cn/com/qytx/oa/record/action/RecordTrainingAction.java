package cn.com.qytx.oa.record.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.utils.DateUtils;
import cn.com.qytx.oa.record.domain.RecordTraining;
import cn.com.qytx.oa.record.service.IRecordTraining;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能：培训action
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
public class RecordTrainingAction extends BaseActionSupport{
	private static final long serialVersionUID = 8180437843337223841L;
	
	
	@Resource(name="recordTrainingImpl")
	private IRecordTraining recordTrainingImpl;
	
	@Resource(name="userService")
	private IUser userService;
	
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	
	private Integer userId;
	
	private RecordTraining recordTraining;
	
	private Integer trainingId;
	
	private String operType;
	
	private Integer loginUserNow;
	
	/**
	 * 功能：职称列表
	 * @return
	 */
	public String trainingList(){
		try{
			UserInfo userInfo=null;
			if(loginUserNow!=null){
				userInfo=userService.findOne(loginUserNow);
			}else{
				userInfo = this.getLoginUser();
			}
			
			Page<RecordTraining> pageInfo = null;
			List<Map<String,Object>> mapList = null;
			if(userInfo!=null){
				Sort sort = new Sort(Direction.DESC,"createTime");
				pageInfo = this.recordTrainingImpl.findRecordTrainingPage(getPageable(sort), userId);
				List<RecordTraining> rtList = pageInfo.getContent();
				if(rtList!=null&&!rtList.isEmpty()){
					mapList = new ArrayList<Map<String,Object>>();
					int no = this.getIDisplayStart()+1;
					for(RecordTraining rt:rtList){
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("no", no++);
						map.put("trainingId", rt.getId());
						map.put("trainingName", StringUtils.isNotBlank(rt.getTrainPlanName())?rt.getTrainPlanName():"--");
						map.put("trainMechanism", StringUtils.isNotBlank(rt.getTrainMechanism())?rt.getTrainMechanism():"--");
						map.put("trainMoney", rt.getTrainMoney()!=null?rt.getTrainMoney():"--");
						map.put("trainingDate", rt.getTrainDate()!=null?DateUtils.date2ShortStr(rt.getTrainDate()):"--");
						map.put("trainingEndDate",rt.getTrainEndDate()!=null?DateUtils.date2ShortStr(rt.getTrainEndDate()):"--");
						mapList.add(map);
					}
				}
				ajaxPage(pageInfo, mapList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String toAddTraining(){
		if(trainingId!=null){
			recordTraining = recordTrainingImpl.findOne(trainingId);
			String attment = recordTraining.getAttment();
			if(StringUtils.isNotBlank(attment)){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
				if(attachList!=null&&!attachList.isEmpty()){
					for(Attachment att:attachList){
						att.setAttacthSuffix(getExtension(att.getAttachName()));
					}
				}
				recordTraining.setAttachmentList(attachList);		
			}
		}else{
			UserInfo userInfo = userService.findOne(userId);
			recordTraining = new RecordTraining();
			recordTraining.setUserInfo(userInfo);
		}
		if(operType!=null&&"wap".equals(operType)){
			return "wap";
		}
		if(operType!=null&&"detail".equals(operType)){
			return "detail";
		}
		return SUCCESS;
	}
	
	public String saveOrUpdate(){
		try{
			UserInfo userInfo = this.getLoginUser();
			if(userInfo!=null){
				if(trainingId!=null){//修改
					RecordTraining oldRt = this.recordTrainingImpl.findOne(trainingId);
					oldRt.setAttment(recordTraining.getAttment());
					oldRt.setTrainDate(recordTraining.getTrainDate());
					oldRt.setTrainEndDate(recordTraining.getTrainEndDate());
					oldRt.setTrainMechanism(recordTraining.getTrainMechanism());
					oldRt.setTrainMoney(recordTraining.getTrainMoney());
					oldRt.setTrainPlanName(recordTraining.getTrainPlanName());
					oldRt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					oldRt.setUpdateUserId(userInfo.getUserId());
					this.recordTrainingImpl.saveOrUpdate(oldRt);
					ajax("2");
				}else{
					recordTraining.setCompanyId(userInfo.getCompanyId());
					recordTraining.setCreateTime(new Timestamp(System.currentTimeMillis()));
					recordTraining.setCreateUserId(userInfo.getUserId());
					recordTraining.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					recordTraining.setIsDelete(0);
					this.recordTrainingImpl.saveOrUpdate(recordTraining);
					ajax("1");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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


	


	public RecordTraining getRecordTraining() {
		return recordTraining;
	}


	public void setRecordTraining(RecordTraining recordTraining) {
		this.recordTraining = recordTraining;
	}


	public Integer getTrainingId() {
		return trainingId;
	}


	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}


	public String getOperType() {
		return operType;
	}


	public void setOperType(String operType) {
		this.operType = operType;
	}


	public Integer getLoginUserNow() {
		return loginUserNow;
	}


	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}

	
	
}
