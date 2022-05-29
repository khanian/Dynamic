package com.security.dynamic.web;

import com.security.dynamic.service.user.UserService;
import com.security.dynamic.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping(value = "/signup")
    public ModelAndView signUp(UserDto userDto) {
        log.info("controller signup start---------");
        Long userId = userService.userSignUp(userDto);
        log.info("userId = {}", userId);
        return new ModelAndView("redirect:/login");
    }

    /*@PostMapping(value = "/loginProc")
    public void loginProc(UserDto userDto, Authentication authentication) {
        log.info("controller login start --------->");
        log.debug("loginRequestDto.getUsername() = {}", userDto.getUsername());
        log.debug("loginRequestDto.getPassword() = {}", userDto.getPassword());
        log.debug("loginRequestDto.getEmail() = {}", userDto.getEmail());
        userService.userLogin(userDto, authentication);
    }*/

    @GetMapping("/api/v1/userDetails/{id}")
    public UserDto getUserDetail(@PathVariable Long id) {
        return userService.userDetails(id);
    }

    @GetMapping("/api/v1/userDetails")
    public List<UserDto> getUserDetailsAll() {
        return userService.userDetailsAll();
    }

    @GetMapping("/api/v1/userDetailsPage/{page_no}/{page_size}")
    public List<UserDto> getUserDetailPage(@PathVariable int page_no, @PathVariable int page_size) {
        Pageable pageable = PageRequest.of(page_no, page_size, Sort.by("createdDate"));
        return userService.getUserDetailByPage(pageable);
    }
}
