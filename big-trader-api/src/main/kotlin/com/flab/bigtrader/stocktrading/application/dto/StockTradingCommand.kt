package com.flab.bigtrader.stocktrading.application.dto

import com.flab.bigtrader.stocktrading.domain.StockTradingEvent
import com.flab.bigtrader.stocktrading.presentation.dto.TradingType

data class StockTradingCommand(
    val name: String,
    val price: Long,
    val count: Long,
    val tradingType: TradingType,
) {
    fun toEvent(id: String) = StockTradingEvent(
        id,
        this.name,
        this.price,
        this.count,
        tradingType
    )
}
