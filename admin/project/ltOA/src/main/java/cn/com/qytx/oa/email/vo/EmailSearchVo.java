package cn.com.qytx.oa.email.vo;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

/**
 * 功能:邮件搜索VO类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-04-01
 * 修改日期: 2013-04-01
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class EmailSearchVo implements Serializable{

    private Integer emailBoxId;
    private Integer readStatus;
    private Integer markLevel;
    private String timeStart;
    private String timeEnd;
    private String userIds;
    private String userNames;
    private String subject;
    private ArrayList<String> contentInfoList;
    private String contentInfo1;
    private String contentInfo2;
    private String contentInfo3;
    private String attachmentName;


    /**
     * 转成json字符串
     *
     * @return json字符串
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * 由json字符串合成对象
     *
     * @param json json字符串
     * @return 实例对象
     */
    public static EmailSearchVo getInstanceFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, EmailSearchVo.class);
    }

    public Integer getEmailBoxId() {
        return emailBoxId;
    }

    public void setEmailBoxId(Integer emailBoxId) {
        this.emailBoxId = emailBoxId;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getMarkLevel() {
        return markLevel;
    }

    public void setMarkLevel(Integer markLevel) {
        this.markLevel = markLevel;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContentInfo1() {
        return contentInfo1;
    }

    public void setContentInfo1(String contentInfo1) {
        this.contentInfo1 = contentInfo1;
    }

    public String getContentInfo2() {
        return contentInfo2;
    }

    public void setContentInfo2(String contentInfo2) {
        this.contentInfo2 = contentInfo2;
    }

    public String getContentInfo3() {
        return contentInfo3;
    }

    public void setContentInfo3(String contentInfo3) {
        this.contentInfo3 = contentInfo3;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public ArrayList<String> getContentInfoList() {
        ArrayList<String> list = new ArrayList<String>();
        if (StringUtils.isNotEmpty(this.contentInfo1)) {
            list.add(this.contentInfo1);
        }
        if (StringUtils.isNotEmpty(this.contentInfo2)) {
            list.add(this.contentInfo2);
        }
        if (StringUtils.isNotEmpty(this.contentInfo3)) {
            list.add(this.contentInfo3);
        }
        return list;
    }
}
