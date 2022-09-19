CREATE SCHEMA `auditing` DEFAULT CHARACTER SET utf8 ;
# make this default please

CREATE TABLE application (
    application_id int NOT NULL AUTO_INCREMENT,
    application_name varchar(255) NOT NULL,
    
    
    PRIMARY KEY (application_id),
    UNIQUE (application_id)
);


CREATE TABLE business_entity (
    business_entity_id int NOT NULL AUTO_INCREMENT,
    business_entity_name varchar(255) NOT NULL,
    
    
    PRIMARY KEY (business_entity_id),
    UNIQUE (business_entity_id)
);


CREATE TABLE user_table (
    user_id int NOT NULL AUTO_INCREMENT,
    user_name varchar(255) NOT NULL,
    user_image varchar(255) NOT NULL,
    user_title varchar(255) NOT NULL,
    
    
    PRIMARY KEY (user_id),
    UNIQUE (user_id)
);


CREATE TABLE action_type (
    action_type_id int NOT NULL AUTO_INCREMENT,
    code varchar(255) NOT NULL,
    name_ar varchar(255) NOT NULL,
    name_en varchar(255) NOT NULL,
    message_template_ar varchar(255) NOT NULL,
    message_template_en varchar(255) NOT NULL,
    
    
    PRIMARY KEY (action_type_id),
    UNIQUE (action_type_id)
);


CREATE TABLE action (
    action_id int NOT NULL AUTO_INCREMENT,
    action_type_id int NOT NULL,
    application_id int NOT NULL,
    business_entity_id int NOT NULL,
    user_id int NOT NULL,
    
    description_ar varchar(255) NOT NULL,
    description_en varchar(255) NOT NULL,
	date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    trace_id varchar(255) NOT NULL,
    
    PRIMARY KEY (action_id),
    UNIQUE (action_id),
    FOREIGN KEY (action_type_id) REFERENCES action_type(action_type_id),
    FOREIGN KEY (application_id) REFERENCES application(application_id),
    FOREIGN KEY (business_entity_id) REFERENCES business_entity(business_entity_id),
    FOREIGN KEY (user_id) REFERENCES user_table(user_id)
);


CREATE TABLE parameter_type (
    parameter_type_id int NOT NULL AUTO_INCREMENT,
    code varchar(255) NOT NULL,
    name_ar varchar(255) NOT NULL,
    name_en varchar(255) NOT NULL,
    
    
    PRIMARY KEY (parameter_type_id),
    UNIQUE (parameter_type_id)
);


CREATE TABLE parameter (
    parameter_id int NOT NULL AUTO_INCREMENT,
	parameter_type_id int NOT NULL,
    action_id int NOT NULL,
    parameter_value varchar(255) NOT NULL,
    
    
    PRIMARY KEY (parameter_id),
    UNIQUE (parameter_id),
    FOREIGN KEY (parameter_type_id) REFERENCES parameter_type(parameter_type_id),
    FOREIGN KEY (action_id) REFERENCES action(action_id)
);



#parameters primary types
INSERT INTO `parameter_type` (`code`, `name_ar`, `name_en`) VALUES ('1', 'العميل', 'customer');
INSERT INTO `parameter_type` (`code`, `name_ar`, `name_en`) VALUES ('2', 'الطلب', 'order');
INSERT INTO `parameter_type` (`code`, `name_ar`, `name_en`) VALUES ('3', 'المنتج', 'product');


#action primary types
INSERT INTO `action_type` (`code`, `name_ar`, `name_en`, `message_template_ar`, `message_template_en`)
VALUES ('3133', 'إضافة طلب', 'ORDER_CREATED', 'العميل {{customer.value}} أضاف طلب {{order.value}} بمنتج {{product.value}}',
 'Customer {{customer.value}} created order {{order.value}} with product {{product.value}}');
INSERT INTO `action_type` (`code`, `name_ar`, `name_en`, `message_template_ar`, `message_template_en`)
VALUES ('3133', 'استرجاع منتج', 'ORDER_REFUNDED', 'اليوزر {{user.value}} استرجع منتج {{order.value}} من العميل {{customer.value}}',
 'User {{user.value}} refunded order {{order.value}} created by customer: {{customer.value}}');


#users to be used 
INSERT INTO `user_table` (`user_name`, `user_image`, `user_title`) VALUES ('paulo', 'https://dummyimage.com/500x500/000/fff&text=PE', 'Admin');
INSERT INTO `user_table` (`user_name`, `user_image`, `user_title`) VALUES ('emil', 'https://dummyimage.com/500x500/000/fff&text=PE', 'DB Admin');


#business entities to be used 
INSERT INTO `business_entity` (`business_entity_name`) VALUES ('Paulo Co.');
INSERT INTO `business_entity` (`business_entity_name`) VALUES ('Fawry Co.');


#applications to be used 
INSERT INTO `application` (`application_name`) VALUES ('Paulo Application');
INSERT INTO `application` (`application_name`) VALUES ('Fawry Application');







