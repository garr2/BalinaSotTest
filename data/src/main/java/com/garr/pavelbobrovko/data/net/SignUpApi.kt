package com.garr.pavelbobrovko.data.net

import com.garr.pavelbobrovko.data.entity.SignUpRequest
import com.garr.pavelbobrovko.data.entity.SignUpResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignUpApi {

    @POST("signup")
    @Headers("Content-Type: application/json", "Accept: application/json" )
    fun signUp(@Body signUpRequest: SignUpRequest): Observable<SignUpResponse>
}