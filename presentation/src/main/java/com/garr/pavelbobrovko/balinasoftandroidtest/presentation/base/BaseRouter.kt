package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base

import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Toast
import com.garr.pavelbobrovko.domain.entity.AppErrorType
import com.garr.pavelbobrovko.domain.entity.AppException
import io.reactivex.exceptions.CompositeException

abstract class BaseRouter<A : BaseActivity>(val activity: A) {

    fun goBack() {
        activity.onBackPressed()
    }

    fun showError(e: Throwable) {
        if (e is CompositeException){
            Log.d("myLog", e.exceptions.toString())
        }

        if(e is AppException) {

            val message: String
            when(e.errorType) {
                AppErrorType.VALIDATION_ERROR -> {
                    if (e.subInfo!=null){
                       message = "errorType: Validation error. SubInfo:\n"
                        for (item in e.subInfo!!){
                            message + "field: ${item.field}, message: ${item.message}\n"
                        }
                    }else message = "Validation error"
                }
                AppErrorType.SERVER_IS_NOT_AVAILABLE -> {
                    message ="Server is not available"
                }
                else -> {
                    message ="Unknown error"
                }
            }

            Toast.makeText(activity, message,
                Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(activity, "Error " + e.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }
}