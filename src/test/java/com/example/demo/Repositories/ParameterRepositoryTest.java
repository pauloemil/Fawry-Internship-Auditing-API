package com.example.demo.Repositories;

import com.example.demo.DTOs.SearchDto;
import com.example.demo.Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class ParameterRepositoryTest {
	@Autowired
	TestEntityManager em;

	@Autowired
	ParameterRepository parameterRepository;

	Action action;
	User user;
	ActionType actionType;
	BusinessEntity businessEntity;
	Application application;
	ParameterType orderType;
	Parameter orderParameter;

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
		user.setUser_name("paulo");
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

	}


	@Test
	void saveParameter_shouldSuccess() {
		Parameter saved = parameterRepository.save(orderParameter);

		assertThat(saved.getParameter_id()).isEqualTo(orderParameter.getParameter_id());
		assertThat(saved.getAction().getAction_id()).isEqualTo(action.getAction_id());
	}
}