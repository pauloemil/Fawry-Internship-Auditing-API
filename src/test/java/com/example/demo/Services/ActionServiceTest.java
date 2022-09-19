package com.example.demo.Services;

import com.example.demo.Entities.Action;
import com.example.demo.Exceptions.NotFoundException;
import com.example.demo.Repositories.ActionRepository;
import com.example.demo.ServicesImplementations.ActionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class ActionServiceTest {
	@MockBean
	private ActionRepository actionRepository;// = mock(ActionRepository.class);
	@Autowired
	private ActionService actionService;

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
	@BeforeEach
	void setUp() {
		action = new Action();
		action.setAction_id(SOME_ID);
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

		Action saved = actionService.saveAction(action);

		assertThat(saved.getAction_id()).isEqualTo(SOME_ID);
		verify(actionRepository).save(action);
	}
}