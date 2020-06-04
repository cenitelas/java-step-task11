package org.step.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.step.model.Message;
import org.step.model.User;

import java.util.List;

public interface MessageRepositorySpringData extends JpaRepository<Message, Long> {

    List<Message> findAllByUser_Id(Long id);

    Message findById(long id);

    List<Message> findByDescriptionLike(String description);

    @Query(value = "SELECT * FROM messages where description=:description", nativeQuery = true)
    List<Message> findByDescriptionLikeNative(@Param("description") String description);

    @EntityGraph(value = "Messages.forUsers")
    Message findByUser(User user);
}
