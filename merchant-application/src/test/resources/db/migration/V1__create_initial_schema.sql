--1
CREATE SCHEMA IF NOT EXISTS bestcommerce AUTHORIZATION test;

--2
create table bestcommerce.merchant
(
    id            INTEGER GENERATED ALWAYS AS IDENTITY,
    type          VARCHAR(25),
    name          VARCHAR(25) UNIQUE,
    password_hash VARCHAR(25) NOT NULL
);

--3
CREATE TABLE bestcommerce.owner
(
    id      INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(25),
    address TEXT,
    phone   VARCHAR(25) UNIQUE NOT NULL,
    email   VARCHAR(25) UNIQUE NOT NULL
);

--4
ALTER TABLE bestcommerce.merchant
    ADD COLUMN owner_id INTEGER REFERENCES bestcommerce.owner (id);

--5
ALTER TABLE bestcommerce.merchant
    ADD PRIMARY KEY (id);

---------------------------

--6
INSERT INTO bestcommerce.owner(name, address, phone, email)
VALUES ('name', 'address', 'phone', 'email');

--7
INSERT INTO bestcommerce.merchant(name, type, password_hash, owner_id)
VALUES ('name', 'type', 'test', 1);
