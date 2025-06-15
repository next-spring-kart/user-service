package org.nextspringkart.userservice.service

import org.nextspringkart.userservice.dto.request.LoginRequest
import org.nextspringkart.userservice.dto.request.RegisterRequest
import org.nextspringkart.userservice.dto.response.AuthResponse
import org.nextspringkart.userservice.entity.User
import org.nextspringkart.userservice.exception.InvalidCredentialsException
import org.nextspringkart.userservice.exception.UserAlreadyExistsException
import org.nextspringkart.userservice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val userService: UserService
) {

    fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("User with email ${request.email} already exists")
        }

        val user = User(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber
        )

        val savedUser = userRepository.save(user)
        val token = jwtService.generateToken(savedUser)
        val expirationTime = jwtService.getExpirationTime()

        return AuthResponse(
            token = token,
            expiresIn = expirationTime,
            user = userService.mapToUserResponse(savedUser)
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw InvalidCredentialsException("Invalid email or password")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialsException("Invalid email or password")
        }

        if (!user.isActive) {
            throw InvalidCredentialsException("Account is deactivated")
        }

        val token = jwtService.generateToken(user)
        val expirationTime = jwtService.getExpirationTime()

        return AuthResponse(
            token = token,
            expiresIn = expirationTime,
            user = userService.mapToUserResponse(user)
        )
    }
}