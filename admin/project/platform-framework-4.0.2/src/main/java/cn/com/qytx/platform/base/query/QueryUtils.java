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

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.com.qytx.platform.base.query.Sort.Order;

/**
 * Simple utility class to create JPA queries.
 * 
 * @author Oliver Gierke
 * @author Kevin Raymond
 * @author Thomas Darimont
 * @author Komi Innocent
 */
 public final class QueryUtils {

    //QUERY ALL
    public static final String FIND_ALL_QUERY_STRING = "from %s x";

	public static final String COUNT_QUERY_STRING = "select count(%s) as totalnumalias from %s x";
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x";

    //别名
	public static final String DEFAULT_ALIAS = "x";
	private static final String COUNT_REPLACEMENT_TEMPLATE = "select count(%s) $5$6$7";
	private static final String SIMPLE_COUNT_VALUE = "$2";
	private static final String COMPLEX_COUNT_VALUE = "$3$6";
	private static final String ORDER_BY_PART = "(?iu)\\s+order\\s+by\\s+.*$";

	private static final Pattern ALIAS_MATCH;
	private static final Pattern COUNT_MATCH;

	private static final String IDENTIFIER = "[\\p{Alnum}._$]+";
	private static final String IDENTIFIER_GROUP = String.format("(%s)", IDENTIFIER);

	private static final String LEFT_JOIN = "left (outer )?join " + IDENTIFIER + " (as )?" + IDENTIFIER_GROUP;
	private static final Pattern LEFT_JOIN_PATTERN = Pattern.compile(LEFT_JOIN, Pattern.CASE_INSENSITIVE);

	private static final String EQUALS_CONDITION_STRING = "%s.%s = :%s";
	private static final Pattern ORDER_BY = Pattern.compile(".*order\\s+by\\s+.*", CASE_INSENSITIVE);

	static {

		StringBuilder builder = new StringBuilder();
		builder.append("(?<=from)"); // from as starting delimiter
		builder.append("(?: )+"); // at least one space separating
		builder.append(IDENTIFIER_GROUP); // Entity name, can be qualified (any
		builder.append("(?: as)*"); // exclude possible "as" keyword
		builder.append("(?: )+"); // at least one space separating
		builder.append("(\\w*)"); // the actual alias

		ALIAS_MATCH = compile(builder.toString(), CASE_INSENSITIVE);

		builder = new StringBuilder();
		builder.append("(select\\s+((distinct )?(.+?)?)\\s+)?(from\\s+");
		builder.append(IDENTIFIER);
		builder.append("(?:\\s+as)?\\s+)");
		builder.append(IDENTIFIER_GROUP);
		builder.append("(.*)");

		COUNT_MATCH = compile(builder.toString(), CASE_INSENSITIVE);

	}

	/**
	 * Private constructor to prevent instantiation.
	 */
	private QueryUtils() {

	}

	/**
	 * Returns the query string to execute an exists query for the given id attributes.
	 * 
	 * @param entityName the name of the entity to create the query for, must not be {@literal null}.
	 * @param countQueryPlaceHolder the placeholder for the count clause, must not be {@literal null}.
	 * @param idAttributes the id attributes for the entity, must not be {@literal null}.
	 * @return
	 */
	public static String getExistsQueryString(String entityName, String countQueryPlaceHolder,
			Iterable<String> idAttributes) {

		StringBuilder sb = new StringBuilder(String.format(COUNT_QUERY_STRING, countQueryPlaceHolder, entityName));
		sb.append(" WHERE ");

		for (String idAttribute : idAttributes) {
			sb.append(String.format(EQUALS_CONDITION_STRING, "x", idAttribute, idAttribute));
			sb.append(" AND ");
		}

		sb.append("1 = 1");
		return sb.toString();
	}

	/**
	 * Returns the query string for the given class name.
	 * 
	 * @param template
	 * @param entityName
	 * @return
	 */
	public static String getQueryString(String template, String entityName) {

		Assert.hasText(entityName, "Entity name must not be null or empty!");

		return String.format(template, entityName);
	}

	/**
	 * Adds {@literal order by} clause to the JPQL query. Uses the {@link #DEFAULT_ALIAS} to bind the sorting property to.
	 * 
	 * @param query
	 * @param sort
	 * @return
	 */
	public static String applySorting(String query, Sort sort) {

		return applySorting(query, sort, DEFAULT_ALIAS);
	}

	/**
	 * Adds {@literal order by} clause to the JPQL query.
	 * 
	 * @param query
	 * @param sort
	 * @param alias
	 * @return
	 */
	public static String applySorting(String query, Sort sort, String alias) {

		Assert.hasText(query);

		if (null == sort || !sort.iterator().hasNext()) {
			return query;
		}

		StringBuilder builder = new StringBuilder(query);

		if (!ORDER_BY.matcher(query).matches()) {
			builder.append(" order by ");
		} else {
			builder.append(", ");
		}

		Set<String> aliases = getOuterJoinAliases(query);

		for (Order order : sort) {
			builder.append(getOrderClause(aliases, alias, order)).append(", ");
		}

		builder.delete(builder.length() - 2, builder.length());

		return builder.toString();
	}

	/**
	 * Returns the order clause for the given {@link Order}. Will prefix the clause with the given alias if the referenced
	 * property refers to a join alias.
	 * 
	 * @param joinAliases the join aliases of the original query.
	 * @param alias the alias for the root entity.
	 * @param order the order object to build the clause for.
	 * @return
	 */
	private static String getOrderClause(Set<String> joinAliases, String alias, Order order) {

		String property = order.getProperty();
		boolean qualifyReference = !property.contains("("); // ( indicates a function

		for (String joinAlias : joinAliases) {
			if (property.startsWith(joinAlias)) {
				qualifyReference = false;
				break;
			}
		}

		String reference = qualifyReference ? String.format("%s.%s", alias, property) : property;
		String wrapped = order.isIgnoreCase() ? String.format("lower(%s)", reference) : reference;

		return String.format("%s %s", wrapped, toJpaDirection(order));
	}

	/**
	 * Returns the aliases used for {@code left (outer) join}s.
	 * 
	 * @param query
	 * @return
	 */
	static Set<String> getOuterJoinAliases(String query) {

		Set<String> result = new HashSet<String>();
		Matcher matcher = LEFT_JOIN_PATTERN.matcher(query);

		while (matcher.find()) {

			String alias = matcher.group(3);
			if (StringUtils.hasText(alias)) {
				result.add(alias);
			}
		}

		return result;
	}

	private static String toJpaDirection(Order order) {
		return order.getDirection().name().toLowerCase(Locale.US);
	}

	/**
	 * Resolves the alias for the entity to be retrieved from the given JPA query.
	 * 
	 * @param query
	 * @return
	 */
	public static String detectAlias(String query) {

		Matcher matcher = ALIAS_MATCH.matcher(query);

		return matcher.find() ? matcher.group(2) : null;
	}

	/**
	 * Creates a where-clause referencing the given entities and appends it to the given query string. Binds the given
	 * entities to the query.
	 * 
	 * @param <T>
	 * @param queryString
	 * @param entities
	 * @param entityManager
	 * @return
	 */
	public static <T> Query applyAndBind(String queryString, Iterable<T> entities, EntityManager entityManager) {

		Assert.notNull(queryString);
		Assert.notNull(entities);
		Assert.notNull(entityManager);

		Iterator<T> iterator = entities.iterator();

		if (!iterator.hasNext()) {
			return entityManager.createQuery(queryString);
		}

		String alias = detectAlias(queryString);
		StringBuilder builder = new StringBuilder(queryString);
		builder.append(" where");

		int i = 0;

		while (iterator.hasNext()) {

			iterator.next();

			builder.append(String.format(" %s = ?%d", alias, ++i));

			if (iterator.hasNext()) {
				builder.append(" or");
			}
		}

		Query query = entityManager.createQuery(builder.toString());

		iterator = entities.iterator();
		i = 0;

		while (iterator.hasNext()) {
			query.setParameter(++i, iterator.next());
		}

		return query;
	}

	/**
	 * Creates a count projected query from the given original query.
	 * 
	 * @param originalQuery must not be {@literal null} or empty.
	 * @return
	 */
	public static String createCountQueryFor(String originalQuery) {
		return createCountQueryFor(originalQuery, null);
	}

	/**
	 * Creates a count projected query from the given original query.
	 * 
	 * @param originalQuery must not be {@literal null}.
	 * @param countProjection may be {@literal null}.
	 * @return
	 * @since 1.6
	 */
	public static String createCountQueryFor(String originalQuery, String countProjection) {

		Assert.hasText(originalQuery, "OriginalQuery must not be null or empty!");

		Matcher matcher = COUNT_MATCH.matcher(originalQuery);
		String countQuery = null;

		if (countProjection == null) {

			String variable = matcher.matches() ? matcher.group(4) : null;
			boolean useVariable = variable != null && StringUtils.hasText(variable) && !variable.startsWith("new")
					&& !variable.startsWith("count(") && !variable.contains(",");

			String replacement = useVariable ? SIMPLE_COUNT_VALUE : COMPLEX_COUNT_VALUE;
			countQuery = matcher.replaceFirst(String.format(COUNT_REPLACEMENT_TEMPLATE, replacement));
		} else {
			countQuery = matcher.replaceFirst(String.format(COUNT_REPLACEMENT_TEMPLATE, countProjection));
		}

		return countQuery.replaceFirst(ORDER_BY_PART, "");
	}

	/**
	 * Returns whether the given {@link Query} contains named parameters.
	 * 
	 * @param query
	 * @return
	 */
	public static boolean hasNamedParameter(Query query) {

		for (Parameter<?> parameter : query.getParameters()) {
			if (parameter.getName() != null) {
				return true;
			}
		}

		return false;
	}


	/**
	 * Executes a count query and transparently sums up all values returned.
	 * 
	 * @param query must not be {@literal null}.
	 * @return
	 */
	public static Long executeCountQuery(TypedQuery<Long> query) {

		Assert.notNull(query);

		List<Long> totals = query.getResultList();
		Long total = 0L;

		for (Long element : totals) {
			total += element == null ? 0 : element;
		}

		return total;
	}


}
