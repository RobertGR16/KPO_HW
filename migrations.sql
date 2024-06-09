CREATE DATABASE "AuthService";
CREATE DATABASE "TicketService";

-- Подключение к базе данных AuthService
\c "AuthService"

CREATE TABLE IF NOT EXISTS "users"
(
    "id" SERIAL PRIMARY KEY,
    "nickname" VARCHAR(50) NOT NULL,
    "email" VARCHAR(100) UNIQUE NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    "created" TIMESTAMP DEFAULT now()
);

CREATE TABLE IF NOT EXISTS "session"
(
    "id" SERIAL PRIMARY KEY,
    "user_id" int NOT NULL,
    "token" VARCHAR(255) NOT NULL,
    "expires" TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES "users"(id)
);


-- Подключение к базе данных TicketService
\c "TicketService"

CREATE TABLE IF NOT EXISTS "station"
(
    "id" SERIAL PRIMARY KEY,
    "station" VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "orders"
(
    "id" SERIAL PRIMARY KEY,
    "user_id" INT NOT NULL,
    "from_station_id" INT NOT NULL,
    "to_station_id" INT NOT NULL,
    "status" INT NOT NULL,
    "created" TIMESTAMP DEFAULT now(),
    FOREIGN KEY (from_station_id) REFERENCES station(id),
    FOREIGN KEY (to_station_id) REFERENCES station(id)
);

-- Заполнение таблицы station mock данными
INSERT INTO "station" ("station") VALUES ('Moscow');
INSERT INTO "station" ("station") VALUES ('New York');
INSERT INTO "station" ("station") VALUES ('London');
INSERT INTO "station" ("station") VALUES ('Paris');
INSERT INTO "station" ("station") VALUES ('Berlin');
INSERT INTO "station" ("station") VALUES ('Tokyo');
INSERT INTO "station" ("station") VALUES ('Cairo');

