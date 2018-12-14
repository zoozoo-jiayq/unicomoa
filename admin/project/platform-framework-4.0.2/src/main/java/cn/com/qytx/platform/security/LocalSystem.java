package cn.com.qytx.platform.security;

/**
 * for test
 * Created by serv on 14-6-30.
 */
public class LocalSystem {

    public static ThreadLocal<SessionVariable> variableThreadLocal = new ThreadLocal<SessionVariable>();

    public static void setSessionVariable(SessionVariable sessionVariable){
        variableThreadLocal.set(sessionVariable);
    }

    public static SessionVariable getSessionVariable(){
        return variableThreadLocal.get();
    }
}
