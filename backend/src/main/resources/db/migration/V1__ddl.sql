CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE service (
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL UNIQUE,
    created_time TIMESTAMP NOT NULL DEFAULT TIMEZONE('UTC', NOW()),
    reference UUID NOT NULL DEFAULT uuid_generate_v1(),
    PRIMARY KEY (id)
);