/*
 * Copyright 2008-2014 the original author or authors.
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
 * 分页查询结果的接口对象
 * @param <T>
 * @author Oliver Gierke
 */
public interface Page<T> extends Slice<T> {

	/**
	 * 总页数
	 * 
	 * @return the number of toral pages
	 */
	int getTotalPages();

	/**
	 * 总条目数
	 * 
	 * @return the total amount of elements
	 */
	long getTotalElements();

	/**
	 * 是否有上一页
	 * 
	 * @deprecated use {@link #hasPrevious()} instead.
	 * @return if there is a previous page
	 */
	@Deprecated
	boolean hasPreviousPage();

	/**
	 * 是否是第一页
	 * 
	 * @deprecated use {@link #isFirst()} instead.
	 * @return
	 */
	@Deprecated
	boolean isFirstPage();

	/**
	 * 是否有下一页
	 * 
	 * @deprecated use {@link #hasNext()} instead.
	 * @return if there is a next page
	 */
	@Deprecated
	boolean hasNextPage();

	/**
	 * 是否是最后一页
	 * 
	 * @deprecated use {@link #isLast()} instead.
	 * @return
	 */
	@Deprecated
	boolean isLastPage();
}
