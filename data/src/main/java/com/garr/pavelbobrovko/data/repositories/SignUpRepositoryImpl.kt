package com.garr.pavelbobrovko.data.repositories

import com.garr.pavelbobrovko.data.entity.toSignUpData
import com.garr.pavelbobrovko.data.net.SignUpRestService
import com.garr.pavelbobrovko.domain.entity.SignUpData
import com.garr.pavelbobrovko.domain.repositories.SignUpRepository
import io.reactivex.Observable

class SignUpRepositoryImpl(val apiSevice: SignUpRestService): SignUpRepository {

    override fun signUp(email: String, pass: String): Observable<SignUpData> {
        return apiSevice.signUp(email,pass)
                .map {
                    it.toSignUpData()
                }
    }
}