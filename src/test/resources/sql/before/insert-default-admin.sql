delete from usr; commit;

insert into usr (created_at, rating, id, username, email, password, avatar_url, description)
values (now(), 5, 0, 'diagorn', 'diagorn1999@yandex.ru', '$2a$10$vnNXmwkv0/TligPO0XYbhuUubj8t3BwYzGpxxwMlSOCccEGR/atQS',
        null, null);
commit;

insert into user_user_role (user_id, user_role_id)
values (2, 0);
commit;