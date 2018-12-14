package cn.com.qytx.oa.count.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.count.vo.AttendanceCountVo;
import cn.com.qytx.oa.count.vo.LoginCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyCountVo;
import cn.com.qytx.oa.count.vo.ProcessApplyVo;
import cn.com.qytx.oa.count.vo.ProcessFrequencyVo;
import cn.com.qytx.oa.count.vo.ProcessUseVo;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
* 功能: 统计接口
* 版本: 5.0
* 开发人员: 潘博
* 创建日期:  2015-2-9
* 修改日期:  2015-2-9
* 修改列表:
*/
@Repository("countDao")
public class CountDao extends BaseDao<UserInfo, Integer> implements Serializable{

	private static String color[] = {"#a5c2d5","#cbab4f","#76a871","#CD4C43","#ECBD31","#9bb60f","#058d8d","#84588d","#9bb60f","#058d8d","#84588d","#b4cfe4"};
	/**
	 * pc或手机端使用频率统计
	 * @param groupName 组名
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param logType 日志类型
	 * @return
	 */
	public Map<Integer,LoginCountVo> getLoginCountList(String beginTime,String endTime,Integer logType){
		//.createSQLQuery("").list();
		String sql = "select MONTH(insert_time) as month,count(vid) as count from log g where 1=1 ";
		if(logType != null){
			sql += " and g.log_type="+logType;
		}
		if(StringUtils.isNotBlank(beginTime)){
			sql += " and insert_time >='"+beginTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and insert_time <='"+endTime+"'";
		}
		sql += " GROUP BY MONTH(insert_time)";
		Map<Integer,LoginCountVo> map = null; 
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list != null && list.size() > 0){
			map = new HashMap<Integer, LoginCountVo>();
			for(Object[] obj:list){
				LoginCountVo lc = new LoginCountVo();
				Integer month = (Integer)obj[0];
				lc.setMonth(month);
				lc.setName(this.getMonthStr(month));
				lc.setValue((Integer)obj[1]);
				map.put(month, lc);
			}
		}
		return map;
	}
	
	
	/**
	 * 考勤使用次数
	 * @param groupName 组名
	 * @param beginTime 开始日期
	 * @param endTime	结束日期
	 * @param logType 日志类型
	 * @return
	 */
	public Map<Integer,AttendanceCountVo> getAttentanceCountMap(String beginTime,String endTime,Integer attSource){
		//.createSQLQuery("").list();
		String sql = "select MONTH(create_time) as month,count(att_id) as attId from tb_attendance where 1=1 ";
		if(attSource != null){
			sql += " and att_Source="+attSource;
		}
		if(StringUtils.isNotBlank(beginTime)){
			sql += " and create_time >='"+beginTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and create_time <='"+endTime+"'";
		}
		sql += " GROUP BY MONTH(create_time)";
		Map<Integer,AttendanceCountVo> map = null; 
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list != null && list.size() > 0){
			map = new HashMap<Integer, AttendanceCountVo>();
			for(Object[] obj:list){
				AttendanceCountVo acc = new AttendanceCountVo();
				Integer month = (Integer)obj[0];
				acc.setMonth(month);
				acc.setName(this.getMonthStr(month));
				acc.setValue((Integer)obj[1]);
				map.put(month, acc);
			}
		}
		return map;
	}
	
	/**
	 * 流程使用次数
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessUseVo> getProcessUseCountList(Integer top){
		if(top == null){
			top = 10;
		}
		String sql = "select top "+top+" process.process_name as processName,count(var.id) as count from tb_cbb_workflow_var var,tb_cbb_process_attribute process  where var.processattributeid=process.process_attribute_id GROUP BY process.process_name  ORDER BY count(var.id) desc;";
		List<ProcessUseVo> puvList = null;
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list != null && list.size() > 0){
			puvList = new ArrayList<ProcessUseVo>();
			for(int i =0;i < list.size();i++){
				Object[] obj = list.get(i);
				ProcessUseVo puv = new ProcessUseVo();
				puv.setName((String)obj[0]);
				puv.setValue((Integer)obj[1]);
				puv.setColor("#a5c2d5");
				puvList.add(puv);
			}
		}
		return puvList;
	}
	
	/**
	 * 流程 用户申请次数前top名
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessApplyVo> getProcessApplyCountList(Integer top){
		if(top == null){
			top = 10;
		}
		String sql = "select top "+top+" var.beforeuser as userName,count(var.beforeuser) as count from tb_cbb_workflow_var var GROUP BY var.beforeuser ORDER BY count(var.beforeuser) desc";
		List<ProcessApplyVo> puvList = null;
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list != null && list.size() > 0){
			puvList = new ArrayList<ProcessApplyVo>();
			for(int i =0;i < list.size();i++){
				Object[] obj = list.get(i);
				ProcessApplyVo pav = new ProcessApplyVo();
				pav.setName((String)obj[0]);
				pav.setValue((Integer)obj[1]);
				pav.setColor(color[i]);
				puvList.add(pav);
			}
		}
		return puvList;
	}
	
	
	/**
	 * 流程 使用频率
	 * @param top 前几名
	 * @return
	 */
	public List<ProcessFrequencyVo> getProcessFrequencyCountList(Integer top){
		if(top == null){
			top = 10;
		}
		String sql = "select top "+top+" process.process_name as processName,count(var.id) as count from tb_cbb_workflow_var var,tb_cbb_process_attribute process  where var.processattributeid=process.process_attribute_id GROUP BY process.process_name  ORDER BY count(var.id) desc;";
		String sql2 = "select count(var.beforeuser) as count from tb_cbb_workflow_var var";
		List<ProcessFrequencyVo> puvList = null;
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		Object objCount = super.entityManager.createNativeQuery(sql2).getSingleResult();
		Float count = null;
		if(list != null && list.size() > 0){
			if(objCount != null){
				count = Float.parseFloat((Integer)objCount+"");
				
			}
			puvList = new ArrayList<ProcessFrequencyVo>();
			for(int i =0;i < list.size();i++){
				Object[] obj = list.get(i);
				ProcessFrequencyVo pfv = new ProcessFrequencyVo();
				pfv.setName((String)obj[0]);
				float totalCount = Float.parseFloat((Integer)obj[1]/count+"");
				pfv.setValue((float)(Math.round(totalCount*100))/100);
				pfv.setColor(color[i]);
				puvList.add(pfv);
			}
		}
		return puvList;
	}
	
	/**
	 * 流程 总量统计
	 * @param top 前几名
	 * @return
	 */
	public Map<Integer,ProcessApplyCountVo> getProcessApplyCountMap(String beginTime,String endTime){
		//.createSQLQuery("").list();
		String sql = "select MONTH(createtime) as month,count(beforeuser) as count from tb_cbb_workflow_var where 1=1 ";
		if(StringUtils.isNotBlank(beginTime)){
			sql += " and createtime >='"+beginTime+"'";
		}
		if(StringUtils.isNotBlank(endTime)){
			sql += " and createtime <='"+endTime+"'";
		}
		sql += " GROUP BY MONTH(createtime)";
		Map<Integer,ProcessApplyCountVo> map = null; 
		List<Object[]> list = super.entityManager.createNativeQuery(sql).getResultList();
		if(list != null && list.size() > 0){
			map = new HashMap<Integer, ProcessApplyCountVo>();
			for(Object[] obj:list){
				ProcessApplyCountVo pac = new ProcessApplyCountVo();
				Integer month = (Integer)obj[0];
				pac.setMonth(month);
				pac.setName(this.getMonthStr(month));
				pac.setValue((Integer)obj[1]);
				map.put(month, pac);
			}
		}
		return map;
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
}
