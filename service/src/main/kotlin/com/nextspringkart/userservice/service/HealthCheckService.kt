package com.nextspringkart.userservice.service

import org.springframework.stereotype.Service

@Service
class HealthCheckService {
    fun getApplicationHealth(): String {
        return "User service is running ok"
    }
}
