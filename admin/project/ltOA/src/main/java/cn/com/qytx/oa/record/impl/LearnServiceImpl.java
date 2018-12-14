package cn.com.qytx.oa.record.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.qytx.oa.record.dao.LearnDao;
import cn.com.qytx.oa.record.domain.RecordLearn;
import cn.com.qytx.oa.record.service.LearnService;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.base.query.Sort;
import cn.com.qytx.platform.base.service.impl.BaseServiceImpl;
import cn.com.qytx.platform.utils.enumeration.DataFilterType;
@Service
@Transactional
public class LearnServiceImpl extends BaseServiceImpl<RecordLearn> implements LearnService {
	@Autowired
    private LearnDao learnDao;
	@Override
	public BaseDao<RecordLearn, Integer> companyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDao<RecordLearn, Integer> companyId(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BaseDao<RecordLearn, Integer> dataFilter(DataFilterType arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(RecordLearn arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public int deleteByIds(Iterable<Integer> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByIds(boolean arg0, Iterable<String> arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RecordLearn> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordLearn> findAll(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordLearn> findAll(String arg0, Sort arg1, Object... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<RecordLearn> findAll(String arg0, Pageable arg1, Object... arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecordLearn findById(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecordLearn findOne(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecordLearn findOne(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDao<RecordLearn, Integer> isDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecordLearn saveOrUpdate(RecordLearn arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecordLearn> saveOrUpdate(Iterable<RecordLearn> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDao<RecordLearn, Integer> unDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(RecordLearn recordLearn) {
		// TODO Auto-generated method stub
		this.learnDao.save(recordLearn);
	}

	@Override
	public Page<RecordLearn> find(Pageable page, int userId) {
		// TODO Auto-generated method stub
		return this.learnDao.find(page, userId);
	}

	public LearnDao getLearnDao() {
		return learnDao;
	}

	public void setLearnDao(LearnDao learnDao) {
		this.learnDao = learnDao;
	}

	@Override
	public RecordLearn findById(Integer learnId) {
		// TODO Auto-generated method stub
		return this.learnDao.findById(learnId);
	}

}
