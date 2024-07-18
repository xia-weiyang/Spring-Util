package com.jiushig.springutil;

public interface HttpPredicate<T> {

    boolean predicate(T t, HttpUtil.Builder builder);
}
