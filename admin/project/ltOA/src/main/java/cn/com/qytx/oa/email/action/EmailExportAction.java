/**
 *
 */
package cn.com.qytx.oa.email.action;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.platform.utils.DateUtils;

/**
 * 功能:邮件处理的Controller，包含导出Excel相关Action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-27
 * 修改日期: 2013-03-27
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class EmailExportAction extends EmailBaseAction {

    private static final long serialVersionUID = -4716297123980095128L;

    /**
     * log4j日志对象
     */
    private transient  Logger logger = Logger.getLogger(EmailExportAction.class);

    /**
     * 收件箱ID集合字符串
     */
    private String emailToIds;

    /**
     * 发件箱ID集合字符串
     */
    private String emailBodyIds;


    /**
     * 收件箱、废纸篓导出
     *
     * @return null ，导出的文件下载
     */
    public String emailToExport() {
        OutputStream outStream = null;
        try {
            outStream = getResponse().getOutputStream();
            if (StringUtils.isEmpty(this.emailToIds)) {
                logger.error("exportEmailTo:没有选中任何邮件");
            } else {
                String[] emailToIdArray = this.emailToIds.split(",");
                LinkedList<EmailTo> emailToList = new LinkedList<EmailTo>();
                for (String id : emailToIdArray) {
                    EmailTo emailTo = this.emailService.findEmailToById(Integer.parseInt(id));
                    emailToList.add(emailTo);
                }
                String fileName = DateUtils.date2ShortStr(null) + "-邮件列表.xls";
                getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
                List<Map<String, Object>> emailToListMap = emailToList2ListMap(emailToList);
                LinkedHashMap<String, String> headKeyValueMap = getExcelHeadValueKeyListEmailTo();
                //System.out.println("导出行数:" + emailToListMap.size());
                ExportExcel exportExcel = new ExportExcel(outStream, emailToListMap, headKeyValueMap);
                exportExcel.export();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 已发送、草稿箱导出
     *
     * @return null 导出的文件下载
     */
    public String emailBodyExport() {
        OutputStream outStream = null;
        try {
            outStream = getResponse().getOutputStream();
            if (StringUtils.isEmpty(this.emailBodyIds)) {
                logger.error("exportEmailTo:没有选中任何邮件");
            } else {
                String[] emailBodyIdArray = this.emailBodyIds.split(",");
                LinkedList<EmailBody> emailBodyList = new LinkedList<EmailBody>();
                for (String id : emailBodyIdArray) {
                    EmailBody emailBody = this.emailService.findOne(Integer.parseInt(id));
                    emailBodyList.add(emailBody);
                }
                String fileName = DateUtils.date2ShortStr(null) + "-邮件列表.xls";
                getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
                List<Map<String, Object>> emailToListMap = emailBodyList2ListMap(emailBodyList);
                LinkedHashMap<String, String> headKeyValueMap = getExcelHeadValueKeyListEmailBody();
                //System.out.println("发件箱导出行数:" + emailToListMap.size());
                ExportExcel exportExcel = new ExportExcel(outStream, emailToListMap, headKeyValueMap);
                exportExcel.export();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String getEmailToIds() {
        return emailToIds;
    }

    public void setEmailToIds(String emailToIds) {
        this.emailToIds = emailToIds;
    }

    public String getEmailBodyIds() {
        return emailBodyIds;
    }

    public void setEmailBodyIds(String emailBodyIds) {
        this.emailBodyIds = emailBodyIds;
    }

}
