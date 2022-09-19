package com.example.demo.Services;

import com.example.demo.Entities.Application;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ApplicationRepository;
import com.example.demo.ServicesImplementations.ApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ApplicationServiceTest {

	@Autowired
	private ApplicationService applicationService;
	@MockBean
	private ApplicationRepository applicationRepository;


	private static final long SOME_ID = 123;
	private static final long SOME_WRONG_ID = 321;


	@TestConfiguration
	static class TestConfigs{
		@Bean
		ApplicationService applicationService(){
			return new ApplicationServiceImpl();
		}
	}

	Application application;
	@BeforeEach
	void setUp() {
		application	= new Application();
		application.setApplication_id(SOME_ID);
	}

	@Test
	void findApplicationWithValidId_shouldReturnIt() {
		when(applicationRepository.findById(any()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (id != application.getApplication_id()) {
						throw new NotFoundException(String.format("Application with id: %1d is not found!", id));
					}
					else {
						return Optional.of(application);
					}
				});
		Application found = applicationService.findApplicationById(SOME_ID);

		assertThat(found.getApplication_id()).isEqualTo(SOME_ID);

		verify(applicationRepository).findById(any());
	}

	@Test
	void findApplicationWithValidId_shouldThrowNotFoundError() {
		when(applicationRepository.findById(any()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (id != application.getApplication_id()) {
						throw new NotFoundException(String.format("Application with id: %1d is not found!", id));
					}
					else {
						return Optional.of(application);
					}
				});

		assertThatThrownBy(()->applicationRepository.findById(SOME_WRONG_ID))
				.isInstanceOf(NotFoundException.class).hasMessageContaining(SOME_WRONG_ID+"");
		verify(applicationRepository).findById(any());
	}
}