package com.garr.pavelbobrovko.data.entity

import com.garr.pavelbobrovko.domain.entity.SignUpData

fun SignUpResponse.toSignUpData(): SignUpData{
    return SignUpData(login = this.data.login, token = this.data.token, userId = this.data.userId)
}