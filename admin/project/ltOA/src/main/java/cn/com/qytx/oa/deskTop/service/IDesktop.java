package cn.com.qytx.oa.deskTop.service;

import java.util.List;
import java.util.Map;

import cn.com.qytx.oa.deskTop.domain.DesktopModule;
import cn.com.qytx.oa.deskTop.domain.DesktopPage;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.ModuleInfo;

/**
 * 功能:
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-16 下午12:00
 * 修改日期: 13-4-16 下午12:00
 * 修改人员: 汤波涛
 * 修改列表:
 */
public interface IDesktop extends BaseService<DesktopPage> {

    /**
     * 查找某个用户的所有的桌面页
     *
     * @param userId 用户Id
     * @return 桌面页集合
     */
    public List<DesktopPage> findAllPages(int userId);

    /**
     * 根据桌面页查询存在于该桌面页的所有应用（Module）
     *
     * @param pageId 桌面页Id
     * @return Module集合
     */
    public List<DesktopModule> findAllModuleByPageId(int pageId);

    /**
     * 获取某个用户所有的DesktopModule
     * @param userId 用户ID
     * @return 该用户的所有DesktopModule
     */
    public List<DesktopModule> findAllModule(int userId);

    /**
     * 保存一个桌面页
     *
     * @param desktopPage 实例
     */
    public void savePage(DesktopPage desktopPage);

    /**
     * 保存一个应用
     *
     * @param desktopModule 应用
     */
    public void saveModule(DesktopModule desktopModule);

    /**
     * 删除一个桌面页
     *
     * @param desktopPageId 桌面页ID
     */
    public void deletePage(int desktopPageId);

    /**
     * 删除一个桌面页
     *
     * @param desktopPage 桌面页
     */
    public void deletePage(DesktopPage desktopPage);

    /**
     * 删除一个应用
     *
     * @param desktopModuleId 应用Id
     */
    public void deleteModule(int desktopModuleId);

    /**
     * 删除一个应用
     *
     * @param desktopModule 实例
     */
    public void deleteModule(DesktopModule desktopModule);

    /**
     * 检查某个用户是否存在默认的桌面页和应用
     *
     * @param userId    用户Id
     * @param companyId 公司ID
     */
    public void saveDefaultAfterChecked(int userId, int companyId,List<ModuleInfo> moduleList);

    /**
     * 对Module重新指定排序号
     * @param desktopModuleId module的Id
     * @param newOrderNo 新的排序号
     */
    public void reorderModule(int desktopModuleId,int newOrderNo);

    /**
     * 对Page重新指定排序号
     * @param desktopPageId page的Id
     * @param newOrderNo 新的排序号
     */
    public void reorderPage(int desktopPageId,int newOrderNo);

    /**
     * 查询某个用户的未删除页面个数
     * @param userId 用户ID
     * @return 页面个数
     */
    public int countOfPage(int userId);

    /**
     * 查询某个页面上的应用菜单个数
     * @param pageId 页面Id
     * @return module的个数
     */
    public int countOfModuleByPageId(int pageId);

    /**
	 * 获取事务处理个数
     * @param userId,Integer remindFlag 
	 */
	public Map<String, Integer> getAffairsCount(Integer userId,Integer remindFlag);

}
