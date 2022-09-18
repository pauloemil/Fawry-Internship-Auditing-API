package com.example.demo.Services;

import com.example.demo.Entities.Parameter;
import com.example.demo.Repositories.ParameterRepository;
import com.example.demo.ServicesImplementations.ParameterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class ParameterServiceTest {

	@Autowired
	private ParameterService parameterService;
	@MockBean
	private ParameterRepository parameterRepository;

	@TestConfiguration
	static class TestConfigs{
		@Bean
		ParameterService parameterService(){
			return new ParameterServiceImpl();
		}
	}

	private static final long SOME_ID = 123;

	Parameter parameter;
	@BeforeEach
	void setUp() {
		parameter = new Parameter();
		parameter.setParameter_id(SOME_ID);
	}


	@Test
	void shouldSaveParameter() {
		when(parameterRepository.save(any(Parameter.class))).then(answer -> parameter);

		Parameter saved = parameterService.saveParameter(parameter);

		assertThat(saved.getParameter_id()).isEqualTo(SOME_ID);
		verify(parameterRepository).save(parameter);
	}

	//TODO: make other test to get the error! but i dont have it right now!

	// there is nothing to be tested here! it should be tested in the repository layer!
}