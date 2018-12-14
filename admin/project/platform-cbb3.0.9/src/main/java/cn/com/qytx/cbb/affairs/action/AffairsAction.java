package cn.com.qytx.cbb.affairs.action;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.service.IAffairs;
import cn.com.qytx.cbb.affairs.vo.AffairsVo;
import cn.com.qytx.cbb.consts.CbbConst;
import cn.com.qytx.cbb.consts.MessageConst;
import cn.com.qytx.cbb.org.util.ExportExcel;
import cn.com.qytx.cbb.sysPara.domain.SysPara;
import cn.com.qytx.cbb.sysPara.service.ISysPara;
import cn.com.qytx.cbb.util.DateUtils;
import cn.com.qytx.cbb.util.StringUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * 功能:事务提醒功能action
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-3-22
 * 修改日期: 2013-3-22
 * 修改列表:
 */
public class AffairsAction extends BaseActionSupport
{
	
    /**
     * 系统参数服务类
     */
    @Autowired
    private ISysPara sysParaService;
	
	/**
	 * 任务管理
	 */
    @Autowired
    private IAffairs taskServiceImp;
	
    /**
     * 序列号
     */
    private static final long serialVersionUID = 2314184560157105391L;

    /**
     * 日志类
     */
    private static final Logger LOGGER = Logger.getLogger(AffairsAction.class);

    /**
     * 系统参数设置
     */
    private static SysPara affairSysPara = null;

    /**
     * 事务提醒service
     */
    @Autowired
    private IAffairs affairsImpl;



    /**
     * 事务提醒Id集合
     */
    private Integer[] affairsId;

    /**
     * 重发事务提醒Id
     */
    private Integer retransmissionId;

    /**
     * 事务提醒状态
     */
    private Integer remindFlag;

    /**
     * 事务查询Vo
     */
    private AffairsVo affairsVo;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 系统参数设置
     */
    private SysPara sysPara;

    /**
     * 分页获取接收到的事务提醒信息
     */
    public void getAffairsPage() throws Exception
    {
        try
        {
            Page<Affairs> pageInfo = null;
            List<Sort.Order> orders = new ArrayList<Sort.Order>();
//            orders.add(new Sort.Order(Sort.Direction.DESC,"remindFlag"));
            orders.add(new Sort.Order(Sort.Direction.DESC, "remindTime"));
            Sort sort = new Sort(orders);
            // 查询分页数据信息
            if (null != remindFlag)
            {
                pageInfo = affairsImpl.findPageByUserId(this.getPageable(sort), getLoginUser().getUserId(), remindFlag);
            }

            // 获取结果
            List<Affairs> affairsList = null;
            if (null!=pageInfo) {
            	affairsList = pageInfo.getContent();
			}
           
            // 把事务信息信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (affairsList != null)
            {
                // 汇总ID
                Integer[] idList = new Integer[affairsList.size()];

                // 获取序号
                int i = (this.getPageable().getPageNumber() - 1) * this.getIDisplayLength() + 1;
                int j = 0;
                Map<String, Object> map;
                for (Affairs tempAffairs : affairsList)
                {
                    map = new HashMap<String, Object>();

                    // 序号
                    map.put("no", i);

                    // id
                    map.put("id", tempAffairs.getId());
                    idList[j++] = tempAffairs.getId();

                    // 发送人
                    String fromUserName = tempAffairs.getAffairsBody().getFromUserInfo().getUserName();
                    map.put("fromUserName", fromUserName);

                    // 内容
                    String contentInfo = tempAffairs.getAffairsBody().getContentInfo();
                    map.put("contentInfo", null == contentInfo ? "" : contentInfo);

                    // 类型
                    String smsType = tempAffairs.getAffairsBody().getSmsType();
                    map.put("smsType", smsType);

                    // 发送时间
                    Timestamp sendTime = tempAffairs.getRemindTime();
                    map.put("sendTime",null == sendTime?"":DateUtils.dateToStrForDynamic(sendTime));
                    // 提醒url
                    String remindUrl = tempAffairs.getAffairsBody().getRemindUrl();
                    map.put("remindUrl", remindUrl);
                    if(remindUrl.indexOf("/dom/public!taskIsDelete.action")>=0||remindUrl.indexOf("/jbpmflow/detailSearch!urlToRollbackTofirstPersonForAffire")>=0
                    		||remindUrl.indexOf("/jbpmflow/detailSearch!taskIsEndForAffire.action")>=0||remindUrl.indexOf("/jbpmApp/myjob_urlToRollbackToOtherPerson.action")>=0){
                    	map.put("status_1", "2");
                    }else{
                    	map.put("status_1", "1");
                    }
                    
                    mapList.add(map);
                    i++;
                }

                // 如果为查询未确认事务提醒,则更改状态为已接受
                if (MessageConst.SENDED == remindFlag)
                {
                    affairsImpl.updateReadedAffairs(idList, MessageConst.RECEIVED);
                }
            }

           this.ajaxPage(pageInfo, mapList);
        }
        catch (Exception e)
        {
            LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 删除事务提醒信息
     */
    public void deleteAffairs()
    {
        try
        {
            // 基本参数校验
            if (null == affairsId)
            {
                ajax("param error");
                return;
            }

            // 删除联系人信息
            affairsImpl.deleteBatch(affairsId);
            ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAffairs error. ", e);
        }
    }

    /**
     * 设置事务提醒信息为已读
     */
    public void updateReadedAffairs()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 基本参数校验
            if (null == affairsId)
            {
                out.print("param error");
                return;
            }

            // 设置事务提醒信息为已读
            affairsImpl.updateReadedAffairs(affairsId, MessageConst.READED);
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("updateReadedAffairs error. ", e);
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
     * 根据人员Id设置未接受的事务变更为已接受
     */
    public void updateReceivedAffairs()
    {
        PrintWriter out = null;
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 设置事务提醒信息为已接受
            affairsImpl.updateReceivedAffairs(user.getUserId(), MessageConst.RECEIVED);
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("updateReceivedAffairs error. ", e);
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
     * 删除所有已读事务提醒信息
     */
    public void deleteAllReaded()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除联系人信息
            affairsImpl.deleteAllReaded(user.getUserId());
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllReaded error. ", e);
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
     * 删除所有未读事务提醒信息
     */
    public void deleteAllUnReaded()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除联系人信息
            affairsImpl.deleteAllUnReaded(user.getUserId());
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllReaded error. ", e);
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
     * 删除所有事务提醒信息
     */
    public void deleteAllAffairs()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除所有事务提醒信息
            affairsImpl.deleteAllAffairs(user.getUserId());
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
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
     * 全部标记为已读
     */
    public void updateAllReaded()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 全部标记为已读
            affairsImpl.updateAllReaded(user.getUserId());
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
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
     * 按模块标记为已读
     */
    public void updateModuleReaded()
    {
        PrintWriter out = null;
        try
        {
            // 初始化输出流
            out = this.getResponse().getWriter();

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 全部标记为已读
            affairsImpl.updateModuleReaded(user.getUserId(), moduleName);
            out.print("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
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
     * 获取发送的事务提醒记录
     */
    public void getSendAffairsPage()
    {
            // 分页信息
            Page<Affairs> pageInfo = null;

            // 查询分页数据信息
            pageInfo = affairsImpl.findSendPageByUserId(this.getPageable(), getLoginUser().getUserId());

            // 获取结果
            List<Affairs> affairsList = pageInfo.getContent();

            // 把发送的事务提醒信息填充到map里面
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (affairsList != null)
            {
                // 汇总ID
                Integer[] idList = new Integer[affairsList.size()];

                // 获取序号
                int i = (this.getPageable().getPageNumber() - 1) * this.getIDisplayLength() + 1;
                int j = 0;
                for (Affairs tempAffairs : affairsList)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    // 序号
                    map.put("no", i);
                    // id
                    map.put("id", tempAffairs.getId());
                    idList[j++] = tempAffairs.getId();
                    // 发送人
                    String fromUserName = tempAffairs.getAffairsBody().getFromUserInfo().getUserName();
                    map.put("fromUserName", fromUserName);
                    // 收信人
                    String toUserName = tempAffairs.getToUserInfo().getUserName();
                    map.put("toUserName", toUserName);
                    // 内容
                    String contentInfo = tempAffairs.getAffairsBody().getContentInfo();
                    map.put("contentInfo", null == contentInfo? "" : contentInfo);
                    // 类型
                    String smsType = tempAffairs.getAffairsBody().getSmsType();
                    map.put("smsType", smsType);
                    // 发送时间
                    Timestamp sendTime = tempAffairs.getRemindTime();
                    map.put("sendTime",null==sendTime?"": DateTimeUtil.dateToString(sendTime, CbbConst.TIME_FORMAT_STR));
                    // 状态
                    Integer remindFlag = tempAffairs.getRemindFlag();
                    map.put("remindFlag", remindFlag);
                    mapList.add(map);
                    i++;
                }
            }
            this.ajaxPage(pageInfo,mapList);
    }

    /**
     * 删除所有事务提醒信息
     */
    public void deleteSendAffairs()
    {
        try
        {
            // 删除发送的事务提醒信息
            affairsImpl.deleteSendAffairs(affairsId);
            this.ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
        }
    }

    /**
     * 删除已提醒收信人提醒
     */
    public void deleteAllSendReaded()
    {
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除已提醒收信人提醒
            affairsImpl.deleteAllSendReaded(user.getUserId());
            this.ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
        }
    }

    /**
     * 删除所有已发送的事务提醒
     */
    public void deleteAllSendAffairs()
    {
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除所有已发送的事务提醒
            affairsImpl.deleteAllSendAffairs(user.getUserId());
            this.ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
        }
    }

    /**
     * 删除收信人已删除提醒
     */
    public void deleteToUserDeleted()
    {
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 删除所有已发送的事务提醒
            affairsImpl.deleteToUserDeleted(user.getUserId());
            this.ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
        }
    }

    /**
     * 重发事务提醒
     */
    public void retransmissionAffairs()
    {
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 重发事务提醒
            if (affairsImpl.retransmissionAffairs(retransmissionId, user))
            {
                this.ajax("success");
            }
            else
            {
                this.ajax("error");
            }
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteAllAffairs error. ", e);
        }
    }

    /**
     * 获取所有事务提醒信息
     */
    public void getAllAffairs()
    {
        PrintWriter out = null;
        try
        {
            out = this.getResponse().getWriter();
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            affairsVo.setUserinfo(user);

            // 获取结果
            List<Affairs> affairsList = affairsImpl.getAllAffairsByVo(affairsVo);

            if (null != affairsList && affairsList.size() > 200){
                affairsList = affairsList.subList(0, 200);
            }
            
            // 组装结果信息
            List<Map<String, Object>> mapList = analyzeResult(affairsList);
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            Gson json = new Gson();
            String jsons = json.toJson(jsonMap);
            out.print(jsons);
        }
        catch (Exception e)
        {
        	LOGGER.error("getAddressGroupList error. ", e);
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
     * 根据vo导出事务提醒
     */
    public void exportAffairsByVo()
    {
        HttpServletResponse response = this.getResponse();
        response.setContentType("application/vnd.ms-excel");

        OutputStream outp = null;
        try
        {
            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            affairsVo.setUserinfo(user);
            List<Affairs> affairsList = affairsImpl.getAllAffairsByVo(affairsVo);

            // 把联系人信息填充到map里面 // 解决中文 文件名问题)
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String("内部提醒.xls".getBytes(), "iso8859-1"));
            outp = response.getOutputStream();
            List<Map<String, Object>> mapList = analyzeResult(affairsList);

            ExportExcel exportExcel = new ExportExcel(outp, getExportHeadList(), mapList,
                    getExportKeyList());
            exportExcel.export();
        }
        catch (Exception e)
        {
        	LOGGER.error("getAddressGroupList error. ", e);
        }
    }

    /**
     * 功能：将结果信息转成table需要的格式
     * @param affairsList affairsList
     * @return List<Map<String, Object>>
     */
    private List<Map<String, Object>> analyzeResult(List<Affairs> affairsList)
    {
        // 把事务信息信息填充到map里面
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (affairsList != null)
        {
            for (Affairs tempAffairs : affairsList)
            {
                Map<String, Object> map = new HashMap<String, Object>();

                // id
                map.put("id", tempAffairs.getId());

                // 发送人/接收人
                String fromUserName = "";
                if (MessageConst.USERTYPE_SEND == affairsVo.getUserType())
                {
                    fromUserName = tempAffairs.getAffairsBody().getFromUserInfo().getUserName();
                }
                else if (MessageConst.USERTYPE_RECEIVE == affairsVo.getUserType())
                {
                    fromUserName = tempAffairs.getToUserInfo().getUserName();
                }
                map.put("fromUserName", fromUserName);

                // 内容
                String contentInfo = tempAffairs.getAffairsBody().getContentInfo();
                map.put("contentInfo", null == contentInfo ? "" : contentInfo);

                // 发送时间
                Timestamp sendTime = tempAffairs.getRemindTime();
                map.put("sendTime",null==sendTime?"": DateTimeUtil.dateToString(sendTime, CbbConst.TIME_FORMAT_STR));

                // 提醒url
                String remindUrl = tempAffairs.getAffairsBody().getRemindUrl();

                // 状态
                Integer remindFlag = tempAffairs.getRemindFlag();
                if (MessageConst.READED == remindFlag)
                {
                    map.put("remindFlag", MessageConst.REMIND_YES_STR);
                }
                else
                {
                    map.put("remindFlag", MessageConst.REMIND_NO_STR);
                }

                // 类别
                map.put("smsType", tempAffairs.getAffairsBody().getSmsType());
                map.put("remindUrl", remindUrl);
                mapList.add(map);
            }
        }
        return mapList;
    }

    private List<String> getExportHeadList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("类别");
        if (MessageConst.USERTYPE_SEND == affairsVo.getUserType())
        {
            headList.add("发送人");
        }
        else if (MessageConst.USERTYPE_RECEIVE == affairsVo.getUserType())
        {
            headList.add("收信人");
        }

        headList.add("内容");
        headList.add("发送时间");
        headList.add("提醒");
        return headList;
    }

    private List<String> getExportKeyList()
    {
        List<String> headList = new ArrayList<String>();
        headList.add("smsType");
        headList.add("fromUserName");
        headList.add("contentInfo");
        headList.add("sendTime");
        headList.add("remindFlag");
        return headList;
    }

    /**
     * 根据VO条件删除微讯信息
     */
    public void deleteAffairsByVo()
    {
        try
        {
            // 基本参数校验
            if (null == affairsVo)
            {
                ajax("param error");
                return;
            }

            // 首先获取登录人基本信息
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
            affairsVo.setUserinfo(user);

            // 删除联系人信息
            affairsImpl.deleteAffairsByVo(affairsVo);
            ajax("success");
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteMessageById error. ", e);
        }

    }

    /**
     * 功能：获取提醒弹屏页面
     * @return String
     * @throws InterruptedException 
     */
    public String getShortcutMenu() throws InterruptedException
    {
        UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");
        // 查询所有未确认的事务提醒信息
        List<Affairs> list = affairsImpl.findUnReadByUserId(user.getUserId());
        
        // 将查询结果转换成Map格式,页面展示
        Map<String, List<Affairs>> affairsMap = transformAffairsList(list);
        int affairsSize = 0;
        if (null != list && !list.isEmpty())
        {
            affairsSize = list.size();
        }
        this.getRequest().setAttribute("affairsSize", affairsSize);
        this.getRequest().setAttribute("affairsMap", affairsMap);
        return SUCCESS;
    }

    /**
     * 功能：是否有最新的事务提醒
     */
    public void getNewAffairs()
    {
        try
        {
            UserInfo user = (UserInfo) this.getSession().getAttribute("adminUser");

            // 查询所有未确认的事务提醒信息
            List<Affairs> list = affairsImpl.findUnReadByUserId(user.getUserId());
            if ((null != list && !list.isEmpty()))
            {
                ajax("success");
            }
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteMessageById error. ", e);
        }
    }
    
    /**
     * 功能：是否有最新的事务提醒
     */
    public void getNewAffairsNum()
    {  
        // 查询所有未确认的事务提醒信息
        List<Affairs> list = affairsImpl.findUnReadByUserId(getLoginUser().getUserId());
        if (null != list)
        {
            ajax(list.size());
        }else{
            ajax("0");
        }       
    }
    
    /**
     * 功能：快速查询是否有最新的事务提醒
     */
    public void getQuickAffairs()
    {
        try
        {
            // 查询所有未确认的事务提醒信息            
            if (MessageTimerTask.hasUnreadAffairs(getLoginUser().getUserId()))
            {
                ajax("success");
            }
        }
        catch (Exception e)
        {
        	LOGGER.error("deleteMessageById error. ", e);
        }
    }

    private Map<String, List<Affairs>> transformAffairsList(List<Affairs> list)
    {
        Date now = new Date();
        Map<String, List<Affairs>> map = new HashMap<String, List<Affairs>>();
        if (null != list && !list.isEmpty())
        {
            for (Affairs affairs : list)
            {
                // 更新时间
                String differenceTime = DateTimeUtil
                        .getTimeDifference(now, affairs.getRemindTime());
                affairs.setLimitTime(differenceTime);

                // 首先判断此类型的事务是否已添加
                List<Affairs> tempList = map.get(affairs.getAffairsBody().getSmsType());
                if(tempList!=null&&!tempList.isEmpty()){
                	tempList.add(affairs);
                }else{
                	tempList = new ArrayList<Affairs>();
                	tempList.add(affairs);
                }
                map.put(affairs.getAffairsBody().getSmsType(), tempList);
            }
        }
        return map;
    }

    /**
     * 功能：获取事务提醒配置信息
     */
    public void queryAffairsManage()
    {
            // 首先配置信息
            if (null == affairSysPara)
            {
                SysPara sysPara = sysParaService.findParaByName("affairManage");
                if (null != sysPara)
                {
                    affairSysPara = sysPara;
                }
            }
            // 组装结果信息
            Map<Integer, String> affairsmap = CbbConst.AFFAIRSMAP;
            Set<Map.Entry<Integer, String>> set = affairsmap.entrySet();
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            Integer iii=1;
            for (Map.Entry<Integer, String> entry : set)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                //序号
                map.put("no", iii);
                iii++;
                // 模块名称
                String moduleName = entry.getValue();
                map.put("moduleName", moduleName);

                // 模块Id
                Integer moduleId = entry.getKey();
                map.put("moduleId", moduleId);

                // 模块权限
                String affairPriv = getAffairPriv(moduleId, null != affairSysPara ? affairSysPara.getParaValue() : null);
                map.put("affairPriv", affairPriv);
                mapList.add(map);
            }
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("aaData", mapList);
            ajax(jsonMap);
    }

    /**
     * 功能：更新事务提醒配置信息
     */
    public void updateAffairManage()
    {
            // 入参校验
            if (null == sysPara)
            {
                this.ajax("error");
                return;
            }
            // 首先获取配置信息
            if (null == affairSysPara)
            {
                SysPara tempSysPara = sysParaService.findParaByName("affairManage");
                if (null != tempSysPara)
                {
                    affairSysPara = tempSysPara;
                }
                else
                {
                    affairSysPara = new SysPara();
                }

            }
            // 更新配置信息
            affairSysPara.setParaName("affairManage");
            affairSysPara.setParaValue(sysPara.getParaValue());
            sysParaService.saveOrUpdate(affairSysPara);
            this.ajax("success");
    }

    private String getAffairPriv(Integer moduleId, String affairManage)
    {

        if (StringUtil.isEmpty(affairManage))
        {
            return "0|0";
        }
        StringBuilder sb = new StringBuilder();
        String[] affairsArray = affairManage.split("\\|");
        // 允许事务提醒 1表示允许, 0表示不允许
        String allow = affairsArray[0];
        if (StringUtil.isEmpty(allow))
        {
            sb.append("0");
        }
        else
        {
            if (allow.indexOf("," + moduleId + ",") >= 0)
            {
                sb.append("1");
            }
            else
            {
                sb.append("0");
            }
        }

        // 默认事务提醒 1表示允许, 0表示不允许
        if (affairsArray.length > 1)
        {
            String defaultSelect = affairsArray[1];
            if (defaultSelect.indexOf("," + moduleId + ",") >= 0)
            {
                sb.append("|1");
            }
            else
            {
                sb.append("|0");
            }
        }
        else
        {
            sb.append("|0");
        }
        return sb.toString();
    }

    /**
     * 功能：根据模块名获取是否发送及默认选中发送事务提醒
     */
    public void getAffairPriv()
    {
            // 入参校验
            if (StringUtil.isEmpty(moduleName))
            {
                this.ajax("error");
                return;
            }

            // 首先获取配置信息
            if (null == affairSysPara)
            {
                SysPara tempSysPara = sysParaService.findParaByName("affairManage");
                if (null != tempSysPara)
                {
                    affairSysPara = tempSysPara;
                }
                else
                {
                    affairSysPara = new SysPara();
                }
            }

            // 获取根据模块名称获取对应的模块ID
            Map<Integer, String> affairsmap = CbbConst.AFFAIRSMAP;
            Set<Map.Entry<Integer, String>> set = affairsmap.entrySet();

            for (Map.Entry<Integer, String> entry : set)
            {
                // 模块名称
                String tempModuleName = entry.getValue();

                if (moduleName.equals(tempModuleName))
                {
                    Integer moduleId = entry.getKey();
                    String affairPriv = getAffairPriv(moduleId, affairSysPara.getParaValue());
                    this.ajax(affairPriv); 
                    return;
                }
            }
            this.ajax("0|0");
    }


    public Integer[] getAffairsId()
    {
        return affairsId;
    }

    public void setAffairsId(Integer[] affairsId)
    {
        this.affairsId = affairsId;
    }

    public Integer getRemindFlag()
    {
        return remindFlag;
    }

    public void setRemindFlag(Integer remindFlag)
    {
        this.remindFlag = remindFlag;
    }

    public Integer getRetransmissionId()
    {
        return retransmissionId;
    }

    public void setRetransmissionId(Integer retransmissionId)
    {
        this.retransmissionId = retransmissionId;
    }

    public AffairsVo getAffairsVo()
    {
        return affairsVo;
    }

    public void setAffairsVo(AffairsVo affairsVo)
    {
        this.affairsVo = affairsVo;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public SysPara getSysPara()
    {
        return sysPara;
    }

    public void setSysPara(SysPara sysPara)
    {
        this.sysPara = sysPara;
    }
}
