package cn.com.qytx.fileserver.utils;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipUtil {
	/**
	 * 方法描述：把文件或者文件夹压缩为ZIP格式
	 * @param zipFileName 文件路径
	 * @param filePath 要压缩的文件或文件夹路径
	 */
	public static boolean fileZip(String filePath ,String zipFileName) {
				File zipFile = new File(zipFileName);
		 		File resource = new File(filePath);  
		         if (!resource.exists()){
		        	 return false;
		         }
		         Project prj = new Project();  
		         Zip zip = new Zip();  
		         zip.setProject(prj);  
		         zip.setDestFile(zipFile);
		         FileSet fileSet = new FileSet();  
		         fileSet.setProject(prj);  
		         if(resource.isDirectory()){
		        	 fileSet.setDir(resource);  
		         }else{
		        	 fileSet.setFile(resource);
		         }
		         //fileSet.setIncludes("**/*.java"); 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");  
		         //fileSet.setExcludes(...); 排除哪些文件或文件夹  
		         zip.addFileset(fileSet);  
		         
		         zip.execute();
		         resource.delete();
		         return true;

	}

}
