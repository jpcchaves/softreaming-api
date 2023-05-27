create table users_roles (
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);