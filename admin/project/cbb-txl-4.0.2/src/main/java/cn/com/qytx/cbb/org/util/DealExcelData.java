package cn.com.qytx.cbb.org.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 文件名:
 * CopyRright(C):北京全亚通信技术有限公司
 * 创建人:贾永强
 * 创建日期:2013-2-28上午9:42:11
 * 修改人:
 * 修改日期:
 * 功能描述:
 * 版本号:
 */
public abstract class DealExcelData
{
    public ImportBean dealExcel(List<List<String>> listData, String errFileName,
            List<String> headList)
    {
        ImportBean importBean = new ImportBean();
        if (null != listData && !listData.isEmpty() && listData.size() > 1)
        {
            for (int i = 1; i < listData.size(); i++)
            {
                // 处理一行记录
                dealOneLine(importBean, listData.get(i));
            }
        }
        else
        {
            importBean.setErrorString("file is empty.");
        }
        try
        {
            writeErrorFile(errFileName, importBean, headList);
        }
        catch (IOException e)
        {
        }
        return importBean;
    }

    public abstract void dealOneLine(ImportBean importBean, List<String> datalist);

    /**
     * 将错误信息写入文件中
     * @param path
     * @param importBean
     * @throws IOException
     */
    private void writeErrorFile(String path, ImportBean importBean, List<String> headList)
            throws IOException
    {
        if (importBean.getErrorBeans() != null && importBean.getErrorBeans().size() > 0)
        {

            String fileName = UUID.randomUUID().toString() + ".xls";
            File errorFile = new File(path + File.separator + fileName);
            boolean result = errorFile.createNewFile();
            if (result)
            {
                OutputStream outputStream = new FileOutputStream(errorFile);
                ExportExcel exportUtil = new ExportExcel(outputStream, headList,
                        importBean.getErrorBeans());
                exportUtil.export();
                importBean.setErrorNum(importBean.getErrorBeans().size());
                importBean.setErrorFileName(fileName);
            }
        }
    }
}
