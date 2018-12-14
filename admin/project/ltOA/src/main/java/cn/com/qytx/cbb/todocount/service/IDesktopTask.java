package cn.com.qytx.cbb.todocount.service;

/**
 * 功能:
 * 版本:1.0
 * 开发人员: 汤波涛
 * 创建日期: 13-4-18 下午7:28
 * 修改日期: 13-4-18 下午7:28
 * 修改人员: 汤波涛
 * 修改列表:
 */
public interface IDesktopTask {

    /**
     * 获取桌面应用的任务数量
     *
     * @return 未完成的任务数量
     */
    public int countOfTask(int userId);

    /**
     * 获取任务链接
     * @return URL地址
     */
    public String getTaskUrl();

    /**
     * 获取模块名，数据库ModuleInfo表中的moduleName字段值
     * @return 模块名
     */
    public String getModuleName();

}
