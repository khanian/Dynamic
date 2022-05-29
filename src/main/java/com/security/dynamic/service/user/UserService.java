package com.security.dynamic.service.user;

import com.security.dynamic.domain.user.User;
import com.security.dynamic.domain.user.UserRepository;
import com.security.dynamic.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //private final HttpSession httpSession;

    /*private User createNewUser(UserDto userDto ) {
        log.debug("START createNewUser!!!!!!!!!!!!!");
        return User.builder().username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER).build();
    }

    private User saveUserToRepo(User user) {
        log.debug("START saveUserToRepo");
        return userRepository.save(user);
    }*/

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
//    public UserDto userSignupOld(UserDto userDto) {
//        log.info("userSignup start::::::::singupRequestDto.getUsername ; {}", userDto.getUsername());
//        User user = createNewUser(userDto);
//        log.debug("createNewUser::::::::::::user.gerId() ; {}", user.getId());
//        user = saveUserToRepo(user);
//        log.debug("user.getUsername ; {}", user.getUsername());
//        customUserDetailsService.loadUserByUsername(user.getEmail());
//        log.debug("user.getEmail ; {}", user.getEmail());
//        return new UserDto(user);
//    }

    @Transactional
    public Long userSignUp(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        log.debug("encrypt password :: {}", bCryptPasswordEncoder.encode(userDto.getPassword()));
        return userRepository.save(userDto.toEntity()).getId();
    }

    /*public void userLogin(UserDto userDto, Authentication authentication) {
        log.debug("email ::::::: {}", userDto.getEmail());
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        log.debug("customUserDetails.getUsername() :: {}", customUserDetails.getUsername());
        log.debug("customUserDetails.getPassword() :: {}", customUserDetails.getPassword());
        //customUserDetailsService.loadUserByUsername(userDto.getEmail());
    }*/

    private UserDto findUserDetailsById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> {
            return new IllegalArgumentException("no user maching user id");
        });
        return new UserDto(user);
    }

    private List<UserDto> findUserDetailsByPage(Pageable pageable) {
        Page<User> ListUser = userRepository.findAll(pageable);
        return ListUser.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    private List<UserDto> userListToUserResponseList () {
        List<User> ListUser = userRepository.findAll();
        return ListUser.stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    public UserDto userDetails(Long id) {
        return findUserDetailsById(id);
    }

    public List<UserDto> userDetailsAll() {
        return userListToUserResponseList();
    }

    public List<UserDto> getUserDetailByPage(Pageable pageable) {
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