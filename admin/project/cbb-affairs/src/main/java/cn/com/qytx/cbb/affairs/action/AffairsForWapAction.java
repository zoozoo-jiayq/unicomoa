package cn.com.qytx.cbb.affairs.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.service.IAffairs;
import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * 功能:事务提醒功能action for wap
 * 版本: 1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class AffairsForWapAction extends BaseActionSupport {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 2314184560157105392L;

    private Integer affairsId;

    private Affairs affairs;
    
    private String userId; //手机端登录人Id
	private Integer pageSize; //每页显示多少条数据
	private Integer pageNum; //当前第几页

    /**
     * 事务提醒service
     */
    @Autowired
    IAffairs affairsImpl;

    public String list() {
        PrintWriter out = null;
        try {
            out = this.getResponse().getWriter();
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            // 检查邮件箱id
            Page<Affairs> page = this.affairsImpl.findPageByUserId(this.getPageable(), adminUser.getUserId(), MessageConst.SENDED);

            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            if (page.getContent() != null) {
                for (Affairs affairs : page.getContent()) {
                    Map<String, Object> toInfoMap = new HashMap<String, Object>();
                    AffairsBody affairsBody = affairs.getAffairsBody();
                    UserInfo fromUser = affairsBody.getFromUserInfo();
                    toInfoMap.put("id", affairs.getId());
                    toInfoMap.put("fromUserName", fromUser.getUserName());
                    toInfoMap.put("contentInfo", StringUtils.defaultString(affairsBody.getContentInfo()));
                    toInfoMap.put("sendTime", DateTimeUtil.getTimeDifference(new Date(), affairs.getRemindTime()));
                    aaDataList.add(toInfoMap);
                }
            }
            this.ajaxPage(page, aaDataList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } 
        return null;
    }
    /**
     * 我的提醒
     * @Title: myAffairsByPage   
     * @return
     */
    public String myAffairsByPage() {
        try {
        	//设置列表排序
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"remindTime"));
			PageRequest pageable = new PageRequest(pageNum-1,pageSize,sort);
            Page<Affairs> page = this.affairsImpl.findPageByUserId(pageable, Integer.parseInt(userId));
            /**
             * 更新未读 为已读
             */
            this.affairsImpl.updateRedAffairsFlag(Integer.parseInt(userId), 2);
            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            if (page.getContent() != null) {
                for (Affairs affairs : page.getContent()) {
                    Map<String, Object> toInfoMap = new HashMap<String, Object>();
                    AffairsBody affairsBody = affairs.getAffairsBody();
                    toInfoMap.put("id", affairs.getId());
                    toInfoMap.put("moduleCode", affairsBody.getSmsType());
                    toInfoMap.put("title", StringUtils.defaultString(affairsBody.getContentInfo()));
                    toInfoMap.put("contentInfo", StringUtils.defaultString(affairsBody.getRemindUrl()));
                    toInfoMap.put("remindTime", new SimpleDateFormat("M月d日 HH:mm").format(affairs.getRemindTime()));
                    aaDataList.add(toInfoMap);
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("iTotalRecords", page.getTotalElements());
            jsonMap.put("iTotalDisplayRecords", page.getTotalElements());
            jsonMap.put("aaData", aaDataList);
            ajax("100||"+new Gson().toJson(jsonMap));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } 
        return null;
    }
    public String show() {
        this.affairsImpl.updateReadedAffairs(new Integer[]{this.affairsId}, MessageConst.READED);
        this.affairs = this.affairsImpl.findById(this.affairsId);
        return SUCCESS;
    }

//    private String shortContentInfo(String contentInfo) {
//
//        if (StringUtils.isEmpty(contentInfo)) {
//            return "";
//        }
//        if (contentInfo.length() > 15) {
//            contentInfo = contentInfo.substring(0, 15) + "...";
//        }
//        return contentInfo;
//    }

    /**
     * 得到未读提醒数量
     * @Title: getUnReadAffairs   
     */
    public void getUnReadAffairs(){
    	try{
    		int count=affairsImpl.getUnReadAffairs(Integer.parseInt(userId));
    		ajax(count);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    /**
     * 修改红点状态
     * @Title: updateRedAffairsFlag   
     */
    public void updateRedAffairsFlag(){
    	try{
    		affairsImpl.updateRedAffairsFlag(Integer.parseInt(userId), MessageConst.READED);
    		ajax("100||修改成功");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void setAffairsImpl(IAffairs affairsImpl) {
        this.affairsImpl = affairsImpl;
    }

    public Integer getAffairsId() {
        return affairsId;
    }

    public void setAffairsId(Integer affairsId) {
        this.affairsId = affairsId;
    }

    public Affairs getAffairs() {
        return affairs;
    }

    public void setAffairs(Affairs affairs) {
        this.affairs = affairs;
    }
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
    
}
