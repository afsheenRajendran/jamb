package com.ithellam.common.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.google.common.collect.Lists;

/**
 * Utility for declaratively checking object equality based on field values.
 * Basic checks on {@code null} values and same class type are done
 * automatically.
 * <p>
 * The equality algorithm will declare the objects equal if any of the
 * conditions are met:
 * <ol>
 * <li>The references are the same ({@code ==} equality)</li>
 * <li>Both objects are {@code null}</li>
 * <li>Each comparison function evaluates to {@code true}</li>
 * </ol>
 * <p>
 * The example below outlines different ways to define comparison functions.
 *
 * <pre>
 * {@code
 * ObjectComparator.withLeftHandSide(myObj)
 *     .withFieldComparison(MyObject::getField)
 *     .withFieldComparison(myObj -> myObj.getNestedObject().getNestedField())
 *     .withComparison((left, right) -> left.getMap().keys().equals(right.getMap().getKeys()))
 *     .compareTo(otherObj);
 * }
 * </pre>
 * </p>
 * <p>
 * A comparator can be created and used without a bound LHS:
 *
 * <pre>
 * {
 *     &#64;code
 *     ObjectComparator<MyObject> comparator = ObjectComparator.<MyObject>create()
 *             .withFieldComparison(MyObject::getField);
 *     comparator.equals(myObj, otherObj);
 * }
 * </pre>
 *
 * This allows the stateless re-use of the comparator. {@code compareTo} is
 * basically readability sugar.
 * </p>
 *
 * @param <T>
 *            The type being checked
 */
public class ObjectComparator<T> {
    private final List<BiFunction<T, T, Boolean>> comparisons = Lists.newArrayList();
    private final T lhs;

    private ObjectComparator(final T lhs) {
        this.lhs = lhs;
    }

    private ObjectComparator() {
        this.lhs = null;
    }

    public static <T> ObjectComparator<T> create() {
        return new ObjectComparator<>();
    }

    public static <T> ObjectComparator<T> withLeftHandSide(final T lhs) {
        return new ObjectComparator<>(lhs);
    }

    public boolean compareTo(final Object rhs) {
        return this.equals(this.lhs, rhs);
    }

    /**
     * Compares the {@code lhs} and {@code rhs} parameters using all configured
     * comparison functions
     *
     * @param rhs
     *            The object being compared to
     * @return True if the objects are equal
     */
    @SuppressWarnings("unchecked")
    public boolean equals(final T lhs, final Object rhs) {
        if (lhs == rhs) {
            return true;
        }

        if (lhs == null || rhs == null || rhs.getClass() != lhs.getClass()) {
            return false;
        }

        final T castRhs = (T) rhs;

        return comparisons.stream().allMatch(comparison -> comparison.apply(lhs, castRhs));
    }

    /**
     * Adds a custom comparison which can do any arbitrary comparison of the left-
     * and right-hand sides
     *
     * @param comparisonFn
     *            The comparison function
     * @return The comparator for chaining
     */
    public ObjectComparator<T> withComparison(final BiFunction<T, T, Boolean> comparisonFn) {
        this.comparisons.add(comparisonFn);
        return this;
    }

    /**
     * Adds an {@code Object.equals} comparison on both sides using the
     * {@code fieldAccessor} to get the values
     *
     * @param fieldAccessor
     *            The lambda to access a field on each object being compared
     * @return The comparator for chaining
     */
    public ObjectComparator<T> withFieldComparison(final Function<T, Object> fieldAccessor) {
        return withComparison((left, right) -> Objects.equals(fieldAccessor.apply(left), fieldAccessor.apply(right)));
    }
}
