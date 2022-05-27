package com.security.dynamic.web;

import com.security.dynamic.config.auth.dto.UserSessionDto;
import com.security.dynamic.service.posts.PostsService;
import com.security.dynamic.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model
            //, @LoginUser UserSessionDto user
    ) {
        model.addAttribute("posts", postsService.findAllDesc());

        // @LoginUser annotation 추가 이전 소스
        //SessionUser user = (SessionUser) httpSession.getAttribute("user");

        UserSessionDto userSessionDto = (UserSessionDto) httpSession.getAttribute("user");

        if (userSessionDto != null) {
            model.addAttribute("username", userSessionDto.getUsername());
        }
        return "index";
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
