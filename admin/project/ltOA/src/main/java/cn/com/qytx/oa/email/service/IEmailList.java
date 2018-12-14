package cn.com.qytx.oa.email.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.email.domain.EmailBody;
import cn.com.qytx.oa.email.domain.EmailTo;
import cn.com.qytx.oa.email.vo.EmailSearchVo;
import cn.com.qytx.platform.base.service.BaseService;

public interface IEmailList extends BaseService<EmailBody>,Serializable{
	/**
	 * 获得所有未读列表
	 */
	public List<EmailTo> getAllNotReadEmail(int userId, String searchWord);
	
	
	 /**
     * 搜索某个用户的某个邮件箱邮件
     *
     * @param userId     用户Id
     * @param emailBoxId 邮件箱Id
     * @param searchWord 搜索关键字
     * @return 搜索结果
     */
    public List<EmailTo> findAllReceiveEmailBySearchWord(int userId, int emailBoxId, String searchWord);
    
    
    
	/**
     * 根据高级搜索条件查询某个用户的已发送邮件
     *
     * @param userId        用户ID
     * @param emailSearchVo 高级搜索信息
     * @return 结果集-翻页
     * @throws Exception 日期转换异常等等
     */
    public List<EmailBody> findAllSendedEmailBodyBySearchVo(int userId, EmailSearchVo emailSearchVo) throws Exception;
    
    /**
     * 查询某个用户已发送邮件
     *
     * @param userId 用户Id
     * @return 查询结果
     */
    public List<EmailBody> findAllSendedEmailBodyByUserId(int userId,String searchWord);
}
