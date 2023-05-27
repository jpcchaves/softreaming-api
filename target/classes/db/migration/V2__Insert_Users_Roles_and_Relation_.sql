insert into "public"."users" ("created_at",
                              "email",
                              "is_admin",
                              "name",
                              "password",
                              "updated_at",
                              "username")
values ('2023-05-27',
        'admin@softreaming.com',
        true,
        'Admin',
        '$2a$08$xoLuSsqvDIcZ6.FzEbx3nehvw7mj4y794l2twMGgDVuWAyJXlCG56',
        '2023-05-27',
        'admin'),
       ('2023-05-27',
        'teste@teste.com',
        false,
        'Teste',
        '$2a$08$7WPY32xWBLJ5VoEOBaINc.ktldt4yZy/SBwVA7mfOLbVYjE1q3F/a',
        '2023-05-27 00:00:00',
        'teste');

insert into "public"."roles" ("name")
values ('ROLE_ADMIN'),
       ('ROLE_USER');

insert into "public"."users_roles" ("role_id", "user_id")
values ('1', '1'),
       ('2', '2');






