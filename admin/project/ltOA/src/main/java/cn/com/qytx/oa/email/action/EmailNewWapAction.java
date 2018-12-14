package cn.com.qytx.oa.email.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.domain.EmailWapIndex;
import cn.com.qytx.oa.email.domain.MailSenderInfo;
import cn.com.qytx.oa.email.domain.MailsInfo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.util.UpdateFieldUtil;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * 功能:手机端新接口功能开发
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-7-31
 * 修改日期: 2013-7-31
 * 修改列表:
 */
public class EmailNewWapAction extends EmailBaseAction
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = -2256516097649134170L;

    private  transient Logger logger = Logger.getLogger(EmailNewWapAction.class);
    
    private int emailId;
    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 邮件ID集合字符串
     */
    private String emailToIds;

    /**
     * 要移动或者删除的目标邮件箱ID
     */
    private int targetBoxId;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 邮件箱类型 1：系统-收件箱 2：系统-发件箱 3：系统-草稿箱 4：系统-废纸篓 5：自定义邮件箱
     */
    private Integer boxType;


    /**
     * 搜索词
     */
    private String searchWord;

    /**
     * 邮件内容 json格式
     */
    private String mailInfo;

    /**
     * 功能：根据人员ID获取对应人员的邮件箱
     */
    public void findBoxList()
    {
        MailSenderInfo mailSenderInfo = new MailSenderInfo();

        // 接受者
        String[] receivers = new String[1];
        receivers[0] = "1";
        mailSenderInfo.setReceivers(receivers);

        // 抄送者
        String[] ccs = new String[1];
        ccs[0] = "1";
        mailSenderInfo.setCcs(ccs);

        // 密送者
        String[] bccs = new String[1];
        ccs[0] = "1";
        mailSenderInfo.setBccs(bccs);

        // 是否存草稿
        String isSend = "0";
        mailSenderInfo.setIsSend(isSend);

        // 邮件标题
        String subject = "手机发送标题";
        mailSenderInfo.setSubject(subject);

        // 邮件内容
        String content = "手机发送内容";
        mailSenderInfo.setContent(content);
        
//        Gson gson1 = new Gson();
//        String jsonStr111 = gson1.toJson(mailSenderInfo);
        
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne(userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            UserInfo loginUser=(UserInfo) getSession().getAttribute("adminUser");
            emailBoxService.saveDefaultAfterCheckExist(loginUser.getUserId(),getLoginUser());
            
            out = this.getResponse().getWriter();
            // 查出私人邮件箱列表
            List<EmailBox> emailBoxList = emailBoxService.findAllByUserId(userId);
            EmailBox inBox = this.emailBoxService.findDefaultBoxByUserIdAndBoxType(userId, EmailConst.EMAIL_BOX_TYPE_INBOX);
            // 查出私人邮件箱列表
            int unread = this.emailService.countOfNotReadInBox(userId,inBox.getId());

            // 组装手机端首页的json对象
            EmailWapIndex ewi = new EmailWapIndex();
            ewi.setBoxList(emailBoxList);
            ewi.setUnRead(unread);

            Gson gson = new Gson();
            String jsonStr = gson.toJson(ewi);
            out.print("100||" + jsonStr);
            out.flush();
        }
        catch (Exception ex)
        {
            logger.error("findBoxList error:", ex);
            out.print("101||1");
            out.flush();
        }
    }

    /**
     * 功能：获取未读邮件
     */
    public void getUnReadEmail()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            out = this.getResponse().getWriter();
            // 查出私人邮件箱列表
            int unread = this.emailService.countOfNotReadEmail(userId);

            out.print("100||" + unread);
            out.flush();
        }
        catch (Exception ex)
        {
            logger.error("getUnReadEmail error:", ex);
            out.print("100||1");
            out.flush();
        }
    }

    /**
     * 功能：删除邮件
     * @return
     */
    public String emailToDelete()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            out = getResponse().getWriter();
            if (!StringUtils.isEmpty(this.emailToIds))
            {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray)
                {
                    if (!StringUtils.isEmpty(id)){
                        if (null != boxType && (boxType == 2 || boxType == 3)){
                            this.emailService.deleteEmailBody(Integer.parseInt(id));
                        }else{
                            this.emailService.deleteEmailTo(Integer.parseInt(id));
                        }
                    }
                }
            }
            
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax标记为已读
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToReaded()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            out = getResponse().getWriter();
            if (!StringUtils.isEmpty(this.emailToIds))
            {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray)
                {
                    if (!StringUtils.isEmpty(id)){
                        this.emailService.readedEmailTo(Integer.parseInt(id));
                    }
                    
                }
            }
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax全部标记为已读
     * @return ajax请求处理，返回Text文本到页面
     */
    public String emailToReadedAll()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne(userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            out = getResponse().getWriter();
            emailService.updateEmailToAllReaded(getLoginUser().getUserId(), this.targetBoxId);
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * ajax 移动或批量移动邮件到指定邮件箱
     * @return ajax请求返回Text信息到页面
     */
    public String emailToMove()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            out = getResponse().getWriter();
            if (!StringUtils.isEmpty(this.emailToIds))
            {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray)
                {
                    if (!StringUtils.isEmpty(id)){
                        this.emailService.moveEmailTo(Integer.parseInt(id), targetBoxId);
                    }
                    
                }
            }
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * 功能：查询邮件内容
     */
    public void findAllMail()
    {
        PrintWriter out = null;
        try
        {
          
            
            out = getResponse().getWriter();
            // 将返回结果封装成MailsInfo的json对象
            List<MailsInfo> mailsInfoList = null;
            Sort sort = new Sort(new Sort.Order(Direction.DESC, "lastUpdateTime"));
            // 查询发件箱的邮件
            if (null != boxType && 2 == boxType.intValue())
            {            
                Page<EmailBody> pageEmailBody = null;
               
                // 设置分页起始页码
                pageEmailBody = emailService.findAllSendedEmailBodyByUserId(this.getPageable(sort), userId,searchWord);                
                mailsInfoList = dealEmailBody(pageEmailBody.getContent());
            }
            else if (null != boxType && 3 == boxType.intValue())
            {
                Page<EmailBody> pageEmailBody = null;
                // 设置分页起始页码
                pageEmailBody = emailService.findAllDraftEmailBodyByUserId(
                        this.getPageable(sort), userId,searchWord);
                
                mailsInfoList = dealEmailBody(pageEmailBody.getContent());
            }
            else if (null != boxType && 4 == boxType.intValue())
            {
                Page<EmailTo> pageEmailBody = null;
                // 设置分页起始页码
                pageEmailBody = emailService.findAllReceiveEmailBySearchWord(this.getPageable(sort),
                        userId, targetBoxId,searchWord);                
                mailsInfoList = dealEmailTo(pageEmailBody.getContent());
            }
            else
            {
                Page<EmailTo> pageEmailTo = null;

                // 设置分页起始页码
                pageEmailTo = emailService.findAllReceiveEmailBySearchWord(this.getPageable(sort), userId,
                        targetBoxId, searchWord);
                
                mailsInfoList = dealEmailTo(pageEmailTo.getContent());
            }

            if (null == mailsInfoList || mailsInfoList.isEmpty())
            {
                out.print("100||0");                
            }
            else
            {
                Gson gson = new Gson();
                String jsonStr = gson.toJson(mailsInfoList);
                out.print("100||" + jsonStr);
                
            }
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("102||1");
            out.flush();
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }

    /**
     * 销毁邮件，可批量销毁
     * @return ajax请求返回Text文本信息到页面
     */
    public String emailToDestroy()
    {

        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne(userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            out = getResponse().getWriter();
            if (!StringUtils.isEmpty(this.emailToIds))
            {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray)
                {
                    if (!StringUtils.isEmpty(id)){
                        this.emailService.destroyEmailTo(Integer.parseInt(id));
                    }
                    
                }
            }
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * 恢复删除的邮件，可批量恢复
     * @return ajax请求，返回Text文本信息到页面
     */
    public String emailToRecovery()
    {

        PrintWriter out = null;
        try
        {
            // 首先获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            out = getResponse().getWriter();
            if (!StringUtils.isEmpty(this.emailToIds))
            {
                String[] emailToIdArray = this.emailToIds.split(",");
                for (String id : emailToIdArray)
                {
                    if (!StringUtils.isEmpty(id)){
                        this.emailService.recoveryEmailTo(Integer.parseInt(id));
                    }
                }
            }
            out.print("100||0");
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("100||1");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
        return null;
    }

    /**
     * 保存邮件并识别是否需要发送
     * @return success：跳转到邮件发送成功页面
     *         error：返回到邮件编辑页面并提示错误信息
     */
    public void save()
    {
        PrintWriter out = null;
        try
        {
            out = getResponse().getWriter();
            Gson gson = new Gson();
            MailSenderInfo mailSenderInfo = gson.fromJson(this.mailInfo, MailSenderInfo.class);

            // 根据用户id获取用户
            UserInfo userInfo = userService.findOne( userId);
            this.getRequest().getSession().setAttribute("adminUser", userInfo);
            
            // 参数转换
            // 接受者
            String[] receivers = mailSenderInfo.getReceivers();
            String[] receiversName = mailSenderInfo.getReceiversName();

            // 抄送者
            String[] ccs = mailSenderInfo.getCcs();
            String[] ccsName = mailSenderInfo.getCcsName();
            
            // 密送者
            String[] bccs = mailSenderInfo.getBccs();
            String[] bccsName = mailSenderInfo.getBccsName();
            
            // 是否存草稿
            String isSend = mailSenderInfo.getIsSend();

            // 邮件标题
            String subject = mailSenderInfo.getSubject();

            // 邮件内容
            String content = mailSenderInfo.getContent();
            
            // 邮件主键Id
            Integer emailId = mailSenderInfo.getEmailId();

            if ("0".equals(isSend) && arrayIsEmpty(receivers) && arrayIsEmpty(ccs)
                    && arrayIsEmpty(bccs))
            {
                out.print("103||发送失败");
                out.flush();
                return;
            }
            else
            {
                // 组装邮件的内容
                EmailBody emailBody = new EmailBody();
                emailBody.setCompanyId(TransportUser.get().getCompanyId());
                // 接收人Id集合
                emailBody.setToId(arrayToString(receivers));

                // 接收人姓名
                emailBody.setToName(arrayToString(receiversName));

                // 抄送人Id集合
                emailBody.setCopyToId(arrayToString(ccs));                

                // 抄送人姓名
                emailBody.setCopyToName(arrayToString(ccsName));
                
                // 密送者Id集合
                emailBody.setSecretToId(arrayToString(bccs));

                // 密送者姓名
                emailBody.setSecretToName(arrayToString(bccsName));

                // 标题
                emailBody.setSubject(subject);

                // 内容
                emailBody.setContentInfo(content);

                // 设置发送状态
                emailBody.setSendStatus(EmailConst.EMAIL_NOT_SEND);

                // 设置附件大小
                emailBody.setAttachmentSize(0L);

                // 设置重要级别
                emailBody.setImportantLevel(EmailConst.EMAIL_IMPORTANT_LEVEL_NORMAL);
                
                // 设置发送人
                emailBody.setCreateUserInfo(userInfo);
                emailBody.setSmsRemind(0);
                emailBody.setNeedReceipt(0);
                emailBody.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                emailBody.setLastUpdateUserId(userId);
                // 是否为草稿箱再次发送
                if (null != emailId)
                {
                    EmailBody emailBodyDB = this.emailService.findOne(emailId);
                    UpdateFieldUtil.update(emailBody, emailBodyDB, "id", "sendStatus");
                    if ("0".equals(isSend)) {
                        this.emailService.updateAndSend(emailBodyDB);
                    } else {
                        this.emailService.update(emailBodyDB);
                    }
                }
                else
                {
                    if ("0".equals(isSend))
                    {
                        this.emailService.saveAndSend(emailBody);
                    }
                    else
                    {
                        this.emailService.saveOrUpdate(emailBody);
                    }
                }
                out.print("100||发送成功");
                out.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            out.print("103||发送失败");
        }
        finally
        {
            if (out != null)
            {
                out.close();
            }
        }
    }

    private boolean arrayIsEmpty(String[] array)
    {
        if (null == array || array.length == 0)
        {
            return true;
        }
        return false;
    }

    private String arrayToString(String[] array)
    {
        if (null == array || array.length == 0)
        {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String temp : array)
        {
            sb.append(temp + ",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    
    private List<MailsInfo> dealEmailBody(List<EmailBody> emailBodyList)
    {
        List<MailsInfo> mailsInfoList = new ArrayList<MailsInfo>();
        MailsInfo mailsInfo;
        for (EmailBody emailBody : emailBodyList)
        {
            mailsInfo = new MailsInfo();

            // 设置邮件id
            mailsInfo.setMessageID(emailBody.getId());

            // 发送人姓名
            UserInfo fromUser = emailBody.getCreateUserInfo();
            String[] from = new String[2];
            from[0] = fromUser.getUserName();            
            
            // 发送人部门
            GroupInfo g = groupService.findOne(fromUser.getGroupId());
            if (null != g )
            {
                from[1] = g.getGroupName().toString();
            }
            mailsInfo.setFrom(from);
            
            // 接收人Id及姓名
            String toId = emailBody.getToId();
            String toName = emailBody.getToName();
            mailsInfo.setReceive(toName);
            mailsInfo.setReceiveId(toId);
            
            // 抄送人Id及姓名
            String copyToName = emailBody.getCopyToName();
            String copyToId = emailBody.getCopyToId();
            mailsInfo.setCcId(copyToId);
            mailsInfo.setCc(copyToName);

            // 密送人Id及姓名
            String secretToName = emailBody.getSecretToName();
            String secretToId = emailBody.getSecretToId();
            mailsInfo.setScId(secretToId);
            mailsInfo.setSc(secretToName);
            
            // 发送时间 SentDate
            Timestamp createTime = emailBody.getCreateTime();
            mailsInfo.setSentDate(DateTimeUtil.dateToString(createTime,
            		CbbConst.TIME_FORMAT_STR));

            // 主题
            String subject = emailBody.getSubject();
            mailsInfo.setSubject(subject);

            // 内容
            String contentInfo = emailBody.getContentInfo();
            mailsInfo.setContent(contentInfo);

            // 是否包含附件
            boolean hasAttachment = StringUtils.isNotEmpty(emailBody.getAttachment());
            if (hasAttachment)
            {
                mailsInfo.setAttachment(0); // 0表示有附件
                mailsInfo.setAttachmentJson(emailBody.getAttachment());
            }
            else
            {
                mailsInfo.setAttachment(1); // 1表示无附件
            }
            
            // 是否已阅读
            // 收件人==收件人+密送人
            int toCount = super.getToUsersCountFromEmailBody(emailBody);
            String toNames = "";
            Integer readStatus = 0;
            if (toCount == 1) {
                // 一个收件人：可能是收件人也可能是密送人
                if (!StringUtils.isEmpty(emailBody.getToId())) {
                    toNames += super.getToUsersNameFromEmailBody(emailBody,
                            EmailConst.EMAIL_TO_TYPE_TO);
                    readStatus = this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(),
                            Integer.parseInt(getNoSepStr(emailBody.getToId())));

                } else if (!StringUtils.isEmpty(emailBody.getSecretToId())) {
                    toNames += super.getToUsersNameFromEmailBody(emailBody,
                            EmailConst.EMAIL_TO_TYPE_SECRET);
                    readStatus = this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(),
                            Integer.parseInt(getNoSepStr(emailBody.getSecretToId())));
                }
            } else {
                toNames = "查看";
                readStatus = -1;//表示为群邮件
                if (allNotRead(emailBody)) {
                    readStatus = -2;//表示为群邮件均未读取、可编辑
                }
            }
            mailsInfo.setReadStatus(readStatus);
            mailsInfoList.add(mailsInfo);
        }
        return mailsInfoList;
    }
    
    private List<MailsInfo> dealEmailTo(List<EmailTo> resultList)
    {
        List<MailsInfo> mailsInfoList = new ArrayList<MailsInfo>();
        MailsInfo mailsInfo;
        // 获取收件箱中邮件
        if (resultList == null || resultList.isEmpty())
        {
            return mailsInfoList;
        }

        for (EmailTo tempEmailTo : resultList)
        {
            mailsInfo = new MailsInfo();

            // 设置邮件id
            mailsInfo.setMessageID(tempEmailTo.getId());

            // 发送人姓名
            UserInfo fromUser = tempEmailTo.getCreateUserInfo();
            String[] from = new String[2];
            from[0] = fromUser.getUserName();
            
            // 设置发送人姓名
            mailsInfo.setUserId(fromUser.getUserId());
            
            // 发送人部门
            GroupInfo g = groupService.findOne(fromUser.getGroupId());
            if (null != g)
            {
                from[1] = g.getGroupName().toString();
            }
            mailsInfo.setFrom(from);
            EmailBody emailBody = tempEmailTo.getEmailBody();

            // 接收人Id及姓名
            String toId = emailBody.getToId();
            String toName = emailBody.getToName();
            mailsInfo.setReceive(toName);
            mailsInfo.setReceiveId(toId);
            
            // 抄送人Id及姓名
            String copyToName = emailBody.getCopyToName();
            String copyToId = emailBody.getCopyToId();
            mailsInfo.setCcId(copyToId);
            mailsInfo.setCc(copyToName);

            // 密送人Id及姓名
            String secretToName = emailBody.getSecretToName();
            String secretToId = emailBody.getSecretToId();
            mailsInfo.setScId(secretToId);
            mailsInfo.setSc(secretToName);

            // 发送时间 SentDate
            Timestamp createTime = emailBody.getCreateTime();
            mailsInfo.setSentDate(DateTimeUtil.dateToString(createTime,
            		CbbConst.TIME_FORMAT_STR));

            // 主题
            String subject = emailBody.getSubject();
            mailsInfo.setSubject(subject);

            // 内容
            String contentInfo = emailBody.getContentInfo();
            mailsInfo.setContent(contentInfo);

            // 是否包含附件
            boolean hasAttachment = StringUtils.isNotEmpty(emailBody.getAttachment());
            if (hasAttachment)
            {
                mailsInfo.setAttachment(0); // 0表示有附件
                
                // 附件信息
                mailsInfo.setAttachmentJson(emailBody.getAttachment());
            }
            else
            {
                mailsInfo.setAttachment(1); // 1表示无附件
            }

            // 是否已读
            Integer readStatus = tempEmailTo.getReadStatus();
            if (null != readStatus && readStatus == 1)
            {
                mailsInfo.setIsNew(0); // 0表示已读
            }
            else
            {
                mailsInfo.setIsNew(1); // 1表示未读
            }
            
            mailsInfoList.add(mailsInfo);
        }
        return mailsInfoList;
    }
    
    /**
     * 获得单个Email对象
     * @return
     */
    public void getEmailByIdForWap(){
    	try{
    		if(boxType!=null&&(boxType==2||boxType==3)){
    			EmailBody emailBody = emailService.findOne(emailId);
    			if(emailBody==null){
    	    		ajax("102||邮件不存在");
    	    		return;
    	    	}
    			List<EmailBody> list  = new ArrayList<EmailBody>();
    	    	list.add(emailBody);
    	    	List<MailsInfo> mailsInfoList = new ArrayList<MailsInfo>();
    	    	mailsInfoList = dealEmailBody(list);
    	    	MailsInfo mailsInfo = mailsInfoList.get(0);
    	    	Gson json = new Gson();
    	    	String jsonStr = json.toJson(mailsInfo);
    	    	ajax("100||"+jsonStr);
    			return;
    		}else{
    			EmailTo emailTo = emailService.findEmailToById(emailId);
    	    	if(emailTo==null){
    	    		ajax("102||邮件不存在");
    	    		return;
    	    	}
    	    	List<EmailTo> list  = new ArrayList<EmailTo>();
    	    	list.add(emailTo);
    	    	List<MailsInfo> mailsInfoList = new ArrayList<MailsInfo>();
    	    	mailsInfoList = dealEmailTo(list);
    	    	MailsInfo mailsInfo = mailsInfoList.get(0);
    	    	Gson json = new Gson();
    	    	String jsonStr = json.toJson(mailsInfo);
    	    	ajax("100||"+jsonStr);
    	    	return;
    		}
    	}catch(Exception e){
    		ajax("102||邮件读取失败");
    	}
    }
    
    
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getEmailToIds()
    {
        return emailToIds;
    }

    public void setEmailToIds(String emailToIds)
    {
        this.emailToIds = emailToIds;
    }

    public int getTargetBoxId()
    {
        return targetBoxId;
    }

    public void setTargetBoxId(int targetBoxId)
    {
        this.targetBoxId = targetBoxId;
    }

    public String getFolderName()
    {
        return folderName;
    }

    public void setFolderName(String folderName)
    {
        this.folderName = folderName;
    }

    public Integer getBoxType()
    {
        return boxType;
    }

    public void setBoxType(Integer boxType)
    {
        this.boxType = boxType;
    }

    public String getSearchWord()
    {
        return searchWord;
    }

    public void setSearchWord(String searchWord)
    {
        this.searchWord = searchWord;
    }

    public String getMailInfo()
    {
        return mailInfo;
    }

    public void setMailInfo(String mailInfo)
    {
        this.mailInfo = mailInfo;
    }

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}
    
}
