package com.ithellam.common.utils;


import java.util.Objects;



import com.google.common.base.MoreObjects;

public class Tuple<T1, T2> {
    private final T1 value1;
    private final T2 value2;

    public static <U, V> Tuple<U, V> of(final U value1, final V value2) {
        return new Tuple<>(value1, value2);
    }

    public Tuple(final T1 value1, final T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getValue1() {
        return value1;
    }

    public T2 getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }

    @Override
    public boolean equals(final Object obj) {
        return ObjectComparator.withLeftHandSide(this)
        .withFieldComparison(Tuple::getValue1)
        .withFieldComparison(Tuple::getValue2)
        .compareTo(obj);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
        .add("value1", value1)
        .add("value2", value2)
        .toString();
    }
}
