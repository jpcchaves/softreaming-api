CREATE TABLE `categories`
(
    `id`       BIGINT(19)   NOT NULL AUTO_INCREMENT,
    `category` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_0900_ai_ci',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `UK_5ky4frjmcobbiayt5jyx53mff` (`category`) USING BTREE
)
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
;