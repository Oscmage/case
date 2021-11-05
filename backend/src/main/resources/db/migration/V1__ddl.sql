CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE service (
    id UUID NOT NULL DEFAULT uuid_generate_v1(),
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    created_time TIMESTAMP NOT NULL DEFAULT TIMEZONE('UTC', NOW()),
    reference UUID NOT NULL DEFAULT uuid_generate_v1(),
    polling BOOLEAN NOT NULL DEFAULT FALSE,
    updated TIMESTAMP NOT NULL DEFAULT TIMEZONE('UTC', NOW()),
    PRIMARY KEY (id)
);

CREATE FUNCTION sync_lastmod() RETURNS trigger AS $$
BEGIN
  NEW.updated := NOW();

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER
    sync_lastmod
    BEFORE UPDATE ON
    service
    FOR EACH ROW EXECUTE PROCEDURE
    sync_lastmod();