package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.Comparator;

import cn.com.qytx.cbb.publicDom.service.impl.PublicDomServiceImpl.ReaderName;

/**
 * 功能：按照人员级别排序
 * 版本: 1.0
 * 开发人员：贾永强
 * 创建日期: 上午11:57:16 
 * 修改日期：上午11:57:16 
 * 修改列表：
 */
public class ComparatorByUserOrder implements Comparator<ReaderName> {

	@Override
    public int compare(ReaderName arg0, ReaderName arg1) {
	    // TODO Auto-generated method stub
		
	    return arg0.getOrderIndex()-arg1.getOrderIndex();
    }

}
