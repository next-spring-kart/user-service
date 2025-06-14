package org.nextspringkart.userservice.controller

import org.nextspringkart.userservice.service.HealthCheckService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController(private var healthCheckService: HealthCheckService) {
    @GetMapping
    fun getHealthStatus(): ResponseEntity<String> {
        val status = healthCheckService.getApplicationHealth()
        return ResponseEntity.ok().body(status)
    }
}
