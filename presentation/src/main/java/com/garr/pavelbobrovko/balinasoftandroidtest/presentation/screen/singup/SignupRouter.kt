package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.screen.singup

import android.widget.Toast
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base.BaseRouter
import com.garr.pavelbobrovko.domain.entity.SignUpData

class SignupRouter(activity: SignupActivity): BaseRouter<SignupActivity>(activity) {

    fun showResult(signUpData: SignUpData){
        Toast.makeText(activity,signUpData.toString(),Toast.LENGTH_SHORT).show()
    }
}