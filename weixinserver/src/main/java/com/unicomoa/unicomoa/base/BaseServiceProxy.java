package com.unicomoa.unicomoa.base;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public abstract class BaseServiceProxy<T extends BaseModel> implements PagingAndSortingRepository<T, Integer> {
	
	
	abstract protected BaseRepository<T> getBaseRepository();

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return getBaseRepository().count();
	}

	@Override
	public void delete(T arg0) {
		// TODO Auto-generated method stub
		getBaseRepository().delete(arg0);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		getBaseRepository().deleteAll();
	}

	@Override
	public void deleteAll(Iterable<? extends T> arg0) {
		// TODO Auto-generated method stub
		getBaseRepository().deleteAll(arg0);
	}

	@Override
	public void deleteById(Integer arg0) {
		// TODO Auto-generated method stub
		getBaseRepository().deleteById(arg0);
	}

	@Override
	public boolean existsById(Integer arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().existsById(arg0);
	}

	@Override
	public Iterable<T> findAll() {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll();
	}

	@Override
	public Iterable<T> findAllById(Iterable<Integer> arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAllById(arg0);
	}

	@Override
	public Optional<T> findById(Integer arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().findById(arg0);
	}

	@Override
	public <S extends T> S save(S arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().save(arg0);
	}

	@Override
	public <S extends T> Iterable<S> saveAll(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().saveAll(arg0);
	}

	@Override
	public Page<T> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll(arg0);
	}

	@Override
	public Iterable<T> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return getBaseRepository().findAll(arg0);
	}

	
	
}
