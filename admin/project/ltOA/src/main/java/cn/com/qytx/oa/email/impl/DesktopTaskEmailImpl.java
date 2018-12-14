package cn.com.qytx.oa.email.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.todocount.service.IDesktopTask;
import cn.com.qytx.oa.email.service.IEmail;

/**
 * 功能:个人桌面任务实现类--电子邮件之未读邮件
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-18 下午7:33
 * 修改日期: 13-4-18 下午7:33
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Service
@Transactional
public class DesktopTaskEmailImpl implements IDesktopTask,Serializable {

	@Autowired
    private IEmail emailService;

    @Override
    public int countOfTask(int userId) {
        return emailService.countOfNotReadEmail(userId);
    }

    /**
     * 获取任务链接
     *
     * @return URL地址
     */
    public String getTaskUrl() {
        return "/logined/email/emailNotReadForDesktopTask.action";
    }

    /**
     * 获取模块名，数据库ModuleInfo表中的moduleName字段值
     *
     * @return 模块名
     */
    public String getModuleName() {
        return "电子邮件";
    }

    public IEmail getEmailService() {
        return emailService;
    }

    public void setEmailService(IEmail emailService) {
        this.emailService = emailService;
    }
}
