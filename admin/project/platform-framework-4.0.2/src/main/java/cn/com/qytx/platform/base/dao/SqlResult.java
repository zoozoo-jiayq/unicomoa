package cn.com.qytx.platform.base.dao;

/**
 * 功能：将sql查询结果转为实体类
 * <br/>
 * 作者： jiayongqiang
 * <br/>
 * 创建时间：2014年8月25日
 * <br/>
 */
public interface SqlResult <Object> {

	public Object transform(Object[] os);
}
