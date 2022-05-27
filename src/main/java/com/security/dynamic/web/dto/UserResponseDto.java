package com.security.dynamic.web.dto;

import com.security.dynamic.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private String username;
    private String password;
    private String email;
    private String picture;
    private String role;

    public UserResponseDto(User user) {
    }

    /* DTO -> Entity */
    /*public User toEntity() {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
        return user;
    }*/

}