package com.ecommerce.ecommerce.commons.util;

public interface Mapper <S, T> {
    T toDto(S entity);
    S toEntity(T Dto);
}
