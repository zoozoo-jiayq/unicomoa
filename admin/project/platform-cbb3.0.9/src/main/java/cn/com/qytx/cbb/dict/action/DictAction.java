package cn.com.qytx.cbb.dict.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.com.qytx.cbb.dict.domain.Dict;
import cn.com.qytx.cbb.dict.service.IDict;
import cn.com.qytx.platform.base.action.BaseActionSupport;

import com.google.gson.Gson;

/**
 * 功能:数据维护
 * 版本: 1.0
 * 开发人员: zhangjingjing
 * 创建日期: 2014-6-26
 * 修改日期: 2014-6-26
 * 修改列表: 
 */
public class DictAction extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	@Resource(name = "dictService")
	private  IDict dictService;

	private Integer sysTag; // 类型类别
	private String infoType;//类型
	private String name;//名字
	private Integer id;//对象ID
	private Integer sysTagItem;//项目类别
	private String ids;
	private int typeValue;
    private int infoOrder; //排序
	/**
	 * @Title: 根据不同项目和类型获取通用信息
	 * @param
	 * @return
	 * @return 返回类型
	 */
	public String getDicts() {
		List<Dict> list = dictService.findList(infoType, Math.abs(sysTag));
		ajax(list,true);
		return null;
	}
	/**
	 * 
	 * 功能：手机端接口
	 * @return
	 */
	public String getMoblieDicts() {
		final List<Dict> list = dictService.findList(infoType, Math.abs(sysTag));
		try {
			Gson gson = new Gson();
			String data = gson.toJson(list);
			ajax("100||" + data);
		} catch (Exception e) {
			ajax("102||操作异常");
			LOGGER.error(e.getMessage());
		} 
		return null;
	}
	/**
	 * @Title: 根据不同项目和类型获取通用信息
	 * @Description:
	 * @param
	 * @return
	 * @return 返回类型
	 * @throws
	 */
	public String getSysDicts() {
		List<Dict> list = dictService.findSysList(sysTagItem==null?0:sysTagItem);
		ajax(list,true);
		return null;
	}

	/**
	 * add类别
	 * 
	 * @return String
	 */
	public String addType() {
			if (dictService.getInfoTypeByName(infoType, sysTag, name) == null) {
				List<Dict> list = dictService.findAllListByValue(infoType, sysTag);
				if (list != null && !list.isEmpty())
                {
					typeValue = list.get(list.size()-1).getValue() + 1;
                }else{
                	typeValue = 1;
                }
				Dict d = new Dict();
				d.setInfoType(infoType);
				d.setName(name);
				d.setCreateDate(new Timestamp(System.currentTimeMillis()));
				d.setModifyDate(new Timestamp(new Date().getTime()));
				d.setSysTag(sysTag);
				d.setValue(typeValue);
				d.setRecordUserId(getLoginUser().getUserId());
				d.setIsDelete(0);
				d.setCompanyId(getLoginUser().getCompanyId());
				d.setInfoOrder(infoOrder);
				dictService.saveOrUpdate(d);
				ajax("0");
			} else {
				ajax("1");
			}
		return null;
	}

	/**
	 * update类别
	 * 
	 * @return
	 */
	public String updateType() {
			Dict type = dictService.findOne(id);
			// 判断名称是否已经存在
			Dict t = dictService.getInfoTypeByNameAndNotId(type.getInfoType(), type.getSysTag(), name, id);
			if (t == null) {
				type.setName(name);
				//type.setValue(typeValue);
				type.setModifyDate(new Timestamp(new Date().getTime()));
				type.setRecordUserId(getLoginUser().getUserId());
				type.setInfoOrder(infoOrder);
				dictService.saveOrUpdate(type);
				if (sysTag==-1) {
					dictService.upateDictByInfoType(type.getInfoType(), infoType);
				}
				ajax("0");
			} else {
				ajax("1");
			}
		return null;
	}

	/**
	 * 删除类别
	 * 
	 * @return
	 */
	public String deleteType() {
			if (sysTag==-1) {
				dictService.deleteByInfoType(infoType);
			}
			else {
				String[] typeIds = ids.split(",");
				for (int i = 0; i < typeIds.length; i++) {
					dictService.delete(Integer.parseInt(typeIds[i]),false);
				}
			}
		  ajax("0");
		return null;
	}

	public Integer getSysTag() {
		return sysTag;
	}

	public void setSysTag(Integer sysTag) {
		this.sysTag = sysTag;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSysTagItem() {
		return sysTagItem;
	}

	public void setSysTagItem(Integer sysTagItem) {
		this.sysTagItem = sysTagItem;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(int typeValue) {
		this.typeValue = typeValue;
	}
	/**
	 * @return the infoOrder
	 */
	public int getInfoOrder() {
		return infoOrder;
	}
	/**
	 * @param infoOrder the infoOrder to set
	 */
	public void setInfoOrder(int infoOrder) {
		this.infoOrder = infoOrder;
	}
	
}
