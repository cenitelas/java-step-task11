package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.exception.NotFoundException;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.UserRepositorySpringData;
import org.step.security.Role;
import org.step.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<User> {

    private final UserRepository<User> userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositorySpringData userRepositorySpringData;

    @Autowired
    public UserServiceImpl(UserRepository<User> userRepository,
                           PasswordEncoder passwordEncoder,
                           UserRepositorySpringData userRepositorySpringData) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositorySpringData = userRepositorySpringData;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean save(User user, boolean isAdmin) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        String passwordAfterEncoding = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordAfterEncoding);

        if (isAdmin) {
            user.setAuthorities(Collections.singleton(Role.ROLE_ADMIN));
        } else {
            user.setAuthorities(Collections.singleton(Role.ROLE_USER));
        }
        User afterSaving = userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    @Override
    public User findById(Long id) {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<User> userById = userRepository.findById(id);

        if (userById.isPresent()) {
            return userById.get();
        } else {
            throw new IllegalStateException("User not found");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        if (user.getId() != null) {
            String passwordAfterEncoding = passwordEncoder.encode(user.getPassword());

            user.setPassword(passwordAfterEncoding);

            return userRepository.update(user);
        } else {
            throw new RuntimeException("User ID is null");
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepositorySpringData.findByUsernameCustom(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found", username)));
    }
}
