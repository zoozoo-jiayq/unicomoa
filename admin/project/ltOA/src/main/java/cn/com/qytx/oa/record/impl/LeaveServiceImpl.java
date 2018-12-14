package cn.com.qytx.oa.record.impl;

import cn.com.qytx.oa.record.dao.LeaveDao;
import cn.com.qytx.oa.record.domain.RecordLeave;
import cn.com.qytx.oa.record.domain.RecordWork;
import cn.com.qytx.oa.record.service.IRecordLeave;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.org.dao.UserDao;
import cn.com.qytx.platform.org.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lilipo on 2016/12/22.
 */
@Service
@Transactional
public class LeaveServiceImpl extends BaseServiceImpl<RecordLeave> implements IRecordLeave {

    @Autowired
    private LeaveDao leaveDao;
    @Autowired
    private UserDao userDao;
    /**
     * 功能：
     * @param pageable
     * @param recordLeave
     * @return
     */
    @Override
    public Page<RecordLeave> findList(Pageable pageable, RecordLeave recordLeave) {
        // TODO Auto-generated method stub
        return leaveDao.findList(pageable, recordLeave);
    }

    @Override
    public RecordLeave saveOrUpdateLeave(RecordLeave recordLeave, UserInfo userInfo) {
        leaveDao.saveOrUpdate(recordLeave);
        userDao.saveOrUpdate(userInfo);
        return recordLeave;
    }
}
