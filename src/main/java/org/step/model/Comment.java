package org.step.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 1024)
    @Column(name = "DESCRIPTION", length = 1024)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

//    @CreationTimestamp (Date creationDate);
    @CreatedDate
    private LocalDateTime creationDate;

//    @UpdateTimestamp (Date lastUpdate);
    @LastModifiedDate
    private LocalDateTime lastUpdate;
}
