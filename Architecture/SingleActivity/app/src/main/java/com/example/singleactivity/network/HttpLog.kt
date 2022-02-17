package com.example.singleactivity.network.service

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.enqueueWithLog(
    onSuccess: (Response<T>) -> Unit,
    onFailure: (Call<T>) -> Unit
) {
    val TAG_HTTP = "HTTP Log"
//    ProgressManager.current.showProgressDialog()  Show loading animation
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            handleOnResponse(call, response)
            //ProgressManager.current.dismissProgressDialog()
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            //ProgressManager.current.dismissProgressDialog()
            onFailure(call)
            Log.e(
                TAG_HTTP,
                "-------------------------FAILURE----------------------------------"
            )
            Log.e(TAG_HTTP, "Url:" + call.request().url)
            Log.e(TAG_HTTP, "Url:" + call.request().headers)
            Log.e(TAG_HTTP, "Message:" + t.message)
            Log.e(TAG_HTTP, "----------------------------------------------------------------")
        }

        private fun handleOnResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                onSuccess.invoke(response)
                Log.d(
                    TAG_HTTP,
                    "---------------------------SUCCESS-------------------------------"
                )
                Log.d(TAG_HTTP, "Url:" + call.request().url)
                Log.d(TAG_HTTP, "StatusCode:" + response.code())
                Log.d(TAG_HTTP, "Response Body:" + response.body())
                Log.d(TAG_HTTP, "----------------------------------------------------------------")
            } else {
                Log.e(
                    TAG_HTTP,
                    "--------------------------NON_SUCCESS--------------------------------"
                )
                Log.e(TAG_HTTP, "Url:" + call.request().url)
                Log.e(TAG_HTTP, "Response Body:" + response.body())
                Log.e(TAG_HTTP, "----------------------------------------------------------------")
            }
        }
    })
}
