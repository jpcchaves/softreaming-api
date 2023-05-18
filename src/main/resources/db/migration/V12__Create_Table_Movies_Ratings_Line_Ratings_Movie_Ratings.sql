CREATE TABLE
    `movies` (
                 `id` bigint NOT NULL AUTO_INCREMENT,
                 `created_at` datetime(6) DEFAULT NULL,
                 `duration` varchar(255) NOT NULL,
                 `long_description` text NOT NULL,
                 `movie_url` varchar(255) NOT NULL,
                 `name` varchar(255) NOT NULL,
                 `poster_url` varchar(255) NOT NULL,
                 `release_date` varchar(255) NOT NULL,
                 `short_description` varchar(255) NOT NULL,
                 PRIMARY KEY (`id`),
                 UNIQUE KEY `UK_need1sfwodvn2yjle40es9twm` (`name`)
);

CREATE TABLE
    `movies_categories` (
                            `movie_id` bigint NOT NULL,
                            `category_id` bigint NOT NULL,
                            CONSTRAINT `FKqrh6tstpqrf9baynpdkvxtni1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
                            CONSTRAINT `FKsep7u2w9avfsm5xe1l5ny5xgl` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
);

CREATE TABLE
    `ratings` (
                  `id` bigint NOT NULL AUTO_INCREMENT,
                  `created_at` datetime(6) DEFAULT NULL,
                  `rating` double DEFAULT NULL,
                  `ratings_amount` int DEFAULT NULL,
                  PRIMARY KEY (`id`)
);

CREATE TABLE
    `line_rating` (
                      `id` bigint NOT NULL AUTO_INCREMENT,
                      `created_at` datetime(6) DEFAULT NULL,
                      `rate` double DEFAULT NULL,
                      `user_id` bigint DEFAULT NULL,
                      `rating_id` bigint NOT NULL,
                      PRIMARY KEY (`id`),
                      KEY `FKd7rjrbp19iadacsww2yeiiw6u` (`rating_id`),
                      CONSTRAINT `FKd7rjrbp19iadacsww2yeiiw6u` FOREIGN KEY (`rating_id`) REFERENCES `ratings` (`id`)
);

CREATE TABLE
    `movie_rating` (
                       `rating_id` bigint DEFAULT NULL,
                       `movie_id` bigint NOT NULL,
                       PRIMARY KEY (`movie_id`),
                       KEY `FK3eue4x1nmlr5umhm28x7vd7m4` (`rating_id`),
                       CONSTRAINT `FK3eue4x1nmlr5umhm28x7vd7m4` FOREIGN KEY (`rating_id`) REFERENCES `ratings` (`id`),
                       CONSTRAINT `FKrw226r1p47nxbd66d1nsqvte3` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`)
);


