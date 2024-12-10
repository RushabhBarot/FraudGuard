package com.Project.FraudGuard.Services;

import com.Project.FraudGuard.DTOs.UserDTO;
import com.Project.FraudGuard.Entities.UserEntity;
import com.Project.FraudGuard.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    // Create or update a user
    public UserDTO saveUser(UserDTO userDTO) {
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        UserEntity savedUser = userRepository.save(userEntity);
        return mapper.map(savedUser, UserDTO.class);
    }

    // Get a user by ID
    public Optional<UserDTO> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> mapper.map(user, UserDTO.class));
    }

    // Get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
