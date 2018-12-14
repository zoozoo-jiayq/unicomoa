package cn.com.qytx.cbb.org.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 读取excel文件信息
 * @author Administrator
 */
public class ImportData
{
    /**
     * 文件路径
     */
    private String fileName;
    
    /**
     * 文件
     */
    private File file;

    /**
     * excel文件信息
     */
    private List<List<String>> dataList;
    
    /**
     * 构造函数
     * @param fileName 文件全路径信息
     */
    public ImportData(String fileName)
    {
        this.fileName = fileName;
    }
    
    /**
     * 构造函数
     * @param file 文件
     */
    public ImportData(File file)
    {
        this.file = file;
    }
    
    /**
     * 读取文件内容信息
     * @return excel文件内容
     * @throws BiffException 
     */
    public List<List<String>> readData()
    {
        Workbook wb = null;
        dataList = new ArrayList<List<String>>();
        try
        {
            // 获取输入流
            InputStream is = null;
            if (null != fileName)
            {
                is = new FileInputStream(fileName);
            }
            else
            {
                is = new FileInputStream(file);
            }
             
            wb = Workbook.getWorkbook(is);
            
            //获取工作簿
            Sheet[] sheets = wb.getSheets(); 
            for (int i = 0; i < sheets.length; i++)
            {
                Sheet sheet = sheets[i];
                
                //读取工作簿内容
                readSheet(dataList, sheet);
            }
        }
        catch(BiffException ex)
        {
            return null;
        }
        catch(IOException ex)
        {
            return null;
        }
        return  dataList;
    }
    
    private void readSheet(List<List<String>> dataList, Sheet sheet)
    {
        List<String> lineData;
        for (int j = 0; j < sheet.getRows(); j++)
        {
            lineData = new ArrayList<String>();
            
            // 读取一行
            Cell[] cells = sheet.getRow(j); 
            if (cells != null && cells.length > 0)
            {
                for (int k = 0; k < cells.length; k++)
                {
                    // 获取单元格内容信息
                    String val = cells[k].getContents();
                    lineData.add(val);
                }
            }
            dataList.add(lineData);
        }
    }
    
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }
}
