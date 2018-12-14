package cn.com.qytx.oa.record.action;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.oa.record.domain.RecordLeave;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.oa.record.service.IRecordLeave;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IUser;
/**
 * Created by lilipo on 2016/12/22.
 */
public class RecordLeaveAction extends BaseActionSupport {

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1354564517162649551L;
	@Autowired
    private IRecordLeave leaveService;
    @Resource(name="userService")
    private IUser userService;
    @Resource(name="attachmentService")
    private IAttachment attachmentService;
    @Autowired
    private IDict dictService;

    @Autowired
    private IGroup groupService;
    private RecordLeave recordLeave;
    //员工ID
    private Integer userId;

    private Integer leaveId;
    //type:1跳转到详情页面 2 跳转到编辑页面
    private Integer type;
    
    /**
	 * 当前登录用户的id
	 */
	private  Integer loginUserNow;
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
                recordLeave.setCompanyId(user.getCompanyId());
                Order order = new Order(Direction.DESC, "createTime");
                Sort s = new Sort(order);
                Pageable pageable = getPageable(s);
                Page<RecordLeave> pageInfo = leaveService.findList(pageable, recordLeave);
                List<RecordLeave> list=pageInfo.getContent();
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
    public List<Map<String,Object>> analyzeResult(List<RecordLeave> list,Pageable pageable){
        List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
        List<Dict> dicts=this.dictService.findListByInfoType("leave_type");
        if(list!=null && list.size()>0){
            int i = 1;
            if(pageable!=null){
                i = pageable.getPageNumber() * pageable.getPageSize() + 1;
            }
            for(RecordLeave recordLeave:list){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("no",i);
                map.put("id",recordLeave.getId());
                map.put("name",recordLeave.getUserInfo().getUserName());
                for (Dict dict : dicts) {
                    if (dict.getValue()==recordLeave.getLeaveType()) {
                        map.put("leaveType",StringUtils.isNoneBlank(dict.getName())?dict.getName():"--");
                    }
                }
                map.put("intendedToLeaveDate",recordLeave.getIntendedToLeaveDate()!=null? DateUtils.date2ShortStr(recordLeave.getIntendedToLeaveDate()):"--");
                map.put("wageCutoffDate",recordLeave.getWageCutoffDate()!=null?DateUtils.date2ShortStr(recordLeave.getWageCutoffDate()):"--");
                mapList.add(map);
                i++;
            }
        }
        return mapList;
    }
    /**
     * 检查是否已有离职信息
     * @return  1 已有  0 未有
     */
    public void checkLeave(){
        RecordLeave temp = leaveService.findOne(" user_id=? and isDelete=?", userId, CbbConst.NOT_DELETE);
        if(temp!=null){
            ajax("1");
        }else {
            ajax("0");
        }
    }


    /**
     * 去添加
     */
    public String toAddOrUpdate(){
        if(userId!=null){
                UserInfo userInfo = userService.findOne(userId);
                recordLeave = new RecordLeave();
                if(userInfo.getGroupId()!=null){
                	GroupInfo group= groupService.findOne(userInfo.getGroupId());
                	recordLeave.setLeaveDepartmentName(group.getGroupName());
                }else{
                	recordLeave.setLeaveDepartmentName("--");
                }
                recordLeave.setUserInfo(userInfo);
        }

        return SUCCESS;
    }
    /**
     *
     * 功能：修改或新增离职信息
     */
    public void saveOrUpdateLeave(){
        UserInfo user=getLoginUser();
        try {
            if(user!=null){
                UserInfo employee  = userService.findOne(recordLeave.getUserInfo().getUserId());
                if(employee!=null){
                    if (recordLeave.getId() != null) {//修改
                        employee.setIsDelete(1);
                        RecordLeave temp = leaveService.findOne(recordLeave.getId());
                        temp.setPosition(recordLeave.getPosition());
                        temp.setLeaveType(recordLeave.getLeaveType());
                        temp.setApplyDate(recordLeave.getApplyDate());
                        temp.setIntendedToLeaveDate(recordLeave.getIntendedToLeaveDate());
                        temp.setActualLeaveDate(recordLeave.getActualLeaveDate());
                        temp.setWageCutoffDate(recordLeave.getWageCutoffDate());
                        temp.setLeaveTheMonthWage(recordLeave.getLeaveTheMonthWage());
                        temp.setWhereabouts(recordLeave.getWhereabouts());
                        temp.setResignationProcedure(recordLeave.getResignationProcedure());
                        temp.setRemark(recordLeave.getRemark());
                        temp.setLeaveReason(recordLeave.getLeaveReason());
                        temp.setLeaveDepartmentName(recordLeave.getLeaveDepartmentName());
                        temp.setAttment(recordLeave.getAttment());
                        temp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        temp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        temp.setUpdateUserId(user.getUserId());
                        temp.setCompanyId(user.getCompanyId());
                        temp.setIsDelete(0);
                        leaveService.saveOrUpdateLeave(temp,employee);
                        ajax(2);//成功
                    }else {
                        employee.setIsDelete(1);
                        recordLeave.setCreateUserId(user.getUserId());
                        recordLeave.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        recordLeave.setUpdateUserId(user.getUserId());
                        recordLeave.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        recordLeave.setIsDelete(0);
                        recordLeave.setCompanyId(user.getCompanyId());
                        leaveService.saveOrUpdateLeave(recordLeave,employee);
                        ajax(1);//成功
                    }
                }

            }else {

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     *
     * 功能：通过id 查找工作详情
     * @return
     */
    public String findDetail(){
    	UserInfo user=null;
    	if(loginUserNow!=null){
    		user=userService.findOne(loginUserNow);
    	}else{
    		user=getLoginUser();
    	}
        
        if(user!=null){
            recordLeave=leaveService.findOne(leaveId);
            List<Dict> dicts=this.dictService.findListByInfoType("leave_type");
            for (Dict dict : dicts) {
                if (dict.getValue()==recordLeave.getLeaveType()) {
                    recordLeave.setLeaveTypeString(dict.getName());
                }
            }
            String attment = recordLeave.getAttment();
            if(StringUtils.isNotBlank(attment)){
                List<Attachment> attachList = attachmentService.getAttachmentsByIds(attment);
                if(attachList!=null&&!attachList.isEmpty()){
                    for(Attachment att:attachList){
                        att.setAttacthSuffix(getExtension(att.getAttachName()));
                    }
                }
                recordLeave.setAttachmentList(attachList);
            }
        }
        if(type==2){
        	return "wap";
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

    public RecordLeave getRecordLeave() {
        return recordLeave;
    }

    public void setRecordLeave(RecordLeave recordLeave) {
        this.recordLeave = recordLeave;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Integer getLoginUserNow() {
		return loginUserNow;
	}

	public void setLoginUserNow(Integer loginUserNow) {
		this.loginUserNow = loginUserNow;
	}
    
    
}
