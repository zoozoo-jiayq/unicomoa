package cn.com.qytx.oa.email.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.email.dao.EmailBodyDao;
import cn.com.qytx.oa.email.dao.EmailToDao;
import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.service.IEmailList;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

@Service
@Transactional
public class emailListImpl extends BaseServiceImpl<EmailBody> implements IEmailList {
	private static final long serialVersionUID = -1344068474736437626L;
	
	@Autowired
	EmailToDao emailToDao;
	
	@Autowired
	EmailBodyDao emailBodyDao;
	
	/**
	 * 获得所有未读列表
	 */
	@Transactional(readOnly=true)
	public List<EmailTo> getAllNotReadEmail(int userId, String searchWord){
        return this.emailToDao.getAllNotReadEmail(userId, searchWord);
	}
	
    /**
     * 查询某个用户未读取的邮件
     * @param userId 用户ID
     * @return 查询结果
     */
	@Transactional(readOnly=true)
    public List<EmailTo> findAllNotReadEmail(int userId) {
        return this.emailToDao.findAllNotReadEmail(userId);
    }
	
    /**
     * 搜索某个用户的某个邮件箱邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱Id
     * @param searchWord 搜索关键字
     * @return 搜索结果
     */
	@Transactional(readOnly=true)
    public List<EmailTo> findAllReceiveEmailBySearchWord(int userId, int emailBoxId, String searchWord) {
        return this.emailToDao.findAllReceiveEmailBySearchWord(userId, emailBoxId, searchWord);
    }
    
    /**
     * 获取某个用户的某个邮件箱的所有邮件
     *
     * @param userId     用户ID
     * @param emailBoxId 邮件箱ID
     * @return 查询结果
     */
	@Transactional(readOnly=true)
    public List<EmailTo> findAllReceiveEmailByUserId(int userId, int emailBoxId) {
        return this.emailToDao.findAllReceiveEmailByUserId(userId, emailBoxId);
    }

	/**
     * 根据高级搜索条件查询某个用户的已发送邮件
     *
     * @param userId        用户ID
     * @param emailSearchVo 高级搜索信息
     * @return 结果集-翻页
     * @throws Exception 日期转换异常等等
     */
    public List<EmailBody> findAllSendedEmailBodyBySearchVo(int userId, EmailSearchVo emailSearchVo) throws Exception {
        return this.emailBodyDao.findAllSendedEmailBodyBySearchVo(userId, emailSearchVo);
    }
    
    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId,String searchWord) {
        return this.emailBodyDao.findAllSendedEmailBodyByUserId(userId, searchWord);

    }

}
