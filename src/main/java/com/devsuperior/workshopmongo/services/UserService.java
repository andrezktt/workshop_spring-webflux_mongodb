package com.devsuperior.workshopmongo.services;

import com.devsuperior.workshopmongo.entities.User;
import com.devsuperior.workshopmongo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.workshopmongo.dto.UserDTO;
import com.devsuperior.workshopmongo.repositories.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Flux<UserDTO> findAll() {
		return repository.findAll().map(UserDTO::new);
	}

	public Mono<UserDTO> findById(String id) {
		return repository.findById(id)
				.map(UserDTO::new)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado!")));
	}

	public Mono<UserDTO> insert(UserDTO dto) {
		User entity = new User();
		copyDtoToEntity(dto, entity);
		Mono<UserDTO> result = repository.save(entity).map(UserDTO::new);
		return result;
	}

	public Mono<UserDTO> update(String id, UserDTO dto) {
		return repository.findById(id)
				.flatMap(user -> {
					user.setName(dto.getName());
					user.setEmail(dto.getEmail());
					return repository.save(user);
				})
				.map(UserDTO::new)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado!")));
	}

	public Mono<Void> delete(String id) {
		return repository.findById(id)
				.switchIfEmpty(Mono.error(new ResourceNotFoundException("Recurso não encontrado!")))
				.flatMap(user -> repository.delete(user));
	}

	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
	}
}
