drop database if exists pbsbase;
create database pbsbase;
use pbsbase;

create table auth_table
(
	id int not null,
	auth varchar(16) not null,
	unique (id),
	unique (auth)
);

create table gender_table
(
	id int not null,
	gender varchar(16) not null,
	unique (id),
	unique (gender)
);

create table contact_table
(
	contact_id int auto_increment primary key,
	telnum varchar(16) not null,
	name varchar(16) not null,
	passwd varchar(16) not null,
	gender_id int default 0,
	remark varchar(64),
	auth_id int default 1,
	unique(telnum),
	unique (name)
);

create table friend_table
(
	friend_id int auto_increment primary key,
	contact_tel varchar(16) not null,
	contact_name varchar(16) not null,
	telnum varchar(16) not null,
	name varchar(16) not null,
	gender_id int default 0,
	remark varchar(64),
	constraint friends_fk1 foreign key(contact_tel) references contact_table(telnum) on delete cascade,
	constraint friends_fk2 foreign key(contact_name) references contact_table(name) on delete cascade,
	unique (contact_tel,telnum),
	unique (contact_tel, name),
	unique (contact_name, telnum),
	unique (contact_name, name)
);

create table level_table
(
	id int not null,
	level varchar(16) not null,
	unique (id),
	unique (level)
);

create table type_table
(
	id int not null,
	type varchar(16) not null,
	unique (id),
	unique (type)
);

create table log_table
(
	log_id int auto_increment primary key,
	time bigint not null,
	genr varchar(64) not null,
	type_id int not null,
	level_id int not null,
	content varchar(256) not null,
	unique (time)
);

insert into auth_table values (0, 'Admin');
insert into auth_table values (1, 'User');

insert into gender_table values (0, 'Unknown');
insert into gender_table values (1, 'Male');
insert into gender_table values (2, 'Female');

insert into contact_table values (null, '00000000000', 'pbsadmin', '123456', 1, 'Admin info', 0);

insert into type_table values (1, 'SYSTEM');
insert into type_table values (2, 'CONTACT');
insert into type_table values (3, 'FRIEND');
insert into type_table values (4, 'LOG');
insert into type_table values (5, 'CONSOLE');
insert into type_table values (6, 'WECHATPBS');
insert into type_table values (7, 'TEST');

insert into level_table values (1, 'ERROR');
insert into level_table values (2, 'CRIT');
insert into level_table values (3, 'WARN');
insert into level_table values (4, 'DEBUG');