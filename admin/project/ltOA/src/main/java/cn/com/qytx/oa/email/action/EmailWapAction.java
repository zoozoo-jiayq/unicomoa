/**
 *
 */
package cn.com.qytx.oa.email.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.util.CommonUtils;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能:移动端处理，邮件处理的Controller，包含收件箱、已发送、废纸篓、草稿箱等模块的List列表相关Action和邮件主页面Action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-27
 * 修改日期: 2013-04-01
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class EmailWapAction extends EmailBaseAction {

    private static final long serialVersionUID = -4716297123980095126L;
    /**
     * log4j日志对象
     */
    private transient  Logger logger = Logger.getLogger(EmailWapAction.class);


    /**
     * 获取收件箱列表，为移动设备页面准备
     *
     * @return json数据
     */
    public String getInBoxList() {

        try {
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            // 检查邮件箱id
            Page<EmailTo> page = null;
            Sort sort = new Sort(new Sort.Order(Direction.ASC, "readStatus"),new Sort.Order(Direction.DESC, "createTime"));
            page = this.emailService.findAllInBoxEmailsForMobile(this.getPageable(sort), adminUser.getUserId());

            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            if (page.getContent() != null) {
                for (EmailTo emailTo : page.getContent()) {
                    Map<String, Object> toInfoMap = new HashMap<String, Object>();
                    UserInfo fromUser = emailTo.getCreateUserInfo();
                    EmailBody emailBody = emailTo.getEmailBody();
                    toInfoMap.put("id", emailTo.getId());
                    toInfoMap.put("readStatus", emailTo.getReadStatus());
                    toInfoMap.put("fromUserName", fromUser.getUserName());
                    toInfoMap.put("subject", getSubjectOrNo(emailBody));
                    toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody.getCreateTime()));
                    toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody.getCreateTime()));
                    toInfoMap.put("hasAttachment", StringUtils.isNotEmpty(emailBody.getAttachment()));
                    aaDataList.add(toInfoMap);
                }
            }
           this.ajaxPage(page, aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } 
        return null;
    }

    /**
     * 获取发件箱列表，为移动设备页面准备
     *
     * @return json数据
     */
    public String getOutBoxList() {

        try {
            UserInfo adminUser = (UserInfo) getSession().getAttribute(
                    "adminUser");
            // 检查邮件箱id
            Page<EmailBody> page = null;
            page = this.emailService.findAllSendedEmailBodyByUserId(this.getPageable(), adminUser.getUserId(),"");
            List<Map<String, Object>> aaDataList = new ArrayList<Map<String, Object>>();
            if (page.getContent() != null) {
                for (EmailBody emailBody : page.getContent()) {
                    Map<String, Object> toInfoMap = new HashMap<String, Object>();
                    toInfoMap.put("id", emailBody.getId());
                    toInfoMap.put("toName", CommonUtils.getShortStr(getNoSepStr(emailBody.getToName()), 10));
                    toInfoMap.put("subject", getSubjectOrNo(emailBody));
                    toInfoMap.put("hasAttachment", StringUtils.isNotEmpty(emailBody.getAttachment()));
                    toInfoMap.put("shortTime", DateUtils.dateToStrForDynamic(emailBody
                            .getCreateTime()));
                    toInfoMap.put("longTime", DateUtils.date2LongStr(emailBody
                            .getCreateTime()));
                    aaDataList.add(toInfoMap);
                }
            }
            this.ajaxPage(page, aaDataList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
