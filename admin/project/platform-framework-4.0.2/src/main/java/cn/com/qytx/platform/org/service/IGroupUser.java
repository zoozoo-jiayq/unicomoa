package cn.com.qytx.platform.org.service;

import java.io.Serializable;
import java.util.List;

import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.GroupUser;

/**
 * 部门/群组操作接口
 * User: 黄普友
 * Date: 13-2-20
 * Time: 下午3:18
 */
public interface IGroupUser  extends BaseService<GroupUser>,Serializable  {

    /**
     * 根据用户ID删除部门/群组人员对应关系
     * @param ids
     */
    public void deleteGroupUserByUserIds(String ids,Integer groupType,Integer companyId);

    /**
     * 根据部门ID，用户ID删除部门/群组人员对应关系
     * @param ids
     */
    public void deleteGroupUserByUserIds(Integer companyId,Integer groupId,String ids);

    /**
     *
     * @Title: getGroupUserByUserId
     * @Description: TODO(根据用户id得到对应)
     * @param @param id
     * @param @return
     * @return GroupUser    返回类型
     */
    public List<GroupUser> getGroupUserByUserId(Integer id);
    
    /**
     * 功能：根据类型获取集合
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public List<GroupUser> findByType(Integer companyId,Integer groupType);
    
    /**
     * 功能：根据群组ID获取人员数
     * 作者：jiayongqiang
     * 参数：
     * 输出：
     */
    public int getUsersCountBySetId(int groupId);

    /**
     * 根据群组ID获取该群组下所有人员ID
     * @param companyId
     * @param groupId
     * @return
     */
    public List<Integer> getUserIdsBySetId(Integer companyId,Integer groupId);

    /**
	 * 根据群组id删除群组人员关系
	 * @param companyId
	 * @param groupId
	 */
	public void deleteBySetId(int companyId,int groupId);
	
	/**
	 * 根据群组ID获取群组下的人员
	 * @param groupId
	 * @return
	 */
	public List<Integer> getUserIdsBySetId(int groupId);
}
