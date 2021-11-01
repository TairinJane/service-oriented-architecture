package com.example.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PagesController {

    @GetMapping("/")
    fun index() = "index"
}