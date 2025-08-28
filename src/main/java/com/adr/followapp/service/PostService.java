package com.adr.followapp.service;

import java.util.List;

import com.adr.followapp.dto.PostContent;
import com.adr.followapp.dto.PostDto;

public interface PostService {

	PostDto createPost(PostContent postContent);
	
	String deletePost(Long id);
	
	List<PostDto> listAllPost(Long userId);
}
