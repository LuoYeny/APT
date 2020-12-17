package com.allapt.entity.attack;

import com.allapt.entity.CVE;
import com.allapt.entity.capec.Capec;
import com.allapt.entity.capec.Consequences;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/25 21:44
 */
@NodeEntity
public class AttackPattern {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String label="AttackPattern";
    private String description;
    @Relationship(type = "Mitigates", direction = Relationship.INCOMING)
    private List<AttackMitigations> attackMitigationsList;
    @Relationship(type = "detect", direction = Relationship.INCOMING)
    private  Detection detection;
    @Relationship(type = "targets", direction = Relationship.INCOMING)
    private  List<Platform> platformList;
    @Relationship(type = "uses", direction = Relationship.INCOMING)
    private  DataSources dataSources;
    @Relationship(type = "useCapec", direction = Relationship.INCOMING)
    private Capec capec;
    @Relationship(type = "exploit", direction = Relationship.INCOMING)
    private List<CVE> cveList;

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

    public String getType() {
        return label;
    }

    public void setType(String type) {
        this.label = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<AttackMitigations> getAttackMitigationsList() {
        return attackMitigationsList;
    }

    public void setAttackMitigationsList(List<AttackMitigations> attackMitigationsList) {
        this.attackMitigationsList = attackMitigationsList;
    }

    public Detection getDetection() {
        return detection;
    }

    public void setDetection(Detection detection) {
        this.detection = detection;
    }

    public List<Platform> getPlatformList() {
        return platformList;
    }

    public void setPlatformList(List<Platform> platformList) {
        this.platformList = platformList;
    }

    public DataSources getDataSources() {
        return dataSources;
    }

    public void setDataSources(DataSources dataSources) {
        this.dataSources = dataSources;
    }

    public Capec getCapec() {
        return capec;
    }

    public void setCapec(Capec capec) {
        this.capec = capec;
    }

    public List<CVE> getCveList() {
        return cveList;
    }

    public void setCveList(List<CVE> cveList) {
        this.cveList = cveList;
    }

    @Override
    public String toString() {
        return "AttackPattern{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", attackMitigationsList=" + attackMitigationsList +
                ", detection=" + detection +
                ", platformList=" + platformList +
                ", dataSources=" + dataSources +
                ", capec=" + capec +
                '}';
    }
}
