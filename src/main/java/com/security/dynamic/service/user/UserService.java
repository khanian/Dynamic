package com.security.dynamic.service.user;

import com.security.dynamic.config.auth.CustomUserDetailsService;
import com.security.dynamic.domain.user.Role;
import com.security.dynamic.domain.user.User;
import com.security.dynamic.domain.user.UserRepository;
import com.security.dynamic.web.dto.SignupRequestDto;
import com.security.dynamic.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService customUserDetailsService;

    //private final HttpSession httpSession;

    private User createNewUser(SignupRequestDto signupRequestDto) {
        log.debug("###############createNewUser");
        return User.builder().username(signupRequestDto.getUsername())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(Role.USER).build();
    }

    private User saveUserToRepo(User user) {
        log.debug("+++++++++saveUserToRepo start");
        return userRepository.save(user);
    }

    /*private void validateDuplicate(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        optionalUser.ifPresent(findUser -> {
            throw new RuntimeException();
        });
    }*/

    /*
     * Public method
     *
     * */
    public UserResponseDto userSignup(SignupRequestDto signupRequestDto) {
        log.info("--------userSignup start--------singupRequestDto ; {}", signupRequestDto.toString());
        User user = createNewUser(signupRequestDto);
        log.info("--------createNewUser--------user; {}", user.toString());
        user = saveUserToRepo(user);
        log.info("--------createNewUser--------user.getUsername ; {}", user.getUsername());
        customUserDetailsService.loadUserByUsername(user.getEmail());
        log.info("--------createNewUser--------user.getEmail ; {}", user.getEmail());
        return new UserResponseDto(user);
    }

    private UserResponseDto findUserDetailsById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("no user maching user id");
        });
        return new UserResponseDto(user);
    }

    private List<UserResponseDto> findUserDetailsByPage(Pageable pageable) {
        Page<User> ListUser = userRepository.findAll(pageable);
        return ListUser.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    private List<UserResponseDto> userListToUserResponseList () {
        List<User> ListUser = userRepository.findAll();
        return ListUser.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto userDetails(Long id) {
        return findUserDetailsById(id);
    }

    public List<UserResponseDto> userDetailsAll() {
        return userListToUserResponseList();
    }

    public List<UserResponseDto> getUserDetailByPage(Pageable pageable) {
        return findUserDetailsByPage(pageable);
    }

    /* username이 DB에 있는지 확인 */
    /*@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("+++++++++loadUserByUsername start");
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자가 존재하지 않습니다. : " + email));
        httpSession.setAttribute("user", new UserSessionDto(user));
        *//* 시큐리티 세션에 유저 정보 저장 *//*
        return new CustomUserDetails(Optional.of(user));
    }*/
}