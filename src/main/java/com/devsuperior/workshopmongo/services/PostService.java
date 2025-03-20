package com.devsuperior.workshopmongo.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.entities.Post;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PostService {

	@Autowired
	private PostRepository repository;

	public Flux<PostDTO> findAll() {
		return repository.findAll().map(PostDTO::new);
	}

	public Mono<PostDTO> findById(String id) {
		return repository.findById(id).map(PostDTO::new);
	}

	public Flux<PostDTO> findByTitle(String text) {
		return repository.searchTitle(text).map(PostDTO::new);
	}

//	public List<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
//		maxDate = maxDate.plusSeconds(86400); // 24 * 60 * 60
//		List<PostDTO> result = repository.fullSearch(text, minDate, maxDate).stream().map(x -> new PostDTO(x)).toList();
//		return result;
//	}
}
