INSERT INTO public.users (id, created_at, updated_at, email, first_name, last_name, middle_name, phone, tag_name,telegram)
VALUES
       (1, '2021-01-19 10:04:32.988791', null, 'tr@uyt.rt', 'Люся', 'Варежкина', 'Ильинична', '+79211112233','@Л.Варежкина', 'lyska'),
       (2, '2021-01-19 10:05:39.391445', null, 'dsada@ds.ru', 'Иван', 'Иванов', 'Иванович', '+79212222222', '@И.Иванов', 'vano'),
       (3, '2021-01-19 10:05:39.391445', null, 'fds@ds.ru', 'Соловей', 'Разбойников', 'Иванович', '+79213333333', '@С.Разбойников', 'solo'),
       (4, '2021-01-19 10:05:39.391445', null, 'aazx@ds.ru', 'Петр', 'Петров', 'Петрович', '+79214444444', '@П.Петров', 'petro');

INSERT INTO public.projects (id, created_at, updated_at, description, name)
VALUES
(1, '2021-01-15 16:13:13.000000', null, 'descr1', 'project name 1');

INSERT INTO public.projects_users
VALUES (1, 1),
       (2,1);