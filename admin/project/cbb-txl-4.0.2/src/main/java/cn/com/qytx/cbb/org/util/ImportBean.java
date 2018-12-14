package cn.com.qytx.cbb.org.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入结果信息bean
 * @author Administrator
 */
public class ImportBean
{
    private int allNum = 0;// 总个数
    private int successNum = 0;// 成功个数
    private int skipNum = 0;// 跳过个数
    private int overrideNum = 0;// 覆盖个数
    private int errorNum = 0;// 失败个数
    private String checkResult = "";// 校验结果
    private List<List<String>> errors = new ArrayList<List<String>>();// 失败记录
    private List<List<String>> skipList = new ArrayList<List<String>>();// 跳过记录
    private List<List<String>> overrideList = new ArrayList<List<String>>();// 覆盖记录
    private String errorString;//错误信息
    private List<List<CellBean>> errorBeans = new ArrayList<List<CellBean>>();// 失败记录,包含单元格样式
    private String errorFileName;//保存错误信息的文件路径
    
    public String getErrorFileName() {
		return errorFileName;
	}

	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	public List<List<CellBean>> getErrorBeans()
    {
        return errorBeans;
    }

    public void setErrorBeans(List<List<CellBean>> errorBeans)
    {
        this.errorBeans = errorBeans;
    }

    public List<List<String>> getErrors()
    {
        return errors;
    }

    public void setErrors(List<List<String>> errors)
    {
        this.errors = errors;
    }

    public List<List<String>> getSkipList()
    {
        return skipList;
    }

    public void setSkipList(List<List<String>> skipList)
    {
        this.skipList = skipList;
    }

    public List<List<String>> getOverrideList()
    {
        return overrideList;
    }

    public void setOverrideList(List<List<String>> overrideList)
    {
        this.overrideList = overrideList;
    }
    
    public String getErrorString()
    {
        return errorString;
    }

    public void setErrorString(String errorString)
    {
        this.errorString = errorString;
    }

    public int getAllNum()
    {
        return allNum;
    }

    public void setAllNum(int allNum)
    {
        this.allNum = allNum;
    }

    public int getSuccessNum()
    {
        return successNum;
    }

    public void setSuccessNum(int successNum)
    {
        this.successNum = successNum;
    }

    public int getSkipNum()
    {
        return skipNum;
    }

    public void setSkipNum(int skipNum)
    {
        this.skipNum = skipNum;
    }

    public int getOverrideNum()
    {
        return overrideNum;
    }

    public void setOverrideNum(int overrideNum)
    {
        this.overrideNum = overrideNum;
    }

    public String getCheckResult()
    {
        return checkResult;
    }

    public void setCheckResult(String checkResult)
    {
        this.checkResult = checkResult;
    }

    public int getErrorNum()
    {
        return errorNum;
    }

    public void setErrorNum(int errorNum)
    {
        this.errorNum = errorNum;
    }
}
