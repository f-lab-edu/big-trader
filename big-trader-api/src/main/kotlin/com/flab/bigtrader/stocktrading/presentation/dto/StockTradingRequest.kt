package com.flab.bigtrader.stocktrading.presentation.dto

import com.flab.bigtrader.stocktrading.application.dto.StockTradingCommand

data class StockTradingRequest(
    val name: String,
    val price: Long,
    val count: Long,
    val tradingType: TradingType
) {
    fun toCommand() = StockTradingCommand(
        name = this.name,
        price = this.price,
        count = this.count,
        tradingType = this.tradingType
    )
}

enum class TradingType {
    BUY,
    SELL
}
