package com.security.dynamic.web;

import com.security.dynamic.service.user.UserService;
import com.security.dynamic.web.dto.SignupRequestDto;
import com.security.dynamic.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping(value = "/signup")
    public UserResponseDto SignUp(@RequestBody SignupRequestDto signupRequestDto) {
        log.info("controller signup start---------");
        return userService.userSignup(signupRequestDto);
    }

    @GetMapping("/api/v1/userDetails/{id}")
    public UserResponseDto getUserDetail(@PathVariable Long id) {
        return userService.userDetails(id);
    }

    @GetMapping("/api/v1/userDetails")
    public List<UserResponseDto> getUserDetailsAll() {
        return userService.userDetailsAll();
    }

    @GetMapping("/api/v1/userDetailsPage/{page_no}/{page_size}")
    public List<UserResponseDto> getUserDetailPage(@PathVariable int page_no, @PathVariable int page_size) {
        Pageable pageable = PageRequest.of(page_no, page_size, Sort.by("createdDate"));
        return userService.getUserDetailByPage(pageable);
    }
}
