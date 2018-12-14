package cn.com.qytx.cbb.attendance.vo;


public class PlanVo {
	 private Integer id; //考勤
	 
	 //考勤方案名称
	 private String subject;
	 
	 //考勤组成员
	 private String userIds;
	 
	 //通用上班时间
	 private String commonOn;
	 
	 //通用上午下班时间
	 private String commonAmOff;
	 
	 //通用下午上班时间
	 private String commonPmOn;
	 
	 //通用下班时间
	 private String commonOff;
	 
	 
	 //周一是否需要打卡
	 private Integer monRest;
	 
	 //周一上班时间
	 private String monOn;
	 
	 //周一下班时间
	 private String monOff;
	 
	 //周二是否需要打卡
	 private Integer tuesRest;
	 
	 //周二上班时间
	 private String tuesOn;
	 
	 //周二下班时间
	 private String tuesOff;
	 
	 //周三是否需要打卡
	 private Integer wedRest;
	 
	 //周三上班时间
	 private String wedOn;
	 
	 //周三下班时间
	 private String wedOff;
	 
	 //周四是否需要打卡
	 private Integer thurRest;
	 
	 //周四上班时间
	 private String thurOn;
	 
	 //周四下班时间
	 private String thurOff;
	 
	 //周五是否需要打卡
	 private Integer friRest;
	 
	 //周五上班时间
	 private String friOn;
	 
	 //周五下班时间
	 private String friOff;
	 
	 //周六是否需要打卡
	 private Integer satRest;
	 
	 //周六上班时间
	 private String satOn;
	 
	 //周六下班时间
	 private String satOff;
	 
	 //周日是否需要打卡
	 private Integer sunRest;
	 
	 //周日上班时间
	 private String sunOn;
	 
	 //周日下班时间
	 private String sunOff;
	 
	 //地理位置
	 private String location;
	 
	 //经度
	 private String longitude;
	 
	 //纬度
	 private String latitude;
	 
	 //有效范围
	 private Integer range;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getCommonOn() {
		return commonOn;
	}

	public void setCommonOn(String commonOn) {
		this.commonOn = commonOn;
	}

	public String getCommonOff() {
		return commonOff;
	}

	public void setCommonOff(String commonOff) {
		this.commonOff = commonOff;
	}

	public Integer getMonRest() {
		return monRest;
	}

	public void setMonRest(Integer monRest) {
		this.monRest = monRest;
	}

	public String getMonOn() {
		return monOn;
	}

	public void setMonOn(String monOn) {
		this.monOn = monOn;
	}

	public String getMonOff() {
		return monOff;
	}

	public void setMonOff(String monOff) {
		this.monOff = monOff;
	}

	public Integer getTuesRest() {
		return tuesRest;
	}

	public void setTuesRest(Integer tuesRest) {
		this.tuesRest = tuesRest;
	}

	public String getTuesOn() {
		return tuesOn;
	}

	public void setTuesOn(String tuesOn) {
		this.tuesOn = tuesOn;
	}

	public String getTuesOff() {
		return tuesOff;
	}

	public void setTuesOff(String tuesOff) {
		this.tuesOff = tuesOff;
	}

	public Integer getWedRest() {
		return wedRest;
	}

	public void setWedRest(Integer wedRest) {
		this.wedRest = wedRest;
	}

	public String getWedOn() {
		return wedOn;
	}

	public void setWedOn(String wedOn) {
		this.wedOn = wedOn;
	}

	public String getWedOff() {
		return wedOff;
	}

	public void setWedOff(String wedOff) {
		this.wedOff = wedOff;
	}

	public Integer getThurRest() {
		return thurRest;
	}

	public void setThurRest(Integer thurRest) {
		this.thurRest = thurRest;
	}

	public String getThurOn() {
		return thurOn;
	}

	public void setThurOn(String thurOn) {
		this.thurOn = thurOn;
	}

	public String getThurOff() {
		return thurOff;
	}

	public void setThurOff(String thurOff) {
		this.thurOff = thurOff;
	}

	public Integer getFriRest() {
		return friRest;
	}

	public void setFriRest(Integer friRest) {
		this.friRest = friRest;
	}

	public String getFriOn() {
		return friOn;
	}

	public void setFriOn(String friOn) {
		this.friOn = friOn;
	}

	public String getFriOff() {
		return friOff;
	}

	public void setFriOff(String friOff) {
		this.friOff = friOff;
	}

	public Integer getSatRest() {
		return satRest;
	}

	public void setSatRest(Integer satRest) {
		this.satRest = satRest;
	}

	public String getSatOn() {
		return satOn;
	}

	public void setSatOn(String satOn) {
		this.satOn = satOn;
	}

	public String getSatOff() {
		return satOff;
	}

	public void setSatOff(String satOff) {
		this.satOff = satOff;
	}

	public Integer getSunRest() {
		return sunRest;
	}

	public void setSunRest(Integer sunRest) {
		this.sunRest = sunRest;
	}

	public String getSunOn() {
		return sunOn;
	}

	public void setSunOn(String sunOn) {
		this.sunOn = sunOn;
	}

	public String getSunOff() {
		return sunOff;
	}

	public void setSunOff(String sunOff) {
		this.sunOff = sunOff;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public String getCommonAmOff() {
		return commonAmOff;
	}

	public void setCommonAmOff(String commonAmOff) {
		this.commonAmOff = commonAmOff;
	}

	public String getCommonPmOn() {
		return commonPmOn;
	}

	public void setCommonPmOn(String commonPmOn) {
		this.commonPmOn = commonPmOn;
	}
	
	
}
