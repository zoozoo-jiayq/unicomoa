package cn.com.qytx.oa.knowledge.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.qytx.oa.knowledge.domain.Knowledge;
import cn.com.qytx.platform.base.dao.BaseDao;
import cn.com.qytx.platform.base.query.Page;
import cn.com.qytx.platform.base.query.Pageable;
import cn.com.qytx.platform.org.domain.UserInfo;

/**
 * 功能: 知识库Dao
 * 版本: 1.0
 * 开发人员: 李华伟
 * 创建日期: 2013-11-7
 * 修改日期: 2013-11-7
 * 修改列表:
 */
@Repository
public class KnowledgeDao extends BaseDao<Knowledge, Integer> implements Serializable
{
    /**
     * 序列号
     */
    private static final long serialVersionUID = -2233883992168435135L;

    /**
     * 
     * 功能：根据姓名和分类Id查询知识库
     * @param title  知识库名称
     * @param knowledgeTypeId  知识库类型id
     * @return Knowledge
     */
    public Knowledge findKnowledgeByParam(String title, Integer knowledgeTypeId)
    {
        String hql = "title = ? and knowledgeType.vid = ? and isDelete = 0";
        return (Knowledge)super.findOne(hql, title, knowledgeTypeId);
    }    
    
    /**
     * 功能：根据Id删除知识库信息
     * @param id 主键
     */
    public void deleteById(Integer id)
    {
        String hql = "update Knowledge set isDelete = 1 where vid = ?";
        super.executeQuery(hql, id);
    }
    
    /**
     * 功能：分页查询知识库列表页面
     * @param page page
     * @param knowledge knowledge查询条件
     * @return 分页数据
     */
    public Page<Knowledge> findPageByVo(Pageable page, UserInfo userInfo,Knowledge knowledge)
    {
        StringBuilder hqlSb = new StringBuilder();
        hqlSb.append("isDelete = 0");

        // 条件过滤
        if (null != knowledge)
        {
            List<Object> params = new ArrayList<Object>();
           
            
            // 分类列表
            if (null != knowledge.getKnowledgeType() && null != knowledge.getKnowledgeType().getVid() 
                    && 0 != knowledge.getKnowledgeType().getVid())
            {
                hqlSb.append(" and ( knowledgeType.vid = ? or knowledgeType.path like ?)");
                params.add(knowledge.getKnowledgeType().getVid());
                params.add("%"+knowledge.getKnowledgeType().getPath()+",%");
            }
            
            // 审核分类 获取审核通过及待审核
            Integer auditSign = knowledge.getAuditSign();
            if (null != auditSign && -1 != auditSign)
            {
                hqlSb.append(" and auditSign = ?");
                params.add(auditSign);
            }
          if(knowledge.getIsPrivate()==0){
        	  hqlSb.append(" and isPrivate =0");
//              if(userInfo.getIsForkGroup()!=2){
//            	  hqlSb.append(" and (isForkGroup =2 or isForkGroup=? )");
//                  params.add(userInfo.getIsForkGroup());	  
//              }
            }else{
            	hqlSb.append(" and isPrivate =?");
            	params.add(knowledge.getIsPrivate());
//                hqlSb.append(" and isForkGroup=? ");
//                params.add(userInfo.getIsForkGroup());	  
            }
          
            
            
            
            // 模糊查询条件
            String search = knowledge.getSearch();
            if (!StringUtils.isEmpty(search))
            {//  params.add("%"+search+"%");   or contentInfo like ?
                hqlSb.append(" and (title like ?  or (");
                params.add("%"+search+"%");
              
                
                String[] keyArr = search.split(";");
                for (int i = 0; i< keyArr.length; i++)
                {
                    hqlSb.append("keyword like ? ");
                    if (i != keyArr.length -1)
                    {
                        hqlSb.append(" and ");
                    }
                    else
                    {
                        hqlSb.append(" ))");
                    }
                    params.add("%"+keyArr[i]+"%");
                }
            }
            //功能模块id
            if (knowledge.getColumnId()!=null) {
            	hqlSb.append(" and columnId = ? ");
                params.add(knowledge.getColumnId());
			}
          
          //  hqlSb.append(" order by createTime desc");
            return super.findAll( hqlSb.toString(),page, params.toArray());
        }
        return null;
    }
    
    /**
     * 
     * 功能：根据Id获取知识库信息
     * @param knowledgeTypeId Id
     * @return Knowledge
     */
    public Knowledge findKnowledgeByTypeId(Integer knowledgeTypeId)
    {
        String hql = " knowledgeType.vid = ? and isDelete = 0";
        return (Knowledge)super.findOne(hql, knowledgeTypeId);
    }   
    
    /**
     * 功能：后台首页 知识库 5条
     * @return List<Notice>
     */
    @SuppressWarnings("deprecation")
    public List<Object> findLaster5Notice(){
        String hql = "from Knowledge where isDelete = 0 order by createTime desc";
        
        return super.findMaxRow(hql,0,5);
    }
    /**
     * 功能：以详情的样式，分页查询知识库
     * @param page
     * @param knowledge
     * @return
     */
    public Page<Knowledge> findByPageDetail(Pageable page,Knowledge knowledge){
    	StringBuilder hqlSb = new StringBuilder();
        hqlSb.append("isDelete = 0");

        // 条件过滤
        if (null != knowledge)
        {
            List<Object> params = new ArrayList<Object>();
            Integer isForkGroup = knowledge.getIsForkGroup();
            if(isForkGroup!=null&&isForkGroup!=2){//查询本地和省的知识库
            	hqlSb.append(" and (isForkGroup = 2 or isForkGroup = "+isForkGroup+" )");
            }
            // 模糊查询条件
            String search = knowledge.getSearch();
            if (!StringUtils.isEmpty(search))
            {//  params.add("%"+search+"%");   or contentInfo like ?
                hqlSb.append(" and (title like ?  or (");
                params.add("%"+search+"%");
              
                
                String[] keyArr = search.split(";");
                for (int i = 0; i< keyArr.length; i++)
                {
                    hqlSb.append("keyword like ? ");
                    if (i != keyArr.length -1)
                    {
                        hqlSb.append(" and ");
                    }
                    else
                    {
                        hqlSb.append(" ))");
                    }
                    params.add("%"+keyArr[i]+"%");
                }
            }
            
            // 分类列表
            if (null != knowledge.getKnowledgeType() && null != knowledge.getKnowledgeType().getVid() 
                    && 0 != knowledge.getKnowledgeType().getVid())
            {
                hqlSb.append(" and ( knowledgeType.vid = ? or knowledgeType.path like ?)");
                params.add(knowledge.getKnowledgeType().getVid());
                params.add("%"+knowledge.getKnowledgeType().getPath()+"%");
            }
            
            // 审核分类 获取审核通过及待审核
            Integer auditSign = knowledge.getAuditSign();
            if (null != auditSign && -1 != auditSign)
            {
                hqlSb.append(" and auditSign = ?");
                params.add(auditSign);
            }
          //  hqlSb.append(" order by createTime desc");
            return super.findAll( hqlSb.toString(),page, params.toArray());
        }
    	return null;
    }

	/**
	 * 功能：分页查询知识库
	 * @param page
	 * @param creatUserId 创建人id
	 * @param typeId 类型id
	 * @param startTime 查询开始时间
	 * @param endTime  查询结束时间
	 * @return
	 */
	public Page<Knowledge> findKnowledge(Pageable page, Integer isForkGroup,Integer creatUserId,
			Integer typeId,Integer isprivate, Timestamp startTime, Timestamp endTime) {
		StringBuilder hqlSb = new StringBuilder();
        hqlSb.append("isDelete = 0");
        // 条件过滤
            List<Object> params = new ArrayList<Object>();
            if(isForkGroup!=null&&isForkGroup>0){//查询本地和省的知识库
            	hqlSb.append(" and isForkGroup = ? ");
            	params.add(isForkGroup);
            }
            if(isprivate!=null&&isprivate==0){//查询本地和省的知识库
            	hqlSb.append(" and isPrivate =0 ");
            }else if(isprivate!=null&&isprivate==-1){
            	hqlSb.append(" and isPrivate >0 ");
            }
            if(creatUserId!=null&&creatUserId>0){//创建者id
            	hqlSb.append(" and createUserInfo.userId = ? ");
            	params.add(creatUserId);
            }
            if(typeId!=null&&typeId>0){//创建者id
            	hqlSb.append(" and knowledgeType.vid = ? ");
            	params.add(typeId);
            }
            if(startTime!=null){
            	hqlSb.append(" and dateDiff(s,?,createTime)>=0 ");
            	params.add(startTime);
            }
            if(endTime!=null){
            	hqlSb.append(" and dateDiff(s,?,createTime)<0 ");
            	params.add(endTime);
            }
            return super.findAll( hqlSb.toString(),page,params.toArray());
        
	}
	
   /**
     * 功能：分页查询知识库列表页面
     * @param page page
     * @return 分页数据
     */
    public Page<Knowledge> findPage(Pageable page, UserInfo userInfo,Integer typeId,Integer moduleId)
    {
    	return super.findAll( " isDelete = 0 and columnId = ? and knowledgeType.vid=?",page, moduleId,typeId);
    }
	
}
