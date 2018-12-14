package cn.com.qytx.cbb.customForm.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;

import cn.com.qytx.cbb.customForm.domain.FormAttribute;
import cn.com.qytx.cbb.customForm.domain.FormCategory;
import cn.com.qytx.cbb.customForm.service.IFormAttribute;
import cn.com.qytx.cbb.customForm.service.IFormCategory;
import cn.com.qytx.cbb.customJpdl.domain.NodeFormAttribute;
import cn.com.qytx.cbb.customJpdl.service.NodeFormAttributeService;
import cn.com.qytx.cbb.customJpdl.service.ProcessAttributeService;
import cn.com.qytx.cbb.publicDom.service.IDocTemplateService;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.SimpleTreeNode;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

/**
 * 功能: 分类设置ACTION
 * 版本: 1.0
 * 开发人员: 吴洲
 * 创建日期: 2013-3-25
 * 修改日期: 2013-3-25
 * 修改列表:
 */
public class CategoryAction extends BaseActionSupport {
	
	/**接口类*/
	// 分类设置服务类
	@Resource(name = "formCategoryService")
	IFormCategory formCategoryService;
	// 表单属性服务类
	@Resource(name = "formAttributeService")
	IFormAttribute formAttributeService;
	//流程服务类
	@Resource(name = "processAttributeService")
	ProcessAttributeService processAttributeService;
	//节点服务类
	@Resource(name = "nodeFormAttributeService")
	NodeFormAttributeService nodeFormAttributeService;
	
	/**用户信息*/
    @Resource(name = "userService")
    IUser userService;
    /**用户信息*/
    @Resource(name = "groupService")
    IGroup groupService;
    /**
     * 部门/群组管理接口
     */
    @Resource(name = "groupUserService")
    IGroupUser groupUserService;
    /**
     * 角色管理接口
     */
    @Resource(name = "roleService")
    IRole roleService;
	/** 正文模板服务类   */
	@Resource(name="docTemplateService")
	private IDocTemplateService docTemplateService;
	
	
	private Integer type = 0;//分类类别  1，表单分类     2.流程分类
	private FormCategory fc = new FormCategory();
	private List<FormCategory> fcateList = new ArrayList<FormCategory>();
	private List<FormAttribute> fattList =new ArrayList<FormAttribute>();
	private List<UserInfo> uiList = new ArrayList<UserInfo>();
	private Integer categoryId = 0;//分类id
	private String oldCategoryName="";//分类原始名称
	private Integer nodeId=0;//节点id
	private String userNameSearch = "";
	
	
	/**
     * 获取类别列表
     * @return 类别信息集合json
     */
	@SuppressWarnings("unchecked")
	public String findCategoryList(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到登录用户
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
            //分页信息
            Page<FormCategory> pageInfo = formCategoryService.findByPage(getPageable(),userInfo.getCompanyId(), type);
            List<FormCategory> fcList = pageInfo.getContent(); 
            //把类别id和该类别下的表单总数放到map中
            Map<Object, Object> cateMap = new HashMap<Object, Object>();
            if(type==FormCategory.CATEGORY_TYPE_FORM){
	            List<Object> list = formAttributeService.findGroupByCategory();
	            Object[] o = null;
	            for(int i=0;i<list.size();i++){
	            	o=(Object[]) list.get(i);
	            	cateMap.put(o[0], o[1]);
	            }
            }
          //把类别id和该类别下的流程总数放到map中
            Map<Object, Object> catePMap = new HashMap<Object, Object>();
            if(FormCategory.CATEGORY_TYPE_PROCESS==type){
	            List<Object> processlist = processAttributeService.getCountByCategory();
	            Object[] op = null;
	            for(int i=0;i<processlist.size();i++){
	            	op=(Object[]) processlist.get(i);
	            	catePMap.put(op[0], op[1]);
	            }
            }
            List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
            if (fcList != null) {
            	int i = getPageable().getPageNumber() * this.getIDisplayLength() + 1;
                for(FormCategory fcInfo : fcList) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", fcInfo.getCategoryId());//id
                    if (type==FormCategory.CATEGORY_TYPE_FORM) {
                    	if (cateMap.get(fcInfo.getCategoryId())!=null) {
                        	map.put("count", cateMap.get(fcInfo.getCategoryId()));
    					}else {
    						map.put("count", 0);
    					}
					}else if (type==FormCategory.CATEGORY_TYPE_PROCESS) {
						if (catePMap.get(fcInfo.getCategoryId())!=null) {
							map.put("count", catePMap.get(fcInfo.getCategoryId()));
						}else {
							map.put("count", 0);
						}
					} else if (type==FormCategory.DOC_RED) {
						    int count = docTemplateService.getCount(fcInfo.getCategoryId());
							map.put("count", count);
						
					}else{
					    map.put("count", "&nbsp");
					}
                    String createTime="&nbsp;";
                    if(fcInfo.getCreateTime()!=null){
                    	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    	createTime = sim.format(fcInfo.getCreateTime());
                    }
                    map.put("createTime",createTime);
                    map.put("no", i);
                    map.put("name", fcInfo.getCategoryName());//名字
                    //add by jiayq,
                    if(fcInfo.getDomType()!=null){
                    	map.put("domType", FormCategory.DOM_TYPE_MAP.get(fcInfo.getDomType()));
                    }else{
                    	map.put("domType", "");
                    }
                    mapList.add(map);
                    i++;
                }
            }
            ajaxPage(pageInfo,mapList);
		} catch (Exception e) {  
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
			return null;
		}
		return null;
	}
	/**
	 * 添加表单
	 */
	public String addCategory(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			List<FormCategory> falist = new ArrayList<FormCategory>();
			if(userInfo!=null){
			falist = formCategoryService.findCategoryByCategoryName(userInfo.getCompanyId(), fc.getType(), fc.getCategoryName());
			}
			Map<String, Integer> map = new HashedMap();
			if (falist != null && falist.size()>0) {
				map.put("marked", 0);//类别名称已经存在，不能重复添加
			}else {
				if (userInfo != null && userInfo.getCompanyId() != null) {
					fc.setCompanyId(userInfo.getCompanyId());
				}
				fc.setCreateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				fc.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				fc.setCompanyId(userInfo.getCompanyId());
				formCategoryService.saveOrUpdate(fc);
				map.put("marked", 1);//1、保存成功   0、表单名称已经存在
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
	 * 功能：load分类详细
	 * @return
	 */
	public String loadCategory(){
		fc = formCategoryService.findById(categoryId);
		if (fc.getType()==FormCategory.CATEGORY_TYPE_FORM || fc.getType() == FormCategory.DOC_FORM) {
			return "form";
		}else if (fc.getType()==FormCategory.CATEGORY_TYPE_PROCESS || fc.getType() == FormCategory.DOC_FLOW) {
			return "process";
		} else {
			return "doc";
		} 
	}
	/**
	 * 功能：修改类别
	 * @return
	 */
	public String editCategory(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			Map<String, Integer> map = new HashedMap();
			if (fc.getCategoryName().equals(oldCategoryName)) {
				//分类名称没有修改
				FormCategory oldForm = new FormCategory();
				oldForm = formCategoryService.findById(fc.getCategoryId());
				oldForm.setOrderIndex(fc.getOrderIndex());
				oldForm.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				oldForm.setDomType(fc.getDomType());
				formCategoryService.saveOrUpdate(oldForm);
				map.put("marked", 1);//1、保存成功   0、表单名称已经存在
			}else {
				//分类名称修改
				List<FormCategory> falist = new ArrayList<FormCategory>();
				falist = formCategoryService.findCategoryByCategoryName(userInfo.getCompanyId(), fc.getType(), fc.getCategoryName());
				if (falist.size()>0) {
					map.put("marked", 0);
				}else {
					FormCategory oldForm;
					oldForm = formCategoryService.findById(fc.getCategoryId());
					oldForm.setCategoryName(fc.getCategoryName());
					oldForm.setOrderIndex(fc.getOrderIndex());
					oldForm.setLastUpdateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					oldForm.setDomType(fc.getDomType());
					formCategoryService.saveOrUpdate(oldForm);
					map.put("marked", 1);//1、保存成功   0、表单名称已经存在
				}
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	
	/**
	 * 功能：删除表单
	 * @return 0表单已经被使用不能删除 1删除成功
	 */
	public String deleteCategory(){
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			int isDelete = 0;
			isDelete = formCategoryService.delete(fc.getCategoryId(), fc.getType());
			Map<String, Integer> map = new HashedMap();
			if (isDelete==1) {
				map.put("marked", 1);//1、删除成功   0、表单已经被使用不能删除
			}else {
				map.put("marked", 0);//1、删除成功   0、表单已经被使用不能删除
			}
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
	 * 查找所有类别
	 * @return
	 */
	public String findAllCategory(){
		PrintWriter out = null;
		try {	
			out = this.getResponse().getWriter();
			//得到公司ID
			UserInfo userInfo = (UserInfo)this.getSession().getAttribute("adminUser");
			List<FormCategory> fclist ;
			fclist = formCategoryService.findByTypeCompanyId(userInfo.getCompanyId(), type);
			Map<String, List<FormCategory>> map = new HashedMap();
			map.put("fcList", fclist);
			Gson json = new Gson();
			String jsons = json.toJson(map);
			out.print(jsons);
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}finally{
			if(out!=null){
			    out.close();
			}
		}
		return null;
	}
	/**
	 * 功能：获取所有流程分类，及其流程定义
	 * @param
	 * @return
	 * @throws   
	 */
	public String getFormCategorys(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		//获取所有分类,
		//update by jiayq,默认查询工作流表单分类
		if(this.type==null || this.type==0){
			this.type = 1;
		}
		fcateList = getCategories();
		//获取所有表单
		//fattList = formAttributeService.findAll(user.getCompanyId());
		return "index";
	}
	/**
	 * 功能：获取流程分类
	 * @param
	 * @return
	 * @throws   
	 */
	public List<FormCategory> getCategories(){
		UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");
		return formCategoryService.findByTypeCompanyId(user.getCompanyId(), type);
	}
	/**
	 * 通过节点id得到该节点上的所有候选人员
	 */
	public String getUser(){
		try {
			UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");//得到登陆用户信息
			List<UserInfo> userAlllist = new ArrayList<UserInfo>();//所有人员
			NodeFormAttribute nodeFormAtt =nodeFormAttributeService.findById(nodeId);
			if (nodeFormAtt!=null) {
				String candidateStr = nodeFormAtt.getCandidate();
				String deptsStr = nodeFormAtt.getDepts();
				String rolesStr = nodeFormAtt.getRoles();
				//把候选人插入到userAlllist中
				if (candidateStr != null && !candidateStr.equals("")) {
					String[] useridArr = candidateStr.split(",");
					if (useridArr != null && useridArr.length > 0) {
						for (int i = 0; i < useridArr.length; i++) {
							int userid = Integer.parseInt(useridArr[i]);
							List<UserInfo> uList = userService.findUsersByIds(String.valueOf(userid));
							 if(uList!=null&&uList.size()>0){
								 userAlllist.add(uList.get(0));
								}
						}
					}
				}
				//把候选部门中的人插入到userAlllist中
				if (deptsStr != null && !deptsStr.equals("")) {
					userAlllist.addAll(userService.findUsersByGroupId(deptsStr));
				}
				//把所有角色中的人员插入到userAlllist中
				if (rolesStr != null && !rolesStr.equals("")) {
					userAlllist.addAll(userService.findUsersByRoleId(rolesStr));
				}
				//过滤重复的人员
				Map<Integer, UserInfo> map = new HashMap<Integer, UserInfo>();
				if (userAlllist != null && userAlllist.size() > 0) {
					for (int i = 0; i < userAlllist.size(); i++) {
						if (map.get(userAlllist.get(i).getUserId()) == null) {
							//过滤搜索
							if (userNameSearch.equals("") || userAlllist.get(i).getUserName().contains(userNameSearch)) {
								map.put(userAlllist.get(i).getUserId(), userAlllist.get(i));
								UserInfo userinfo = new UserInfo();
								userinfo = userAlllist.get(i);
								uiList.add(userinfo);
							}
						}
					}
					
				}
				//放到树中
				Collection<SimpleTreeNode> treeNodes = new ArrayList<SimpleTreeNode>();
				if(uiList!=null)
	            {
	                for (UserInfo userinfo : uiList)
	                {

	                    SimpleTreeNode treeNode = new SimpleTreeNode();
	                    treeNode.setId(""+userinfo.getUserId());
	                    treeNode.setName(userinfo.getUserName());
						GroupInfo ginfo = groupService.findOne(userinfo.getGroupId());
//						}
						String treeGroupName="无";
						if (ginfo != null && ginfo.getGroupName() != null) {
							treeGroupName=ginfo.getGroupName();
						}
	                    treeNode.setObj(treeGroupName+"_"+userinfo.getLoginName()); 
	                    treeNode.setPId("0");
	                    treeNodes.add(treeNode);
	                }
	            }
				 Gson json = new Gson();
		            String jsons = json.toJson(treeNodes);

		            ajax(jsons);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}
		
		return null;
	}
	
	/**
	 * 通过节点id得到该节点上的所有候选人员
	 */
	public String getUserWap(){
//		PrintWriter out = null;
		try {
//			out = this.getResponse().getWriter();
			UserInfo user = (UserInfo) ActionContext.getContext().getSession().get("adminUser");//得到登陆用户信息
			
			List<UserInfo> userAlllist = new ArrayList<UserInfo>();//所有人员
			NodeFormAttribute nodeFormAtt =nodeFormAttributeService.findById(nodeId);
			if (nodeFormAtt!=null) {
				String candidateStr = nodeFormAtt.getCandidate();
				String deptsStr = nodeFormAtt.getDepts();
				String rolesStr = nodeFormAtt.getRoles();
				//判断是否只读取本部门人员
				int isMyDep = 0;
				if (nodeFormAtt.getIsMydepCanAccept()!=null) {
					isMyDep = nodeFormAtt.getIsMydepCanAccept();
				}
				//把候选人插入到userAlllist中
				if (candidateStr != null && !candidateStr.equals("")) {
					String[] useridArr = candidateStr.split(",");
					if (useridArr != null && useridArr.length > 0) {
						for (int i = 0; i < useridArr.length; i++) {
							int userid = Integer.parseInt(useridArr[i]);
							List<UserInfo> uList = userService.findUsersByIds(String.valueOf(userid));
							 if(uList!=null&&uList.size()>0){
								 userAlllist.add(uList.get(0));
								}
						}
					}
				}
				//把候选部门中的人插入到userAlllist中
				if (deptsStr != null && !deptsStr.equals("")) {
					List<UserInfo> candiList = userService.findUsersByGroupId(deptsStr);
					if (candiList != null && candiList.size() > 0) {
						userAlllist.addAll(candiList);
					}
				}
				//把所有角色中的人员插入到userAlllist中
				if (rolesStr != null && !rolesStr.equals("")) {
					List<UserInfo> roleCanList = userService.findUsersByRoleId(rolesStr);
					if (roleCanList != null && roleCanList.size() > 0) {
						userAlllist.addAll(roleCanList);
					}
				}
				//过滤重复的人员      
				Map<Integer, UserInfo> map = new HashMap<Integer, UserInfo>();
				if (isMyDep==1) {//只取本部门的人员
					if (userAlllist != null && userAlllist.size() > 0) {
						for (int i = 0; i < userAlllist.size(); i++) {
							if (map.get(userAlllist.get(i).getUserId()) == null) {
								//过滤搜索
								if (userNameSearch.equals("") || userAlllist.get(i).getUserName().contains(userNameSearch)) {
									map.put(userAlllist.get(i).getUserId(), userAlllist.get(i));
									UserInfo userinfo = new UserInfo();
									userinfo = userAlllist.get(i);
									uiList.add(userinfo);
								}
							}
						}
					}
				}else {
					if (userAlllist != null && userAlllist.size() > 0) {
						for (int i = 0; i < userAlllist.size(); i++) {
							if (map.get(userAlllist.get(i).getUserId()) == null) {
								//过滤搜索
								if (userNameSearch.equals("") || userAlllist.get(i).getUserName().contains(userNameSearch)) {
									map.put(userAlllist.get(i).getUserId(), userAlllist.get(i));
									UserInfo userinfo = new UserInfo();
									userinfo = userAlllist.get(i);
									uiList.add(userinfo);
								}
							}
						}
					}
				}
				
				 HttpServletRequest request = ServletActionContext.getRequest();
			        String path = request.getContextPath();
			        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			                + request.getServerPort() + path + "/";
				//放到树中
				Collection<TreeNode> treeNodes = new ArrayList<TreeNode>();
				if(uiList!=null&&uiList.size()>0)
	            {
	                for (UserInfo userinfo : uiList)
	                {
	                    TreeNode treeNode = new TreeNode();
                        treeNode.setId("uid_" + userinfo.getLoginName());// 部门ID前加gid表示类型为部门
                        treeNode.setName(userinfo.getUserName());
                        treeNode.setObj(userinfo.getPhone()); // 把号码存放到node里面，js里面调用
                        // 职务
                        treeNode.setTarget(userinfo.getJob());
                        if (null != user.getSex() && 0 == userinfo.getSex())
                        {
                            treeNode.setIcon(basePath + "images/woman.png");
                        }
                        else
                        {
                            treeNode.setIcon(basePath + "images/man.png");
                        }
                        treeNodes.add(treeNode);
	                    
	                }
	            }
				 Gson json = new Gson();
		            String jsons = json.toJson(treeNodes);
		            ajax(jsons);
			}
	 
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage(),ex);
		}
		
		return null;
	}
	/**
	 * 得到类型
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置类型
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the fc
	 */
	public FormCategory getFc() {
		return fc;
	}

	/**
	 * @param fc the fc to set
	 */
	public void setFc(FormCategory fc) {
		this.fc = fc;
	}
	/**
	 * 得到分类id
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置分类id
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 得到原始分类名称
	 * @return the oldCategoryName
	 */
	public String getOldCategoryName() {
		return oldCategoryName;
	}
	/**
	 * 设置原始分类名称
	 * @param oldCategoryName the oldCategoryName to set
	 */
	public void setOldCategoryName(String oldCategoryName) {
		this.oldCategoryName = oldCategoryName;
	}
	/**
	 * @return the fcateList
	 */
	public List<FormCategory> getFcateList() {
		return fcateList;
	}
	/**
	 * @param fcateList the fcateList to set
	 */
	public void setFcateList(List<FormCategory> fcateList) {
		this.fcateList = fcateList;
	}
	/**
	 * @return the fattList
	 */
	public List<FormAttribute> getFattList() {
		return fattList;
	}
	/**
	 * @param fattList the fattList to set
	 */
	public void setFattList(List<FormAttribute> fattList) {
		this.fattList = fattList;
	}
	/**
	 * @return the nodeId
	 */
	public Integer getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the uiList
	 */
	public List<UserInfo> getUiList() {
		return uiList;
	}
	/**
	 * @param uiList the uiList to set
	 */
	public void setUiList(List<UserInfo> uiList) {
		this.uiList = uiList;
	}
	/**
	 * @return the userNameSearch
	 */
	public String getUserNameSearch() {
		return userNameSearch;
	}
	/**
	 * @param userNameSearch the userNameSearch to set
	 */
	public void setUserNameSearch(String userNameSearch) {
		this.userNameSearch = userNameSearch;
	}
	
}
