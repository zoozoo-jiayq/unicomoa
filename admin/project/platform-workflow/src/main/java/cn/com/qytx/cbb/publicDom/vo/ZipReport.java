package cn.com.qytx.cbb.publicDom.vo;

/**
 * 功能：公文归档统计报表
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:13:29 
 * 修改日期：上午11:13:29 
 * 修改列表：
 */
public class ZipReport {

	//分局
	private String forkGroupName;
	
	//收文待归档
	private int gatherWaitZip = 0 ;
	
	//收文已归档
	private int gatherCompZip = 0 ;
	
	//发文待归档
	private int disWaitZip = 0 ;
	
	//发文已归档
	private int disCompZip = 0 ;
	
	public String getForkGroupName() {
		return forkGroupName;
	}

	public void setForkGroupName(String forkGroupName) {
		this.forkGroupName = forkGroupName;
	}

	public int getGatherWaitZip() {
		return gatherWaitZip;
	}

	public void setGatherWaitZip(int gatherWaitZip) {
		this.gatherWaitZip = gatherWaitZip;
	}

	public int getGatherCompZip() {
		return gatherCompZip;
	}

	public void setGatherCompZip(int gatherCompZip) {
		this.gatherCompZip = gatherCompZip;
	}

	public int getDisWaitZip() {
		return disWaitZip;
	}

	public void setDisWaitZip(int disWaitZip) {
		this.disWaitZip = disWaitZip;
	}

	public int getDisCompZip() {
		return disCompZip;
	}

	public void setDisCompZip(int disCompZip) {
		this.disCompZip = disCompZip;
	}

	public int getGatherCount(){
		return this.gatherCompZip+this.gatherWaitZip;
	}
	
	public int getDisCount(){
		return this.disCompZip+this.disWaitZip;
	}
	
	public int getCount(){
		return this.getGatherCount()+this.getDisCount();
	}
}
