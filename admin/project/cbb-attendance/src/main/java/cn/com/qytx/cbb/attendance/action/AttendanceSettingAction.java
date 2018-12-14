package cn.com.qytx.cbb.attendance.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import cn.com.qytx.cbb.attendance.domain.AttendanceSetting;
import cn.com.qytx.cbb.attendance.domain.CompanyAddress;
import cn.com.qytx.cbb.attendance.service.IAttendanceSetting;
import cn.com.qytx.cbb.attendance.service.ICompanyAddress;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 打卡设置Action
 * 
 * @ClassName: AttendanceSettingAction
 * @author: WANG
 * @Date: 2016年6月2日 下午3:21:52
 * 
 */
public class AttendanceSettingAction extends BaseActionSupport {
	private static final long serialVersionUID = 1L;

	@Resource(name = "companyAddressImpl")
	private ICompanyAddress companyAddressImpl;
	@Resource(name = "attendanceSettingImpl")
	private IAttendanceSetting attendanceSettingImpl;

	private Integer userId;// 当前操作人
	private Integer companyId;// 公司id
	
	private Integer comAddressId;//公司设置id
	
	private Integer attSettingId;//打卡设置id
	
	private BigDecimal lng;// 经度

	private BigDecimal lat;// 经度

	private String address;// 地址

	private String memo;// 备注

	private Integer range;// 允许打卡距离

	private Integer dutyType;// 1一日两次，2一日4次

	private Timestamp amOnDuty;// 上午上班时间

	private Timestamp amOffDuty;// 上午下班班时间

	private Timestamp pmOnDuty;// 下午上班时间

	private Timestamp pmOffDuty;// 下午下班时间

	/**
	 * 打卡信息设置
	 * 
	 * @Title: attendanceSet
	 */
	public void attendanceSet() {
		try {
			if (userId == null) {
				ajax("101||当前操作人不能为空");
				return;
			}
			if (companyId == null) {
				ajax("101||公司id不能为空");
				return;
			}
			if (lng == null || lat == null) {
				ajax("101||公司经纬度不能为空");
				return;
			}
			if (StringUtils.isBlank(address)) {
				ajax("101||公司地址不能为空");
				return;
			}
			if (range == null) {
				ajax("101||允许打卡距离不能为空");
				return;
			}
			if (dutyType == null) {
				ajax("101||打卡类型不能为空");
				return;
			}
			if (amOnDuty==null) {
				ajax("101||打卡时间设置不能为空");
				return;
			}
			if (dutyType == 2) {
				if (amOffDuty==null) {
					ajax("101||打卡时间设置不能为空");
					return;
				}
				if (pmOnDuty==null) {
					ajax("101||打卡时间设置不能为空");
					return;
				}
			}
			if (pmOffDuty==null) {
				ajax("101||打卡时间设置不能为空");
				return;
			}
			AttendanceSetting attendanceSetting = new AttendanceSetting();
			CompanyAddress companyAddress = new CompanyAddress();
			attendanceSetting.setId(attSettingId);
			attendanceSetting.setCreateTime(new Timestamp(System.currentTimeMillis()));
			attendanceSetting.setCreateUserId(userId);
			attendanceSetting.setIsDelete(0);
			attendanceSetting.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			attendanceSetting.setCompanyId(companyId);
			attendanceSetting.setAmOnDuty(amOnDuty);
			attendanceSetting.setAmOffDuty(amOffDuty);
			attendanceSetting.setPmOnDuty(pmOnDuty);
			attendanceSetting.setPmOffDuty(pmOffDuty);
			attendanceSetting.setRange(range);
			attendanceSetting.setDutyType(dutyType);
			companyAddress.setId(comAddressId);
			companyAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
			companyAddress.setCreateUserId(userId);
			companyAddress.setIsDelete(0);
			companyAddress.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
			companyAddress.setCompanyId(companyId);
			companyAddress.setLat(lat);
			companyAddress.setLng(lng);
			companyAddress.setAddress(address);
			companyAddress.setMemo(memo);
			attendanceSettingImpl.saveOrUpdate(attendanceSetting);
			companyAddressImpl.saveOrUpdate(companyAddress);
			ajax("100||操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajax("102||操作异常");
		}
	}

	/**
	 * 获取打卡设置信息
	 * 
	 * @Title: getAttendanceSet
	 */
	public void getAttendanceSet() {
		try {
			if (companyId == null) {
				ajax("101||公司id不能为空");
				return;
			}
			AttendanceSetting attendanceSetting = attendanceSettingImpl.findAttendanceSetting(companyId);
			CompanyAddress companyAddress = companyAddressImpl.findCompanyAddress(companyId);
			Map<String, Object> map = new HashMap<String, Object>();
			if(attendanceSetting==null||companyAddress==null){
				ajax("101||打卡未设置");
				return;
			}
			map.put("attSettingId", attendanceSetting.getId());
			map.put("comAddressId", companyAddress.getId());
			map.put("companyId", companyAddress.getCompanyId());
			map.put("lat", companyAddress.getLat()==null?"":companyAddress.getLat());
			map.put("lng", companyAddress.getLng()==null?"":companyAddress.getLng());
			map.put("address", companyAddress.getAddress()==null?"":companyAddress.getAddress());
			map.put("range", attendanceSetting.getRange()==null?0:attendanceSetting.getRange());
			map.put("dutyType", attendanceSetting.getDutyType()==null?"":attendanceSetting.getDutyType());
			map.put("amOnDuty", attendanceSetting.getAmOnDuty()==null?"":attendanceSetting.getAmOnDuty());
			map.put("amOffDuty", attendanceSetting.getAmOffDuty()==null?"":attendanceSetting.getAmOffDuty());
			map.put("pmOnDuty", attendanceSetting.getPmOnDuty()==null?"":attendanceSetting.getPmOnDuty());
			map.put("pmOffDuty", attendanceSetting.getPmOffDuty()==null?"":attendanceSetting.getPmOffDuty());
			map.put("systemTime", new Timestamp(System.currentTimeMillis()));
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			ajax("100||" + gson.toJson(map));
		} catch (Exception e) {
			e.printStackTrace();
			ajax("102||操作异常");
		}
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public Integer getDutyType() {
		return dutyType;
	}

	public void setDutyType(Integer dutyType) {
		this.dutyType = dutyType;
	}

	public Integer getComAddressId() {
		return comAddressId;
	}

	public void setComAddressId(Integer comAddressId) {
		this.comAddressId = comAddressId;
	}

	public Integer getAttSettingId() {
		return attSettingId;
	}

	public void setAttSettingId(Integer attSettingId) {
		this.attSettingId = attSettingId;
	}

	public Timestamp getAmOnDuty() {
		return amOnDuty;
	}

	public void setAmOnDuty(Timestamp amOnDuty) {
		this.amOnDuty = amOnDuty;
	}

	public Timestamp getAmOffDuty() {
		return amOffDuty;
	}

	public void setAmOffDuty(Timestamp amOffDuty) {
		this.amOffDuty = amOffDuty;
	}

	public Timestamp getPmOnDuty() {
		return pmOnDuty;
	}

	public void setPmOnDuty(Timestamp pmOnDuty) {
		this.pmOnDuty = pmOnDuty;
	}

	public Timestamp getPmOffDuty() {
		return pmOffDuty;
	}

	public void setPmOffDuty(Timestamp pmOffDuty) {
		this.pmOffDuty = pmOffDuty;
	}

}
