package com.security.dynamic.config.auth;

import com.security.dynamic.config.auth.dto.CustomUserDetails;
import com.security.dynamic.config.auth.dto.UserSessionDto;
import com.security.dynamic.domain.user.User;
import com.security.dynamic.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /* username이 DB에 있는지 확인 */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("$$loadUserByUsername start$$");
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + email));
        log.debug("user.getPassword = {}", user.getPassword());
        //세션 생성
        httpSession.setAttribute("user", new UserSessionDto(user));
        // 세션 로그
        UserSessionDto userSessionDto = (UserSessionDto) httpSession.getAttribute("user");
        log.debug("$$userSessionDto.getUsername ::::::: {}", userSessionDto.getUsername());
        /* 시큐리티 세션에 유저 정보 저장 */
        return new CustomUserDetails(userSessionDto);
    }
}
