CREATE TABLE features (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    enabled_by_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE feature_configs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    environment VARCHAR(20) NOT NULL,
    client_id VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    feature_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_feature_config_feature FOREIGN KEY (feature_id)
        REFERENCES features(id) ON DELETE CASCADE,
    CONSTRAINT uq_feature_env_client UNIQUE (feature_id, environment, client_id)
);
