package com.allapt.entity;

import com.allapt.entity.attack.AttackPattern;
import com.allapt.entity.attack.Software;
import com.allapt.entity.capec.Consequences;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/14 15:46
 */
@NodeEntity
public class Group {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String Description;
    private String label="Group";
    public Long getId() {
        return id;
    }
    @Relationship(type = "uses", direction = Relationship.OUTGOING)
    private List<AttackPattern> attackPatternList;
    @Relationship(type = "uses", direction = Relationship.OUTGOING)
    private List<Software> softwareList;
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

    public List<AttackPattern> getAttackPatternList() {
        return attackPatternList;
    }

    public void setAttackPatternList(List<AttackPattern> attackPatternList) {
        this.attackPatternList = attackPatternList;
    }

    public List<Software> getSoftwareList() {
        return softwareList;
    }

    public void setSoftwareList(List<Software> softwareList) {
        this.softwareList = softwareList;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", label='" + label + '\'' +
                ", attackPatternList=" + attackPatternList +
                ", softwareList=" + softwareList +
                '}';
    }
}
