/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
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
 * #L%
 */
package org.prolobjectlink.db.jpa.criteria;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.jpa.criteria.predicate.JpaAndPredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaBetweenPredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaConjuntion;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaDisjunction;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaEqual;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaExist;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaGreaterEqual;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaGreaterThan;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsEmpty;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsFalsePredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsMember;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsNotEmpty;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsNotMember;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaIsTruePredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaLessEqual;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaLessThan;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaLike;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaNotEqual;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaNotLike;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaNotNullPredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaNotPredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaNullPredicate;
import org.prolobjectlink.db.jpa.criteria.predicate.JpaOrPredicate;
import org.prolobjectlink.db.util.JavaLists;
import org.prolobjectlink.db.util.JavaReflect;

public class JpaCriteriaBuilder extends JpaAbstractWrapper implements CriteriaBuilder {

	private final Metamodel metamodel;

	public JpaCriteriaBuilder(Metamodel metamodel) {
		this.metamodel = metamodel;
	}

	public CriteriaQuery<Object> createQuery() {
		return createQuery(Object.class);
	}

	public <T> CriteriaQuery<T> createQuery(Class<T> resultClass) {
		return new JpaCriteriaQuery<T>(null, metamodel, false, resultClass);
	}

	public CriteriaQuery<Tuple> createTupleQuery() {
		return new JpaCriteriaQuery<Tuple>(null, metamodel, false, Tuple.class);
	}

	public <T> CriteriaUpdate<T> createCriteriaUpdate(Class<T> targetEntity) {
		return new JpaCriteriaUpdate<T>(targetEntity, metamodel);
	}

	public <T> CriteriaDelete<T> createCriteriaDelete(Class<T> targetEntity) {
		return new JpaCriteriaDelete<T>(targetEntity, null, metamodel);
	}

	public <Y> CompoundSelection<Y> construct(Class<Y> resultClass, Selection<?>... selections) {
		return new JpaCompoundSelection<Y>(null, resultClass, null, selections);
	}

	public CompoundSelection<Tuple> tuple(Selection<?>... selections) {
		return new JpaCompoundSelection<Tuple>(null, Tuple.class, null, selections);
	}

	public CompoundSelection<Object[]> array(Selection<?>... selections) {
		return new JpaCompoundSelection<Object[]>(null, Object[].class, null, selections);
	}

	public Order asc(Expression<?> x) {
		return new JpaOrder(x, true);
	}

	public Order desc(Expression<?> x) {
		return new JpaOrder(x, false);
	}

	public <N extends Number> Expression<Double> avg(Expression<N> x) {
		return new JpaAvg<Double>(x.getAlias(), Double.class, x, metamodel);
	}

	public <N extends Number> Expression<N> sum(Expression<N> x) {
		return new JpaSum<N>(x.getAlias(), (Class<? extends N>) Number.class, x, metamodel);
	}

	public Expression<Long> sumAsLong(Expression<Integer> x) {
		return new JpaSum<Long>(x.getAlias(), Long.class, x, metamodel);
	}

	public Expression<Double> sumAsDouble(Expression<Float> x) {
		return new JpaSum<Double>(x.getAlias(), Double.class, x, metamodel);
	}

	public <N extends Number> Expression<N> max(Expression<N> x) {
		return new JpaMax<N>(x.getAlias(), (Class<? extends N>) Number.class, x, metamodel);
	}

	public <N extends Number> Expression<N> min(Expression<N> x) {
		return new JpaMin<N>(x.getAlias(), (Class<? extends N>) Number.class, x, metamodel);
	}

	public <X extends Comparable<? super X>> Expression<X> greatest(Expression<X> x) {
		return new JpaGreatest<X>(null, x.getJavaType(), x, metamodel);
	}

	public <X extends Comparable<? super X>> Expression<X> least(Expression<X> x) {
		return new JpaLeast<X>(null, x.getJavaType(), x, metamodel);
	}

	public Expression<Long> count(Expression<?> x) {
		return new JpaCount<Long>(x.getAlias(), Long.class, x, metamodel);
	}

	public Expression<Long> countDistinct(Expression<?> x) {
		return new JpaCountDistinct<Long>(x.getAlias(), Long.class, x, metamodel);
	}

	public Predicate exists(Subquery<?> subquery) {
		return new JpaExist(null, Boolean.class, subquery, metamodel, null, newList());
	}

	public <Y> Expression<Y> all(Subquery<Y> subquery) {
		return new JpaAll<Y>(null, (Class<? extends Y>) Object.class, subquery, metamodel);
	}

	public <Y> Expression<Y> some(Subquery<Y> subquery) {
		return new JpaSome<Y>(null, (Class<? extends Y>) Object.class, subquery, metamodel);
	}

	public <Y> Expression<Y> any(Subquery<Y> subquery) {
		return new JpaAny<Y>(null, (Class<? extends Y>) Object.class, subquery, metamodel);
	}

	public Predicate and(Expression<Boolean> x, Expression<Boolean> y) {
		return and(x, y);
	}

	public Predicate and(Predicate... restrictions) {
		List<Expression<?>> exps = JavaLists.arrayList();
		for (Predicate predicate : restrictions) {
			exps.add(predicate);
		}
		return new JpaAndPredicate("", Boolean.class, null, metamodel, exps);
	}

	public Predicate or(Expression<Boolean> x, Expression<Boolean> y) {
		return or(x, y);
	}

	public Predicate or(Predicate... restrictions) {
		return new JpaOrPredicate("", Boolean.class, null, metamodel, newList(restrictions));
	}

	public Predicate not(Expression<Boolean> restriction) {
		return new JpaNotPredicate("", Boolean.class, restriction, metamodel, newList());
	}

	public Predicate conjunction() {
		return new JpaConjuntion(null, Boolean.class, null, metamodel, newList());
	}

	public Predicate disjunction() {
		return new JpaDisjunction(null, Boolean.class, null, metamodel, newList());
	}

	public Predicate isTrue(Expression<Boolean> x) {
		return new JpaIsTruePredicate(x.getAlias(), Boolean.class, x, metamodel, null, null);
	}

	public Predicate isFalse(Expression<Boolean> x) {
		return new JpaIsFalsePredicate(x.getAlias(), Boolean.class, x, metamodel, null, null);
	}

	public Predicate isNull(Expression<?> x) {
		return new JpaNullPredicate(null, Boolean.class, x, metamodel, null, null);
	}

	public Predicate isNotNull(Expression<?> x) {
		return new JpaNotNullPredicate(null, Boolean.class, x, metamodel, null, null);
	}

	public Predicate equal(Expression<?> x, Expression<?> y) {
		return new JpaEqual("", Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate equal(Expression<?> x, Object y) {
		return new JpaEqual("", Boolean.class, null, metamodel, newList(x, new JpaObject<Object>(y, Object.class)));
	}

	public Predicate notEqual(Expression<?> x, Expression<?> y) {
		return new JpaNotEqual("", Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate notEqual(Expression<?> x, Object y) {
		return new JpaNotEqual("", Boolean.class, null, metamodel, newList(x, new JpaObject<Object>(y, Object.class)));
	}

	public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x,
			Expression<? extends Y> y) {
		return new JpaGreaterThan("", Boolean.class, null, metamodel, newList(x, y));
	}

	public <Y extends Comparable<? super Y>> Predicate greaterThan(Expression<? extends Y> x, Y y) {
		return new JpaGreaterThan("", Boolean.class, null, metamodel,
				newList(x, new JpaObject(y, JavaReflect.classOf(y))));
	}

	public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x,
			Expression<? extends Y> y) {
		return new JpaGreaterEqual("", Boolean.class, null, metamodel, newList(x, y));
	}

	public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Expression<? extends Y> x, Y y) {
		return new JpaGreaterEqual("", Boolean.class, null, metamodel,
				newList(x, new JpaObject(y, JavaReflect.classOf(y))));
	}

	public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Expression<? extends Y> y) {
		return new JpaLessThan("", Boolean.class, null, metamodel, newList(x, y));
	}

	public <Y extends Comparable<? super Y>> Predicate lessThan(Expression<? extends Y> x, Y y) {
		return new JpaLessThan("", Boolean.class, null, metamodel,
				newList(x, new JpaObject(y, JavaReflect.classOf(y))));
	}

	public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x,
			Expression<? extends Y> y) {
		return new JpaLessEqual("", Boolean.class, null, metamodel, newList(x, y));
	}

	public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Expression<? extends Y> x, Y y) {
		return new JpaLessEqual("", Boolean.class, null, metamodel,
				newList(x, new JpaObject(y, JavaReflect.classOf(y))));
	}

	public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Expression<? extends Y> x,
			Expression<? extends Y> y) {
		return new JpaBetweenPredicate(null, Boolean.class, v, metamodel, null, newList(x, y));
	}

	public <Y extends Comparable<? super Y>> Predicate between(Expression<? extends Y> v, Y x, Y y) {
		return new JpaBetweenPredicate(null, Boolean.class, v, metamodel, null,
				newList(new JpaObject<Y>(x, JavaReflect.classOf(x)), new JpaObject<Y>(y, JavaReflect.classOf(y))));
	}

	public Predicate gt(Expression<? extends Number> x, Expression<? extends Number> y) {
		return new JpaGreaterThan(null, Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate gt(Expression<? extends Number> x, Number y) {
		return new JpaGreaterThan(null, Boolean.class, null, metamodel,
				newList(x, new JpaObject<Number>(y, Number.class)));
	}

	public Predicate ge(Expression<? extends Number> x, Expression<? extends Number> y) {
		return new JpaGreaterEqual(null, Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate ge(Expression<? extends Number> x, Number y) {
		return new JpaGreaterEqual(null, Boolean.class, null, metamodel,
				newList(x, new JpaObject<Number>(y, Number.class)));
	}

	public Predicate lt(Expression<? extends Number> x, Expression<? extends Number> y) {
		return new JpaLessThan(null, Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate lt(Expression<? extends Number> x, Number y) {
		return new JpaLessThan(null, Boolean.class, null, metamodel,
				newList(x, new JpaObject<Number>(y, Number.class)));
	}

	public Predicate le(Expression<? extends Number> x, Expression<? extends Number> y) {
		return new JpaLessEqual(null, Boolean.class, null, metamodel, newList(x, y));
	}

	public Predicate le(Expression<? extends Number> x, Number y) {
		return new JpaLessEqual(null, Boolean.class, null, metamodel,
				newList(x, new JpaObject<Number>(y, Number.class)));
	}

	public <N extends Number> Expression<N> neg(Expression<N> x) {
		return new JpaNegative<N>(null, (Class<? extends N>) Number.class, x, metamodel);
	}

	public <N extends Number> Expression<N> abs(Expression<N> x) {
		return new JpaAbs<N>(x.getAlias(), (Class<? extends N>) Number.class, x, metamodel);
	}

	public <N extends Number> Expression<N> sum(Expression<? extends N> x, Expression<? extends N> y) {
		return new JpaPlus<N>(null, (Class<? extends N>) Number.class, x, y, metamodel);
	}

	public <N extends Number> Expression<N> sum(Expression<? extends N> x, N y) {
		return new JpaPlus<N>(null, (Class<? extends N>) Number.class, x,
				new JpaObject<N>(y, (Class<? extends N>) Number.class), metamodel);
	}

	public <N extends Number> Expression<N> sum(N x, Expression<? extends N> y) {
		return new JpaPlus<N>(null, (Class<? extends N>) Number.class,
				new JpaObject<N>(x, (Class<? extends N>) Number.class), y, metamodel);
	}

	public <N extends Number> Expression<N> prod(Expression<? extends N> x, Expression<? extends N> y) {
		return new JpaTimes<N>(null, (Class<? extends N>) Number.class, x, y, metamodel);
	}

	public <N extends Number> Expression<N> prod(Expression<? extends N> x, N y) {
		return new JpaTimes<N>(null, (Class<? extends N>) Number.class, x,
				new JpaObject<N>(y, (Class<? extends N>) Number.class), metamodel);
	}

	public <N extends Number> Expression<N> prod(N x, Expression<? extends N> y) {
		return new JpaTimes<N>(null, (Class<? extends N>) Number.class,
				new JpaObject<N>(x, (Class<? extends N>) Number.class), y, metamodel);
	}

	public <N extends Number> Expression<N> diff(Expression<? extends N> x, Expression<? extends N> y) {
		return new JpaMinus<N>(null, (Class<? extends N>) Number.class, x, y, metamodel);
	}

	public <N extends Number> Expression<N> diff(Expression<? extends N> x, N y) {
		return new JpaMinus<N>(null, (Class<? extends N>) Number.class, x,
				new JpaObject<N>(y, (Class<? extends N>) Number.class), metamodel);
	}

	public <N extends Number> Expression<N> diff(N x, Expression<? extends N> y) {
		return new JpaMinus<N>(null, (Class<? extends N>) Number.class,
				new JpaObject<N>(x, (Class<? extends N>) Number.class), y, metamodel);
	}

	public Expression<Number> quot(Expression<? extends Number> x, Expression<? extends Number> y) {
		return new JpaDiv<Number>(null, Number.class, x, y, metamodel);
	}

	public Expression<Number> quot(Expression<? extends Number> x, Number y) {
		return new JpaDiv<Number>(null, Number.class, x, new JpaObject<Number>(y, Number.class), metamodel);
	}

	public Expression<Number> quot(Number x, Expression<? extends Number> y) {
		return new JpaDiv<Number>(null, Number.class, new JpaObject<Number>(x, Number.class), y, metamodel);
	}

	public Expression<Integer> mod(Expression<Integer> x, Expression<Integer> y) {
		return new JpaMod<Integer>(null, Integer.class, x, y, metamodel);
	}

	public Expression<Integer> mod(Expression<Integer> x, Integer y) {
		return new JpaMod<Integer>(null, Integer.class, x, new JpaObject<Integer>(y, Integer.class), metamodel);
	}

	public Expression<Integer> mod(Integer x, Expression<Integer> y) {
		return new JpaMod<Integer>(null, Integer.class, new JpaObject<Integer>(x, Integer.class), y, metamodel);
	}

	public Expression<Double> sqrt(Expression<? extends Number> x) {
		return new JpaSqrt<Double>(null, Double.class, x, metamodel);
	}

	public Expression<Long> toLong(Expression<? extends Number> number) {
		return new JpaTypeCast<Long>(null, Long.class, number, metamodel);
	}

	public Expression<Integer> toInteger(Expression<? extends Number> number) {
		return new JpaTypeCast<Integer>(null, Integer.class, number, metamodel);
	}

	public Expression<Float> toFloat(Expression<? extends Number> number) {
		return new JpaTypeCast<Float>(null, Float.class, number, metamodel);
	}

	public Expression<Double> toDouble(Expression<? extends Number> number) {
		return new JpaTypeCast<Double>(null, Double.class, number, metamodel);
	}

	public Expression<BigDecimal> toBigDecimal(Expression<? extends Number> number) {
		return new JpaTypeCast<BigDecimal>(null, BigDecimal.class, number, metamodel);
	}

	public Expression<BigInteger> toBigInteger(Expression<? extends Number> number) {
		return new JpaTypeCast<BigInteger>(null, BigInteger.class, number, metamodel);
	}

	public Expression<String> toString(Expression<Character> character) {
		return new JpaTypeCast<String>(null, String.class, character, metamodel);
	}

	public <T> Expression<T> literal(T value) {
		Class<T> cls = JavaReflect.classOf(value);
		JpaObject<T> obj = new JpaObject<T>(value, cls);
		return new JpaLiteral<T>(null, cls, obj, metamodel);
	}

	public <T> Expression<T> nullLiteral(Class<T> resultClass) {
		return new JpaLiteral<T>(null, (Class<? extends T>) Object.class, null, metamodel);
	}

	public <T> ParameterExpression<T> parameter(Class<T> paramClass) {
		return new JpaParameterExpression(null, paramClass, null, metamodel, 0);
	}

	public <T> ParameterExpression<T> parameter(Class<T> paramClass, String name) {
		return new JpaParameterExpression(name, paramClass, null, metamodel, 0);
	}

	public <C extends Collection<?>> Predicate isEmpty(Expression<C> collection) {
		return new JpaIsEmpty(null, Boolean.class, collection, metamodel, null, newList());
	}

	public <C extends Collection<?>> Predicate isNotEmpty(Expression<C> collection) {
		return new JpaIsNotEmpty(null, Boolean.class, collection, metamodel, null, newList());
	}

	public <C extends Collection<?>> Expression<Integer> size(Expression<C> collection) {
		return new JpaSize<Integer>(null, Integer.class, collection, metamodel);
	}

	public <C extends Collection<?>> Expression<Integer> size(C collection) {
		return new JpaSize<Integer>(null, Integer.class, new JpaObject<C>(collection, JavaReflect.classOf(collection)),
				metamodel);
	}

	public <E, C extends Collection<E>> Predicate isMember(Expression<E> elem, Expression<C> collection) {
		return new JpaIsMember(null, Boolean.class, elem, metamodel, null, newList(collection));
	}

	public <E, C extends Collection<E>> Predicate isMember(E elem, Expression<C> collection) {
		return new JpaIsMember(null, Boolean.class, new JpaObject<E>(elem, JavaReflect.classOf(elem)), metamodel, null,
				newList(collection));
	}

	public <E, C extends Collection<E>> Predicate isNotMember(Expression<E> elem, Expression<C> collection) {
		return new JpaIsNotMember(null, Boolean.class, elem, metamodel, null, newList(collection));
	}

	public <E, C extends Collection<E>> Predicate isNotMember(E elem, Expression<C> collection) {
		return new JpaIsNotMember(null, Boolean.class, new JpaObject<E>(elem, (Class<? extends E>) Collection.class),
				metamodel, null, newList(collection));
	}

	public <V, M extends Map<?, V>> Expression<Collection<V>> values(M map) {
//	TODO	return new JpaValues<V>(null, (Class<? extends Collection<V>>) Object.class, null, metamodel, map);
		return null;
	}

	public <K, M extends Map<K, ?>> Expression<Set<K>> keys(M map) {
//	TODO	return new JpaKeys<K>(null, (Class<? extends Set<K>>) Object.class, null, metamodel, map);
		return null;
	}

	public Predicate like(Expression<String> x, Expression<String> pattern) {
		return new JpaLike(null, Boolean.class, null, metamodel, newList(x, pattern));
	}

	public Predicate like(Expression<String> x, String pattern) {
		return new JpaLike(null, Boolean.class, null, metamodel, newList(x, new JpaString(pattern)));
	}

	public Predicate like(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
		return new JpaLike(null, Boolean.class, null, metamodel, newList(x, pattern, escapeChar));
	}

	public Predicate like(Expression<String> x, Expression<String> pattern, char escapeChar) {
		return new JpaLike(null, Boolean.class, null, metamodel, newList(x, pattern, new JpaCharacter(escapeChar)));
	}

	public Predicate like(Expression<String> x, String pattern, Expression<Character> escapeChar) {
		return new JpaLike(null, Boolean.class, null, metamodel, newList(x, new JpaString(pattern), escapeChar));
	}

	public Predicate like(Expression<String> x, String pattern, char escapeChar) {
		return new JpaLike(null, Boolean.class, null, metamodel,
				newList(x, new JpaString(pattern), new JpaCharacter(escapeChar)));
	}

	public Predicate notLike(Expression<String> x, Expression<String> pattern) {
		return new JpaNotLike(null, Boolean.class, null, metamodel, newList(x, pattern));
	}

	public Predicate notLike(Expression<String> x, String pattern) {
		return new JpaNotLike(null, Boolean.class, null, metamodel, newList(x, new JpaString(pattern)));
	}

	public Predicate notLike(Expression<String> x, Expression<String> pattern, Expression<Character> escapeChar) {
		return new JpaNotLike(null, Boolean.class, null, metamodel, newList(x, pattern, escapeChar));
	}

	public Predicate notLike(Expression<String> x, Expression<String> pattern, char escapeChar) {
		return new JpaNotLike(null, Boolean.class, null, metamodel, newList(x, pattern, new JpaCharacter(escapeChar)));
	}

	public Predicate notLike(Expression<String> x, String pattern, Expression<Character> escapeChar) {
		return new JpaNotLike(null, Boolean.class, null, metamodel, newList(x, new JpaString(pattern), escapeChar));
	}

	public Predicate notLike(Expression<String> x, String pattern, char escapeChar) {
		return new JpaNotLike(null, Boolean.class, null, metamodel,
				newList(x, new JpaString(pattern), new JpaCharacter(escapeChar)));
	}

	public Expression<String> concat(Expression<String> x, Expression<String> y) {
		return new JpaConcat<String>(null, String.class, x, y, metamodel);
	}

	public Expression<String> concat(Expression<String> x, String y) {
		return new JpaConcat<String>(null, String.class, x, new JpaString(y), metamodel);
	}

	public Expression<String> concat(String x, Expression<String> y) {
		return new JpaConcat<String>(null, String.class, new JpaString(x), y, metamodel);
	}

	public Expression<String> substring(Expression<String> x, Expression<Integer> from) {
		return new JpaSubstring<String>(null, String.class, x, from, metamodel);
	}

	public Expression<String> substring(Expression<String> x, int from) {
		return new JpaSubstring<String>(null, String.class, x, new JpaInterger(from), metamodel);
	}

	public Expression<String> substring(Expression<String> x, Expression<Integer> from, Expression<Integer> len) {
		return new JpaSubstring<String>(null, String.class, x, from, len, metamodel);
	}

	public Expression<String> substring(Expression<String> x, int from, int len) {
		return new JpaSubstring<String>(null, String.class, x, new JpaInterger(from), new JpaInterger(len), metamodel);
	}

	public Expression<String> trim(Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, metamodel);
	}

	public Expression<String> trim(Trimspec ts, Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, ts, metamodel);
	}

	public Expression<String> trim(Expression<Character> t, Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, t, metamodel);
	}

	public Expression<String> trim(Trimspec ts, Expression<Character> t, Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, ts, t, metamodel);
	}

	public Expression<String> trim(char t, Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, new JpaCharacter(t), metamodel);
	}

	public Expression<String> trim(Trimspec ts, char t, Expression<String> x) {
		return new JpaTrim<String>(null, String.class, x, ts, new JpaCharacter(t), metamodel);
	}

	public Expression<String> lower(Expression<String> x) {
		return new JpaLower<String>(x.getAlias(), String.class, x, metamodel);
	}

	public Expression<String> upper(Expression<String> x) {
		return new JpaUpper<String>(x.getAlias(), String.class, x, metamodel);
	}

	public Expression<Integer> length(Expression<String> x) {
		return new JpaLength<Integer>(x.getAlias(), Integer.class, x, metamodel);
	}

	public Expression<Integer> locate(Expression<String> x, Expression<String> pattern) {
		return new JpaLocate<Integer>(null, Integer.class, x, pattern, metamodel);
	}

	public Expression<Integer> locate(Expression<String> x, String pattern) {
		return new JpaLocate<Integer>(null, Integer.class, x, new JpaString(pattern), metamodel);
	}

	public Expression<Integer> locate(Expression<String> x, Expression<String> pattern, Expression<Integer> from) {
		return new JpaLocate<Integer>(null, Integer.class, x, pattern, from, metamodel);
	}

	public Expression<Integer> locate(Expression<String> x, String pattern, int from) {
		return new JpaLocate<Integer>(null, Integer.class, x, new JpaString(pattern), new JpaInterger(from), metamodel);
	}

	public Expression<Date> currentDate() {
		Expression<?> date = new JpaDate(System.currentTimeMillis());
		return new JpaCurrentDate<Date>("", Date.class, date, metamodel);
	}

	public Expression<Timestamp> currentTimestamp() {
		Expression<?> timestap = new JpaTimestamp(System.currentTimeMillis());
		return new JpaCurrentTimestamp<Timestamp>("", Timestamp.class, timestap, metamodel);
	}

	public Expression<Time> currentTime() {
		Expression<?> time = new JpaTime(System.currentTimeMillis());
		return new JpaCurrentTime<Time>("", Time.class, time, metamodel);
	}

	public <T> In<T> in(Expression<? extends T> expression) {
		return new JpaIn(null, Object.class, expression, metamodel, null, newList());
	}

	public <Y> Expression<Y> coalesce(Expression<? extends Y> x, Expression<? extends Y> y) {
		return new JpaCoalecse<Y>(null, (Class<? extends Y>) Object.class, x, y, metamodel);
	}

	public <Y> Expression<Y> coalesce(Expression<? extends Y> x, Y y) {
		return coalesce(x, new JpaObject<Y>(y, (Class<? extends Y>) Object.class));
	}

	public <Y> Expression<Y> nullif(Expression<Y> x, Expression<?> y) {
		return new JpaNullIf<Y>(null, (Class<? extends Y>) Object.class, x, y, metamodel);
	}

	public <Y> Expression<Y> nullif(Expression<Y> x, Y y) {
		return nullif(x, new JpaObject<Y>(y, JavaReflect.classOf(y)));
	}

	public <T> Coalesce<T> coalesce() {
		return new JpaCoalecse(null, (Class<? extends T>) Object.class, metamodel);
	}

	public <C, R> SimpleCase<C, R> selectCase(Expression<? extends C> expression) {
		return new JpaSimpleCase(null, JavaReflect.classOf(expression.getJavaType()), expression, metamodel);
	}

	public <R> Case<R> selectCase() {
		return new JpaCase(null, (Class<? extends R>) Object.class, metamodel);
	}

	public <T> Expression<T> function(String name, Class<T> type, Expression<?>... args) {
		return new JpaFunction<T>(name, type, args, metamodel);
	}

	public <X, T, V extends T> Join<X, V> treat(Join<X, T> join, Class<V> type) {
		// TODO Auto-generated method stub
//		Set<Join<X, ?>> joins = JavaSets.linkedHashSet();
//		Set<Fetch<X, ?>> fetches = JavaSets.linkedHashSet();
//		return new JpaJoin<X, V>(null, type, null, metamodel, join, join.getModel(), metamodel.managedType(type), joins,
//				fetches, ((JpaJoin<?, ?>) join).isJoin, ((JpaJoin<?, ?>) join).isFetch, join.getJoinType());
		return null;
	}

	public <X, T, E extends T> CollectionJoin<X, E> treat(CollectionJoin<X, T> join, Class<E> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, T, E extends T> SetJoin<X, E> treat(SetJoin<X, T> join, Class<E> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, T, E extends T> ListJoin<X, E> treat(ListJoin<X, T> join, Class<E> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, K, T, V extends T> MapJoin<X, K, V> treat(MapJoin<X, K, T> join, Class<V> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, T extends X> Path<T> treat(Path<X> path, Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, T extends X> Root<T> treat(Root<X> root, Class<T> type) {
		// TODO Auto-generated method stub
//		Set<Join<T, ?>> joins = JavaSets.linkedHashSet();
//		Set<Join<T, ?>> fetches = JavaSets.linkedHashSet();
//		return new JpaRoot<T>(null, type, root, metamodel, root, model,
//				metamodel.managedType(type), joins, fetches, false, false, root);
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metamodel == null) ? 0 : metamodel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaCriteriaBuilder other = (JpaCriteriaBuilder) obj;
		if (metamodel == null) {
			if (other.metamodel != null)
				return false;
		} else if (!metamodel.equals(other.metamodel)) {
			return false;
		}
		return true;
	}

}
