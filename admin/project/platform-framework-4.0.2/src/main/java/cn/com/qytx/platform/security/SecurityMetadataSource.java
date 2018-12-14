package cn.com.qytx.platform.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import cn.com.qytx.platform.org.domain.ModuleInfo;
import cn.com.qytx.platform.org.service.IModule;

/**
 * User: 黄普友
 * Date: 13-2-2
 * Time: 下午2:17
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private final static Logger LOGGER = Logger.getLogger(SecurityMetadataSource.class);
    /**模块信息*/
    @Resource(name = "moduleService")
    IModule moduleService;
    private  Map<String,Collection<ConfigAttribute>> moduleMap=null;

    /**
     * 加载方法，从数据库中读取资源
     */
    public void loadResourceDefine(){
        moduleMap=new HashMap<String, Collection<ConfigAttribute>>();
        List<ModuleInfo> moduleList=moduleService.getAllModule();
        for (ModuleInfo module : moduleList) {
            String moduleUrl=module.getUrl();
            String moduleFlag=module.getModuleCode();//模块编码
            if(StringUtils.isNotEmpty(moduleUrl)&&StringUtils.isNotEmpty(moduleFlag)){
                //处理url
                if(!moduleUrl.startsWith("/")){
                    moduleUrl="/"+moduleUrl;
                }
                Collection<ConfigAttribute> list =new ArrayList<ConfigAttribute>();
                list.add(new SecurityConfig(moduleFlag));
                moduleMap.put(moduleUrl,list);
            }
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url=((FilterInvocation)object).getRequestUrl();
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("正在访问的url是："+url);

        }

        if(moduleMap==null||moduleMap.size()==0){
            return null;
        }
        Collection<ConfigAttribute> moduleCollection=moduleMap.get(url);
        //假如url在module表中不存在，否则判断权限
        if(moduleCollection==null){
            return null;
        }else{
            return moduleCollection;
        }
    }

    /**
     * 获取所有权限配置属性
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttribute=new HashSet<ConfigAttribute>();
        if(moduleMap!=null){
            for(Map.Entry<String,Collection<ConfigAttribute>> entry:moduleMap.entrySet())
            {
                allAttribute.addAll(entry.getValue());
            }
        }
        return allAttribute;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
