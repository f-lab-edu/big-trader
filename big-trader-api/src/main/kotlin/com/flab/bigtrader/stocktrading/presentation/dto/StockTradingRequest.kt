package com.flab.bigtrader.stocktrading.presentation.dto

data class StockTradingRequest(
    val name: String,
    val price: Long,
    val tradingType: TradingType
)

enum class TradingType {
    BUY,
    Sell
}
