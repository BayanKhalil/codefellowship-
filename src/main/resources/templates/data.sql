
CREATE TABLE `application_user`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `bio`   varchar(255) DEFAULT NULL,
    `date_of_birth`   varchar(255) DEFAULT NULL,
    `first_name`   varchar(255) DEFAULT NULL,
    `last_name`   varchar(255) DEFAULT NULL UNIQUE,
    `password`   varchar(255) DEFAULT NULL UNIQUE,
    `username`   varchar(255) DEFAULT NULL UNIQUE,
    `department` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
);


INSERT INTO `application_user` (`bio`, `date_of_birth`, `first_name`, `last_name`,`password`,`username`)
VALUES ('qwe', '2021-07-21', 'bayan','khalil','$2a$10$Mo5qdD0cqFoEzdjrHAh6w.f3XIWef.8aGBbtZzkovNoFNIuRA7OlC','bayan khalil');