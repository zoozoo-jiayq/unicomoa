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
import cn.com.qytx.platform.utils.DateUtils;
import cn.com.qytx.oa.record.domain.RecordTransfer;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.oa.record.service.IRecordTransfer;
import cn.com.qytx.oa.record.service.IUserRecord;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;

/**
 * 功能：调动action
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
public class TransferAction extends BaseActionSupport{
	private static final long serialVersionUID = 8180437843337223841L;
	
	
	@Resource(name="recordTransferImpl")
	private IRecordTransfer recordTransferImpl;
	
	@Resource(name="userService")
	private IUser userService;
	
	@Resource(name="groupService")
	private IGroup groupService;
	
	@Resource(name="attachmentService")
	private IAttachment attachmentService;
	
	@Autowired
    private IUserRecord userRecordService;
	
	private Integer userId;
	
	private RecordTransfer transfer;
	
	private Integer transferId;
	
	private String operType;
	
	/**
	 * 当前登录用户的id
	 */
	private  Integer loginUserNow;
	
	/**
	 * 功能：调动列表
	 * @return
	 */
	public String transferList(){
	
		try{
			UserInfo userInfo=null;
			if(loginUserNow!=null){
				userInfo=userService.findOne(loginUserNow);
			}else{
				userInfo = this.getLoginUser();
			}
			
			Page<RecordTransfer> pageInfo = null;
			List<Map<String,Object>> mapList = null;
			if(userInfo!=null){
				Sort sort = new Sort(Direction.DESC,"createTime");
				pageInfo = this.recordTransferImpl.findRecordTransferPage(getPageable(sort), userId);
				List<RecordTransfer> rtList = pageInfo.getContent();
				if(rtList!=null&&!rtList.isEmpty()){
					mapList = new ArrayList<Map<String,Object>>();
					int no = this.getIDisplayStart()+1;
					for(RecordTransfer rt:rtList){
						Map<String,Object> map = new HashMap<String, Object>();
						map.put("no", no++);
						map.put("transferId", rt.getId());
						map.put("userName", rt.getUserInfo()!=null?rt.getUserInfo().getUserName():"--");//调动人
						map.put("transferType", getTransferType(rt.getType()));//调动类型
						map.put("transferDate", rt.getTransferDate()!=null?DateUtils.date2ShortStr(rt.getTransferDate()):"--");
						map.put("effectiveDate", rt.getEffectiveDate()!=null?DateUtils.date2ShortStr(rt.getEffectiveDate()):"--");
						map.put("createTime", rt.getCreateTime()!=null?DateUtils.date2ShortStr(rt.getCreateTime()):"--");
						map.put("transferReason", StringUtils.isNotBlank(rt.getTransferReason())?rt.getTransferReason():"--");
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
	
	
	public String toAddTransfer(){
		if(transferId!=null){
			transfer = recordTransferImpl.findOne(transferId);
			GroupInfo beforeGroup = groupService.findOne(transfer.getBeforeGroupId());
			GroupInfo postGroup = groupService.findOne(transfer.getPostGroupId());
			transfer.setBeforeGroupName(beforeGroup!=null?beforeGroup.getGroupName():null);
			transfer.setPostGroupName(postGroup!=null?postGroup.getGroupName():null);
			transfer.setTypeStr(getTransferType(transfer.getType()));
			String attment = transfer.getAttment();
			if(StringUtils.isNotBlank(attment)){
				List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
				if(attachList!=null&&!attachList.isEmpty()){
					for(Attachment att:attachList){
						att.setAttacthSuffix(getExtension(att.getAttachName()));
					}
				}
				transfer.setAttachmentList(attachList);		
			}
		}else{
			UserInfo userInfo = userService.findOne(userId);
			transfer = new RecordTransfer();
			transfer.setUserInfo(userInfo);
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
			UserRecord userRecord = this.userRecordService.findByUserId(transfer.getUserInfo().getUserId());
			if(userInfo!=null){
				if(transferId!=null){//修改
					RecordTransfer oldTt = this.recordTransferImpl.findOne(transferId);
					oldTt.setAttment(transfer.getAttment());
					oldTt.setBeforeGroupId(transfer.getBeforeGroupId());
					oldTt.setPostGroupId(transfer.getPostGroupId());
					oldTt.setBeforePosition(transfer.getBeforePosition());
					oldTt.setPostPosition(transfer.getPostPosition());
					userRecord.setJob(transfer.getPostPosition());
					oldTt.setEffectiveDate(transfer.getEffectiveDate());
					oldTt.setTransferDate(transfer.getTransferDate());
					oldTt.setRemark(transfer.getRemark());
					oldTt.setTransferProcedure(transfer.getTransferProcedure());
					oldTt.setTransferReason(transfer.getTransferReason());
					oldTt.setType(transfer.getType());
					this.recordTransferImpl.addOrUpdateTransfer(oldTt,userRecord);
					ajax("2");
				}else{
					transfer.setCompanyId(userInfo.getCompanyId());
					transfer.setCreateTime(new Timestamp(System.currentTimeMillis()));
					transfer.setCreateUserId(userInfo.getUserId());
					transfer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					transfer.setIsDelete(0);
					transfer.setIsLast(1);
					userRecord.setJob(transfer.getPostPosition());
					this.recordTransferImpl.addOrUpdateTransfer(transfer,userRecord);
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
	
	private  static String getTransferType(Integer type){
		String transferType = "晋升";
		if(type==1){
			transferType = "晋升";
		}else if(type==2){
			transferType = "平调";
		}else if(type==3){
			transferType = "降级";
		}else{
			transferType = "其他";
		}
		return transferType;
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


	public RecordTransfer getTransfer() {
		return transfer;
	}


	public void setTransfer(RecordTransfer transfer) {
		this.transfer = transfer;
	}


	public Integer getTransferId() {
		return transferId;
	}


	public void setTransferId(Integer transferId) {
		this.transferId = transferId;
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
