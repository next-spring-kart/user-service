package org.nextspringkart.userservice.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping
    fun getHealthStatus(): ResponseEntity<String> {
        return ResponseEntity.ok().body("Status is OK");
    }
}