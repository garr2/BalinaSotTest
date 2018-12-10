package com.garr.pavelbobrovko.domain.entity

import java.lang.Exception

data class AppException(val errorType: AppErrorType, val subInfo: Array<ErrorSubInfo>? = null): Exception() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppException

        if (errorType != other.errorType) return false
        if (subInfo != null) {
            if (other.subInfo == null) return false
            if (!subInfo.contentEquals(other.subInfo)) return false
        } else if (other.subInfo != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = errorType.hashCode()
        result = 31 * result + (subInfo?.contentHashCode() ?: 0)
        return result
    }
}