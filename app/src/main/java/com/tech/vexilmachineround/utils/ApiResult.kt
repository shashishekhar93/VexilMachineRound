package com.tech.vexilmachineround.utils

sealed class ApiResult<out T> {
    //loading state
    object Loading: ApiResult<Nothing>()

    //success with data
    data class Success<T>(val data:T) : ApiResult<T>()

    //Error with message
    data class Error(val message: String, val exception: Throwable? = null): ApiResult<Nothing>()

    //Nothing is Kotlin's "bottom type" - a special type that represents "no value" or "computation never returns

    //empty or no data
    object Empty : ApiResult<Nothing>()


}