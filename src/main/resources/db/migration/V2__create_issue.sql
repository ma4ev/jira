drop table if exists issues;
drop sequence if exists issues_id_seq;

create sequence issues_id_seq start 1 increment 1;

create table issues
(
    id                 bigserial not null
        constraint issue_pk
            primary key,
    type               varchar   not null,
    theme              varchar   not null,
    author_id          bigint    not null,
    project_id         bigint    not null,
    component          varchar   not null,
    description        text,
    priority           varchar   not null,
    initial_assessment bigint,
    remaining_time     bigint,
    date_performance   date,
    created_at         timestamp not null,
    updated_at         timestamp,
    status             varchar   not null

);

comment on column issues.initial_assessment is 'duration in minutes';
comment on column issues.remaining_time is 'duration in minutes';