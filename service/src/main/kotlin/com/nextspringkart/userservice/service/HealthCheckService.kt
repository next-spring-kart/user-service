package com.nextspringkart.userservice.service

import org.springframework.stereotype.Service

@Service
class HealthCheckService {
    fun getApplicationHealth() = "User service is running ok"
}
