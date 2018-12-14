package cn.com.qytx.oa.file.action;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.domain.TreeNodes;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.oa.modulePriv.domain.ModulePrivVo;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.log.domain.Log;
import cn.com.qytx.platform.log.service.ILog;
import cn.com.qytx.platform.log.service.LogType;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.tree.TreeNode;

import com.google.gson.Gson;

/**
 * 功能:文件夹action 版本: 1.0 开发人员: 徐长江 创建日期: 2013-3-21 修改日期: 2013-3-21 修改列表:
 */

public class FileSortAction extends BaseActionSupport {
	@Autowired
	ILog logImpl;
	/***
	 * 模块判断
	 */
	private String module;
	/***
	 * 访问权限
	 */
	private String visitPriv;
	/***
	 * 新建权限
	 */
	private String newPriv;
	/***
	 * 编辑权限
	 */
	private String editPriv;
	/***
	 * 删除权限
	 */
	private String delPriv;
	/***
	 * 下载权限
	 */
	private String downPriv;
	/***
	 * 签阅权限
	 */
	private String checkPriv;

	/***
	 * 所有者权限
	 */
	private String ownPriv;
	/***
	 * 批量设置权限
	 */
	private String addP;
	/***
	 * 文件类型实体
	 */
	private FileSort fileSort;
	/***
	 * 文件类型list
	 */
	private List<FileSort> repList;
	/**
	 * 文件夹的id
	 */
	private String fileSortId;
	/**
	 * 用户id
	 */
	private int userId;
	
	/**
	 * 文件柜类型 1公共文件 2共享文件
	 */
	private Integer type=1;

	/** 权限表里面的标识符 */
	private int vid;
	//fileSortType:0知识库管理；1空中课堂
	private int fileSortType;

	public int getFileSortType() {
		return fileSortType;
	}

	public void setFileSortType(int fileSortType) {
		this.fileSortType = fileSortType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/***
	 * 树形节点list
	 */
	private Collection<TreeNodes> treeNodes;
	/** 文件类别impl */
	@Autowired
	IFileSort fileSortImpl;
	/** 权限设置impl */
	@Autowired
	IDataPriv dataPrivImpl;
	/** 权限 */
	DataPriv dataPriv;

	/** 用户信息 */
	@Autowired
	IUser userService;
	/** 群组人员 **/
	@Autowired
	IGroupUser groupUserService;
	/**
	 * 角色
	 */
	@Autowired
	IRole roleService;
	/**
	 * 角色人员
	 */
	@Resource(name="roleUserService")
	IRoleUser roleUserService;
	/**
	 * 文件
	 */
	@Autowired
	IFileContent fileContentImpl;
	@Autowired
	ICompany companyService;
	@Autowired
	IGroup groupService;
	

	/**
	 * 功能：得到文件夹列表
	 * 
	 * @return
	 */
	public String findFileSortList() {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("ContentType", "text/json");
		response.setCharacterEncoding("utf-8");
		try {
			String isDelete = "0";
			repList = fileSortImpl.findAllFileSort(isDelete);
			treeNodes = new ArrayList<TreeNodes>();
			for (int i = 0; i < repList.size(); i++) {
				FileSort rep = (FileSort) repList.get(i);
				// if(!"0".equals(String.valueOf((rep.getParentId())))){
				String thisid = String.valueOf(rep.getSortId());
				TreeNodes treeNode = new TreeNodes();
				treeNode.setId(thisid);
				treeNode.setName(rep.getSortName());
				treeNode.setPId(String.valueOf(rep.getParentId()));
				Object[] arr = new Object[2];
				arr[0] = rep.getParentId();
				arr[1] = rep.getPath();
				treeNode.setArr(arr);
				if (fileSort != null) {
					treeNodes.add(treeNode);
					if (String.valueOf(fileSort.getSortId()) != null&& !String.valueOf(fileSort.getSortId()).equals("0")) {
						if (String.valueOf(fileSort.getSortId()).equals(thisid)) {
							setTreeNodesByRep(thisid, treeNode);
							treeNodes.add(treeNode);
						}
					} else {
						treeNodes.add(treeNode);
					}
					if (String.valueOf(fileSort.getSortId()).equals("0")) {
						treeNodes.add(treeNode);
					}
				}

				// }
			}
			Gson json = new Gson();
			String treeJsons = json.toJson(treeNodes);
			PrintWriter writer = new PrintWriter(response.getWriter());
			writer.print(treeJsons);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	/**
	 * 功能：设置文件夹数据
	 * 
	 * @param pId
	 * @param ptreeNode
	 */
	private void setTreeNodesByRep(String pId, TreeNode ptreeNode) {
		Collection<TreeNode> cl = new ArrayList<TreeNode>();
		for (int i = 0; i < repList.size(); i++) {
			FileSort rep = (FileSort) repList.get(i);
			int thisid = rep.getSortId();
			TreeNodes treeNode = new TreeNodes();
			treeNode.setId("" + thisid);
			treeNode.setName(rep.getSortName());
			treeNode.setPId(pId);
			Object[] arr = new Object[2];
			arr[0] = rep.getParentId();
			arr[1] = rep.getPath();
			treeNode.setArr(arr);
			if (String.valueOf(rep.getParentId()).equals(pId)) {// 如果编码不标准
				cl.add(treeNode);
				setTreeNodesByRep(String.valueOf(thisid), treeNode);
			}
		}
		ptreeNode.setChildren(cl);
	}

	public String getFileSortId() {
		return fileSortId;
	}

	public void setFileSortId(String fileSortId) {
		this.fileSortId = fileSortId;
	}

	/*    *//**
	 * 功能：获取文件夹内容列表【文件管理】
	 * 
	 * @return
	 */
	/*
	 * public String findFileContentList() { HttpServletRequest request =
	 * ServletActionContext.getRequest(); String path =
	 * request.getContextPath(); String basePath = request.getScheme() + "://" +
	 * request.getServerName() + ":" + request.getServerPort() + path + "/";
	 * 
	 * HttpServletResponse response = ServletActionContext.getResponse();
	 * response.setHeader("ContentType", "text/json");
	 * response.setCharacterEncoding("utf-8"); try { Object userObject =
	 * this.getSession().getAttribute("adminUser"); UserInfo userInfo =
	 * (UserInfo) userObject;
	 *//** 用户的标识符 */
	/*
	 * int uId = userInfo.getUserId();
	 *//** 组的标识符 */
	/*
	 * GroupInfo groupInfoBean = groupUserService.getGroupInfoByUserId(uId);
	 * 
	 * int groupId=0; if(groupInfoBean!=null){ groupId=
	 * groupInfoBean.getGroupId();}
	 *//** 根据用户查询角色列表 */
	/*
	 * List<Integer> listRole = new ArrayList<Integer>(); List<RoleInfo> roleId
	 * = roleService.getRoleByUser(uId);
	 * 
	 * if (roleId != null) { for (int i = 0; i < roleId.size(); i++) { RoleInfo
	 * roleInfo = roleId.get(i);
	 * 
	 * listRole.add(roleInfo.getRoleId()); } } String dataPrivHql =
	 * dataPrivImpl.getDataPrivHql("fileSort.sortId", "访问权限", "", uId,
	 * groupInfoBean == null ? 0 : groupInfoBean.getGroupId(), listRole);
	 * 
	 * repList = fileSortImpl.findAllFileContent(dataPrivHql); treeNodes = new
	 * ArrayList<TreeNodes>(); for (int i = 0; i < repList.size(); i++) {
	 * FileSort rep = (FileSort) repList.get(i); //
	 * if(!"0".equals(String.valueOf((rep.getParentId())))){ String thisid =
	 * String.valueOf(rep.getSortId()); TreeNodes treeNode = new TreeNodes();
	 * treeNode.setId(thisid); treeNode.setName(rep.getSortName());
	 * treeNode.setPId(String.valueOf(rep.getParentId()));
	 * treeNode.setIcon(basePath + "images/fileclose.png");
	 * treeNode.setIconOpen(basePath + "images/fileopen.png");
	 * treeNode.setIconClose(basePath + "images/fileclose.png"); Object[] arr =
	 * new Object[2]; arr[0] = rep.getParentId(); arr[1] = rep.getPath();
	 * treeNode.setArr(arr); treeNodes.add(treeNode); } Gson json = new Gson();
	 * String treeJsons = json.toJson(treeNodes); PrintWriter writer = new
	 * PrintWriter(response.getWriter()); writer.print(treeJsons);
	 * writer.flush(); writer.close(); } catch (Exception ex) {
	 * ex.printStackTrace(); } return null;
	 * 
	 * }
	 */
	/**
	 * 功能：获取文件夹内容列表【文件管理】
	 * 
	 * @return
	 */
	public String findFileContentList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("ContentType", "text/json");
		response.setCharacterEncoding("utf-8");
		try {
			Object userObject = this.getSession().getAttribute("adminUser");
			UserInfo userInfo = (UserInfo) userObject;
			/** 用户的标识符 */
			int uId = userInfo.getUserId();
			/*  *//** 组的标识符 */
			/*
			 * GroupInfo groupInfoBean =
			 * groupUserService.getGroupInfoByUserId(uId);
			 * 
			 * int groupId=0; if(groupInfoBean!=null){ groupId=
			 * groupInfoBean.getGroupId();}
			 *//** 根据用户查询角色列表 */
			/*
			 * List<Integer> listRole = new ArrayList<Integer>(); List<RoleInfo>
			 * roleId = roleService.getRoleByUser(uId);
			 * 
			 * if (roleId != null) { for (int i = 0; i < roleId.size(); i++) {
			 * RoleInfo roleInfo = roleId.get(i);
			 * 
			 * listRole.add(roleInfo.getRoleId()); } } String dataPrivHql =
			 * dataPrivImpl.getDataPrivHql("fileSort.sortId", "访问权限", "", uId,
			 * groupInfoBean == null ? 0 : groupInfoBean.getGroupId(),
			 * listRole);
			 */

			repList = fileSortImpl.findAllFileContentNotDataPriv(uId,fileSortType,type);
			CompanyInfo companyInfo = companyService.findOne(userInfo.getCompanyId());
			treeNodes = new ArrayList<TreeNodes>();
			TreeNodes treeNodeHead = new TreeNodes();
			treeNodeHead.setId("0");
			if(companyInfo != null){
				treeNodeHead.setName(companyInfo.getShortName());
				treeNodeHead.setTitle(companyInfo.getShortName());
			}else{
				treeNodeHead.setName("");
				treeNodeHead.setTitle("");
			}
			treeNodeHead.setPId("-1");
//			treeNodeHead.setIcon(basePath + "images/fileclose.png");
//			treeNodeHead.setIconOpen(basePath + "images/fileopen.png");
//			treeNodeHead.setIconClose(basePath + "images/fileclose.png");
			treeNodeHead.setOpen(true);
			treeNodeHead.isChecked();
			Object[] a = new Object[2];
			a[0] = -1;
			a[1] = "";
			treeNodeHead.setArr(a);
			treeNodes.add(treeNodeHead);

			for (int i = 0; i < repList.size(); i++) {
				FileSort rep = (FileSort) repList.get(i);
				// if(!"0".equals(String.valueOf((rep.getParentId())))){
				String thisid = String.valueOf(rep.getSortId());
				TreeNodes treeNode = new TreeNodes();
				treeNode.setId(thisid);
				treeNode.setName(rep.getSortName());
				treeNode.setPId(String.valueOf(rep.getParentId()));
				treeNode.setTitle(rep.getSortName());
//				treeNode.setIcon(basePath + "images/fileclose.png");
//				treeNode.setIconOpen(basePath + "images/fileopen.png");
//				treeNode.setIconClose(basePath + "images/fileclose.png");
				Object[] arr = new Object[2];
				arr[0] = rep.getParentId();
				arr[1] = rep.getPath();
				treeNode.setArr(arr);
				treeNodes.add(treeNode);
			}
			Gson json = new Gson();
			String treeJsons = json.toJson(treeNodes);
			PrintWriter writer = new PrintWriter(response.getWriter());
			writer.print(treeJsons);
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	/**
	 * 功能：获取文件夹的权限
	 * 
	 * @return
	 */
	public String getFileSortNdePriv() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();

			Object userObject = this.getSession().getAttribute("adminUser");
			UserInfo userInfo = (UserInfo) userObject;
			/** 用户的标识符 */
			int uId = userInfo.getUserId();
			/** 组的标识符 */
			//BaseGroupUser groupInfoBean = groupUserService.getGroupUserByUserId(uId);
			int groupId = userInfo.getGroupId();
			/** 根据用户查询角色列表 */
			List<Integer> listRole = new ArrayList<Integer>();
			List<RoleInfo> roleId = roleService.getRoleByUser(uId);

			if (roleId != null) {
				for (int i = 0; i < roleId.size(); i++) {
					RoleInfo roleInfo = roleId.get(i);

					listRole.add(roleInfo.getRoleId());
				}
			}

			String equaluser = "";
			FileSort delCatorlog = fileSortImpl.findByFileSortId(fileSort
					.getSortId());
			if (delCatorlog != null) {
				if (delCatorlog.getCreateUser().equals(userInfo.getUserName())) {
					equaluser = "equal";
				} else {
					equaluser = "notequal";
				}
			}
			/** 访问权限 */
			List visitPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "访问权限", "", uId, groupId,
					listRole);
			/** 新建权限 */
			List newPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "新建权限", "", uId, groupId,
					listRole);
			/** 编辑权限 */
			List editPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "编辑权限", "", uId, groupId,
					listRole);
			/** 删除权限 */
			List delPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "删除权限", "", uId, groupId,
					listRole);
			/** 签阅权限 */
			List checkPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "签阅权限", "", uId, groupId,
					listRole);
			/** 签阅权限 */
			List ownPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "所有者权限", "", uId, groupId,
					listRole);
			/** 下载权限 */
			List downPrivHave = dataPrivImpl.getFileSortNedPriv(String
					.valueOf(fileSort.getSortId()), "下载权限", "", uId, groupId,
					listRole);

			Map map = new HashMap();
			map.put("newPrivHave", newPrivHave.size());
			map.put("visitPrivHave", visitPrivHave.size());
			map.put("editPrivHave", editPrivHave.size());
			map.put("delPrivHave", delPrivHave.size());
			map.put("checkPrivHave", checkPrivHave.size());
			map.put("ownPrivHave", ownPrivHave.size());
			map.put("downPrivHave", downPrivHave.size());
			map.put("equaluser", equaluser);
			Gson json = new Gson();
			String aa = json.toJson(map);
			out.print(aa);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 功能：获取文件夹的权限列表
	 * 
	 * @return
	 */
	public String getFileSortPriv() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			HttpServletRequest hr = getRequest();

			if (dataPriv.getRefId() != null && dataPriv.getModuleName() != null) {
				DataPriv dataP = dataPrivImpl.getDataPrivByRefId(dataPriv
						.getRefId(), dataPriv.getModuleName());

				if (dataP != null) {

					int vid = dataP.getId();
					String roleIds = dataP.getRoleIds();
					String roleNames = dataP.getRoleNames();
					String userIds = dataP.getUserIds();
					String userNames = dataP.getUserNames();
					String groupIds = dataP.getGroupIds();
					String groupNames = dataP.getGroupNames();
					Map map = new HashMap();

					map.put("vid", vid);
					map.put("roleIds", roleIds);
					map.put("roleNames", roleNames);
					map.put("userIds", userIds);
					map.put("userNames", userNames);
					map.put("groupIds", groupIds);
					map.put("groupNames", groupNames);
					Gson json = new Gson();
					String jsonPriv = json.toJson(map);
					out.print(jsonPriv);
				}
			} //else {
				//System.out.print("1");
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：获取所有类别的文件夹
	 * 
	 * @return
	 */
	public String findAllFileSorts() {
		PrintWriter out = null;
		try {

			out = this.getResponse().getWriter();
			Object userObject = this.getSession().getAttribute("adminUser");
			if (userObject != null) {
				PageRequest pageRequest = (PageRequest) this.getPageable();
				// 分页信息
				Page<FileSort> pageInfo = fileSortImpl.findAllFileSorts(pageRequest);
				/** 得到结果 */
				List<FileSort> FileSortList = pageInfo.getContent();
				/** 封装list */
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				if (FileSortList != null) {
					int i = pageRequest.getPageNumber() * this.getIDisplayLength() + 1;
					for (FileSort filesort : FileSortList) {
						Map<String, Object> map = new HashMap<String, Object>();
						/** 标识符 */
						map.put("id", filesort.getSortId());
						/** 序号 */
						map.put("no", i);
						String sortNo = filesort.getSortNo();
						if (StringUtils.isNotEmpty(sortNo)) {
							map.put("sortNo", sortNo);
						} else {
							map.put("sortNo", "&nbsp;");
						}
						/** 文件夹名字 */
						String sortName = filesort.getSortName();
						if (StringUtils.isNotEmpty(sortName)) {
							map.put("sortName", sortName);
						} else {
							map.put("sortName", "&nbsp;");
						}
						mapList.add(map);
						i++;
					}
				}
				this.ajaxPage(pageInfo, mapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return null;
	}

	/**
	 * 功能：得到文件夹的权限列表
	 * 
	 * @return
	 */
	public String fileSortPrivList() {
		PrintWriter out = null;
		try {
			{
				out = this.getResponse().getWriter();
				Object userObject = this.getSession().getAttribute("adminUser");
				UserInfo userInfo = (UserInfo) userObject;
				String aa = "测试" + fileSortId;
				String finalFileSortIdPrivs = "'访问权限','新建权限','删除权限','编辑权限','下载权限','所有者权限'";
				/** 得到子文件夹的id的集合 */
				List<FileSort> list = fileSortImpl.findFileSortByLikeId(Integer
						.parseInt(fileSortId));
				StringBuffer fileSortIds = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					FileSort fileSort = list.get(i);
					int fileSortId = fileSort.getSortId();
					fileSortIds.append(String.valueOf(fileSortId)).append(",");
					// fileSortIds += fileSortId + ",";
				}
				String finalFileSortIds = "";
				/** 文件夹的id集合 */
				finalFileSortIds = fileSortId + "," + fileSortIds;
				/** 根据id得到当前的权限表记录的集合 */

				List<ModulePrivVo> voList = new ArrayList<ModulePrivVo>();
				List<DataPriv> dataPrivList = dataPrivImpl.getFileSortById(
						finalFileSortIds, finalFileSortIdPrivs);
				/** 封装map */

				Map<Integer, ModulePrivVo> map = new HashMap<Integer, ModulePrivVo>();
				for (int i = 0; i < dataPrivList.size(); i++) {
					DataPriv dp = dataPrivList.get(i);
					ModulePrivVo mpv = map.get(dp.getRefId());
					if (null == mpv) {
						FileSort fileSo = fileSortImpl.findByFileSortId(dp
								.getRefId());
						String sortName = fileSo.getSortName();
						mpv = new ModulePrivVo();
						mpv.setRefName(sortName);
						map.put(dp.getRefId(), mpv);
					}

					if ("访问权限".equals(dp.getModuleName())) {

						StringBuffer visitPriv = new StringBuffer();
						List<UserInfo> userList = userService.findUsersByIds(dp.getUserIds(), dp
										.getGroupIds(), dp.getRoleIds());
						if (null != userList && userList.size() > 0) {
							for (UserInfo user : userList) {
								visitPriv.append(user.getUserName())
										.append(",");
								// visitPriv+=user.getUserName()+",";
							}
							/*
							 * String visitPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */

							mpv.setVisitPriv(visitPriv.toString());
						}
					} else if ("新建权限".equals(dp.getModuleName())) {
						StringBuffer newPriv = new StringBuffer();
						List<UserInfo> userListnew = userService
								.findUsersByIds( dp
										.getUserIds(), dp.getGroupIds(), dp
										.getRoleIds());
						if (null != userListnew && userListnew.size() > 0) {
							for (UserInfo user : userListnew) {
								newPriv.append(user.getUserName()).append(",");
								// newPriv+=user.getUserName()+",";
							}
							/*
							 * String newPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */
							mpv.setNewPriv(newPriv.toString());
						}
					} else if ("删除权限".equals(dp.getModuleName())) {
						StringBuffer delPriv = new StringBuffer();
						List<UserInfo> userListdel = userService
								.findUsersByIds(dp
										.getUserIds(), dp.getGroupIds(), dp
										.getRoleIds());
						if (null != userListdel && userListdel.size() > 0) {
							for (UserInfo user : userListdel) {
								delPriv.append(user.getUserName()).append(",");
								// delPriv+=user.getUserName()+",";
							}
							/*
							 * String delPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */
							mpv.setDelPriv(delPriv.toString());
						}
					} else if ("编辑权限".equals(dp.getModuleName())) {
						StringBuffer editPriv = new StringBuffer();
						List<UserInfo> userListedit = userService
								.findUsersByIds(dp
										.getUserIds(), dp.getGroupIds(), dp
										.getRoleIds());
						if (null != userListedit && userListedit.size() > 0) {
							for (UserInfo user : userListedit) {
								editPriv.append(user.getUserName()).append(",");
								// editPriv+=user.getUserName()+",";
							}
							/*
							 * String editPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */
							mpv.setEditPriv(editPriv.toString());
						}
					} else if ("下载权限".equals(dp.getModuleName())) {
						StringBuffer downPriv = new StringBuffer();
						List<UserInfo> userListdown = userService
								.findUsersByIds( dp
										.getUserIds(), dp.getGroupIds(), dp
										.getRoleIds());
						if (null != userListdown && userListdown.size() > 0) {
							for (UserInfo user : userListdown) {
								downPriv.append(user.getUserName()).append(",");
								// downPriv+=user.getUserName()+",";
							}
							/*
							 * String downPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */
							mpv.setDownPriv(downPriv.toString());
						}
					} else if ("所有者权限".equals(dp.getModuleName())) {
						StringBuffer ownPriv = new StringBuffer();
						List<UserInfo> userListown = userService
								.findUsersByIds(dp
										.getUserIds(), dp.getGroupIds(), dp
										.getRoleIds());
						if (null != userListown && userListown.size() > 0) {
							for (UserInfo user : userListown) {
								ownPriv.append(user.getUserName()).append(",");
								// ownPriv+=user.getUserName()+",";
							}
							/*
							 * String ownPriv = dp.getGroupNames() + "," +
							 * dp.getRoleNames() + "," + dp.getUserNames();
							 */
							mpv.setOwnPriv(ownPriv.toString());
						}
					}
				}
				Set<Map.Entry<Integer, ModulePrivVo>> set = map.entrySet();
				if (null != set && !set.isEmpty()) {
					for (Map.Entry<Integer, ModulePrivVo> entry : set) {
						voList.add(entry.getValue());
					}
				}
				Map<String, Object> mapLast = new HashMap<String, Object>();
				mapLast.put("mapList", voList);
				Gson json = new Gson();
				String jsons = json.toJson(mapLast);
				PrintWriter writer = new PrintWriter(this.getResponse()
						.getWriter());
				writer.print(jsons);

			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：根据人员得到文件夹的权限列表
	 * 
	 * @return
	 */
	public String fileSortPrivListByPerson() {
		PrintWriter out = null;
		try {
			{
				out = this.getResponse().getWriter();
				Object userObject = this.getSession().getAttribute("adminUser");
				String finalFileSortIdPrivs = "'访问权限','新建权限','删除权限','编辑权限','下载权限','所有者权限'";
				/** 得到子文件夹的id的集合 */
				List<FileSort> list = fileSortImpl.findFileSortByLikeId(Integer
						.parseInt(fileSortId));
				StringBuffer fileSortIds = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					FileSort fileSort = list.get(i);
					int fileSortId = fileSort.getSortId();
					fileSortIds.append(String.valueOf(fileSortId)).append(",");
					// fileSortIds += fileSortId + ",";
				}
				String finalFileSortIds = "";
				/** 文件夹的id集合 */
				finalFileSortIds = fileSortId + "," + fileSortIds;
				/** 根据id得到当前的权限表记录的集合 */

				List<ModulePrivVo> voList = new ArrayList<ModulePrivVo>();
				List<DataPriv> dataPrivList = dataPrivImpl.getFileSortById(
						finalFileSortIds, finalFileSortIdPrivs);
				/** 封装map */
				/** 用户的标识符 */

				/** 组的标识符 */
				UserInfo u = userService.findOne(userId);
				int groupId = u.getGroupId();
//				BaseGroupUser groupInfoBean = groupUserService.getGroupUserByUserId(userId);
//				int groupId = groupInfoBean.getGroupId();

				/** 根据用户查询角色列表 */
				List<Integer> listRole = new ArrayList<Integer>();
				List<RoleInfo> roleId = roleService.getRoleByUser(userId);

				if (roleId != null) {
					for (int i = 0; i < roleId.size(); i++) {
						RoleInfo roleInfo = roleId.get(i);

						listRole.add(roleInfo.getRoleId());
					}
				}

				Map<Integer, ModulePrivVo> map = new HashMap<Integer, ModulePrivVo>();
				for (int i = 0; i < dataPrivList.size(); i++) {
					DataPriv dp = dataPrivList.get(i);
					ModulePrivVo mpv = map.get(dp.getRefId());
					if (null == mpv) {
						FileSort fileSo = fileSortImpl.findByFileSortId(dp
								.getRefId());
						String sortName = fileSo.getSortName();
						mpv = new ModulePrivVo();
						mpv.setRefName(sortName);
						map.put(dp.getRefId(), mpv);
					}

					if ("访问权限".equals(dp.getModuleName())) {
						List visitPrivHave = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "访问权限", "", userId, groupId,
								listRole);

						if (visitPrivHave.size() > 0) {
							mpv.setVisitPriv("√");
						} else {
							mpv.setVisitPriv("");
						}

					} else if ("新建权限".equals(dp.getModuleName())) {
						List newPrivHave = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "新建权限", "", userId, groupId,
								listRole);

						if (newPrivHave.size() > 0) {
							mpv.setNewPriv("√");
						} else {
							mpv.setNewPriv("");
						}
					} else if ("删除权限".equals(dp.getModuleName())) {
						List delPriv = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "删除权限", "", userId, groupId,
								listRole);

						if (delPriv.size() > 0) {
							mpv.setDelPriv("√");
						} else {
							mpv.setDelPriv("");
						}

					} else if ("编辑权限".equals(dp.getModuleName())) {
						List editPriv = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "编辑权限", "", userId, groupId,
								listRole);

						if (editPriv.size() > 0) {
							mpv.setEditPriv("√");
						} else {
							mpv.setEditPriv("");
						}

					} else if ("下载权限".equals(dp.getModuleName())) {
						List downPriv = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "下载权限", "", userId, groupId,
								listRole);

						if (downPriv.size() > 0) {
							mpv.setDownPriv("√");
						} else {
							mpv.setDownPriv("");
						}

					} else if ("所有者权限".equals(dp.getModuleName())) {
						List ownPriv = dataPrivImpl.getFileSortNedPriv(
								fileSortId, "所有者权限", "", userId, groupId,
								listRole);

						if (ownPriv.size() > 0) {
							mpv.setOwnPriv("√");
						} else {
							mpv.setOwnPriv("");
						}

					}
				}
				Set<Map.Entry<Integer, ModulePrivVo>> set = map.entrySet();
				if (null != set && !set.isEmpty()) {
					for (Map.Entry<Integer, ModulePrivVo> entry : set) {
						voList.add(entry.getValue());
					}
				}
				Map<String, Object> mapLast = new HashMap<String, Object>();
				mapLast.put("mapList", voList);
				Gson json = new Gson();
				String jsons = json.toJson(mapLast);
				PrintWriter writer = new PrintWriter(this.getResponse()
						.getWriter());
				writer.print(jsons);
				writer.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：设置文件夹权限
	 * 
	 * @return
	 */
	public String setFileSort() {
		PrintWriter out = null;
		try {
			/** 获取输出函数 */
			out = this.getResponse().getWriter();

			/** 如果群组名称为空 */
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				/** 批量设置 */
				if (dataPriv.getModuleName().equals("批量设置")) {

					if (addP.equals("addP")) {
						if (visitPriv.equals("setVisit")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "访问权限");
							if (dataP != null) {
								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());
							} else {
								DataPriv dataPrivVisit = new DataPriv();
								dataPrivVisit.setRefId(dataPriv.getRefId());
								dataPrivVisit.setUserIds(dataPriv.getUserIds());
								dataPrivVisit.setUserNames(dataPriv
										.getUserNames());
								dataPrivVisit.setGroupIds(dataPriv
										.getGroupIds());
								dataPrivVisit.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivVisit.setRoleIds(dataPriv.getRoleIds());
								dataPrivVisit.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivVisit.setModuleName("访问权限");
								dataPrivVisit.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivVisit.setCreateUserId(adminUser
										.getUserId());
								dataPrivVisit.setIsDelete(0);
								dataPrivVisit.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivVisit.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivVisit.setCompanyId(adminUser
										.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivVisit);
							}
						}
						if (newPriv.equals("setNew")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "新建权限");

							if (dataP != null) {
								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());

							} else {
								DataPriv dataPrivNew = new DataPriv();
								dataPrivNew.setRefId(dataPriv.getRefId());
								dataPrivNew.setUserIds(dataPriv.getUserIds());
								dataPrivNew.setUserNames(dataPriv
										.getUserNames());
								dataPrivNew.setGroupIds(dataPriv.getGroupIds());
								dataPrivNew.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivNew.setRoleIds(dataPriv.getRoleIds());
								dataPrivNew.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivNew.setModuleName("新建权限");
								dataPrivNew.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivNew.setCreateUserId(adminUser
										.getUserId());
								dataPrivNew.setIsDelete(0);
								dataPrivNew.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivNew.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivNew
										.setCompanyId(adminUser.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivNew);
							}
						}
						if (delPriv.equals("setDel")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "删除权限");
							if (dataP != null) {
								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());
							} else {
								DataPriv dataPrivDel = new DataPriv();
								dataPrivDel.setRefId(dataPriv.getRefId());
								dataPrivDel.setUserIds(dataPriv.getUserIds());
								dataPrivDel.setUserNames(dataPriv
										.getUserNames());
								dataPrivDel.setGroupIds(dataPriv.getGroupIds());
								dataPrivDel.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivDel.setRoleIds(dataPriv.getRoleIds());
								dataPrivDel.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivDel.setModuleName("删除权限");
								dataPrivDel.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivDel.setCreateUserId(adminUser
										.getUserId());
								dataPrivDel.setIsDelete(0);
								dataPrivDel.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivDel.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivDel
										.setCompanyId(adminUser.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivDel);
							}
						}
						if (editPriv.equals("setEdit")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "编辑权限");
							if (dataP != null) {

								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());

							} else {

								DataPriv dataPrivEdit = new DataPriv();
								dataPrivEdit.setRefId(dataPriv.getRefId());
								dataPrivEdit.setUserIds(dataPriv.getUserIds());
								dataPrivEdit.setUserNames(dataPriv
										.getUserNames());
								dataPrivEdit
										.setGroupIds(dataPriv.getGroupIds());
								dataPrivEdit.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivEdit.setRoleIds(dataPriv.getRoleIds());
								dataPrivEdit.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivEdit.setModuleName("编辑权限");
								dataPrivEdit.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivEdit.setCreateUserId(adminUser
										.getUserId());
								dataPrivEdit.setIsDelete(0);
								dataPrivEdit.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivEdit.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivEdit.setCompanyId(adminUser
										.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivEdit);
							}
						}
						/*
						 * if (checkPriv.equals("setCheck")) { DataPriv dataP =
						 * dataPrivImpl.getDataPrivByRefId(dataPriv.getRefId(),
						 * "签阅权限"); if (dataP != null) {
						 * 
						 * } else { DataPriv dataPrivcheck = new DataPriv();
						 * dataPrivcheck.setRefId(dataPriv.getRefId());
						 * dataPrivcheck.setUserIds(dataPriv.getUserIds());
						 * dataPrivcheck.setUserNames(dataPriv.getUserNames());
						 * dataPrivcheck.setGroupIds(dataPriv.getGroupIds());
						 * dataPrivcheck
						 * .setGroupNames(dataPriv.getGroupNames());
						 * dataPrivcheck.setRoleIds(dataPriv.getRoleIds());
						 * dataPrivcheck.setRoleNames(dataPriv.getRoleNames());
						 * dataPrivcheck.setModuleName("签阅权限");
						 * dataPrivcheck.setCreateTime(new
						 * Timestamp(Calendar.getInstance()
						 * .getTimeInMillis()));
						 * dataPrivcheck.setCreateUserId(adminUser.getUserId());
						 * dataPrivcheck.setIsDelete(0);
						 * dataPrivcheck.setLastUpdateTime(new
						 * Timestamp(Calendar
						 * .getInstance().getTimeInMillis()));
						 * dataPrivcheck.setLastUpdateUserId
						 * (adminUser.getUserId());
						 * dataPrivcheck.setCompyId(adminUser.getCompanyId());
						 * dataPrivImpl.save(dataPrivcheck); } }
						 */
						if (downPriv.equals("setDown")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "下载权限");
							if (dataP != null) {
								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());

							} else {
								DataPriv dataPrivDown = new DataPriv();
								dataPrivDown.setRefId(dataPriv.getRefId());
								dataPrivDown.setUserIds(dataPriv.getUserIds());
								dataPrivDown.setUserNames(dataPriv
										.getUserNames());
								dataPrivDown
										.setGroupIds(dataPriv.getGroupIds());
								dataPrivDown.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivDown.setRoleIds(dataPriv.getRoleIds());
								dataPrivDown.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivDown.setModuleName("下载权限");
								dataPrivDown.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivDown.setCreateUserId(adminUser
										.getUserId());
								dataPrivDown.setIsDelete(0);
								dataPrivDown.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivDown.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivDown.setCompanyId(adminUser
										.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivDown);
							}
						}
						if (ownPriv.equals("setOwn")) {
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "所有者权限");
							if (dataP != null) {
								dataPrivImpl.updateDataPrivById(dataP.getId(),
										dataPriv.getRoleIds(), dataPriv
												.getRoleNames(), dataPriv
												.getUserIds(), dataPriv
												.getUserNames(), dataPriv
												.getGroupIds(), dataPriv
												.getGroupNames());

							} else {
								DataPriv dataPrivOwn = new DataPriv();
								dataPrivOwn.setRefId(dataPriv.getRefId());
								dataPrivOwn.setUserIds(dataPriv.getUserIds());
								dataPrivOwn.setUserNames(dataPriv
										.getUserNames());
								dataPrivOwn.setGroupIds(dataPriv.getGroupIds());
								dataPrivOwn.setGroupNames(dataPriv
										.getGroupNames());
								dataPrivOwn.setRoleIds(dataPriv.getRoleIds());
								dataPrivOwn.setRoleNames(dataPriv
										.getRoleNames());
								dataPrivOwn.setModuleName("所有者权限");
								dataPrivOwn.setCreateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivOwn.setCreateUserId(adminUser
										.getUserId());
								dataPrivOwn.setIsDelete(0);
								dataPrivOwn.setLastUpdateTime(new Timestamp(
										Calendar.getInstance()
												.getTimeInMillis()));
								dataPrivOwn.setLastUpdateUserId(adminUser
										.getUserId());
								dataPrivOwn
										.setCompanyId(adminUser.getCompanyId());
								dataPrivImpl.saveOrUpdate(dataPrivOwn);
							}
						}
						out.print("1");

					}
					if (addP.equals("delP")) {
						if (visitPriv.equals("setVisit")) {

							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "访问权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (newPriv.equals("setNew")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "新建权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (delPriv.equals("setDel")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "删除权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (editPriv.equals("setEdit")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "编辑权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (editPriv.equals("setCheck")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "签阅权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (downPriv.equals("setDown")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "下载权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						if (ownPriv.equals("setOwn")) {
							/*** 加加入的 */
							DataPriv dataP = dataPrivImpl.getDataPrivByRefId(
									dataPriv.getRefId(), "所有者权限");
							String userids = dataP.getUserIds();
							String usernames = dataP.getUserNames();
							String groupids = dataP.getGroupIds();
							String groupnames = dataP.getGroupNames();
							String roleids = dataP.getRoleIds();
							String rolenames = dataP.getRoleNames();
							int dataprivid = dataP.getId();

							String userid = getStr(userids, dataPriv
									.getUserIds());
							String username = getStr(usernames, dataPriv
									.getUserNames());
							String groupid = getStr(groupids, dataPriv
									.getGroupIds());
							String groupidname = getStr(groupnames, dataPriv
									.getGroupNames());
							String roleid = getStr(roleids, dataPriv
									.getRoleIds());
							String rolename = getStr(rolenames, dataPriv
									.getRoleNames());

							dataPrivImpl.updateDataPrivById(dataprivid, roleid,
									rolename, userid, username, groupid,
									groupidname);
						}
						out.print("2");

					}

				}
				/** 批量设置 */
				else {

					dataPriv.setId(dataPriv.getId());
					dataPriv.setCreateTime(new Timestamp(Calendar.getInstance()
							.getTimeInMillis()));
					dataPriv.setCreateUserId(adminUser.getUserId());
					dataPriv.setIsDelete(0);
					dataPriv.setLastUpdateTime(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					dataPriv.setLastUpdateUserId(adminUser.getUserId());
					dataPriv.setCompanyId(adminUser.getCompanyId());
					dataPrivImpl.saveOrUpdate(dataPriv);
					/** 重置子文件夹 */

					if (dataPriv.getParam1().equals("set")) {
						List<FileSort> filesorts = fileSortImpl
								.findFileSortByLikeId(dataPriv.getRefId());
						for (int i = 0; i < filesorts.size(); i++) {
							FileSort filesort = filesorts.get(i);
							/** 文件夹的id */
							int sortId = filesort.getSortId();
							int pvid = 0;
							/** 若子文件夹已经存在，更新文件夹 */
							DataPriv dpriv = dataPrivImpl.getDataPrivByRefId(
									sortId, dataPriv.getModuleName());
							if (dpriv != null) {
								/*
								 * pvid=dpriv.getId(); DataPriv dp=new
								 * DataPriv(); dp.setId(pvid);
								 */
								dpriv.setCreateTime(new Timestamp(Calendar
										.getInstance().getTimeInMillis()));
								dpriv.setCreateUserId(adminUser.getUserId());
								dpriv.setIsDelete(0);
								dpriv.setLastUpdateTime(new Timestamp(Calendar
										.getInstance().getTimeInMillis()));
								dpriv
										.setLastUpdateUserId(adminUser
												.getUserId());
								dpriv.setCompanyId(adminUser.getCompanyId());
								dpriv.setGroupIds(dataPriv.getGroupIds());
								dpriv.setGroupNames(dataPriv.getGroupNames());
								dpriv.setRefId(sortId);
								dpriv.setRoleIds(dataPriv.getRoleIds());
								dpriv.setRoleNames(dataPriv.getRoleNames());
								dpriv.setUserIds(dataPriv.getUserIds());
								dpriv.setUserNames(dataPriv.getUserNames());
								dpriv.setModuleName(dataPriv.getModuleName());
								dataPrivImpl.saveOrUpdate(dpriv);
							}
							/** 重置文件夹 */
							if (dpriv == null) {
								DataPriv dp = new DataPriv();
								dp.setCreateTime(new Timestamp(Calendar
										.getInstance().getTimeInMillis()));
								dp.setCreateUserId(adminUser.getUserId());
								dp.setIsDelete(0);
								dp.setLastUpdateTime(new Timestamp(Calendar
										.getInstance().getTimeInMillis()));
								dp.setLastUpdateUserId(adminUser.getUserId());
								dp.setCompanyId(adminUser.getCompanyId());
								dp.setGroupIds(dataPriv.getGroupIds());
								dp.setGroupNames(dataPriv.getGroupNames());
								dp.setRefId(sortId);
								dp.setRoleIds(dataPriv.getRoleIds());
								dp.setRoleNames(dataPriv.getRoleNames());
								dp.setUserIds(dataPriv.getUserIds());
								dp.setUserNames(dataPriv.getUserNames());
								dp.setModuleName(dataPriv.getModuleName());
								dataPrivImpl.saveOrUpdate(dp);
							}
							/** 根据模块名称和refId查找该文件夹是否已有了权限，若有就更新，没有就添加 */
							/*
							 * DataPriv
							 * dp=dataPrivImpl.getDataPrivByRefId(sortId,
							 * dataPriv.getModuleName()); if(dp!=null) {
							 * dataPrivImpl.updateDataPriv(sortId,
							 * dataPriv.getModuleName(), dataPriv.getRoleIds(),
							 * dataPriv.getRoleNames(), dataPriv.getUserIds(),
							 * dataPriv.getUserNames(), dataPriv.getGroupIds(),
							 * dataPriv.getGroupNames()); }
							 */
							/*
							 * else { dataPrivImpl.save(dataPriv); }
							 */

						}
					}

					out.print("1");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：添加文件夹
	 * 
	 * @return
	 */
	public String addFileSort() {
		PrintWriter out = null;

		try {
			// 获取输出函数
			out = this.getResponse().getWriter();
			// 如果群组名称为空

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				FileSort filesort = fileSortImpl.findByFileSortName(fileSort
						.getSortName(), adminUser.getUserId(), fileSort
						.getParentId(),fileSort.getSortType());
				if (filesort != null) {
					out.print("1");
				} else {
					fileSort.setCreateTime(new Timestamp(Calendar.getInstance()
							.getTimeInMillis()));
					fileSort.setCreateUserId(adminUser.getUserId());
					fileSort.setCreateUser(adminUser.getUserName());
					fileSort.setIsDelete(0);
					fileSort.setPath("/" + 0 + "/");
					fileSort.setParentId(0);
					fileSort.setSortType("0");
					fileSort.setLastUpdateTime(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					fileSort.setLastUpdateUserId(adminUser.getUserId());
					fileSort.setCompanyId(adminUser.getCompanyId());
					fileSortImpl.save(fileSort);

					// 系统日志
					UserInfo userInfo = (UserInfo) getSession().getAttribute(
							"adminUser");
					Log log = new Log();
					log.setCompanyId(userInfo.getCompanyId());
					log.setInsertTime(new Timestamp(new Date().getTime()));
					log.setModuleName("file");
					log.setSysName("xyoa3.0");
					log.setIp(getRequest().getRemoteAddr());
					log.setIsDelete(0);
					log.setLogType(LogType.LOG_USER_ADD);
					// log.setRefId(Integer.parseInt(fileSort.getFileSorts().toString()));
					log.setRefId(fileSort.getSortId());
					log.setRemark("文件夹'" + fileSort.getSortName() + "'添加成功");
					log.setUserId(userInfo.getUserId());
					log.setUserName(userInfo.getUserName());
					log.setType(0);// 手动添加系统日志
					logImpl.saveOrUpdate(log);

					if (fileSort.getSortId() > 0) {
						out.print("2");
						return null;
					} else {
						out.print("部门添加失败！");
						return null;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("部门添加失败！");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：添加子文件夹
	 * 
	 * @return
	 */
	public String addChildFileSort() {
		PrintWriter out = null;
		String name = "";
		int fileSortId = 0;
		String path = "";
		try {
			// 获取输出函数
			out = this.getResponse().getWriter();
			// 如果群组名称为空

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				String filePath = fileSort.getPath();
				if (filePath == null || filePath == "" || "".equals(filePath)) {
					filePath = "/0/";
				}
				FileSort filesort = fileSortImpl.findByFileSortName(fileSort
						.getSortName(), adminUser.getUserId(), fileSort
						.getParentId(),fileSort.getSortType());
				FileSort fsort = fileSortImpl.findByFileSortId(fileSort
						.getParentId());
				if (fsort != null) {
					name = fsort.getSortName();
					fileSortId = fsort.getSortId();
					path = fsort.getPath();
				}

				if (filesort != null) {
					out.print("1");
				} else {
					fileSort.setCreateTime(new Timestamp(Calendar.getInstance()
							.getTimeInMillis()));
					fileSort.setCreateUserId(adminUser.getUserId());
					fileSort.setCreateUser(adminUser.getUserName());
					fileSort.setIsDelete(0);
					fileSort.setPath(fileSort.getPath() + "/"
							+ fileSort.getParentId() + "/");
					fileSort.setParentId(fileSort.getParentId());
					fileSort.setSortType(fileSort.getSortType());

					fileSort.setLastUpdateTime(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					fileSort.setLastUpdateUserId(adminUser.getUserId());
					fileSort.setCompanyId(adminUser.getCompanyId());
					fileSortImpl.save(fileSort);

					/** 子文件夹继承了父文件夹的权限 */
					// dataPrivImpl

					List<DataPriv> list = dataPrivImpl
							.getChildFileSortById(fileSort.getParentId());
					if (list.size() > 0) {

						for (int i = 0; i < list.size(); i++) {
							DataPriv datapriv = list.get(i);
							String modulename = datapriv.getModuleName();
							String userids = datapriv.getUserIds();
							String usernames = datapriv.getUserNames();
							String groupids = datapriv.getGroupIds();
							String groupnames = datapriv.getGroupNames();
							String roleids = datapriv.getRoleIds();
							String rolenames = datapriv.getRoleNames();
							int createuserid = datapriv.getCreateUserId();
							new Timestamp(Calendar.getInstance()
									.getTimeInMillis());
							adminUser.getUserId();
							DataPriv dataPriv = new DataPriv();
							dataPriv.setCompanyId(adminUser.getCompanyId());
							dataPriv.setCreateTime(new Timestamp(Calendar
									.getInstance().getTimeInMillis()));
							dataPriv.setRefId(fileSort.getSortId());
							dataPriv.setModuleName(modulename);
							dataPriv.setIsDelete(0);
							dataPriv.setUserIds(userids);
							dataPriv.setCreateUserId(adminUser.getUserId());
							dataPriv.setUserNames(usernames);
							dataPriv.setGroupIds(groupids);
							dataPriv.setGroupNames(groupnames);
							dataPriv.setRoleIds(roleids);
							dataPriv.setRoleNames(rolenames);
							dataPriv.setLastUpdateTime(new Timestamp(Calendar
									.getInstance().getTimeInMillis()));
							dataPrivImpl.saveOrUpdate(dataPriv);
						}
					}
					if (fileSort.getSortId() > 0) {
						Map<String, Object> jsonMap = new HashMap<String, Object>();
						jsonMap.put("name", name);
						jsonMap.put("fileSortId", fileSortId);
						jsonMap.put("path", path);
						Gson json = new Gson();
						String jsons = json.toJson(jsonMap);
						out.print(jsons);
					}

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("部门添加失败！");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：编辑文件夹
	 * 
	 * @return
	 */
	public String editFileSort() {
		try {

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			FileSort fils = fileSortImpl.findByFileSortId(fileSort.getSortId());
			List<FileSort> fileList = fileSortImpl
					.findFileSortByParentIdNotMine(fileSort.getSortName(), fils
							.getParentId(), fileSort.getSortId());// 判断是否重名
			if (fileList != null && fileList.size() != 0) {
				PrintWriter writer = new PrintWriter(this.getResponse()
						.getWriter());
				writer.print("1");
				writer.flush();
				writer.close();
			} else {
				Timestamp timestamp = new Timestamp(Calendar.getInstance()
						.getTimeInMillis());
				fileSort.setSortId(fileSort.getSortId());
				fileSortImpl.updateFileSort(fileSort.getSortNo(), fileSort
						.getSortName(), timestamp, adminUser.getUserId(),
						fileSort.getSortId());
				PrintWriter writer = new PrintWriter(this.getResponse()
						.getWriter());
				writer.print("2");
				writer.flush();
				writer.close();

			}

		} catch (Exception ex) {
			ajax("0");
			LOGGER.error(ex.getMessage());
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：删除文件夹
	 * 
	 * @return
	 */
	public String deleteFileSort() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			List list = fileSortImpl.findAllByFileSortId(fileSort.getSortId());
			if (list.size() == 0) {
				fileSortImpl.delete(fileSort.getSortId(),false);
				fileContentImpl.deleteFilesBySortId(fileSort.getSortId());
				out.print("");
				return null;
			} else {
				out.print("此文件夹下面有子文件夹，不能删除！");

				return null;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("部门删除失败！");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 功能：是否有有子文件件夹
	 * 
	 * @return
	 */
	public String checkChildFileSort() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			List list = fileSortImpl.findAllByFileSortId(fileSort.getSortId());
			if (list.size() > 0) {
				out.print("1");
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 功能：得到子文件夹
	 * 
	 * @return
	 */
	public String getFSortById() {
		PrintWriter out = null;

		try {
			// 获取输出函数
			out = this.getResponse().getWriter();
			// 如果群组名称为空

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {

				FileSort fsort = fileSortImpl.findByFileSortId(fileSort
						.getSortId());

				if (fileSort.getSortId() > 0) {
					Map<String, Object> jsonMap = new HashMap<String, Object>();

					jsonMap.put("sortNo", fsort.getSortNo());
					jsonMap.put("sortName", fsort.getSortName());
					Gson json = new Gson();
					String jsons = json.toJson(jsonMap);
					out.print(jsons);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("部门添加失败！");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 
	 * 功能：得到筛选列表字符串
	 * 
	 * @param str
	 * @param str2
	 * @return
	 */
	private static String getStr(String str, String str2) {
		StringBuffer res = new StringBuffer();
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		String[] arr1 = str.split(",");
		String[] arr2 = str2.split(",");
		for (String s : arr1) {
			if (s != "") {
				list.add(s);
			}
		}
		for (String s : arr2) {
			if (s != "") {
				list2.add(s);
			}
		}

		for (String s : list2) {
			list.remove(s);
		}
		for (String s : list) {
			res.append("").append(s).append(",");
			/* res+=""+s+","; */
		}

		return res.toString();
	}

	public FileSort getFileSort() {
		return fileSort;
	}

	public void setFileSort(FileSort fileSort) {
		this.fileSort = fileSort;
	}

	public DataPriv getDataPriv() {
		return dataPriv;
	}

	public void setDataPriv(DataPriv dataPriv) {
		this.dataPriv = dataPriv;
	}

	public String getVisitPriv() {
		return visitPriv;
	}

	public void setVisitPriv(String visitPriv) {
		this.visitPriv = visitPriv;
	}

	public String getAddP() {
		return addP;
	}

	public void setAddP(String addP) {
		this.addP = addP;
	}

	public String getNewPriv() {
		return newPriv;
	}

	public void setNewPriv(String newPriv) {
		this.newPriv = newPriv;
	}

	public String getEditPriv() {
		return editPriv;
	}

	public void setEditPriv(String editPriv) {
		this.editPriv = editPriv;
	}

	public String getDelPriv() {
		return delPriv;
	}

	public void setDelPriv(String delPriv) {
		this.delPriv = delPriv;
	}

	public String getDownPriv() {
		return downPriv;
	}

	public void setDownPriv(String downPriv) {
		this.downPriv = downPriv;
	}

	public String getOwnPriv() {
		return ownPriv;
	}

	public void setOwnPriv(String ownPriv) {
		this.ownPriv = ownPriv;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public String getCheckPriv() {
		return checkPriv;
	}

	public void setCheckPriv(String checkPriv) {
		this.checkPriv = checkPriv;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}
}