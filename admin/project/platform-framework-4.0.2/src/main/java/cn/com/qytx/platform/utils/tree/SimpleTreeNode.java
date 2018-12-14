package cn.com.qytx.platform.utils.tree;

/**
 * 简化的树节点类
 */
public class SimpleTreeNode {

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    /**
     * 附带的数据
     */
    private Object obj;
	/** 节点id */
	private String id;
	
	/** 父节点 id 默认值为0*/
	private String pId = "0";
	
	/** 节点名称 */
	private String name;
	
	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPId() {
		return pId;
	}

	public void setPId(String id) {
		pId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public static void main(String[] args) {

	}
}
