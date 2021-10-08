create table PROJECT (id bigint not null auto_increment, hours integer, primary key (id)) engine=InnoDB;
create table TIMEREGISTRATION (id bigint not null auto_increment, consultant varchar(255), date date, hours integer, project_id bigint, primary key (id)) engine=InnoDB;
alter table TIMEREGISTRATION add constraint PROJECT_FK foreign key (project_id) references PROJECT (id);
