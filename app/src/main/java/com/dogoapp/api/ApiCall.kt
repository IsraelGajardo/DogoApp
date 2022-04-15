package com.dogoapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall<T> {
    private val dataFormState: MutableLiveData<ApiCallState<T>> = MutableLiveData<ApiCallState<T>>()

    fun getDataFormState(): LiveData<ApiCallState<T>> {
        return dataFormState
    }

    fun apiCall(call: Call<T>) {
        val state: ApiCallState<T> = ApiCallState()
        dataFormState.value = state
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                Log.d("TAG", "resp " + response.toString());
                val newState: ApiCallState<T> = ApiCallState()
                if (response.code() == 200) {
                    newState.setData(response.body())
                    dataFormState.value = newState
                } else {
                    newState.setConnError()
                    dataFormState.value = newState
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                Log.d("TAG", "call error");
                val newState: ApiCallState<T> = ApiCallState()
                newState.setConnError()
                dataFormState.value = newState
            }
        })
    }
}