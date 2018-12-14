package cn.com.qytx.oa.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.message.dao.MessageDao;
import cn.com.qytx.oa.message.domain.MessageView;
import cn.com.qytx.oa.message.service.IMessageView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;

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
public class MessageViewImpl extends BaseServiceImpl<MessageView> implements IMessageView
{

    /**
     * 微讯视图Dao
     */
	@Autowired
    MessageDao messageDao;

    
    /**
     * 功能：分组查询所有微讯信息
     * @param page 分页数据
     * @param userId 当前用户ID
     * @return Page<Message>
     */
    public Page<MessageView> findAllUserMessageByPage(Pageable page, Integer userId)
    {
        return messageDao.findAllUserMessageByPage(page, userId);
    }
}
