package org.step.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

import static org.step.model.User.USER_MESSAGE_LIST_QUERY;

@NamedEntityGraph(name = Message.FIND_MESSAGE_BY_USER, attributeNodes = {
        @NamedAttributeNode("user")
//        @NamedAttributeNode(value = "messageList", subgraph = "user_message")
})
//subgraphs = {
//        @NamedSubgraph(name = "user_message", attributeNodes = {
//                @NamedAttributeNode("user")
//        })
@Entity
@Table(name = "MESSAGES")
@Data
@NoArgsConstructor
public class Message {


    @Transient
    public static final String FIND_MESSAGE_BY_USER = "Messages.forUsers";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    @SequenceGenerator(name = "message_seq", allocationSize = 1, sequenceName = "message_seq")
//    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    // @Min @Max - числа
    @Size(min = 5, max = 1024, message = "")
    @Column(length = 1025)
    private String description;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "message")
    private List<Comment> commentList;
}
