package cn.com.qytx.cbb.publicDom.vo;

import cn.com.qytx.cbb.dict.domain.Dict;

/** 封装公文类型和数量
 * @author jiayongqiang
 *
 */
public class DocumentTypeVo {

	private Dict dict;
	private int count = 0 ;
	private boolean select = false;
	
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public DocumentTypeVo(Dict dict, int count) {
		super();
		this.dict = dict;
		this.count = count;
	}
	public Dict getDict() {
		return dict;
	}
	public void setDict(Dict dict) {
		this.dict = dict;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
