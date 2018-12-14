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
package cn.com.qytx.platform.utils;

import java.util.List;

/**
 * 树形对象接口
 */
public interface Treeable {

    /**
     * @return 返回节点ID
     */
    Integer getId();

    /**
     * @return 返回节点名称
     */
    String getName();

    /**
     * @return 返回节点类型
     */
    String getType();

    /**
     * @return 返回父节点
     */
    <T extends Treeable> T getParent();

    /**
     * @return 返回父节点的ID
     */
    Integer getParentId();

    /**
     * @return 返回父节点的名称
     */
    String getParentName();

    /**
     * @return 该节点的子节点
     */
    <T extends Treeable> List<T> getChildren();


}
