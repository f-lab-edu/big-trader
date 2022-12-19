package com.flab.bigtrader.stocktrading.application.dto

import com.flab.bigtrader.stocktrading.domain.StockTradingBuyEvent
import com.flab.bigtrader.stocktrading.presentation.dto.TradingType

data class StockTradingCommand(
    val name: String,
    val price: Long,
    val tradingType: TradingType,
) {
    fun toEvent(id: String) = StockTradingBuyEvent(
        id,
        this.name,
        this.price,
        tradingType
    )
}
