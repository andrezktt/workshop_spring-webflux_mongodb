package com.devsuperior.workshopmongo.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongo.dto.PostDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
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

	public Flux<PostDTO> fullSearch(String text, Instant minDate, Instant maxDate) {
		maxDate = maxDate.plusSeconds(86400);
		return repository.fullSearch(text, minDate, maxDate).map(PostDTO::new);
	}
}
