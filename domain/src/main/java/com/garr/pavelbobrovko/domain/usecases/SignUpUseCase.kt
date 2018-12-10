package com.garr.pavelbobrovko.domain.usecases

import android.provider.ContactsContract
import com.garr.pavelbobrovko.domain.entity.SignUpData
import com.garr.pavelbobrovko.domain.executor.PostExecutorThread
import com.garr.pavelbobrovko.domain.repositories.SignUpRepository
import io.reactivex.Observable

class SignUpUseCase(postExecutorThread: PostExecutorThread
                    ,private val signUpRepository: SignUpRepository)
    : BaseUseCase(postExecutorThread) {

    fun signUp(email: String, pass: String): Observable<SignUpData>{
        return signUpRepository.signUp(email,pass)
                .observeOn(postExecutorThread)
                .subscribeOn(workExecutorThread)
    }
}