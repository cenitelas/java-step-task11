package org.step.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.step.security.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.step.model.User.USER_MESSAGE_LIST_QUERY;
import static org.step.model.User.USER_UPDATE_PASSWORD;

@Entity
@Table(name = "USERS")
//@BatchSize(size = 100)
@NamedQueries({
        @NamedQuery(
                name = USER_UPDATE_PASSWORD,
                query = "update User u set u.password=?1",
                lockMode = LockModeType.NONE
//                hints = {
//                        @QueryHint(name = "", value = )
//                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                query = "SELECT * FROM USERS", resultClass = User.class, name = "User.allUsers"
        )
})
@NamedEntityGraph(name = USER_MESSAGE_LIST_QUERY, attributeNodes = {
        @NamedAttributeNode("messageList")
//        @NamedAttributeNode(value = "messageList", subgraph = "user_message")
})
//subgraphs = {
//        @NamedSubgraph(name = "user_message", attributeNodes = {
//                @NamedAttributeNode("user")
//        })
@Getter
@Setter
@ToString(of = {"id", "username"})
@EqualsAndHashCode(of = {"id", "username"})
@NoArgsConstructor
@AllArgsConstructor(staticName = "from")
@RequiredArgsConstructor(staticName = "field")
@Builder
//@DynamicUpdate
//@DynamicInsert
public class User {

    @Transient
    public static final String USER_MESSAGE_LIST_QUERY = "User.messageList";
    @Transient
    public static final String USER_UPDATE_PASSWORD = "User.updatePassword";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
//    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ_DB")
//    @Builder.Default
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NonNull
    private String username;

    @Column(name = "password", nullable = false, length = 1024)
    @NonNull
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "AUTHORITIES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
//    @Getter
//    @Setter
    private Set<GrantedAuthority> authorities = new HashSet<>();

    /*
    @OneToOne
    @ManyToMany
     */
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
//            fetch = FetchType.EAGER
//            orphanRemoval = true
    )
    @Singular(value = "message")
    private List<Message> messageList;

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList;

    public User(Long id, String username) {
        this.username = username;
        this.id = id;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Set<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
