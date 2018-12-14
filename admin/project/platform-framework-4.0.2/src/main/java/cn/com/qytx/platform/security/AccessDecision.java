package cn.com.qytx.platform.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * User: 黄普友
 * Date: 13-2-2
 * Time: 下午2:19
 */
public class AccessDecision implements AccessDecisionManager {
    private static final Logger LOGGER = Logger.getLogger(AccessDecision.class);

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(authentication==null||object==null||configAttributes==null){
            throw new AccessDeniedException("没有权限");
        }else{
            if (configAttributes.size()==0) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("资源没有对应的角色！");
                }
                throw new AccessDeniedException("没有权限");
            }
            LOGGER.debug("正在访问的url是："+object.toString());
            Iterator<ConfigAttribute> ite = configAttributes.iterator();
            while (ite.hasNext()) {
                ConfigAttribute ca = ite.next();
                String needRole = ((SecurityConfig) ca).getAttribute();
                for (GrantedAuthority ga : authentication.getAuthorities()) {
                    LOGGER.debug("授权信息是："+ga.getAuthority());
                    if (needRole.equals(ga.getAuthority())) {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("判断到，needRole 是"+needRole+",用户的角色是:"+ga.getAuthority()+"，授权数据相匹配");
                        }
                        return;
                    }
                }
            }
            throw new AccessDeniedException("没有权限");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
