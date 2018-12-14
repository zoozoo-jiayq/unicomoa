package cn.com.qytx.oa.record.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;
import cn.com.qytx.oa.record.domain.RecordLearn;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.utils.CbbConst;
@Repository
public class LearnDao extends BaseDao<RecordLearn, Serializable> implements Serializable{  
	/**
     * 保存学习信息
     * @param recordLearn 学习信息
     */
	public void save(RecordLearn recordLearn){
		this.saveOrUpdate(recordLearn);
	}
	/**
     * 分页查询学习信息
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
	public Page<RecordLearn> find(Pageable page,int userId){
		String condition="userInfo.userId=?1 and isDelete=?2";
        Object[] values = {userId, CbbConst.NOT_DELETE};
		return this.findAll(condition, page, values);
	}
    /**
     * 根据id查学习信息
     *@param learnId 学习信息ID
     * @return 
     */
    public RecordLearn findById(Integer id) {
    	RecordLearn learn=this.findOne(" id=? and isDelete=?", id, CbbConst.NOT_DELETE);
    	return learn;
    }
}
