package cn.com.qytx.oa.count.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.qytx.oa.count.service.ICountService;
import cn.com.qytx.oa.count.vo.AttendanceCountVo;
import cn.com.qytx.platform.base.action.BaseActionSupport;
/**
* 功能: 手机端和PC端使用系统次数统计
* 版本: 5.0
* 开发人员: 潘博
* 创建日期:  2015-2-9
* 修改日期:  2015-2-9
* 修改列表:
*/
public class AttendanceCountAction extends BaseActionSupport{
	private static final long serialVersionUID = 1L;

	@Resource(name="countService")
	private ICountService countService;//统计业务接口
	
	private Integer attSource;
	
	
	//统计用户使用OA次数
	public String countAttendacne(){
			Calendar c = Calendar.getInstance();
			Integer year = c.get(Calendar.YEAR);
			String beginTime = year+"-01-01 00:00:00.000";
			String endTime = year+"-12-31 23:59:59.999";
			Map<Integer,AttendanceCountVo> map = this.countService.getAttentanceCountMap(beginTime, endTime, attSource);
			List<AttendanceCountVo> lcvList = this.getAllYearAttendanceData(map);
			ajax(lcvList,true);
			return null;
		}

		private List<AttendanceCountVo> getAllYearAttendanceData(Map<Integer,AttendanceCountVo> map){
			String color[] = {"#a5c2d5","#cbab4f","#76a871","#CD4C43","#ECBD31","#9bb60f","#058d8d","#84588d","#9bb60f","#058d8d","#84588d","#b4cfe4"};
			List<AttendanceCountVo> lcvList = new ArrayList<AttendanceCountVo>(12);
			for(int i =1;i <= 12;i++){
				if(map.get(i) == null){
					AttendanceCountVo acc = new AttendanceCountVo();
					acc.setColor(color[i-1]);
					acc.setMonth(i);
					acc.setName(this.getMonthStr(i));
					acc.setValue(0);
					lcvList.add(acc);
				}else{
					AttendanceCountVo acc = map.get(i);
					acc.setColor(color[i-1]);
					lcvList.add(acc);
				}
			}
			return lcvList;
		}
		
		
		private String getMonthStr(Integer month){
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

		public Integer getAttSource() {
			return attSource;
		}

		public void setAttSource(Integer attSource) {
			this.attSource = attSource;
		}
	
}
