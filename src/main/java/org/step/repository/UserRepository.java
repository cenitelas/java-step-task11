package org.step.repository;

import org.step.model.User;
import org.step.rowMapper.UserMapper;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface UserRepository<T extends User> {

    Optional<T> findByUsername(String username);

    T save(T user);

    boolean delete(T user);

    Optional<T> findById(Long id);

    List<User> findAll();

    T update(T user);

    Boolean findByUserWithJdbcTemplate(String username, String password);

}
