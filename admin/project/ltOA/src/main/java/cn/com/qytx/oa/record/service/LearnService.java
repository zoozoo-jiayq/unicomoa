package cn.com.qytx.oa.record.service;

import java.io.Serializable;

import cn.com.qytx.oa.record.domain.RecordLearn;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

public interface LearnService extends BaseService<RecordLearn> , Serializable{
    /**
     * 保存一份新的学习信息
     *
     * @param userRecord 学习实例
     */
    public void save(RecordLearn recordLearn);
    /**
     * 根据组Id查询出所有的学习信息列表
     * @param page 翻页信息
     * @param userId 用户ID
     * @return 查询的结果列表信息
     */
    public Page<RecordLearn> find(Pageable page,int userId);
    /**
     * 根据id查学习信息
     *@param learnId 学习信息ID
     * @return 
     */
    public RecordLearn findById(Integer learnId) ;
}
