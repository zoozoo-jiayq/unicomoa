package cn.com.qytx.oa.record.service;

import cn.com.qytx.oa.record.domain.RecordLeave;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.service.BaseService;
import cn.com.qytx.platform.org.domain.UserInfo;

import java.io.Serializable;

/**
 * Created by lilipo on 2016/12/22.
 */
public interface IRecordLeave extends BaseService<RecordLeave>,Serializable {
    /**
     *
     * 功能：分页查询员工离职信息
     * @return
     */
    public Page<RecordLeave> findList(Pageable pageable, RecordLeave recordLeave);

    RecordLeave saveOrUpdateLeave(RecordLeave recordLeave, UserInfo userInfo);
}
