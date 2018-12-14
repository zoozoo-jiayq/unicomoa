package cn.com.qytx.oa.record.action;

import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.record.domain.RecordPenalties;
import cn.com.qytx.oa.record.service.IRecordPenalties;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.base.query.Sort.Order;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * 功能:个人奖惩信息处理Controller
 * 版本:1.0
 * 开发人员: 李自立
 * 创建日期: 2016-12-19
 */
public class PenaltiesAction extends BaseActionSupport {
    @Autowired
    private IRecordPenalties iRecordPenalties;
    @Resource(name="userService")
    private IUser userService;
    @Resource(name="attachmentService")
    private IAttachment attachmentService;
    private Integer userId;

    private Integer penaltiesId;
    /**
     * 当前登录人的id
     */
    private Integer loginUserNow;
    private RecordPenalties recordPenalties;
    //type:1跳转到详情页面 2 跳转到编辑页面
    private Integer type;
    /**
     * 查询某个员工的奖惩信息列表
     */
    public String findAllByUserId(){
    	UserInfo adminUser=null;
    	if(loginUserNow!=null){
    		adminUser=userService.findOne(loginUserNow);
    		if(adminUser==null){
    			return null;
    		}
    	}
        if(userId!=null){
	    	 Order order = new Order(Direction.DESC, "createTime");
	         Sort s = new Sort(order);
	         Pageable pageable = getPageable(s);
            Page<RecordPenalties> page = iRecordPenalties.findAllPenaltiesByUserId(pageable,userId);
            List list = page.getContent();
            ArrayList mapList = new ArrayList();
            java.text.DecimalFormat df=new java.text.DecimalFormat("#0.00");
            if(list != null) {
                int no = this.getPageable().getPageNumber() * this.getPageable().getPageSize() + 1;
                for(int i = 0; i < list.size(); ++i) {
                    RecordPenalties recordPenalties = (RecordPenalties)list.get(i);
                    HashMap map = new HashMap();
                    map.put("no", no++);
                    map.put("id", recordPenalties.getId());
                    map.put("name", StringUtils.isNotBlank(recordPenalties.getUserInfo().getUserName())?recordPenalties.getUserInfo().getUserName():"--");
                    map.put("project", StringUtils.isNotBlank(recordPenalties.getProject())?recordPenalties.getProject():"--");
                    map.put("date", recordPenalties.getPenaltiesDate()!=null? DateUtils.date2ShortStr(recordPenalties.getPenaltiesDate()):"--");
                    map.put("nature", recordPenalties.getNature()==0?"奖励":"惩罚");
                    map.put("money", recordPenalties.getPenalties_money()==null?"--":df.format(recordPenalties.getPenalties_money()));
                    mapList.add(map);
                }

                this.ajaxPage(page, mapList);
            }
        }

        return null;
    }
    /**
     *
     * 功能：通过id 查找奖惩详情
     * @return
     */
    public String findDetail(){
    	UserInfo user=null;
    	if(loginUserNow!=null){
    		user=userService.findOne(loginUserNow);
    		if(penaltiesId!=null&&user!=null){
                recordPenalties = iRecordPenalties.findOne(penaltiesId);
                if (recordPenalties.getAttment() != null && !recordPenalties.getAttment().equals("")) {
                    recordPenalties.setAttachmentList(attachmentService.getAttachmentsByIds(recordPenalties.getAttment()));
                }
            }
    		return "toDetailWap";
    	}else{
    		user=getLoginUser();
    	}
        
        if(penaltiesId!=null&&user!=null){
            recordPenalties = iRecordPenalties.findOne(penaltiesId);
            if (recordPenalties.getAttment() != null && !recordPenalties.getAttment().equals("")) {
                recordPenalties.setAttachmentList(attachmentService.getAttachmentsByIds(recordPenalties.getAttment()));
            }
        }
        if(type==1){
            return "toDetail";
        }else{
            return "toUpdate";
        }
    }

    /**
     * 添加
     */
    public String toAddOrUpdate(){
        if(penaltiesId!=null){

        }else{


        }
        if(userId!=null){
            UserInfo userInfo = userService.findOne(userId);
            recordPenalties = new RecordPenalties();
            recordPenalties.setUserInfo(userInfo);
        }
        return SUCCESS;
    }
    /**
     * 添加 或修改
     */
    public String saveOrUpdate(){
        try {
            UserInfo userInfo = this.getLoginUser();
            if(userInfo!=null){
                if(penaltiesId!=null){
                    //修改
                    RecordPenalties temp = iRecordPenalties.findById(penaltiesId);
                    temp.setProject(recordPenalties.getProject());
                    temp.setPenaltiesDate(recordPenalties.getPenaltiesDate());
                    temp.setPenalties_money(recordPenalties.getPenalties_money());
                    temp.setWagesMonth(recordPenalties.getWagesMonth());
                    temp.setRemark(recordPenalties.getRemark());
                    temp.setReminded(recordPenalties.getReminded());
                    temp.setNature(recordPenalties.getNature());
                    temp.setExplain(recordPenalties.getExplain());
                    temp.setAttment(recordPenalties.getAttment());
                    temp.setCompanyId(userInfo.getCompanyId());
                    temp.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    temp.setUpdateUserId(userInfo.getUserId());
                    iRecordPenalties.saveOrUpdate(temp);
                    ajax("2");
                }else {
                    //添加
                    recordPenalties.setCompanyId(userInfo.getCompanyId());
                    recordPenalties.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    recordPenalties.setCreateUserId(userInfo.getUserId());
                    recordPenalties.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    recordPenalties.setIsDelete(0);
                    iRecordPenalties.saveOrUpdate(recordPenalties);
                    ajax("1");
                }
            }
        }catch (Exception e){
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

    public Integer getPenaltiesId() {
        return penaltiesId;
    }

    public void setPenaltiesId(Integer penaltiesId) {
        this.penaltiesId = penaltiesId;
    }

    public RecordPenalties getRecordPenalties() {
        return recordPenalties;
    }

    public void setRecordPenalties(RecordPenalties recordPenalties) {
        this.recordPenalties = recordPenalties;
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