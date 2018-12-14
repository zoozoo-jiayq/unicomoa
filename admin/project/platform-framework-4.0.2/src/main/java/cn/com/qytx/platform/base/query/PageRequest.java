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
 * 分页请求实现类
 */
public class PageRequest extends AbstractPageRequest {

	private static final long serialVersionUID = -4541509938956089562L;

	private final Sort sort;

	/**
	 * 构造分页请求对象
	 * @param page 页码索引
	 * @param size 页面大小
	 */
	public PageRequest(int page, int size) {
		this(page, size, null);
	}

	/**
	 * 构造分页对象
	 * @param page 页码索引
	 * @param size 页面大小
	 * @param direction 排序规则，升序/降序
	 * @param properties 排序字段
	 */
	public PageRequest(int page, int size, Sort.Direction direction, String... properties) {
		this(page, size, new Sort(direction, properties));
	}

	/**
	 * 构造分页对象
	 * @param page 页码索引
	 * @param size 页面大小
	 * @param sort 排序
	 */
	public PageRequest(int page, int size, Sort sort) {
		super(page, size);
		this.sort = sort;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#getSort()
	 */
	public Sort getSort() {
		return sort;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#next()
	 */
	public Pageable next() {
		return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.AbstractPageRequest#previous()
	 */
	public PageRequest previous() {
		return getPageNumber() == 0 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.data.domain.Pageable#first()
	 */
	public Pageable first() {
		return new PageRequest(0, getPageSize(), getSort());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageRequest)) {
			return false;
		}

		PageRequest that = (PageRequest) obj;

		boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);

		return super.equals(that) && sortEqual;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 * super.hashCode() + (null == sort ? 0 : sort.hashCode());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(),
				sort == null ? null : sort.toString());
	}
}
