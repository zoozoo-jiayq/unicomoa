package cn.com.qytx.cbb.publicDom.service.impl;

import java.util.Comparator;

import cn.com.qytx.cbb.publicDom.service.impl.PublicDomServiceImpl.ReaderName;

public class ComparatorByDate implements Comparator<ReaderName> {

	@Override
    public int compare(ReaderName o1, ReaderName o2) {
	    // TODO Auto-generated method stub
	        return (int)(o1.getReadTime()-o2.getReadTime());
    }
	

}
