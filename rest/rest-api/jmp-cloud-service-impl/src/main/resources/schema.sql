drop table if exists "subscriptions";

drop table if exists "users";

CREATE TABLE users (
  user_id IDENTITY NOT NULL PRIMARY KEY,
  name VARCHAR(20) NOT NULL,
  surname VARCHAR(20) NOT NULL,
  birthday DATE NOT NULL);

CREATE TABLE subscriptions (
  subscription_id IDENTITY NOT NULL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  start_date DATE NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (user_id));

