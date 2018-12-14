package cn.com.qytx.oa.message.service;

import java.io.Serializable;

import cn.com.qytx.oa.message.domain.MessageView;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

/**
 * 功能:微讯消息service
 * 版本: 1.0
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public interface IMessageView extends BaseService<MessageView>, Serializable
{
    /**
     * 功能：分组查询所有微讯信息
     * @param page 分页数据
     * @param userId 当前用户ID
     * @return Page<Message>
     */
    public Page<MessageView> findAllUserMessageByPage(Pageable page, Integer userId);
    
}
