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
    private String name;
    private String password;
    private String email;

    public UserResponseDto(User user) {
    }
}