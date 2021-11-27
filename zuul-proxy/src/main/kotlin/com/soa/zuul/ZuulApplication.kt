package com.soa.zuul

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
class ZuulApplication

fun main(args: Array<String>) {
    runApplication<ZuulApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}