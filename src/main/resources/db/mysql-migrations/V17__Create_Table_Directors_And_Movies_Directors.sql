CREATE TABLE
    `directors`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `first_name` varchar(100) DEFAULT NULL,
    `last_name`  varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE
    `movies_directors`
(
    `movie_id`    bigint NOT NULL,
    `director_id` bigint NOT NULL,
    PRIMARY KEY (`movie_id`, `director_id`),
    KEY `FKbtmuusnf9w4db2p998xls2ci8` (`director_id`),
    CONSTRAINT `FKbtmuusnf9w4db2p998xls2ci8` FOREIGN KEY (`director_id`) REFERENCES `directors` (`id`),
    CONSTRAINT `FKco6yamau93db0bwxv6uneg1d7` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
);