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
package cn.com.qytx.platform.base.datafilter;

import java.util.Collection;

/**
 * 数据权限包含的上下文信息,本接口中定义的常量字符串可以作为数据权限HQL中的变量占位符使用
 */
public interface FilterContext {

    /**
     * 登录用户id
     */
    String USER_ID = "userId";
    /**
     * 用户的单位ID
     */
    String USER_ORG_ID = "userOrgId";
    /**
     * 当前单位和上级单位的id集合
     */
    String USER_PARENT_DEPT_IDS = "userParentDeptIds";
    /**
     * 用户所属部门的id集合
     */
    String USER_DEPT_IDS = "userDeptIds";
    /**
     * 用户所属群组id集合
     */
    String USER_GROUP_IDS = "userGroupIds";
    /**
     * 用户默认部门id
     */
    String USER_DEFAULT_DEPT_ID = "groupId";
    /**
     * 用户所属部门和子部门的id集合
     */
    String USER_CHILD_DEPT_IDS = "userChildDeptIds";
    /**
     * 用户所属单位和子单位的id集合
     */
    String USER_CHILD_ORG_IDS = "userChildOrgIds";
    /**
     * 当前时间
     */
    String DATE_NOW = "dateNow";
    /**
     * 当前年
     */
    String DATE_YEAR = "dateYear";
    
    String IS_FORK_GROUP = "isForkGroup";

    /**
     * 获取用户具有的规则权限的jpql 列表
     * @param modelClass     操作的entityClass
     * @param dataFilterType 由用户传入，默认为"READ"
     * @param orgId 单位id
     * @return jpql 列表
     */
    public Collection<String> getFilterRuleJpqlList(final Class modelClass, final String dataFilterType,final Integer orgId);


}
