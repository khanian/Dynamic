package com.security.dynamic.domain.user;

import com.security.dynamic.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column
    private String password;

    @Builder
    public User(String username, String email, String picture, Role role, String password) {
        this.username = username;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.password = password;
    }

    public User update(String username, String picture) {
        this.username = username;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public LocalDateTime getModifiedDate() {return this.getModifiedDate();}

}
