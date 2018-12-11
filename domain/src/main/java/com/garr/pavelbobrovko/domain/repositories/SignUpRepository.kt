package com.garr.pavelbobrovko.domain.repositories

import com.garr.pavelbobrovko.domain.entity.RegistrationData
import com.garr.pavelbobrovko.domain.entity.SignUpData
import io.reactivex.Observable

interface SignUpRepository {

    fun signUp(registrationData: RegistrationData): Observable<SignUpData>
}