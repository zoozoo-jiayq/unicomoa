package cn.com.qytx.cbb.affairs.action;

import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.com.qytx.cbb.affairs.service.IAffairs;

/**
 * 事务及微讯聊天消息通知
 */
public class MessageTimerTask extends TimerTask
{
    /**
     * 日志类
     */
    private static final Logger logger = Logger.getLogger(MessageTimerTask.class);

    private ServletContext servletContext;

    /**
     * 含有未读信息的人员Id列表
     */
    public static List<Integer> messageUnreadUserList;

    /**
     * 有未接受的事务提醒人员Id列表
     */
    public static List<Integer> affairsSendUserList;

    @Override
    public void run()
    {
        try
        {
            WebApplicationContext wac = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(servletContext);

            // 获取有为接受的事务提醒人员
            IAffairs affairsImpl = (IAffairs) wac.getBean(IAffairs.class);
            setAffairsSendUserList(affairsImpl.findUnReadAffairsUser());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error("MessageTimerTask error.", ex);

            // 出现异常时,清空数据列表信息,防止页面重复弹出提示信息
            setAffairsSendUserList(null);
            setMessageUnreadUserList(null);
        }

    }

    public ServletContext getServletContext()
    {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

    /**
     * 功能：是否有未读短信
     * @param userId
     * @return boolean
     */
    public static boolean hasUnreadMessage(Integer userId)
    {
        if (null != messageUnreadUserList && !messageUnreadUserList.isEmpty())
        {
            for (Integer tempUserId : messageUnreadUserList)
            {
                if (userId.intValue() == tempUserId.intValue())
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 功能：是否有未接受事务
     * @param userId
     * @return boolean
     */
    public static boolean hasUnreadAffairs(Integer userId)
    {
        if (null != affairsSendUserList && !affairsSendUserList.isEmpty())
        {
            for (Integer tempUserId : affairsSendUserList)
            {
                if (userId.intValue() == tempUserId.intValue())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static List<Integer> getMessageUnreadUserList()
    {
        return messageUnreadUserList;
    }

    public static void setMessageUnreadUserList(List<Integer> messageUnreadUserList)
    {
        MessageTimerTask.messageUnreadUserList = messageUnreadUserList;
    }

    public static List<Integer> getAffairsSendUserList()
    {
        return affairsSendUserList;
    }

    public static void setAffairsSendUserList(List<Integer> affairsSendUserList)
    {
        MessageTimerTask.affairsSendUserList = affairsSendUserList;
    }
}
