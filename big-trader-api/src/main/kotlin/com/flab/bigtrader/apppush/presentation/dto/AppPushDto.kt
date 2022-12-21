package com.flab.bigtrader.apppush.presentation.dto

import com.flab.bigtrader.apppush.infrastructure.dto.AppPushSendEvent

data class AppPushRequest(
    val message: String
) {
    fun toAppPushSendEvent() = AppPushSendEvent(message = this.message)
}
