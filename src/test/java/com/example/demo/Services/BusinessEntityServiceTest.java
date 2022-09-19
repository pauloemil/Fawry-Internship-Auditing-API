package com.example.demo.Services;

import com.example.demo.Entities.BusinessEntity;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.BusinessEntityRepository;
import com.example.demo.ServicesImplementations.BusinessEntityServiceImpl;
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
class BusinessEntityServiceTest {

	@Autowired
	private BusinessEntityService businessEntityService;
	@MockBean
	private BusinessEntityRepository businessEntityRepository;


	private static final long SOME_ID = 123;
	private static final long SOME_WRONG_ID = 321;


	@TestConfiguration
	static class TestConfigs{
		@Bean
		BusinessEntityService businessEntityService(){
			return new BusinessEntityServiceImpl();
		}
	}

	BusinessEntity businessEntity;
	@BeforeEach
	void setUp(){
		businessEntity = new BusinessEntity();
		businessEntity.setBusiness_entity_id(SOME_ID);
	}
	@Test
	void findBusinessEntityWithValidId_shouldReturnIt() {
		when(businessEntityRepository.findById(any()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (id != businessEntity.getBusiness_entity_id()) {
						throw new NotFoundException(String.format("Business Entity with id: %1d is not found!", id));
					}
					else {
						return Optional.of(businessEntity);
					}
				});

		BusinessEntity found = businessEntityService.findBusinessEntityById(SOME_ID);

		assertThat(found.getBusiness_entity_id()).isEqualTo(SOME_ID);
		verify(businessEntityRepository).findById(any());
	}

	@Test
	void findBusinessEntityWithInvalidId_shouldThrowNotFoundError() {
		when(businessEntityRepository.findById(any()))
				.then(answer-> {
					long id =  answer.getArgument(0);

					if (id != businessEntity.getBusiness_entity_id()) {
						throw new NotFoundException(String.format("Business Entity with id: %1d is not found!", id));
					}
					else {
						return Optional.of(businessEntity);
					}
				});

		assertThatThrownBy(()->{
			businessEntityService.findBusinessEntityById(SOME_WRONG_ID);
		}).isInstanceOf(NotFoundException.class).hasMessageContaining(SOME_WRONG_ID+"");
		verify(businessEntityRepository).findById(any());
	}
}