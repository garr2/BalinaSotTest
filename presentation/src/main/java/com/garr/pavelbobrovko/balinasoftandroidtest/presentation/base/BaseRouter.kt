package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base

import android.support.v4.app.FragmentManager
import android.util.Log
import android.widget.Toast
import com.garr.pavelbobrovko.balinasoftandroidtest.R
import com.garr.pavelbobrovko.domain.entity.AppErrorType
import com.garr.pavelbobrovko.domain.entity.AppException
import io.reactivex.exceptions.CompositeException

abstract class BaseRouter<A : BaseActivity>(val activity: A) {

    fun goBack() {
        activity.onBackPressed()
    }

    fun showError(e: Throwable) {
        if(e is AppException) {

            val message: String
            when(e.errorType) {
                AppErrorType.VALIDATION_ERROR -> {
                    if (e.subInfo!=null){
                       message = activity.getString(R.string.first_path_of_validation_err)
                        for (item in e.subInfo!!){
                            var subInfo = activity.getString(R.string.last_path_validation_err)
                            subInfo = String.format(subInfo,item.field,item.message)
                            message + "\n" + subInfo
                        }
                    }else message = activity.getString(R.string.validation_error)
                }
                AppErrorType.SERVER_IS_NOT_AVAILABLE -> {
                    message =activity.getString(R.string.server_unavaliable)
                }
                else -> {
                    message =activity.getString(R.string.uknown_err)
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