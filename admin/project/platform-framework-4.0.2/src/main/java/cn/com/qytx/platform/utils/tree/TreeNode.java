package cn.com.qytx.platform.utils.tree;

import java.io.Serializable;
import java.util.Collection;

import com.google.gson.annotations.Expose;

/**
 * 树节点类
 */
public class TreeNode implements Serializable{

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    /**
     * 附带的数据
     */
    @Expose
    private Object obj;
	/** 节点id */
    @Expose
	private String id;
	
	/** 父节点 id 默认值为0*/
	@Expose
    private String pId = "0";
	
	/** 节点名称 */
	@Expose
	private String name;
	
	/** 
	 * 记录 treeNode 节点的 展开 / 折叠 状态 
	 * 初始化节点数据时，如果设定 treeNode.open = true，则会直接展开此节点
	 * 默认值为false
	 */
	private boolean open = false;
	
	/** 
	 * 是否为父节点
	 * 根据 treeNode.children 属性判断，有子节点则设置为 true，否则为 false
	 * 如果设定 treeNode.isParent = true，即使无子节点数据，也会设置为父节点
	 */
	@Expose
	private boolean isParent;

	/**
	 * 节点的 checkBox / radio 的 勾选状态
	 * 建立 treeNode 数据时设置 treeNode.checked = true 可以让节点的输入框默认为勾选状态
	 * 默认值：false
	 */
	private boolean checked = false;
	
	/**
	 * 设置节点是否隐藏 checkbox / radio
	 * 默认值：false
	 */
	private boolean nocheck = false;
	
	/**
	 * 设置节点的 checkbox / radio 是否禁用
	 * 默认值：false
	 */
	private boolean chkDisabled = false;
	
	/**
	 * 强制节点的 checkBox / radio 的 半勾选状态
	 * 强制为半勾选状态后，不再进行自动计算半勾选状态
	 * 设置 treeNode.halfCheck = false 或 null 才能恢复自动计算半勾选状态
	 */
	private boolean halfCheck = false;
	
	/**
	 * 节点自定义图标的 URL 路径
	 * 父节点如果只设置 icon ，会导致展开、折叠时都使用同一个图标
	 * 父节点展开、折叠使用不同的个性化图标需要同时设置 treeNode.iconOpen / treeNode.iconClose 两个属性
	 */
	@Expose
	private String icon;

	/**
	 * 父节点自定义展开时图标的 URL 路径
	 * 此属性只针对父节点有效
	 * 此属性必须与 iconClose 同时使用
	 */
	private String iconOpen;
	
	/**
	 * 父节点自定义折叠时图标的 URL 路径
	 * 此属性只针对父节点有效
	 * 此属性必须与 iconOpen 同时使用
	 */
	private String iconClose;
	
	/**
	 * 节点自定义图标的 className(css样式)
	 * 需要修改 css，增加相应 className 的设置
	 * css 方式简单、方便，并且同时支持父节点展开、折叠状态切换图片
	 * css 建议采用图片分割渲染的方式以减少反复加载图片，并且避免图片闪动
	 */
	private String iconSkin;
	
	/**
	 * 节点链接的目标 URL
	 */
	private String url;
	
	/**
	 * 节点的title
	 */
	@Expose
	private String title;
	
	/**
	 * 设置点击节点后在何处打开 url
	 * 同超链接 target 属性: "_blank", "_self" 或 其他指定窗口名称
	 * 省略此属性，则默认为 "_blank"
	 */
	private String target;
	
	/**
	 * 节点的子节点数据集合
	 */
	private Collection<TreeNode> children;

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

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public boolean isHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Collection<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(Collection<TreeNode> children) {
		this.children = children;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public static void main(String[] args) {

	}
}
