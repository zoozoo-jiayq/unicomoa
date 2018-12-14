/**
 *
 */
package cn.com.qytx.oa.email.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.service.IEmail;
import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.oa.util.CommonUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.DateUtils;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;

/**
 * 功能:邮件处理所有Controller的基类，包含一些公共方法、功能属性、服务等
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-27
 * 修改日期: 2013-03-27
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class EmailBaseAction extends BaseActionSupport {

    private static final long serialVersionUID = -4716297123980095126L;
    protected static final int NOT_READ_BOX_ID = -1;
    protected static final int DEFAULT_PAGE_MAX = 15;
    protected static final String NOT_EXIST = "notExist";
    /**
     * log4j日志对象
     */
    private transient  Logger logger = Logger.getLogger(EmailBaseAction.class);

    /**
     * 注入的邮件服务
     */
    @Autowired
    protected IEmail emailService;
    /**
     * 注入的邮件箱服务
     */
    @Autowired
    protected IEmailBox emailBoxService;
    /**
     * 注入的分组信息服务
     */
    @Autowired
    protected IGroup groupService;
    /**
     * 注入的用户信息服务
     */
    @Autowired
    protected IUser userService;

    @Autowired
    IRole roleService;

    /**
     * 错误消息
     */
    protected String errorMsg;
    

    public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
     * 检查邮件箱ID是否合法，是否属于当前用户。。。不符合则加载该用户默认收件箱
     *
     * @param emailBoxId 邮件箱ID
     * @param adminUser  登录用户
     * @return 合法的邮件箱
     */
    protected EmailBox checkEmailBoxId(int emailBoxId, UserInfo adminUser) {
        EmailBox emailBox = null;
        // 校验邮件箱是否存在，是否属于当前用户，否则加载当前用户默认邮件箱或者报错
        boolean needDefaultBox = false;
        if (emailBoxId == 0) {
            needDefaultBox = true;
        } else {
            emailBox = emailBoxService.findById(emailBoxId);
            if (emailBox == null) {
                needDefaultBox = true;
            } else if (!emailBox.getUserId().equals(adminUser.getUserId())) {
                needDefaultBox = true;
            } else {
                needDefaultBox = false;
            }
        }
        if (needDefaultBox) {
            emailBox = emailBoxService.findDefaultBoxByUserIdAndBoxType(
                    adminUser.getUserId(), EmailConst.EMAIL_BOX_TYPE_INBOX);
        }
        return emailBox;
    }

    /**
     * 查询一封邮件的所有收件人是否均未读取
     *
     * @param emailBody 邮件体
     * @return 若均未读取 返回true，否则返回false
     */
    protected boolean allNotRead(EmailBody emailBody) {
        for (UserInfo userInfo : getToUsersFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_TO)) {
            if (this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(), userInfo.getUserId()) == EmailConst.EMAIL_READED) {
                return false;
            }
        }
        for (UserInfo userInfo : getToUsersFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_COPY)) {
            if (this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(), userInfo.getUserId()) == EmailConst.EMAIL_READED) {
                return false;
            }
        }
        for (UserInfo userInfo : getToUsersFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_SECRET)) {
            if (this.emailService.getReadStatusByEmailBodyIdAndUserId(emailBody.getId(), userInfo.getUserId()) == EmailConst.EMAIL_READED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 获取用户某个默认邮件箱
     *
     * @param boxType 默认邮件箱类型
     * @return 指定类型的默认邮件箱
     */
    protected EmailBox getDefaultEmailBox(int boxType) {
        UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");
        return this.emailBoxService.findDefaultBoxByUserIdAndBoxType(adminUser
                .getUserId(), boxType);
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户对象
     */
    protected UserInfo getLoginUser() {
        return (UserInfo) getSession().getAttribute("adminUser");
    }

    /**
     * 收件箱信息转成List<Map>形式供给导出Excel使用
     *
     * @param emailToList 收件箱信息列表
     * @return 转换后的List<Map>结果
     */
    protected List<Map<String, Object>> emailToList2ListMap(List<EmailTo> emailToList) {

        List<Map<String, Object>> listMap = new LinkedList<Map<String, Object>>();
        int count = 0;
        for (EmailTo emailTo : emailToList) {
            Map<String, Object> toInfoMap = new HashMap<String, Object>();
            UserInfo fromUser = emailTo.getCreateUserInfo();
            EmailBody emailBody = emailTo.getEmailBody();

            toInfoMap.put("fromUserName", fromUser.getUserName());
            toInfoMap.put("toNames", getToUsersNameFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_TO));
            toInfoMap.put("copyNames", getToUsersNameFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_COPY));
//            toInfoMap.put("markLevel", emailTo.getMarkLevel());
            toInfoMap.put("readStatus", emailTo.getReadStatus().equals(EmailConst.EMAIL_NOT_READ) ? "未读" : "已读");
            toInfoMap.put("subject", emailBody.getSubject());
            toInfoMap.put("contentInfo", getHtmlText(emailBody.getContentInfo()));
            toInfoMap.put("longTime", DateUtils.date2LongStr(emailTo
                    .getCreateTime()));
            toInfoMap.put("attachmentNames", getAttachmentNames(emailBody.getAttachment()));
            listMap.add(toInfoMap);
        }
        return listMap;
    }

    public String getHtmlText(String html){
        if(html!=null){
            return html.replaceAll("<.*?>","").replaceAll("&nbsp;","");
        }else{
            return null;
        }
    }

    /**
     * 获取附件名称集合字符串
     *
     * @param attachment 附件JSON串
     * @return 名称字符串
     */
    private String getAttachmentNames(String attachment) {

        if (StringUtils.isEmpty(attachment)) {
            return "";
        }
        StringBuilder namesSB = new StringBuilder("");
        Gson gson = new Gson();
        List jsonList = gson.fromJson(attachment, List.class);
        for (Object obj : jsonList) {
            if (obj != null && obj instanceof StringMap) {
                namesSB.append(",");
                namesSB.append(((StringMap) obj).get("name"));
            }
        }
        String names = namesSB.toString();
        if (names.length() > 1) {
            return names.substring(1);
        } else {
            return "";
        }
    }

    /**
     * 发件箱信息转成List<Map>形式供给导出Excel使用
     *
     * @param emailBodyList 收件箱信息列表
     * @return 转换后的List<Map>结果
     */
    protected List<Map<String, Object>> emailBodyList2ListMap(List<EmailBody> emailBodyList) {

        List<Map<String, Object>> listMap = new LinkedList<Map<String, Object>>();
        for (EmailBody emailBody : emailBodyList) {
            Map<String, Object> toInfoMap = new HashMap<String, Object>();
            // 收件人==收件人+密送人
            UserInfo fromUser = emailBody.getCreateUserInfo();
            toInfoMap.put("fromUserName", fromUser.getUserName());
            toInfoMap.put("toNames", getToUsersNameFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_TO));
            toInfoMap.put("copyNames", getToUsersNameFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_COPY));
            toInfoMap.put("secretNames", getToUsersNameFromEmailBody(emailBody, EmailConst.EMAIL_TO_TYPE_SECRET));
            toInfoMap.put("subject", emailBody.getSubject());
            toInfoMap.put("contentInfo", getHtmlText(emailBody.getContentInfo()));
            toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                    .getCreateTime()));
            toInfoMap.put("attachmentNames", getAttachmentNames(emailBody.getAttachment()));
            listMap.add(toInfoMap);
        }
        return listMap;
    }

    /**
     * 发件箱导出的Excel头信息
     *
     * @return 头的Value和Key的List
     */
    protected LinkedHashMap<String, String> getExcelHeadValueKeyListEmailBody() {
        LinkedHashMap<String, String> headKeyValueList = new LinkedHashMap<String, String>();
        headKeyValueList.put("fromUserName", "发件人");
        headKeyValueList.put("toNames", "收件人");
        headKeyValueList.put("copyNames", "抄送人");
        headKeyValueList.put("secretNames", "密送人");
        headKeyValueList.put("subject", "主题");
        headKeyValueList.put("contentInfo", "内容");
        headKeyValueList.put("longTime", "发送时间");
        headKeyValueList.put("attachmentNames", "附件名称");
        return headKeyValueList;
    }

    /**
     * 收件箱导出的Excel头信息
     *
     * @return 头的Value和Key的List
     */
    protected LinkedHashMap<String, String> getExcelHeadValueKeyListEmailTo() {
        LinkedHashMap<String, String> headKeyValueList = new LinkedHashMap<String, String>();
        headKeyValueList.put("fromUserName", "发件人");
        headKeyValueList.put("toNames", "收件人");
        headKeyValueList.put("copyNames", "抄送人");
//        headKeyValueList.put("markLevel", "重要级别");
        headKeyValueList.put("readStatus", "读取状态");
        headKeyValueList.put("subject", "主题");
        headKeyValueList.put("contentInfo", "内容");
        headKeyValueList.put("longTime", "发送时间");
        headKeyValueList.put("attachmentNames", "附件名称");
        return headKeyValueList;
    }

    /**
     * 从邮件体中获取默认的邮件内容信息的JSON形式Map，供给回复、转发时使用
     *
     * @param emailBody 邮件体
     * @return 包含有收发件人、发送时间、主题的Map
     */
    protected String getDefaultContentJSONMap(EmailBody emailBody) {
        cleanSep(emailBody);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("fromName", StringUtils.defaultString(emailBody.getCreateUserInfo().getUserName()));
        map.put("toName", StringUtils.defaultString(emailBody.getToName()));
        map.put("copyToName", StringUtils.defaultString(emailBody.getCopyToName()));
        map.put("sendTime", DateUtils.date2LongStr(emailBody.getSendTime()));
        map.put("subject", StringUtils.defaultString(emailBody.getSubject()));
        map.put("contentInfo", StringUtils.defaultString(emailBody.getContentInfo()));
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 获取前后没有逗号的收件人ID或者Name集合
     *
     * @param str
     * @return str
     */
    protected String getNoSepStr(String str) {
        if (StringUtils.isNotEmpty(str)) {
            if (str.startsWith(EmailConst.EMAIL_TO_SEPARATOR)) {
                str = str.substring(1);
            }
            if (str.endsWith(EmailConst.EMAIL_TO_SEPARATOR)) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    /**
     * 清楚一个邮件体对象中的收件人字符串中的 前后逗号
     *
     * @param emailBody 邮件体
     * @return 删除了前后逗号的邮件体对象
     */
    protected EmailBody cleanSep(EmailBody emailBody) {

        emailBody.setToId(getNoSepStr(emailBody.getToId()));
        emailBody.setToName(getNoSepStr(emailBody.getToName()));

        emailBody.setCopyToId(getNoSepStr(emailBody.getCopyToId()));
        emailBody.setCopyToName(getNoSepStr(emailBody.getCopyToName()));

        emailBody.setSecretToId(getNoSepStr(emailBody.getSecretToId()));
        emailBody.setSecretToName(getNoSepStr(emailBody.getSecretToName()));

        return emailBody;
    }

    /**
     * 获取收件人数量：包括收件人和密送人
     *
     * @param emailBody 邮件体对象
     * @return 收件人和密送人数量
     */
    protected int getToUsersCountFromEmailBody(EmailBody emailBody) {

        int count = 0;
        if (!StringUtils.isEmpty(emailBody.getToId())) {
            count += getNoSepStr(emailBody.getToId()).split(EmailConst.EMAIL_TO_SEPARATOR).length;
        }
        if (!StringUtils.isEmpty(emailBody.getSecretToId())) {
            count += getNoSepStr(emailBody.getSecretToId()).split(
                    EmailConst.EMAIL_TO_SEPARATOR).length;
        }
        return count;
    }

    /**
     * 从一封邮件体中的收件人字符串中获取一封邮件体中的某种类型的接收人列表
     *
     * @param emailBody 邮件体
     * @param toType    接收人类型
     * @return 接收人列表
     */
    protected List<UserInfo> getToUsersFromEmailBody(EmailBody emailBody,
                                                          int toType) {

        List<UserInfo> userList = new ArrayList<UserInfo>();
//        String[] toIds = null;
        String toIds = "";
        String separator = EmailConst.EMAIL_TO_SEPARATOR;
        if (toType == EmailConst.EMAIL_TO_TYPE_TO) {
            toIds = StringUtils.isEmpty(emailBody.getToId()) ? "" : getNoSepStr(emailBody
                    .getToId());
        } else if (toType == EmailConst.EMAIL_TO_TYPE_COPY) {
            toIds = StringUtils.isEmpty(emailBody.getCopyToId()) ? ""
                    : getNoSepStr(emailBody.getCopyToId());
        } else if (toType == EmailConst.EMAIL_TO_TYPE_SECRET) {
            toIds = StringUtils.isEmpty(emailBody.getSecretToId()) ? ""
                    : getNoSepStr(emailBody.getSecretToId());
        }
        if (StringUtils.isNotBlank(toIds)) {
        	userList = userService.findUsersByIds(toIds);
//            for (String toId : toIds) {
//                UserInfo user = this.userService.findOne(Integer.parseInt(toId));
//                if (user != null) {
//                    userList.add(user);
//                }
//            }
        }
        return userList;
    }

    /**
     * 获取某个邮件体中某种发件人的姓名列表字符串，逗号分割
     *
     * @param emailBody 邮件体对象
     * @param toType    收件人类型
     * @return 指定收件人类型的收件人姓名集合字符串
     */
    protected String getToUsersNameFromEmailBody(EmailBody emailBody, int toType) {
        StringBuffer sb = new StringBuffer("");
        for (UserInfo userInfo : getToUsersFromEmailBody(emailBody, toType)) {
            sb.append(userInfo.getUserName());
            sb.append(EmailConst.EMAIL_TO_SEPARATOR);
        }
        return StringUtils.removeEnd(sb.toString(),
                EmailConst.EMAIL_TO_SEPARATOR);

    }

    /**
     * 获取邮件主题
     *
     * @param emailBody 邮件体
     * @return 邮件主题
     */
    protected String getSubjectOrNo(EmailBody emailBody) {
        if (StringUtils.isEmpty(emailBody.getSubject())) {
            return "[无主题]";
        } else {
            return emailBody.getSubject();
        }
    }

    /**
     * 获取邮件的大小
     *
     * @param emailBody 邮件体
     * @return 邮件大小
     */
    protected int getEmailSize(EmailBody emailBody) {
        int size = 0;
        if (emailBody.getAttachmentSize() != null) {
            size += emailBody.getAttachmentSize();
        }
        if (StringUtils.isNotEmpty(emailBody.getContentInfo())) {
            size += emailBody.getContentInfo().getBytes().length;
        }
        return size;
    }

    /**
     * 邮件箱信息转入Map给供给Ajax数据用
     * @param emailBox 邮件箱
     * @return map
     */
    protected HashMap<String,Object> getEmailBoxAjaxMap(EmailBox emailBox){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("id",emailBox.getId());
        map.put("boxName",StringUtils.defaultString(emailBox.getBoxName()));
        map.put("emailCount", CommonUtils.obj2Str(emailBox.getEmailCount()));
        return map;
    }

}
