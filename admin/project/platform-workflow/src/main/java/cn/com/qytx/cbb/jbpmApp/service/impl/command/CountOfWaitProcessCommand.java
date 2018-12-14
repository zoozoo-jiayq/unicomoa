package cn.com.qytx.cbb.jbpmApp.service.impl.command;

import javax.persistence.EntityManager;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.jpa.JpaDbSessionImpl;
import org.jbpm.pvm.internal.session.DbSession;

/**
 * 功能：查询待处理的工作流/公文数量
 * 作者： jiayongqiang
 * 创建时间：2014年8月4日
 */
public class CountOfWaitProcessCommand implements Command<Integer> {

    //1 ,公文;2工作流
    private int type;
    private String user;
    
    @Override
    public Integer execute(Environment environment) throws Exception {
        // TODO Auto-generated method stub
        JpaDbSessionImpl impl = (JpaDbSessionImpl) environment.get(DbSession.class);
        EntityManager entityManager = impl.getEntityManager();
        
        return null;
    }

}
