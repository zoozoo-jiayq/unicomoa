package cn.com.qytx.oa.message.action;

import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MessageOnloadServlet extends HttpServlet
{
    /**
     * 描述含义
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initialization of the servlet. <br>
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException
    {
        MessageTimerTask mtt = new MessageTimerTask();
        mtt.setServletContext(this.getServletContext());
        Timer timer = new Timer();
        timer.schedule(mtt, 1000, 3000);
    }
}
