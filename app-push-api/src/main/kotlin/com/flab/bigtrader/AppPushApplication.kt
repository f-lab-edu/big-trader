package com.flab.bigtrader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppPushApplication

fun main(args: Array<String>) {
    runApplication<AppPushApplication>(*args)
}