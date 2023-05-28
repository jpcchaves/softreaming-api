CREATE TABLE
    public.categories
(
    id       bigserial              NOT NULL,
    category character varying(255) NOT NULL
);

ALTER TABLE
    public.categories
    ADD
        CONSTRAINT categories_pkey PRIMARY KEY (id);

insert into "public"."categories" ("category")
values ('Ação'),
       ('Aventura'),
       ('Cinema de arte'),
       ('Chanchada'),
       ('Comédia'),
       ('Comédia de ação'),
       ('Comédia de terror'),
       ('Comédia dramática'),
       ('Comédia romântica'),
       ('Dança'),
       ('Documentário'),
       ('Docuficção'),
       ('Drama'),
       ('Espionagem'),
       ('Faroeste'),
       ('Fantasia'),
       ('Fantasia científica'),
       ('Ficção científica'),
       ('Filmes com truques'),
       ('Filmes de guerra'),
       ('Mistério'),
       ('Musical'),
       ('Filme policial'),
       ('Romance'),
       ('Terror'),
       ('Thriller'),
       ('Crime');