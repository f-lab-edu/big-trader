package com.flab.bigtrader.stockexchange.presentation.dto

import com.flab.bigtrader.stockexchange.domain.StockExchangeEvent

data class StockExchangeMessage(
    val id: String,
    val name: String,
    val price: Long,
    val count: Long,
    val tradingType: TradingType,
) {
    fun toEvent(): StockExchangeEvent = StockExchangeEvent(
        id,
        name,
        price,
        count,
        tradingType,
    )
}

enum class TradingType {
    BUY,
    SELL;

    fun getReverseType(): TradingType = if (this == BUY) SELL else BUY
}
