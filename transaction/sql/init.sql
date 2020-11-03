create table cboard.dashboard_role
(
	role_id varchar(100) not null
		primary key,
	role_name varchar(100) null,
	user_id varchar(50) null
);

create table cboard.dashboard_user
(
	user_id varchar(50) not null
		primary key,
	login_name varchar(100) null,
	user_name varchar(100) null,
	user_password varchar(100) null,
	user_status varchar(100) null
);

