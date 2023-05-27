create table profiles (
    id bigint not null auto_increment,
    created_at varchar(255),
    img_url longtext not null,
    name varchar(255) not null,
    user_id bigint not null,
    primary key (id)
);