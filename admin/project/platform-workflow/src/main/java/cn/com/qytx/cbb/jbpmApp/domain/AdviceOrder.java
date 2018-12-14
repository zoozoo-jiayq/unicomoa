package cn.com.qytx.cbb.jbpmApp.domain;

import java.util.Comparator;

import cn.com.qytx.cbb.customWidget.vo.JbpmAdvice;

/**
 * 功能：意见对象的排序实现
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 下午1:21:14 
 * 修改日期：下午1:21:14 
 * 修改列表：
 */
public class AdviceOrder implements Comparator<JbpmAdvice> {

	@Override
	public int compare(JbpmAdvice arg0, JbpmAdvice arg1) {
		// TODO Auto-generated method stub
		int index1 = arg0.getOrderIndex();
		int index2 = arg1.getOrderIndex();
		if(index1 == index2){
			return 0;
		}else if(index1>index2){
			return 1;
		}else{
			return -1;
		}
	}

}
