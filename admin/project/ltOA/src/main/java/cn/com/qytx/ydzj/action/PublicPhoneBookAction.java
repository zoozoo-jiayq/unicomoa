package cn.com.qytx.ydzj.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import cn.com.qytx.cbb.file.config.FilePathConfig;
import cn.com.qytx.platform.base.action.BaseActionSupport;
import cn.com.qytx.ydzj.domain.PublicPhoneBook;
import cn.com.qytx.ydzj.domain.PublicPhoneBookCategory;
import cn.com.qytx.ydzj.service.IPublicPhoneBook;
import cn.com.qytx.ydzj.service.IPublicPhoneBookCategory;

import com.google.gson.Gson;
/**
 * 
 * <br/>功能: 电话本
 * <br/>版本: 1.0
 * <br/>开发人员: REN
 * <br/>创建日期: 2014-10-8
 * <br/>修改日期: 2014-10-8
 * <br/>修改列表:
 */
public class PublicPhoneBookAction extends BaseActionSupport {

	private static final long serialVersionUID = 1L;
	
	protected static final Logger logger = Logger.getLogger(PublicPhoneBookAction.class.getName());
	
	private int categoryId = 0;
	private String keyword = "";
	//公共电话本接口
	@Resource(name="publicPhoneBookImpl")
	private IPublicPhoneBook publicPhoneBookImpl;
	//公共电话本类别接口
	@Resource(name="publicPhoneBookCategoryImpl")
	private IPublicPhoneBookCategory publicPhoneBookCategoryImpl;
	@Resource(name="filePathConfig")
	private FilePathConfig fiePathConfig;
	/**
	 * 功能：获得共工电话本
	 * <br/>@return
	 */
	public String getPublicPhoneBook(){
		String ret = "102||操作失败";
		try {
			List<PublicPhoneBookCategory> list = publicPhoneBookCategoryImpl.findAllPhoneBookCategory();
			if (list!=null&&list.size()>0) {
				for (PublicPhoneBookCategory publicPhoneBookCategory : list) {
					List<PublicPhoneBook> booklist = publicPhoneBookImpl.findBooks(publicPhoneBookCategory.getVid(), null);
					//常用号码添加图片
					if(booklist!=null&&booklist.size()>0){
						for(PublicPhoneBook book :booklist){
							if(book!=null&&StringUtils.isNotBlank(book.getPicture())){
								book.setPictureUrl(fiePathConfig.getFileViewPath()+book.getPicture());
							}
						}
					}
					publicPhoneBookCategory.setPhoneCount(booklist==null?0:booklist.size());
					publicPhoneBookCategory.setPhoneList(booklist);
				}
			}
			Gson gson = new Gson();
			String json = gson.toJson(list);
			ret = "100||" + json;
		} catch (Exception ex) {
			logger.error(ex);
			ret = "102||操作失败";
		}
		ajax(ret);
		return null;
	}
	/**
	 * 获得公共电话本类别
	 */
	public String getPublicPhoneBookCategory(){
		String ret = "102||操作失败";
		try {
			List<PublicPhoneBookCategory> list = publicPhoneBookCategoryImpl.findAllPhoneBookCategory();
			Map<Integer, Integer> map = publicPhoneBookImpl.getPhoneBookNum();
			if (list!=null&&list.size()>0) {
				for (PublicPhoneBookCategory publicPhoneBookCategory : list) {
					if (map.get(publicPhoneBookCategory.getVid())!=null) {
						Integer phoneCount = map.get(publicPhoneBookCategory.getVid());
						publicPhoneBookCategory.setPhoneCount(phoneCount);
					}else {
						publicPhoneBookCategory.setPhoneCount(0);
					}
				}
			}
			Gson gson = new Gson();
			String json = gson.toJson(list);
			ret = "100||" + json;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			ret = "102||操作失败";
		}
		ajax(ret);
		return null;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
