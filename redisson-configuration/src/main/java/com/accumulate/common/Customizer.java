package com.accumulate.common;

public interface Customizer<T> {
    /**
     * 自定义方法
     *
     * @param bean bean
     */
    void customize(T bean);
}
