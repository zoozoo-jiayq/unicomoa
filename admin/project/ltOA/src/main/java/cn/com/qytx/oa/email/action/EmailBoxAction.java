package cn.com.qytx.oa.email.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.oa.email.service.EmailConst;
import cn.com.qytx.oa.email.service.IEmailBox;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.application.TransportUser;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.gson.Gson;

/**
 * 功能:邮件箱处理Controller，包含增、删、改、查、列表等action
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-22
 * 修改日期: 2013-03-22
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class EmailBoxAction extends BaseActionSupport {

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	/**
     * log4j日志对象
     */
	transient  Logger logger = Logger.getLogger(EmailBoxAction.class);

    /**
     * 注入的邮件箱服务
     */
    @Autowired
    private IEmailBox emailBoxService;
    /**
     * 收件箱对象
     */
    private EmailBox emailBox;

    /**
     * 邮件箱ID
     */
    private int id;

    private int pageMax;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 保存一个新的邮件箱
     *
     * @return null，ajax处理，返回Text数据到页面
     */
    public String save() {
        PrintWriter out = null;
        String result = "";
        UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");
        try {
            out = getResponse().getWriter();
            if (this.emailBox.getOrderNo() != null && this.emailBox.getOrderNo() < 0) {
                result = "排序号不能小于0！";
            } else if (StringUtil.isEmpty(this.emailBox.getBoxName())) {
                result = "邮件箱名称不能为空！";
            } else {
                if (this.emailBox.getPageMax() == 0) {
                    this.emailBox.setPageMax(EmailConst.EMAIL_PAGE_MAX);
                }
                emailBox.setCreateUserId(this.getLoginUser().getUserId());
                emailBox.setCreateUserInfo(this.getLoginUser());
                emailBox.setLastUpdateInfo(this.getLoginUser());
                emailBox.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                emailBox.setLastUpdateUserId(this.getLoginUser().getUserId());
                this.emailBox.setUserId(adminUser.getUserId());
                this.emailBox.setCompanyId(adminUser.getCompanyId());
                this.emailBoxService.saveOrUpdate(this.emailBox);
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
     * 删除一个邮件箱
     *
     * @return null，ajax处理，返回Text信息到页面
     */
    public String delete() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            EmailBox emailBox = this.emailBoxService.findById(this.id);
            if (emailBox == null) {
                result = "您要删除的邮件箱不存在:id" + this.id;
            } else if (emailBox.getBoxType() != 5) {
                result = "不允许删除默认邮件箱:" + emailBox.getBoxName() + ",id:"
                        + emailBox.getId();
            } else {
                this.emailBoxService.delete(id);
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
     * 邮件箱列表
     *
     * @return null，ajax请求处理，返回JSON数据到页面
     */
    public String list() {
        PrintWriter out = null;
        try {
            UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");
            out = this.getResponse().getWriter();
            List<EmailBox> emailBoxList = this.emailBoxService
                    .findAllByUserId(adminUser.getUserId());

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (EmailBox emailBox : emailBoxList) {
                Map<String, Object> boxInfoMap = new HashMap<String, Object>();
                boxInfoMap.put("id", emailBox.getId());
                boxInfoMap.put("orderNo", emailBox.getOrderNo() == null ? "" : emailBox.getOrderNo());
                boxInfoMap.put("boxName", emailBox.getBoxName());
                boxInfoMap.put("boxSize", this.emailBoxService.getBoxSize(emailBox));
                boxInfoMap.put("boxType", emailBox.getBoxType());
                boxInfoMap.put("pageMax", emailBox.getPageMax() == null ? "" : emailBox.getPageMax());
                list.add(boxInfoMap);
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
     * 修改邮件箱的每页显示邮件数
     *
     * @return ajax Text
     */
    public String changPageMax() {

        PrintWriter out = null;
        String result = "";
//        UserInfo adminUser = (UserInfo) getSession().getAttribute("adminUser");

        try {
            EmailBox emailBoxDB = emailBoxService.findById(this.id);
            if (emailBoxDB == null) {
                result = "邮件箱不存在！";
            } else if (this.pageMax <= 0) {
                result = "指定的邮件箱每页显示数量不能小于0！";
            } else {
                emailBoxDB.setPageMax(this.pageMax);
                emailBoxDB.setLastUpdateInfo(this.getLoginUser());
                emailBoxDB.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
                emailBoxDB.setLastUpdateUserId(this.getLoginUser().getUserId());
                emailBoxDB.setCompanyId(TransportUser.get().getCompanyId());
                this.emailBoxService.saveOrUpdate(emailBoxDB);
                result ="保存成功，"+ emailBoxDB.getBoxName() + "的每页显示邮件数为:" + emailBoxDB.getPageMax() + "！";
            }
            out = getResponse().getWriter();
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
     * 修改一个邮件箱
     *
     * @return success，返回到邮件箱修改页面
     */
    public String edit() {

        this.emailBox = this.emailBoxService.findById(this.id);
        if (this.emailBox == null) {
            errorMsg = "查询的邮件箱不存在，id：" + id;
            return ERROR;
        }
        if (this.emailBox.getBoxType() != 5) {
            errorMsg = "不允许修改默认邮件箱:" + this.emailBox.getBoxName() + ",id:"
                    + this.emailBox.getId();
            return ERROR;
        }
        errorMsg = null;
        return SUCCESS;
    }

    /**
     * 邮件箱更新
     *
     * @return success:返回到list页面，error：返回到修改页面并提示错误信息
     */
    public String update() {
        EmailBox emailBoxDB = emailBoxService.findById(this.emailBox.getId());
        errorMsg="";
        if (emailBoxDB == null) {
            errorMsg = "查询的邮件箱不存在，id：" + id;
            return ERROR;
        }
        if (emailBoxDB.getBoxType() != 5) {
            errorMsg = "不允许修改默认邮件箱:" + emailBoxDB.getBoxName() + ",id:"
                    + emailBoxDB.getId();
            return ERROR;
        }
        if (StringUtil.isEmpty(this.emailBox.getBoxName())) {
            errorMsg = "邮件箱名称不能为空";
            return ERROR;
        }
        emailBoxDB.setBoxName(this.emailBox.getBoxName());
        emailBoxDB.setOrderNo(this.emailBox.getOrderNo());
        if (this.emailBox.getPageMax() != null && this.emailBox.getPageMax() > 0) {
            emailBoxDB.setPageMax(this.emailBox.getPageMax());
        }
        emailBoxDB.setLastUpdateInfo(this.getLoginUser());
        emailBoxDB.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
        emailBoxDB.setLastUpdateUserId(this.getLoginUser().getUserId());
        this.emailBoxService.saveOrUpdate(emailBoxDB);
        ajax(errorMsg);
        return null;
    }

    public IEmailBox getEmailBoxService() {
        return emailBoxService;
    }

    public void setEmailBoxService(IEmailBox emailBoxService) {
        this.emailBoxService = emailBoxService;
    }

    public EmailBox getEmailBox() {
        return emailBox;
    }

    public void setEmailBox(EmailBox emailBox) {
        this.emailBox = emailBox;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getPageMax() {
        return pageMax;
    }

    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }
}
