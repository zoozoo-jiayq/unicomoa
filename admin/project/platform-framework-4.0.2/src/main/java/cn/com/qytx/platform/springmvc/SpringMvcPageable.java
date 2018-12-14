package cn.com.qytx.platform.springmvc;

import cn.com.qytx.platform.base.query.PageRequest;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;

public  class SpringMvcPageable {
	
	/** 分页开始条数 */
    private Integer iDisplayStart=0;

    /** 每页显示条数 */
    private Integer  iDisplayLength = 15;

    private Pageable pageable;

    /**
     * 获取分页开始索引值，默认从0开始
     * @return 分页开始索引值
     */
    public Integer getIDisplayStart() {
        return iDisplayStart;
    }

    /**
     * 设置分页开始索引值
     * @param iDisplayStart 分页开始索引值
     */
    public void setIDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    /**
     * 获取每个页面显示的记录数
     * @return 返回每个页面显示的数量，默认值15
     */
    public Integer getIDisplayLength() {
        return iDisplayLength;
    }

    /**
     * 设置每个页面显示数量
     * @param iDisplayLength 每个页面的显示数量
     */
    public void setIDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }


    /**
     * 获取Pageable类型的分页对象
     * @return 返回Pageable类型的分页对象，
     */
    public Pageable getPageable(){
        int page = iDisplayStart / iDisplayLength ;
        
        pageable = new PageRequest(page,iDisplayLength);
        return pageable;
    }
    
    /**
     * 获取Pageable类型的分页对象，附带排序效果
     * @return 返回Pageable类型的分页对象，按照Sort对象定义的排序规则排序
     */
    public Pageable getPageable(Sort sort){
        int page = iDisplayStart / iDisplayLength ;
        pageable = new PageRequest(page,iDisplayLength,sort);
        return pageable;
    }
    
	
}
