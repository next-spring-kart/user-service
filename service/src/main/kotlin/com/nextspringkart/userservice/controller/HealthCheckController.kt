package com.nextspringkart.userservice.controller

import com.nextspringkart.userservice.service.HealthCheckService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController(
    private val healthCheckService: HealthCheckService
) {

    @GetMapping
    fun getHealthStatus() = healthCheckService.getApplicationHealth()

}
