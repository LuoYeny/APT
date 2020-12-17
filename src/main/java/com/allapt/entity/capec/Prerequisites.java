package com.allapt.entity.capec;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/14 15:46
 */
@NodeEntity
public class Prerequisites {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String Description;
    private String label="Prerequisites";
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Prerequisites{" +
                "name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
