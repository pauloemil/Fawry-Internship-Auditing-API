package com.example.demo.Repositories.RepositoriesImplementaions;

import com.example.demo.DTOs.ActionsResponseDto;
import com.example.demo.DTOs.SearchDto;
import com.example.demo.Entities.*;

import com.example.demo.Repositories.ActionQueryDSLRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ActionQueryDSLRepositoryImpl implements ActionQueryDSLRepository {

    @Autowired
    private EntityManager em;

    public ActionsResponseDto findAppsGeneric(SearchDto searchDto){
        final JPAQuery<Action> query = new JPAQuery<>(em);
        final QAction qAction = QAction.action;
        final QParameter qParameter = QParameter.parameter;

        query.from(qAction);

        if (searchDto.getAction_type_id() != 0) {
            query.where(qAction.action_type.action_type_id.eq(searchDto.getAction_type_id()));
        }
        if(searchDto.getApplication_name() != null) {
            query.where(qAction.application.application_name.eq(searchDto.getApplication_name()));
        }
        if(searchDto.getUser_name() != null) {
            query.where(qAction.user.user_name.eq(searchDto.getUser_name()));
        }
        if(searchDto.getBusiness_entity_name() != null) {
            query.where(qAction.business_entity.business_entity_name.eq(searchDto.getBusiness_entity_name()));
        }
        if(searchDto.getParameter_type_id() != 0 && searchDto.getParameter_value() != null){
            query.join(qAction.parameters,  qParameter);
            query.where(qParameter.parameter_type.parameter_type_id.eq(searchDto.getParameter_type_id()).
                    and(qParameter.parameter_value.eq(searchDto.getParameter_value())));
        }
        //defaults
        long limit = searchDto.getLimit();
        long page_number = searchDto.getPage_number();


        // should use map-struct

        ActionsResponseDto actionsResponseDto = new ActionsResponseDto();
        actionsResponseDto.setTotal_count(query.stream().count());

        query.limit(limit).offset(limit*(page_number-1));

        actionsResponseDto.setActions(query.fetch());
        actionsResponseDto.setPage_number(page_number);
        actionsResponseDto.setPage_size(limit);

        return actionsResponseDto;
    }


}
