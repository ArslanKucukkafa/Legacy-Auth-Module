package com.arslankucukkafa.labormarketauth.util.hierarchy;


import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
public class HierarchicalEntity<T extends HierarchicalEntity<T>> {

    @Id
    private Long id;

    private String description;

    @Nullable
    private HierarchicalEntity parent;

    @Nullable
    private Set<HierarchicalEntity> children = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Nullable
    public HierarchicalEntity getParent() {
        return parent;
    }

    public void setParent(@Nullable HierarchicalEntity parent) {
        this.parent = parent;
    }

    @Nullable
    public Set<HierarchicalEntity> getChildren() {
        return children;
    }

    public void setChildren(@Nullable Set<HierarchicalEntity> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
