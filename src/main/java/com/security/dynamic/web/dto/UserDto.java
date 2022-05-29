package com.security.dynamic.web.dto;

import com.security.dynamic.domain.user.Role;
import com.security.dynamic.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String picture;
    //private String role;

    public UserDto(User user) {
    }

    /* DTO -> Entity */
    public User toEntity() {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
        return user;
    }

}