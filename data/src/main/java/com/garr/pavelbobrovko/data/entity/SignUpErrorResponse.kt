package com.garr.pavelbobrovko.data.entity

import com.garr.pavelbobrovko.domain.entity.ErrorSubInfo

data class SignUpErrorResponse(val status: Int = 0, val error: String = "", val valid: Array<ErrorSubInfo>? = null){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SignUpErrorResponse

        if (status != other.status) return false
        if (error != other.error) return false
        if (valid != null) {
            if (other.valid == null) return false
            if (!valid.contentEquals(other.valid)) return false
        } else if (other.valid != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + error.hashCode()
        result = 31 * result + (valid?.contentHashCode() ?: 0)
        return result
    }


}