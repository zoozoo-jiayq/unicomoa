/**
 * 
 */
package cn.com.qytx.oa.email.service;

import java.util.List;

import cn.com.qytx.oa.email.domain.EmailBox;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:邮件箱服务接口类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-14
 * 修改日期: 2013-03-14
 * 修改人员: 汤波涛
 * 修改列表:初始加入的方法
 */
public interface IEmailBox extends BaseService<EmailBox> {
	
	
	/**
	 * 获取某个用户所有的邮件箱
	 * @param userId 用户Id
	 * @return 该用户的所有邮件箱
	 */
	public List<EmailBox> findAllByUserId(int userId);
	
	/**
	 * 获取某个用户所有的自定义邮件箱
	 * @param userId 用户Id
	 * @return 该用户的所有自定义邮件箱
	 */
	public List<EmailBox> findAllDiyBoxByUserId(int userId);
	
	/**
	 * 根据Vid查询单个邮件箱
	 * @param emailBoxId 邮件箱Id
	 * @return 查询出来的邮件箱
	 */
	public EmailBox findById(int emailBoxId);
	
	/**
	 * 根据用户ID和邮件箱类型查询一个系统默认邮件箱
	 * @param userId 用户ID
	 * @param boxType 邮件箱类型
	 * @return 查询出来的邮件对象
	 */
	public EmailBox findDefaultBoxByUserIdAndBoxType(int userId,int boxType);
	
	/**
	 * 为某个用户创建默认的几个邮件箱
	 * @param userId 创建的邮件箱用户Id
	 */
	public void saveDefault(int userId,UserInfo currentUser);
	
	/**
	 * 检查某人默认的系统邮件箱是否存在，不存在则创建
	 * @param userId 创建的邮件箱用户Id
	 */
	public void saveDefaultAfterCheckExist(int userId,UserInfo currentUser);
	
	
    /**
     * 获取某个邮件箱的总容量
     * @param emailBox 邮件箱
     * @return 容量byte
     */
    public long getBoxSize(EmailBox emailBox);
    /**
     * 删除一个邮件箱
     *
     * @param id 邮件箱ID
     */
    public void delete(int id);

    public void deleteEmailBoxByUserIds(String userIds);
}
