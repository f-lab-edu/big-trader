package com.flab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class BigTraderApplication

fun main(args: Array<String>) {
    runApplication<BigTraderApplication>(*args)
}
