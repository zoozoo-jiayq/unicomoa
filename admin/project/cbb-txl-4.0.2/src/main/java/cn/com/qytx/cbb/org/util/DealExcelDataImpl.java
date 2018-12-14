package cn.com.qytx.cbb.org.util;

import java.util.ArrayList;
import java.util.List;

import jxl.format.Colour;

/**
 *文件名:
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-2-28上午9:46:30
 *修改人:
 *修改日期:
 *功能描述:
 *版本号:
 */
public class DealExcelDataImpl extends DealExcelData {

	/* (non-Javadoc)
	 * @see cn.com.qytx.demo.importData.DealExcelData#dealOneLine(cn.com.qytx.demo.importData.ImportBean, java.util.List)
	 */
	@Override
	public void dealOneLine(ImportBean importBean, List<String> list) {
		  // 初始化校验是否通过
        boolean isCheck = true;
        List<CellBean> cellBeanList = new ArrayList<CellBean>();
        
        // 空行及设置为错误
        if (null != list && !list.isEmpty() && list.size() > 0)
        {
            for (int i = 0; i < list.size(); i++)
            {
                CellBean cellBean = new CellBean();
                String val = list.get(i);
                cellBean.setContent(val);
                
                // 单元格数据校验
                if (0 == i)
                {
                    if (isNum(val))
                    {
                        // 处理数据
                    }
                    else
                    {
                        isCheck = false;
                        
                        // 设置背景色为红色
                        cellBean.setBackgroundColour(Colour.RED);
                    }
                }
                if (5 == i)
                {
                   // 设置背景色为红色
                   cellBean.setBackgroundColour(Colour.RED);       
                }
                cellBeanList.add(cellBean);
            }
        }
        else
        {
            isCheck = false;
            CellBean cellBean = new CellBean();
            cellBean.setContent("");

            // 设置背景色为红色
            cellBean.setBackgroundColour(Colour.RED);
            cellBeanList.add(cellBean);
        }
        
        // 校验失败将错误信息保存在错误列表中
        if (!isCheck)
        {
            importBean.getErrorBeans().add(cellBeanList);
        }
		
	}


    
    public static boolean isNum(String str)
    {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }   

}
