package org.yosua.binfood.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
@SQLRestriction("deleted = false")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String token;

    @Column(name = "token_expired_at")
    private Long tokenExpiredAt;

    private boolean deleted = Boolean.FALSE;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
