CREATE TABLE
    public.movies (
                      id bigserial NOT NULL,
                      created_at timestamp(6) without time zone NULL,
                      duration character varying(255) NOT NULL,
                      long_description text NOT NULL,
                      movie_url character varying(255) NOT NULL,
                      name character varying(255) NOT NULL,
                      poster_url character varying(255) NOT NULL,
                      release_date character varying(255) NOT NULL,
                      short_description character varying(255) NOT NULL
);

ALTER TABLE
    public.movies
    ADD
        CONSTRAINT movies_pkey PRIMARY KEY (id);

CREATE TABLE
    public.movies_categories (movie_id bigserial NOT NULL, category_id bigserial NOT NULL);

CREATE TABLE
    public.ratings (
                       id bigserial NOT NULL,
                       created_at timestamp(6) without time zone NULL,
                       rating double precision NULL,
                       ratings_amount integer NULL
);


CREATE TABLE
    public.line_rating (
                           id bigserial NOT NULL,
                           created_at timestamp(6) without time zone NULL,
                           rate double precision NULL,
                           user_id bigint NULL,
                           rating_id bigint NOT NULL
);

CREATE TABLE
    public.movie_rating (rating_id bigint NULL, movie_id bigint NOT NULL);
