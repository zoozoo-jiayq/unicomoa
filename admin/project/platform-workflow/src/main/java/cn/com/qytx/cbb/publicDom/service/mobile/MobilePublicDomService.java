package cn.com.qytx.cbb.publicDom.service.mobile;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.qytx.cbb.publicDom.mobile.vo.AdviceObj;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobilePublicDomView;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobileReadState;
import cn.com.qytx.cbb.publicDom.mobile.vo.MobleViewProcessDetail;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能：手机端服务接口
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-5-6 上午9:20:14 
 * 修改日期：2013-5-6 上午9:20:14 
 * 修改列表：
 */
public interface MobilePublicDomService {
	
	/**
	 * 功能：查询未处理的公文
	 * @param
	 * @return
	 * @throws   
	 */
	public List<MobilePublicDomView> findNotCompletedDom(UserInfo userInfo,String menu,Pageable page);
	
	/**
	 * 功能：查询已处理的公文
	 * @param
	 * @return
	 * @throws   
	 */
	public List<MobilePublicDomView> findCompletedDom(UserInfo user,String menu,String title,Pageable page);
	
	/**
	 * 功能：查询未处理的公文数量
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,Long> findNotCompletedTaskCount(String searchType,UserInfo user,Pageable page);
	
	/**
	 * 功能：查询公文详情
	 * @param
	 * @return
	 * @throws   
	 */
	public MobleViewProcessDetail  findDomDetail(String instanceId,String userId);
	
	/**
	 * 功能：查询阅读状态
	 * @param
	 * @return
	 * @throws   
	 */
	public MobileReadState findReadStateByInstanceId(String instanceId);
	
	/**
	 * 功能：获取指定变量的值
	 * @param
	 * @return
	 * @throws   
	 */
	public Map<String,Object> getVarsMap(String instanceId,Set<String> set);
	
	/**
	 * 功能：根据控件ID和实例ID获取审批记录
	 * @param
	 * @return
	 * @throws   
	 */
	public List<AdviceObj> getAdviceObjlist(String instanceId,String editorId);
	
	/**
	 * 功能：保存公文信息
	 * @param
	 * @return
	 * @throws   
	 */
	public void saveDom(String instanceId,UserInfo userInfo,String formData);
}
