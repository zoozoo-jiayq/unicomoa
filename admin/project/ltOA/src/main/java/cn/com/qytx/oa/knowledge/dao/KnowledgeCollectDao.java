package cn.com.qytx.oa.knowledge.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.qytx.oa.knowledge.domain.KnowledgeCollect;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;

/**
 * 功能: 知识库收藏的dao
 * 版本: 1.0
 * 开发人员: 马恺
 * 创建日期: 2014-11-25
 * 修改日期: 2014-11-25
 * 修改列表:
 *
 */
@Component
public class KnowledgeCollectDao extends BaseDao<KnowledgeCollect,Integer> implements Serializable{

	private static final long serialVersionUID = 6897770095022097696L;
	
	/**
	 * 更新
	 * @param kc
	 */
	public void saveOrUpdateKC(KnowledgeCollect kc){
		super.saveOrUpdate(kc);
	}

	/**
	 * 删除收藏的知识
	 * @param id
	 */
	public void changeStateById(Integer id){
		String hql = "update KnowledgeCollect set state=1 where id=?";
		super.executeQuery(hql, id);
//		super.bulkUpdate(hql);
	}
	
	/**
	 * 分页查询收藏的知识库
	 * @param page
	 * @param kc  知识库收藏查询条件
	 * @return
	 */
	public Page<KnowledgeCollect> findByPage(Pageable page,KnowledgeCollect kc){
		StringBuilder hql = new StringBuilder();
		hql.append(" state=0");
		List<Object> params = new ArrayList<Object>();
		if(kc!=null){
			Integer knowledgeTypeId = kc.getKnowledgeTypeId();
			if(knowledgeTypeId!=null && knowledgeTypeId!=0){
				hql.append(" and (knowledge.knowledgeType.vid= "+knowledgeTypeId+" or knowledge.knowledgeType.path like '%"+knowledgeTypeId+",%') ");
			}
			Integer seatId = kc.getSeat().getUserId();
			hql.append(" and seat.userId = ? and knowledge.isDelete=0 ");
			params.add(seatId);
			String search = kc.getSearch();
			if(search!=null&&!"".equals(search)){
				hql.append(" and (knowledge.title like ? or (");
				params.add("%"+search+"%");
				String[] keyArr = search.split(";");
                for (int i = 0; i< keyArr.length; i++){
                    hql.append("knowledge.keyword like ? ");
                    if (i != keyArr.length -1)
                    {
                        hql.append(" and ");
                    }
                    else
                    {
                        hql.append(" ))");
                    }
                    params.add("%"+keyArr[i]+"%");
                }
			}
			return super.findAll( hql.toString(),page, params.toArray());
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据知识库ID和坐席id查询收集信息
	 * @param knowledgeId 知识库id
	 * @param seatId  坐席id
	 * @return
	 */
	public KnowledgeCollect findOne(Integer knowledgeId,Integer seatId){
		String hql = " state=0 and knowledge.vid=?1 and seat.userId=?2";
		return super.findOne(hql,  knowledgeId,seatId);
	}

	/**
	 * 功能：知识库收藏
	 * @param page  
	 * @param creatUserId  创建人di
	 * @param start  查询开始时间
	 * @param end  查询结束时间
	 * @return
	 */
	public Page<KnowledgeCollect> findKnowledgeColl(Pageable page,
			Integer creatUserId, Timestamp startTime, Timestamp endTime) {
		StringBuilder hql = new StringBuilder();
		hql.append(" state=0");
		List<Object> params = new ArrayList<Object>();
		
			if(creatUserId!=null&&creatUserId>0){
				hql.append(" and seat.userId = ? and knowledge.isDelete=0 ");
				params.add(creatUserId);
			}
			   if(startTime!=null){
				   hql.append(" and dateDiff(s,?,lastUpdateTime)>=0 ");
	            	params.add(startTime);
	            }
	            if(endTime!=null){
	            	hql.append(" and dateDiff(s,?,lastUpdateTime)<0 ");
	            	params.add(endTime);
	            }
		
				return super.findAll( hql.toString(),page, params.toArray());	
			
	}
	
}
