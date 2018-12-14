/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.qytx.platform.security;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import cn.com.qytx.platform.base.datafilter.FilterContext;
import cn.com.qytx.platform.org.domain.UserInfo;

import com.google.common.collect.Maps;

/**
 * 用户上下文模型实体
 * @author serv
 */
public class SessionVariable implements Serializable{

    private UserInfo user;

    private List<String> relationIds;
    
    private Map<String,Object> filterParameters =null;
    
    public SessionVariable(UserInfo user) {
        this.user = user;
        initFilterParameters();
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<String> getRelationIds() {
        return relationIds;
    }

    //TODO 登录后放入用户具有的所有关系id集合
    //用户ID 示例： user_123
    //部门id 示例:  dept_123
    //角色id 示例:  role_123
    //设置权限的时候需要设置关系id字符串规则也为这样
    public void setRelationIds(List<String> relationIds) {
        this.relationIds = relationIds;
    }

    private void initFilterParameters()
    {
    	filterParameters=Maps.newHashMap();
    	filterParameters.put(FilterContext.USER_ID, getUser().getUserId());// 34
        filterParameters.put(FilterContext.USER_ORG_ID, getUser().getCompanyId());//4343
        filterParameters.put(FilterContext.USER_PARENT_DEPT_IDS, null);//List<Integer> TODO 放入用户权限上下文
        filterParameters.put(FilterContext.USER_DEPT_IDS, null);//List<Integer>
//        filterParameters.put(FilterContext.USER_GROUP_IDS, null);
        filterParameters.put(FilterContext.USER_DEFAULT_DEPT_ID, null);//List<Integer>
        filterParameters.put(FilterContext.USER_CHILD_DEPT_IDS, null);//List<Integer>
//        filterParameters.put(FilterContext.USER_CHILD_ORG_IDS, null);
        filterParameters.put(FilterContext.DATE_NOW,new DateTime().toDate());
        filterParameters.put(FilterContext.DATE_YEAR,new DateTime().getYear());
        filterParameters.put(FilterContext.IS_FORK_GROUP,getUser().getIsForkGroup());
    }
    /**
     * 获取用户的变量map信息
     *
     * @return
     */
    public Map<String,Object> getFilterParameters(){
        return filterParameters;
    }
}
