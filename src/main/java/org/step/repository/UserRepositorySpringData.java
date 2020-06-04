package org.step.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.step.dto.UserDTO;
import org.step.model.User;

import java.util.List;
import java.util.Optional;

import static org.step.model.User.USER_MESSAGE_LIST_QUERY;
import static org.step.model.User.USER_UPDATE_PASSWORD;

@Repository
public interface UserRepositorySpringData extends JpaRepository<User, Long> {

//    @EntityGraph(value = "User.messageList", type = EntityGraph.EntityGraphType.LOAD)
    @EntityGraph(attributePaths = {"messageList"}, type = EntityGraph.EntityGraphType.LOAD)
    User findByUsername(String username);

//    @Query(value = "select u from User u");
    @Query(nativeQuery = true, value = "SELECT * FROM USERS WHERE USERNAME = ?1 AND ID = ?2")
    List<User> findAllByСегодняПлохаяПогода(String username, Long id);

    @Query("select u from User u where u.username=?1")
    Optional<User> findByUsernameCustom(String username);

    @Modifying
    @Query(name = USER_UPDATE_PASSWORD)
    void updatePassword(String password);

    @Query(nativeQuery = true, value = "SELECT ID FROM USERS WHERE USERNAME = ?1")
    Long findIdOfUser(String username);

    /*
    Need to explicitly wrap username with % (%username%)
     */
    List<User> findAllByUsernameContains(String username);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM USERS WHERE USERNAME = %?1%",
            countQuery = "SELECT count(*) FROM USERS WHERE USERNAME = %?1%"
    )
    Page<User> findAllWithCountAndLikeUsername(String username, Pageable pageable);

    @Query("select LENGTH(u.username) as fn_len from User u where u.username like ?1%")
    List<Object[]> findByAsArraySort(String username, Sort sort);

    @Query("select u from User u where u.username like %:username%")
    User findByUsernameWithParam(@Param("username") String username);

    @Query("select u from #{#entityName} u where u.username = ?1")
    User findWithSpEL(String username);

//    @Procedure(name = "")
    @Query("select new org.step.dto.UserDTO(u.username) from User u where u.id=:id")
    UserDTO findByBlaBla(@Param("id") Long id);
}
