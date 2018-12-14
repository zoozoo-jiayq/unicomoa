package cn.com.qytx.oa.record.impl;

import cn.com.qytx.oa.record.dao.PenaltiesDao;
import cn.com.qytx.oa.record.domain.RecordPenalties;
import cn.com.qytx.oa.record.service.IRecordPenalties;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能:个人奖惩信息接口实现类
 * 版本:1.0
 * 开发人员: 李自立
 * 创建日期: 2016-12-19
 */
@Service
@Transactional
public class RecordPenaltiesImpl extends BaseServiceImpl<RecordPenalties> implements IRecordPenalties {

    @Autowired
    private PenaltiesDao penaltiesDao;
    @Override
    public Page<RecordPenalties> findAllPenaltiesByUserId(Pageable page, int userId) {
        return penaltiesDao.findAllPenaltiesByUserId(page, userId);
    }

    @Override
    public RecordPenalties findById(int id) {
        return penaltiesDao.findById(id);
    }
    @Override
    public RecordPenalties saveOrUpdate(RecordPenalties recordPenalties){
        penaltiesDao.saveOrUpdate(recordPenalties);
        return recordPenalties;
    }
}
