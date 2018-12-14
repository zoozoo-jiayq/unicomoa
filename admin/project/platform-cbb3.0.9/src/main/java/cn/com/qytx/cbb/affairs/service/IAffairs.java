package cn.com.qytx.cbb.affairs.service;

import java.util.List;

import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.vo.AffairsVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

public interface IAffairs extends BaseService<Affairs>{
	 /**
     * 根据ID查询单条
     * @param affairsId 事物Id
     * @return 单条事物
     */
    public Affairs findById(Integer affairsId);

    /**
     * 分页获取事务提醒列表
     * @param id 接收人Id
     * @param remindFlag 消息提醒状态
     * @return Page<Affairs>
     */
    public Page<Affairs> findPageByUserId(Pageable pageable, Integer id, Integer remindFlag);

    /**
     * 批量删除事务提醒
     * @param affairs affairs
     */
    public void deleteBatch(Integer[] affairs);

    /**
     * 批量事务提醒为已读
     * @param affairs affairs
     * @param remainFilg 事务状态
     */
    public void updateReadedAffairs(Integer[] affairs, Integer remindFlag);


    /**
     * 功能：批量事务提醒为已接受
     * @param userId 用户
     * @param remainFilg 更新状态
     */
    public void updateReceivedAffairs(Integer userId, Integer remindFlag);
    
    /**
     * 删除所有已读信息
     * @param userId userId
     */
    public void deleteAllReaded(Integer userId);

    /**
     * 删除所有未读信息
     * @param userId userId
     */
    public void deleteAllUnReaded(Integer userId);
    
    /**
     * 删除所有信息
     * @param userId userId
     */
    public void deleteAllAffairs(Integer userId);

    /**
     * 设置所有已接受消息为已读
     * @param userId userId
     */
    public void updateAllReaded(Integer userId);

    /**
     * 分页获取发送的事务提醒列表
     * @param page 分页信息
     * @param id 用户Id
     * @return Page<Affairs>
     */
    public Page<Affairs> findSendPageByUserId(Pageable pageable, Integer id);

    /**
     * 批量删除发送事务提醒
     * @param affairs affairs
     */
    public void deleteSendAffairs(Integer[] affairs);

    /**
     * 删除已提醒收信人提醒
     * @param id 发送人Id
     */
    public void deleteAllSendReaded(Integer id);

    /**
     * 删除所有已发送的事务提醒
     * @param userId 发送人Id
     */
    public void deleteAllSendAffairs(Integer userId);

    /**
     * 删除收信人已删除提醒
     * @param userId 发送人Id
     */
    public void deleteToUserDeleted(Integer userId);

    /**
     * 重发事务提醒
     * @param affairsId 事务提醒ID
     * @param userInfo 用户信息
     * @return boolean
     */
    public boolean retransmissionAffairs(Integer affairsId, UserInfo userInfo);

    /**
     * 根据VO查询所有消息
     * @param affairsVo affairsVo
     * @return List<Affairs>
     */
    public List<Affairs> getAllAffairsByVo(AffairsVo affairsVo);

    /**
     * 获取所有事务提醒列表
     * @param userId 用户Id
     * @param remindFlag 提醒状态
     * @return List<Affairs>
     */
    public List<Affairs> findAllByUserId(Integer userId, Integer remindFlag);

    /**
     * 获取所有未阅的提醒列表
     * @param userId 用户Id
     * @return List<Affairs>
     */
    public List<Affairs> findUnReadByUserId(Integer userId);
    
    /**
     * 功能：根据Vo删除事务提醒
     * @param affairsVo affairsVo
     */
    public void deleteAffairsByVo(AffairsVo affairsVo);

    /**
     * 功能：根据模块名称更新此模块提醒为已读
     * @param userId 用户ID
     * @param moduleName 模块名称
     */
    public void updateModuleReaded(Integer userId, String moduleName);

    /**
     * 功能：获取未读事务提醒的数量
     * @param userId
     * @return 未读微讯数量
     */
    public int getNewAffairsNum(Integer userId);
    
    /**
     * 功能：获取有未接受的事务提醒的员工Id集合
     * @return List<Integer>
     */
    public List<Integer> findUnReadAffairsUser();
}
