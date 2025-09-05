package com.bytes7.feature_flag7.model;

import com.bytes7.feature_flag7.backend.enums.Environment;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(
    name = "feature_configs",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_feature_env_client",
                          columnNames = {"feature_id", "environment", "client_id"})
    }
)
public class FeatureConfig {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment", nullable = false, length = 20)
    private Environment environment;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feature_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_feature_config_feature"))
    private Feature feature;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public FeatureConfig() {}

    public FeatureConfig(Environment environment, String clientId, Boolean enabled) {
        this.environment = environment;
        this.clientId = clientId;
        this.enabled = enabled;
    }

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters y Setters

    public UUID getId() { return id; }
    public Environment getEnvironment() { return environment; }
    public void setEnvironment(Environment environment) { this.environment = environment; }
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Feature getFeature() { return feature; }
    public void setFeature(Feature feature) { this.feature = feature; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeatureConfig)) return false;
        FeatureConfig that = (FeatureConfig) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
