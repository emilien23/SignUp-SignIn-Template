package com.example.authorizationtemplate.domain.mapper;

public interface BaseObjectsMapper<T, V> {
    V map(T obj);
}
