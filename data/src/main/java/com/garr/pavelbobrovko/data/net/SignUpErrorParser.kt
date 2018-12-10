package com.garr.pavelbobrovko.data.net

import android.util.Log
import com.garr.pavelbobrovko.data.entity.SignUpErrorResponse
import com.garr.pavelbobrovko.domain.entity.AppErrorType
import com.garr.pavelbobrovko.domain.entity.AppException
import com.google.gson.Gson
import io.reactivex.ObservableTransformer
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException

class SignUpErrorParser(private val gson: Gson) {

    fun <T> parseError(): ObservableTransformer<T,T>{
        return ObservableTransformer { observable->
            observable.onErrorReturn { throwable ->
                when(throwable){
                    is HttpException -> {
                        val errorBody = throwable.response().errorBody()?.string()
                        if (errorBody == null) {
                            throw AppException(AppErrorType.UNKNOWN)
                        }

                        var exception: AppException
                        try {
                            //здесь мы брасали AppException))
                            val errorObject = gson.fromJson<SignUpErrorResponse>(errorBody,
                                    SignUpErrorResponse::class.java)

                            when(errorObject.status){
                                400 -> {
                                    exception = AppException(AppErrorType.VALIDATION_ERROR, errorObject.valid)
                                }
                                else -> exception = AppException(AppErrorType.UNKNOWN)
                            }
                        }catch (e: Exception){
                            // а здесь мы его перехватывали т.к. он наследуется от Exception)
                           exception = AppException(AppErrorType.UNKNOWN)
                        }
                        throw exception
                    }
                    is SocketTimeoutException -> {
                        throw AppException(AppErrorType.SERVER_IS_NOT_AVAILABLE)
                    }
                    else -> {
                        throw AppException(AppErrorType.UNKNOWN)
                    }
                }
            }
        }
    }
}