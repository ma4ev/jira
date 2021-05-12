drop table if exists issue_relations;
drop sequence if exists issue_relations_id_seq;

create sequence issue_relations_id_seq;

create table issue_relations
(
    id            bigserial not null
        constraint issue_relations_pk
            primary key,
    issue_id      bigint    not null,
    relation_type varchar   not null,
    created_at    timestamp not null,
    updated_at    timestamp
);