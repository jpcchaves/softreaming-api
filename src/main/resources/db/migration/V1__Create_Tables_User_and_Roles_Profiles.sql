CREATE TABLE
    public.users
(
    id         bigserial                      NOT NULL,
    created_at timestamp(6) without time zone NULL,
    email      character varying(255)  unique       NOT NULL,
    is_admin   boolean                        NULL,
    name       character varying(255)         NULL,
    password   character varying(255)         NULL,
    updated_at timestamp(6) without time zone NULL,
    username   character varying(255)   unique     NOT NULL
);

ALTER TABLE
    public.users
    ADD
        CONSTRAINT users_pkey PRIMARY KEY (id);

CREATE TABLE
    public.roles
(
    id   bigserial              NOT NULL,
    name character varying(255) NULL
);

ALTER TABLE
    public.roles
    ADD
        CONSTRAINT roles_pkey PRIMARY KEY (id);

CREATE TABLE
    public.users_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

CREATE TABLE
    public.profiles
(
    id         bigserial                      NOT NULL,
    created_at timestamp(6) without time zone NULL,
    img_url    text                           NOT NULL,
    name       character varying(255)         NOT NULL,
    user_id    bigint                         NOT NULL
);

ALTER TABLE
    public.profiles
    ADD
        CONSTRAINT profiles_pkey PRIMARY KEY (id);