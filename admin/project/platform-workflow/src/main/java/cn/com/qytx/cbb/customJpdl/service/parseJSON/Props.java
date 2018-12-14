package cn.com.qytx.cbb.customJpdl.service.parseJSON;


/**
 *文件名:
 *CopyRright(C):北京全亚通信技术有限公司
 *创建人:贾永强
 *创建日期:2013-3-14上午9:27:41
 *修改人:
 *修改日期:
 *功能描述:
 *版本号:
 */
public class Props {
	private BaseValueObject name;
//	private BaseValueObject key;
//	private BaseValueObject text;
//	private BaseValueObject temp1;
//	private BaseValueObject temp2;
	//名字
	private BaseValueObject text;
	
	//描述
	private BaseValueObject desc;
	
	//表达式
	private BaseValueObject expr;

	public BaseValueObject getText() {
		return text;
	}

	public void setText(BaseValueObject text) {
		this.text = text;
	}

	public BaseValueObject getDesc() {
		return desc;
	}

	public void setDesc(BaseValueObject desc) {
		this.desc = desc;
	}
	
	public BaseValueObject getName() {
		return name;
	}

	public void setName(BaseValueObject name) {
		this.name = name;
	}

	public BaseValueObject getExpr() {
		return expr;
	}

	public void setExpr(BaseValueObject expr) {
		this.expr = expr;
	}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "名字是："+getText()+";描述是:"+getDesc();
		}
	
}
