package com.garr.pavelbobrovko.balinasoftandroidtest.factory

import com.garr.pavelbobrovko.balinasoftandroidtest.executor.UIThread
import com.garr.pavelbobrovko.data.net.SignUpRestService
import com.garr.pavelbobrovko.data.repositories.SignUpRepositoryImpl
import com.garr.pavelbobrovko.domain.usecases.SignUpUseCase

object UseCaseProvider {

    private val uiThread = UIThread()

    private const val signUpRestUrl = "http://junior.balinasoft.com/api/account/"

    fun provideSignUpUseCase(): SignUpUseCase{
        return SignUpUseCase(uiThread,SignUpRepositoryImpl(SignUpRestService(signUpRestUrl)))
    }
}