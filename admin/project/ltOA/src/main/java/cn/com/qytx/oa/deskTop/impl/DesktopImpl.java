package cn.com.qytx.oa.deskTop.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.domain.Affairs;
import cn.com.qytx.cbb.affairs.domain.AffairsBody;
import cn.com.qytx.cbb.affairs.service.IAffairs;
import cn.com.qytx.oa.deskTop.dao.DesktopModuleDao;
import cn.com.qytx.oa.deskTop.dao.DesktopPageDao;
import cn.com.qytx.oa.deskTop.domain.DesktopModule;
import cn.com.qytx.oa.deskTop.domain.DesktopPage;
import cn.com.qytx.oa.deskTop.service.IDesktop;
import cn.com.qytx.oa.util.CommonUtils;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.utils.CbbConst;

/**
 * 功能:
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-17 下午1:04
 * 修改日期: 13-4-17 下午1:04
 * 修改人员: 汤波涛
 * 修改列表:
 */
@Service
@Transactional
public class DesktopImpl extends BaseServiceImpl<DesktopPage> implements IDesktop{

    /**
     * 个人桌面页Dao
     */
	@Resource(name="desktopPageDao")
    private DesktopPageDao desktopPageDao;
    /**
     * 个人桌面应用Dao
     */
	@Resource(name="desktopModuleDao")
    private DesktopModuleDao desktopModuleDao;

	@Autowired
    IAffairs affairsService;
    /**
     * 查找某个用户的所有的桌面页
     *
     * @param userId 用户Id
     * @return 桌面页集合
     */
    public List<DesktopPage> findAllPages(int userId) {
        return this.desktopPageDao.findAllPages(userId);
    }


    /**
     * 根据桌面页查询存在于该桌面页的所有应用（Module）
     *
     * @param pageId 桌面页Id
     * @return Module集合
     */
    public List<DesktopModule> findAllModuleByPageId(int pageId) {
        return this.desktopModuleDao.findAllModuleByPageId(pageId);
    }

    /**
     * 获取某个用户所有的DesktopModule
     *
     * @param userId 用户ID
     * @return 该用户的所有DesktopModule
     */
    public List<DesktopModule> findAllModule(int userId) {
        return this.desktopModuleDao.findAllModule(userId);
    }


    /**
     * 保存一个桌面页
     *
     * @param desktopPage 实例
     */
    public void savePage(DesktopPage desktopPage) {
        this.desktopPageDao.saveOrUpdate(desktopPage);
    }


    /**
     * 保存一个应用
     *
     * @param desktopModule 应用
     */
    public void saveModule(DesktopModule desktopModule) {
        this.desktopModuleDao.saveOrUpdate(desktopModule);
    }


    /**
     * 删除一个桌面页
     *
     * @param desktopPageId 桌面页ID
     */
    public void deletePage(int desktopPageId) {
        DesktopPage desktopPage = this.desktopPageDao.findByIdNotDeleted(desktopPageId);
        this.deletePage(desktopPage);
    }

    /**
     * 删除一个桌面页
     *
     * @param desktopPage 桌面页
     */
    public void deletePage(DesktopPage desktopPage) {
        this.desktopPageDao.deletePage(desktopPage);
    }


    /**
     * 删除一个应用
     *
     * @param desktopModuleId 应用Id
     */
    public void deleteModule(int desktopModuleId) {
        DesktopModule desktopModule = this.desktopModuleDao.findByIdNotDeleted(desktopModuleId);
        this.deleteModule(desktopModule);
    }

    /**
     * 删除一个应用
     *
     * @param desktopModule 实例
     */
    public void deleteModule(DesktopModule desktopModule) {
        desktopModule.setIsDelete(CbbConst.DELETED);
        this.desktopModuleDao.saveOrUpdate(desktopModule);
    }


    /**
     * 检查某个用户是否存在默认的桌面页和应用
     *
     * @param userId 用户Id
     */
    public void saveDefaultAfterChecked(int userId, int companyId, List<ModuleInfo> moduleList) {

        int pageCount = this.desktopPageDao.countOfPage(userId);
        if (pageCount == 0) {
            //为该用户的桌面新增默认的Page以及Module
            DesktopPage desktopPageOne = new DesktopPage();
            desktopPageOne.setIsDelete(CbbConst.NOT_DELETE);
            desktopPageOne.setOrderNo(1);
            desktopPageOne.setUserId(userId);
            desktopPageOne.setCompanyId(companyId);
            this.savePage(desktopPageOne);

            DesktopPage desktopPageTwo = null;

            int pageModuleCount = 0;
            for (ModuleInfo moduleInfo : moduleList) {
                if ((moduleInfo.getModuleLevel() == 2 || moduleInfo.getModuleLevel() == 3)
                        && !moduleInfo.getModuleType().equals(2)
                        && CommonUtils.isNotEmpty(moduleInfo.getUrl()) ) {
                    DesktopModule desktopModule = new DesktopModule();
                    desktopModule.setIsDelete(CbbConst.NOT_DELETE);
                    desktopModule.setCompanyId(companyId);
                    desktopModule.setUserId(userId);
                    desktopModule.setModuleInfo(moduleInfo);
                    if (pageModuleCount < 15) {//第一个Page页放15个
                        desktopModule.setDesktopPageId(desktopPageOne.getId());
                        desktopModule.setOrderNo(pageModuleCount);
                    } else if (pageModuleCount < 25) {//第二个Page页放10个
                        if (null == desktopPageTwo){
                            desktopPageTwo = new DesktopPage();
                            desktopPageTwo.setIsDelete(CbbConst.NOT_DELETE);
                            desktopPageTwo.setOrderNo(2);
                            desktopPageTwo.setUserId(userId);
                            desktopPageTwo.setCompanyId(companyId);
                            this.savePage(desktopPageTwo);
                        }
                        desktopModule.setDesktopPageId(desktopPageTwo.getId());
                        desktopModule.setOrderNo(pageModuleCount - 15);                        
                    } else {
                        break;
                    }
                    this.saveModule(desktopModule);
                    pageModuleCount++;
                }
            }
        }
    }

    /**
     * 对Module重新指定排序号
     *
     * @param desktopModuleId module的Id
     * @param newOrderNo      新的排序号
     */
    public void reorderModule(int desktopModuleId, int newOrderNo) {
        DesktopModule desktopModule = this.desktopModuleDao.findByIdNotDeleted(desktopModuleId);
        desktopModule.setOrderNo(newOrderNo);
        this.desktopModuleDao.saveOrUpdate(desktopModule);
    }

    /**
     * 对Page重新指定排序号
     *
     * @param desktopPageId page的Id
     * @param newOrderNo    新的排序号
     */
    public void reorderPage(int desktopPageId, int newOrderNo) {
        DesktopPage desktopPage = this.desktopPageDao.findByIdNotDeleted(desktopPageId);
        desktopPage.setOrderNo(newOrderNo);
        this.desktopPageDao.saveOrUpdate(desktopPage);
    }


    /**
     * 查询某个用户的未删除页面个数
     *
     * @param userId 用户ID
     * @return 页面个数
     */
    public int countOfPage(int userId) {
        return this.desktopPageDao.countOfPage(userId);
    }

    /**
     * 查询某个页面上的应用菜单个数
     *
     * @param pageId 页面Id
     * @return module的个数
     */
    public int countOfModuleByPageId(int pageId) {
        return this.desktopModuleDao.countOfModuleByPageId(pageId);
    }


    /**
	 * 获取事务处理个数
	 */
	@Override
	public Map<String, Integer> getAffairsCount(Integer userId,Integer remindFlag) {
		List<Affairs> list = affairsService.findAllByUserId(userId, remindFlag);
		Map<String, Integer> res = new HashMap<String,Integer>();
		if(list!=null&&list.size()>0){
			for(Affairs aff:list){
				AffairsBody affairsBody = aff.getAffairsBody();
				if(affairsBody!=null){
					String smsType = affairsBody.getSmsType();
					if(res.get(smsType)!=null){
						int count = res.get(smsType);
						count = count+1;
						res.put(smsType, count);
					}else{
						res.put(smsType, 1);
					}
				}
			}
		}
		return res;
	}


}
