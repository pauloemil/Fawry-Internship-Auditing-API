package com.example.demo.Services;

import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.DTOs.ParameterDto;
import com.example.demo.Entities.*;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Mappers.ActionMapper;
import com.example.demo.Mappers.ParameterMapper;
import com.example.demo.Repositories.ActionRepository;
import com.example.demo.ServicesImplementations.ActionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ActionServiceTest {
	@MockBean
	private ActionRepository actionRepository;// = mock(ActionRepository.class);
	@MockBean
	private ParameterService parameterService;
	@MockBean
	private ActionTypeService actionTypeService;
	@MockBean
	private ApplicationService applicationService;
	@MockBean
	private BusinessEntityService businessEntityService;
	@MockBean
	private UserService userService;
	@MockBean
	private ParameterTypeService parameterTypeService;

	@Autowired
	private ActionService actionService;

	@Autowired
	ActionMapper actionMapper;
	@Autowired
	ParameterMapper parameterMapper;

	private static final long SOME_ID = 123;
	private static final long SOME_WRONG_ID = 321;


	@TestConfiguration
	static class TestConfig{
		@Bean
		ActionService actionService() {
			return new ActionServiceImpl();
		}
	}

	Action action;
	ActionRequestDto actionRequestDto;
	ActionType actionType;
	BusinessEntity be;
	Application application;
	User user;
	ParameterDto customerParameter;
	List<ParameterDto> parameters;
	@BeforeEach
	void setUp() {
		action = new Action();
		action.setAction_id(SOME_ID);
		System.out.println(action);

		parameters = new ArrayList<>();

		customerParameter = new ParameterDto();
		customerParameter.setParameter_id(12);
		customerParameter.setParameter_type_name("customer");
		customerParameter.setParameter_value("paulo");
		parameters.add(customerParameter);

		ParameterDto orderParameter = new ParameterDto();
		orderParameter.setParameter_id(11);
		orderParameter.setParameter_type_name("order");
		orderParameter.setParameter_value("Paulo's order");
		parameters.add(orderParameter);

		ParameterDto productParameter = new ParameterDto();
		productParameter.setParameter_id(11);
		productParameter.setParameter_type_name("product");
		productParameter.setParameter_value("Wrak fra5 bel bsal el a7mar");
		parameters.add(productParameter);

		actionRequestDto = new ActionRequestDto();
		actionRequestDto.setParameters(parameters);
		actionRequestDto.setAction_type_name("ORDER_CREATED");
		actionRequestDto.setUser_id(1);
		actionRequestDto.setApplication_id(1);
		actionRequestDto.setBusiness_entity_id(1);
		actionRequestDto.setBusiness_entity_id(1);

		actionType = new ActionType();
		actionType.setMessage_template_en("{{customer.value}} bomba");
		actionType.setMessage_template_ar("{{customer.value}} بومبا");

		be = new BusinessEntity();
		be.setBusiness_entity_name("BE Name");

		application = new Application();
		application.setApplication_name("Application Name");

		user = new User();
		user.setUser_name("User Name");
	}

	@Test
	void findActionWithValidId_shouldFindIt() {
		when(actionRepository.findById(SOME_ID)).then(answer -> Optional.of(action));

		Action found = actionService.findActionById(SOME_ID);

		assertThat(found.getAction_id()).isEqualTo(SOME_ID);
		verify(actionRepository).findById(any());
	}

	@Test
	void findActionWithInvalidId_shouldThrowNotFoundError() {
		when(actionRepository.findById(any())).then(answer -> {
			long id =  answer.getArgument(0);
			if (id != action.getAction_id())
				throw new NotFoundException(String.format("Action with id: %1d is not found!", id));
			else {
				return action;
			}
		});

		assertThatThrownBy(()->{
			actionService.findActionById(SOME_WRONG_ID);
		}).isInstanceOf(NotFoundException.class).hasMessageContaining(SOME_WRONG_ID+"");
		verify(actionRepository).findById(any());
	}

	@Test
	void saveValidAction_shouldReturnIt() {
		when(actionRepository.save(any(Action.class))).then(answer -> answer.getArgument(0));
		when(parameterService.saveParameter(any(Parameter.class))).then(answer -> answer.getArgument(0));
		when(businessEntityService.findBusinessEntityById(anyLong())).then(
				answer -> {
					be.setBusiness_entity_id(answer.getArgument(0));
					return be;
				}
		);
		when(applicationService.findApplicationById(anyLong())).then(
				answer -> {
					application.setApplication_id(answer.getArgument(0));
					return application;
				}
		);
		when(userService.findUserById(anyLong())).then(
				answer -> {
					user.setUser_id(answer.getArgument(0));
					return user;
				}
		);
		when(actionTypeService.findActionTypeByName(anyString())).then(
				answer -> {
					actionType.setName(answer.getArgument(0));
					return actionType;
				}
		);
		Action saved = actionService.saveAction(actionRequestDto);

		assertThat(saved.getUser().getUser_name()).isEqualTo(user.getUser_name());
		assertThat(saved.getBusiness_entity().getBusiness_entity_name()).isEqualTo(be.getBusiness_entity_name());
		assertThat(saved.getAction_type().getName()).isEqualTo(actionType.getName());
		assertThat(saved.getDescription_en()).contains(customerParameter.getParameter_value());

	}
}