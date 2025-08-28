package com.adr.followapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.adr.followapp.dto.PostContent;
import com.adr.followapp.dto.PostDto;
import com.adr.followapp.entity.Post;
import com.adr.followapp.entity.User;
import com.adr.followapp.repository.PostRepository;
import com.adr.followapp.repository.UserRepository;
import com.adr.followapp.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final PostRepository postRepository;

	private final UserRepository userRepository;	

	private final ObjectMapper objectMapper;

	private static final String NOTIFICATION_TOPIC = "post-notification";
	
	public PostServiceImpl(KafkaTemplate<String, String> kafkaTemplate, PostRepository postRepository,
			UserRepository userRepository, ObjectMapper objectMapper) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.postRepository = postRepository;
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public PostDto createPost(PostContent postContent) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("username(" + username + ") not found"));

		Post post = new Post();
		post.setContent(postContent.getContent());
		post.setCreatedAt(LocalDateTime.now());
		post.setUser(user);

		Post savedPost = postRepository.save(post);
		
		PostDto postDto = new PostDto(savedPost.getContent(), savedPost.getCreatedAt(), savedPost.getUser().getUsername());

		try {			
			String postJson = objectMapper.writeValueAsString(postDto);
			logger.info("New Post created. Publishing to Kafka topic: "+NOTIFICATION_TOPIC);
			kafkaTemplate.send(NOTIFICATION_TOPIC,postJson);
			
		}catch(JsonProcessingException e) {
			logger.error("Failed to serialize PostDto to Json :"+e.getMessage());
			e.printStackTrace();
		}
		
		return new PostDto(savedPost.getContent(), savedPost.getCreatedAt(), savedPost.getUser().getUsername());
	}

	@Override
	public String deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post does not exist for id:" + id));
		
		String LoggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if(!post.getUser().getUsername().equals(LoggedInUserName)) {			
			throw new IllegalArgumentException("Unauthorized: You do not have permission to delete this post.");		
		}

		 postRepository.delete(post);
		 return "post delete sucessfully"; 
	}

	@Override
	public List<PostDto> listAllPost(Long userId) {
	    List<Post> posts = postRepository.findByUserId(userId);

	    List<PostDto> postDtos = posts.stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());

	    return postDtos;
	}
	
	private PostDto convertToDto(Post post) {
	    PostDto dto = new PostDto();
	    dto.setContent(post.getContent());
	    dto.setPostCreatedAt(post.getCreatedAt());
	    dto.setUsername(post.getUser().getUsername());
	    return dto;
	}

}
