package cn.com.qytx.cbb.autoCreateEntity;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.com.qytx.cbb.autoEntity.service.AutoCreateEntityService;
import cn.com.qytx.cbb.autoEntity.service.AutoCreateTableService;
import cn.com.qytx.cbb.autoEntity.service.impl.DefaultCreateEntityImpl;
import cn.com.qytx.cbb.autoEntity.service.impl.DefaultCreateTableImpl;

public class Main {

	public static void main(String[] args) {
		//第一步,创建数据库
		AutoCreateTableService tableService = new DefaultCreateTableImpl();
		tableService.createTable(null);
		
		//第二步,创建Java文件
		AutoCreateEntityService entityService = new DefaultCreateEntityImpl();
		File f = entityService.createEntity(null);
		
		//第三部，编译java文件，并加载到JVM中
		Class c = entityService.compileFile(f);
		Method m;
		try {
			m = c.getMethod("hello", null);
			m.invoke(c.newInstance(), null);
			
		//第四步，让Hibernate JPA能够识别该类
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
