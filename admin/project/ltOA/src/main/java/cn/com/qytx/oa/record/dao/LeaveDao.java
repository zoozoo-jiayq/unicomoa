package cn.com.qytx.oa.record.dao;

import cn.com.qytx.oa.record.domain.RecordLeave;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by lilipo on 2016/12/22.
 */
@Repository
public class LeaveDao extends BaseDao<RecordLeave, Serializable> implements Serializable {
    /**
     *
     * 功能：分页查询员工离职信息
     * @return
     */
    public Page<RecordLeave> findList(Pageable pageable, RecordLeave recordLeave){

        String hql=" select r from RecordLeave r where r.isDelete=0";
        if(recordLeave.getUserInfo()!=null){
            hql +=" and r.userInfo.userId="+recordLeave.getUserInfo().getUserId();
        }
        if(recordLeave.getCompanyId()!=null){
            hql +=" and r.companyId="+recordLeave.getCompanyId();
        }
        hql +=" order by createTime desc";
        return super.findByPage(pageable, hql);

    }
}
