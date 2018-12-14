package cn.com.qytx.oa.productionSchedule.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.platform.base.domain.BaseDomain;
import cn.com.qytx.platform.base.domain.DeleteState;

@Entity
@Table(name = "tb_production_schedule")
public class ProductionSchedule extends BaseDomain {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "daily_car_assembly")
	private Double dailyCarAssembly;// 每日总装车

	@Column(name = "stop")
	private Double stop;// 停时

	@Column(name = "transport_schedule")
	private Double transportSchedule;// 运输收入较进度计划
	@Column(name = "coal_loading")
	private Double coalLoading;// 煤装车
	@Column(name = "middle")
	private Double middle;// 中时
	@Column(name = "door_to_stand")
	private Double doorToStand;// 门到站
	@Column(name = "large_coal_mine")
	private Double largeCoalMine;// 大矿煤
	@Column(name = "income")
	private Double income;// 收入

	@Column(name = "bothincome")
	private Double Bothincome;// 两端收入

	@Column(name = "small_coal_mine")
	private Double smallCoalMine;// 小矿煤

	@Column(name = "sender")
	private Double sender;// 发送人

	@Column(name = "stand_door")
	private Double standDoor;// 站到门

	@Column(name = "groceries")
	private Double groceries;// 杂货

	@Column(name = "use_of_vehicles")
	private Double useOfVehicles;// 运用车

	@Column(name = "dead_load")
	private Double deadLoad;// 静载重

	@Column(name = "compared_plan")
	private Double comparedPlan;// 装车数较计划

	@Column(name = "door_to_door")
	private Double doorToDoor;// 门到门

	@Column(name = "send_tons")
	private Double sendTons;// 发送吨

	@Column(name = "send_plan")
	private Double sendPlan;// 发送吨较计划

	@Column(name = "unload")
	private Double unload;// 卸车

	@Column(name = "send_person_plan")
	private Double sendPersonPlan;// 发送人较计划
	
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "update_time")
	private Timestamp updateTime;
	@DeleteState
	@Column(name = "is_delete")
	private Integer isDelete = 0;
	@Column(name = "rTime")
	private Timestamp rTime;
	
	@Transient
	private String rTimeStr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDailyCarAssembly() {
		return dailyCarAssembly;
	}

	public void setDailyCarAssembly(Double dailyCarAssembly) {
		this.dailyCarAssembly = dailyCarAssembly;
	}

	public Double getStop() {
		return stop;
	}

	public void setStop(Double stop) {
		this.stop = stop;
	}

	public Double getTransportSchedule() {
		return transportSchedule;
	}

	public void setTransportSchedule(Double transportSchedule) {
		this.transportSchedule = transportSchedule;
	}

	public Double getCoalLoading() {
		return coalLoading;
	}

	public void setCoalLoading(Double coalLoading) {
		this.coalLoading = coalLoading;
	}

	public Double getMiddle() {
		return middle;
	}

	public void setMiddle(Double middle) {
		this.middle = middle;
	}

	public Double getDoorToStand() {
		return doorToStand;
	}

	public void setDoorToStand(Double doorToStand) {
		this.doorToStand = doorToStand;
	}

	public Double getLargeCoalMine() {
		return largeCoalMine;
	}

	public void setLargeCoalMine(Double largeCoalMine) {
		this.largeCoalMine = largeCoalMine;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getBothincome() {
		return Bothincome;
	}

	public void setBothincome(Double bothincome) {
		Bothincome = bothincome;
	}

	public Double getSmallCoalMine() {
		return smallCoalMine;
	}

	public void setSmallCoalMine(Double smallCoalMine) {
		this.smallCoalMine = smallCoalMine;
	}

	public Double getSender() {
		return sender;
	}

	public void setSender(Double sender) {
		this.sender = sender;
	}


	public Double getStandDoor() {
		return standDoor;
	}

	public void setStandDoor(Double standDoor) {
		this.standDoor = standDoor;
	}

	public Double getGroceries() {
		return groceries;
	}

	public void setGroceries(Double groceries) {
		this.groceries = groceries;
	}

	public Double getUseOfVehicles() {
		return useOfVehicles;
	}

	public void setUseOfVehicles(Double useOfVehicles) {
		this.useOfVehicles = useOfVehicles;
	}

	public Double getDeadLoad() {
		return deadLoad;
	}

	public void setDeadLoad(Double deadLoad) {
		this.deadLoad = deadLoad;
	}

	public Double getComparedPlan() {
		return comparedPlan;
	}

	public void setComparedPlan(Double comparedPlan) {
		this.comparedPlan = comparedPlan;
	}

	public Double getDoorToDoor() {
		return doorToDoor;
	}

	public void setDoorToDoor(Double doorToDoor) {
		this.doorToDoor = doorToDoor;
	}

	public Double getSendTons() {
		return sendTons;
	}

	public void setSendTons(Double sendTons) {
		this.sendTons = sendTons;
	}

	public Double getSendPlan() {
		return sendPlan;
	}

	public void setSendPlan(Double sendPlan) {
		this.sendPlan = sendPlan;
	}

	public Double getUnload() {
		return unload;
	}

	public void setUnload(Double unload) {
		this.unload = unload;
	}

	public Double getSendPersonPlan() {
		return sendPersonPlan;
	}

	public void setSendPersonPlan(Double sendPersonPlan) {
		this.sendPersonPlan = sendPersonPlan;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getrTime() {
		return rTime;
	}

	public void setrTime(Timestamp rTime) {
		this.rTime = rTime;
	}

	public String getrTimeStr() {
		return rTimeStr;
	}

	public void setrTimeStr(String rTimeStr) {
		this.rTimeStr = rTimeStr;
	}

	
	
	
}
