package com.nextspringkart.userservice.controller

import com.nextspringkart.userservice.dto.request.AddressRequest
import com.nextspringkart.userservice.dto.request.LoginRequest
import com.nextspringkart.userservice.dto.request.RegisterRequest
import com.nextspringkart.userservice.dto.request.UpdateProfileRequest
import com.nextspringkart.userservice.dto.response.AddressResponse
import com.nextspringkart.userservice.dto.response.UserResponse
import com.nextspringkart.userservice.service.AuthService
import com.nextspringkart.userservice.service.UserService
import com.nextspringkart.userservice.util.SecurityUtils
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val authService: AuthService,
    private val userService: UserService,
    private val healthCheckController: HealthCheckController
) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserResponse> {
        val response = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest) = authService.login(request)


    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    fun getProfile(): ResponseEntity<UserResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        val userProfile = userService.getUserProfile(userId)
        return ResponseEntity.ok(userProfile)
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    fun updateProfile(@Valid @RequestBody request: UpdateProfileRequest): ResponseEntity<UserResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        val updatedProfile = userService.updateProfile(userId, request)
        return ResponseEntity.ok(updatedProfile)
    }

    @GetMapping("/addresses")
    @PreAuthorize("hasRole('USER')")
    fun getAddresses(): ResponseEntity<List<AddressResponse>> {
        val userId = SecurityUtils.getCurrentUserId()
        val addresses = userService.getUserAddresses(userId)
        return ResponseEntity.ok(addresses)
    }

    @PostMapping("/addresses")
    @PreAuthorize("hasRole('USER')")
    fun addAddress(@Valid @RequestBody request: AddressRequest): ResponseEntity<AddressResponse> {
        val userId = SecurityUtils.getCurrentUserId()
        val address = userService.addAddress(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(address)
    }

    @GetMapping("/health")
    fun getHealthStatus() = healthCheckController.getHealthStatus()

    @GetMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    fun logout(): ResponseEntity<String> {
        return ResponseEntity.ok("Logged out successfully")
    }
}
