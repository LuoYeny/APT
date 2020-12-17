package com.allapt.entity.capec;

import com.allapt.entity.CWE;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/14 15:35
 */
@NodeEntity
public class Capec {
    @Id
    @GeneratedValue
    private Long id;
    private String capecId;
    private String name;
    private String Description;
    private String likelihoodOfAttack;
    private String label="Capec";
    @Relationship(type = "hasConsequences ", direction = Relationship.OUTGOING)
    private List<Consequences> consequencesList;
    @Relationship(type = "indicates ", direction = Relationship.INCOMING)
    private List<Indicators> indicatorsList;
    @Relationship(type = "hasInstance", direction = Relationship.OUTGOING)
    private List<Instances> instancesList;
    @Relationship(type = "Mitigates", direction = Relationship.OUTGOING)
    private List<Mitigations> mitigationsList;
    @Relationship(type = "include", direction = Relationship.OUTGOING)
    private List<Prerequisites> prerequisitesList;
    @Relationship(type = "include", direction = Relationship.OUTGOING)
    private List<Resources> resourcesList;
    @Relationship(type = "include ", direction = Relationship.OUTGOING)
    private List<SkillsRequired> skillsRequiredList;
    @Relationship(type = "exploit", direction = Relationship.OUTGOING)
    private List<CWE> cweList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapecId() {
        return capecId;
    }

    public void setCapecId(String capecId) {
        this.capecId = capecId;
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

    public String getLikelihoodOfAttack() {
        return likelihoodOfAttack;
    }

    public void setLikelihoodOfAttack(String likelihoodOfAttack) {
        this.likelihoodOfAttack = likelihoodOfAttack;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Consequences> getConsequencesList() {
        return consequencesList;
    }

    public void setConsequencesList(List<Consequences> consequencesList) {
        this.consequencesList = consequencesList;
    }

    public List<Indicators> getIndicatorsList() {
        return indicatorsList;
    }

    public void setIndicatorsList(List<Indicators> indicatorsList) {
        this.indicatorsList = indicatorsList;
    }

    public List<Instances> getInstancesList() {
        return instancesList;
    }

    public void setInstancesList(List<Instances> instancesList) {
        this.instancesList = instancesList;
    }

    public List<Mitigations> getMitigationsList() {
        return mitigationsList;
    }

    public void setMitigationsList(List<Mitigations> mitigationsList) {
        this.mitigationsList = mitigationsList;
    }

    public List<Prerequisites> getPrerequisitesList() {
        return prerequisitesList;
    }

    public void setPrerequisitesList(List<Prerequisites> prerequisitesList) {
        this.prerequisitesList = prerequisitesList;
    }

    public List<Resources> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<Resources> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public List<SkillsRequired> getSkillsRequiredList() {
        return skillsRequiredList;
    }

    public void setSkillsRequiredList(List<SkillsRequired> skillsRequiredList) {
        this.skillsRequiredList = skillsRequiredList;
    }

    public List<CWE> getCweList() {
        return cweList;
    }

    public void setCweList(List<CWE> cweList) {
        this.cweList = cweList;
    }
    //    @Override
//    public String toString() {
//        return "Capec{" +
//                "id=" + id +
//                ", capecId='" + capecId + '\'' +
//                ", name='" + name + '\'' +
//                ", Description='" + Description + '\'' +
//                ", likelihoodOfAttack='" + likelihoodOfAttack + '\'' +
//                ", label='" + label + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "Capec{" +
                "capecId='" + capecId + '\'' +
                ", likelihoodOfAttack='" + likelihoodOfAttack + '\'' +
                '}';
    }
}
