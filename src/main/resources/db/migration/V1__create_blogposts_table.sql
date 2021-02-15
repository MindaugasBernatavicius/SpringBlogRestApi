create table blogposts (
       id bigint not null auto_increment,
       text varchar(255),
       title varchar(255),
       primary key (id)
) engine=InnoDB
