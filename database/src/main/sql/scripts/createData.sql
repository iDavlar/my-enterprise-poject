/*
 drop section
 */

drop table if exists pizzeria.order_entries;

drop table if exists pizzeria.orders;

drop table if exists pizzeria.pizzas;

drop table if exists pizzeria.address;

drop table if exists pizzeria.users;

drop schema if exists pizzeria;

/*
 create section
 */

create schema if not exists pizzeria;

create table pizzeria.users
(
    id         serial primary key,
    first_name char(100) not null,
    last_name  char(100) not null,
    birthday   date      not null,
    login      char(30)  not null,
    password   char(20)  not null,
    telephone  char(20)  not null
);

create table pizzeria.address
(
    id        serial primary key,
    user_id   int references pizzeria.users on delete cascade not null,
    city      char(50)                                        not null,
    region    char(50),
    street    char(50)                                        not null,
    apartment char(5)
);

create table pizzeria.pizzas
(
    id   serial primary key,
    name char(128) not null,
    cost int       not null
);

create table pizzeria.orders
(
    id         serial primary key,
    user_id    int references pizzeria.users   not null,
    address_id int references pizzeria.address not null,
    data       timestamp                       not null
);

create table pizzeria.order_entries
(
    id       serial primary key,
    order_id int references pizzeria.orders on delete cascade not null,
    pizza_id int references pizzeria.pizzas                   not null,
    amount   int                                              not null
);

/*
 insert section
 */
-- delete
-- from pizzeria.order_entries;
--
-- delete
-- from pizzeria.orders;
--
-- delete
-- from pizzeria.pizzas;
--
-- delete
-- from pizzeria.address;
--
-- delete
-- from pizzeria.users;

insert into pizzeria.users (first_name, last_name, birthday, login, password, telephone)
VALUES ('Даниил', 'Ардюков', '1997.11.28', 'Davlar', '123456', '88005553535'),
       ('Егор', 'Носов', '1990.01.30', 'EgorNos', '123456', '454647'),
       ('Глеб', 'Дмитриев', '2002.07.15', 'GlebDmi', '123456', '+79023456789'),
       ('Анна', 'Петрова', '1980.01.10', 'PetrovAn', '123456', '332244'),
       ('Мария', 'Павлова', '1997.03.20', 'MashPaw', '123456', '89021234567');

insert into pizzeria.address (user_id, city, region, street, apartment)
VALUES (1, 'Актау', null, '3 - 9', '15'),
       (2, 'Москва', 'Центр', 'Центральная 4', '15'),
       (2, 'Москва', 'Центр', 'Декабристов 7', '12'),
       (3, 'Санкт-Петербург', null, 'Красноармейская 98', '15'),
       (4, 'Санкт-Петербург', null, 'Советская 5', null),
       (1, 'Братск', 'Центральный', 'Советская 10', '45'),
       (5, 'Екатеринбург', null, 'Приморская 4', '88');

insert into pizzeria.pizzas (name, cost)
VALUES ('4 сыра', 500),
       ('Пиперони', 600),
       ('С грибами', 425),
       ('С ветчиной', 715),
       ('Гавайская', 1000);

insert into pizzeria.orders (user_id, address_id, data)
VALUES (1, 1, '2023.10.01'),
       (1, 1, '2023.10.15'),
       (2, 2, '2023.10.02'),
       (2, 2, '2023.10.11'),
       (3, 4, '2023.10.01'),
       (3, 4, '2023.10.14'),
       (4, 5, '2023.10.07'),
       (5, 7, '2023.10.07'),
       (2, 3, '2023.10.17');

insert into pizzeria.order_entries (order_id, pizza_id, amount)
VALUES (1, 1, 1),
       (1, 4, 1),
       (2, 5, 1),
       (3, 3, 3),
       (4, 1, 1),
       (4, 2, 1),
       (4, 3, 1),
       (4, 4, 1),
       (4, 5, 1),
       (5, 3, 2),
       (6, 2, 1),
       (7, 5, 1),
       (8, 1, 4),
       (9, 1, 3);








