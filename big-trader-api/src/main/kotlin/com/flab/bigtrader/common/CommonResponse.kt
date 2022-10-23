package com.flab.bigtrader.common

data class CommonErrorResponse(
        val message: String,
) {
    companion object {
        @JvmStatic
        fun of(message: String) = CommonErrorResponse(message = message)
    }
}
