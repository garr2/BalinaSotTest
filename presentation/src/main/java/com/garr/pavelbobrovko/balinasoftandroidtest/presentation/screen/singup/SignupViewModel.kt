package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.screen.singup

import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Patterns
import com.garr.pavelbobrovko.balinasoftandroidtest.factory.UseCaseProvider
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base.BaseViewModel
import com.garr.pavelbobrovko.domain.entity.RegistrationData
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception

class SignupViewModel: BaseViewModel<SignupRouter>() {

    val email = ObservableField<String>("")
    val pass = ObservableField<String>("")

    private val signUpUseCase = UseCaseProvider.provideSignUpUseCase()

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

    fun signUpClick(){
        val wrongEmail = checkEmail(email.get()?: "")
        if (wrongEmail && checkPass(pass.get()?:"")){
            val registrationData = RegistrationData(email.get()!!,pass.get()!!)
           val disposable = signUpUseCase.signUp(registrationData)
                .subscribeBy (
                    onNext = {
                       router?.showResult(it)
                    },
                    onError = {
                        router?.showError(it)// нет дизайна для показа ошибок.
                    }
                )
            addToDisposable(disposable)
        }else if (wrongEmail){
            router?.showError(Exception("Wrong email"))
        }else router?.showError(Exception("Password can't be shorter than 8 symbols"))
    }

    private fun checkEmail(email: String): Boolean{
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    private fun checkPass(pass: String): Boolean{
        return pass.length >= MIN_PASSWORD_LENGTH
    }
}