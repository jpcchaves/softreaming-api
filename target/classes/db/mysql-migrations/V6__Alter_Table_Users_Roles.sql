alter table users_roles
    add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles (id),
    add constraint FK2o0jvgh89lemvvo17cbqvdxaa foreign key (user_id) references users (id);