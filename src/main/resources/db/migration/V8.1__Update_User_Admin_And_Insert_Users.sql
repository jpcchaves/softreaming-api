update
    `users`
set `created_at` = '2023-05-23 15:00:00'
where `id` = '1';

update
    `users`
set `updated_at` = '2023-05-23 15:00:00'
where `id` = '1';

INSERT INTO `softreaming_db`.`users` (`id`, `email`, `name`, `password`, `username`, is_admin, `created_at`,
                                      `updated_at`)
VALUES ('2', 'teste@teste.com', 'teste', '$2a$12$SePc8N5Jqw4MyjfpbM6c7uYTSAWaO92DoPjECYdFO6bizwPULmzwm', 'Teste',
        true, '2023-05-23 15:01:00', '2023-05-23 15:01:00');