package cn.com.qytx.oa.message.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.oa.message.domain.Message;
import cn.com.qytx.oa.message.vo.MessageVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能:微讯消息service
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public interface IMessage extends BaseService<Message>, Serializable
{
    /**
     * 分页获取微讯列表
     * @param userId 接收人Id
     * @param remindFlag 消息提醒状态
     * @return Page<Affairs>
     */
    public Page<Message> findPageByUserId(Pageable page, Integer userId, Integer remindFlag);

    /**
     * 批量微讯为已读
     * @param messageIds messageIds
     * @param remainFilg 状态
     */
    public void updateReadedMessage(Integer[] messageIds, Integer remainFilg);

    /**
     * 批量删除微讯信息
     * @param messageIds messageIds
     */
    public void deleteMessageById(Integer[] messageIds);

    /**
     * 分页过滤查询所有微讯信息
     * @param pageInfo pageInfo
     * @param messageVo messageVo
     * @return Page<Message>
     */
    public Page<Message> findAllMessagePageByVo(Pageable pageInfo, MessageVo messageVo);

    /**
     * 根据VO条件删除微讯信息
     * @param messageVo 查询条件Vo
     */
    public void deleteMessageByVo(MessageVo messageVo);

    /**
     * 根据VO条件查询微讯信息
     * @param messageVo 查询条件Vo
     */
    public List<Message> findAllByVo(MessageVo messageVo);

    /**
     * 根据微讯Id获取微讯信息
     * @param id 微讯Id
     * @return Message
     */
    public Message findById(Integer id);

    /**
     * 获取用户1和用户2的微讯聊天记录
     * @param page 分页信息
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @return 分页查询结果Page<Message>
     */
    public Page<Message> findPageWithUId(Pageable page, Integer userId1, Integer userId2, String order);

    /**
     * 功能：根据当前用户Id获取当前微讯聊天人信息
     * @param userId 用户Id
     * @return List<UserInfo>用户列表
     */
    public List<UserInfo> getCurrentUser(Integer userId);

    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findCurrentUserMessage(Integer userId1, Integer userId2, String beginTime);
    
    /**
     * 功能：根据聊天人Id,批量与此人微讯为已读
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void updateReadedByUserId(Integer userId, Integer withUserId);
    
    /**
     * 功能：根据聊天人Id,批量删除与此人微讯
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void deleteByUserId(Integer userId, Integer withUserId);
    
    
    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findUserMessageByTime(Integer userId1, Integer userId2, String beginTime);
    
    /**
     * 功能：获取有未读微讯的员工Id集合
     * @return List<Integer>
     */
    public List<Integer> findUnReadMessageUser();
}
