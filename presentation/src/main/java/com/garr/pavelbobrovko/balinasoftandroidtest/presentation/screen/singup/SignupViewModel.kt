package com.garr.pavelbobrovko.balinasoftandroidtest.presentation.screen.singup

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import com.garr.pavelbobrovko.balinasoftandroidtest.R
import com.garr.pavelbobrovko.balinasoftandroidtest.app.BalinaSoftTest
import com.garr.pavelbobrovko.balinasoftandroidtest.factory.UseCaseProvider
import com.garr.pavelbobrovko.balinasoftandroidtest.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy
import java.lang.Exception

class SignupViewModel: BaseViewModel<SignupRouter>() {

    val email = ObservableField<String>("")
    val pass = ObservableField<String>("")
    val inputType = ObservableInt(InputType.TYPE_TEXT_VARIATION_PASSWORD)

    private val emailList = ArrayList<String>()
    private var isListNotEmpty = false

    private val signUpUseCase = UseCaseProvider.provideSignUpUseCase()

    private val arrayAdapter = ArrayAdapter<String>(BalinaSoftTest.instance
        ,R.layout.autocomplete_adapter_item
            , emailList)

    val adapter = ObservableField<ArrayAdapter<String>>(arrayAdapter)

    init {
        arrayAdapter.setNotifyOnChange(true)
    }

    fun signUpClick(){
        val wrongEmail = checkEmail(email.get()?: "")
        if (wrongEmail && checkPass(pass.get()?:"")){
            signUpUseCase.signUp(email.get()!!,pass.get()!!)
                .subscribeBy (
                    onNext = {
                       router?.showResult(it)
                        Log.d("myLog", it.toString())
                    },
                    onError = {
                        router?.showError(it)// нет дизайна для показа ошибок.
                    }
                )
        }else if (wrongEmail){
            router?.showError(Exception("Wrong email"))
        }else router?.showError(Exception("Password can't be shorter than 8 symbols"))
    }

    private fun checkEmail(email: String): Boolean{
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    private fun checkPass(pass: String): Boolean{
        return pass.length >= 8
    }

    fun updateArrayAdapter(str: String): ArrayList<String>{

        if(str.endsWith("@",true)){

            val tempArr = str.split("@")
            val emailPath = tempArr[0]
            val emailArray = BalinaSoftTest.instance.resources.getStringArray(R.array.email_list)

            for (item in emailArray){
                emailList.add("$emailPath@$item")
            }

            arrayAdapter.clear()
            arrayAdapter.addAll(emailList)
            isListNotEmpty = true

        }else if (isListNotEmpty && !str.contains("@")){

            emailList.clear()
            arrayAdapter.clear()
            isListNotEmpty = false
        }

        return emailList
    }
}