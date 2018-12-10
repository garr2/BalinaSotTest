package com.garr.pavelbobrovko.data.entity

data class SignUpResponse(val data: Response){
    data class Response(val login: String = "", val token: String = "", val userId: Int = 0)
}