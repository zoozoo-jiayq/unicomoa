package cn.com.qytx.oa.email.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.oa.util.UpdateFieldUtil;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.OnlineUserListener;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功能:邮件处理的Controller，包含收件箱、已发送、废纸篓、草稿箱等模块的除列表，导出外的所有Action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-27
 * 修改人员: 汤波涛
 * 修改列表:2013-03-27日重大修改，拆分EmailAction为EmailBaseAction、EmailListAction、EmailExportAction、EmailAction
 */
public class EmailAction extends EmailBaseAction {

    private static final long serialVersionUID = -4716297123980095125L;

    /**
     * log4j日志对象
     */
    private static final Logger logger = Logger.getLogger(EmailAction.class);

    /**
     * 邮件体
     */
    private EmailBody emailBody;
    /**
     * 是否需要立即发送，0：不需要，1：需要
     */
    private int send = 0;

    /**
     * 邮件体id
     */
    private int emailBodyId = 0;

    /**
     * 收件ID
     */
    private int emailToId = 0;

    /**
     * 收件箱ID集合字符串
     */
    private String emailToIds;

    /**
     * 发件箱ID集合字符串
     */
    private String emailBodyIds;


    /**
     * 要移动或者删除的目标邮件箱ID
     */
    private int targetBoxId;

    /**
     * 是否需要回复全部，0：否，1：是
     */
    private int replyAll;

    /**
     * 需要标记的星级值
     */
    private int markLevel;

    /**
     * 邮件编辑页面，from哪种请求的编辑
     */
    private String from;

    /**
     * 邮箱数组集合
     */
    private Integer boxIds[];

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 群组ID
     */
    private Integer groupId;
    
    /**
     * 隐藏的按钮
     */
    private String hideBtn;

    /**
     * 群组Id(gid_)或者用户Id(uid_)
     */
    private String idStr;
    
    /**
     * 表示是否向在线人员发送微讯
     */
    private String onLine;
    
    /**
     * 是否有上一封 1/0
     */
    private int prevFlag = -1;
    /**
     * 是否有下一封1/0
     */
    private int nextFlag = -1;
    private String prevTitle;
    private String nextTitle;
    private int prevEmailToId = 0;
    private int prevEmailBodyId = 0;
    private int nextEmailToId = 0;
    private int nextEmailBodyId = 0;
    
    /**
     * 单条邮件展示
     *
     * @return success 返回到邮件详情页面 jsp
     */
    public String emailDetail() throws Exception {
    	String emailListJson = (String) this.getRequest().getSession().getAttribute("emailListJson");
        // 标记为已读
        if (emailToId != 0) {
            EmailTo emailTo = this.emailService.findEmailToById(emailToId);
            if (emailTo == null || emailTo.getEmailToStatus().equals(EmailConst.EMAIL_CANCEL_SEND)
                    || emailTo.getIsDelete().equals(CbbConst.DELETED)) {
                this.errorMsg = "email_not_exist";
                return NOT_EXIST;
            }
            if (!emailTo.getReadStatus().equals(EmailConst.EMAIL_READED)) {
                //来自收件箱或者废纸篓中的打开，标记为已读取
                this.emailService.readedEmailTo(emailToId);
            }
            EmailBox emailBoxWast = this.emailBoxService.findDefaultBoxByUserIdAndBoxType
                    (getLoginUser().getUserId(), EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
            if (emailTo.getEmailBox().getId().equals(emailBoxWast.getId())) {
                this.from = "emailToWastebasket";//来自草稿箱中
            } else {
                this.from = "emailTo";//来自收件箱
            }
            this.emailBody = emailTo.getEmailBody();
            //初始化上一封 下一封邮件
            getPrevNextEmail(emailListJson,1);
        } else if (emailBodyId != 0) {
            this.emailBody = this.emailService.findEmailBodyByIdNotDelete(emailBodyId);
            if (emailBody == null) {
                this.errorMsg = "email_not_exist";
                return NOT_EXIST;
            }
            this.from = "emailBody";//来自发件箱
            if (emailBody.getSendStatus().equals(EmailConst.EMAIL_NOT_SEND)) {
                this.from = "emailBodyDraft";//来自草稿箱
            }
            //初始化上一封 下一封邮件
            getPrevNextEmail(emailListJson,2);
        } else {
            this.errorMsg = "email_not_exist";
            return NOT_EXIST;
        }
        cleanSep(this.emailBody);
        return SUCCESS;
    }

    /**
     * ajax标记为已读
     *
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToReaded() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何邮件";
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray) {
                    this.emailService.readedEmailTo(Integer.parseInt(id));
                }
                result = "标记" + emailToIdArray.length + "封邮件为已读成功";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax标记为已读
     *
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToReadedAll() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            int count = emailService.updateEmailToAllReaded(getLoginUser().getUserId(), this.targetBoxId);
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 创建一封新邮件
     *
     * @return success 邮件编辑页面
     */
    public String emailBodyCreate() {
        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        this.from = "create";
        return SUCCESS;
    }

    /**
     * 保存邮件并识别是否需要发送
     *
     * @return success：跳转到邮件发送成功页面
     *         error：返回到邮件编辑页面并提示错误信息
     */
    public String save() {
        PrintWriter out = null;
        int status = 1;
        String result = "";
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            out = getResponse().getWriter();
            if (this.send != 0 && StringUtil.isEmpty(emailBody.getToId())
                    && StringUtil.isEmpty(emailBody.getCopyToId())
                    && StringUtil.isEmpty(emailBody.getSecretToId())) {
                status = 0;
                result = "收件人、抄送人、密送人不能同时为空";
            } else {
                emailBody.setSendStatus(EmailConst.EMAIL_NOT_SEND);
                emailBody.setCreateUserInfo(this.getLoginUser());
                emailBody.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                emailBody.setLastUpdateUserId(this.getLoginUser().getUserId());
                emailBody.setSendTime(new Timestamp(System.currentTimeMillis()));
                emailBody.setCompanyId(this.getLoginUser().getCompanyId());
                if (emailBody.getAttachmentSize() == null) {
                    emailBody.setAttachmentSize(0L);
                }
                if (emailBody.getImportantLevel() == null) {
                    emailBody.setImportantLevel(EmailConst.EMAIL_IMPORTANT_LEVEL_NORMAL);
                }
                if (send != 0) {
                    this.emailService.saveAndSend(emailBody);
                } else {
                    this.emailService.saveOrUpdate(emailBody);
                }
                status = 1;
                result = String.valueOf(this.emailBody.getId());
            }
            jsonMap.put("status", status);
            jsonMap.put("result", result);
            Gson gson = new Gson();
            out.print(gson.toJson(jsonMap));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }


    /**
     * 更新邮件并识别是否需要发送
     *
     * @return success：跳转到邮件发送成功页面
     *         error：返回到邮件编辑页面并提示错误信息
     */
    public String update() throws Exception {


        PrintWriter out = null;
        int status = 1;
        String result = "";
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            out = getResponse().getWriter();
            if (this.send != 0 && StringUtil.isEmpty(this.emailBody.getToId())
                    && StringUtil.isEmpty(this.emailBody.getCopyToId())
                    && StringUtil.isEmpty(this.emailBody.getSecretToId())) {
                status = 0;
                result = "收件人、抄送人、密送人不能同时为空";
            } else {
                EmailBody emailBodyDB = this.emailService.findOne(this.emailBody.getId());
                UpdateFieldUtil.update(this.emailBody, emailBodyDB, "id");
                emailBodyDB.setCreateTime(new Timestamp(System.currentTimeMillis()));
                this.emailBody = null;
                if (send != 0) {
                    this.emailService.updateAndSend(emailBodyDB);
                } else {
                    this.emailService.update(emailBodyDB);
                }
                this.emailBody = emailBodyDB;
                status = 1;
                result = String.valueOf(this.emailBody.getId());
            }
            jsonMap.put("status", status);
            jsonMap.put("result", result);
            Gson gson = new Gson();
            out.print(gson.toJson(jsonMap));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 删除邮件，可批量删除
     *
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToDelete() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何行！";
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray) {
                    this.emailService.deleteEmailTo(Integer.parseInt(id));
                }
                result = "删除" + emailToIdArray.length + "封邮件成功！";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 删除邮件，可批量删除
     *
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToReadedDelete() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            int count = this.emailService.deleteEmailToReaded(getLoginUser().getUserId(), this.targetBoxId);
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 销毁邮件，可批量销毁
     *
     * @return ajax请求返回Text文本信息到页面
     */
    public String emailToDestroy() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何行！";
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray) {
                    this.emailService.destroyEmailTo(Integer.parseInt(id));
                }
                result = "销毁" + emailToIdArray.length + "封邮件成功！";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 恢复删除的邮件，可批量恢复
     *
     * @return ajax请求，返回Text文本信息到页面
     */
    public String emailToRecovery() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何行";
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray) {
                    this.emailService.recoveryEmailTo(Integer.parseInt(id));
                }
                result = "恢复" + emailToIdArray.length + "封邮件成功";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax 移动或批量移动邮件到指定邮件箱
     *
     * @return ajax请求返回Text信息到页面
     */
    public String emailToMove() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何邮件";
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray) {
                    this.emailService.moveEmailTo(Integer.parseInt(id),
                            targetBoxId);
                }
                result = "移动" + emailToIdArray.length + "封邮件成功";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 转发一封邮件--来自收件箱
     *
     * @return success 跳转到邮件编辑页面并加载初始值
     */
    public String emailToForward() {

        int emailToId = Integer.parseInt(this.emailToIds.split(",")[0]);
        EmailTo emailTo = this.emailService.findEmailToById(emailToId);
        EmailBody emailBodyDB = emailTo.getEmailBody();
        EmailBody emailBodyCreate = new EmailBody();
        emailBodyCreate.setSubject("转发:" + emailBodyDB.getSubject());
        emailBodyCreate.setAttachment(emailBodyDB.getAttachment());
        emailBodyCreate.setAttachmentSize(emailBodyDB.getAttachmentSize());
        emailBodyCreate.setImportantLevel(emailBodyDB.getImportantLevel());

        this.emailBody = emailBodyCreate;
        cleanSep(this.emailBody);
        ActionContext.getContext().put("defaultContentJSONMap", super.getDefaultContentJSONMap(emailBodyDB));

        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于转发
        this.from = "forward";
        return SUCCESS;
    }

    /**
     * 转发一封邮件--来自发件箱
     *
     * @return 跳转到邮件编辑页面并加载初始值
     */
    public String emailBodyForward() {

        EmailBody emailBodyDB = this.emailService.findOne(this.emailBodyId);
        EmailBody emailBodyCreate = new EmailBody();
        emailBodyCreate.setSubject("Fw:" + emailBodyDB.getSubject());

        emailBodyCreate.setAttachment(emailBodyDB.getAttachment());
        emailBodyCreate.setAttachmentSize(emailBodyDB.getAttachmentSize());
        emailBodyCreate.setImportantLevel(emailBodyDB.getImportantLevel());
        emailBodyCreate.setContentInfo(emailBodyDB.getContentInfo());
        this.emailBody = emailBodyCreate;
        cleanSep(this.emailBody);
        ActionContext.getContext().put("defaultContentJSONMap", super.getDefaultContentJSONMap(emailBodyDB));

        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于转发
        this.from = "forward";
        return SUCCESS;
    }

    /**
     * 再次发送邮件--来自已发送列表页面
     *
     * @return success:跳转到邮件编辑页面并加载初始值
     */
    public String emailBodyAgainSend() {

        EmailBody emailBodyDB = this.emailService.findOne(this.emailBodyId);
        this.emailBody = emailBodyDB;
        this.emailBody.setId(null);
        cleanSep(this.emailBody);
        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于再次发送
        this.from = "againSend";
        return SUCCESS;
    }

    /**
     * 给指定的用户发送邮件
     *
     * @return success:跳转到邮件编辑页面并加载初始值
     */
    public String sendEmailByUserId() throws Exception{
        UserInfo loginUser=getLoginUser();
        if(this.userId==null){
            ajax("用户ID为空");
            return null;
        }
        UserInfo userInfo=this.userService.findOne(this.userId);
        if(userInfo==null){
            ajax("找不到用户:userId="+this.userId);
            return null;
        }
        this.emailBody = new EmailBody();
        this.emailBody.setToName(userInfo.getUserName());
        this.emailBody.setToId(String.valueOf(userInfo.getUserId()));

        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于新建
        this.from = "create";
        return SUCCESS;
    }
    
    /**
     * 给指定的群组发送邮件
     *
     * @return success:跳转到邮件编辑页面并加载初始值
     */
    public String sendEmailByGroupId() throws Exception{
        if(this.groupId==null){
            ajax("群组ID为空");
            return null;
        }
        List<UserInfo> userInfoList=this.userService.findUsersByGroupId(groupId+"");
        if(userInfoList==null){
            ajax("找不到群组下用户:groupId="+this.groupId);
            return null;
        }
        
        StringBuffer userNameSb = new StringBuffer();
        StringBuffer userIdSb = new StringBuffer();
        List<Integer> onLIneList = new ArrayList<Integer>();
        for(UserInfo tempUserInfo : userInfoList)
        {
         // 判断是否只向在线人员发送微讯
            if (!StringUtil.isEmpty(onLine) && "onLine".equals(onLine)){
                if (isOnline(onLIneList, tempUserInfo.getUserId()))
                {
                    userNameSb.append(tempUserInfo.getUserName() + ",");
                    userIdSb.append(tempUserInfo.getUserId()+",");
                }
            }else{
                userNameSb.append(tempUserInfo.getUserName() + ",");
                userIdSb.append(tempUserInfo.getUserId()+",");
            }            
        }
        this.emailBody = new EmailBody();
        if (userIdSb.length() > 0)
        {
            this.emailBody.setToName(userNameSb.substring(0, userNameSb.length()-1));
            this.emailBody.setToId(userIdSb.substring(0, userIdSb.length()-1));
        }
        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于新建
        this.from = "create";
        return SUCCESS;
    }
    
    private boolean isOnline(List<Integer> onLIneList, Integer userId)
    {
        if (null != onLIneList && !onLIneList.isEmpty()){
            for (Integer temp:onLIneList){
                if (userId.intValue() == temp.intValue()){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 回复、回复全部 一封邮件
     *
     * @return success:跳转到邮件编辑页面并加载初始值
     */
    public String emailToReply() {

        EmailTo emailTo = this.emailService.findEmailToById(emailToId);
        EmailBody emailBodyDB = emailTo.getEmailBody();
        EmailBody emailBodyCreate = new EmailBody();
        emailBodyCreate.setSubject("Re:" + emailBodyDB.getSubject());

        String sendUserId=String.valueOf(emailBodyDB.getCreateUserInfo().getUserId());
        String sendUserName=emailBodyDB.getCreateUserInfo().getUserName();

        String toNames = emailBodyDB.getToName();
        if(!StringUtil.isEmpty(toNames)){
        	String m = toNames.substring(0,1);
        	if(",".equals(m)){
        		toNames = toNames.substring(1);
        	}
        	if(toNames.length()>0){
        		String n = toNames.substring(toNames.length()-1);
        		if(",".equals(n)){
        			toNames = toNames.substring(0,toNames.length()-1);
        		}
        	}
        }
        ActionContext.getContext().put("defaultToUsers", toNames);
        if (this.replyAll != 0) {
            // 回复全部
            //如果收件人中没有发件人则加上发件人
            if(!emailBodyDB.getToId().contains(EmailConst.EMAIL_TO_SEPARATOR+sendUserId+EmailConst.EMAIL_TO_SEPARATOR)){
                emailBodyDB.setToId(EmailConst.EMAIL_TO_SEPARATOR+sendUserId+emailBodyDB.getToId());
                emailBodyDB.setToName(EmailConst.EMAIL_TO_SEPARATOR+sendUserName+emailBodyDB.getToName());
            }
            //如果收件人、抄送人有自己则删除自己
            UserInfo adminUser=getLoginUser();
            String selfUserId=EmailConst.EMAIL_TO_SEPARATOR+adminUser.getUserId()+EmailConst.EMAIL_TO_SEPARATOR;
            String selfUserName=EmailConst.EMAIL_TO_SEPARATOR+adminUser.getUserName()+EmailConst.EMAIL_TO_SEPARATOR;
            if(emailBodyDB.getToId().contains(selfUserId)){
                emailBodyDB.setToId(emailBodyDB.getToId().replaceAll(selfUserId,","));
                emailBodyDB.setToName(emailBodyDB.getToName().replaceAll(selfUserName,","));
            }
            emailBodyCreate.setToId(emailBodyDB.getToId());
            emailBodyCreate.setToName(emailBodyDB.getToName());
            emailBodyCreate.setCopyToId(emailBodyDB.getCopyToId());
            emailBodyCreate.setCopyToName(emailBodyDB.getCopyToName());
        }else{
            emailBodyCreate.setToId(sendUserId);
            emailBodyCreate.setToName(sendUserName);
        }
        emailBodyCreate.setAttachment(emailBodyDB.getAttachment());
        emailBodyCreate.setAttachmentSize(emailBodyDB.getAttachmentSize());
        emailBodyCreate.setContentInfo(emailBodyDB.getContentInfo());
        emailBodyCreate.setImportantLevel(emailBodyDB.getImportantLevel());
        this.emailBody = emailBodyCreate;
        cleanSep(this.emailBody);
        ActionContext.getContext().put("defaultContentJSONMap", super.getDefaultContentJSONMap(emailTo.getEmailBody()));

        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于回复
        this.from = "reply";
        return SUCCESS;
    }

    /**
     * 修改邮件体，草稿箱
     *
     * @return success 返回到邮件编辑页面
     */
    public String emailBodyEditDraft() {
        EmailBody emailBodyDB = this.emailService.findOne(this.emailBodyId);
        this.emailBody = emailBodyDB;
        cleanSep(this.emailBody);
        List<UserInfo> recentUserInfoList = this.emailService.findRecentContacts(getLoginUser().getUserId(), EmailConst.EMAIL_RECENT_CONTACTS_COUNT);
        ActionContext.getContext().put("recentUserInfoList", recentUserInfoList);
        // 标记来自于修改草稿箱
        this.from = "emailBodyEditDraft";
        return SUCCESS;
    }

    /**
     * ajax 删除或批量删除 已发送、草稿箱邮件
     *
     * @return null ,ajax请求，返回text文本到页面
     */
    public String emailBodyDelete() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailBodyIds)) {
                result = "没有选中任何邮件";
            } else {
                String[] emailBodyIdArray = this.emailBodyIds.split(",");
                for (String id : emailBodyIdArray) {
                    this.emailService.deleteEmailBody(Integer.parseInt(id));
                }
                result = "删除" + emailBodyIdArray.length + "封邮件成功";
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 修改收件箱邮件星级
     *
     * @return ajax数据
     */
    public String emailToChangeMarkLevel() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            this.emailService.updateEmailToMarkLevel(this.emailToId, this.markLevel);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax 发送或批量发送草稿箱中的邮件
     *
     * @return null, ajax请求，返回text文本
     */
    public String emailBodyDraftSend() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailBodyIds)) {
                result = "没有选中任何邮件";
            } else {
                String[] emailBodyIdArray = this.emailBodyIds.split(",");
                int total = emailBodyIdArray.length;
                int count = 0;
                for (String id : emailBodyIdArray) {
                    if (this.emailService.updateAndSendDraft(Integer.parseInt(id))) {
                        count++;
                    }
                }
                result = "发送" + count + "封邮件成功";
                if (total > count) {
                    result += ", " + (total - count) + " 封邮件由于没有指定任何收件人而发送失败";
                }
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 修改一封邮件，收信人尚未读取
     *
     * @return success 返回到邮件编辑页面
     */
    public String emailBodyEditNotRead() {
        EmailBody emailBodyDB = this.emailService.findOne(this.emailBodyId);
        if (emailBodyDB == null) {
            logger.error("邮件ID：" + this.emailBodyId + "不存在或者已删除");
            return ERROR;
        }
        if (!allNotRead(emailBodyDB)) {
            logger.error("邮件:" + emailBodyDB.getSubject() + "ID:" + emailBodyDB.getId() + "已经有收件人读取过，不允许修改");
            return ERROR;
        }
        this.emailBody = emailBodyDB;
        cleanSep(this.emailBody);
        this.from = "emailBodyEditNotRead";
        return SUCCESS;
    }

    /**
     * 删除收件人已经删除的邮件
     *
     * @return ajax文本
     */
    public String emailBodyDeleteIfToUserDeleted() {

        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            UserInfo adminUser = super.getLoginUser();
            int count = emailService.deleteEmailBodyIfToUserDeleted(adminUser.getUserId());
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 删除收件人已经读取的邮件
     *
     * @return ajax文本
     */
    public String emailBodyDeleteIfToUserReaded() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            UserInfo adminUser = super.getLoginUser();
            int count = emailService.deleteEmailBodyIfToUserReaded(adminUser.getUserId());
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 删除收件人已经读取的邮件
     *
     * @return ajax文本
     */
    public String emailBodyDeleteIfToUserNotRead() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            UserInfo adminUser = super.getLoginUser();
            int count = emailService.deleteEmailBodyIfToUserNotRead(adminUser.getUserId());
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 清空废纸篓
     *
     * @return 返回ajax Text
     */
    public String emailToWastebasketClean() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            UserInfo adminUser = super.getLoginUser();
            int count = super.emailService.deleteEmailToWastebasket(adminUser.getUserId());
            result = String.valueOf(count);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 查看邮件阅读状态
     *
     * @return success 邮件阅读状态页面
     */
    public String readStatus() {

        PrintWriter out = null;
        try {
            out = this.getResponse().getWriter();
            boolean fromEmailBody = StringUtils.startsWith(this.from, "emailBody");
            List<EmailTo> emailToList = emailService.findAllEmailToHasCancelSendByEmailBodyId(this.emailBodyId);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (EmailTo emailTo : emailToList) {
                if (!fromEmailBody && emailTo.getToType().equals(EmailConst.EMAIL_TO_TYPE_SECRET)) {
                    continue;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", emailTo.getId());
                UserInfo toUser = this.userService.findOne(emailTo.getToId());
                if (null == toUser){
                    continue;
                }
                String toName = toUser.getUserName();
                if (emailTo.getToType().equals(EmailConst.EMAIL_TO_TYPE_SECRET)) {
                    toName += "(密送)";
                }
                map.put("toName", toName);

                map.put("toUserId", toUser.getUserId());
                // 所取所在部门名称
                GroupInfo groupInfo = groupService.findOne(toUser.getGroupId());
                String groupName = groupInfo != null ? groupInfo.getGroupName()
                        : "";
                map.put("groupName", groupName);
                //获取角色名称
                List<RoleInfo> roleList = roleService.getRoleByUser(toUser.getUserId());
                StringBuffer roleName = new StringBuffer();
                for (RoleInfo roleInfo : roleList) {
                    roleName.append(roleInfo.getRoleName() + EmailConst.EMAIL_TO_SEPARATOR);
                }
                String rName = StringUtils.removeEnd(roleName.toString(), EmailConst.EMAIL_TO_SEPARATOR);
                map.put("roleName", rName);
                int readStatus = emailTo.getReadStatus();
                if (emailTo.getEmailToStatus().equals(EmailConst.EMAIL_CANCEL_SEND)) {
                    readStatus = 2;
                }
                //0 未读、1已读、2已取消
                map.put("readStatus", readStatus);

                boolean canTakeBack = false;
                if (fromEmailBody && readStatus == 0) {
                    canTakeBack = true;
                }
                map.put("canTakeBack", canTakeBack);
               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                map.put("readTime", emailTo.getReadTime() == null ? "" : format.format(emailTo.getReadTime()));
                list.add(map);
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", list);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(jsonMap);
            out.print(jsonStr);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 邮件收回 ajax
     *
     * @return ajax text
     */
    public String emailToTakeBack() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.emailToIds)) {
                result = "没有选中任何邮件";
            } else {
                String[] emailToIdArray = this.emailToIds.split(EmailConst.EMAIL_TO_SEPARATOR);
                int total = emailToIdArray.length;
                int count = 0;
                for (String id : emailToIdArray) {
                    if (this.emailService.updateEmailToForTakeBack(Integer.parseInt(id))) {
                        count++;
                    }
                }
                result = "收回" + count + "封邮件成功";
                if (total > count) {
                    result += ", " + (total - count) + " 封邮件由于收件人已经读取，无法收回";
                }
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 功能：根据邮箱Id获取邮箱内邮件数量
     */
    public String getBoxEmailCount() {
        PrintWriter out = null;
        try {
            // 初始化输出流
            out = this.getResponse().getWriter();
            int userId = getLoginUser().getUserId();

            HashMap<String, Integer> jsonMap = new HashMap<String, Integer>();
            // 查出私人邮件箱列表
            List<EmailBox> emailBoxList = emailBoxService.findAllDiyBoxByUserId(userId);
            //查询出私人邮件箱中的邮件份数
            for (EmailBox emailBox : emailBoxList) {
                int emailCount = this.emailService.countOfEmailToByBoxId(emailBox.getUserId(), emailBox.getId());
//                HashMap<String, String> diyBoxMap = new HashMap<String, String>();
                jsonMap.put(String.valueOf(emailBox.getId()), emailCount);
            }
            EmailBox inBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(userId, EmailConst.EMAIL_BOX_TYPE_INBOX);
            int inBoxEmailCount = this.emailService.countOfEmailToByBoxId(userId, inBox.getId());
            jsonMap.put("inBox", inBoxEmailCount);
            //发件箱
            int outBoxEmailCount = this.emailService.countOfEmailBodyByBoxId(userId, EmailConst.EMAIL_SENDED);
            jsonMap.put("outBox", outBoxEmailCount);
            //草稿篓
            int draftBoxEmailCount = this.emailService.countOfEmailBodyByBoxId(userId, EmailConst.EMAIL_NOT_SEND);
            jsonMap.put("draftBox", draftBoxEmailCount);
            //废纸篓箱
            EmailBox wastebasketBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(userId, EmailConst.EMAIL_BOX_TYPE_WASTEBASKET);
            int wastebasketBoxEmailCount = this.emailService.countOfEmailToByBoxId(userId, wastebasketBox.getId());
            jsonMap.put("wastebasketBox", wastebasketBoxEmailCount);

            //未读邮件
            //8.3日修改为查询收件箱未读邮件
//            jsonMap.put("notRead", this.emailService.countOfNotReadEmail(userId));
            jsonMap.put("notRead", this.emailService.countOfNotReadInBox(userId,inBox.getId()));

            String jsonStr = new Gson().toJson(jsonMap);
            //System.out.println(jsonStr);
            out.print(jsonStr);
            out.flush();
        } catch (Exception e) {
            logger.error("getBoxEmailNum error. ", e);
        } finally {
            if (null != out) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 功能：根据邮箱Id获取邮箱内邮件数量
     */
    public String loadDiyEmailBoxList() {
        PrintWriter out = null;
        try {
            // 初始化输出流
            out = this.getResponse().getWriter();
            int userId = getLoginUser().getUserId();
            // 查出私人邮件箱列表
            List<EmailBox> emailBoxList = emailBoxService.findAllDiyBoxByUserId(userId);
            List<Map<String, Object>> listJson = new ArrayList<Map<String, Object>>();
            //查询出私人邮件箱中的邮件份数
            for (EmailBox emailBox : emailBoxList) {
                int emailCount = this.emailService.countOfEmailToByBoxId(emailBox.getUserId(), emailBox.getId());
                emailBox.setEmailCount(emailCount);
                listJson.add(super.getEmailBoxAjaxMap(emailBox));
            }
            String jsonStr = new Gson().toJson(listJson);
            //System.out.println(jsonStr);
            out.print(jsonStr);
            out.flush();
        } catch (Exception e) {
            logger.error("loadDiyEmailBoxList error. ", e);
        } finally {
            if (null != out) {
                out.close();
            }
        }
        return null;
    }

    /**
     * 
     * 功能：聊天室特殊发送邮件,不区分群组和用户
     */
    public void chatSendEmailByIdStr()
    {
        try
        {
            HttpServletRequest request = this.getRequest();
            String ctxPath = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + ctxPath + "/";
            
            if (idStr.startsWith("gid_"))
            {
                this.getResponse().sendRedirect(basePath + "logined/email/sendEmailByGroupId.action?hideBtn=returnBtn&groupId="+idStr.substring(4, idStr.length()) 
                        + "&onLine=" + this.onLine);
            }else if(idStr.startsWith("uid_")){
                int uid = Integer.parseInt(idStr.substring(4, idStr.length()));
                this.getResponse().sendRedirect(basePath + "logined/email/sendEmailByUserId.action?hideBtn=returnBtn&userId=" + uid);
            }
        }
        catch (Exception e)
        {
            logger.error("chatSendEmailByIdStr error", e);
        }
    }
    
    /**
     * 获得上一封 下一封邮件的title
     * @param type 1 以emailToId排列  2 以emailBodyId排列
     * @return
     */
    private void getPrevNextEmail(String emailListJson,int type){
    	if(emailListJson==null||"".equals(emailListJson)||"null".equals(emailListJson)){
    		return;
    	}
    	Gson json = new Gson();
    	List<Map<String,Object>> emailList = json.fromJson(emailListJson, new TypeToken<List<Map<String,Object>>>() {}.getType());
    	for(int i=0;i<emailList.size();i++){
    		Map<String,Object> map = emailList.get(i);
    		int emailToIdSession = Integer.valueOf(map.get("emailToId").toString());
    		int emailBodyIdSession = Integer.valueOf(map.get("emailBodyId").toString());
    		if(type==1){//判断邮箱内邮件id是否相同
    			if(emailToIdSession==emailToId){
    				if(i==0){
    					prevFlag = 0;//已经是第一封邮件
    					prevTitle = "已经是第一封邮件";
    				}else{
    					prevFlag = 1;//不是第一封邮件
    					int m = i-1;
    					Map<String,Object> prevMap = emailList.get(m);
    					int prevToId = Integer.valueOf(prevMap.get("emailToId").toString());
    					int prevBodyId = Integer.valueOf(prevMap.get("emailBodyId").toString());
    					EmailTo prevEmailTo = emailService.findEmailToById(prevToId);
    					prevTitle = "上一封："+prevEmailTo.getEmailBody().getSubject();
    					prevEmailToId = prevToId;
    					prevEmailBodyId = prevBodyId;
    				}
    				
    				if(i==emailList.size()-1){
    					nextFlag = 0;//已经是最后一封邮件
    					nextTitle = "已经是最后一封邮件";
    				}else{
    					nextFlag = 1;//不是最后一封邮件
    					int n = i+1;
    					Map<String,Object> nextMap = emailList.get(n);
    					int nextToId = Integer.valueOf(nextMap.get("emailToId").toString());
    					int nextBodyId = Integer.valueOf(nextMap.get("emailBodyId").toString());
    					EmailTo nextEmailTo = emailService.findEmailToById(nextToId);
    					nextTitle = "下一封："+nextEmailTo.getEmailBody().getSubject();
    					nextEmailToId = nextToId;
    					nextEmailBodyId = nextBodyId;
    				}
    			}else{
    				continue;
    			}
    		}else if(type == 2){//判断邮箱内邮件主体id是否相同
    			if(emailBodyIdSession==emailBodyId){
    				if(i==0){
    					prevFlag = 0;//已经是第一封邮件
    					prevTitle = "已经是第一封邮件";
    				}else{
    					prevFlag = 1;//不是第一封邮件
    					int m = i-1;
    					Map<String,Object> prevMap = emailList.get(m);
    					int prevBodyId = Integer.valueOf(prevMap.get("emailBodyId").toString());
    					EmailBody prevEmailBody = emailService.findOne(prevBodyId);
    					prevTitle = "上一封："+prevEmailBody.getSubject();
    					prevEmailBodyId = prevBodyId;
    				}
    				
    				if(i==emailList.size()-1){
    					nextFlag = 0;//已经是最后一封邮件
    					nextTitle = "已经是最后一封邮件";
    				}else{
    					nextFlag = 1;//不是最后一封邮件
    					int n = i+1;
    					Map<String,Object> nextMap = emailList.get(n);
    					int nextBodyId = Integer.valueOf(nextMap.get("emailBodyId").toString());
    					EmailBody nextEmailBody = emailService.findOne(nextBodyId);
    					nextTitle = "下一封："+nextEmailBody.getSubject();
    					nextEmailBodyId = nextBodyId;
    				}
    			}else{
    				continue;
    			}
    		}else{
    			break;
    		}
    	}
    }
    
    /**
     * 功能：获得收件箱未读数量
     * @return
     */
    public void getNotReadCount(){
//        EmailBox inBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(this.getLoginUser().getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX);
        int notReadCount = this.emailService.countOfNotReadInBox(this.getLoginUser().getUserId(),targetBoxId);
        ajax(notReadCount);
    }
    
    public int getEmailBodyId() {
        return emailBodyId;
    }

    public void setEmailBodyId(int emailBodyId) {
        this.emailBodyId = emailBodyId;
    }

    public EmailBody getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(EmailBody emailBody) {
        this.emailBody = emailBody;
    }

    public int getSend() {
        return send;
    }

    public void setSend(int send) {
        this.send = send;
    }

    public String getEmailToIds() {
        return emailToIds;
    }

    public void setEmailToIds(String emailToIds) {
        this.emailToIds = emailToIds;
    }

    public int getEmailToId() {
        return emailToId;
    }

    public void setEmailToId(int emailToId) {
        this.emailToId = emailToId;
    }

    public int getTargetBoxId() {
        return targetBoxId;
    }

    public void setTargetBoxId(int targetBoxId) {
        this.targetBoxId = targetBoxId;
    }

    public int getReplyAll() {
        return replyAll;
    }

    public void setReplyAll(int replyAll) {
        this.replyAll = replyAll;
    }

    public String getEmailBodyIds() {
        return emailBodyIds;
    }

    public void setEmailBodyIds(String emailBodyIds) {
        this.emailBodyIds = emailBodyIds;
    }

    public int getMarkLevel() {
        return markLevel;
    }

    public void setMarkLevel(int markLevel) {
        this.markLevel = markLevel;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer[] getBoxIds() {
        return boxIds;
    }

    public void setBoxIds(Integer[] boxIds) {
        this.boxIds = boxIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHideBtn() {
        return hideBtn;
    }

    public void setHideBtn(String hideBtn) {
        this.hideBtn = hideBtn;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    public String getIdStr()
    {
        return idStr;
    }

    public void setIdStr(String idStr)
    {
        this.idStr = idStr;
    }

    public String getOnLine()
    {
        return onLine;
    }

    public void setOnLine(String onLine)
    {
        this.onLine = onLine;
    }

	public int getPrevFlag() {
		return prevFlag;
	}

	public void setPrevFlag(int prevFlag) {
		this.prevFlag = prevFlag;
	}

	public int getNextFlag() {
		return nextFlag;
	}

	public void setNextFlag(int nextFlag) {
		this.nextFlag = nextFlag;
	}


	public String getPrevTitle() {
		return prevTitle;
	}

	public void setPrevTitle(String prevTitle) {
		this.prevTitle = prevTitle;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public int getPrevEmailToId() {
		return prevEmailToId;
	}

	public void setPrevEmailToId(int prevEmailToId) {
		this.prevEmailToId = prevEmailToId;
	}

	public int getPrevEmailBodyId() {
		return prevEmailBodyId;
	}

	public void setPrevEmailBodyId(int prevEmailBodyId) {
		this.prevEmailBodyId = prevEmailBodyId;
	}

	public int getNextEmailToId() {
		return nextEmailToId;
	}

	public void setNextEmailToId(int nextEmailToId) {
		this.nextEmailToId = nextEmailToId;
	}

	public int getNextEmailBodyId() {
		return nextEmailBodyId;
	}

	public void setNextEmailBodyId(int nextEmailBodyId) {
		this.nextEmailBodyId = nextEmailBodyId;
	}
    
    
}
