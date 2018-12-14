package cn.com.qytx.cbb.org.action.mobile;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.org.service.IMobile;
import cn.com.qytx.cbb.push.service.PushPlatService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.ThreadPoolUtils;

import com.google.gson.Gson;

/**
 * 手机接口Action
 * User: 黄普友
 * Date: 14-6-26
 * Time: 下午1:59
 */
public class MobileAction extends BaseActionSupport {
//	private static String masterSecret="c2130125347e1d34684a50b4";
//	private static String appKey="579901981add492059e9ce07";
	private static final long serialVersionUID = 1L;
	protected final static Logger logger = LoggerFactory.getLogger(MobileAction.class);
	public static final int MAX = Integer.MAX_VALUE;
    
    public static final int MIN = MAX/2;
    @Autowired
    IMobile mobileImpl;

    @Autowired
    PushPlatService pushPlatService;
    
    @Autowired
    IUser userService;
    public String execute()
    {
        try
        {
            HttpServletRequest request=this.getRequest();
            String method = request.getParameter("method");
            String ret="";
            if(StringUtils.isBlank(method))
            {
                ret="101||method不能为空!";
            }
            else
            {

                if(method.equals("addUsersInfo"))
                {
                    //添加人员
                    ret=addUsersInfo(request);
                }
                else if(method.equals("getBasicInfo"))
                {
                    //获取基础数据
                    ret=getBasicInfo(request);
                }
                else if(method.equals("addGroup"))
                {
                    //添加部门/群组信息
                    ret=addGroup(request);
                }
                else if(method.equals("updateGroup"))
                {
                    //修改部门/群组信息
                    ret=updateGroup(request);
                }
                else if(method.equals("deleteGroup"))
                {
                    //删除部门/群组信息
                    ret=deleteGroup(request);
                }
                else if(method.equals("deleteUsersInfo"))
                {
                    //删除人员信息
                    ret=deleteUsersInfo(request);
                }
                else if(method.equals("updateUsersInfo"))
                {
                    //修改人员信息
                    ret=updateUsersInfo(request);
                }
                else if(method.equals("addGroupUsers"))
                {
                    //添加部门/群组人员对应
                    ret=addGroupUsers(request);
                }
                else if(method.equals("deleteGroupUsers"))
                {
                    //删除部门/群组人员对应信息
                    ret=deleteGroupUsers(request);
                }
                else if(method.equals("userOrder"))
                {
                    //人员排序
                    ret=userOrder(request);
                }
                else if(method.equals("groupOrder"))
                {
                    //部门排序
                    ret=groupOrder(request);
                }
            }
            LOGGER.info("返回结果="+ret);
            ajax(ret);
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            ajax("102||数据库操作错误");
        }
        return null;
    }
    /**
     * 修改人员信息
     * @param request
     * @return
     */
    private String groupOrder(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }

            String sortList=null;
            if(request.getParameter("sortList")!=null)
            {
                sortList=request.getParameter("sortList");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }

            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(sortList))
            {
                ret = "101||要排序的部门ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=groupOrder,companyId="+companyId+",userId="+userId+",userName="+userName+",sortList="+sortList);
            ret=mobileImpl.groupOrder(companyId,userId,userName,sortList);
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 修改人员信息
     * @param request
     * @return
     */
    private String userOrder(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }

            String sortList=null;
            if(request.getParameter("sortList")!=null)
            {
                sortList=request.getParameter("sortList");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }

            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(sortList))
            {
                ret = "101||要排序的人员ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=userOrder,companyId="+companyId+",userId="+userId+",userName="+userName+",sortList="+sortList);
            ret=mobileImpl.userOrder(companyId,userId,userName,sortList);
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 修改人员信息
     * @param request
     * @return
     */
    private String deleteGroupUsers(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }
            Integer groupId=null;
            if(request.getParameter("groupId")!=null)
            {
                groupId=Integer.parseInt(request.getParameter("groupId"));
            }
            String userIds=null;
            if(request.getParameter("userIds")!=null)
            {
                userIds=request.getParameter("userIds");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(groupId==null)
            {
                ret = "101||部门ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userIds))
            {
                ret = "101||要删除的人员ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=deleteGroupUsers,companyId="+companyId+",userId="+userId+",userName="+userName+",groupId="+groupId+",userIds="+userIds);
            ret=mobileImpl.deleteGroupUsers(companyId,userId,userName,groupId,userIds);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 修改人员信息
     * @param request
     * @return
     */
    private String addGroupUsers(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }
            Integer groupId=null;
            if(request.getParameter("groupId")!=null)
            {
                groupId=Integer.parseInt(request.getParameter("groupId"));
            }
            String userIds=null;
            if(request.getParameter("userIds")!=null)
            {
                userIds=request.getParameter("userIds");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(groupId==null)
            {
                ret = "101||部门ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userIds))
            {
                ret = "101||要添加的人员ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=addGroupUsers,companyId="+companyId+",userId="+userId+",userName="+userName+",groupId="+groupId+",userIds="+userIds);
            ret=mobileImpl.addGroupUsers(companyId,userId,userName,groupId,userIds);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 修改人员信息
     * @param request
     * @return
     */
    private String updateUsersInfo(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }

            String userJson=null;
            if(request.getParameter("userJson")!=null)
            {
                userJson=request.getParameter("userJson");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userJson))
            {
                ret = "101||要修改的人员信息不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=updateUsersInfo,companyId="+companyId+",userId="+userId+",userName="+userName+",userJson="+userJson);
            ret=mobileImpl.updateUsersInfo(companyId,userId,userName,userJson);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 删除人员信息
     * @param request
     * @return
     */
    private String deleteUsersInfo(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }

            String userIds=null;
            if(request.getParameter("userIds")!=null)
            {
                userIds=request.getParameter("userIds");
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userIds))
            {
                ret = "101||要删除的人员ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=deleteUsersInfo,companyId="+companyId+",userId="+userId+",userName="+userName+",userIds="+userIds);
//            ret=mobileImpl.deleteUsersInfo(companyId,userId,userName,userIds);
            userService.deleteUserByIds(userIds, false,companyId);
        	sendPush(companyId);
            return "100||删除成功";
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    private String deleteGroup(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }


            Integer groupId=null;
            if(request.getParameter("groupId")!=null)
            {
                groupId=Integer.parseInt(request.getParameter("groupId"));
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(groupId==null)
            {
                ret = "101||部门/群组ID不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=deleteGroup,companyId="+companyId+",userId="+userId+",userName="+userName+",groupId="+groupId);

            ret=mobileImpl.deleteGroup(companyId,userId,userName,groupId);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    private String updateGroup(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }

            String groupName="";
            if(request.getParameter("groupName")!=null)
            {
                groupName=request.getParameter("groupName");
            }
            Integer groupId=null;
            if(request.getParameter("groupId")!=null)
            {
                groupId=Integer.parseInt(request.getParameter("groupId"));
            }
            Integer parentId=null;
            if(request.getParameter("parentId")!=null)
            {
                parentId=Integer.parseInt(request.getParameter("parentId"));
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(groupId==null)
            {
                ret = "101||部门/群组ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(groupName))
            {
                ret = "101||部门/群组名称不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=updateGroup,companyId="+companyId+",userId="+userId+",userName="+userName+",groupId="+groupId+
                        ",parentId="+parentId+",groupName="+groupName);

            ret=mobileImpl.updateGroup(companyId,userId,userName,groupId,parentId,groupName);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }

    /**
     * 添加部门
     * @param request
     * @return
     */
    private String addGroup(HttpServletRequest request)
    {
        try
        {
            String ret="";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName="";
            if(request.getParameter("userName")!=null)
            {
                userName=request.getParameter("userName");
            }
            Integer parentId=0;
            if(request.getParameter("parentId")!=null)
            {
                parentId=Integer.parseInt(request.getParameter("parentId"));
            }
            String groupName="";
            if(request.getParameter("groupName")!=null)
            {
                groupName=request.getParameter("groupName");
            }

            Integer groupType=null;
            if(request.getParameter("groupType")!=null)
            {
                groupType=Integer.parseInt(request.getParameter("groupType"));
            }
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(groupType==null)
            {
                ret = "101||类型不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=addGroup,companyId="+companyId+",userId="+userId+",userName="+userName+",parentId="+parentId+
                    ",parentId="+parentId+",groupName="+groupName+",groupType="+groupType);

            ret=mobileImpl.addGroup(companyId,userId,userName,parentId,groupName,groupType);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 获取基础信息
     * @return
     */
     private String getBasicInfo(HttpServletRequest request)
     {
         try
         {
        	 
            String ret="";
             Integer companyId=null;
             if(request.getParameter("companyId")!=null)
             {
                 companyId=Integer.parseInt(request.getParameter("companyId"));
             }
             //登录人
             Integer userId=null;
             if(request.getParameter("userId")!=null)
             {
                 userId=Integer.parseInt(request.getParameter("userId"));
             }
             Integer infoType=null;
             if(request.getParameter("infoType")!=null)
             {
                 infoType=Integer.parseInt(request.getParameter("infoType"));
             }
             Integer isOrg=0;
             if(request.getParameter("isOrg")!=null)
             {
                 isOrg=Integer.parseInt(request.getParameter("isOrg"));
             }
             String lastUpdateTime=request.getParameter("lastUpdateTime");
             if(companyId==null)
             {
                 ret = "101||单位ID不能为空" ;
                 return ret;
             }
             if(userId==null)
             {
                 ret = "101||操作人员ID不能为空" ;
                 return ret;
             }
             if(infoType==null)
             {
                 ret = "101||获取类型不能为空" ;
                 return ret;
             }
             logger.info("接收到请求: method=getBasicInfo,companyId="+companyId+",userId="+userId+",lastUpdateTime="+lastUpdateTime+",infoType="+infoType+",isOrg="+isOrg);

             ret=mobileImpl.getBasicInfo(companyId,userId,lastUpdateTime,infoType,isOrg);
             logger.info("手机端返回基础数据："+ret);
             return ret;
         }
         catch (Exception ex)
         {
        	 ex.printStackTrace();
             LOGGER.error("手机端获取基础数据失败:"+ex);
             return "102||数据库操作错误";
         }
     }
    /**
     * 添加人员信息
     * @param request
     * @return
     */
    private String addUsersInfo(HttpServletRequest request)
    {

        try
        {
            String ret="100||操作成功";
            Integer companyId=null;
            if(request.getParameter("companyId")!=null)
            {
                companyId=Integer.parseInt(request.getParameter("companyId"));
            }
            //登录人
            Integer userId=null;
            if(request.getParameter("userId")!=null)
            {
                userId=Integer.parseInt(request.getParameter("userId"));
            }
            String userName=request.getParameter("userName");
            String userJson=request.getParameter("userJson");
            if(companyId==null)
            {
                ret = "101||单位ID不能为空" ;
                return ret;
            }
            if(userId==null)
            {
                ret = "101||操作人员ID不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userName))
            {
                ret = "101||操作人员姓名不能为空" ;
                return ret;
            }
            if(StringUtils.isBlank(userJson))
            {
                ret = "101||人员信息不能为空" ;
                return ret;
            }
            LOGGER.info("接收到请求,method=addUsersInfo,companyId="+companyId+",userId="+userId+",userName="+userName+",userJson="+userJson);
            
            ret=mobileImpl.addUsersInfo(companyId,userId,userName,userJson);
            if(ret!=null&&ret.startsWith("100")){
            	sendPush(companyId);
            }
            return ret;
        }
        catch (Exception ex)
        {
        	ex.printStackTrace();
            LOGGER.error(ex.getMessage());
            return "102||数据库操作错误";
        }
    }
    /**
     * 
     * 功能：发送推送
     * @param companyId
     */
    public void sendPush(final Integer companyId){
        	ThreadPoolUtils.getInstance().getThreadPool().execute(new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						String msgTitle="";
						String msgContent="";
						String androidTag = "company_android_"+companyId;
						String iosTag = "company_ios_"+companyId;
						Map<String,String> hm = new HashMap<String,String>();
						hm.put("mt", "upbasicdata");
						String msgContentType="";
						String path = this.getClass().getResource("/application.properties").getPath();
						FileInputStream file=new FileInputStream(path);
						Properties propertie=new Properties();
						propertie.load(file);
						String masterSecret=propertie.getProperty("masterSecret");
						String appKey=propertie.getProperty("pushUrl");
						
						String extra = new Gson().toJson(hm);
						
						// 推送至android
						pushPlatService.pushJpushToPlat(companyId, androidTag, "android",
								msgContent, msgTitle, extra, "tag");
						// 推送至ios
						pushPlatService.pushJpushToPlat(companyId, iosTag, "ios", msgContent,
								msgTitle, extra, "tag");
						
					} catch (Exception e) {
						e.printStackTrace();
					}//要调用的url
				}
			}));
    }
}
