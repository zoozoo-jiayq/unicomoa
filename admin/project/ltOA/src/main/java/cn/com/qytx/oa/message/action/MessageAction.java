package cn.com.qytx.oa.message.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairs.service.MessageConst;
import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.oa.message.domain.Message;
import cn.com.qytx.oa.message.domain.MessageView;
import cn.com.qytx.oa.message.service.IMessage;
import cn.com.qytx.oa.message.service.IMessageView;
import cn.com.qytx.oa.message.vo.MessageVo;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.DateUtils;
import cn.com.qytx.platform.utils.OnlineUserListener;
import cn.com.qytx.platform.utils.PageUtil;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * 功能:微讯action
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class MessageAction extends BaseActionSupport
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = 4261356209036257049L;

    /**
     * 日志类
     */
    private static final Logger logger = Logger.getLogger(MessageAction.class);

    /**
     * 微讯消息
     */
    private Message messageInfo;

    /**
     * 微讯service
     */
    @Autowired
    IMessage messageImpl;
    
    /**
     * 微讯serviceview
     */
    @Autowired
    IMessageView messageViewImpl;

    

    /**
     * 微讯状态
     */
    private Integer remindFlag;

    /**
     * 微讯Id
     */
    private Integer[] messageId;

    /**
     * 微讯vo
     */
    private MessageVo messageVo;

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 用户信息
     */
	@Autowired
    IUser userService;

    /**
     * 即时微讯查询开始时间
     */
    private String beginTime;

    /**
     * 内容
     */
    private String contentInfo;

    /**
     * 群组ID
     */
    private Integer groupId;

    private String requestType;

    /**
     * 聊天室发送微讯id字符串,有可能是群组或者个人id
     */
    private String idStr;

    /**
     * 表示是否向在线人员发送微讯
     */
    private String onLine;

    /**
     * 发送微讯消息
     */
    public void sendMessage()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 入参校验
            if (null == messageInfo || StringUtil.isEmpty(messageInfo.getToUids()))
            {
                this.ajax("param error");
                return;
            }

            // 保存或者更新实体类信息
            messageInfo.setCreateUserInfo(user);
            messageInfo.setCompanyId(user.getCompanyId());
            messageInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            messageInfo.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            messageInfo.setLastUpdateUserId(user.getUserId());
            String userIds = messageInfo.getToUids();
            if(!userIds.equals("")){
            	String[] userIdArr = userIds.split(",");
            	for(String userId:userIdArr){
            		if(userId!=null&&!"".equals(userId)){
            			Message message = new Message();
            			message.setCompanyId(messageInfo.getCompanyId());
            			message.setContentInfo(messageInfo.getContentInfo());
            			message.setCreateTime(new Timestamp(System.currentTimeMillis()));
            			message.setCreateUserInfo(user);
            			message.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
            			message.setLastUpdateUserId(user.getUserId());
            			message.setRemindFlag(messageInfo.getRemindFlag());
            			message.setMsgType(messageInfo.getMsgType());
            			message.setToUserInfo(userService.findOne(Integer.valueOf(userId)));
            			messageImpl.saveOrUpdate(message);
            		}
            	}
            	
            }
            ajax("success");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("sendMessage error. ", e);
        }
    }

    /**
     * 分页获取未确认微讯消息
     */
    public void getMessagePage()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            Page<Message> pageInfo = null;
            // 查询分页数据信息
            if (null != remindFlag)
            {
                pageInfo = messageImpl.findPageByUserId(getPageable(), user.getUserId(), remindFlag);
            }

            // 获取结果
            List<Message> messageList = pageInfo.getContent();

            // 把事务信息信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (messageList != null)
            {
                // 汇总ID
                Integer[] idList = new Integer[messageList.size()];

                // 获取序号
                int i = (getPageable().getPageNumber() - 1) * this.getIDisplayLength() + 1;
                int j = 0;
                for (Message tempMessage : messageList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 序号
                    map.put("no", i);

                    // id
                    map.put("id", tempMessage.getId());
                    idList[j++] = tempMessage.getId();

                    // 发送人
                    String fromUserName = tempMessage.getCreateUserInfo().getUserName();
                    map.put("fromUserName", fromUserName);

                    // 内容
                    String contentInfo = tempMessage.getContentInfo();
                    map.put("contentInfo", contentInfo);

                    // 发送时间
                    Timestamp sendTime = tempMessage.getSendTime();
                    if (null != sendTime)
                    {
                        map.put("sendTime",
                                DateTimeUtil.dateToString(sendTime, CbbConst.TIME_FORMAT_STR));
                    }
                    else
                    {
                        map.put("sendTime", "");
                    }
                    mapList.add(map);
                    i++;
                }

                // 如果为未阅微讯,则更改状态为已阅
                messageImpl.updateReadedMessage(idList, MessageConst.READED);
            }

            this.ajaxPage(pageInfo, mapList);
        }
        catch (Exception e)
        {
            logger.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 根据Id数组删除微讯信息
     */
    public void deleteMessageById()
    {
        try
        {
            // 基本参数校验
            if (null == messageId)
            {
                ajax("param error");
                return;
            }

            // 删除联系人信息
            messageImpl.deleteMessageById(messageId);
            ajax("success");
        }
        catch (Exception e)
        {
            logger.error("deleteMessageById error. ", e);
        }
    }

    /**
     * 获取分页微讯查询列表
     */
    public void getAllMessagePage()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            messageVo.setUserinfo(user);

            // 排序字段和排序方式
            String sortFiled = messageVo.getSortFiled();
            String sortType = messageVo.getSortType();
            Sort sort = null;
            if (!StringUtil.isEmpty(sortFiled) && !StringUtil.isEmpty(sortType))
            {
                if ("1".equals(sortFiled))
                {
                    // 类型
                    if (sortType.toLowerCase().equals("desc")) {
                    	sort = new Sort(new Sort.Order(Direction.DESC,"cast(x.contentInfo as varchar(4000))"));
					}else {
						sort = new Sort(new Sort.Order(Direction.ASC,"cast(x.contentInfo as varchar(4000))"));
					}
                    
                }
                else if ("2".equals(sortFiled))
                {
                    // 内容
                    if (sortType.toLowerCase().equals("desc")) {
                    	sort = new Sort(new Sort.Order(Direction.DESC,"x.sendTime"));
					}else {
						sort = new Sort(new Sort.Order(Direction.ASC,"x.sendTime"));
					}
                }

            }
            // 查询分页数据信息
            Page<Message> pageInfo = null;
            if (sort==null) {
            	pageInfo = messageImpl.findAllMessagePageByVo(this.getPageable(), messageVo);
			}else {
				pageInfo = messageImpl.findAllMessagePageByVo(this.getPageable(sort), messageVo);
			}

            // 获取结果
            List<Message> messageList = pageInfo.getContent();

            // 把发送的事务提醒信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (messageList != null)
            {
                // 获取序号
                int i = (this.getPageable().getPageNumber() - 1) * this.getIDisplayLength() + 1;
                for (Message tempMessage : messageList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 序号
                    map.put("no", i);

                    // id
                    map.put("id", tempMessage.getId());

                    // 微讯类型
                    Integer msgType = tempMessage.getMsgType();
                    map.put("msgType", msgType);

                    // 发信人
                    String fromUserName = tempMessage.getCreateUserInfo().getUserName();
                    map.put("fromUserName", fromUserName);

                    // 收信人
                    String toUserName = tempMessage.getToUserInfo().getUserName();
                    map.put("toUserName", toUserName);

                    // 内容
                    String contentInfo = tempMessage.getContentInfo();
                    map.put("contentInfo", contentInfo);

                    // 发送时间
                    Timestamp sendTime = tempMessage.getSendTime();
                    if (null != sendTime)
                    {
                        map.put("sendTime", DateUtils.dateToStrForDynamic(sendTime));
                    }
                    else
                    {
                        map.put("sendTime", "");
                    }

                    // 状态
                    Integer remindFlag = tempMessage.getRemindFlag();
                    map.put("remindFlag", remindFlag);
                    mapList.add(map);
                    i++;
                }
            }
            this.ajaxPage(pageInfo, mapList);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 根据VO条件删除微讯信息
     */
    public void deleteMessageByVo()
    {
        try
        {
            // 基本参数校验
            if (null == messageVo)
            {
                ajax("param error");
                return;
            }

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            messageVo.setUserinfo(user);

            // 删除联系人信息
            messageImpl.deleteMessageByVo(messageVo);
            ajax("success");
        }
        catch (Exception e)
        {
            logger.error("deleteMessageById error. ", e);
        }
    }

    /**
     * 导出文件
     */
    public void exportMessageByVo()
    {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");

        OutputStream outp = null;
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            messageVo.setUserinfo(user);
            List<Message> list = messageImpl.findAllByVo(messageVo);

            // 把联系人信息填充到map里面
//            response.addHeader("Content-Disposition", "attachment;filename="
//                    + new String("即时消息.xls".getBytes(), "UTF-8"));// 解决中文
//                                                                      // 文件名问题)
            response.addHeader("Content-Disposition", "attachment;filename="
                  + URLEncoder.encode("即时消息.xls", "UTF-8"));// 解决中文
            outp = response.getOutputStream();
            List<Map<String, Object>> mapList = analyzeResult(list);

            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList,
                    getExportKeyList());
            exportExcel.export();
        }
        catch (Exception e)
        {
            logger.error("getAddressGroupList error. ", e);
        }
    }

    private List<Map<String, Object>> analyzeResult(List<Message> list)
    {
        // 把订单信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (list != null)
        {
            for (Message tempMessage : list)
            {
                Map<String, Object> map = new HashMap<String, Object>();

                // 微讯类型
                Integer msgType = tempMessage.getMsgType();
                if (MessageConst.MSGTYPE_FROM_WEBPAGE_VALUE == msgType)
                {
                    map.put("msgType", MessageConst.MSGTYPE_FROM_WEBPAGE);
                }
                else if (MessageConst.MSGTYPE_FROM_ANDROID_VALUE == msgType)
                {
                    map.put("msgType", MessageConst.MSGTYPE_FROM_ANDROID);
                }
                else
                {
                    map.put("msgType", msgType);
                }

                // 发信人
                String fromUserName = tempMessage.getCreateUserInfo().getUserName();
                map.put("fromUserName", fromUserName);

                // 收信人
                String toUserName = tempMessage.getToUserInfo().getUserName();
                map.put("toUserName", toUserName);

                // 内容
                String contentInfo = tempMessage.getContentInfo();
                // 格式化数据,去掉html标签内容
                contentInfo = getHtmlText(contentInfo);
                map.put("contentInfo", contentInfo);

                // 发送时间
                Timestamp sendTime = tempMessage.getSendTime();
                if (null != sendTime)
                {
                    map.put("sendTime",
                            DateTimeUtil.dateToString(sendTime, CbbConst.TIME_FORMAT_STR));
                }
                else
                {
                    map.put("sendTime", "");
                }

                // 状态
                Integer remindFlag = tempMessage.getRemindFlag();
                if (MessageConst.REMIND_YES == remindFlag)
                {
                    map.put("remindFlag", MessageConst.REMIND_YES_STR);
                }
                else
                {
                    map.put("remindFlag", MessageConst.REMIND_NO_STR);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    private String getHtmlText(String html)
    {
        if (html != null)
        {
            return html.replaceAll("<.*?>", "").replaceAll("&nbsp;", "");
        }
        else
        {
            return null;
        }
    }

    private List<String> getExportHeadList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("类型");
        headList.add("发信人");
        headList.add("收信人");
        headList.add("内容");
        headList.add("发送时间");
        headList.add("提醒");
        return headList;
    }

    private List<String> getExportKeyList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("msgType");
        headList.add("fromUserName");
        headList.add("toUserName");
        headList.add("contentInfo");
        headList.add("sendTime");
        headList.add("remindFlag");
        return headList;
    }

    /**
     * 跳转回复微讯页面
     * @return String
     */
    public String replyMessage()
    {
        try
        {
            if (null != messageId)
            {
                Message message = messageImpl.findById(messageId[0]);
                this.getRequest().setAttribute("userInfoId",
                        message.getCreateUserInfo().getUserId());
                this.getRequest().setAttribute("userInfoName",
                        message.getCreateUserInfo().getUserName() + ",");
            }
            else if (null != userId)
            {
                UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
                UserInfo replayUser = userService.findOne(userId);
                this.getRequest().setAttribute("userInfoId", replayUser.getUserId());
                this.getRequest().setAttribute("userInfoName", replayUser.getUserName() + ",");
            }
        }
        catch (Exception e)
        {
            logger.error("replyMessage error.", e);
        }
        return SUCCESS;
    }

    /**
     * 根据用户Id,获取微讯交谈记录
     */
    public void getMessageWithUId()
    {
        try
        {
            if (null == userId)
            {
                logger.error("getMessageWithUId error. param is null");
                return;
            }
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "sendTime"));
            Page<Message> pageInfo = messageImpl.findPageWithUId(this.getPageable(sort), user.getUserId(), userId, "asc");
            String html = PageUtil.getAjaxPageHtml(pageInfo);
            // 获取结果
            List<Message> messageList = pageInfo.getContent();

            StringBuffer  contentSb = new StringBuffer();
            if (messageList != null)
            {
            	int i=0;
                for (Message tempMessage : messageList)
                {
                	if(i%2==1)
                	{
                		contentSb.append("<li class='even'>");
                	}
                	else
                	{
                		contentSb.append("<li>");
                	}
                    contentSb.append("<h3><a href=\"javascript:void(0);\" class=\"fr\" onclick=\"deleteMessage('"
                                    + tempMessage.getId() + "')\" >删除</a>");
                    contentSb.append(tempMessage.getCreateUserInfo().getUserName() + " ");
                    contentSb.append(DateUtils.dateToStrForDynamic(tempMessage.getSendTime()));
                    contentSb.append("</h3>");
                    // 微讯内容
                    contentSb.append("<p >"
                            + tempMessage.getContentInfo().replaceAll("clear", "") + "</p>");
                    contentSb.append("</li>");
                    i++;
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("pageHtml", html);
			 jsonMap.put("contents", contentSb.toString());
            ajax(jsonMap);
        }
        catch (Exception e)
        {
            logger.error("getMessageWithUId error.", e);
        }
    }

    /**
     * 功能：查询当前微讯聊天人员
     */
    public void getCurrentUser()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<UserInfo> list = messageImpl.getCurrentUser(user.getUserId());
            Gson json = new Gson();
            String jsons = json.toJson(list);
            ajax(jsons);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("getCurrentUser error.", e);
        }
    }

    /**
     * 功能：获取当前聊天信息
     */
    public void getCurrentUserMessage()
    {
        if (null == userId)
        {
            logger.error("getCurrentUserMessage error. param is null");
            return;
        }

        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<Message> list = messageImpl.findCurrentUserMessage(userId, user.getUserId(),
                    beginTime);
            Gson json = new Gson();
            list = changeJson(list);
            String jsons = json.toJson(list);
            ajax(jsons);
        }
        catch (Exception e)
        {
            logger.error("getCurrentUser error.", e);
        }
    }

    private List<Message> changeJson(List<Message> list)
    {
        List<Message> messageList = new ArrayList<Message>();
        if (null != list && !list.isEmpty())
        {
            Message temp;
            for (Message tempMessage : list)
            {
                temp = new Message();
                temp.setSendTimeStr(DateTimeUtil.dateToString(tempMessage.getSendTime(),
                        CbbConst.MINUTE_FORMAT_STR));
                temp.setContentInfo(tempMessage.getContentInfo());
                temp.setCreateUserId(tempMessage.getCreateUserInfo().getUserId());
                temp.setFromUserName(tempMessage.getCreateUserInfo().getUserName());
                temp.setToUid(tempMessage.getToUserInfo().getUserId());
                temp.setToUserName(tempMessage.getToUserInfo().getUserName());
                messageList.add(temp);
            }
        }
        return messageList;
    }

    /**
     * 功能：根据用户Id更新微讯状态为已读
     */
    public void updateMessageReaded()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            messageImpl.updateReadedByUserId(user.getUserId(), userId);
            ajax("success");
        }
        catch (Exception e)
        {
            logger.error("getCurrentUser error.", e);
        }
    }

    /**
     * 功能：获取是否有新微信
     */
    public void getNewMessage()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<Integer> userIdList = messageImpl.findUnReadMessageUser();
            if(userIdList!=null&&!userIdList.isEmpty()){
            	if(userIdList.contains(user.getUserId())){
            		 ajax("success");
            	}
            }
           /* if (MessageTimerTask.hasUnreadMessage(user.getUserId()))
            {
                ajax("success");
            }*/
        }
        catch (Exception e)
        {
            logger.error("getCurrentUser error.", e);
        }
    }

    /**
     * 功能：根据用户Id删除与此用户的聊天信息
     */
    public void deleteByUserId()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            messageImpl.deleteByUserId(user.getUserId(), userId);
            ajax("success");
        }
        catch (Exception e)
        {
            logger.error("getCurrentUser error.", e);
        }
    }

    /**
     * 分页获取未确认微讯消息
     */
    public void findAllUserMessage()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            Page<MessageView> pageInfo = messageViewImpl.findAllUserMessageByPage(this.getPageable(), user.getUserId());

            // 获取结果
            List<MessageView> messageList = pageInfo.getContent();

            // 把事务信息信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (messageList != null)
            {

                // 获取序号
                int i = (this.getPageable().getPageNumber() - 1) * this.getIDisplayLength() + 1;
                for (MessageView tempMessage : messageList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 序号
                    map.put("no", i);

                    // id
                    map.put("fromUserId", tempMessage.getCreateUserInfo().getUserId());

                    // 发送人
                    String fromUserName = tempMessage.getCreateUserInfo().getUserName();
                    map.put("fromUserName", fromUserName);

                    // 内容
                    String contentInfo = tempMessage.getContentInfo();
                    map.put("contentInfo",
                            contentInfo.replaceAll("<.*?>", "").replaceAll("&nbsp;", ""));

                    // 未读数量
                    Integer unReadNum = tempMessage.getUnReadNum();
                    map.put("unReadNum", unReadNum);

                    // 发送时间
                    Timestamp sendTime = tempMessage.getSendTime();
                    if (null != sendTime)
                    {
                        map.put("sendTime",
                                DateTimeUtil.dateToString(sendTime, CbbConst.TIME_FORMAT_STR));
                    }
                    else
                    {
                        map.put("sendTime", "");
                    }
                    mapList.add(map);
                    i++;
                }
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            jsonMap.put("iDisplayStart", this.getIDisplayStart() + this.getIDisplayLength());
            if (null != messageList)
            {
                jsonMap.put("loadAll", messageList.size() < this.getIDisplayLength());
            }
            else
            {
                jsonMap.put("loadAll", true);
            }
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
            logger.error("getAddressGroupList error. ", e);
        }
        finally
        {
            if (null != out)
            {
                out.close();
            }
        }
    }

    /**
     * 根据用户Id,获取微讯交谈记录
     */
    public void getWapMessageWithUId()
    {
        try
        {
            if (null == userId)
            {
                logger.error("getMessageWithUId error. param is null");
                return;
            }
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            Page<Message> pageInfo = messageImpl.findPageWithUId(this.getPageable(), user.getUserId(), userId, "desc");

            // 获取结果
            List<Message> messageList = pageInfo.getContent();

            // 把发送的事务提醒信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (messageList != null)
            {
                // 汇总ID
                Integer[] idList = new Integer[messageList.size()];
                Message tempMessage;
                int j = 0;
                for (int i = messageList.size() - 1; i >= 0; i--)
                {
                    tempMessage = messageList.get(i);
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 内容 去掉所有Html标签
                    // map.put("contentInfo",
                    // tempMessage.getContentInfo().replaceAll("<.*?>", "")
                    // .replaceAll("&nbsp;", ""));
                    map.put("contentInfo", tempMessage.getContentInfo());

                    // 时间
                    map.put("sendTime", DateTimeUtil.dateToString(tempMessage.getSendTime(),
                            CbbConst.TIME_FORMAT_STR));

                    // 接收发送标志
                    if (userId.intValue() == tempMessage.getCreateUserInfo().getUserId().intValue()
                            && userId.intValue() != tempMessage.getToUserInfo().getUserId()
                                    .intValue())
                    {
                        // 1表示接收消息
                        map.put("receiveFlag", 1);
                        idList[j++] = tempMessage.getId();
                    }
                    else
                    {
                        // 2表示发送消息
                        map.put("receiveFlag", 2);

                        if (tempMessage.getCreateUserInfo().getUserId().intValue() == tempMessage
                                .getToUserInfo().getUserId().intValue())
                        {
                            // 自己给自己发送的
                            idList[j++] = tempMessage.getId();
                        }
                    }

                    mapList.add(map);
                }
                // 如果为未阅微讯,则更改状态为已阅
                messageImpl.updateReadedMessage(idList, MessageConst.READED);
            }

            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            jsonMap.put("iDisplayStart", this.getIDisplayStart() + this.getIDisplayLength());
            if (null != messageList)
            {
                jsonMap.put("loadAll", messageList.size() < this.getIDisplayLength());
            }
            else
            {
                jsonMap.put("loadAll", true);
            }
            
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            ajax(jsons);
        }
        catch (Exception e)
        {
            logger.error("getMessageWithUId error.", e);
        }
    }

    /**
     * 根据用户Id,获取时间后的微讯交谈记录
     */
    public void getWapMessageWithUIdByTime()
    {
        try
        {
            if (null == userId)
            {
                logger.error("getMessageWithUId error. param is null");
                return;
            }
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            List<Message> messageList = messageImpl.findUserMessageByTime(userId, user.getUserId(),
                    beginTime);

            // 把发送的事务提醒信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (messageList != null)
            {
                // 汇总ID
                Integer[] idList = new Integer[messageList.size()];
                Message tempMessage;
                int j = 0;
                for (int i = messageList.size() - 1; i >= 0; i--)
                {
                    tempMessage = messageList.get(i);
                    Map<String, Object> map = new HashMap<String, Object>();

                    // 内容 去掉所有Html标签
                    map.put("contentInfo", tempMessage.getContentInfo().replaceAll("<.*?>", "")
                            .replaceAll("&nbsp;", ""));

                    // 时间
                    map.put("sendTime", DateTimeUtil.dateToString(tempMessage.getSendTime(),
                            CbbConst.TIME_FORMAT_STR));

                    // 接收发送标志
                    if (userId.intValue() == tempMessage.getCreateUserInfo().getUserId().intValue()
                            && userId.intValue() != tempMessage.getToUserInfo().getUserId()
                                    .intValue())
                    {
                        // 1表示接收消息
                        map.put("receiveFlag", 1);
                        idList[j++] = tempMessage.getId();
                    }
                    else
                    {
                        // 2表示发送消息
                        map.put("receiveFlag", 2);
                        if (tempMessage.getCreateUserInfo().getUserId().intValue() == tempMessage
                                .getToUserInfo().getUserId().intValue())
                        {
                            // 自己给自己发送的
                            idList[j++] = tempMessage.getId();
                        }
                    }

                    mapList.add(map);
                }
                // 如果为未阅微讯,则更改状态为已阅
                messageImpl.updateReadedMessage(idList, MessageConst.READED);
            }

            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            ajax(jsons);
        }
        catch (Exception e)
        {
            logger.error("getWapMessageWithUIdByTime error.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public String sendMessageByGroupId()
    {
        try
        {
        	HttpServletRequest request = this.getRequest();
        	String ctxPath = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + ctxPath + "/";
//            UserInfo adminUser = (UserInfo) this.getSession().getAttribute("adminUser");
            if (this.groupId == null)
            {
                ajax("群组ID为空");
                return null;
            }
            List<UserInfo> userInfoList = this.userService.findUsersByGroupId(groupId + "");
            if (userInfoList == null)
            {
                ajax("找不到群组下用户:groupId=" + this.groupId);
                return SUCCESS;
            }

            // 是否需要过滤权限
            // this.modulePrivService.removeNoPrivUser(userInfoList, adminUser
            // .getUserId(), adminUser.getCompanyId(), null);

            StringBuffer userNameSb = new StringBuffer();
            StringBuffer userIdSb = new StringBuffer();

            List<Integer> onLIneList = new ArrayList<Integer>();
            for (UserInfo tempUserInfo : userInfoList)
            {
                // 判断是否只向在线人员发送微讯
                if (!StringUtil.isEmpty(onLine) && "onLine".equals(onLine))
                {
                    if (isOnline(onLIneList, tempUserInfo.getUserId()))
                    {
                        userNameSb.append(tempUserInfo.getUserName() + ",");
                        userIdSb.append(tempUserInfo.getUserId() + ",");
                    }
                }
                else
                {
                    // 查询非在线人员
                    userNameSb.append(tempUserInfo.getUserName() + ",");
                    userIdSb.append(tempUserInfo.getUserId() + ",");
                }

            }

//            messageVo = new MessageVo();
//            this.getRequest().setAttribute("userInfoId", userIdSb.toString());
//            this.getRequest().setAttribute("userInfoName", userNameSb.toString());
//            if (StringUtil.isEmpty(contentInfo))
//            {
//                contentInfo = "";
//            }
//            this.getRequest().setAttribute("contentInfo", contentInfo);
            this.getResponse().sendRedirect(
                    basePath + "logined/message/alert_send_message.jsp?type=box&userInfoId="
                            + userIdSb.toString() + "&userInfoName="
                            + URLEncoder.encode(userNameSb.toString(), "UTF-8"));
            return null;
        }
        catch (Exception ex)
        {
            logger.error("sendMessageByGroupId error.", ex);
            return null;
        }
    }

    private boolean isOnline(List<Integer> onLIneList, Integer userId)
    {
        if (null != onLIneList && !onLIneList.isEmpty())
        {
            for (Integer temp : onLIneList)
            {
                if (userId.intValue() == temp.intValue())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 功能：聊天室特殊发送微讯
     */
    public void chatSendMessage()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            HttpServletRequest request = this.getRequest();
            String ctxPath = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + ctxPath + "/";

            if (idStr.startsWith("gid_"))
            {
                this.getResponse().sendRedirect(
                        basePath + "message/sendMessageByGroupId.action?type=box&groupId="
                                + idStr.substring(4, idStr.length()) + "&onLine=" + onLine);
            }
            else if (idStr.startsWith("uid_"))
            {
                int uid = Integer.parseInt(idStr.substring(4, idStr.length()));

                // 获取接收人信息
                UserInfo toUser = userService.findOne(uid);
                this.getResponse().sendRedirect(
                        basePath + "logined/message/alert_send_message.jsp?type=box&userInfoId="
                                + toUser.getUserId() + "&userInfoName="
                                + URLEncoder.encode(toUser.getUserName(), "UTF-8"));
            }
        }
        catch (Exception e)
        {
            logger.error("charSendMessage error", e);
        }
    }

    public Message getMessageInfo()
    {
        return messageInfo;
    }

    public void setMessageInfo(Message messageInfo)
    {
        this.messageInfo = messageInfo;
    }


    public Integer getRemindFlag()
    {
        return remindFlag;
    }

    public void setRemindFlag(Integer remindFlag)
    {
        this.remindFlag = remindFlag;
    }

    public Integer[] getMessageId()
    {
        return messageId;
    }

    public void setMessageId(Integer[] messageId)
    {
        this.messageId = messageId;
    }

    public MessageVo getMessageVo()
    {
        return messageVo;
    }

    public void setMessageVo(MessageVo messageVo)
    {
        this.messageVo = messageVo;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getBeginTime()
    {
        return beginTime;
    }

    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getContentInfo()
    {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo)
    {
        this.contentInfo = contentInfo;
    }

    public Integer getGroupId()
    {
        return groupId;
    }

    public void setGroupId(Integer groupId)
    {
        this.groupId = groupId;
    }

    public String getRequestType()
    {
        return requestType;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }

    public String getIdStr()
    {
        return idStr;
    }

    public void setIdStr(String idStr)
    {
        this.idStr = idStr;
    }

    public String getOnLine()
    {
        return onLine;
    }

    public void setOnLine(String onLine)
    {
        this.onLine = onLine;
    }
}
