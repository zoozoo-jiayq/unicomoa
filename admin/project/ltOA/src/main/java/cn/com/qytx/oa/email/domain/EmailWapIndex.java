package cn.com.qytx.oa.email.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 功能: 手机电子邮件首页信息,包含未读邮件和邮件文件夹列表
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-8-5
 * 修改日期: 2013-8-5
 * 修改列表:
 */
public class EmailWapIndex implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6400218770658100998L;

	private int unRead;
    
    private List<EmailBox> boxList;

    public List<EmailBox> getBoxList()
    {
        return boxList;
    }

    public void setBoxList(List<EmailBox> boxList)
    {
        this.boxList = boxList;
    }

    public int getUnRead()
    {
        return unRead;
    }

    public void setUnRead(int unRead)
    {
        this.unRead = unRead;
    }
    
}
