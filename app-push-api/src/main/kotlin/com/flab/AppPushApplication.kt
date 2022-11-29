package com.flab

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class AppPushApplication

fun main(args: Array<String>) {
    runApplication<AppPushApplication>(*args)
}
