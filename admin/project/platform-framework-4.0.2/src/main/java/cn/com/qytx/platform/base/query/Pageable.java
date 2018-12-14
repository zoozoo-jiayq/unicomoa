/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.qytx.platform.base.query;

/**
 * 分页查询请求的接口对象
 * @author Oliver Gierke
 */
public interface Pageable {

	/**
	 * 页码
	 * 
	 * @return the page to be returned.
	 */
	int getPageNumber();

	/**
	 * 每页显示数量
	 * 
	 * @return the number of items of that page
	 */
	int getPageSize();

	/**
     * 偏移量 page * size
	 * Returns the offset to be taken according to the underlying page and page size.
	 * 
	 * @return the offset to be taken
	 */
	int getOffset();

	/**
	 * 排序对象
	 * 
	 * @return
	 */
	Sort getSort();

	/**
	 * 下一页的pageable对象
	 * 
	 * @return
	 */
	Pageable next();

	/**
	 * 上一页的pageable对象
	 * 
	 * @return
	 */
	Pageable previousOrFirst();

	/**
	 * 第一页的pageable对象
	 * 
	 * @return
	 */
	Pageable first();

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	boolean hasPrevious();
}
