package com.example.demo.Services;

import com.example.demo.Entities.ActionType;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ActionTypeRepository;
import com.example.demo.ServicesImplementations.ActionTypeServiceImpl;
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
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
class ActionTypeServiceTest {

	@MockBean
	private ActionTypeRepository actionTypeRepository;
	@Autowired
	private ActionTypeService actionTypeService;

	private static final String NAME_EN = "ORDER_REFUNDED";
	private static final String NAME_EN_WRONG = "ORDER_CREATED";
	private static final long SOME_ID = 123;

	@TestConfiguration
	static class TestConfigs{
		@Bean
		ActionTypeService actionTypeService(){
			return new ActionTypeServiceImpl();
		}
	}

	ActionType actionType;
	ActionType actionType2;
	ActionType actionType3;
	@BeforeEach
	void setUp() {
		actionType = new ActionType();
		actionType.setAction_type_id(SOME_ID);
		actionType.setName(NAME_EN);

		actionType2 = new ActionType();
		actionType2.setAction_type_id(2);
		actionType2.setName(NAME_EN+1);

		actionType3 = new ActionType();
		actionType3.setAction_type_id(2);
		actionType3.setName(NAME_EN+2);
	}

	@Test
	void findActionTypeWithValidName_shouldReturnIt() {
		when(actionTypeRepository.findByName(NAME_EN)).then(answer-> {
			String name =  answer.getArgument(0);
			if (!actionType.getName().equals(name)) {
				throw new NotFoundException(String.format("ActionType with name: %1s is not found!", name));
			}
			else {
				return actionType;
			}
		});

		ActionType found = actionTypeService.findActionTypeByName(NAME_EN);

		assertThat(found.getName()).isEqualTo(NAME_EN);
	}

	@Test
	void findActionTypeWithInvalidId_shouldThrowNotFoundError() {
		when(actionTypeRepository.findByName(NAME_EN_WRONG))
				.then(answer-> {
					String name =  answer.getArgument(0);

					if (!actionType.getName().equals(name)) {
						throw new NotFoundException(String.format("ActionType with name: %1s is not found!", name));
					}
					else {
						return actionType;
					}
				});

		assertThatThrownBy(()->actionTypeRepository.findByName(NAME_EN_WRONG))
				.isInstanceOf(NotFoundException.class).hasMessageContaining(NAME_EN_WRONG);
	}

	@Test
	void findAllActionTypes_shouldReturnAllSuccessfully() {
		List<ActionType> actionTypes = new ArrayList<>();
		actionTypes.add(actionType);
		actionTypes.add(actionType2);
		actionTypes.add(actionType3);

		when(actionTypeRepository.findAll()).thenReturn(actionTypes);

		List<ActionType> found = actionTypeService.findAllActionTypes();

		assertThat(found).containsExactly(actionType, actionType2, actionType3);
	}
}