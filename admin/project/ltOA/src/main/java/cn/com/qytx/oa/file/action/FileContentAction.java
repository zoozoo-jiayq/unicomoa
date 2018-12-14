package cn.com.qytx.oa.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.service.IAffairsBody;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.file.domain.Attachment;
import cn.com.qytx.cbb.file.service.IAttachment;
import cn.com.qytx.oa.dataPriv.domain.DataPriv;
import cn.com.qytx.oa.dataPriv.service.IDataPriv;
import cn.com.qytx.oa.file.domain.FileContent;
import cn.com.qytx.oa.file.domain.FileSort;
import cn.com.qytx.oa.file.service.IFileContent;
import cn.com.qytx.oa.file.service.IFileSort;
import cn.com.qytx.oa.util.StringUtil;
import cn.com.qytx.oa.util.TimeUtil;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.RoleInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IRole;
import cn.com.qytx.platform.org.service.IRoleUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * 
 * 功能:文件action 版本: 1.0 开发人员: 徐长江 创建日期: 2013-3-21 修改日期: 2013-3-21 修改列表:
 */

public class FileContentAction extends BaseActionSupport {

	/** 文件标题 */
	private String subject;
	/** 发布人 */
	private String createUserId;
	/** 开始时间 */
	private Timestamp starttime;
	/** 结束时间 */
	private Timestamp endTime;

	/** 开始时间字符串 */
	private String starttimeStr;

	/** 结束时间字符串 */
	private String endtimeStr;
	/** 文件夹类的id */
	private int sortId;
	/** 文件id */
	private int id;
	/***
	 * 文件类型实体
	 */
	private FileSort fileSort;
	/***
	 * 文件实体
	 */
	private FileContent fileContent;

	/** 文件的id */
	private Integer[] fileIds;

	/** 提醒谁的用户id集合 */
	private String SMS_SELECT_REMIND_TO_ID;
	/** 提醒的类型 */
	private String tixing;

	/** 查询附件的 id */
	private int fileid;
	/** 附件的id */
	private String attachmentId;
	/** 文件 */
	FileContent filecontent = new FileContent();
	/** 修改返回类型 */
	private String filetype;

	/** 文件夹的路径 */
	private String path;
	/** 文件夹的id */
	private int fileSortId;
	/** 文件夹的名字 */
	private String filename;

	/** 日期格式化 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Resource(name="filePathConfig")
	private FilePathConfig filePathConfig;
	/** 文件类别impl */
	@Autowired
	IFileContent fileContentImpl;
	/** 文件impl */
	@Autowired
	IFileSort fileSortImpl;
	/** 权限设置impl */
	@Autowired
	IDataPriv dataPrivImpl;
	/** 用户信息 */
	@Autowired
	IUser userService;
	/** 群组人员 **/
//	@Autowired
//	IGroupUser groupUserService;
	
	/** 角色 **/
	@Autowired
	IRole roleService;
	@Autowired
	IRoleUser roleUserService;
	/** 附件的添加 **/
	@Resource(name="attachmentService")
	IAttachment attachmentService;
	/**
	 * 事务提醒主体Impl
	 */
	@Autowired
	IAffairsBody affairsBodyImpl;
	/** 创建时间 */
	private Timestamp createTime;
	/**
	 * 接收客户端时间
	 */
	private String createTimeStr;

	/**
	 * 文件类型 1公共文件 2共享文件
	 */
	private Integer type=1;

	@Autowired
	private IGroup groupService; // 部门服务类
	@Autowired
	private IDataPriv dataPrivService; // 权限服务类

	List<Attachment> attachListTemp;

	/**
	 * 
	 * 功能：获取文件列表
	 * 
	 * @return 返回根据类别的到的文件
	 */

	public String findAllFileContents() {
		PrintWriter out = null;
		try {
			long fileSize = 0;
			out = this.getResponse().getWriter();
			Object userObject = this.getSession().getAttribute("adminUser");
			if (userObject != null) {
				// 分页信息
				PageRequest pageRequest = (PageRequest) this.getPageable();
				UserInfo userInfo = (UserInfo) userObject;

				/** 用户的标识符 */
				int uId = userInfo.getUserId();
				
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

				if (subject == null) {
					subject = "";
				}

				Page<FileContent> pageInfo = fileContentImpl.getFileConentListById(pageRequest,
						sortId, subject, createUserId,starttime, endTime,uId,type);

				/** 得到结果 */
				List<FileContent> FileSortList = pageInfo.getContent();

				/** 封装list */
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				if (FileSortList != null) {
					int i = pageRequest.getPageNumber() * this.getIDisplayLength() + 1;
					for (FileContent fileContent : FileSortList) {
						Map<String, Object> map = new HashMap<String, Object>();
						/** 标识符 */
						map.put("id", fileContent.getContentId());
						/** 序号 */
						map.put("no", i);
						/** 文件名称 */
						String subject = fileContent.getSubject();
						if (StringUtils.isNotEmpty(subject)) {
							map.put("subject", subject);
						} else {
							map.put("subject", "&nbsp;");
						}
						/** 附近名称 */
						String attachment = fileContent.getAttachmentName();

						String attach = fileContent.getAttachment();

						String attFile = "";
						StringBuffer attName = new StringBuffer();
						StringBuffer attId = new StringBuffer();
						if (attach == null || "".equals(attach)) {

						} else {
							/** 批量添加附件 */
							String[] attIds = attach.substring(1,
									attach.length() - 1).split(",");
							for (int j = 0; j < attIds.length; j++) {
								String attid = attIds[j];
								if (attid != null || attid != ""
										|| !attid.endsWith("")) {
									String catalinaHome = filePathConfig.getFileUploadPath(); 
									String targetDirectory = catalinaHome
											+ "/upload/";

									Attachment AttachmentT = attachmentService
											.getAttachment(Integer
													.parseInt(attid));
									if (null != AttachmentT) {
										attFile += AttachmentT.getAttachFile()
												+ ",";
										File file = new File(targetDirectory
												+ AttachmentT.getAttachFile());
										if (file != null) {
											// fileSize+=getFileSizes(file);
											fileSize = getFileSizes(file);
										} else {
											fileSize = 0;
										}
										attName.append(
												AttachmentT.getAttachName())
												.append(",");
										// attName+=AttachmentT.getAttachName()+",";
										// attId+=AttachmentT.getId()+",";
										attId.append(
												AttachmentT.getId().toString())
												.append(",");
									}
								}
							}

							/** 批量添加附件结束 */
							/*
							 * attach=attach.substring(1, attach.length()-1);
							 * Attachment
							 * AttachmentT=attachmentService.getAttachment
							 * (Integer.parseInt(attach));
							 * attFile=AttachmentT.getAttachFile();
							 * attName=AttachmentT.getAttachName();
							 * attId=AttachmentT.getId();
							 */

						}
						/** 创建人 */
						String createUser = fileContent.getCreateUser();
						if (StringUtils.isNotEmpty(createUser)) {
							map.put("createUser", createUser);
						} else {
							map.put("createUser", "&nbsp;");
						}
						map.put("attFile", attFile);
						map.put("attName", attName);
						map.put("attId", attId);
						/** 文件内容 */
						String content = fileContent.getContent();
						if (StringUtils.isNotEmpty(content)) {
							map.put("content", content);
						} else {
							map.put("content", "&nbsp;");
						}
						/** 判断是否已经签阅 */
						String haveCheck = "1";
						map.put("haveCheck", "&nbsp;");

						/** 创建时间 判断是否有修改记录 */
						Date createTime = fileContent.getCreateTime();
						Date updateTime = fileContent.getLastUpdateTime();

						String havechang = "1";
						String notchang = "2";
						if (TimeUtil.dateCompare(createTime, updateTime))

						{
							map.put("haveChang", havechang);
							map.put("updateTime", TimeUtil.dateToStr(updateTime));
						} else {
							map.put("haveChang", notchang);
						}
						map.put("fileSize", formetFileSize(fileSize));

						/** 判断是否有修改记录 */
						if (StringUtils.isNotEmpty(content)) {
							map.put("content", content);
						} else {
							map.put("content", "&nbsp;");
						}
						if (StringUtils.isNotEmpty(attachment)) {
							map.put("attachment", attachment);
						} else {
							map.put("attachment", "&nbsp;");
						}
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						/** 创建时间 */
						if (StringUtils.isNotEmpty(TimeUtil
								.dateToStr(fileContent.getCreateTime()))) {
							map.put("createTime", DateTimeUtil.dateToString(
									fileContent.getCreateTime(),
									CbbConst.TIME_FORMAT_SUBSTR));
						} else {
							map.put("createTime", "&nbsp;");
						}
						/** 创建时间 */

						if (StringUtils.isNotEmpty(fileContent.getContentNo())) {
							map.put("contentNo", fileContent.getContentNo());
						} else {
							map.put("contentNo", "&nbsp;");
						}
						//map.put("editPrivHave", editPrivHave.size());
						//map.put("checkPrivHave", checkPrivHave.size());
						map.put("sortId", sortId);
						map.put("filename", filename);
						map.put("fileSortId", fileSortId);
						map.put("path", path);
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
	 * 
	 * 功能：获取文件的大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public long getFileSizes(File f) throws Exception {// 取得文件大小
		long s = 0;
		FileInputStream fis = null;
		try {
			if (f.exists()) {

				fis = new FileInputStream(f);
				s = fis.available();
				// fis.close();//新加入的
			} else {
				// f.createNewFile();
				// System.out.println("文件不存在");
				// s=1068;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return s;
	}

	/**
	 * 
	 * 功能：根据大小得到b k m g
	 * 
	 * @param fileS
	 * @return
	 */
	public String formetFileSize(long fileS) {// 转换文件大小
		// DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormat df = new DecimalFormat("0.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 
	 * 功能：获取文件列表
	 * 
	 * @return 返回根据类别的到的文件
	 */
	public String getFilenNoCheckConentListAll() {
		PrintWriter out = null;
		try {

			out = this.getResponse().getWriter();
			Object userObject = this.getSession().getAttribute("adminUser");
			if (userObject != null) {
				PageRequest pageRequest = (PageRequest) this.getPageable();
				UserInfo userInfo = (UserInfo) userObject;

				Page<FileContent> pageInfo = fileContentImpl.getFilenNoCheckConentListAll(
						pageRequest, String.valueOf(userInfo.getUserId()));
				/** 得到结果 */
				List<FileContent> FileSortList = pageInfo.getContent();

				/** 封装list */
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				if (FileSortList != null) {
					int i = pageRequest.getPageNumber() * this.getIDisplayLength() + 1;
					for (FileContent fileContent : FileSortList) {
						Map<String, Object> map = new HashMap<String, Object>();
						/** 标识符 */
						map.put("id", fileContent.getContentId());
						/** 序号 */
						map.put("no", i);
						/** 文件名称 */
						String subject = fileContent.getSubject();
						if (StringUtils.isNotEmpty(subject)) {
							map.put("subject", subject);
						} else {
							map.put("subject", "&nbsp;");
						}

						/** 附近名称 */
						String attachment = fileContent.getAttachmentName();

						String attach = fileContent.getAttachment();

						StringBuffer attFile = new StringBuffer();
						StringBuffer attName = new StringBuffer();
						StringBuffer attId = new StringBuffer();
						if (attach == null || "".equals(attach)) {

						} else {
							/** 批量添加附件 */
							String[] attIds = attach.substring(1,
									attach.length() - 1).split(",");
							for (int j = 0; j < attIds.length; j++) {
								String attid = attIds[j];
								if (!attid.equals("")) {
									Attachment AttachmentT = attachmentService
											.getAttachment(Integer
													.parseInt(attid));
									attFile.append(AttachmentT.getAttachFile())
											.append(",");
									attName.append(AttachmentT.getAttachName())
											.append(",");
									attId
											.append(
													AttachmentT.getId()
															.toString())
											.append(",");
									/*
									 * attFile+=AttachmentT.getAttachFile()+",";
									 * attName+=AttachmentT.getAttachName()+",";
									 * attId+=AttachmentT.getId()+",";
									 */
								}
							}
							/*
							 * attach=attach.substring(1, attach.length()-1);
							 * Attachment
							 * AttachmentT=attachmentService.getAttachment
							 * (Integer.parseInt(attach));
							 * attFile=AttachmentT.getAttachFile();
							 * attName=AttachmentT.getAttachName();
							 * attId=AttachmentT.getId();
							 */

						}
						/** 创建人 */
						String createUser = fileContent.getCreateUser();
						if (StringUtils.isNotEmpty(createUser)) {
							map.put("createUser", createUser);
						} else {
							map.put("createUser", "&nbsp;");
						}
						map.put("attFile", attFile);
						map.put("attName", attName);
						map.put("attId", attId);
						/** 文件内容 */
						String content = fileContent.getContent();
						if (StringUtils.isNotEmpty(content)) {
							map.put("content", content);
						} else {
							map.put("content", "&nbsp;");
						}
						String haveCheck = "1";
						map.put("haveCheck", "&nbsp;");

						/** 创建时间 */
						Date createTime = fileContent.getCreateTime();
						Date updateTime = fileContent.getLastUpdateTime();

						String havechang = "1";
						String notchang = "2";
						if (TimeUtil.dateCompare(createTime, updateTime))

						{
							map.put("haveChang", havechang);
							map.put("updateTime", TimeUtil
									.dateToStr(updateTime));
						} else {
							map.put("haveChang", notchang);
						}

						if (StringUtils.isNotEmpty(content)) {
							map.put("content", content);
						} else {
							map.put("content", "&nbsp;");
						}
						if (StringUtils.isNotEmpty(attachment)) {
							map.put("attachment", attachment);
						} else {
							map.put("attachment", "&nbsp;");
						}
						/** 创建时间 */
						if (StringUtils.isNotEmpty(TimeUtil
								.dateToStr(fileContent.getCreateTime()))) {
							map.put("createTime", TimeUtil
									.dateToStr(fileContent.getCreateTime()));
						} else {
							map.put("createTime", "&nbsp;");
						}
						/** 创建时间 */

						if (StringUtils.isNotEmpty(fileContent.getContentNo())) {
							map.put("contentNo", fileContent.getContentNo());
						} else {
							map.put("contentNo", "&nbsp;");
						}
						map.put("filename", filename);
						map.put("fileSortId", fileSortId);
						map.put("path", path);
						mapList.add(map);
						i++;
					}
				}
				this.ajaxPage(pageInfo,mapList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 
	 * 功能：添加文件
	 * 
	 * @return
	 */
	public String addFile() {
		PrintWriter out = null;
		try {
			// 获取输出函数
			out = this.getResponse().getWriter();
			// 如果群组名称为空

			String name = "";
			int fileSortId = 0;
			String path = "";
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {

				FileSort fileSort = fileSortImpl.findByFileSortId(sortId);
				fileContent.setFileSort(fileSort);
				name = fileSort.getSortName();
				fileSortId = fileSort.getSortId();
				path = fileSort.getPath();

				fileContent.setCreateUser(adminUser.getUserName());
				fileContent.setIsDelete(0);
				fileContent.setLastUpdateTime(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				fileContent.setLastUpdateUserId(adminUser.getUserId());
				fileContent.setCompanyId(adminUser.getCompanyId());

				/** 是否更新 */
				if (fileContent.getContentId() != 0) {
					fileContent.setCreateTime(createTime);
					fileContent.setCreateUserId(Integer.parseInt(createUserId));
				} else {
					fileContent.setCreateTime(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					fileContent.setCreateUserId(adminUser.getUserId());
				}
				fileContentImpl.saveOrUpdate(fileContent);

				/** 事务提醒 */
				if (tixing.equals("0")) {
					AffairsBody affairsBody = new AffairsBody();
					affairsBody.setCompanyId(adminUser.getCompanyId());
					Timestamp createTime = new Timestamp(new Date().getTime());
					affairsBody.setCreateTime(createTime);
					affairsBody.setCreateUserInfo(adminUser);
					affairsBody.setIsDelete(CbbConst.NOT_DELETE);
					affairsBody.setToIds(SMS_SELECT_REMIND_TO_ID);
					affairsBody.setFromUserInfo(adminUser);

					// 模块名
					affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_FILE);
					// 提醒内容
					StringBuffer sb = new StringBuffer();
					sb.append(adminUser.getUserName());
					sb.append("创建了新的文件");
					sb.append(fileContent.getSubject());
					affairsBody.setContentInfo(sb.toString());
					affairsBody.setSendTime(createTime);
					/** 创建时间 */
					Date createtime = fileContent.getCreateTime();
					Date updatetime = fileContent.getLastUpdateTime();
					String havechang = "2";
					String updatedate = "";
					if (TimeUtil.dateCompare(createtime, updatetime))

					{
						havechang = "1";
						updatedate = TimeUtil.dateToStr(updatetime);
					} else {
						havechang = "2";
					}
					// 设置提醒对应URL
					affairsBody
							.setRemindUrl("/logined/file/fcontentaffair.jsp?createUser="
									+ adminUser.getUserName()
									+ "&havechang="
									+ havechang
									+ "&subject="
									+ fileContent.getSubject()
									+ "&keyword="
									+ fileContent.getKeyWord()
									+ "&id="
									+ fileContent.getContentId());
					affairsBodyImpl.saveOrUpdate(affairsBody);
				}
				/** 事务提醒 自动发送有权权限的人 */
				if (tixing.equals("1")) {

					DataPriv datapriv = dataPrivImpl.getDataPrivByRefId(sortId,
							"新建权限");
					String userIds = datapriv.getUserIds();
					String groupIds = datapriv.getGroupIds();
					String roleIds = datapriv.getRoleIds();
					String userids = "";
					String groupids = "";
					String roleids = "";
					if (userIds != null && !"".equals(userIds)) {
						userids = userIds.substring(1, userIds.length() - 1);
					}
					if (groupIds != null && !"".equals(groupIds)) {
						groupids = groupIds.substring(1, groupIds.length() - 1);
					}
					if (roleIds != null && !"".equals(roleIds)) {
						roleids = roleIds.substring(1, roleIds.length() - 1);
					}
					List<UserInfo> userinfo = userService.findUsersByIds(
							 userids, groupids,
							roleids);
					UserInfo usinfo = null;
					StringBuffer usids = new StringBuffer(",");
					if (userinfo != null) {
						for (int i = 0; i < userinfo.size(); i++) {

							usinfo = (UserInfo) userinfo.get(i);
							if (usinfo.getUserId() != null) {
								usids.append(usinfo.getUserId().toString())
										.append(",");

								// usids+=usinfo.getUserId().toString()+",";
							}
						}
					}
					UserInfo u = (UserInfo) userService.findOne(adminUser.getUserId());
					AffairsBody affairsBody = new AffairsBody();
					affairsBody.setCompanyId(u.getCompanyId());
					Timestamp createTime = new Timestamp(new Date().getTime());
					affairsBody.setCreateTime(createTime);
					affairsBody.setCreateUserInfo((UserInfo) u);
					affairsBody.setIsDelete(CbbConst.NOT_DELETE);
					/** 发送有权限的人 */
					affairsBody.setToIds(usids.toString());
					affairsBody.setFromUserInfo((UserInfo) u);

					// 模块名
					affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_FILE);
					// 提醒内容
					StringBuffer sb = new StringBuffer();
					sb.append(u.getUserName());
					sb.append("创建了新的文件");
					sb.append(fileContent.getSubject());
					affairsBody.setContentInfo(sb.toString());
					affairsBody.setSendTime(createTime);
					/** 创建时间 */
					Date createtime = fileContent.getCreateTime();
					Date updatetime = fileContent.getLastUpdateTime();
					String havechang = "2";
					String updatedate = "";
					if (TimeUtil.dateCompare(createtime, updatetime))

					{
						havechang = "1";
					} else {
						havechang = "2";
					}
					// 设置提醒对应URL
					affairsBody
							.setRemindUrl("/logined/file/fcontentaffair.jsp?createUser="
									+ adminUser.getUserName()
									+ "&havechang="
									+ havechang
									+ "&subject="
									+ fileContent.getSubject()
									+ "&keyword="
									+ fileContent.getKeyWord()
									+ "&id="
									+ fileContent.getContentId());
					affairsBodyImpl.saveOrUpdate(affairsBody);
				}
				if (fileContent.getContentId() > 0) {
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					jsonMap.put("name", name);
					jsonMap.put("fileSortId", fileSortId);
					jsonMap.put("path", path);
					Gson json = new Gson();
					String jsons = json.toJson(jsonMap);
					out.print(jsons);
				} else {
					out.print("添加失败！");
					return null;
				}

			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("添加失败！");
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
	 * 功能：修改文件
	 * 
	 * @return
	 */
	public String updateFile() {
		PrintWriter out = null;
		try {
			// 获取输出函数
			out = this.getResponse().getWriter();
			// 如果群组名称为空

			String name = "";
			int fileSortId = 0;
			String path = "";
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				FileSort fileSort = fileSortImpl.findByFileSortId(sortId);
				if (fileSort != null) {
					fileContent.setFileSort(fileSort);
					name = fileSort.getSortName();
					fileSortId = fileSort.getSortId();
					path = fileSort.getPath();
				}

				fileContent.setCreateUser(adminUser.getUserName());
				fileContent.setIsDelete(0);
				fileContent.setLastUpdateTime(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				fileContent.setLastUpdateUserId(adminUser.getUserId());
				fileContent.setCompanyId(adminUser.getCompanyId());

				// **是否更新*//*
				if (fileContent.getContentId() != 0) {
					fileContent.setCreateTime(createTime);
					fileContent.setCreateUserId(Integer.parseInt(createUserId));
				} else {
					fileContent.setCreateTime(new Timestamp(Calendar
							.getInstance().getTimeInMillis()));
					fileContent.setCreateUserId(adminUser.getUserId());
				}
				// fileContentImpl.saveOrUpdate(fileContent);

				fileContentImpl.updateFile(fileContent.getSubject(),
						fileContent.getContent(), fileContent.getAttachment(),
						fileContent.getContentNo(), fileContent.getKeyWord(),
						fileContent.getAttachmentDesc(), fileContent
								.getContentId());
				/** 事务提醒 */
				if (tixing.equals("0")) {
					AffairsBody affairsBody = new AffairsBody();
					affairsBody.setCompanyId(adminUser.getCompanyId());
					Timestamp createTime = new Timestamp(new Date().getTime());
					affairsBody.setCreateTime(createTime);
					affairsBody.setCreateUserInfo(adminUser);
					affairsBody.setIsDelete(CbbConst.NOT_DELETE);
					affairsBody.setToIds(SMS_SELECT_REMIND_TO_ID);
					affairsBody.setFromUserInfo(adminUser);

					// 模块名
					affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_FILE);
					// 提醒内容
					StringBuffer sb = new StringBuffer();
					sb.append(adminUser.getUserName());
					sb.append("修改了文件");
					sb.append(fileContent.getSubject());
					affairsBody.setContentInfo(sb.toString());
					affairsBody.setSendTime(createTime);

					/** 创建时间 */
					Date createtime = fileContent.getCreateTime();
					Date updatetime = fileContent.getLastUpdateTime();
					String havechang = "2";
					String updatedate = "";
					if (TimeUtil.dateCompare(createtime, updatetime))

					{
						havechang = "1";
						updatedate = TimeUtil.dateToStr(updatetime);
					} else {
						havechang = "2";
					}
					// 设置提醒对应URL
					affairsBody
							.setRemindUrl("/logined/file/fcontentaffair.jsp?createUser="
									+ adminUser.getUserName()
									+ "&havechang="
									+ havechang
									+ "&subject="
									+ fileContent.getSubject()
									+ "&keyword="
									+ fileContent.getKeyWord()
									+ "&id="
									+ fileContent.getContentId());
					affairsBodyImpl.saveOrUpdate(affairsBody);
				}
				/** 事务提醒 自动发送有权权限的人 */
				if (tixing.equals("1")) {

					DataPriv datapriv = dataPrivImpl.getDataPrivByRefId(sortId,
							"新建权限");
					String userIds = datapriv.getUserIds();
					String groupIds = datapriv.getGroupIds();
					String roleIds = datapriv.getRoleIds();
					String userids = "";
					String groupids = "";
					String roleids = "";
					if (userIds != null && !"".equals(userIds)) {
						userids = userIds.substring(1, userIds.length() - 1);
					}
					if (groupIds != null && !"".equals(groupIds)) {
						groupids = groupIds.substring(1, groupIds.length() - 1);
					}
					if (roleIds != null && !"".equals(roleIds)) {
						roleids = roleIds.substring(1, roleIds.length() - 1);
					}
					List<UserInfo> userinfo = userService.findUsersByIds(userids, groupids,
							roleids);
					UserInfo usinfo = null;
					StringBuffer usids = new StringBuffer(",");
					if (userinfo != null) {
						for (int i = 0; i < userinfo.size(); i++) {

							usinfo = (UserInfo) userinfo.get(i);
							usids.append(usinfo.getUserId().toString()).append(
									",");
							// usids+=usinfo.getUserId()+",";

						}
					}
					UserInfo u = (UserInfo) userService.findOne( adminUser.getUserId());

					AffairsBody affairsBody = new AffairsBody();
					affairsBody.setCompanyId(u.getCompanyId());
					Timestamp createTime = new Timestamp(new Date().getTime());
					affairsBody.setCreateTime(createTime);
					affairsBody.setCreateUserInfo((UserInfo)u);
					affairsBody.setIsDelete(CbbConst.NOT_DELETE);
					/** 发送有权限的人 */
					affairsBody.setToIds(usids.toString());
					affairsBody.setFromUserInfo(u);

					// 模块名
					affairsBody.setSmsType(CbbConst.AFFAIRS_TYPE_FILE);
					// 提醒内容
					StringBuffer sb = new StringBuffer();
					sb.append(u.getUserName());
					sb.append("修改了文件");
					sb.append(fileContent.getSubject());
					affairsBody.setContentInfo(sb.toString());
					affairsBody.setSendTime(createTime);

					/** 创建时间 */
					Date createtime = fileContent.getCreateTime();
					Date updatetime = fileContent.getLastUpdateTime();
					String havechang = "2";
					String updatedate = "";
					if (TimeUtil.dateCompare(createtime, updatetime))

					{
						havechang = "1";
					} else {
						havechang = "2";
					}
					// 设置提醒对应URL
					affairsBody
							.setRemindUrl("/logined/file/fcontentaffair.jsp?createUser="
									+ adminUser.getUserName()
									+ "&havechang="
									+ havechang
									+ "&subject="
									+ fileContent.getSubject()
									+ "&keyword="
									+ fileContent.getKeyWord()
									+ "&id="
									+ fileContent.getContentId());
					affairsBodyImpl.saveOrUpdate(affairsBody);
				}

				if (fileContent.getContentId() > 0) {
					Map<String, Object> jsonMap = new HashMap<String, Object>();
					jsonMap.put("name", name);
					jsonMap.put("fileSortId", fileSortId);
					jsonMap.put("path", path);

					jsonMap.put("fileType", filetype);
					Gson json = new Gson();
					String jsons = json.toJson(jsonMap);
					out.print(jsons);
				} else {
					out.print("修改失败！");
					return null;
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());
			out.print("修改文件失败！");
			return null;
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * 功能：查阅情况查询
	 * 
	 * @return 返回到前台json数据
	 */
	public String getViewFileCheckList() {
		try {
			int readerSum = 0;
			int notReaderSum = 0;
			List<Integer> userIdList = new ArrayList<Integer>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			UserInfo currUser = (UserInfo) getSession().getAttribute(
					"adminUser");
			DataPriv dataPrivBean = dataPrivService.getDataPrivByRefId(sortId,
					"访问权限");

			if (null != dataPrivBean) {
				List<UserInfo> userList = userService.findUsersByIds(validateIsEmpty(dataPrivBean
						.getUserIds()), validateIsEmpty(dataPrivBean
						.getGroupIds()), validateIsEmpty(dataPrivBean
						.getRoleIds()));
				if (null != userList && userList.size() > 0) {
					for (UserInfo user : userList) {
						userIdList.add(user.getUserId());
					}
					// 查询人员角色部门
					List<Integer> groupIdList = getGroupIdListByUserIdList(userIdList);// 根据人员列表查询所有部门Id
					for (int groupId : groupIdList) {
						GroupInfo groupInfo = groupService.findOne(groupId);
						Map<String, Object> map = new HashMap<String, Object>();
						StringBuffer readerBuf = new StringBuffer();
						StringBuffer notReaderBuf = new StringBuffer();
						for (int userId : userIdList) {

							UserInfo temp =  userService.findOne(userId);
							GroupInfo group = groupService.getGroupByUserId(temp.getCompanyId(), userId);
							
							if (group!=null && group.getGroupId() == groupId) {
								UserInfo userInfo = (UserInfo) userService.findOne(userId);
								if (null != userInfo) {
									// 未读
									notReaderBuf.append(userInfo
											.getUserName()
											+ ",");
									notReaderSum++;
								}
							}
						}

						if (StringUtils.isNotEmpty(groupInfo.getGroupName())) {
							map.put("department", groupInfo.getGroupName());
						} else {
							map.put("department", "&nbsp;");
						}
						if (StringUtils.isNotEmpty(readerBuf.toString())) {
							map.put("reader", readerBuf.toString());
						} else {
							map.put("reader", "&nbsp;");
						}
						if (StringUtils.isNotEmpty(notReaderBuf.toString())) {
							map.put("notReader", notReaderBuf.toString());
						} else {
							map.put("notReader", "&nbsp;");
						}
						/*
						 * map.put("department", groupInfo.getGroupName());
						 * map.put("reader", readerBuf.toString());
						 * map.put("notReader", notReaderBuf.toString());
						 */
						mapList.add(map);
					}
				}
			}
			Map<String, Object> sumMap = new HashMap<String, Object>();
			sumMap.put("department", "合计");
			sumMap.put("reader", readerSum);
			sumMap.put("notReader", notReaderSum);
			mapList.add(sumMap);
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("aaData", mapList);
			Gson json = new Gson();
			String jsons = json.toJson(jsonMap);
			PrintWriter writer = new PrintWriter(this.getResponse().getWriter());
			writer.print(jsons);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能：修改文件
	 * 
	 * @return
	 */
	public String updateFileContent() {

		try {

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				FileContent fileContent = fileContentImpl.getFileConentById(id);

				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("contentId", fileContent.getContentId());
				request.setAttribute("subject", fileContent.getSubject());
				request.setAttribute("attachment", fileContent.getAttachment());
				request.setAttribute("content", fileContent.getContent());
				request.setAttribute("attachmentName", fileContent
						.getAttachmentName());
				request.setAttribute("keyWord", fileContent.getKeyWord());
				request.setAttribute("contentNo", fileContent.getContentNo());
				request.setAttribute("attachmentDesc", fileContent
						.getAttachmentDesc());

				request.setAttribute("sortId", sortId);
				request.setAttribute("createTime", fileContent.getCreateTime());
				request.setAttribute("createUserId", fileContent
						.getCreateUserId());
				request.setAttribute("filetype", filetype);

				// 日志附件
				String attachIds = fileContent.getAttachment();
				if (attachIds != null && !attachIds.trim().equals("")) {
					attachIds = attachIds.substring(1, attachIds.length() - 1);
					List<Attachment> attachList;
					attachListTemp = new ArrayList<Attachment>();
					
					
					
					
					
					
					
					
					
					
					attachList = attachmentService.getAttachmentsByIds(attachIds);
					for (int j = 0; j < attachList.size(); j++) {
						Attachment ac = attachList.get(j);
						String attachName = ac.getAttachName();
						String attacthSuffix = getAttacthSuffix(attachName);
						attachListTemp.add(ac);
					}

				}
				// request.setAttribute("filecontent", filecontent);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());

		} finally {

		}
		return SUCCESS;
	}

	/**
	 * 功能： 获取附件后缀
	 * 
	 * @return
	 */
	public String getAttacthSuffix(String attacthName) {
		String attacthSuffix = "fileTxt";

		if (attacthName.trim().equals("")) {
			attacthSuffix = "fileTxt";
		} else {
			String[] atts = attacthName.split("\\.");
			if (atts.length > 1) {
				String att = atts[atts.length - 1];
				if (att.endsWith("doc") || att.endsWith("docx")) {
					attacthSuffix = "fileWord";
				} else if (att.endsWith("xls") || att.endsWith("xlsx")) {
					attacthSuffix = "fileExcel";
				} else if (att.endsWith("ppt") || att.endsWith("pptx")) {
					attacthSuffix = "filePPT";
				} else if (att.endsWith("jpg") || att.endsWith("gif")
						|| att.endsWith("png")) {
					attacthSuffix = "filePicture";
				} else if (att.endsWith("rar")) {
					attacthSuffix = "fileRar";
				}
			} else {
				attacthSuffix = "fileTxt";
			}
		}
		return attacthSuffix;
	}

	/**
	 *功能： 得到文件夹信息
	 * 
	 * @return
	 */
	public String getFileContentById() {

		try {

			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				FileContent fileContent = fileContentImpl.getFileConentById(id);
				HttpServletRequest request = ServletActionContext.getRequest();
				request.setAttribute("contentId", fileContent.getContentId());
				request.setAttribute("subject", fileContent.getSubject());
				request.setAttribute("attachment", fileContent.getAttachment());
				request.setAttribute("content", fileContent.getContent());
				request.setAttribute("attachmentName", fileContent
						.getAttachmentName());
				request.setAttribute("keyWord", fileContent.getKeyWord());
				request.setAttribute("contentNo", fileContent.getContentNo());
				request.setAttribute("attachmentDesc", fileContent
						.getAttachmentDesc());
				request.setAttribute("sortId", sortId);
				request.setAttribute("createTime", fileContent.getCreateTime());
				request.setAttribute("createUserId", fileContent
						.getCreateUserId());

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.error(ex.getMessage());

			return null;
		} finally {

		}
		return SUCCESS;
	}

	/**
	 * 
	 * 功能：删除文件
	 * 
	 * @return
	 */
	public String delFile() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			// 得到登录用户
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");

			if (adminUser != null) {
				if (fileIds != null && fileIds.length > 0) {
					for (int i = 0; i < fileIds.length; i++) {
						fileContentImpl.delete((Integer)fileIds[i],false);
					}
				}
				out.print(1);
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
	 * 功能：清空查阅情况
	 * 
	 * @return 清空结果
	 */
	public String clearFileCheck() {
		String resultStr = "清空失败!";
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(this.getResponse().getWriter());
			resultStr = "清空成功!";
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
		Gson json = new Gson();
		String jsons = json.toJson(resultStr);
		writer.print(jsons);
		writer.flush();
		writer.close();
		return null;
	}

	/**
	 * 
	 * 功能：签阅文件
	 * 
	 * @return
	 */
	public String checkFile() {
		PrintWriter out = null;
		try {
			out = this.getResponse().getWriter();
			// 得到登录用户
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				if (fileIds != null && fileIds.length > 0) {
					for (int i = 0; i < fileIds.length; i++) {

					}
				}
				out.print(1);
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
	 * 
	 * 功能：签阅文件
	 * 
	 * @return
	 */
	public String checkFileById() {
		PrintWriter out = null;
		try {

			out = this.getResponse().getWriter();
			// 得到登录用户
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {


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
	 * 
	 * 功能：得到文件夹的权限列表
	 * 
	 * @return
	 */
	public String getAttachmentFile() {
		PrintWriter out = null;
		try {
			{
				out = this.getResponse().getWriter();
				Object userObject = this.getSession().getAttribute("adminUser");

				List<FileContent> list = fileContentImpl
						.getAttachmentFile(sortId);

				/** 封装map */
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					FileContent dp = list.get(i);
					String attach = dp.getAttachment();

					StringBuffer attFile = new StringBuffer();
					StringBuffer attName = new StringBuffer();
					StringBuffer attId = new StringBuffer();
					if (attach == null || "".equals(attach)) {

					} else {

						/** 批量添加附件 */
						String[] attIds = attach.substring(1,
								attach.length() - 1).split(",");
						for (int j = 0; j < attIds.length; j++) {
							String attid = attIds[j];
							Attachment AttachmentT = attachmentService
									.getAttachment(Integer.parseInt(attid));
							attFile.append(AttachmentT.getAttachFile()).append(
									",");
							attName.append(AttachmentT.getAttachName()).append(
									",");
							attId.append(AttachmentT.getId().toString())
									.append(",");
							/*
							 * attFile+=AttachmentT.getAttachFile()+",";
							 * attName+=AttachmentT.getAttachName()+",";
							 * attId+=AttachmentT.getId()+",";
							 */

						}
						map.put("attFile", attFile);
						map.put("attName", attName);
						map.put("attId", attId);
						mapList.add(map);
					}

				}

				Map<String, Object> mapLast = new HashMap<String, Object>();
				mapLast.put("mapList", mapList);
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
	 * 
	 * 功能：得到文件夹的权限列表
	 * 
	 * @return
	 */
	public String getContentById() {
		PrintWriter out = null;
		try {
			{
				out = this.getResponse().getWriter();
				Object userObject = this.getSession().getAttribute("adminUser");

				FileContent content = fileContentImpl.getFileConentById(fileid);

				Map<String, Object> mapLast = new HashMap<String, Object>();
				mapLast.put("content", content.getContent());
				mapLast.put("updateTime", TimeUtil.dateToStr(content
						.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
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
	 * 
	 * 功能：签阅文件
	 * 
	 * @return
	 */
	public String getCheckFilePerson() {

		try {

			HttpServletRequest request = ServletActionContext.getRequest();
			// 得到登录用户
			UserInfo adminUser = (UserInfo) this.getSession().getAttribute(
					"adminUser");
			if (adminUser != null) {
				StringBuffer userNames = new StringBuffer();
				request.setAttribute("userNames", userNames);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {

		}
		return SUCCESS;
	}

	public int getSortId() {
		return sortId;
	}

	public void setSortId(int sortId) {
		this.sortId = sortId;
	}

	public FileContent getFileContent() {
		return fileContent;
	}

	public void setFileContent(FileContent fileContent) {
		this.fileContent = fileContent;
	}

	public Integer[] getFileIds() {
		return fileIds;
	}

	public void setFileIds(Integer[] fileIds) {
		this.fileIds = fileIds;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public IDataPriv getDataPrivImpl() {
		return dataPrivImpl;
	}

	public void setDataPrivImpl(IDataPriv dataPrivImpl) {
		this.dataPrivImpl = dataPrivImpl;
	}

	public String getSMS_SELECT_REMIND_TO_ID() {
		return SMS_SELECT_REMIND_TO_ID;
	}

	public void setSMS_SELECT_REMIND_TO_ID(String sMSSELECTREMINDTOID) {
		SMS_SELECT_REMIND_TO_ID = sMSSELECTREMINDTOID;
	}

	public String getTixing() {
		return tixing;
	}

	public void setTixing(String tixing) {
		this.tixing = tixing;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getFileid() {
		return fileid;
	}

	public void setFileid(int fileid) {
		this.fileid = fileid;
	}

	/**
	 * 功能：去掉重复数据
	 * 
	 * @param oldList
	 *            原始数据List
	 * @return 去掉重复数据的List
	 */
	public List<Integer> filterDuplicate(List<Integer> oldList) {
		Set set = new HashSet();
		List<Integer> newList = new ArrayList<Integer>();
		for (Iterator iter = oldList.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			if (set.add(element))
				newList.add(element);
		}
		return newList;
		
	}

	/**
	 * 功能：验证字符串是否为空
	 * 
	 * @param str
	 *            需验证字符串
	 * @return 字符串
	 */
	public String validateIsEmpty(String str) {
		if (str == null || str.equals("")) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 功能：转换时间格式
	 * 
	 * @param time
	 *            需转换时间
	 * @return 转换后的字符串(格式：yyyy-MM-dd)
	 */
	public String getTimeStr(Timestamp time) {
		if (null == time) {
			return "";
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String newTime = sdf.format(time);
		return newTime;
	}

	/**
	 * 功能 :转换时间格式：
	 * 
	 * @param time
	 *            需转换的时间
	 * @param rex
	 *            转换格式
	 * @return 转换后的字符串
	 */
	public String getTimeStr(Timestamp time, String rex) {
		if (null == time) {
			return "";
		}
		DateFormat sdf = new SimpleDateFormat(rex);
		String newTime = sdf.format(time);
		return newTime;
	}

	/**
	 * 功能：根据用户Id(List)查询部门Id(List)
	 * 
	 * @param userIdList
	 *            用户Id列表
	 * @return 部门Id列表
	 */
	public List<Integer> getGroupIdListByUserIdList(List<Integer> userIdList) {
		List<Integer> groupIdList = new ArrayList<Integer>();
		for (int userId : userIdList) {
//			GroupUser groupUser = groupUserService.findOne(userId);
//			if(groupUser != null){
//				GroupInfo groupInfoBean = groupService.findOne(groupUser.getGroupId());
//				if (null != groupInfoBean) {
//					groupIdList.add(groupInfoBean.getGroupId());
//				}
//			}
			UserInfo u = userService.findOne(userId);
			groupIdList.add(u.getGroupId());
		}
		return filterDuplicate(groupIdList);
	}

	/**
	 * 功能：根据用户Id查询用户名称
	 * 
	 * @param userIdStr
	 *            用户id字符串
	 * @return 用户id、用户名称的Map结构
	 */
	public Map<String, Object> getUserNameByUserId(String userIdStr) {
		StringBuffer userNameBuf = new StringBuffer("");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (!StringUtil.isEmpty(userIdStr)) {
			String[] userIdArr = userIdStr.split(",");
			UserInfo currUser = (UserInfo) getSession().getAttribute(
					"adminUser");
			for (String userId : userIdArr) {
				UserInfo userInfoBean = userService.findOne(Integer.parseInt(userId));
				if (null != userInfoBean) {
					userNameBuf.append(userInfoBean.getUserName() + ",");
				}
			}
		}
		resultMap.put("userIdBuf", userIdStr);
		resultMap.put("userNameBuf", userNameBuf);
		return resultMap;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public FileContent getFilecontent() {
		return filecontent;
	}

	public void setFilecontent(FileContent filecontent) {
		this.filecontent = filecontent;
	}

	public List<Attachment> getAttachListTemp() {
		return attachListTemp;
	}

	public void setAttachListTemp(List<Attachment> attachListTemp) {
		this.attachListTemp = attachListTemp;
	}

	public void setStarttimeStr(String starttimeStr) {
		if (!StringUtil.isEmpty(starttimeStr)) {
			setStarttime(DateTimeUtil.stringToTimestamp(starttimeStr,
					CbbConst.TIME_FORMAT_SUBSTR));
		}
		this.starttimeStr = starttimeStr;
	}

	public String getEndtimeStr() {
		return endtimeStr;
	}

	public void setEndtimeStr(String endtimeStr) {
		if (!StringUtil.isEmpty(endtimeStr)) {
			setEndTime(DateTimeUtil.stringToTimestamp(endtimeStr,
					CbbConst.TIME_FORMAT_SUBSTR));
		}
		this.endtimeStr = endtimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		if (!StringUtils.isEmpty(createTimeStr)) {
			setCreateTime(DateTimeUtil.stringToTimestamp(createTimeStr,
					CbbConst.TIME_FORMAT_SUBSTR));
		}
		this.createTimeStr = createTimeStr;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getFileSortId() {
		return fileSortId;
	}

	public void setFileSortId(int fileSortId) {
		this.fileSortId = fileSortId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
