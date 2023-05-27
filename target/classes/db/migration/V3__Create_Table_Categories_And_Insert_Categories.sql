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

insert into "public"."categories" ("id", "category")
values ('1', 'Ação'),
       ('2', 'Aventura'),
       ('3', 'Cinema de arte'),
       ('4', 'Chanchada'),
       ('5', 'Comédia'),
       ('6', 'Comédia de ação'),
       ('7', 'Comédia de terror'),
       ('8', 'Comédia dramática'),
       ('9', 'Comédia romântica'),
       ('10', 'Dança'),
       ('11', 'Documentário'),
       ('12', 'Docuficção'),
       ('13', 'Drama'),
       ('14', 'Espionagem'),
       ('15', 'Faroeste'),
       ('16', 'Fantasia'),
       ('17', 'Fantasia científica'),
       ('18', 'Ficção científica'),
       ('19', 'Filmes com truques'),
       ('20', 'Filmes de guerra'),
       ('21', 'Mistério'),
       ('22', 'Musical'),
       ('23', 'Filme policial'),
       ('24', 'Romance'),
       ('25', 'Terror'),
       ('26', 'Thriller'),
       ('27', 'Crime');