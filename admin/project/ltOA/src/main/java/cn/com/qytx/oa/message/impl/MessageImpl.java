package cn.com.qytx.oa.message.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.oa.message.dao.MessageDao;
import cn.com.qytx.oa.message.domain.Message;
import cn.com.qytx.oa.message.service.IMessage;
import cn.com.qytx.oa.message.vo.MessageVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

/**
 * 功能:微讯Impl
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
@Service
@Transactional
public class MessageImpl extends BaseServiceImpl<Message> implements IMessage
{


	/**
     * 微讯Dao
     */
	@Autowired
    MessageDao messageDao;

    
	@Autowired
	IUser userService;
    

    /**
     * 保存新微讯信息
     */
    public void save(Message messageInfo)
    {
        if (null != messageInfo)
        {
            // 设置发送时间
            String sendTimeStr = messageInfo.getSendTimeStr();
            if (StringUtil.isEmpty(sendTimeStr))
            {
                messageInfo.setSendTime(new Timestamp(System.currentTimeMillis()));
            }
            else
            {
                messageInfo.setSendTime(DateTimeUtil.stringToTimestamp(sendTimeStr,
                        CbbConst.TIME_FORMAT_STR));
            }

            // 根据接收人Id不同,分解消息
            String toUids = messageInfo.getToUids();
            if (!StringUtil.isEmpty(toUids))
            {
                String[] idsArr = toUids.split(CbbConst.USERID_SEGMENTATION);

                // 发送人 发送时间
                UserInfo user = messageInfo.getCreateUserInfo();
                Timestamp now = new Timestamp(new Date().getTime());
                for (String id : idsArr)
                {
                    if (!StringUtil.isEmpty(id))
                    {
                        Message temp = new Message();

                        // 设置消息内容
                        temp.setIsDelete(CbbConst.NOT_DELETE);
                        temp.setCreateTime(now);
                        temp.setLastUpdateTime(now);
                        temp.setLastUpdateUserId(user.getUserId());
                        temp.setCompanyId(user.getCompanyId());
                        temp.setRemindFlag(MessageConst.SENDED);

                        // 发送人
                        temp.setCreateUserInfo(user);

                        // 接收人
                        Integer toUid = Integer.parseInt(id);
                        if (user.getUserId().intValue() != toUid)
                        {
                            UserInfo toUser = new UserInfo();
                            toUser.setUserId(toUid);
                            temp.setToUserInfo(toUser);
                        }
                        else
                        {
                            temp.setToUserInfo(user);
                        }

                        // 发送时间
                        temp.setSendTime(messageInfo.getSendTime());

                        // 微讯来源类型
                        temp.setMsgType(messageInfo.getMsgType());

                        // 内容
                        temp.setContentInfo(messageInfo.getContentInfo());

                        // 保存微讯信息
                        messageDao.saveOrUpdate(temp);
                        
                        // 如果为首页发送的微讯,则更新与此人的聊天记录为已阅
                        
                        if (!StringUtil.isEmpty(messageInfo.getSrcType()) &&  "box".equals(messageInfo.getSrcType()))
                        {
                            messageDao.updateReadedByUserId(user.getUserId(), toUid);
                        }
                    }
                }
            }
            
            
            
        }
    }


    /**
     * 分页获取微讯列表
     * @param userId 接收人Id
     * @param remindFlag 消息提醒状态
     * @return Page<Affairs>
     */
    public Page<Message> findPageByUserId(Pageable page, Integer userId, Integer remindFlag)
    {
        return messageDao.findPageByUserId(page, userId, remindFlag);
    }

    /**
     * 批量设置微讯状态
     * @param messageIds messageIds
     * @param remainFilg 状态
     */
    public void updateReadedMessage(Integer[] messageIds, Integer remainFilg)
    {
        if (null != messageIds)
        {
            for (Integer messageId : messageIds)
            {
                messageDao.updateReadedMessage(messageId, remainFilg);
            }
        }
    }

    /**
     * 批量删除微讯信息
     * @param messageIds messageIds
     */
    public void deleteMessageById(Integer[] messageIds)
    {
        if (null != messageIds)
        {
            for (Integer messageId : messageIds)
            {
                messageDao.deleteMessageById(messageId);
            }
        }
    }

    /**
     * 分页过滤查询所有微讯信息
     * @param pageInfo pageInfo
     * @param messageVo messageVo
     * @return Page<Message>
     */
    public Page<Message> findAllMessagePageByVo(Pageable pageInfo, MessageVo vo)
    {
        return messageDao.findAllMessagePageByVo(pageInfo, vo);
    }

    /**
     * 根据VO条件删除微讯信息
     * @param messageVo 查询条件Vo
     */
    public void deleteMessageByVo(MessageVo messageVo)
    {
        messageDao.deleteMessageByVo(messageVo);
    }

    /**
     * 根据VO条件查询微讯信息
     * @param messageVo 查询条件Vo
     */
    public List<Message> findAllByVo(MessageVo messageVo)
    {
        return messageDao.findAllByVo(messageVo);
    }

    /**
     * 根据微讯Id获取微讯信息
     * @param id 微讯Id
     * @return Message
     */
    public Message findById(Integer id)
    {
        return messageDao.findOne(id);
    }

    /**
     * 获取用户1和用户2的微讯聊天纪律
     * @param page 分页信息
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @return 分页查询结果Page<Message>
     */
    public Page<Message> findPageWithUId(Pageable page, Integer userId1, Integer userId2,  String order)
    {
        return messageDao.findPageWithUId(page, userId1, userId2, order);
    }

    /**
     * 功能：根据当前用户Id获取当前微讯聊天人信息
     * @param userId 用户Id
     * @return List<UserInfo>用户列表
     */
    public List<UserInfo> getCurrentUser(Integer userId)
    {
    	List<Integer> list = messageDao.getCurrentUserId(userId);
    	List<UserInfo> lsitUserInfo = new ArrayList<UserInfo>();
    	if(list!=null&&list.size()>0){
    		String userIds="";
    		for(Integer id : list){
    			if(id!=null){
    				userIds+=id+",";
    			}
    		}
    		if(userIds.length()>0){
    			userIds = userIds.substring(0,userIds.length()-1);
    			lsitUserInfo = userService.findAll(" x.userId in ( "+userIds+" )");
    		}
    	}
        return lsitUserInfo;
    }

    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findCurrentUserMessage(Integer userId1, Integer userId2, String beginTime)
    {
        return messageDao.findCurrentUserMessage(userId1, userId2, beginTime);
    }
    
    /**
     * 功能：根据聊天人Id,批量与此人微讯为已读
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void updateReadedByUserId(Integer userId, Integer withUserId)
    {
        messageDao.updateReadedByUserId(userId, withUserId);
    }
    
    /**
     * 功能：根据聊天人Id,批量删除与此人微讯
     * @param userId 登录人Id
     * @param withUserId 通讯人Id
     */
    public void deleteByUserId(Integer userId, Integer withUserId)
    {
        messageDao.deleteByUserId(userId, withUserId);
    }
    
    
    /**
     * 功能：查询与当前用户的聊天记录
     * @param userId1 用户Id1
     * @param userId2 用户Id2
     * @param beginTime 查询开始时间
     * @return List<Message>
     */
    public List<Message> findUserMessageByTime(Integer userId1, Integer userId2, String beginTime)
    {
        return messageDao.findUserMessageByTime(userId1, userId2, beginTime);
    }
    
    /**
     * 功能：获取有未读微讯的员工Id集合
     * @return List<Integer>
     */
    public List<Integer> findUnReadMessageUser()
    {
        return messageDao.findUnReadMessageUser();
    }
}
