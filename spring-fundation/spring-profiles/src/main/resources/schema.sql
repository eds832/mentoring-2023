drop table if exists "users";

CREATE TABLE "users" (
  user_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NULL,
  surname VARCHAR(20) NULL,
  email VARCHAR(40) NULL,
  PRIMARY KEY (user_id)
);
