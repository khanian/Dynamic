package com.security.dynamic.service.user;

import com.security.dynamic.config.auth.dto.MyUserDetail;
import com.security.dynamic.domain.user.Role;
import com.security.dynamic.domain.user.User;
import com.security.dynamic.domain.user.UserRepository;
import com.security.dynamic.web.dto.SignupRequestDto;
import com.security.dynamic.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    /*
     * Autowired, Bean Injection
     * user repository to CRUD user
     * password encoder to encode password
     * */

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Controller should not know SignUpDto to User method
     * Controller should not directly Connect to Repository
     *
     * */

    private User createNewUser(SignupRequestDto signupRequestDto) {
        return User.builder().name(signupRequestDto.getName())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(Role.USER).build();
    }

    private User saveUserToRepo(User user) {
        return userRepository.save(user);
    }

    /*
     * Public method
     *
     * */
    public UserResponseDto userSignup(SignupRequestDto signupRequestDto) {
        User user = createNewUser(signupRequestDto);
        user = saveUserToRepo(user);
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info(email);
        //여기서 받은 유저 패스워드와 비교하여 로그인 인증
        User user = userRepository.findUserByEmail(email);
        return new MyUserDetail(user);
    }
}