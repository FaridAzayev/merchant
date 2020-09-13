create table merchant (
    id              INTEGER GENERATED ALWAYS AS IDENTITY,
    type            VARCHAR(25),
    name            VARCHAR(25) UNIQUE,
    password_hash   VARCHAR(25) NOT NULL
);