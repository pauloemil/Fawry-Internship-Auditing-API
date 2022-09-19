package com.example.demo.Services;

import com.example.demo.Entities.ParameterType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ParameterTypeRepository;
import com.example.demo.ServicesImplementations.ParameterTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ParameterTypeServiceTest {

	@Autowired
	private ParameterTypeService parameterTypeService;
	@MockBean
	private ParameterTypeRepository parameterTypeRepository;

	private static final long SOME_ID = 123;
	private static final String NAME_EN = "Customer";
	private static final String NAME_EN_WRONG = "Order";


	@TestConfiguration
	static class TestConfigs{
		@Bean
		ParameterTypeService parameterTypeService(){
			return new ParameterTypeServiceImpl();
		}
	}

	ParameterType parameterType;
	ParameterType parameterType2;
	ParameterType parameterType3;
	@BeforeEach
	void setUp() {
		parameterType = new ParameterType();
		parameterType.setParameter_type_id(SOME_ID);
		parameterType.setName(NAME_EN);

		parameterType2 = new ParameterType();
		parameterType2.setParameter_type_id(2);
		parameterType2.setName(NAME_EN+2);

		parameterType3 = new ParameterType();
		parameterType3.setParameter_type_id(3);
		parameterType3.setName(NAME_EN+3);
	}

	@Test
	void findParameterTypeWithValidName_shouldReturnIt() {
		when(parameterTypeRepository.findByName(any()))
				.then(answer-> {
					String name =  answer.getArgument(0);

					if (!parameterType.getName().equals(name)) {
						throw new NotFoundException(String.format("Parameter Type with name: %1s is not found!", name));
					}
					else {
						return Optional.of(parameterType);
					}
				});

		ParameterType found = parameterTypeService.findParameterTypeByName(NAME_EN);

		assertThat(found.getName()).isEqualTo(NAME_EN);
		verify(parameterTypeRepository).findByName(any());
	}

	@Test
	void findParameterTypeWithInvalidName_shouldThrowNotFoundError() {
		when(parameterTypeRepository.findByName(any()))
				.then(answer-> {
					String name =  answer.getArgument(0);

					if (!parameterType.getName().equals(name)) {
						throw new NotFoundException(String.format("Parameter Type with name: %1s is not found!", name));
					}
					else {
						return Optional.of(parameterType);
					}
				});
		assertThatThrownBy(()->{
			parameterTypeService.findParameterTypeByName(NAME_EN_WRONG);
		}).isInstanceOf(NotFoundException.class).hasMessageContaining(NAME_EN_WRONG);
		verify(parameterTypeRepository).findByName(any());
	}

	@Test
	void findParameterTypes_shouldReturnAllSuccessfully() {
		List<ParameterType> parameterTypeList = new ArrayList<>();
		parameterTypeList.add(parameterType);
		parameterTypeList.add(parameterType2);
		parameterTypeList.add(parameterType3);
		when(parameterTypeRepository.findAll()).thenReturn(parameterTypeList);

		List<ParameterType> found = parameterTypeService.findAllParameterTypes();

		assertThat(found).containsExactly(parameterType, parameterType2, parameterType3);
		verify(parameterTypeRepository).findAll();
	}
}