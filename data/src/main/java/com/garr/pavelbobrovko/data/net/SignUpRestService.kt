package com.garr.pavelbobrovko.data.net

import com.garr.pavelbobrovko.data.entity.SignUpRequest
import com.garr.pavelbobrovko.data.entity.SignUpResponse
import com.google.gson.Gson
import io.reactivex.Observable

class SignUpRestService(apiUrl: String): RestService<SignUpErrorParser>(apiUrl) {

    private lateinit var restApi: SignUpApi

    override fun buildApiClass() {
        restApi = retrofit.create(SignUpApi::class.java)
    }

    override fun initErrorParser(gson: Gson): SignUpErrorParser {
        return SignUpErrorParser(gson)
    }

    fun signUp(email: String, pass: String): Observable<SignUpResponse>{
        return restApi.signUp(SignUpRequest(email,pass))
                .compose(errorParser.parseError())
    }
}