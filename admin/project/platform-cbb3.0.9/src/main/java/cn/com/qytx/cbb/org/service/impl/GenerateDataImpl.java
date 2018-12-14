package cn.com.qytx.cbb.org.service.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.cache.CacheNameUtil;
import cn.com.qytx.cbb.cache.RedisCache;
import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.cbb.org.action.mobile.OrgInitAction;
import cn.com.qytx.cbb.org.service.GenerateDataService;
import cn.com.qytx.cbb.secret.domain.SecretSettings;
import cn.com.qytx.cbb.secret.sevice.ISecretSettings;
import cn.com.qytx.cbb.util.CalculateUtil;
import cn.com.qytx.cbb.util.FileToZip;
import cn.com.qytx.platform.base.PlatformException;
import cn.com.qytx.platform.org.domain.CompanyInfo;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.GroupUser;
import cn.com.qytx.platform.org.domain.UserGroup;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.org.service.ICompany;
import cn.com.qytx.platform.org.service.IGroup;
import cn.com.qytx.platform.org.service.IGroupUser;
import cn.com.qytx.platform.org.service.IUser;
import cn.com.qytx.platform.org.service.IUserGroup;
import cn.com.qytx.platform.utils.datetime.DateTimeUtil;

import com.google.gson.Gson;

/**
 * <br/>功能: 自动化生成数据文件 
 * <br/>版本: 1.0 
 * <br/>开发人员: zyf 
 * <br/>创建日期: 2015年5月13日 
 * <br/>修改日期: 2015年5月13日 
 * <br/>修改列表:
 */
@Service("generateDataService")
@Transactional
public class GenerateDataImpl implements GenerateDataService {
	protected final static Logger logger = LoggerFactory.getLogger(GenerateDataImpl.class);
	
	@Resource(name = "userService")
	IUser userService;// 人员接口实现类

	@Resource(name = "filePathConfig")
	private FilePathConfig filePathConfig;

	@Resource(name = "groupUserService")
	IGroupUser groupUserService; // 部门人员信息实现类

	@Resource(name = "companyService")
	private ICompany companyService;

	@Resource(name = "groupService")
	private IGroup groupService; // 部门/群组管理接口
	
    @Resource(name="userGroupService")
    private IUserGroup userGroupService;
    
	@Resource
	private ISecretSettings secretSettingsService;
	
	/**
	 * 初始化通讯录
	 * 
	 * @param companyId
	 *            单位ID
	 * @param userId
	 *            登陆人员ID
	 * @param dbFilePath
	 *            数据库文件物理路径
	 * @return
	 */
	public Map<String,Object> initContact(Integer companyId, Integer userId, Integer count,
			String url, String dbFilePath, String dbFileLocalPath)
			throws Exception {
		Map<String,Object> result = null;
		// 得到数据库生成地址，下载地址
		if (companyId == null) {
			throw new PlatformException("单位ID不能为空");
		}
		if (userId == null) {
			throw new PlatformException("操作人员ID不能为空");
		}
		Long num = userService.count(
				"companyId=?1 and isDelete=0 and partitionCompanyId=?2 ",
				companyId, companyId % 10);// 获取单位总人数
		if (num < count) {
			// ret = getInitContact(companyId, userId);
			result = this.getBasicInfo(companyId, userId, "");
		} else {
			result = getInitContactDBFile(companyId, userId, url, dbFilePath);
			if (result == null) {
				result = this.getBasicInfo(companyId, userId, "");
			}
		}
		return result;
	}

	/**
	 * 获取初始化通讯录数据 数据库文件
	 * 
	 * @param companyId
	 * @param userId
	 * @param url
	 *            下载地址
	 * @param dbFilePath
	 *            共享盘地址
	 * @return
	 * @throws Exception
	 */
	private Map<String,Object> getInitContactDBFile(Integer companyId, Integer userId,
			String url, String dbFilePath) throws Exception {
		String file = "/" + companyId + "/"
				+ OrgInitAction.BigData_FileName;
		File f = new File(dbFilePath + file);
		if (f.exists()) {
			url += file;
			Map map = new HashMap();
			map.put("type", 2);
			map.put("result", url);
			return map;
		} else {
			return null;
		}
	}

	/**
	 * add by jiayq，文件路径规则：fileUploadPath+"/"+bigData+"/"+companyId+"/"+userInfo.zip
	 * 根据单位ID生成包含所有单位信息的压缩文件，
	 * 如果单位人员达不到设置的大数据标准，则不生成文件，
	 * 如果单位、人员、信息的更新时间超过一天，且存在zip文件，则不生成新文件
	 * 如果文件为空，则生成文件
	 */
	public void generateFileWithTargetCompanyInfo(int companyId,
			String dbFilePath, String dbFileLocalPath, boolean isNeedClear) {
		// TODO Auto-generated method stub

		// 1,判断公司人员是否达到生成标准
		Long num = userService.count(
				"companyId=?1 and isDelete=0 and partitionCompanyId=?2 ",
				companyId, companyId % 10);// 获取单位总人数
		if (num < filePathConfig.getBigDataStand()) {
			return;
		}

		CompanyInfo company = companyService.findOne(companyId);
		if (company != null) {
			Date cur = new Date(Calendar.getInstance().getTimeInMillis());
			if (!isNeedClear) {// 如果不需要强制清除，则判断是否当日更新过
				// 2,如果单位、人员、信息的更新时间超过一天，且存在zip文件，则不生成新文件
				Timestamp userLastUpdateDate = userService
						.getLastUpdateNew(companyId);
				Timestamp groupLastUpdateDate = groupService
						.getLastUpdateTime(companyId);
				long currentTime = cur.getTime();
				if ((currentTime - userLastUpdateDate.getTime() >= OrgInitAction.ONE_DAY)
						|| (currentTime - groupLastUpdateDate.getTime() >= OrgInitAction.ONE_DAY)) {
					if (isExist(companyId)) {
						return;
					}
				}
			}

			// 3,生成文件
			try {
				// 本地文件路径
				String dbParentLocalPath = dbFileLocalPath + companyId + "/";
				// 共享盘路径
				String dbParent = dbFilePath + companyId + "/";

				// 3.1创建本地文件目录和共享盘文件目录
				// 将本地文件删除
				FileUtils.deleteDirectory(new File(dbParentLocalPath));
				File fileParent = new File(dbParent);
				File fileParentLocal = new File(dbParentLocalPath);
				if (!fileParent.exists() && !fileParent.isDirectory()) {
					fileParent.mkdirs();
				}
				if (!fileParentLocal.exists() && !fileParentLocal.isDirectory()) {
					fileParentLocal.mkdirs();
				}

				// 3.2 创建临时文件 (在本地文件目录)
				String dbLocal = dbParentLocalPath + "/"
						+ UUID.randomUUID().toString();
				System.out.println(dbLocal);
				Boolean isOk = createTable(dbLocal, new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(cur));// 创建表
				if (isOk) {
					// 获取人员列表
					List<UserInfo> userList = userService
							.findAll(
									"companyId=?1 and isDelete=0 and partitionCompanyId=?2 ",
									companyId, companyId % 10);
					if (userList != null && userList.size() > 0) {
						for (UserInfo userInfo : userList) {
							userInfo.setIsLogined(1);
						}
					}
					// 获取部门人员对应关系
					List<GroupUser> groupUserList = groupUserService.findAll(
							"companyId=?1", companyId);
					// 获取部门列表
					List<GroupInfo> groupList = groupService.findAll(
							"companyId=?1 and isDelete=0", companyId);
					// 获取部门下面对应人员数量
					Map<Integer, Integer> groupCountMap = groupService
							.getCompanyGroupCountMap(companyId);
					if (groupList != null && !groupList.isEmpty()) {
						addGroupDataToDB(dbLocal, groupList, userList,
								groupUserList, groupCountMap);
					}
					if (userList != null && !userList.isEmpty()) {
						addUserDataToDB(dbLocal, userList);
					}
					// 3.3 压缩文件到共享盘
					FileToZip.toZip(dbParentLocalPath, dbParent + "/"
							+ OrgInitAction.BigData_FileName);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断单位下的文件是否存在，如果不存在，且记录数大于
	 * 
	 * @param companyId
	 * @return
	 */
	private boolean isExist(int companyId) {
		boolean result = false;

		String fileUploadPath = filePathConfig.getFileUploadPath();
		fileUploadPath += OrgInitAction.BigData_PREX + companyId + "/"
				+ OrgInitAction.BigData_FileName;
		File f = new File(fileUploadPath);
		if (f.exists() && f.isFile()) {
			result = true;
		}
		return result;
	}

	/**
	 * 创建表
	 * 
	 * @param db
	 *            数据库文件
	 * @return
	 */
	private boolean createTable(String db, String updateDate) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite://" + db);
			stmt = c.createStatement();
			// 创建DBUserInfo表
			String sql = "CREATE TABLE user_list "
					+ "(id integer primary key autoincrement ,"
					+ " sex           INT    , " + " userId       INT, "
					+ " phone         VCHAR(20) , "
					+ " groupName    VCHAR(200), " + " groupId      INT, "
					+ " vNum          VCHAR(20), "
					+ " userName     VCHAR(200), "
					+ " telephone    VCHAR(20), "
					+ " telephone2      VCHAR(200), "
					+ " phone2     VCHAR(200), " + " vgroup       VCHAR(200), "
					+ " job           VCHAR(200), "
					+ " title        VCHAR(200), "
					+ " email        VCHAR(200), "
					+ " userPY       VCHAR(200), "
					+ " userNum      VCHAR(200), " + " userPower     INT, "
					+ " signName     VCHAR(200), " + " userState      INT, "
					+ " role      INT, " + " orderIndex       INT, "
					+ " action      INT, " + " photo     VCHAR(200), "
					+ " flg       INT, " + " userType       INT, "
					+ " isSelected       INT, "
					+ " firstName       VCHAR(200), "
					+ " personType      text," + " recordID INT,"
					+ " isVirtual INT," + " isLogined INT," + " linkId INT,"
					+ " fullPy VCHAR(200)," + " formattedNumber VCHAR(200)) ";
			stmt.executeUpdate(sql);
			// 创建DBGroupInfo实体
			sql = "CREATE TABLE group_list "
					+ "(id integer primary key autoincrement,"
					+
					// "(id INT PRIMARY KEY     NOT NULL," +
					" groupName    VCHAR(200), " + " groupId      INT, "
					+ " parentId     INT, " + " type     INT, "
					+ " createUserId     INT, " + " unitType     INT, "
					+ " userIdstr         TEXT, "
					+ " hasecode     VCHAR(200), " + " isChecked     INT, "
					+ " orderIndex     INT, " + " action     INT, "
					+ " userCount     INT, " + " path     VCHAR(200), "
					+ " isAllSelected  INT, " + " createGroupMember text"
					+ ",groupUserNum   INT," + " grade INT)";
			stmt.executeUpdate(sql);
			// 创建数据库更新时间表
			sql = "CREATE TABLE db_update_time "
					+ "(id INT , lastUpdateTime    VCHAR(32)) ";
			stmt.executeUpdate(sql);
			// 添加默认数据
			sql = "insert into  db_update_time(id,lastUpdateTime) values (1,"
					+ "'" + updateDate + "')";
			stmt.executeUpdate(sql);

			// 创建android_metadata表
			sql = "CREATE TABLE android_metadata " + "( locale    VCHAR(32)) ";
			stmt.executeUpdate(sql);

			// android_metadata添加默认数据
			sql = "insert into  android_metadata(locale) values (" + "'zh_CN')";
			stmt.executeUpdate(sql);
			stmt.close();
			stmt = null;
			c.close();
			c = null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 添加部门数据到数据库文件
	 * 
	 * @param db
	 * @param groupList
	 * @return
	 */
	private boolean addGroupDataToDB(String db, List<GroupInfo> groupList,
			List<UserInfo> userList, List<GroupUser> groupUserList,
			Map<Integer, Integer> groupCountMap) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite://" + db);
			conn.setAutoCommit(false);
			int id = 0;
			Map<Integer, String> deptUserMap = getGroupUserMap(groupUserList);// 获取群组人员id
																				// map
			Map<Integer, String> groupUserMap = getGroupUserIdstrMap(groupList,
					userList);// 获取部门人员id map
			// Map<Integer,String> groupPathMap=new HashMap<Integer,
			// String>();//部门路径Map
			// getGroupPaths(groupList,0,groupPathMap);

			//获取群组人员数量
			Map<Integer, Integer> groupUserCountMap = getGroupUserCount(
					groupList, deptUserMap);

			String sql = "insert into group_list(groupName,groupId,parentId,type,createUserId,unitType,userIdstr,hasecode,isChecked,orderIndex,action,userCount,path,isAllSelected,createGroupMember,groupUserNum,grade) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			for (GroupInfo group : groupList) {
				try {
					id++;
					// stmt.setInt(1, id);
					stmt.setString(1, group.getGroupName());
					stmt.setInt(2, group.getGroupId());
					stmt.setInt(3, group.getParentId());
					stmt.setInt(4, group.getGroupType());
					Integer userId = group.getUserId();
					if (userId == null) {
						userId = 0;
					}
					stmt.setInt(5, userId);
					stmt.setInt(6, group.getGroupType());
					String userIdStr = "";
					if (group.getGroupType().intValue() == GroupInfo.DEPT
							.intValue()) {
						userIdStr = groupUserMap.get(group.getGroupId());
					} else {
						userIdStr = deptUserMap.get(group.getGroupId());
					}
					if (userIdStr != null) {
						if (userIdStr.startsWith(",")) {
							userIdStr = userIdStr.replaceFirst(",", "");
						}
						if (userIdStr.endsWith(",")) {
							userIdStr = userIdStr.substring(0,
									userIdStr.length() - 1);
						}
					} else {
						userIdStr = "";
					}
					stmt.setString(7, userIdStr);
					stmt.setString(8, "0");
					stmt.setInt(9, 0);
					stmt.setInt(
							10,
							group.getOrderIndex() == null ? 0 : group
									.getOrderIndex());
					stmt.setInt(11, 1);
					int userCount = 0;
					// 人数
					if (group.getGroupType().intValue() == GroupInfo.DEPT
							.intValue()) {
						userCount = groupCountMap.get(group.getGroupId()) == null ? 0
								: groupCountMap.get(group.getGroupId());
					} else if (groupUserCountMap.get(group.getGroupId()) != null) {
						userCount = groupUserCountMap.get(group.getGroupId());
					}
					stmt.setInt(12, userCount);
					// 部门路径
					// String groupPath=groupPathMap.get(group.getGroupId());
					String groupPath = group.getPath();
					if (groupPath != null) {
						if (groupPath.startsWith(",")) {
							groupPath = groupPath.replaceFirst(",", "");
							;
						}
						if (groupPath.endsWith(",")) {
							groupPath = groupPath.substring(0,
									userIdStr.length() - 1);
						}
					} else {
						groupPath = "";
					}

					stmt.setString(13, groupPath);
					stmt.setString(14, "0");
					stmt.setString(15, userId + "");
					stmt.setInt(16, userCount);
					stmt.setInt(17, group.getGrade()==null?0:group.getGrade());
					stmt.addBatch();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			release(conn, stmt);
		}
		return true;
	}

	/**
	 * 添加人员信息到数据库文件
	 * 
	 * @return
	 */
	private boolean addUserDataToDB(String db, List<UserInfo> userList) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite://" + db);
			conn.setAutoCommit(false);
			int id = 0;
			String sql = "insert into user_list(sex,userId,phone,groupName,groupId,vNum,userName,telephone,telephone2,phone2,vgroup,job,title,"
					+ "email,userPY,userNum,userPower,signName,userState,role,orderIndex,action,photo,flg,userType,isSelected,firstName,personType,recordID,isVirtual,isLogined,linkId,fullPy,formattedNumber) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			for (UserInfo user : userList) {
				try {
					id++;
					// stmt.setInt(1, id);
					stmt.setInt(1, user.getSex() == null ? 0 : user.getSex());
					stmt.setInt(2,
							user.getUserId() == null ? 0 : user.getUserId());
					stmt.setString(3,
							user.getPhone() == null ? "" : user.getPhone());
					stmt.setString(
							4,
							user.getGroupName() == null ? "" : user
									.getGroupName());
					stmt.setInt(5,
							user.getGroupId() == null ? 0 : user.getGroupId());
					stmt.setString(6,
							user.getVNum() == null ? "" : user.getVNum());
					stmt.setString(
							7,
							user.getUserName() == null ? "" : user
									.getUserName());
					stmt.setString(
							8,
							user.getOfficeTel() == null ? "" : user
									.getOfficeTel());
					stmt.setString(9,
							user.getHomeTel() == null ? "" : user.getHomeTel());
					stmt.setString(10,
							user.getPhone2() == null ? "" : user.getPhone2());
					stmt.setString(11,
							user.getVGroup() == null ? "" : user.getVGroup());
					stmt.setString(12,
							user.getJob() == null ? "" : user.getJob());
					stmt.setString(13,
							user.getTitle() == null ? "" : user.getTitle());
					stmt.setString(14,
							user.getEmail() == null ? "" : user.getEmail());
					stmt.setString(15, user.getPy() == null ? "" : user.getPy());
					stmt.setString(16,
							user.getUserNum() == null ? "" : user.getUserNum());
					stmt.setInt(
							17,
							user.getUserPower() == null ? 0 : user
									.getUserPower());
					stmt.setString(
							18,
							user.getSignName() == null ? "" : user
									.getSignName());
					stmt.setInt(19, user.getMobileShowState() == null ? 0
							: user.getMobileShowState());
					stmt.setInt(20, user.getRole() == null ? 0 : user.getRole());
					stmt.setInt(
							21,
							user.getOrderIndex() == null ? 0 : user
									.getOrderIndex());
					stmt.setInt(22, 1);// action
					stmt.setString(23,
							user.getPhoto() == null ? "" : user.getPhoto());
					stmt.setInt(24, 0);// flag
					stmt.setInt(25, 1);// userType
					stmt.setInt(26, 0);// isSelected
					stmt.setString(27, user.getPy() == null ? "" : user.getPy());// firstName
					stmt.setInt(28, 0);// personType
					stmt.setInt(29, 0);// groupUserNum
					Integer isVirtual = user.getIsVirtual();
					if (isVirtual == null) {
						isVirtual = 0;
					}
					stmt.setInt(30, isVirtual);
					Integer linkId = user.getLinkId();
					if (linkId == null) {
						linkId = 0;
					}
					stmt.setInt(
							31,
							user.getIsLogined() == null ? 0 : user
									.getIsLogined());
					stmt.setInt(32, linkId);
					stmt.setString(33,
							user.getFullPy() == null ? "" : user.getFullPy());
					stmt.setString(34, user.getFormattedNumber() == null ? ""
							: user.getFormattedNumber());
					stmt.addBatch();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			stmt.executeBatch();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			release(conn, stmt);
		}
		return true;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param pstmt
	 */
	private void release(Connection conn, PreparedStatement pstmt) {
		try {

			if (pstmt != null) {
				pstmt.close();
			}
			pstmt = null;
			if (conn != null) {
				conn.close();
			}
			conn = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取该部门对应的人员ID
	 * 
	 * @param groupUserList
	 * @return
	 */
	private Map<Integer, String> getGroupUserMap(
			final List<GroupUser> groupUserList) {
		if (groupUserList != null) {
			Map<Integer, String> map = new HashMap<Integer, String>();
			for (GroupUser groupUser : groupUserList) {
				String ids = "";
				if (map.get(groupUser.getGroupId()) != null) {

					ids += map.get(groupUser.getGroupId()) + ","
							+ groupUser.getUserId();
				} else {
					ids = groupUser.getUserId().toString();
				}
				map.put(groupUser.getGroupId(), ids);
			}
			return map;
		}
		return null;
	}

	/**
	 * 功能：获得部门下面所有人员的str,同getCompanyGroupCountMap
	 * 
	 * @param groupList
	 * @param userList
	 * @return
	 */
	private Map<Integer, String> getGroupUserIdstrMap(
			List<GroupInfo> groupList, List<UserInfo> userList) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (int i = 0; i < groupList.size(); i++) {
			GroupInfo g = groupList.get(i);
			if (g.getGroupType().intValue() == GroupInfo.DEPT) {// 只判断部门
				int groupId = g.getGroupId();
				StringBuffer sb = new StringBuffer();
				for (UserInfo userInfo : userList) {// 初始化部门人员对应关系
					if (userInfo.getGroupId().intValue() == groupId) {
						sb.append(userInfo.getUserId() + ",");
					}
				}
				map.put(groupId, sb.toString());
			}
		}

		Map<Integer, String> mapStr = map;
		for (int i = 0; i < groupList.size(); i++) {
			GroupInfo g = groupList.get(i);
			if (g.getGroupType().intValue() == GroupInfo.DEPT) {// 只判断部门
				int groupId = g.getGroupId();
				String path = g.getPath();
				if (StringUtils.isNotEmpty(path)) {
					String[] strs = path.split(",");
					for (String str : strs) {
						if (StringUtils.isNotEmpty(str)) {
							int targetGroupId = Integer.parseInt(str);
							if (targetGroupId != groupId) {
								String oldStr = (mapStr.get(targetGroupId) == null ? ""
										: mapStr.get(targetGroupId).toString());
								String incrStr = (map.get(groupId) == null ? ""
										: map.get(groupId).toString());
								mapStr.put(targetGroupId, oldStr + incrStr);
							}
						}
					}
				}
			}
		}
		Set<Integer> set = new HashSet<Integer>();
		set = mapStr.keySet();
		for (Integer groupId : set) {
			map.put(groupId, mapStr.get(groupId));
		}
		return map;
	}

	/**
	 * 功能：获取群组人员数量
	 * @param groupList
	 * @param groupUserMap
	 * @return
	 */
	private Map<Integer, Integer> getGroupUserCount(List<GroupInfo> groupList,
			Map<Integer, String> groupUserMap) {
		Map<Integer, Integer> groupUserCountMap = new HashMap<Integer, Integer>();
		if (null != groupUserMap && !groupUserMap.isEmpty()) {
			Set<Map.Entry<Integer, String>> set = groupUserMap.entrySet();
			Iterator<Map.Entry<Integer, String>> ite = set.iterator();
			while (ite.hasNext()) {
				Map.Entry<Integer, String> entry = ite.next();

				CalculateUtil t = new CalculateUtil();
				t.getGroupUserCount(entry.getKey(), groupList, groupUserMap);
				groupUserCountMap.put(entry.getKey(), t.getTotal());
			}
		}
		return groupUserCountMap;
	}

	/** 
	 * 变量更新
	 * @param companyId 单位ID
     * @param userId  操作人员ID
     * @param lastUpdateTime   最后更新时间，如果为全量更新，则为空值
     * @param isOrg 是否需要群组 0不需要,1需要
	 */
	@Override
	public Map<String, Object> getBasicInfo(Integer companyId, Integer userId,String lastUpdateTime)throws PlatformException {
        Date time=null;
        if(StringUtils.isNotBlank(lastUpdateTime)){
            time=DateTimeUtil.stringToDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
        }
        
        //获取部门和群组的更新数据
        List<Map<String,Object>> groupMap = getGroupUpdateInfo(companyId, userId, time);
        List<Map<String,Object>> userMap = getUserUpdateInfo(companyId, userId, lastUpdateTime);
        
        Gson gson =new Gson();
        String groupInfo=gson.toJson(groupMap);
        String userInfo =gson.toJson(userMap);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("type", 1);
        map.put("uDate",DateTimeUtil.getCurrentTime());
        map.put("groupInfo",groupInfo);
        map.put("userInfo",userInfo);
        return map;
	}
	
	/**
	 * 获取部门和群组的更新数据
	 * @param companyId
	 * @param userId
	 * @param lastUpdateTime
	 * @return
	 */
	private List<Map<String,Object>> getGroupUpdateInfo(Integer companyId, Integer userId,Date lastUpdateTime){
		List<Map<String,Object>> groupMap=new ArrayList<Map<String, java.lang.Object>>();//部门Map
		List<GroupInfo> groupList= groupService.getGroupListChanged(companyId,userId,lastUpdateTime);
	    if(groupList!=null && groupList.size()>0){
        	//得到部门人员Map
    		Map<Integer,Integer> userNumMap=groupService.getCompanyGroupCountMap(companyId);
            for(GroupInfo group:groupList)
            {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("groupId",group.getGroupId());
                map.put("groupName",group.getGroupName());
                map.put("parentId",group.getParentId());
                map.put("unitType",group.getGroupType());
                map.put("orderIndex",group.getOrderIndex());
                map.put("createUserId",group.getUserId());
                //Integer groupUserNum=groupService.getGroupUserAllNum(companyId, group.getGroupId());
                Integer userNum=userNumMap.get(group.getGroupId());
                if(userNum==null){
                	userNum=0;
                }
                map.put("groupUserNum", userNum.intValue());
                map.put("path", group.getPath());
                map.put("grade", group.getGrade());
                if(group.getIsDelete()==0)
                {
                    map.put("action",1);//增加
                }
                else if(group.getIsDelete()==1)
                {
                    map.put("action",3);//删除
                }

                //如果为群组，获取群组人员ID
                if(group.getGroupType()==4||group.getGroupType()==5)
                {
                  List<Integer> list= groupUserService.getUserIdsBySetId(companyId,group.getGroupId());
                  if(list!=null&&list.size()>0)
                  {
                	  if(list!=null&&list.size()>0)
                      {
                		  map.put("userIds",list);
                      }
                  }
                }
                groupMap.add(map);
            }
        }
	    return groupMap;
	}
	
	/**
	 * 获取人员的更新数据
	 * @param companyId
	 * @param userId
	 * @param lastUpdateTime
	 * @return
	 */
	private List<Map<String,Object>> getUserUpdateInfo(Integer companyId, Integer userId,String lastUpdateTime){
		List<Map<String,Object>> userMap=new ArrayList<Map<String, java.lang.Object>>();
		//获取人员数据
        List<UserInfo> userList = userService.findUsersByLastUpdateTime(companyId,lastUpdateTime);
            
        /**====获得保密设置======**/
    	List<SecretSettings> listSecretSettings = secretSettingsService.getSettingsByUserAndCompany(companyId,userId);
    	
    	List<UserGroup> ugList = userGroupService.findAllCompanyUserGroup();
    	
        if(userList!=null && userList.size()>0){
        	userMap = formatUserDate(userList, null, listSecretSettings, userId);
        }
        return userMap;
	}
	
	/**
     * 获取基础数据
     * @param companyId 单位ID
     * @param userId  操作人员ID
     * @param lastUpdateTime   最后更新时间，如果为全量更新，则为空值
     * @param infoType  更新类型 按位操作 第0位 部门数据 第1位 人员数据 第2位模板 第3位公用电话本 第4位推荐内容  第5位群组人员对应
     * @param isOrg 是否需要群组 0不需要,1需要
     * @return
     */
    public Map<String, Object> getBasicInfoFromCache(Integer companyId, Integer userId, String lastUpdateTime) throws Exception {
       
        List<Map<String,Object>> groupMap=new ArrayList<Map<String, java.lang.Object>>();//部门Map
        List<Map<String,Object>> userMap=new ArrayList<Map<String, java.lang.Object>>();//人员Map
        Date time=null;
        if(StringUtils.isNotBlank(lastUpdateTime))
        {
            time=DateTimeUtil.stringToDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
        }
        boolean fromCache = false;//是否从缓存中读取数据
        RedisCache rc = RedisCache.getInstance();
    	Gson gson = new Gson();
        if (StringUtils.isNotBlank(lastUpdateTime)) {
        	//判断上次更新时间是否在缓存有效期内
        	if (rc.checkCacheEnable()&&rc.checkTimeInCacheTime(time)) {
        		fromCache = true;
			}
		}
        if(fromCache){
	        //封装部门数据
	        groupMap = getGroupUpdateInfo(rc,fromCache,lastUpdateTime,companyId,userId);
	        //封装人员数据
	        userMap = getUserUpdateInfo(rc,fromCache,lastUpdateTime,companyId,userId);
	        String groupInfo=gson.toJson(groupMap);
	        String userInfo =gson.toJson(userMap);
	        Map<String,Object> map=new HashMap<String, Object>();
	        map.put("type", 1);
	        map.put("uDate",DateTimeUtil.getCurrentTime());
	        map.put("groupInfo",groupInfo);
	        map.put("userInfo",userInfo);
	        return map;
        }else{
        	return this.getBasicInfo(companyId, userId, lastUpdateTime);
        }
    }


    /**
     * 得到前天凌晨0点的时间
     */
    private static String getXXDaysAgo(int days){
    	String oneHoursAgoTime = "";
    	try {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_YEAR, days);
            oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd")
                    .format(c.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        return oneHoursAgoTime+" 00:00:00";
    }
    /**
     * 得到n天后的时间
     */
    private static Date getXXDaysAgo(Date startTime,int days){
    	try {
            Calendar c = Calendar.getInstance();
            c.setTime(startTime);
            c.add(Calendar.DAY_OF_YEAR, days);
            return c.getTime();
		} catch (Exception e) {
			return null;
		}
        
    }
    /**
     * 获得从开始时间到今天的所有日期列表
     * @param startTime
     */
    public static List<Date> getTimeList(String startTime){
    	List<Date> list = new ArrayList<Date>();
    	try {
    		Date startDate = null;
    		if(StringUtils.isNotBlank(startTime))
            {
                startTime = startTime.substring(0,10) + " 00:00:00";
                startDate = DateTimeUtil.stringToDate(startTime,"yyyy-MM-dd HH:mm:ss");
                String todayZero = DateTimeUtil.getCurrentTime("yyyy-MM-dd")+" 00:00:00";
                Date todayZeroDate = DateTimeUtil.stringToDate(todayZero, "yyyy-MM-dd HH:mm:ss");
                int i = 1;
                while (startDate.getTime()<=todayZeroDate.getTime()) {
					list.add(startDate);
					startDate = getXXDaysAgo(startDate,i);
				}
            }else {
				startDate = new Date();
				list.add(startDate);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return list;
    }
    /**
     * 获得部门变量更新数据
     * @param rc redis缓存
     * @param fromCache 是否从缓存中取
     * @param lastUpdateTime 上次查询时间
     * @param companyId 单位id
     * @param userId 操作人id
     * @return
     */
    private  List<Map<String,Object>> getGroupUpdateInfo(RedisCache rc,boolean fromCache,String lastUpdateTime,Integer companyId,Integer userId){
        List<Map<String,Object>> map = new ArrayList<Map<String,Object>>();    
    	//判断缓存中是否有数据   上次更新时间大于上线时间  并且 大于等于前天零点的时间
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        if(fromCache){
        	Gson gson = new Gson();
        	logger.info("************************从缓存服务器中取部门数据******************");
        	java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<GroupInfo>>() {}.getType();
        	List<Date> list = getTimeList(lastUpdateTime);
        	if (list!=null&&list.size()>0) {
				for (Date date : list) {
					String cacheName = CacheNameUtil.createCacheName(companyId, "Group", "getGroupListChanged", date);
					String caheDate = rc.getDataFromCache(cacheName);
            		if (StringUtils.isNotBlank(caheDate)) {
            			List<GroupInfo> oneDayAgoDate = gson.fromJson(caheDate, type);
            			groupList.addAll(oneDayAgoDate);
					}
				}
			}
        	if(groupList!=null && groupList.size()>0){
        		//得到部门人员Map
        		Map<Integer,Integer> userNumMap = findGroupUserNum(rc, companyId);
        		//得到群组人员Map
        		Map<Integer, List<Integer>> userIdListMap = findGroupUserList(rc, companyId);
        		//封装部门群组数据格式
        		map = formatGroupDate(groupList,userNumMap,userIdListMap,companyId,userId);
        	}
        }else {
        	logger.info("************************从数据库中取部门数据******************");
        	 Date time=null;
             if(StringUtils.isNotBlank(lastUpdateTime)){
                 time=DateTimeUtil.stringToDate(lastUpdateTime,"yyyy-MM-dd HH:mm:ss");
             }
        	map = this.getGroupUpdateInfo(companyId, userId, time);
		}
    	return map;
    }
    /**
     * 获得公司下的所有的部门人数对应关系
     * @param rc 缓存服务器
     * @param companyId
     * @return
     */
    private Map<Integer,Integer> findGroupUserNum(RedisCache rc,Integer companyId){
    	//得到部门人员Map
    	Map<Integer,Integer> userNumMap = new HashMap<Integer, Integer>();
    	Gson gson = new Gson();
    	java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<Integer, Integer>>() {}.getType();
    	String userNumCacheName = CacheNameUtil.createCacheName(companyId, "Group", "getCompanyGroupCountMap");
    	String userNumStr = rc.getDataFromCache(userNumCacheName);
    	if (userNumStr==null) {//缓存中没有
    		userNumMap=groupService.getCompanyGroupCountMap(companyId);
    		String json = gson.toJson(userNumMap);
    		rc.putDataToCache(userNumCacheName, json);
		}else {//缓存中存在，且正常
			userNumMap = gson.fromJson(userNumStr, type);
		}
    	return userNumMap;
    }
    /**
     * 得到群组人员Map
     * @param rc 缓存服务器
     * @param companyId
     * @return
     */
    private Map<Integer, List<Integer>> findGroupUserList(RedisCache rc,Integer companyId){
    	Map<Integer, List<Integer>> userIdListMap = new HashMap<Integer, List<Integer>>() ;
    	Gson gson = new Gson();
    	java.lang.reflect.Type listtype = new com.google.gson.reflect.TypeToken<Map<Integer, List<Integer>>>() {}.getType();
    	String userIdListCacheName = CacheNameUtil.createCacheName(companyId, "Group", "getGroupUserIDList");
    	String userIdListStr = rc.getDataFromCache(userIdListCacheName);
    	if (userIdListStr==null) {//缓存中没有
    		List<GroupInfo> allGroup = new ArrayList<GroupInfo>();
			List<GroupInfo> publicGroup = groupService.getGroupList(companyId, 4);
			if (publicGroup!=null&&publicGroup.size()>0) {
				allGroup.addAll(publicGroup);
			}
			List<GroupInfo> privateGroup = groupService.getGroupList(companyId, 5);
			if (privateGroup!=null&&privateGroup.size()>0) {
				allGroup.addAll(privateGroup);
			}
			if (allGroup!=null&&allGroup.size()>0) {
				for (GroupInfo groupInfo : allGroup) {
					List<Integer> userIdList = groupUserService.getUserIdsBySetId(companyId,groupInfo.getGroupId());
					userIdListMap.put(groupInfo.getGroupId(), userIdList);
				}
			}
			String json = gson.toJson(userIdListMap);
			rc.putDataToCache(userIdListCacheName, json);
		}else {//缓存中有
			userIdListMap = gson.fromJson(userIdListStr, listtype);
		}
    	return userIdListMap;
    }
    /**
     * 封装部门列表为手机端返回时数据格式
     * @param groupList 要格式化的部门列表
     * @param userNumMap 部门人数map
     * @param userIdListMap 群组人员id map
     * @return List<Map<String,Object>>
     */
    private List<Map<String,Object>> formatGroupDate(List<GroupInfo> groupList,Map<Integer, Integer> userNumMap,Map<Integer, List<Integer>> userIdListMap,Integer companyId,Integer userId){
    	List<Map<String,Object>> groupMap=new ArrayList<Map<String, java.lang.Object>>();
    	if (groupList!=null&&groupList.size()>0) {
    		for(GroupInfo group:groupList)
            {
    			Map<String,Object> map=new HashMap<String, Object>();
                map.put("groupId",group.getGroupId());
                map.put("groupName",group.getGroupName());
                map.put("parentId",group.getParentId());
                map.put("unitType",group.getGroupType());
                map.put("orderIndex",group.getOrderIndex());
                map.put("createUserId",group.getUserId());
                //Integer groupUserNum=groupService.getGroupUserAllNum(companyId, group.getGroupId());
                
                Integer userNum = 0;
                if(userNumMap!=null&&group!=null&&group.getGroupId()!=null){
                	userNum = userNumMap.get(group.getGroupId())==null?0:userNumMap.get(group.getGroupId());
                }
                map.put("groupUserNum", userNum.intValue());
                map.put("path", group.getPath());
                map.put("grade", group.getGrade());
                if(group.getIsDelete()==0)
                {
                    map.put("action",1);//增加
                }
                else if(group.getIsDelete()==1)
                {
                    map.put("action",3);//删除
                }

                //如果为群组，获取群组人员ID
                if(group.getGroupType()==4||group.getGroupType()==5)
                {
                	//去掉公共群组中部包含自己的群组和非自己创建的群组
                	if (group.getGroupType()==5) {//个人群组  只返回用户自己创建的群组
                		if (group.getUserId()!=userId) {
							continue;
						}
					}
                	List<Integer> list= userIdListMap.get(group.getGroupId());
                	if (group.getGroupType()==4) {//公共群组  只返回包含自己的群组
						if (list==null||!list.contains(userId)) {
							continue;
						}
					}
	                  if(list!=null&&list.size()>0)
	                  {
	                	  for(int i=0;i<list.size();i++){
	                		  UserInfo user = userService.findOne(list.get(i));
	                		  if(user==null||user.getIsDelete()==1){
	                			  groupUserService.deleteGroupUserByUserIds(list.get(i)+"",group.getGroupType(),companyId);
	                			  list.remove(i);
	                			  i--;
	                		  }
	                	  }
	                	  if(list!=null&&list.size()>0)
	                      {
	                		  map.put("userIds",list);
	                      }
	                  }
                }
                groupMap.add(map);
            }
		}
    	return groupMap;
    }
    /**
     * 获得人员数据
     */
    private List<Map<String,Object>> getUserUpdateInfo(RedisCache rc,boolean fromCache,String lastUpdateTime,Integer companyId,Integer userId){
    	List<Map<String,Object>> userMap=new ArrayList<Map<String, java.lang.Object>>();
    	Gson gson = new Gson();
        List<UserInfo> userList=new ArrayList<UserInfo>();//人员列表
        if(fromCache){
        	logger.info("************************从缓存服务器中取人员变动数据******************");
        	java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<UserInfo>>() {}.getType();
        	List<Date> list = getTimeList(lastUpdateTime);
        	if (list!=null&&list.size()>0) {
				for (Date date : list) {
					String cacheName = CacheNameUtil.createCacheName(companyId, "User", "findUserByLastUpdateTime", date);
					String caheDate = rc.getDataFromCache(cacheName);
            		if (StringUtils.isNotBlank(caheDate)) {
            			List<UserInfo> oneDayAgoDate = gson.fromJson(caheDate, type);
            			userList.addAll(oneDayAgoDate);
					}
				}
			}
        	//得到管理范围
        	String userPower = null;
        	Map<String,String> userPowMap = findAllUserPower(rc);
        	if (userPowMap!=null) {
        		if (userPowMap.get(companyId+"_"+userId)!=null) {
        			userPower = userPowMap.get(companyId+"_"+userId);
        		}
        	}
        	List<String> ugPowerList = new ArrayList<String>();
        	if(StringUtils.isNotBlank(userPower)){
        		ugPowerList=Arrays.asList(userPower.split(","));
        	}
        	//获得当前用户的保密设置
        	List<SecretSettings> listSecretSettings = findMySecretSettings(rc,userId);
        	//封装人员数据格式为手机端返回的数据格式
        	userMap = formatUserDate(userList,ugPowerList,listSecretSettings,userId);
        }else {
        	logger.info("************************从数据库中取人员变动数据******************");
        	userMap = this.getUserUpdateInfo(companyId, userId, lastUpdateTime);
		}
        return userMap;
    }
    /**
     * 获得所有的管理范围数据
     */
    private Map<String,String> findAllUserPower(RedisCache rc){
    	Map<String,String> userPowMap = new HashMap<String, String>();
    	Gson gson = new Gson();
    	java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {}.getType();
    	String userPowCacheName = CacheNameUtil.createCacheName(-1, "UserGroup", "findAll");
    	String userPowStr = rc.getDataFromCache(userPowCacheName);
    	if (userPowStr==null) {//缓存中没有
    		logger.info("************************从数据库中取管理范围数据******************");
    		List<UserGroup> ugList = userGroupService.findAllCompanyUserGroup();
    		if (ugList!=null&&ugList.size()>0) {
				for (UserGroup userGroup : ugList) {
					String key = userGroup.getCompanyId()+"_"+(userGroup.getUser()==null?-1:userGroup.getUser().getUserId());
					String value = userGroup.getGroupPower();
					userPowMap.put(key, value);
				}
				String json = gson.toJson(userPowMap);
				rc.putDataToCache(userPowCacheName, json);
			}else {
				rc.putDataToCache(userPowCacheName, "");
			}
		}else {//缓存中存在，且正常
			logger.info("************************从缓存服务器中取管理范围数据******************");
			userPowMap = gson.fromJson(userPowStr, type);
		}
    	return userPowMap;
    }
    private List<SecretSettings> findMySecretSettings(RedisCache rc,Integer userId){
    	List<SecretSettings> listSecretSettings = new ArrayList<SecretSettings>();
    	Gson gson = new Gson();
    	java.lang.reflect.Type secrettype = new com.google.gson.reflect.TypeToken<List<SecretSettings>>() {}.getType();
    	String secretCacheName = CacheNameUtil.createCacheName(-1, "SecretSettings", "findAll");
    	String secretCacheStr = rc.getDataFromCache(secretCacheName);
    	if (secretCacheStr==null) {//缓存中没有
    		logger.info("************************从数据库中取保密设置数据******************");
    		listSecretSettings = secretSettingsService.findAllCompanySettings();
    		String json = gson.toJson(listSecretSettings);
    		rc.putDataToCache(secretCacheName, json);
		}else {//缓存中存在，且正常
			logger.info("************************从缓存服务器中取保密设置数据******************");
			listSecretSettings = gson.fromJson(secretCacheStr, secrettype);
		}
    	//筛选包含当前用户的保密设置
    	if (listSecretSettings!=null&&listSecretSettings.size()>0) {
			for (int i = 0; i < listSecretSettings.size(); i++) {
				SecretSettings ss = listSecretSettings.get(i);
				if (ss.getInvisibleUserIds().indexOf(","+userId+",")==-1) {
					listSecretSettings.remove(i);
					i--;
				}
			}
		}
    	return listSecretSettings;
    }
    public List<Map<String,Object>> formatUserDate(List<UserInfo> userList,List<String> ugPowerList,List<SecretSettings> listSecretSettings,Integer userId){
    	List<Map<String,Object>> userMap=new ArrayList<Map<String, java.lang.Object>>();
    	if(userList!=null && userList.size()>0)
        {
            for(UserInfo user:userList)
            {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("userId",user.getUserId());
                map.put("phone",user.getPhone());
                //int groupId=getGroupId(user.getUserId());
                map.put("groupId",user.getGroupId());
                map.put("userName",user.getUserName());
                map.put("sex",user.getSex());
                map.put("telephone",user.getOfficeTel());
                map.put("telephone2",user.getHomeTel());
                map.put("phone2",user.getPhone2());
                map.put("job",user.getJob());
                map.put("title",user.getTitle());
                map.put("email",user.getEmail());
                
                map.put("lastLoginTime","");
                if(user.getLastLoginTime()!=null){
                map.put("lastLoginTime",user.getLastLoginTime());
                }
                
                String userPy=user.getPy();
                if(userPy!=null){
                	userPy=userPy.toUpperCase();
                }
                map.put("userPY",userPy);
                map.put("userNum",user.getUserNum());
                map.put("orderIndex",user.getOrderIndex());
                map.put("vgroup",user.getvGroup());
                map.put("vNum",user.getVNum());
                map.put("isVirtual", 0);//是否是虚拟人
                map.put("linkId", 0);
                if(user.getIsVirtual()!=null && user.getIsVirtual().intValue() == 1){
                	map.put("isVirtual", 1);
                	map.put("linkId", user.getLinkId());
                }
                map.put("userState",user.getMobileShowState()); //  //控制手机端该用户是否展示 1,隐藏；0展示，默认0
                if(user.getIsDelete()==0)
                {
                    map.put("action",1);//增加
                }
                else if(user.getIsDelete()==1)
                {
                    map.put("action",3);//删除
                }
                map.put("userPower",user.getUserPower());
                map.put("signName",user.getSignName());
                map.put("role",user.getRole());
                map.put("photo",user.getPhoto());
                map.put("isLogined", user.getIsLogined());
                map.put("fullPy",user.getFullPy()==null?"":user.getFullPy());
                map.put("formattedNumber",user.getFormattedNumber()==null?"":user.getFormattedNumber());
                map.put("property1", "测试字段1");
                map.put("property2", "测试字段2");
                //保密设置
                Boolean hasPower =false;
                if(user.getGroupId()!=null && ugPowerList!=null){
                		hasPower=ugPowerList.contains(user.getGroupId().toString());
                }
                if(listSecretSettings!=null && listSecretSettings.size()>0){
                	if(userId != user.getUserId().intValue() ){//自己的不用保密设置
                    	for(SecretSettings settings: listSecretSettings){
                    		String applyUserIds = settings.getApplyUserIds();
                    		if((applyUserIds.indexOf(","+user.getUserId()+",") >=0 || (user.getLinkId()!=null&&applyUserIds.indexOf(","+user.getLinkId()+",")>=0)) && !hasPower){
                    			String[] arrs= settings.getAttribute().split(",");
                    			for(String att:arrs){
                    				if(att.equals("officeTel")){
                    					 map.put("telephone","");
                    				}else if(att.equals("homeTel")){
                    					 map.put("telephone2","");
                    				}else{
                    					map.put(att, "");
                    				}
                               			   
                    			}
                    		}
                    	}
                	}
                }
                
                userMap.add(map);
            }
        }
    	return userMap;
    }
    public static void main(String[] args) {
    	String lastUpdateTime = "2015-05-04 15:52:00";
    	List<Date> list = getTimeList(lastUpdateTime);
    	for (Date date : list) {
			System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		}
	}
}
