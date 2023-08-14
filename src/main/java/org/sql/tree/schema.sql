CREATE TABLE IF NOT EXISTS clinic_hierarchy
(
    id        BIGINT PRIMARY KEY,
    parent_id BIGINT,
    position  VARCHAR NOT NULL
);