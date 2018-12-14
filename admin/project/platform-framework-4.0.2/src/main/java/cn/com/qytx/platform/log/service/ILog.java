package cn.com.qytx.platform.log.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 *文件名:cbb 日志服务接口
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-2-21上午10:49:04
 *修改人:
 *修改日期:
 *功能描述:
 *版本号:
 */
public interface ILog extends BaseService<Log>,Serializable {

    /**
     * 添加日志
     * @param companyId 企业ID
     * @param userId     登陆人员ID
     * @param userName  登陆人员姓名
     * @param sysName    系统名称
     * @param moduleName 模块名称
     * @param action      //操作类型，如 人员登陆，添加部门。。。。。
     * @param content   // 操作内容
     * @param refId     //第三方id 比方说删除了某个人员，这里填写删除人员的ID
     * @param ip
     */
    public void save(int companyId, int userId, String userName, String sysName, String moduleName, String action, String content, int refId, String ip);
    /**
     * 功能：
     * @param max 最大显示条数
     * @param logType 系统日志类型
     * @param userIds 登陆人
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param ip IP
     * @param remark 备注
     * @param groupIds:部门集合
     * @return
     */
	public List<Log> getLogListByParam(Integer max, Integer logType, String userIds, String startTime, String endTime, String ip, String remark, String userName, String groupIds);
    /**
     * 功能：
     * @param logType 系统日志类型
     * @param userIds 登陆人
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param ip IP
     * @param remark 备注
     * @return
     */
	public void deleteListByParam(Integer max, Integer logType, String userIds, String startTime, String endTime, String ip, String remark);
	

	/**
	 * 日志概况
	 * 功能：
	 * @return
	 */
	public String getAllLogNums();
	
	/**
	 * 功能：最新的修改密码对象
	 * @return
	 */
	public Log getLasterLog();
	
	
	/**
	 * 功能：通过日志字段查询得到分页对象
	 * @param pageable
	 * @param logType 日志类型
	 * @param keyWord 关键字 (IP地址 备注 登录人)
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @return
	 */
	public Page<Log> findByPage(UserInfo user,Pageable pageable, Integer logType,
			String keyWord, String startTime, String endTime);
	
	/**
	 * 获取用户激活的数量
	 * @param companyId
	 * @return
	 */
	public int getUserInfoReport(Integer companyId);
}
