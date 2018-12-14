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
import cn.com.qytx.oa.record.domain.RecordTitle;
import cn.com.qytx.oa.record.service.IRecordTitle;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能：职称action
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
public class RecordTitleAction extends BaseActionSupport{
	private static final long serialVersionUID = 8180437843337223841L;
	
	
	@Resource(name="recordTitleImpl")
	private IRecordTitle recordTitleImpl;
	
	@Resource(name="userService")
	private IUser userService;
	
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	
	private Integer userId;
	
	private RecordTitle recordTitle;
	
	private Integer titleId;
	
	private String operType;
	/**
	 * 当前手机端登录的用户id
	 */
	private Integer loginUserNow;
	/**
	 * 功能：职称列表
	 * @return
	 */
	public String titleList(){
		try{
			UserInfo userInfo=null;
			if(loginUserNow!=null){
				userInfo=userService.findOne(loginUserNow);
			}else {
				userInfo = this.getLoginUser();
			}
			Page<RecordTitle> pageInfo = null;
			List<Map<String,Object>> mapList = null;
			if(userInfo!=null){
				Sort sort = new Sort(Direction.DESC,"createTime");
				pageInfo = this.recordTitleImpl.findRecordTitlePage(getPageable(sort), userId);
				List<RecordTitle> rtList = pageInfo.getContent();
				if(rtList!=null&&!rtList.isEmpty()){
					mapList = new ArrayList<Map<String,Object>>();
					int no = this.getIDisplayStart()+1;
					for(RecordTitle rt:rtList){
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("no", no++);
						map.put("titleId", rt.getId());
						map.put("userName", rt.getUserInfo()!=null?rt.getUserInfo().getUserName():"--");//评定人
						map.put("approveUser",rt.getApproveUser()!=null?rt.getApproveUser().getUserName():"--");//审批人
						map.put("accessTypeStr",getAccessTypeStr(rt.getAccessType()));
						map.put("title",StringUtils.isNotBlank(rt.getTitle())?rt.getTitle():"--");
						map.put("giveDate", rt.getGiveDate()!=null?DateUtils.date2ShortStr(rt.getGiveDate()):"--");
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
	
	
	public String toAddTitle(){
		if(titleId!=null){
			recordTitle = recordTitleImpl.findOne(titleId);
			recordTitle.setAccessTypeStr(getAccessTypeStr(recordTitle.getAccessType()));
			String attment = recordTitle.getAttment();
			if(StringUtils.isNotBlank(attment)){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
				if(attachList!=null&&!attachList.isEmpty()){
					for(Attachment att:attachList){
						att.setAttacthSuffix(getExtension(att.getAttachName()));
					}
				}
				recordTitle.setAttachmentList(attachList);		
			}
		}else{
			UserInfo userInfo = userService.findOne(userId);
			recordTitle = new RecordTitle();
			recordTitle.setUserInfo(userInfo);
		}
		if(operType!=null&&"detail".equals(operType)){
			return "detail";
		}
		if(operType!=null&&"wap".equals(operType)){
			return "wap";
		}
		return SUCCESS;
	}
	
	public String saveOrUpdate(){
		try{
			UserInfo userInfo = this.getLoginUser();
			if(userInfo!=null){
				if(titleId!=null){//修改
					RecordTitle oldRt = this.recordTitleImpl.findOne(titleId);
					if(recordTitle.getApproveUser()!=null&&recordTitle.getApproveUser().getUserId()!=null){
						oldRt.setApproveUser(recordTitle.getApproveUser());
					}else{
						oldRt.setApproveUser(null);
					}
					oldRt.setAccessType(recordTitle.getAccessType());
					oldRt.setApplyDate(recordTitle.getApplyDate());
					oldRt.setAttment(recordTitle.getAttment());
					oldRt.setGiveDate(recordTitle.getGiveDate());
					oldRt.setHiringBeginDate(recordTitle.getHiringBeginDate());
					oldRt.setHiringEndDate(recordTitle.getHiringEndDate());
					oldRt.setHiringPosition(recordTitle.getHiringPosition());
					oldRt.setHiringUnits(recordTitle.getHiringUnits());
					oldRt.setNextApplyDate(recordTitle.getNextApplyDate());
					oldRt.setNextTitle(recordTitle.getNextTitle());
					oldRt.setRemark(recordTitle.getRemark());
					oldRt.setTitle(recordTitle.getTitle());
					oldRt.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					oldRt.setUpdateUserId(userInfo.getUserId());
					this.recordTitleImpl.saveOrUpdate(oldRt);
					ajax("2");
				}else{
					if(recordTitle.getApproveUser()==null||recordTitle.getApproveUser().getUserId()==null){
						recordTitle.setApproveUser(null);
					}
					recordTitle.setCompanyId(userInfo.getCompanyId());
					recordTitle.setCreateTime(new Timestamp(System.currentTimeMillis()));
					recordTitle.setCreateUserId(userInfo.getUserId());
					recordTitle.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					recordTitle.setIsDelete(0);
					this.recordTitleImpl.saveOrUpdate(recordTitle);
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
	
	private  static String getAccessTypeStr(Integer accessType){
		String accessTypeStr = "业绩考核";
		if(accessType==1){
			accessTypeStr = "业绩考核";
		}else{
			accessTypeStr = "考试";
		}
		return accessTypeStr;
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


	


	public RecordTitle getRecordTitle() {
		return recordTitle;
	}


	public void setRecordTitle(RecordTitle recordTitle) {
		this.recordTitle = recordTitle;
	}


	public Integer getTitleId() {
		return titleId;
	}


	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
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
