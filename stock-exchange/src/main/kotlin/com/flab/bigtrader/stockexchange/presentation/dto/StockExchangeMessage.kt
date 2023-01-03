package com.flab.bigtrader.stockexchange.presentation.dto

import com.flab.bigtrader.stockexchange.domain.TradingStatus

data class StockExchangeMessage(
    val id: String,
    val name: String,
    val price: Long,
    val count: Long,
    val tradingType: TradingType,
    val tradingStatus: TradingStatus
) {
}

enum class TradingType {
    BUY,
    SELL
}
