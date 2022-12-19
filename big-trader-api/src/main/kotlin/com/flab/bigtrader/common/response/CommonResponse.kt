package com.flab.bigtrader.common.response

data class CommonErrorResponse(
    val message: String,
) {
    companion object {
        @JvmStatic
        fun of(message: String) = CommonErrorResponse(message = message)
    }
}

data class CommonApiResponse<T>(
    val body: T,
    val message: String? = null,
) {
    companion object {
        @JvmStatic
        fun <T> of(body: T) = CommonApiResponse(body)

        @JvmStatic
        fun <T> of(body: T, message: String) = CommonApiResponse(body, message)
    }
}
