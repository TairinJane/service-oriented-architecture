package com.soa.routes.controllers

import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class PagesController {

    @Autowired
    private lateinit var eurekaClient: EurekaClient

    @Value("\${spring.application.name}")
    private val appName: String? = null

    @GetMapping("/routes")
    fun index() = "index"

    @GetMapping("/greeting")
    @ResponseBody
    fun greeting(): String {
        return "hello from ${eurekaClient.getApplication(appName).name}"
    }

}