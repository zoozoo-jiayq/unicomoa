package cn.com.qytx.oa.count.service.impl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.count.dao.CountDao;
import cn.com.qytx.oa.count.service.ICountService;
import cn.com.qytx.oa.count.vo.AttendanceCountVo;
import cn.com.qytx.oa.count.vo.LoginCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyVo;
import cn.com.qytx.oa.count.vo.ProcessFrequencyVo;
import cn.com.qytx.oa.count.vo.ProcessUseVo;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
* 功能: 统计接口
* 版本: 5.0
* 开发人员: 潘博
* 创建日期:  2015-2-9
* 修改日期:  2015-2-9
* 修改列表:
*/
@Service("countService")
@Transactional
public class CountServiceImpl extends BaseServiceImpl<UserInfo> implements
ICountService {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "countDao")
	CountDao countDao;
	/**
	 * pc或手机端使用频率统计
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param logType 日志类型
	 * @return
	 */
	@Override
	public Map<Integer,LoginCountVo> getLoginCountList(String beginTime,
			String endTime, Integer logType) {
		
		return this.countDao.getLoginCountList(beginTime, endTime, logType);
	}
	
	/**
	 * 考勤使用频率统计
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param attSource 考勤类型
	 * @return
	 */
	@Override
	public Map<Integer, AttendanceCountVo> getAttentanceCountMap(
			String beginTime, String endTime, Integer attSource) {
		return this.countDao.getAttentanceCountMap(beginTime, endTime, attSource);
	}
	
	/**
	 * 流程使用次数
	 * @param top 前几名
	 * @return
	 */
	@Override
	public List<ProcessUseVo> getProcessUseCountList(Integer top) {
		return this.countDao.getProcessUseCountList(top);
	}
	
	/**
	 * 流程 用户申请次数前top名
	 * @param top 前几名
	 * @return
	 */
	@Override
	public List<ProcessApplyVo> getProcessApplyCountList(Integer top) {
		return this.countDao.getProcessApplyCountList(top);
	}

	/**
	 * 流程 使用频率
	 * @param top 前几名
	 * @return
	 */
	@Override
	public List<ProcessFrequencyVo> getProcessFrequencyCountList(Integer top) {
		return this.countDao.getProcessFrequencyCountList(top);
	}
	/**
	 * 流程 总量统计
	 * @param top 前几名
	 * @return
	 */
	@Override
	public Map<Integer, ProcessApplyCountVo> getProcessApplyCountMap(
			String beginTime, String endTime) {
		return this.countDao.getProcessApplyCountMap(beginTime, endTime);
	}

}
