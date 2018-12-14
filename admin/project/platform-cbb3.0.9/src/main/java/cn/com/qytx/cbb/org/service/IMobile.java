package cn.com.qytx.cbb.org.service;

import cn.com.qytx.platform.base.PlatformException;

/**
 * <br/>手机端接口
 * <br/>User: 普友
 * <br/>Date: 14-6-26
 * <br/>Time: 上午10:55
 */
public interface IMobile {
    
	/**
     * 获取基础数据
     * @param companyId 单位ID
     * @param userId  操作人员ID
     * @param lastUpdateTime   最后更新时间，如果为全量更新，则为空值
     * @param infoType  更新类型 按位操作 第0位 部门数据 第1位 人员数据 第2位模板 第3位公用电话本 第4位推荐内容  第5位群组人员对应
     * @param isOrg 是否需要群组 0不需要,1需要
     * @return
     */
   public String getBasicInfo (Integer companyId, Integer userId,String lastUpdateTime,Integer infoType ,Integer isOrg) throws PlatformException;

    /**
     * 添加部门/群组
     * @param companyId   单位ID
     * @param userId    操作人员ID
     * @param userName 操作人员名称
     * @param parentId  上级ID
     * @param groupName  部门/群组名称
     * @param groupType   部门类型 1 企业通讯录 2 公共通讯录 3 私人通讯录 4 公共群组 5 个人群组
     * @return
     */
   public String addGroup (Integer companyId,Integer userId,String userName,Integer parentId ,String groupName,Integer groupType)throws PlatformException;

    /**
     * 更新部门/群组
     * @param companyId  单位ID
     * @param userId   操作人员ID
     * @param userName 操作人员名称
     * @param groupId   要更新的部门/群组ID
     * @param parentId  上级ID
     * @param groupName  部门/群组名称
     * @param orderIndex  排序 数字越小越靠前
     * @return
     */
    public String updateGroup (Integer companyId,Integer userId,String userName,Integer groupId, Integer parentId ,String groupName)throws PlatformException;

    /**
     * 删除部门/群组
     * @param companyId  单位ID
     * @param userId     操作人员ID
     * @param userName  操作人员名称
     * @param groupId    要删除的部门/群组ID
     * @return
     */
    public String deleteGroup (Integer companyId,Integer userId,String userName,Integer groupId)throws PlatformException;

    /**
     * 部门排序
     * @param companyId  单位ID
     * @param userId    操作人员ID
     * @param userName  操作人员名称
     * @param sortList  排序列表 GroupOrder实体类的 json格式
     * @return
     */
    public String groupOrder (Integer companyId,Integer userId,String userName,String sortList)throws PlatformException;

    /**
     * 单位ID
     * @param companyId 单位ID
     * @param userId     操作人ID
     * @param userName 操作人姓名
     * @param userJson  人员Json UserInfo实体类的 json格式
     * @return
     */
    public String addUsersInfo (Integer companyId,Integer userId,String userName,String userJson)throws PlatformException;

    /**
     * 修改人员
     * @param companyId  单位ID
     * @param userId     操作人ID
     * @param userName   操作人姓名
     * @param userJson  人员Json UserInfo实体类的 json格式
     * @return
     */
    public String updateUsersInfo (Integer companyId,Integer userId,String userName,String userJson)throws Exception;

    /**
     * 删除人员
     * @param companyId  单位ID
     * @param userId    操作人ID
     * @param userName  操作人姓名
     * @param userIds  人员ID列表，多个人员ID之间,隔开
     * @return
     */
    public String deleteUsersInfo (Integer companyId,Integer userId,String userName,String userIds)throws PlatformException;

    /**
     * 添加群组人员
     * @param companyId  单位ID
     * @param userId    操作人ID
     * @param userName  操作人姓名
     * @param groupId   群组ID
     * @param userIds   人员ID列表，多个人员ID之间,隔开
     * @return
     */
    public String addGroupUsers (Integer companyId,Integer userId,String userName,Integer groupId,String userIds)throws PlatformException;


    /**
     * 群组人员删除
     * @param companyId  单位ID
     * @param userId    操作人ID
     * @param userName  操作人姓名
     * @param groupId  群组ID
     * @param userIds    人员ID列表，多个人员ID之间,隔开
     * @return
     */
    public String deleteGroupUsers (Integer companyId,Integer userId,String userName,Integer groupId ,String userIds)throws PlatformException;

    /**
     * 人员排序
     * @param companyId  单位ID
     * @param userId    操作人ID
     * @param userName  操作人姓名
     * @param sortList   排序列表 UserOrder实体类的 json格式
     * @return
     */
    public String userOrder (Integer companyId,Integer userId,String userName,String sortList);


}
