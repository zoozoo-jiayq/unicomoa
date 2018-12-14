package cn.com.qytx.oa.count.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.qytx.oa.count.vo.AttendanceCountVo;
import cn.com.qytx.oa.count.vo.LoginCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyVo;
import cn.com.qytx.oa.count.vo.ProcessFrequencyVo;
import cn.com.qytx.oa.count.vo.ProcessUseVo;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;
/**
* 功能: 统计接口
* 版本: 5.0
* 开发人员: 潘博
* 创建日期:  2015-2-9
* 修改日期:  2015-2-9
* 修改列表:
*/
public interface ICountService extends BaseService<UserInfo> , Serializable{
	/**
	 * pc或手机端使用频率统计
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param logType 日志类型
	 * @return
	 */
	public Map<Integer,LoginCountVo> getLoginCountList(String beginTime,String endTime,Integer logType);
	
	/**
	 * 考勤使用频率统计
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param logType 考勤打卡类型
	 * @return
	 */
	public Map<Integer,AttendanceCountVo> getAttentanceCountMap(String beginTime,String endTime,Integer attSource);
	
	
	/**
	 * 流程使用次数
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessUseVo> getProcessUseCountList(Integer top);
	
	/**
	 * 流程 用户申请次数前top名
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessApplyVo> getProcessApplyCountList(Integer top);
	
	/**
	 * 流程 使用频率
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessFrequencyVo> getProcessFrequencyCountList(Integer top);
	
	
	/**
	 * 流程 总量统计
	 * @param top 前几名
	 * @return
	 */
	public Map<Integer,ProcessApplyCountVo> getProcessApplyCountMap(String beginTime,String endTime);
}
