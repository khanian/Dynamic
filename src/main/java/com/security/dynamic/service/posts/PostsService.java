package com.security.dynamic.service.posts;

import com.security.dynamic.domain.posts.Posts;
import com.security.dynamic.domain.posts.PostsRepository;
import com.security.dynamic.web.dto.PostsResponseDto;
import com.security.dynamic.web.dto.PostsSaveRequestDto;
import com.security.dynamic.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다. id="+id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 데이터가 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }
}
