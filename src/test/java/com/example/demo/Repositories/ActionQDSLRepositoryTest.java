package com.example.demo.Repositories;

import com.example.demo.DTOs.ActionsResponseDto;
import com.example.demo.DTOs.SearchDto;
import com.example.demo.Entities.*;
import com.example.demo.Helpers.TemplateDescriptionConverter;
import com.example.demo.Repositories.RepositoriesImplementaions.ActionQueryDSLRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActionQDSLRepositoryTest {
	@Autowired
	TestEntityManager em;

	@Qualifier("actionRepositoryQueryDSL")
	@Autowired
	ActionQueryDSLRepository actionQueryDSLRepository;
	private static final String NAME_EN = "paulo";

	@TestConfiguration
	static class ActionQDSLRepositoryTestConfig {
		@Bean
		ActionQueryDSLRepository actionRepositoryQueryDSL(){
			return new ActionQueryDSLRepositoryImpl();
		}
	}

	Action action;
	User user;
	ActionType actionType;
	BusinessEntity businessEntity;
	Application application;
	ParameterType orderType;
	Parameter orderParameter;
	SearchDto searchDto;
	@BeforeEach
	void setUp() {
		actionType = new ActionType();
		actionType.setName("ORDER_CREATED");
		actionType.setMessage_template_ar("العميل {{customer.value}} أضاف طلب {{order.value}} بمنتج {{product.value}}");
		actionType.setMessage_template_en("Customer {{customer.value}} created order {{order.value}} with product {{product.value}}");
		actionType.setCode("dummy");
		actionType.setName_ar("استرجاع منتج");
		em.persist(actionType);
		em.flush();

		user = new User();
		user.setUser_name(NAME_EN);
		user.setUser_image("https://dummyimage.com/500x500/bd942d/a8a9b3&text=PE");
		user.setUser_title("DB Manager");
		em.persist(user);
		em.flush();

		businessEntity = new BusinessEntity();
		businessEntity.setBusiness_entity_name("Fawry Company");
		em.persist(businessEntity);
		em.flush();

		application = new Application();
		application.setApplication_name("Fawry Application");
		em.persist(application);
		em.flush();

		action = new Action();
		action.setAction_type(actionType);
		action.setUser(user);
		action.setBusiness_entity(businessEntity);
		action.setApplication(application);
		action.setTrace_id("dummy");
		action.setDescription_ar("ar ");
		action.setDescription_en("en");
		em.persist(action);
		em.flush();

		ParameterType customerType = new ParameterType();
		customerType.setName("customer");
		customerType.setCode("dummy");
		customerType.setName_ar("العميل");
		em.persist(customerType);
		em.flush();

		ParameterType productType = new ParameterType();
		productType.setName("product");
		productType.setCode("dummy1");
		productType.setName_ar("المنتج");
		em.persist(productType);
		em.flush();

		orderType = new ParameterType();
		orderType.setName("order");
		orderType.setCode("dummy2");
		orderType.setName_ar("الطلب");
		em.persist(orderType);
		em.flush();

		Parameter customerParameter = new Parameter();
		customerParameter.setParameter_type(customerType);
		customerParameter.setAction(action);
		customerParameter.setParameter_value("paulo emil");
		em.persist(customerParameter);
		em.flush();

		Parameter productParameter = new Parameter();
		productParameter.setParameter_type(productType);
		productParameter.setAction(action);
		productParameter.setParameter_value("big mac + combo");
		em.persist(productParameter);
		em.flush();

		orderParameter = new Parameter();
		orderParameter.setParameter_type(orderType);
		orderParameter.setAction(action);
		orderParameter.setParameter_value("fast food");
		em.persist(orderParameter);
		em.flush();

		searchDto = new SearchDto();
		searchDto.setLimit(10);
		searchDto.setPage_number(1);
	}

	// Search with no parameters
	@Test
	void searchInActionsWithoutParameters_shouldFindIt() {
		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
	}

	// Search with one parameter
	@Test
	void searchInActionsWithValidUserName_shouldFindIt() {
		searchDto.setUser_name(user.getUser_name());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
	}
	@Test
	void searchInActionsWithValidApplicationName_shouldFindIt() {
		searchDto.setApplication_name(application.getApplication_name());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("application").contains(application);
	}
	@Test
	void searchInActionsWithValidBusinessEntityName_shouldFindIt() {
		searchDto.setBusiness_entity_name(businessEntity.getBusiness_entity_name());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("business_entity").contains(businessEntity);
	}
	@Test
	void searchInActionsWithValidActionTypeId_shouldFindIt() {
		searchDto.setAction_type_id(actionType.getAction_type_id());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("action_type").contains(actionType);
	}
	@Test // TODO
	void searchInActionsWithValidParameterTypeIdAndParameterValue_shouldFindIt() {
		searchDto.setParameter_type_id(orderType.getParameter_type_id());
		searchDto.setParameter_value(orderParameter.getParameter_value());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
	}

	// Search with two parameters
	@Test
	void searchInActionsWithValidUserNameAndApplicationName_shouldFindIt() {
		searchDto.setUser_name(user.getUser_name());
		searchDto.setApplication_name(application.getApplication_name());


		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
		assertThat(actionsResponseDto.getActions()).extracting("application").contains(application);

	}

	// Search with three parameters
	@Test
	void searchInActionsWithValidUserNameAndApplicationNameAndBusinessEntityName_shouldFindIt() {
		searchDto.setUser_name(user.getUser_name());
		searchDto.setApplication_name(application.getApplication_name());
		searchDto.setBusiness_entity_name(businessEntity.getBusiness_entity_name());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);
		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
		assertThat(actionsResponseDto.getActions()).extracting("application").contains(application);
		assertThat(actionsResponseDto.getActions()).extracting("business_entity").contains(businessEntity);
	}

	// Search with four parameters
	@Test
	void searchInActionsWithValidUserNameAndApplicationNameAndBusinessEntityNameAndActionTypeId_shouldFindIt() {
		searchDto.setUser_name(user.getUser_name());
		searchDto.setApplication_name(application.getApplication_name());
		searchDto.setBusiness_entity_name(businessEntity.getBusiness_entity_name());
		searchDto.setAction_type_id(actionType.getAction_type_id());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);


		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);

		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
		assertThat(actionsResponseDto.getActions()).extracting("application").contains(application);
		assertThat(actionsResponseDto.getActions()).extracting("business_entity").contains(businessEntity);
		assertThat(actionsResponseDto.getActions()).extracting("action_type").contains(actionType);
	}

	// Search with all parameters
	@Test // TODO
	void searchInActionsWithAllParameters_shouldFindIt() {
		searchDto.setUser_name(user.getUser_name());
		searchDto.setApplication_name(application.getApplication_name());
		searchDto.setBusiness_entity_name(businessEntity.getBusiness_entity_name());
		searchDto.setAction_type_id(actionType.getAction_type_id());
		searchDto.setParameter_type_id(orderType.getParameter_type_id());
		searchDto.setParameter_value(orderParameter.getParameter_value());

		ActionsResponseDto actionsResponseDto = actionQueryDSLRepository.findAppsGeneric(searchDto);

		assertThat(actionsResponseDto.getTotal_count()).isGreaterThan(0);

		assertThat(actionsResponseDto.getActions()).extracting("user").contains(user);
		assertThat(actionsResponseDto.getActions()).extracting("application").contains(application);
		assertThat(actionsResponseDto.getActions()).extracting("business_entity").contains(businessEntity);
		assertThat(actionsResponseDto.getActions()).extracting("action_type").contains(actionType);
	}
}