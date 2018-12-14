package cn.com.qytx.cbb.customJpdl.service.parseJSON;


/**
 *文件名:
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-3-14上午9:26:55
 *修改人:
 *修改日期:
 *功能描述:连线数据模型
 *版本号:
 */
public class PathObject {
	private String id;
	private String from;
	private String to;
	private BaseTextObject text;
	private String expr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpr() {
		if(this.expr!=null){
			//如果是数字不加单引号，如果不是数字才加单引号
//			int firstIndex = expr.indexOf("@");
//			int endIndex = expr.indexOf("@", firstIndex);
//			String value = expr.substring(firstIndex, endIndex);
//			boolean isNumflag = value.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
//			if(isNumflag){
//				return this.expr.replaceAll("@", "");
//			}else{
			String result = this.expr.replaceAll("@", "'");
			result = this.expr.replaceAll("~", "");
				return result;
//			}
		}
		return "";
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

	
	public BaseTextObject getText() {
		return text;
	}
	public void setText(BaseTextObject text) {
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "起始："+from+";结束:"+to+";连线名称:"+text;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PathObject other = (PathObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
