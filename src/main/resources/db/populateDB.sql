DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
(100000, '2015-May-30 10:00', 'Завтрак', 500),
(100000, '2015-May-30 13:00', 'Обед', 1000),
(100000, '2015-May-30 20:00', 'Ужин', 500),
(100000, '2015-May-31 10:00', 'Завтрак', 1000),
(100000, '2015-May-31 13:00', 'Обед', 500),
(100000, '2015-May-31 20:00', 'Ужин', 510),
(100001, '2012-Jan-31 12:00', 'Lunch', 400),
(100001, '2012-Jan-31 19:00', 'Dinner', 1610);
