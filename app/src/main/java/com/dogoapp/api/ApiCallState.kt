package com.dogoapp.api

class ApiCallState<T> {
    private var data: T? = null
    private var conn = true
    private var isLoading = true

    fun setData(data: T?) {
        this.data = data
        isLoading = false
        conn = true
    }

    fun setConnError() {
        data = null
        isLoading = false
        conn = false
    }

    fun getData(): T? {
        return data
    }

    fun hasConn(): Boolean {
        return conn
    }

    fun isLoading(): Boolean {
        return isLoading
    }
}