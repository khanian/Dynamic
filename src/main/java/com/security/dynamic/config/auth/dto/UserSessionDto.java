package com.security.dynamic.config.auth.dto;

import com.security.dynamic.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserSessionDto implements Serializable {
    private String username;
    private String email;
    private String picture;
    private String role;

    /* Entity -> Dto */
    public UserSessionDto(User user){
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRoleKey();
    }

}
