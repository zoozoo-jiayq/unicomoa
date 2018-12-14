package cn.com.qytx.cbb.autoCreateEntity.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class CommonDao  {

    @PersistenceContext
    protected EntityManager entityManager;
    
    public Object findOne(Class c,int id){
    	return entityManager.find(c, id);
    }
}
