package com.garr.pavelbobrovko.domain.repositories

import com.garr.pavelbobrovko.domain.entity.SignUpData
import io.reactivex.Observable

interface SignUpRepository {

    fun signUp(email: String, pass: String): Observable<SignUpData>
}