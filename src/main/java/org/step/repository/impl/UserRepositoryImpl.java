package org.step.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.rowMapper.UserMapper;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostConstruct
    private void postConstruct() {
        // Заполнение базы данных
    }

    @PreDestroy
    private void preDestroy() {
        // удаление всего с базы данных
    }

    @Override
    public User save(User user) {
//        entityManager.getTransaction().begin();
        entityManager.persist(user);
//        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public boolean delete(User user) {
        int update = entityManager.createQuery("delete from User u where u.id=:id")
                .setParameter("id", user.getId())
                .executeUpdate();
        return update != -1;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
//        TypedQuery<User> query = entityManager
//                .createQuery("select u from User u where u.username=:username", User.class);
//        query.setParameter("username", username);
//        User singleResult = query.getSingleResult();

        return entityManager.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }


    public Boolean findByUserWithJdbcTemplate(String username, String password) {
        String endocePassword = bCryptPasswordEncoder.encode(password);
        System.out.println(endocePassword);
        User user = jdbcTemplate.queryForObject("select * from users where username = ? ", new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getLong(1), rs.getString(2), rs.getString(3)
                ));

        boolean check = BCrypt.checkpw(password, user.getPassword());
        return check;
    }

}
