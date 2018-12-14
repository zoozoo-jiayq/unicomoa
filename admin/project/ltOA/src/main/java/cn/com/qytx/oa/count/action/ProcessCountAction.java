package cn.com.qytx.oa.count.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.oa.count.service.ICountService;
import cn.com.qytx.oa.count.vo.ProcessApplyCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyVo;
import cn.com.qytx.oa.count.vo.ProcessFrequencyVo;
import cn.com.qytx.oa.count.vo.ProcessUseVo;
import cn.com.qytx.platform.base.action.BaseActionSupport;

/**
 * 功能: 流程统计 版本: 5.0 开发人员: 潘博 创建日期: 2015-2-9 修改日期: 2015-2-9 修改列表:
 */
public class ProcessCountAction extends BaseActionSupport {

	private Integer top;

	@Resource(name = "countService")
	private ICountService countService;// 统计业务接口

	/**
	 * 流程使用量排名
	 * 
	 * @return
	 */
	public String processUseCount() {
		List<ProcessUseVo> puvList = this.countService
				.getProcessUseCountList(top);
		if (puvList == null) {
			ajax("1");
		} else {
			ajax(puvList, true);
		}
		return null;
	}

	/**
	 * 用户使用流程次数排名
	 * 
	 * @return
	 */
	public String processApplyCount() {
		List<ProcessApplyVo> pavList = this.countService
				.getProcessApplyCountList(top);
		if (pavList == null) {
			ajax("1");
		} else {
			ajax(pavList, true);
		}
		return null;
	}

	/**
	 * 使用频率排名
	 * 
	 * @return
	 */
	public String getProcessFrequencyCount() {
		List<ProcessFrequencyVo> pavList = this.countService
				.getProcessFrequencyCountList(top);
		if (pavList == null) {
			ajax("1");
		} else {
			ajax(pavList, true);
		}
		return null;
	}

	/**
	 * 用户申请总量
	 * 
	 * @return
	 */
	public String getProcessApplyCount() {
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		String beginTime = year + "-01-01 00:00:00.000";
		String endTime = year + "-12-31 23:59:59.999";
		Map<Integer, ProcessApplyCountVo> map = this.countService
				.getProcessApplyCountMap(beginTime, endTime);
		List<ProcessApplyCountVo> lcvList = this
				.getAllYearProcessApplyCountData(map);
		ajax(lcvList, true);
		return null;
	}

	private List<ProcessApplyCountVo> getAllYearProcessApplyCountData(
			Map<Integer, ProcessApplyCountVo> map) {
		String color[] = { "#a5c2d5", "#cbab4f", "#76a871", "#CD4C43",
				"#ECBD31", "#9bb60f", "#058d8d", "#84588d", "#9bb60f",
				"#058d8d", "#84588d", "#b4cfe4" };
		List<ProcessApplyCountVo> lcvList = new ArrayList<ProcessApplyCountVo>(
				12);
		for (int i = 1; i <= 12; i++) {
			if (map.get(i) == null) {
				ProcessApplyCountVo pav = new ProcessApplyCountVo();
				pav.setColor(color[i - 1]);
				pav.setMonth(i);
				pav.setName(this.getMonthStr(i));
				pav.setValue(0);
				lcvList.add(pav);
			} else {
				ProcessApplyCountVo acc = map.get(i);
				acc.setColor(color[i - 1]);
				lcvList.add(acc);
			}
		}
		return lcvList;
	}

	private String getMonthStr(Integer month) {
		String dateName = "";
		switch (month) {
		case 1:
			dateName = "一月";
			break;
		case 2:
			dateName = "二月";
			break;
		case 3:
			dateName = "三月";
			break;
		case 4:
			dateName = "四月";
			break;
		case 5:
			dateName = "五月";
			break;
		case 6:
			dateName = "六月";
			break;
		case 7:
			dateName = "七月";
			break;
		case 8:
			dateName = "八月";
			break;
		case 9:
			dateName = "九月";
			break;
		case 10:
			dateName = "十月";
			break;
		case 11:
			dateName = "十一月";
			break;
		case 12:
			dateName = "十二月";
			break;
		default:
			dateName = "一月";
			break;
		}
		return dateName;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

}
