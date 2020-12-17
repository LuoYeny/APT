package com.allapt.entity.nvd;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/10/26 16:37
 */
@NodeEntity
public class CVSS {
    @Id
    @GeneratedValue
    private Long id;
    private String lable="CVSS";
    private String name;
    private String version;
    private String vectorString;
    private String attackVector;
    private String attackComplexity;
    private String privilegesRequired;
    private String userInteraction;
    private String scope;
    private String confidentialityImpact;
    private String integrityImpact;
    private String availabilityImpact;
    private String baseScore;
    private String baseSeverity;
    private String severity;
    private String exploitabilityScore;
    private String impactScore;
    private String acInsufInfo;
    private String obtainAllPrivilege;
    private String obtainUserPrivilege;
    private String obtainOtherPrivilege;
    private String userInteractionRequired;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVectorString() {
        return vectorString;
    }

    public void setVectorString(String vectorString) {
        this.vectorString = vectorString;
    }

    public String getAttackVector() {
        return attackVector;
    }

    public void setAttackVector(String attackVector) {
        this.attackVector = attackVector;
    }

    public String getAttackComplexity() {
        return attackComplexity;
    }

    public void setAttackComplexity(String attackComplexity) {
        this.attackComplexity = attackComplexity;
    }

    public String getPrivilegesRequired() {
        return privilegesRequired;
    }

    public void setPrivilegesRequired(String privilegesRequired) {
        this.privilegesRequired = privilegesRequired;
    }

    public String getUserInteraction() {
        return userInteraction;
    }

    public void setUserInteraction(String userInteraction) {
        this.userInteraction = userInteraction;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getConfidentialityImpact() {
        return confidentialityImpact;
    }

    public void setConfidentialityImpact(String confidentialityImpact) {
        this.confidentialityImpact = confidentialityImpact;
    }

    public String getIntegrityImpact() {
        return integrityImpact;
    }

    public void setIntegrityImpact(String integrityImpact) {
        this.integrityImpact = integrityImpact;
    }

    public String getAvailabilityImpact() {
        return availabilityImpact;
    }

    public void setAvailabilityImpact(String availabilityImpact) {
        this.availabilityImpact = availabilityImpact;
    }

    public String getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(String baseScore) {
        this.baseScore = baseScore;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getExploitabilityScore() {
        return exploitabilityScore;
    }

    public void setExploitabilityScore(String exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    public String getImpactScore() {
        return impactScore;
    }

    public void setImpactScore(String impactScore) {
        this.impactScore = impactScore;
    }

    public String getAcInsufInfo() {
        return acInsufInfo;
    }

    public void setAcInsufInfo(String acInsufInfo) {
        this.acInsufInfo = acInsufInfo;
    }

    public String getObtainAllPrivilege() {
        return obtainAllPrivilege;
    }

    public void setObtainAllPrivilege(String obtainAllPrivilege) {
        this.obtainAllPrivilege = obtainAllPrivilege;
    }

    public String getObtainUserPrivilege() {
        return obtainUserPrivilege;
    }

    public void setObtainUserPrivilege(String obtainUserPrivilege) {
        this.obtainUserPrivilege = obtainUserPrivilege;
    }

    public String getObtainOtherPrivilege() {
        return obtainOtherPrivilege;
    }

    public void setObtainOtherPrivilege(String obtainOtherPrivilege) {
        this.obtainOtherPrivilege = obtainOtherPrivilege;
    }

    public String getUserInteractionRequired() {
        return userInteractionRequired;
    }

    public void setUserInteractionRequired(String userInteractionRequired) {
        this.userInteractionRequired = userInteractionRequired;
    }
}
