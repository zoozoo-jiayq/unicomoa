package cn.com.qytx.oa.record.service;

import java.io.Serializable;

import cn.com.qytx.oa.record.domain.RecordTitle;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;


/**
 * 功能：档案-职称接口
 * 版本：1.0
 * 开发人员: pb
 * 创建日期：2016年12月19日
 * 修改日期：2016年12月19日	
 */
public interface IRecordTitle extends BaseService<RecordTitle> , Serializable{

    /**
     * 功能：职称列表分页
     * @param page
     * @param userId
     * @return
     */
    public Page<RecordTitle> findRecordTitlePage(Pageable page,Integer userId);
    

}
