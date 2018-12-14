package cn.com.qytx.oa.record.service;

import cn.com.qytx.oa.record.domain.RecordPenalties;
import cn.com.qytx.oa.record.domain.UserRecord;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;

import java.io.Serializable;

/**
 * Created by lilipo on 2016/12/19.
 */
public interface IRecordPenalties extends BaseService<RecordPenalties>, Serializable {
    /**
     * 查询某个员工的奖惩信息
     *
     * @param page   翻页信息
     * @param userId 用户Id
     * @return 查询结果
     */
    public Page<RecordPenalties> findAllPenaltiesByUserId(Pageable page, int userId);


    /**
     * 根据Id查询一份奖惩信息
     *
     * @param id 奖惩信息Id
     * @return 查询结果
     */
    public RecordPenalties findById(int id);

    /**
     * 保存一份奖惩信息
     * @param recordPenalties 奖惩信息
     */
    public RecordPenalties saveOrUpdate(RecordPenalties recordPenalties);
}
