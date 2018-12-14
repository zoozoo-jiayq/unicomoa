package cn.com.qytx.oa.record.dao;

import cn.com.qytx.oa.record.domain.RecordPenalties;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.utils.CbbConst;
import cn.com.qytx.platform.base.query.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by lilipo on 2016/12/19.
 */
@Repository
public class PenaltiesDao extends BaseDao<RecordPenalties, Integer> implements Serializable {

    /**
     * 查询某个员工的奖惩信息
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<RecordPenalties> findAllPenaltiesByUserId(Pageable page, int userId) {
        String condition = " userInfo.userId=?1 and isDelete=?2";
        Object[] values = {userId, CbbConst.NOT_DELETE,};
        return this.findAll(condition, page, values);
    }
    /**
     * 根据Id查询一份奖惩信息
     * @param id 奖惩信息Id
     * @return 查询结果
     */
    public RecordPenalties findById(int id) {
        return (RecordPenalties) super.findOne(" id=? and isDelete=?", id, CbbConst.NOT_DELETE);
    }

    /**
     * 保存一份奖惩信息
     * @param recordPenalties 奖惩信息
     */
    public RecordPenalties saveOrUpdate(RecordPenalties recordPenalties) {
        super.saveOrUpdate(recordPenalties);
        return recordPenalties;
    }
}