create table users (
    id bigint not null auto_increment,
    email varchar(255) not null,
    is_admin bit, name varchar(255),
    password varchar(255),
    username varchar(255) not null,
    primary key (id)
);
