package com.example.demo.Repositories;

import com.example.demo.Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActionRepositoryTest {

	@Autowired
	TestEntityManager em;
	@Autowired
	ActionRepository actionRepository;

	private static final long WRONG_ID = 151616L;

	Action action;
	User user;
	ActionType actionType;
	BusinessEntity businessEntity;
	Application application;

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
	}


	@Test
	void findActionById_shouldFindIt() {
		Optional<Action> found = actionRepository.findById(action.getAction_id());

		assertThat(found).isPresent();
	}

	@Test
	void findActionById_shouldNotFindIt() {
		Optional<Action> found = actionRepository.findById(WRONG_ID);

		assertThat(found).isNotPresent();
	}

	@Test
	void saveAction_shouldSuccess() {
		Action saved = actionRepository.save(action);

		assertThat(saved.getAction_id()).isEqualTo(action.getAction_id());

	}
}