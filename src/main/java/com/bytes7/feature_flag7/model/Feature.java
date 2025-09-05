package com.bytes7.feature_flag7.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(
    name = "features",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_feature_key", columnNames = "feature_key")
    }
)
public class Feature {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false, length = 150, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled_by_default", nullable = false)
    private Boolean enabledByDefault = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
        mappedBy = "feature",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<FeatureConfig> configs = new ArrayList<>();

    // Constructor vacío
    public Feature() {}

    public Feature(String name, String description, Boolean enabledByDefault) {
        this.name = name;
        this.description = description;
        this.enabledByDefault = enabledByDefault;
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

    public void addConfig(FeatureConfig config) {
        config.setFeature(this);
        this.configs.add(config);
    }

    public void removeConfig(FeatureConfig config) {
        config.setFeature(null);
        this.configs.remove(config);
    }

    // GETTERS Y SETTERS

    public UUID getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getEnabledByDefault() { return enabledByDefault; }
    public void setEnabledByDefault(Boolean enabledByDefault) { this.enabledByDefault = enabledByDefault; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<FeatureConfig> getConfigs() { return configs; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Feature)) return false;
        Feature feature = (Feature) o;
        return Objects.equals(id, feature.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
