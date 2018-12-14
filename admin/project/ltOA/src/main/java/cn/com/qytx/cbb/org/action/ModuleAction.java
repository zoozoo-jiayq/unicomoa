package cn.com.qytx.cbb.org.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.org.domain.ModuleInfo;

import com.google.gson.Gson;

/**
 * User: 黄普友 Date: 13-3-20 Time: 上午10:54
 */
public class ModuleAction extends BaseActionSupport {

	
	private String sysName;
	
	/**
	 * 获取封装后的菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMenuList() {
		try {
			String moduleHtml="";
			moduleHtml = getModuleHtml();
			ajax(moduleHtml);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取封装后的菜单
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getMenuListLeft() {
		try {
			 String moduleHtml="";
			 moduleHtml = getModuleLeftHtml();
			 ajax(moduleHtml);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	
	
	/**
	 * 功能：获取默认菜单
	 * @return
	 */
   private String getModuleHtml(){
	   String path = this.getRequest().getContextPath();
		String basePath = this.getRequest().getScheme() + "://"
				+ this.getRequest().getServerName() + ":"
				+ this.getRequest().getServerPort() + path + "/";

		List<ModuleInfo> moduleList = new ArrayList<ModuleInfo>();
		boolean isUnitLogin = false;
		String loginSysName = (String)getSession().getAttribute("loginSysName");
		if(loginSysName!=null&&!"".equals(loginSysName)){
			if("OA".equals(loginSysName)&&getSession().getAttribute("moduleOAList")!=null){
				moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleOAList");
				isUnitLogin = true;
			}else if("HR".equals(loginSysName)&&getSession().getAttribute("moduleHRList")!=null){
				moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleHRList");
				isUnitLogin = true;
			}
		}
		if (!isUnitLogin&&getSession().getAttribute("moduleList") != null) {
			moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleList");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<em class=\"top_jt\"></em>");
		// 获取第一级菜单
		sb.append("<div class=\"m_left\" id=\"first_panel\">");
		sb.append("<div class=\"scroll-up\" ><a href=\"\"></a></div>");
		sb.append("<ul id=\"first_menu\"  style=\"overflow:hidden\" > ");
		int i =0;
		for (ModuleInfo module : moduleList) {
			if (module.getModuleLevel() == 1) {
//				sb.append("<li><a style='cursor: pointer;'><img src='"
//						+ basePath + (module.getIcon()==null?"":module.getIcon()) + "'/>"
//						+ module.getModuleName() + "</a></li>");
				String sysName = module.getModuleClass()==null?"":module.getModuleClass();
				String moduleName = module.getModuleName();
              if(i==0){
           	   sb.append(" <li class=\"current\">");
           	   sb.append(" <em class=\"").append(sysName).append("\"></em>").append(moduleName).append("</li>");
               }else{
               	 sb.append(" <li >");
             	     sb.append(" <em class=\"").append(sysName).append("\"></em>").append(moduleName).append("</li>");
               }
               i++;
			}
		}
		sb.append("</ul>");
		sb.append("<div class='scroll-down'><a href=''></a></div>");
		sb.append("</div>");
		boolean first = true;
		// 添加二级/三级菜单
		for (ModuleInfo module : moduleList) {

			if (module.getModuleLevel() == 1) {
				// 获取二级菜单
				List<ModuleInfo> secondLevelModuleList = getChildModuleList(
						moduleList, module.getModuleId());
				if (secondLevelModuleList != null) {
					if (first) {
						sb.append(" <div class='m_right s_menu' style='display:block;'>");
						first = false;
					} else {
						sb.append(" <div class='m_right s_menu' >");
					}
					sb.append("<ul>");

					for (ModuleInfo secondModule : secondLevelModuleList) {
						String moduleClass= secondModule.getModuleClass()!=null?secondModule.getModuleClass():"";
					  
						// 获取三级菜单
						List<ModuleInfo> thirdLevelModuleList = getChildModuleList(
								moduleList, secondModule.getModuleId());
						// 如果有三级菜单
						if (thirdLevelModuleList != null
								&& thirdLevelModuleList.size() > 0) {
							// 二级菜单添加url链接
							if (secondModule.getUrl() == null
									|| secondModule.getUrl().equals("")) {
								sb.append(" <li><a class='tile_a'  href='javascript:void(0);'>")
								.append("<em class=\""+moduleClass+"\" ></em>")
								.append( secondModule.getModuleName()).append("</a>");
							} else {
								List<ModuleInfo> methodList = getChildMethodList(
										moduleList,
										secondModule.getModuleId());
								Gson gson = new Gson();
								String json = "";
								if (methodList != null
										&& methodList.size() > 0) {
									json = gson.toJson(methodList);
								}
								json = StringEscapeUtils.escapeHtml4(json);
								sb.append(" <li><a   href='javascript:void(0)' onclick=\"gotoUrl("
										+ secondModule.getModuleId()
										+ ",'"
										+ secondModule.getModuleName()
										+ "','"
										+ basePath
										+ (secondModule.getUrl()==null?"":secondModule.getUrl())
										+ "','"
										+ json
										+ "');\"></a>");
							}
							sb.append("<dl>");

							for (ModuleInfo thirdModule : thirdLevelModuleList) {
								List<ModuleInfo> methodList = getChildMethodList(
										moduleList,
										thirdModule.getModuleId());
								Gson gson = new Gson();
								String json = "";
								if (methodList != null
										&& methodList.size() > 0) {

									json = gson.toJson(methodList);
								}
								json = StringEscapeUtils.escapeHtml4(json);
								sb.append("<dd> ");
								sb.append(" <a style='cursor: pointer;' href='javascript:void(0);'  onclick=\"gotoUrl("
										+ thirdModule.getModuleId()
										+ ",'"
										+ thirdModule.getModuleName()
										+ "','"
										+ basePath
										+ (thirdModule.getUrl()==null?"":thirdModule.getUrl())
										+ "','"
										+ json
										+ "');\">"
										+ thirdModule.getModuleName()
										+ "</a>");
								sb.append("</dd>");
							}
							sb.append("</dl>");
						} else {
							// 不存在三级菜单

							List<ModuleInfo> methodList = getChildMethodList(
									moduleList, secondModule.getModuleId());
							Gson gson = new Gson();
							String json = "";
							if (methodList != null && methodList.size() > 0) {
								json = gson.toJson(methodList);
								json = StringEscapeUtils.escapeHtml4(json);
								  moduleClass = secondModule.getModuleClass()!=null?secondModule.getModuleClass():"";
								sb.append("  <li><a class='tile_a'     href='javascript:void(0);' onclick=\"gotoUrl("
										+ secondModule.getModuleId()
										+ ",'"
										+ secondModule.getModuleName()
										+ "','"
										+ basePath
										+ (secondModule.getUrl()==null?"":secondModule.getUrl())
										+ "','"
										+ json
										+ "');\">"
										//+ "<img src='"
										//+ basePath
										//+ (secondModule.getIcon()==null?"":secondModule.getIcon())
										//+ "'/>"
										+  "<em class=\""+moduleClass+"\" ></em>"
										+ secondModule.getModuleName()
										+ "</a>");
							} else {
								
								// sb.append("  <li><a class='ss'  href='javascript:void(0);' >"+secondModule.getModuleName()+"</a>");
								sb.append("  <li><a  class='tile_a'    href='javascript:void(0);' onclick=\"gotoUrl("
										+ secondModule.getModuleId()
										+ ",'"
										+ secondModule.getModuleName()
										+ "','"
										+ basePath
										+ (secondModule.getUrl()==null?"":secondModule.getUrl())
										+ "','"
										+ ""
										+ "');\">"
										//+ "<img src='"
										//+ basePath
										//+ (secondModule.getIcon()==null?"":secondModule.getIcon())
									//	+ "'/>"
									    +  "<em class=\""+moduleClass+"\" ></em>"
										+ secondModule.getModuleName()
										+ "</a>");
							}

						}
						sb.append("</li>");
					}
					sb.append("</ul>");
					sb.append("</div>");
				}
			}
		}
		sb.append(" <iframe id='emptyIframe' style='position:absolute; visibility:inherit;display:none;top:15px; left:3px; width:380px;z-index:-100'"
				+ " filter='progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'; frameborder='0'>"
				+ " </iframe>");
		
		return sb.toString();
   }
	
	
	
   /**
	 * 功能：获取默认菜单 (左侧菜单)
	 * @return
	 */
  private String getModuleLeftHtml(){
	   String path = this.getRequest().getContextPath();
		String basePath = this.getRequest().getScheme() + "://"
				+ this.getRequest().getServerName() + ":"
				+ this.getRequest().getServerPort() + path + "/";

		List<ModuleInfo> moduleList = new ArrayList<ModuleInfo>();
		boolean isUnitLogin = false;
		String loginSysName = (String)getSession().getAttribute("loginSysName");
		if(loginSysName!=null&&!"".equals(loginSysName)){
			if("OA".equals(loginSysName)&&getSession().getAttribute("moduleOAList")!=null){
				moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleOAList");
				isUnitLogin = true;
			}else if("HR".equals(loginSysName)&&getSession().getAttribute("moduleHRList")!=null){
				moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleHRList");
				isUnitLogin = true;
			}
		}
		if (!isUnitLogin&&getSession().getAttribute("moduleList") != null) {
			moduleList = (List<ModuleInfo>) getSession().getAttribute("moduleList");
		}
		
	 
		StringBuffer sb = new StringBuffer();
		 
		// 获取第一级菜单
		sb.append("<div class=\"s_menu\">");
		sb.append("<ul class=\"l_f_menu\">");
	 
		int i =0;
		
		List<ModuleInfo> moduleListFirst = getModuleListByPId(0,moduleList);
		//获取一级菜单
		for (ModuleInfo module : moduleListFirst) {
			       String moduleName = module.getModuleName()!=null?module.getModuleName().toString():"";
			       String moduleClass = module.getModuleClass()!=null?module.getModuleClass().toString():"";
			        int moduleId = module.getModuleId();
			        List<ModuleInfo> moduleListSecond = getModuleListByPId(moduleId,moduleList);
			        if(moduleListSecond!=null&&moduleListSecond.size()>0){
			        	  sb.append("<li>");
						    sb.append("<p><em class=\""+moduleClass+"\"></em>"+moduleName+"</p>");
							for (ModuleInfo moduleSecond : moduleListSecond) {
								  int moduleIdSecond = moduleSecond.getModuleId();
								  String moduleNameSecond = moduleSecond.getModuleName()!=null?moduleSecond.getModuleName().toString():"";
								  List<ModuleInfo> moduleListThree = getModuleListByPId(moduleIdSecond,moduleList);
								 if(moduleListThree!=null&&moduleListThree.size()>0){
									   sb.append("<h3>"+moduleNameSecond+"</h3>");
								       sb.append("<dl>");
								       
								       for (ModuleInfo moduleThree : moduleListThree) {
								    	   String moduleNameThree = moduleThree.getModuleName()!=null?moduleThree.getModuleName().toString():"";
								    	   sb.append("<dt>");
								    	   sb.append("<a href=\"javascript:void(0)\"  href='javascript:void(0);' onclick=\"gotoUrl("
												+ moduleThree.getModuleId()
												+ ",'"
												+ moduleThree.getModuleName()
												+ "','"
												+ basePath
												+ (moduleThree.getUrl()==null?"":moduleThree.getUrl())
												+ "','"
												+ ""
												+ "');\"  >   "+moduleNameThree+"</a>");
								    	   
								    	   sb.append("</dt>");
								       }
		                               sb.append("</dl>");
								 }else{
									 sb.append("<h3><a href=\"javascript:void(0)\"  href='javascript:void(0);' onclick=\"gotoUrl("
												+ moduleSecond.getModuleId()
												+ ",'"
												+ moduleSecond.getModuleName()
												+ "','"
												+ basePath
												+ (moduleSecond.getUrl()==null?"":moduleSecond.getUrl())
												+ "','"
												+ ""
												+ "');\"  >    "+moduleSecond.getModuleName()+"</a></h3>");
									 
								 }
								
							}
						    sb.append("</li>");
			        }
				  
		   
		}
		
		
		sb.append("</ul>");
		sb.append("</div>");
		
		
		
	 return sb.toString();
  }
   
   
   
   
	
	/**
	 * 功能： 根据父ID获取子的菜单项
	 * @param i
	 * @param moduleList
	 * @return
	 */
	private List<ModuleInfo> getModuleListByPId(int pid, List<ModuleInfo> moduleList) {
	     List<ModuleInfo> resList = new ArrayList<ModuleInfo>();
	     if(moduleList!=null){
	    	 for(ModuleInfo m:moduleList){
	    		 int pidm = m.getParentId()!=null?m.getParentId().intValue():-1;
	    		 if(m.getIsDelete()!=null&&m.getIsDelete().intValue()==0){
	    			 if(pidm == pid){
		    			 resList.add(m);
		    		 } 
	    		 }
	    	 }
	    	 
	     }
	     return resList;
    }


	/**
	 * 获取子菜单列表
	 * 
	 * @param moduleList
	 * @param parentId
	 * @return
	 */
	private List<ModuleInfo> getChildModuleList(
			final List<ModuleInfo> moduleList, int parentId) {
		List<ModuleInfo> list = new ArrayList<ModuleInfo>();
		for (ModuleInfo module : moduleList) {
			if (module.getParentId() == parentId && module.getModuleType() == 1) {
				list.add(module);
			}
		}
		return list;
	}

	/**
	 * 获取四级子功能列表
	 * 
	 * @param moduleList
	 * @param parentId
	 * @return
	 */
	private List<ModuleInfo> getChildMethodList(
			final List<ModuleInfo> moduleList, int parentId) {
		List<ModuleInfo> list = new ArrayList<ModuleInfo>();
		for (ModuleInfo module : moduleList) {
			if (module.getParentId() == parentId && module.getModuleType() == 2) {
				list.add(module);
			}
		}
		return list;
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
