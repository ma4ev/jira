create sequence projects_id_seq start 1 increment 1;
create sequence users_id_seq start 1 increment 1;

create table projects
(
    id          int8 not null,
    created_at  timestamp not null,
    updated_at  timestamp,
    description varchar(255),
    name        varchar(255) not null,
    primary key (id)
);

create table projects_users
(
    user_id    int8 not null,
    project_id int8 not null,
    primary key (user_id, project_id)
);

create table users
(
    id          int8 not null,
    created_at  timestamp not null,
    updated_at  timestamp,
    email       varchar(255) not null,
    first_name  varchar(255) not null,
    last_name   varchar(255) not null,
    middle_name varchar(255),
    phone       varchar(255) not null,
    tag_name    varchar(255) not null,
    telegram    varchar(255),
    primary key (id)
);

alter table if exists projects_users
    add constraint projects_users_projects foreign key (project_id) references projects;
alter table if exists projects_users
    add constraint projects_users_users foreign key (user_id) references users;