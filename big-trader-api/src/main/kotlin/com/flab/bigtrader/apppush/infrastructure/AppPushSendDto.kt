package com.flab.bigtrader.apppush.infrastructure

data class AppPushSendEvent(
    val message: String
)

data class AppPushSendResult(
    val count: Long
)
