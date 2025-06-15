package org.nextspringkart.userservice.service

import org.nextspringkart.userservice.dto.request.AddressRequest
import org.nextspringkart.userservice.dto.request.UpdateProfileRequest
import org.nextspringkart.userservice.dto.response.AddressResponse
import org.nextspringkart.userservice.dto.response.UserResponse
import org.nextspringkart.userservice.entity.Address
import org.nextspringkart.userservice.entity.User
import org.nextspringkart.userservice.exception.ResourceNotFoundException
import org.nextspringkart.userservice.repository.AddressRepository
import org.nextspringkart.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val addressRepository: AddressRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found: $username")
    }

    fun getUserProfile(userId: Long): UserResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User not found with id: $userId")
        }
        return mapToUserResponse(user)
    }

    @Transactional
    fun updateProfile(userId: Long, request: UpdateProfileRequest): UserResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User not found with id: $userId")
        }

        val updatedUser = user.copy(
            firstName = request.firstName ?: user.firstName,
            lastName = request.lastName ?: user.lastName,
            phoneNumber = request.phoneNumber ?: user.phoneNumber
        )

        val savedUser = userRepository.save(updatedUser)
        return mapToUserResponse(savedUser)
    }

    fun getUserAddresses(userId: Long): List<AddressResponse> {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User not found with id: $userId")
        }

        return user.addresses.map { mapToAddressResponse(it) }
    }

    @Transactional
    fun addAddress(userId: Long, request: AddressRequest): AddressResponse {
        val user = userRepository.findById(userId).orElseThrow {
            ResourceNotFoundException("User not found with id: $userId")
        }

        // If this is the default address, remove default from others
        if (request.isDefault) {
            addressRepository.removeDefaultFromUserAddresses(userId)
        }

        val address = Address(
            addressLine1 = request.addressLine1,
            addressLine2 = request.addressLine2,
            city = request.city,
            state = request.state,
            postalCode = request.postalCode,
            country = request.country,
            isDefault = request.isDefault,
            user = user
        )

        val savedAddress = addressRepository.save(address)
        return mapToAddressResponse(savedAddress)
    }

    fun mapToUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            phoneNumber = user.phoneNumber,
            role = user.role,
            isActive = user.isActive,
            createdAt = user.createdAt,
            addresses = user.addresses.map { mapToAddressResponse(it) }
        )
    }

    private fun mapToAddressResponse(address: Address): AddressResponse {
        return AddressResponse(
            id = address.id,
            addressLine1 = address.addressLine1,
            addressLine2 = address.addressLine2,
            city = address.city,
            state = address.state,
            postalCode = address.postalCode,
            country = address.country,
            isDefault = address.isDefault
        )
    }
}