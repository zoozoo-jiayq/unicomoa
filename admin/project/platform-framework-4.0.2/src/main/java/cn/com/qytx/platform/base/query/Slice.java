/*
 * Copyright 2014 the original author or authors.
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

import java.util.List;

/**
 * A slice of data that indicates whether there's a next or previous slice available. Allows to obtain a
 * {@link Pageable} to request a previous or next {@link Slice}.
 * 
 * @author Oliver Gierke
 * @since 1.8
 */
 interface Slice<T> extends Iterable<T> {

	/**
	 * 当前页码
	 * 
	 * @return the number of the current {@link Slice}.
	 */
	int getNumber();

	/**
	 * 每页显示的条目数
	 * 
	 * @return the size of the {@link Slice}.
	 */
	int getSize();

	/**
	 * 当前显示的条目数量
	 * 
	 * @return the number of elements currently on this {@link Slice}.
	 */
	int getNumberOfElements();

	/**
	 * list结果集
	 * 
	 * @return
	 */
	List<T> getContent();

	/**
	 * 是否有结果
	 * 
	 * @return
	 */
	boolean hasContent();

	/**
	 * 排序对象
	 * 
	 * @return
	 */
	Sort getSort();

	/**
	 * 是否是第一页
	 * 
	 * @return
	 */
	boolean isFirst();

	/**
	 * 是否是最后一页
	 * 
	 * @return
	 */
	boolean isLast();

	/**
	 * 是否有下一页
	 * 
	 * @return if there is a next {@link Slice}.
	 */
	boolean hasNext();

	/**
	 * 是否有上一页
	 * 
	 * @return if there is a previous {@link Slice}.
	 */
	boolean hasPrevious();

	/**
     * 获取下一页的pageable对象，继承sort等条件
	 * Returns the {@link Pageable} to request the next {@link Slice}. Can be {@literal null} in case the current
	 * {@link Slice} is already the last one. Clients should check {@link #hasNext()} before calling this method to make
	 * sure they receive a non-{@literal null} value.
	 * 
	 * @return
	 */
	Pageable nextPageable();

	/**
     * 获取上一页的pageable对象，继承sort等条件
	 * Returns the {@link Pageable} to request the previous {@link Slice}. Can be {@literal null} in case the current
	 * {@link Slice} is already the first one. Clients should check {@link #hasPrevious()} before calling this method make
	 * sure receive a non-{@literal null} value.
	 * 
	 * @return
	 */
	Pageable previousPageable();
}
