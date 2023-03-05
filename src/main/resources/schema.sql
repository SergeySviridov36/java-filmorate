CREATE TABLE IF NOT EXISTS users(
  users_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar(255) NOT NULL,
  login varchar(255) NOT NULL,
  name varchar(255),
  birthday date
);

CREATE TABLE IF NOT EXISTS genre (
  genre_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255)
);

CREATE TABLE IF NOT EXISTS film_rating (
  MPA_id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar(255)
);

CREATE TABLE IF NOT EXISTS film (
  film_id int PRIMARY KEY,
  name varchar(255),
  release_date date,
  description varchar(255),
  duration int,
  rate int,
  MPA_id int REFERENCES film_rating(MPA_id)
);

CREATE TABLE IF NOT EXISTS friend_user(
  users_id int REFERENCES users(users_id),
  friend_id int REFERENCES users(users_id),
  PRIMARY KEY(users_id,friend_id)
);

CREATE TABLE IF NOT EXISTS like_films(
  users_id int REFERENCES users(users_id),
  film_id int REFERENCES film(film_id),
  PRIMARY KEY(users_id, film_id)
);

CREATE TABLE IF NOT EXISTS film_genre (
  genre_id int REFERENCES genre(genre_id),
  film_id int REFERENCES film(film_id),
   PRIMARY KEY(genre_id,film_id)
);

