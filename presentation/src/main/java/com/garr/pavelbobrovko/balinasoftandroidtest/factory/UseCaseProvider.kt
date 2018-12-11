package com.garr.pavelbobrovko.balinasoftandroidtest.factory

import com.garr.pavelbobrovko.balinasoftandroidtest.BuildConfig
import com.garr.pavelbobrovko.balinasoftandroidtest.executor.UIThread
import com.garr.pavelbobrovko.data.net.SignUpRestService
import com.garr.pavelbobrovko.data.repositories.SignUpRepositoryImpl
import com.garr.pavelbobrovko.domain.usecases.SignUpUseCase

object UseCaseProvider {

    private val uiThread = UIThread()

    private val signUpRestUrl = BuildConfig.SIGNUP_API_URL

    fun provideSignUpUseCase(): SignUpUseCase{
        return SignUpUseCase(uiThread,SignUpRepositoryImpl(SignUpRestService(signUpRestUrl)))
    }
}