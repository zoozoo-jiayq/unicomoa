package cn.com.qytx.cbb.autoEntity.service;

import java.io.File;

/**
 * @author jiayongqiang
 * 自动创建实体
 */
public interface AutoCreateEntityService {

	/**
	 * 生成.java文件
	 * @param info
	 * @return
	 */
	public File createEntity(Object info);
	
	/**
	 * 编译文件
	 * @param f
	 * @return
	 */
	public Class compileFile(File f);
	
	
}
