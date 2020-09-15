CREATE TABLE owner (
    id              INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name            VARCHAR(25),
    address         TEXT,
    phone           VARCHAR(25) UNIQUE NOT NULL,
    email           VARCHAR(25) UNIQUE NOT NULL
);

ALTER TABLE merchant ADD COLUMN owner_id INTEGER REFERENCES owner (id);

ALTER TABLE merchant ADD PRIMARY KEY (id);
