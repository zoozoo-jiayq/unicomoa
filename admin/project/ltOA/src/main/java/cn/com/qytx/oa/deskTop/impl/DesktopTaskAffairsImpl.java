package cn.com.qytx.oa.deskTop.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.cbb.affairs.service.IAffairs;
import cn.com.qytx.cbb.todocount.service.IDesktopTask;

/**
 * 功能:
 * 版本:1.0
 * 创建日期: 13-5-10 下午4:18
 * 修改日期: 13-5-10 下午4:18
 * 修改列表:
 */
@Service("desktopTaskAffairs")
@Transactional
public class DesktopTaskAffairsImpl implements IDesktopTask {

	@Autowired
    IAffairs affairsService;

    @Override
    public int countOfTask(int userId) {
        //获取事物的数量
        return affairsService.getNewAffairsNum(userId);
    }

    @Override
    public String getTaskUrl() {
        return null;
    }

    @Override
    public String getModuleName() {
        return "事务提醒";
    }

    public IAffairs getAffairsService() {
        return affairsService;
    }

    public void setAffairsService(IAffairs affairsService) {
        this.affairsService = affairsService;
    }
}
