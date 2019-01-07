package com.unicomoa.unicomoa.workplan;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unicomoa.unicomoa.base.BaseRepository;

@Repository
public interface WorkPlanRepository extends BaseRepository<WorkPlan> {
	
	@Modifying
	@Query("update WorkPlan set realTarget = realTarget+1 where id =:id")
	public void updateTarget(@Param("id")int id);

	public List<WorkPlan> findByCreaterIdAndDayStrLike(int createrId,String dayStr,Sort sort);
	
	public List<WorkPlan> findByDayStrLike(String dayStr,Sort sort);
}
