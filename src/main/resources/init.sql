--DELETE TABLES--
drop table if exists answers;
drop table if exists questions;
drop table if exists users;

--CREATE TABLES--
create table users
(
    id            serial primary key,
    username      varchar(50) unique not null,
    pw_hash       varchar(50)        not null,
    registered_at timestamp default now()
);

create table questions
(
    id          serial primary key,
    title       text not null,
    body        text,
    user_id     integer references users (id),
    created_at  timestamp default now(),
    modified_at timestamp default now()
);

create table answers
(
    id          serial primary key,
    question_id integer references questions (id) on delete cascade,
    body        text not null,
    user_id     integer references users (id),
    created_at  timestamp default now(),
    modified_at timestamp default now(),
    accepted    boolean   default false
);

--ADD INITIAL DATA--
insert into users(username, pw_hash)
values ('user1', 'pw1'),
       ('user2', 'pw2'),
       ('user3', 'pw3');

insert into questions(title, body, user_id)
values ('What is love?', 'Baby don''t hurt me', 1),
       ('Is it really worth writing multiple sample questions?', 'We''ll never know...', 2);

insert into answers(question_id, body, user_id)
values (1, 'Don''t hurt me', 2),
       (1, 'No more...', 3),
       (2, 'Well it''s pretty empty here by now..', 1);