package com.flab.bigtrader.stockexchange.presentation.dto

import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent

data class StockExchangeMessage(
    val name: String,
    val price: Long,
    val tradingType: TradingType
) {
    fun toEvent(id: String) = StockExchangeEvent(
        id,
        this.name,
        this.price,
        this.tradingType
    )
}

enum class TradingType {
    BUY,
    SELL
}
