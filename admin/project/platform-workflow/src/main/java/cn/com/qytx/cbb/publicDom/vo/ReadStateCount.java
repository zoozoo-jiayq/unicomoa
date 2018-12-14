package cn.com.qytx.cbb.publicDom.vo;


/**
 * 功能：
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 2013-4-24 下午3:02:05 
 * 修改日期：2013-4-24 下午3:02:05 
 * 修改列表：
 */
public class ReadStateCount {

	private int total;
	private int readComplete;
	private int reading;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getReadComplete() {
		return readComplete;
	}
	public void setReadComplete(int readComplete) {
		this.readComplete = readComplete;
	}
	public int getReading() {
		return reading;
	}
	public void setReading(int reading) {
		this.reading = reading;
	}
	
}
