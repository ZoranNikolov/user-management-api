package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.UserNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE (single)
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        return UserMapper.toDTO(userRepository.save(user));
    }

    // CREATE (bulk)
    public List<UserDTO> createUsers(List<UserDTO> userDTOs) {
        List<User> users = userDTOs.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());

        return userRepository.saveAll(users)
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    // READ (single)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toDTO(user);
    }

    // READ (all with search)
    public Page<UserDTO> getAllUsers(String searchTerm, Pageable pageable) {
        Page<User> users;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            users = userRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailAddressContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
                            searchTerm, searchTerm, searchTerm, searchTerm, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }
        return users.map(UserMapper::toDTO);
    }

    // UPDATE
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUserDTO.getFirstName());
                    user.setLastName(updatedUserDTO.getLastName());
                    user.setDateOfBirth(updatedUserDTO.getDateOfBirth());
                    user.setPhoneNumber(updatedUserDTO.getPhoneNumber());
                    user.setEmailAddress(updatedUserDTO.getEmail());
                    return UserMapper.toDTO(userRepository.save(user));
                })
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // DELETE
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}