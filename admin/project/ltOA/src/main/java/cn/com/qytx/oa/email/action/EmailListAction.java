/**
 *
 */
package cn.com.qytx.oa.email.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.service.IEmailList;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.DateUtils;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功能:邮件处理的Controller，包含收件箱、已发送、废纸篓、草稿箱等模块的List列表相关Action和邮件主页面Action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-27
 * 修改日期: 2013-04-01
 * 修改人员: 汤波涛，增加邮件搜索功能
 * 修改列表:
 */
public class EmailListAction extends EmailBaseAction {

    private static final long serialVersionUID = -4716297123980095126L;
    /**
     * 标记来自搜索
     */
    private static final String FROM_SEARCH = "search";
    private static final int NOT_READ = 1;

    /**
     * log4j日志对象
     */
    private transient Logger logger = Logger.getLogger(EmailListAction.class);

    @Autowired
    IEmailList emailListService;
    
    /**
     * 邮件箱ID
     */
    private int emailBoxId;

    /**
     * 邮件箱名称，供给页面显示
     */
    private String emailBoxName = "";



    /**
     * 搜索词
     */
    private String searchWord;

    /**
     * 未读取标记，1：未读取，0：不处理
     */
    private int notRead = 0;

    /**
     * 邮件搜索VO
     */
    private EmailSearchVo emailSearchVo;

    /**
     * from 哪个功能
     */
    private String from;

    /**
     * 搜索EmailSearchVo的Json对象
     */
    private String searchJson;

    private String redirectURL = "logined/email/emailToListPage.action";

    /**
     * 打开电子邮件主页面的Action
     *
     * @return success 返回到邮件主页面对应jsp
     */
    public String emailMainPage() {
        UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");
        // 查出私人邮件箱列表
        List<EmailBox> emailBoxList = emailBoxService
                .findAllDiyBoxByUserId(adminUser.getUserId());
        //查询出私人邮件箱中的邮件份数
        for (EmailBox emailBox : emailBoxList) {
            int emailCount = this.emailService.countOfEmailToByBoxId(emailBox.getUserId(), emailBox.getId());
            emailBox.setEmailCount(emailCount);
        }
        //查询出用户默认邮件箱中的邮件份数
        Map<String, Integer> defaultBoxEmailCountMap = new HashMap<String, Integer>();
        //收件箱
        EmailBox inBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(adminUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX);
        int inBoxEmailCount = this.emailService.countOfEmailToByBoxId(adminUser.getUserId(), inBox.getId());
        defaultBoxEmailCountMap.put("inBox", inBoxEmailCount);
        //发件箱
        int outBoxEmailCount = this.emailService.countOfEmailBodyByBoxId(adminUser.getUserId(), EmailConst.EMAIL_SENDED);
        defaultBoxEmailCountMap.put("outBox", outBoxEmailCount);
        //草稿篓
        int draftBoxEmailCount = this.emailService.countOfEmailBodyByBoxId(adminUser.getUserId(), EmailConst.EMAIL_NOT_SEND);
        defaultBoxEmailCountMap.put("draftBox", draftBoxEmailCount);
        //废纸篓箱
        EmailBox wastebasketBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(adminUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
        int wastebasketBoxEmailCount = this.emailService.countOfEmailToByBoxId(adminUser.getUserId(), wastebasketBox.getId());
        defaultBoxEmailCountMap.put("wastebasketBox", wastebasketBoxEmailCount);

        //未读邮件
        Map<String, String> defaultBoxEmailNotReadCountMap = new HashMap<String, String>();
        if(this.emailService.countOfNotReadEmail(adminUser.getUserId()) == 0){
        	defaultBoxEmailNotReadCountMap.put("notRead","");
        }else{
        	defaultBoxEmailNotReadCountMap.put("notRead","（"+this.emailService.countOfNotReadEmail(adminUser.getUserId())+"）");
        }
        
        ActionContext.getContext().put("emailBoxList", emailBoxList);
        ActionContext.getContext().put("defaultBoxEmailCountMap", defaultBoxEmailCountMap);
        ActionContext.getContext().put("defaultBoxEmailNotReadCountMap", defaultBoxEmailNotReadCountMap);
        return SUCCESS;
    }

    public String emailNotReadForDesktopTask() {
        this.notRead = NOT_READ;
        return emailToListPage();
    }

    /**
     * 电子邮件箱邮件箱列表页面加载action，检查邮件箱id是否存在以及合法性检查 若不存在，则加载为默认邮件箱id
     *
     * @return 邮件箱列表页面
     */
    public String emailToListPage() {

        UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");
        Object searchEmailBoxId = getSession().getAttribute("searchEmailBoxId");
        EmailBox emailBox = null;
        if (searchEmailBoxId != null) {
            this.emailBoxId = Integer.parseInt(String.valueOf(searchEmailBoxId));
            getSession().removeAttribute("searchEmailBoxId");
        }
        if (this.notRead == NOT_READ) {
            this.emailBoxName = "未读邮件";
            this.emailBoxId = NOT_READ_BOX_ID;
            this.setIDisplayLength(DEFAULT_PAGE_MAX);
        } else {
            emailBox = super.checkEmailBoxId(this.emailBoxId, adminUser);
            this.emailBoxName = emailBox.getBoxName();
            this.emailBoxId = emailBox.getId();
            this.setIDisplayLength(emailBox.getPageMax());
        }

        List<EmailBox> boxList = this.emailBoxService
                .findAllDiyBoxByUserId(adminUser.getUserId());
        if (emailBox != null) {
            if (!emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_INBOX)) {
                // 增加收件箱
                boxList.add(this.emailBoxService.findDefaultBoxByUserIdAndBoxType(
                        adminUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX));
                // 删除当前邮件箱
                for (int i = 0; i < boxList.size(); i++) {
                    if (boxList.get(i).getId().equals(emailBox.getId())) {
                        boxList.remove(i);
                    }
                }
            }
        } else {//未读取模式
            // 增加收件箱
            boxList.add(this.emailBoxService.findDefaultBoxByUserIdAndBoxType(
                    adminUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX));
        }

        ActionContext.getContext().put("boxList", boxList);
        return SUCCESS;
    }

    /**
     * 收件箱中的邮件列表查询,兼容查询未读取邮件
     *
     * @return null，ajax处理返回JSON数据到页面
     */
    public String emailToList() {

        try {
        	this.getRequest().getSession().setAttribute("emailListJson", "");
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            // 检查邮件箱id
            EmailBox emailBox = null;
            emailBox = super.checkEmailBoxId(this.emailBoxId, adminUser);
            this.emailBoxId = emailBox.getId();
//            iDisplayLength = emailBox.getPageMax();
            Page<EmailTo> pageEmailTo = null;
            List<EmailTo> listEmailTo = null;
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"createTime"));
            if (StringUtils.equals(this.from, FROM_SEARCH)) {//来自高级搜索
                pageEmailTo = emailService.findAllReceiveEmailBySearchVO(this.getPageable(sort), adminUser.getUserId(), EmailSearchVo.getInstanceFromJson(this.searchJson));
            } else if (this.notRead == 1) {//查询未读取的邮件，不区分邮件箱,每页显示10个
                this.setIDisplayLength(DEFAULT_PAGE_MAX);
                pageEmailTo = emailService.findAllNotReadEmailBySearchWord(this.getPageable(sort), adminUser.getUserId(), searchWord);
                listEmailTo = emailListService.getAllNotReadEmail(adminUser.getUserId(), searchWord);
            } else {
                pageEmailTo = emailService.findAllReceiveEmailBySearchWord(
                        this.getPageable(sort), adminUser.getUserId(), emailBox.getId(),
                        searchWord);
                listEmailTo = emailListService.findAllReceiveEmailBySearchWord(adminUser.getUserId(), emailBox.getId(), searchWord);
            }
            List<Map<String,Object>> emailList = getEmailListForSession(listEmailTo);
            Gson json = new Gson();
            String emailListJson = json.toJson(emailList);
            this.getRequest().getSession().setAttribute("emailListJson", emailListJson);

            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            int count = 1;
            if (pageEmailTo.getContent() != null) {
                for (EmailTo emailTo : pageEmailTo.getContent()) {
                    Map<String, Object> toInfoMap = new HashMap<String, Object>();
                    UserInfo fromUser = emailTo.getCreateUserInfo();
                    EmailBody emailBody = emailTo.getEmailBody();
                    // 所取所在部门名称
                    GroupInfo groupInfo = groupService.getGroupByUserId(fromUser.getCompanyId(), fromUser.getUserId());
                    String groupName = groupInfo != null ? groupInfo.getGroupName()
                            : "";

                    toInfoMap.put("id", emailTo.getId());
                    toInfoMap.put("emailBodyId", emailBody.getId());
                    toInfoMap.put("no", count++);
                    toInfoMap.put("markLevel", emailTo.getMarkLevel());
                    toInfoMap.put("readStatus", emailTo.getReadStatus());
                    toInfoMap.put("fromUserName", fromUser.getUserName());
                    toInfoMap.put("fromUserGroupName", StringUtils.defaultString(groupName));
                    toInfoMap.put("subject", getSubjectOrNo(emailBody));
                    toInfoMap.put("importantLevel",emailBody.getImportantLevel());
                    toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody
                            .getCreateTime()));
                    toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                            .getCreateTime()));
                    toInfoMap.put("hasAttachment", StringUtils.isNotEmpty(emailBody.getAttachment()));
                    toInfoMap.put("attachmentSize", getEmailSize(emailBody));
                    String content=getEmailContent(emailBody.getContentInfo());
                    toInfoMap.put("content",content);
                    aaDataList.add(toInfoMap);
                }
            }
            this.ajaxPage(pageEmailTo,aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } 
        return null;
    }

    /**
     * 获取邮件内容
     * @param contentInfo
     * @return
     */
    private String getEmailContent(String contentInfo) {
		String res="";
		if(StringUtils.isNotBlank(contentInfo)){
			contentInfo = htmlRemoveTag(contentInfo);
			res = contentInfo;
		}
		return res;
	}

    
    /**
     * 删除Html标签
     * 
     * @param inputString
     * @return
     */
    public static String htmlRemoveTag(String inputString) {
    	if (inputString == null)
    		return null;
    	String htmlStr = inputString; // 含html标签的字符串
    	String textStr = "";
    	java.util.regex.Pattern p_script;
    	java.util.regex.Matcher m_script;
    	java.util.regex.Pattern p_style;
    	java.util.regex.Matcher m_style;
    	java.util.regex.Pattern p_html;
    	java.util.regex.Matcher m_html;
    	try {
    		//定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
    		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; 
    		//定义style的正则表达式{或]*?>[\\s\\S]*?<\\/style>
    		String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; 
    		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    		m_script = p_script.matcher(htmlStr);
    		htmlStr = m_script.replaceAll(""); // 过滤script标签
    		p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
    		m_style = p_style.matcher(htmlStr);
    		htmlStr = m_style.replaceAll(""); // 过滤style标签
    		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
    		m_html = p_html.matcher(htmlStr);
    		htmlStr = m_html.replaceAll(""); // 过滤html标签
    		textStr = htmlStr;
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return textStr;// 返回文本字符串
    }
    
	/**
     * 打开已发送列表页面
     *
     * @return success 返回到已发送列表页面
     */
    public String emailSendedListPage() {
        this.setIDisplayLength(super.getDefaultEmailBox(
                EmailConst.EMAIL_BOX_TYPE_OUTBOX).getPageMax());
        this.emailBoxName = "已发送";
        return SUCCESS;
    }

    /**
     * 获取已发送列表中的邮件
     *
     * @return null ajax处理，返回JSON数据到页面
     */
    public String emailSendedList() {

        try {
        	this.getRequest().getSession().setAttribute("emailListJson", "");
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            EmailBox emailBox = emailBoxService
                    .findDefaultBoxByUserIdAndBoxType(adminUser.getUserId(),
                            EmailConst.EMAIL_BOX_TYPE_OUTBOX);
            this.setIDisplayLength(emailBox.getPageMax());
            Page<EmailBody> pageEmailBody = null;
            List<EmailBody> listEmailTo = null;
            Sort sort = new Sort( Direction.DESC,"sendTime", "lastUpdateTime");
            if (StringUtils.equals(this.from, FROM_SEARCH)) {//来自高级搜索
                pageEmailBody = emailService.findAllSendedEmailBodyBySearchVo(this.getPageable(sort),
                        adminUser.getUserId(), EmailSearchVo.getInstanceFromJson(this.searchJson));
                listEmailTo = emailListService.findAllSendedEmailBodyBySearchVo(adminUser.getUserId(), EmailSearchVo.getInstanceFromJson(this.searchJson));
            } else {
                pageEmailBody = emailService.findAllSendedEmailBodyByUserId(
                        this.getPageable(sort), adminUser.getUserId(),"");
                listEmailTo = emailListService.findAllSendedEmailBodyByUserId(adminUser.getUserId(), "");
            }
            
            List<Map<String,Object>> emailList = getEmailListForSessionFromBody(listEmailTo);
            Gson json = new Gson();
            String emailListJson = json.toJson(emailList);
            this.getRequest().getSession().setAttribute("emailListJson", emailListJson);
            
            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            int count = 1;
            for (EmailBody emailBody : pageEmailBody.getContent()) {
                Map<String, Object> toInfoMap = new HashMap<String, Object>();
                toInfoMap.put("id", emailBody.getId());
                toInfoMap.put("no", count++);

                // 收件人==收件人+密送人
                int toCount = super.getToUsersCountFromEmailBody(emailBody);
                String toNames = "";
                Integer readStatus = 0;
                //if (toCount == 1) {
                    // 一个收件人：可能是收件人也可能是密送人
                
                    if (!StringUtils.isEmpty(emailBody.getToId())) {
                        toNames += super.getToUsersNameFromEmailBody(emailBody,
                                EmailConst.EMAIL_TO_TYPE_TO);
                        String noSepStr = getNoSepStr(emailBody.getToId());
                        if(StringUtils.isNotBlank(noSepStr)){
                        	for(Object obj : noSepStr.split(",")){
                        		int thisReadStatus = this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(),
                                        Integer.parseInt(obj.toString()));
                        		if(thisReadStatus == 1){
                        			readStatus = 1;
                        			break;
                        		}
                        	}
                        }
                    } 
                    if (!StringUtils.isEmpty(emailBody.getSecretToId())) {
                        toNames += super.getToUsersNameFromEmailBody(emailBody,
                                EmailConst.EMAIL_TO_TYPE_SECRET);
                        String noSepStr = getNoSepStr(emailBody.getSecretToId());
                        if(StringUtils.isNotBlank(noSepStr)){
                        	for(Object obj : noSepStr.split(",")){
                        		int thisReadStatus = this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(),
                                        Integer.parseInt(obj.toString()));
                        		if(thisReadStatus == 1){
                        			readStatus = 1;
                        			break;
                        		}
                        	}
                       }
                    }
                    if (!StringUtils.isEmpty(emailBody.getCopyToId())) {
                        toNames += super.getToUsersNameFromEmailBody(emailBody,
                                EmailConst.EMAIL_TO_TYPE_COPY);
                        String noSepStr = getNoSepStr(emailBody.getCopyToId());
                        if(StringUtils.isNotBlank(noSepStr)){
                        	for(Object obj : noSepStr.split(",")){
                        		int thisReadStatus = this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(),
                                        Integer.parseInt(obj.toString()));
                        		if(thisReadStatus == 1){
                        			readStatus = 1;
                        			break;
                        		}
                        	}
                       }
                    }
                /*} else {
                    toNames = "查看";
                    readStatus = -1;//表示为群邮件
                    if (allNotRead(emailBody)) {
                        readStatus = -2;//表示为群邮件均未读取、可编辑
                    }
                }*/

                toInfoMap.put("toCount", toCount);
                toInfoMap.put("toName", toNames);
                toInfoMap.put("readStatus", readStatus);
                toInfoMap.put("subject", getSubjectOrNo(emailBody));
                toInfoMap.put("importantLevel",emailBody.getImportantLevel());
                toInfoMap.put("hasAttachment", StringUtils.isNotEmpty(emailBody.getAttachment()));
                toInfoMap.put("attachmentSize", getEmailSize(emailBody));
                toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody
                        .getCreateTime()));
                toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                        .getCreateTime()));
                aaDataList.add(toInfoMap);
            }
           this.ajaxPage(pageEmailBody, aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 打开草稿箱列表页面
     *
     * @return success 返回到草稿箱列表页面jsp
     */
    public String emailDraftListPage() {
        this.setIDisplayLength(super.getDefaultEmailBox(EmailConst.EMAIL_BOX_TYPE_DRAFT)
                .getPageMax());
        this.emailBoxName = "草稿箱";
        return SUCCESS;
    }

    /**
     * 获取草稿箱列表中的草稿
     *
     * @return null：ajax处理，返回JSON数据到页面
     */
    public String emailDraftList() {

        try {
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
//            EmailBox emailBox = emailBoxService
//                    .findDefaultBoxByUserIdAndBoxType(adminUser.getUserId(),
//                            EmailConst.EMAIL_BOX_TYPE_OUTBOX);
//            this.setIDisplayLength(emailBox.getPageMax());
            Page<EmailBody> pageEmailBody = null;
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC,"lastUpdateTime"));
            if (StringUtils.equals(this.from, FROM_SEARCH)) {//来自高级搜索
                pageEmailBody = emailService.findAllDraftEmailBodyBySearchVo(
                        this.getPageable(sort), adminUser.getUserId(), EmailSearchVo.getInstanceFromJson(this.searchJson));
            } else {
                pageEmailBody = emailService.findAllDraftEmailBodyByUserId(
                        this.getPageable(sort), adminUser.getUserId(),"");
            }

            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            for (EmailBody emailBody : pageEmailBody.getContent()) {
                Map<String, Object> toInfoMap = new HashMap<String, Object>();
                toInfoMap.put("id", emailBody.getId());

                toInfoMap.put("subject", getSubjectOrNo(emailBody));
                toInfoMap.put("importantLevel",emailBody.getImportantLevel());
                toInfoMap.put("hasAttachment", StringUtils.isNotEmpty(emailBody.getAttachment()));
                toInfoMap.put("attachmentSize", getEmailSize(emailBody));
                toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody
                        .getLastUpdateTime()));
                toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                        .getLastUpdateTime()));
                aaDataList.add(toInfoMap);
            }
          this.ajaxPage(pageEmailBody, aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } 
        return null;
    }

    /**
     * 打开 废纸篓列表 页面
     *
     * @return success 返回到废纸篓列表页面 jsp
     */
    public String emailWastebasketListPage() {
        this.setIDisplayLength(getDefaultEmailBox(
                EmailConst.EMAIL_BOX_TYPE_WASTEBASKET).getPageMax());
        this.emailBoxName = "垃圾箱";
        return SUCCESS;
    }

    /**
     * 获取废纸篓中的邮件列表
     *
     * @return null ajax请求，返回JSON数据到页面
     */
    public String emailWastebasketList() {

        try {
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            EmailBox emailBox = emailBoxService
                    .findDefaultBoxByUserIdAndBoxType(adminUser.getUserId(),
                            EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
            this.setIDisplayLength(emailBox.getPageMax());
            Page<EmailTo> pageEmailTo = null;
            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
            if (StringUtils.equals(this.from, FROM_SEARCH)) {//来自高级搜索,searchJson已经包含了该废纸篓的emailBoxId
                pageEmailTo = emailService.findAllReceiveEmailBySearchVO(this.getPageable(sort),
                        adminUser.getUserId(), EmailSearchVo.getInstanceFromJson(this.searchJson));
            } else {
                pageEmailTo = emailService.findAllReceiveEmailByUserId(this.getPageable(sort),
                        adminUser.getUserId(), emailBox.getId());
            }
            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            int count = 1;
            for (EmailTo emailTo : pageEmailTo.getContent()) {
                Map<String, Object> toInfoMap = new HashMap<String, Object>();
                UserInfo fromUser = emailTo.getCreateUserInfo();
                EmailBody emailBody = emailTo.getEmailBody();

                // 所取所在部门名称
                GroupInfo groupInfo = groupService.findOne(fromUser.getGroupId());
                String groupName = groupInfo != null ? groupInfo.getGroupName()
                        : "";
                toInfoMap.put("id", emailTo.getId());
                toInfoMap.put("emailBodyId", emailBody.getId());
                toInfoMap.put("no", count++);
                toInfoMap.put("fromUserName", fromUser.getUserName());
                toInfoMap.put("fromUserGroupName", StringUtils.defaultString(groupName));
                toInfoMap.put("subject", getSubjectOrNo(emailBody));
                toInfoMap.put("readStatus", emailTo.getReadStatus());
                toInfoMap.put("importantLevel",emailBody.getImportantLevel());
                toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody
                        .getCreateTime()));
                toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                        .getCreateTime()));
                aaDataList.add(toInfoMap);
            }
            this.ajaxPage(pageEmailTo, aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } 
        return null;
    }

    /**
     * 打开搜索页面，加载某些初始值，如邮件箱列表选择初始值等
     *
     * @return success 返回到邮件搜索页面
     */
    public String searchPage() {
        List<EmailBox> boxList = this.emailBoxService.findAllByUserId(getLoginUser().getUserId());
        ActionContext.getContext().put("boxList", boxList);
        if (StringUtils.isNotEmpty(this.searchJson)) {
            this.emailSearchVo = EmailSearchVo.getInstanceFromJson(this.searchJson);
        }
        return SUCCESS;
    }

    /**
     * 搜索，返回搜索结果JSON字符串
     *
     * @return null 返回搜索结果JSON字符串
     */
    public String searchList() {

        EmailBox emailBox = this.emailBoxService.findById(emailSearchVo.getEmailBoxId());
        String resultType = "";
        if (emailBox == null) {
            logger.error("未选择邮件箱或选择的邮件箱不存在");
            return ERROR;
        }
        if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_INBOX)
                || emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_DIY)) {//收件箱
            resultType = "inbox";
            getSession().setAttribute("searchEmailBoxId", emailBox.getId());
        } else if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_OUTBOX)) {//发件箱
            resultType = "outbox";
        } else if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_WASTEBASKET)) {//废纸篓
            resultType = "wastebasket";
        } else if (emailBox.getBoxType().equals(EmailConst.EMAIL_BOX_TYPE_DRAFT)) {//草稿箱
            resultType = "draft";
        } else {
            logger.error("未知邮件箱类型");
            return ERROR;
        }
        this.from = EmailListAction.FROM_SEARCH;
        this.searchJson = this.emailSearchVo.toJson();
        return resultType;
    }

    /**
     * 封装邮件列表
     * @return
     */
    private List<Map<String,Object>> getEmailListForSession(List<EmailTo> listEmailTo) throws Exception{
    	if(listEmailTo==null||listEmailTo.size()<=0){
    		return null;
    	}
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	for(EmailTo emailTo:listEmailTo){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("emailToId", emailTo.getId().toString());
    		map.put("emailBodyId", emailTo.getEmailBody().getId().toString());
    		list.add(map);
    	}
    	return list;
    }
    /**
     * 封装邮件列表
     * @return
     */
    private List<Map<String,Object>> getEmailListForSessionFromBody(List<EmailBody> listEmailBody) throws Exception{
    	if(listEmailBody==null||listEmailBody.size()<=0){
    		return null;
    	}
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	for(EmailBody emailBody:listEmailBody){
    		Map<String,Object> map = new HashMap<String, Object>();
    		map.put("emailToId", "0");
    		map.put("emailBodyId", emailBody.getId().toString());
    		list.add(map);
    	}
    	return list;
    }
    
    
    
    public int getEmailBoxId() {
        return emailBoxId;
    }

    public void setEmailBoxId(int emailBoxId) {
        this.emailBoxId = emailBoxId;
    }



    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }


    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public int getNotRead() {
        return notRead;
    }

    public void setNotRead(int notRead) {
        this.notRead = notRead;
    }

    public String getEmailBoxName() {
        return emailBoxName;
    }

    public void setEmailBoxName(String emailBoxName) {
        this.emailBoxName = emailBoxName;
    }

    public EmailSearchVo getEmailSearchVo() {
        return emailSearchVo;
    }

    public void setEmailSearchVo(EmailSearchVo emailSearchVo) {
        this.emailSearchVo = emailSearchVo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSearchJson() {
        return searchJson;
    }

    public void setSearchJson(String searchJson) {
        this.searchJson = searchJson;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }
}
