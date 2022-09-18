package com.example.demo.Services;

import com.example.demo.Entities.User;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.UserRepository;
import com.example.demo.ServicesImplementations.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UserServiceTest {

	@Autowired
	private UserService userService;
	@MockBean
	private UserRepository userRepository;

	private static final long SOME_ID = 123;
	private static final long SOME_WRONG_ID = 321;

	@TestConfiguration
	static class TestConfigs{
		@Bean
		UserService userService(){
			return new UserServiceImpl();
		}
	}


	User user;

	@BeforeEach
	void setUp() {
		user = new User();
		user.setUser_id(SOME_ID);
	}

	@Test
	void findUserWithValidName_shouldReturnIt(){
		when(userRepository.findById(user.getUser_id()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (user.getUser_id() != id) {
						throw new NotFoundException(String.format("User with id: %1d is not found!", id));
					}
					else {
						return Optional.of(user);
					}
				});

		User found = userService.findUserById(SOME_ID);

		assertThat(found.getUser_id()).isEqualTo(SOME_ID);
	}
	@Test
	void findUserWithInvalidName_shouldThrowNotFoundError(){
		when(userRepository.findById(user.getUser_id()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (user.getUser_id() == id) {
						throw new NotFoundException(String.format("User with id: %1d is not found!", id));
					}
					else {
						return Optional.of(user);
					}
				});

		assertThatThrownBy(() -> userService.findUserById(SOME_WRONG_ID))
				.isInstanceOf(NotFoundException.class).hasMessageContaining(SOME_WRONG_ID+"");
	}
}