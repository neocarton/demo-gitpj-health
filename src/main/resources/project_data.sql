CREATE TABLE IF NOT EXISTS state (
    id SMALLINT,
    last_page INT NOT NULL,
    last_offset INT NOT NULL,
    last_proceeded_count INT NOT NULL DEFAULT 0,
    total_proceeded_count INT NOT NULL DEFAULT 0,
    PRIMARY KEY(id),
);

CREATE TABLE IF NOT EXISTS data (
    id VARCHAR(200) NOT NULL,
    object_id VARCHAR(100) NOT NULL,
    key VARCHAR(100) NOT NULL,
    value CLOB(100K),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    PRIMARY KEY(id),
);
