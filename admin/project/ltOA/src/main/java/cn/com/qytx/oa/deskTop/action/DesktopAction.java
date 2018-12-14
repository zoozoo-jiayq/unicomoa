/**
 *
 */
package cn.com.qytx.oa.deskTop.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.todocount.service.IDesktopTask;
import cn.com.qytx.oa.deskTop.domain.DesktopModule;
import cn.com.qytx.oa.deskTop.domain.DesktopPage;
import cn.com.qytx.oa.deskTop.service.IDesktop;
import cn.com.qytx.oa.util.CommonUtils;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.CbbConst;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功能:个人桌面相关处理Action类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-04-16
 * 修改日期: 2013-04-16
 * 修改人员: 汤波涛
 * 修改列表:
 */
public class DesktopAction extends BaseActionSupport {

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;
	private transient  Logger logger = Logger.getLogger(DesktopAction.class);
    private static final String SEPARATOR = ",";

//    private IDesktop desktopService;
    private ArrayList<IDesktopTask> desktopTaskServiceList;
    
    
    //事物提醒实现
    @Resource(name="desktopTaskAffairs")
    private IDesktopTask DesktopTaskAffairsImpl;
    
    
    private Integer moduleInfoId;
    private Integer desktopModuleId;
    private Integer desktopPageId;
    private String desktopModuleIds;//英文逗号分割
    private String desktopPageIds;//英文逗号分割
    private String moduleNames;//英文逗号隔开
    private String sysName;//系统名称 OA  或   HR

    @Autowired
    IDesktop desktopService;
    /**
     * 个人桌面主页
     *
     * @return success 返回到个人桌面主页
     */
    public String mainPage() {
        UserInfo user = getLoginUser();
        List<ModuleInfo> allModuleInfoList = getAllModuleInfoList();
        //检查该用户是否是第一次创建DesktopPage等
        this.desktopService.saveDefaultAfterChecked(user.getUserId(), user.getCompanyId(), allModuleInfoList);

        //获取该用户的Page和Page中的Module
        List<DesktopPage> desktopPageList = this.desktopService.findAllPages(user.getUserId());
        for (DesktopPage desktopPage : desktopPageList) {
            List<DesktopModule> desktopModuleList = this.desktopService.findAllModuleByPageId(desktopPage.getId());
            //检查用户私有的时候有权限再次使用
            for (int i = 0; i < desktopModuleList.size(); i++) {
                boolean hasPermission = false;
                for (ModuleInfo moduleInfo : allModuleInfoList) {
                    if (moduleInfo.getModuleId().equals(desktopModuleList.get(i).getModuleInfo().getModuleId())) {
                        hasPermission = true;
                        break;
                    }
                }
                if (!hasPermission) {
                    desktopModuleList.remove(i);
                    i--;
                } else {
                    setPageMenuJson(desktopModuleList.get(i));
                }
            }
            desktopPage.setDesktopModuleList(desktopModuleList);
        }
//        List<DesktopPage> desktopPageList = new ArrayList<DesktopPage>();
        Gson gson=new Gson();
        String desktopPageListJson=gson.toJson(desktopPageList);
        ActionContext.getContext().put("desktopPageListJson", desktopPageListJson);
        return SUCCESS;
    }

    //查询并赋值四级菜单的Json
    private void setPageMenuJson(DesktopModule desktopModule) {
        //查询四级子菜单
        Gson gson = new Gson();
        List<ModuleInfo> pageMenuModuleList = this.getPageMenuList(getAllModuleInfoList(), desktopModule.getModuleInfo().getModuleId());
        String pageMenuJson = gson.toJson(pageMenuModuleList);
        desktopModule.setPageMenuJson(pageMenuJson);
    }

    /**
     * 获取四级子功能列表
     *
     * @param moduleList 所有的ModuleList
     * @param parentId   父ID
     * @return list
     */
    private List<ModuleInfo> getPageMenuList(final List<ModuleInfo> moduleList, int parentId) {
        List<ModuleInfo> list = new ArrayList<ModuleInfo>();
        for (ModuleInfo module : moduleList) {
            if (module.getParentId().equals(parentId) && module.getModuleType().equals(2)) {
                list.add(module);
            }
        }
        return list;
    }

//    //删除桌面菜单项，DesktopModule
    public String deleteDesktopModule() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (this.desktopModuleId == null) {
                result = "deleteDesktopModule:desktopModuleId is null";
            } else {
                this.desktopService.deleteModule(this.desktopModuleId);
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

//    //删除桌面页，DesktopPage
    public String deleteDesktopPage() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (this.desktopPageId == null) {
                result = "deleteDesktopPage:desktopPageId is null";
            } else {
                this.desktopService.deletePage(this.desktopPageId);
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //重新排序桌面的module
    public String reorderDesktopModule() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.desktopModuleIds)) {
                result = "reorderDesktopModule:desktopModuleIds is null";
            } else {
                String[] ids = this.desktopModuleIds.split(SEPARATOR);
                for (int i = 0; i < ids.length; i++) {
                    if (StringUtils.isEmpty(ids[i].trim())) {
                        continue;
                    }
                    this.desktopService.reorderModule(Integer.parseInt(ids[i]), i);
                }
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //重新排序桌面的Page
    public String reorderDesktopPage() {
        PrintWriter out = null;
        String result = "";
        try {
            out = getResponse().getWriter();
            if (StringUtils.isEmpty(this.desktopPageIds)) {
                result = "reorderDesktopPage:desktopPageIds is null";
            } else {
                String[] ids = this.desktopPageIds.split(SEPARATOR);
                for (int i = 0; i < ids.length; i++) {
                    String id=ids[i];
                    if (StringUtils.isEmpty(id.trim())) {
                        continue;
                    }
                    this.desktopService.reorderPage(Integer.parseInt(id), i);
                }
            }
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //获取桌面任务
    public String getDesktopTasks() {
        PrintWriter out = null;
        String status = ERROR;
        Object content = "";
        try {
            out = getResponse().getWriter();
            if (CommonUtils.isEmpty(this.moduleNames)) {
                content = "getDesktopTasks:moduleNames is null";
            } else {
                String[] names = this.moduleNames.split(SEPARATOR);
                ArrayList<HashMap<String, Object>> taskList = new ArrayList<HashMap<String, Object>>();
                Map<String,IDesktopTask> map = this.getAllModuleNames();
                for (String moduleName : names) {
                    if (CommonUtils.isNotEmpty(moduleName)) {
                        IDesktopTask desktopTaskImpl = map.get(moduleName);
                        if (desktopTaskImpl != null) {
                            HashMap<String, Object> taskMap = new HashMap<String, Object>();
                            taskMap.put("moduleName", moduleName);
                            taskMap.put("taskCount", desktopTaskImpl.countOfTask(getLoginUser().getUserId()));
                            taskMap.put("taskUrl", desktopTaskImpl.getTaskUrl());
                            taskList.add(taskMap);
                        }
                    }
                }
                status = SUCCESS;
                content = taskList;
            }
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("status", status);
            jsonMap.put("content", content);
            Gson gson = new Gson();
            String json =gson.toJson(jsonMap);
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //根据moduleName获取对应的IDesktopTask实现类
    private IDesktopTask getDesktopTaskImpl(String moduleName) {
    	if(desktopTaskServiceList==null){
    		desktopTaskServiceList=getdesktopTaskServiceList();
    	}
        for (IDesktopTask desktopTaskImpl : this.desktopTaskServiceList) {
            if (CommonUtils.isNotEmpty(desktopTaskImpl.getModuleName())
                    && moduleName.equals(desktopTaskImpl.getModuleName())) {
                return desktopTaskImpl;
            }
        }
        return null;
    }

    private Map<String,IDesktopTask> getAllModuleNames(){
    	if(desktopTaskServiceList==null){
    		desktopTaskServiceList=getdesktopTaskServiceList();
    	}
    	Map<String,IDesktopTask> map = new HashMap<String, IDesktopTask>();
    	for (IDesktopTask desktopTaskImpl : this.desktopTaskServiceList) {
    		if (CommonUtils.isNotEmpty(desktopTaskImpl.getModuleName())) {
    			map.put(desktopTaskImpl.getModuleName(), desktopTaskImpl);
    		}
    	}
    	return map;
    }
    
    /**
     * 打开应用盒子的Action
     *
     * @return 应用盒子页面
     */
    public String appBoxPage() {
        UserInfo userInfo = getLoginUser();
        List<ModuleInfo> allModuleInfoList = getAllModuleInfoList();
        List<ModuleInfo> oneLevelModuleList = new LinkedList<ModuleInfo>();
        for (ModuleInfo moduleInfo : allModuleInfoList) {
            if (moduleInfo.getModuleLevel() == 1) {
                oneLevelModuleList.add(moduleInfo);
            }
        }
        List<DesktopPage> pageList = this.desktopService.findAllPages(userInfo.getUserId());
        ActionContext.getContext().put("pageList", pageList);
        ActionContext.getContext().put("oneLevelModuleList", oneLevelModuleList);
        return SUCCESS;
    }

    //添加一个桌面页
    public String getNotInDesktopModules() {
        PrintWriter out = null;
        String status = ERROR;
        Object content = null;
        try {
            out = getResponse().getWriter();
            if (this.moduleInfoId == null) {
                status = ERROR;
                content = "getNotInDesktopModules:parentId(moduleInfoId) is null";
            } else {
                UserInfo userInfo = getLoginUser();
                List<ModuleInfo> allModuleInfoList = getAllModuleInfoList();
                List<DesktopModule> userDesktopModuleList = this.desktopService.findAllModule(userInfo.getUserId());
                List<ModuleInfo> over2LevelModuleList = new LinkedList<ModuleInfo>();
                List<ModuleInfo> notInDesktopModuleList = new LinkedList<ModuleInfo>();
                for (ModuleInfo moduleInfo : allModuleInfoList) {
                    boolean notInDesktop = true;
                    for (DesktopModule desktopModule : userDesktopModuleList) {
                        //屏蔽用户已经加到桌面的
                        if (moduleInfo.getModuleId().equals(desktopModule.getModuleInfo().getModuleId())) {
                            notInDesktop = false;
                            break;
                        }
                    }
                    if (notInDesktop && moduleInfo.getModuleLevel() > 1) {
                        if (moduleInfo.getParentId().equals(this.moduleInfoId)) {//加入第二级
                            notInDesktopModuleList.add(moduleInfo);
                        } else {
                            over2LevelModuleList.add(moduleInfo);
                        }
                    }
                }
                //加入第三级菜单
                for (ModuleInfo moduleInfo : over2LevelModuleList) {
                    if (moduleInfo.getModuleLevel() == 3) {
                        for (ModuleInfo moduleInfoNotIn : notInDesktopModuleList) {
                            if (moduleInfoNotIn.getModuleLevel() == 2 && moduleInfo.getParentId().equals(moduleInfoNotIn.getModuleId())) {
                                notInDesktopModuleList.add(moduleInfo);
                                break;
                            }
                        }
                    }
                }

//                //加入第四级
//                for (ModuleInfo moduleInfo : over2LevelModuleList) {
//                    if (moduleInfo.getModuleLevel() == 4) {
//                        for (ModuleInfo moduleInfoNotIn : notInDesktopModuleList) {
//                            if (moduleInfoNotIn.getModuleLevel() == 3 && moduleInfo.getParentId().equals(moduleInfoNotIn.getModuleId())) {
//                                notInDesktopModuleList.add(moduleInfo);
//                                break;
//                            }
//                        }
//                    }
//                }

                //屏蔽没有URL的应用，屏蔽在Tab页下显示的应用
                for (int i = 0; i < notInDesktopModuleList.size(); i++) {
                    ModuleInfo notInDesktopModule = notInDesktopModuleList.get(i);
                    if (CommonUtils.isEmpty(notInDesktopModule.getUrl()) || notInDesktopModule.getModuleType().equals(2)) {
                        notInDesktopModuleList.remove(i--);
                    }
                }

                status = SUCCESS;
                content = notInDesktopModuleList;
            }
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("status", status);
            jsonMap.put("content", content);
            Gson gson = new Gson();
            out.print(gson.toJson(jsonMap));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //添加一个桌面页
    public String addDesktopPage() {
        PrintWriter out = null;
        String result = "";
        HashMap<String, Object> jsonMap = new HashMap<String, Object>();
        try {
            out = getResponse().getWriter();
            UserInfo userInfo = getLoginUser();
            DesktopPage desktopPage = new DesktopPage();
            desktopPage.setOrderNo(this.desktopService.countOfPage(userInfo.getUserId()));
            desktopPage.setIsDelete(CbbConst.NOT_DELETE);
            desktopPage.setUserId(userInfo.getUserId());
            desktopPage.setCompanyId(userInfo.getCompanyId());
            this.desktopService.savePage(desktopPage);
            jsonMap.put("status", "success");
            jsonMap.put("id", desktopPage.getId());
            jsonMap.put("orderNo", desktopPage.getOrderNo());
            Gson gson = new Gson();
            result = gson.toJson(jsonMap);
            out.print(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    //添加一个桌面页
    public String addDesktopModule() {
        PrintWriter out = null;
        String status = ERROR;
        Object content = "";
        try {
            out = getResponse().getWriter();
            if (this.moduleInfoId == null || this.desktopPageId == null) {
                status = ERROR;
                content = "addDesktopModule:moduleInfoId or desktopPageId is null";
            } else {
                UserInfo userInfo = getLoginUser();
                DesktopModule desktopModule = new DesktopModule();
                desktopModule.setIsDelete(CbbConst.NOT_DELETE);
                desktopModule.setCompanyId(userInfo.getCompanyId());
                desktopModule.setOrderNo(this.desktopService.countOfModuleByPageId(this.desktopPageId));
                desktopModule.setUserId(userInfo.getUserId());
                desktopModule.setDesktopPageId(this.desktopPageId);
                ModuleInfo moduleInfo = this.getModuleInfoById(this.moduleInfoId);
                desktopModule.setModuleInfo(moduleInfo);
                this.desktopService.saveModule(desktopModule);

                setPageMenuJson(desktopModule);

                HashMap<String, Object> desktopModuleMap = new HashMap<String, Object>();
                desktopModuleMap.put("orderNo", desktopModule.getOrderNo());
                desktopModuleMap.put("id", desktopModule.getId());
                desktopModuleMap.put("desktopPageId", desktopModule.getDesktopPageId());
                desktopModuleMap.put("moduleInfo.moduleId", moduleInfo.getModuleId());
                desktopModuleMap.put("moduleInfo.icon", moduleInfo.getIcon());
                desktopModuleMap.put("moduleInfo.url", moduleInfo.getUrl());
                desktopModuleMap.put("moduleInfo.moduleName", moduleInfo.getModuleName());
                desktopModuleMap.put("pageMenuJson",desktopModule.getPageMenuJson());
//                //任务情况
//                IDesktopTask desktopTaskImpl = getDesktopTaskImpl(moduleInfo.getModuleName());
//                desktopModuleMap.put("taskCount", desktopTaskImpl.countOfTask(userInfo.getUserId()));//任务个数
//                desktopModuleMap.put("taskUrl", desktopTaskImpl.getTaskUrl());
//                desktopModuleMap.put("moduleName", desktopTaskImpl.getModuleName());

                content = desktopModuleMap;
                status = SUCCESS;
            }
            HashMap<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("status", status);
            jsonMap.put("content", content);
            Gson gson = new Gson();
            out.print(gson.toJson(jsonMap));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return null;
    }


    //从Session缓存中获取moduleInfo
    private ModuleInfo getModuleInfoById(Integer moduleInfoId) {
        for (ModuleInfo moduleInfo : getAllModuleInfoList()) {
            if (moduleInfo.getModuleId().equals(moduleInfoId)) {
                return moduleInfo;
            }
        }
        return null;
    }

    /**
     * 获取菜单
     *
     * @return 菜单List
     */
    private List<ModuleInfo> getAllModuleInfoList() {
    	String loginSysName = (String)getSession().getAttribute("loginSysName");
		if(loginSysName!=null&&!"".equals(loginSysName)){
			if("OA".equals(loginSysName)&&getSession().getAttribute("moduleOAList")!=null){
				return (List<ModuleInfo>) getSession().getAttribute("moduleOAList");
			}else if("HR".equals(loginSysName)&&getSession().getAttribute("moduleHRList")!=null){
				return (List<ModuleInfo>) getSession().getAttribute("moduleHRList");
			}
		}
		if (getSession().getAttribute("moduleList") != null) {
			return  (List<ModuleInfo>) getSession().getAttribute("moduleList");
		}
        return null;
    }

    /**
     * 获得desktopTaskServiceList
     * @return
     */
    private ArrayList<IDesktopTask> getdesktopTaskServiceList(){
    	this.desktopTaskServiceList = new ArrayList<IDesktopTask>();
    	desktopTaskServiceList.add(DesktopTaskAffairsImpl);
    	
    	return desktopTaskServiceList;
    }
    
    public Integer getDesktopModuleId() {
        return desktopModuleId;
    }

    public void setDesktopModuleId(Integer desktopModuleId) {
        this.desktopModuleId = desktopModuleId;
    }

    public Integer getDesktopPageId() {
        return desktopPageId;
    }

    public void setDesktopPageId(Integer desktopPageId) {
        this.desktopPageId = desktopPageId;
    }

    public String getDesktopModuleIds() {
        return desktopModuleIds;
    }

    public void setDesktopModuleIds(String desktopModuleIds) {
        this.desktopModuleIds = desktopModuleIds;
    }

    public String getDesktopPageIds() {
        return desktopPageIds;
    }

    public void setDesktopPageIds(String desktopPageIds) {
        this.desktopPageIds = desktopPageIds;
    }

    public Integer getModuleInfoId() {
        return moduleInfoId;
    }

    public void setModuleInfoId(Integer moduleInfoId) {
        this.moduleInfoId = moduleInfoId;
    }


    public String getModuleNames() {
        return moduleNames;
    }

    public void setModuleNames(String moduleNames) {
        this.moduleNames = moduleNames;
    }

	public ArrayList<IDesktopTask> getDesktopTaskServiceList() {
		return desktopTaskServiceList;
	}

	public void setDesktopTaskServiceList(
			ArrayList<IDesktopTask> desktopTaskServiceList) {
		this.desktopTaskServiceList = desktopTaskServiceList;
	}

	/**
	 * @return the sysName
	 */
	public String getSysName() {
		return sysName;
	}

	/**
	 * @param sysName the sysName to set
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
    
}
