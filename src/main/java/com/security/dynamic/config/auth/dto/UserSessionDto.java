package com.security.dynamic.config.auth.dto;

import com.security.dynamic.domain.user.Role;
import com.security.dynamic.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private final String username;
    private final String email;
    private final String picture;
    private final Role role;
    private final String password;

    /* Entity -> Dto */
    public UserSessionDto(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }

}
