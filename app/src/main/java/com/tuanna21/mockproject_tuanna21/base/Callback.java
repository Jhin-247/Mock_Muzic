package com.tuanna21.mockproject_tuanna21.base;

public interface Callback<T> {
    void success(T data);

    void error(Exception exception);

}
