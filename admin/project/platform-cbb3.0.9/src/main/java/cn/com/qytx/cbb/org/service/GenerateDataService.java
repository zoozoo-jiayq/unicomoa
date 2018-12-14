package cn.com.qytx.cbb.org.service;

import java.util.Map;

import cn.com.qytx.platform.base.PlatformException;

/**
 * <br/>功能: 自动化生成通讯录数据文件 
 * <br/>版本: 1.0
 * <br/>开发人员: zyf
 * <br/>创建日期: 2015年5月13日
 * <br/>修改日期: 2015年5月13日
 * <br/>修改列表:
 */
public interface GenerateDataService {

	
	/**
     * 初始化通讯录
     * @param companyId 单位ID
     * @param userId 登陆人员ID
     * @param count 多大数量时返回数据库文件
     * @param dbFilePath 数据库文件物理路径
     * @param dbFileLocalPath 上传到本地路径
     * @return
     */
    public Map<String,Object> initContact(Integer companyId,Integer userId,Integer count,String url,String dbFilePat, String dbFileLocalPathh)throws Exception;
    
    
    /**
	 * add by jiayq，文件路径规则：fileUploadPath+"/"+bigData+"/"+companyId+"/"+userInfo.zip
	 * 根据单位ID生成包含所有单位信息的压缩文件，
	 * 如果单位人员达不到设置的大数据标准，则不生成文件，
	 * 如果单位、人员、信息的更新时间超过一天，且存在zip文件，则不生成新文件
	 * 如果文件为空，则生成文件
	 */
	public void generateFileWithTargetCompanyInfo(int companyId,String dbFilePath,String dbLocalfilepath,boolean isNeedClear);
	
	 /**
     * 变量更新
     * @param companyId 单位ID
     * @param userId  操作人员ID
     * @param lastUpdateTime   最后更新时间，如果为全量更新，则为空值
     * @return
     */
   public Map<String,Object> getBasicInfo (Integer companyId, Integer userId,String lastUpdateTime) throws PlatformException;

   /**
    * 获取基础数据
    * @param companyId 单位ID
    * @param userId  操作人员ID
    * @param lastUpdateTime   最后更新时间，如果为全量更新，则为空值
    * @param infoType  更新类型 按位操作 第0位 部门数据 第1位 人员数据 第2位模板 第3位公用电话本 第4位推荐内容  第5位群组人员对应
    * @param isOrg 是否需要群组 0不需要,1需要
    * @return
    */
   public Map<String, Object> getBasicInfoFromCache(Integer companyId, Integer userId, String lastUpdateTime) throws Exception ;
}
