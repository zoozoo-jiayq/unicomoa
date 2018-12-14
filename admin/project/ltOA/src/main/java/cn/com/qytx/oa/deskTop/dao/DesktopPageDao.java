package cn.com.qytx.oa.deskTop.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.deskTop.domain.DesktopPage;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.query.Sort.Direction;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:个人桌面页面项
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-16 上午11:46
 * 修改日期: 13-4-16 上午11:46
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Repository("desktopPageDao")
public class DesktopPageDao extends BaseDao<DesktopPage, Integer>{

    /**
	 * 描述含义
	 */
	private static final long serialVersionUID = 1L;

    /**
     * 个人桌面应用Dao
     */
	@Resource(name="desktopModuleDao")
    private DesktopModuleDao desktopModuleDao;
	
	public DesktopPage findByIdNotDeleted(int id) {
        String condition = "id=?1 and isDelete=?2";
        return (DesktopPage) super.findOne(condition, id, CbbConst.NOT_DELETE);
    }
	
    /**
     * 查找某个用户的所有的桌面页
     *
     * @param userId 用户Id
     * @return 桌面页集合
     */
    public List<DesktopPage> findAllPages(int userId) {
        String hql = " userId=?1 and isDelete=?2 ";
        return this.findAll(hql,new Sort(Direction.ASC, "orderNo"), userId, CbbConst.NOT_DELETE);
    }
    
    /**
     * 删除一个桌面页
     *
     * @param desktopPage 桌面页
     */
    /* (non-Javadoc)
     * @see cn.com.qytx.oa.desktop.service.IDesktop#deletePage(cn.com.qytx.oa.desktop.domain.DesktopPage)
     */
    public void deletePage(DesktopPage desktopPage) {
        //先删除属于该页面的所有Module
        String hql = "update DesktopModule set isDelete=? where desktopPageId=? and isDelete=?";
        int count=this.desktopModuleDao.bulkUpdate(hql,CbbConst.DELETED,desktopPage.getId(),CbbConst.NOT_DELETE);
       // System.out.println("删除该桌面的项:"+count);
        desktopPage.setIsDelete(CbbConst.DELETED);
        this.saveOrUpdate(desktopPage);
    }
    
    /**
     * 查询某个用户的未删除页面个数
     *
     * @param userId 用户ID
     * @return 页面个数
     */
    public int countOfPage(int userId) {
        String condition = " userId=?1 and isDelete=?2 ";
        return this.count(condition, userId, CbbConst.NOT_DELETE);
    }

}
