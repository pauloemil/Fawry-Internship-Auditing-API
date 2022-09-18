package com.example.demo.Resources;


import com.example.demo.DTOs.ActionRequestDto;
import com.example.demo.DTOs.ActionsResponseDto;
import com.example.demo.DTOs.SearchDto;
import com.example.demo.Entities.*;
import com.example.demo.Configurations.QueueSender;
import com.example.demo.Exceptions.*;
import com.example.demo.Mappers.StringToActionRequestDto;
import com.example.demo.Repositories.ActionQueryDSLRepository;
import com.example.demo.Services.*;
import com.example.demo.Validators.ActionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/actions")
public class ActionResource {
    private ActionService actionService;
    private ActionTypeService actionTypeService;
    private ApplicationService applicationService;
    private BusinessEntityService businessEntityService;
    private UserService userService;

    private ParameterService parameterService;
    private ParameterTypeService parameterTypeService;


    @Autowired
    private QueueSender queueSender;

    @Autowired
    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }
    @Autowired
    public void setParameterTypeService(ParameterTypeService parameterTypeService) {
        this.parameterTypeService = parameterTypeService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setBusinessEntityService(BusinessEntityService businessEntityService) {
        this.businessEntityService = businessEntityService;
    }
    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setActionTypeService(ActionTypeService actionTypeService) {
        this.actionTypeService = actionTypeService;
    }

    @Autowired
    public void setActionService(ActionService actionService){
        this.actionService = actionService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Action> findActionById(@PathVariable long id){
        Action action = actionService.findActionById(id);
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @Autowired
	ActionQueryDSLRepository actionQueryDSLRepository;


    @GetMapping(path = "/")
    public ResponseEntity<ActionsResponseDto> findActions(@RequestParam(required = false) String user_name,
                                                    @RequestParam(required = false) String application_name,
                                                    @RequestParam(required = false) String business_entity_name,
                                                    @RequestParam(required = false) Long action_type_id,
                                                    @RequestParam(required = false) Long parameter_type_id,
                                                    @RequestParam(required = false) String parameter_value,
                                                    @RequestParam(required = false, defaultValue = "10") Long limit,
                                                    @RequestParam(required = false, defaultValue = "1") Long page_number
    ) throws WrongParameterValueException {
        //TODO @Bean param   // couldn't do it :(
        SearchDto searchDto = new SearchDto();
        if(user_name != null) {
            searchDto.setUser_name(user_name);
        }
        if(action_type_id != null) {
            searchDto.setAction_type_id(action_type_id);
        }
        if(business_entity_name != null) {
            searchDto.setBusiness_entity_name(business_entity_name);
        }
        if(application_name != null) {
            searchDto.setApplication_name(application_name);
        }
        if(parameter_type_id != null && parameter_value != null) {
            searchDto.setParameter_type_id(parameter_type_id);
            searchDto.setParameter_value(parameter_value);
        }

        if(limit <= 0) {
            throw new WrongParameterValueException("limit parameter value can't be negative");
        }
        if(page_number < 1) {
            throw new WrongParameterValueException("page_number parameter value can't be less than 1");
        }
        searchDto.setLimit(limit);
        searchDto.setPage_number(page_number);
        ActionsResponseDto actions = actionQueryDSLRepository.findAppsGeneric(searchDto);
//        System.out.println(searchDto);
//        System.out.println(actions);
        return new ResponseEntity<>(actions, HttpStatus.OK);
    }


    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addAction(@RequestBody String stringJson) throws Exception {
        ActionRequestDto actionRequestDto = StringToActionRequestDto.convertFromString(stringJson);

        ActionValidation actionValidation = new ActionValidation(actionTypeService, applicationService, businessEntityService, userService, parameterTypeService);
        actionValidation.isActionRequestValid(actionRequestDto);

        queueSender.send(stringJson);

        return new ResponseEntity<>("action",HttpStatus.OK);
    }
}
