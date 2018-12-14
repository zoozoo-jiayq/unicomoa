package cn.com.qytx.cbb.org.util;

import java.util.List;

/**
 *文件名:导入excel工具类
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-2-28上午9:40:48
 *修改人:
 *修改日期:
 *功能描述:
 *版本号:
 */
public class ImportExcelService {

	public  static ImportBean  importExcel(String importFile,DealExcelData dealExcelData,String errFileName,List<String> headList){
		  //读取文件获取所有导入信息
        ImportData importData = new ImportData(importFile);
        List<List<String>> listData = importData.readData();
        return dealExcelData.dealExcel(listData,errFileName,headList);
	}
}
