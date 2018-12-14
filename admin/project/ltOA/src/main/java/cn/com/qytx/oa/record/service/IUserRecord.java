package cn.com.qytx.oa.record.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.oa.record.domain.UserRecordSearchVo;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupInfo;
import cn.com.qytx.platform.org.domain.UserInfo;
import cn.com.qytx.platform.utils.tree.TreeNode;


/**
 * 功能:人事档案服务接口类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 2013-03-22
 * 修改日期: 2013-03-22
 * 修改人员: 汤波涛
 * 修改列表:初始添加方法
 */
public interface IUserRecord extends BaseService<UserRecord> , Serializable{

    /**
     * 保存一份新的人事档案信息
     *
     * @param userRecord 人事档案实例
     */
    public void save(UserRecord userRecord);
	
    /**
     * 生成档案编号
     * @return 生产的唯一档案编号
     */
    public String generateRecordNo();

    /**
     * 根据组Id查询出所有的人事档案列表
     * @param page 翻页信息
     * @param groupId 组ID
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordByGroupId(Pageable page,int groupId,Integer treeType);
   
    /**
     * 根据高级搜索VO对象查询
     * @param page 翻页信息
     * @param searchVo 高级搜索信息
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordBySearchVo(Pageable page,UserRecordSearchVo searchVo,String groupIds) throws Exception;
    /**
     * 6月18日  李贺  新增
     * 根据高级搜索VO对象查询
     * @param page 翻页信息
     * @param searchVo 高级搜索信息
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordBySearchVo_new(Pageable page,UserRecordSearchVo searchVo,String groupIds) throws Exception;

    /**
     * 根据人事档案ID查询一份人事档案
     * @param id 人事档案ID
     * @return 符合该ID的人事档案
     */
    public UserRecord findById(int id);

    /**
     * 根据用户ID查询一份人事档案
     * @param userId 用户ID
     * @return 符合该ID的人事档案
     */
    public UserRecord findByUserId(int userId);
    
    /**
     * 功能：根据用户ID删除档案信息
     * @param userId
     */
    public void deleteByUserIds(String userIds);
    
  
    public List<TreeNode> userRecordTree(UserInfo userInfo,GroupInfo forkGroup,int treeType,String path);
    
    /**
	 * 导入保存
	 * @param cells
	 * @param loginUser
	 * @return
	 */
	public String importSave(Cell[] cells, UserInfo loginUser,Map<String, Map<String, Integer>> baseDataMap);
	
	 /**
     * 根据姓名查询出所有的人事档案列表
     *
     * @param page    翻页信息
     * @param groupId 组ID
     * @return 查询的结果列表信息
     */
    public Page<UserRecord> findAllUserRecordList(Pageable page,Integer treeType,String userName);
}
