package cn.com.qytx.oa.deskTop.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.deskTop.domain.DesktopModule;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:个人桌面菜单项Dao类
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-16 上午11:46
 * 修改日期: 13-4-16 上午11:46
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Repository("desktopModuleDao")
public class DesktopModuleDao extends BaseDao<DesktopModule, Integer>{
    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

	public DesktopModule findByIdNotDeleted(int id) {
        String condition = "id=?1 and isDelete=?2";
        return (DesktopModule) super.findOne(condition,id, CbbConst.NOT_DELETE);
    }
	
	 /**
     * 根据桌面页查询存在于该桌面页的所有应用（Module）
     *
     * @param pageId 桌面页Id
     * @return Module集合
     */
    public List<DesktopModule> findAllModuleByPageId(int pageId) {
        String hql = " desktopPageId=?1 and isDelete=?2";
        return this.findAll(hql,new Sort(Direction.ASC, "orderNo"),pageId, CbbConst.NOT_DELETE);
    }
    
    /**
     * 获取某个用户所有的DesktopModule
     *
     * @param userId 用户ID
     * @return 该用户的所有DesktopModule
     */
    public List<DesktopModule> findAllModule(int userId) {
        String hql = " userId=?1 and isDelete=?2 ";
        return this.findAll(hql, userId, CbbConst.NOT_DELETE);
    }
    

    /**
     * 查询某个页面上的应用菜单个数
     *
     * @param pageId 页面Id
     * @return module的个数
     */
    public int countOfModuleByPageId(int pageId) {
        String condition = " desktopPageId=?1 and isDelete=?2 ";
        return this.count(condition,pageId, CbbConst.NOT_DELETE);
    }

}
