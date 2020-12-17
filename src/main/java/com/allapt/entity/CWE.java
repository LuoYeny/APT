package com.allapt.entity;

import org.neo4j.ogm.annotation.*;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.repository.query.Param;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/25 21:42
 */
@NodeEntity
public class CWE {
    @Id
    @GeneratedValue
    private Long id;
    private String CWE;
    private String Name;
    @Property(name = "Weakness Abstraction")
    private String WeaknessAbstraction;
    private String Status;
    private String Description;
    @Property(name = "Extended Description")
    private String ExtendedDescription;
    @Property(name = "Related Weaknesses")
    private String RelatedWeaknesses;
    @Property(name = "Weakness Ordinalities")
    private String WeaknessOrdinalities;
    @Property(name = "Applicable Platforms")
    private String ApplicablePlatforms;
    @Property(name = "Background Details")
    private String BackgroundDetails;
    @Property(name = "Alternate Terms")
    private String  AlternateTerms;
    @Property(name = "Modes Of Introduction")
    private String 	ModesOfIntroduction;
    @Property(name = "Exploitation Factors")
    private String 	ExploitationFactors;
    @Property(name = "Likelihood of Exploit")
    private String LikelihoodofExploit;
    @Property(name = "Common Consequences")
    private String CommonConsequences;
    @Property(name = "Detection Methods")
    private String DetectionMethods;
    @Property(name = "Potential Mitigations")
    private String 	PotentialMitigations;
    @Property(name = "Observed Examples")
    private String 	ObservedExamples;
    @Property(name = "Functional Areas")
    private String FunctionalAreas;
    @Property(name = "Affected Resources")
    private String AffectedResources;
    @Property(name = "Taxonomy Mappings")
    private String TaxonomyMappings;
    @Property(name = "Related Attack Patterns")
    private String RelatedAttackPatterns;
    private String Notes;

    @Override
    public String toString() {
        return "CWE{" +
                "id=" + id +
                ", CWE='" + CWE + '\'' +
                ", name='" + WeaknessAbstraction + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCWE() {
        return CWE;
    }

    public void setCWE(String CWE) {
        this.CWE = CWE;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getWeaknessAbstraction() {
        return WeaknessAbstraction;
    }

    public void setWeaknessAbstraction(String weaknessAbstraction) {
        WeaknessAbstraction = weaknessAbstraction;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getExtendedDescription() {
        return ExtendedDescription;
    }

    public void setExtendedDescription(String extendedDescription) {
        ExtendedDescription = extendedDescription;
    }

    public String getRelatedWeaknesses() {
        return RelatedWeaknesses;
    }

    public void setRelatedWeaknesses(String relatedWeaknesses) {
        RelatedWeaknesses = relatedWeaknesses;
    }

    public String getWeaknessOrdinalities() {
        return WeaknessOrdinalities;
    }

    public void setWeaknessOrdinalities(String weaknessOrdinalities) {
        WeaknessOrdinalities = weaknessOrdinalities;
    }

    public String getApplicablePlatforms() {
        return ApplicablePlatforms;
    }

    public void setApplicablePlatforms(String applicablePlatforms) {
        ApplicablePlatforms = applicablePlatforms;
    }

    public String getBackgroundDetails() {
        return BackgroundDetails;
    }

    public void setBackgroundDetails(String backgroundDetails) {
        BackgroundDetails = backgroundDetails;
    }

    public String getAlternateTerms() {
        return AlternateTerms;
    }

    public void setAlternateTerms(String alternateTerms) {
        AlternateTerms = alternateTerms;
    }

    public String getModesOfIntroduction() {
        return ModesOfIntroduction;
    }

    public void setModesOfIntroduction(String modesOfIntroduction) {
        ModesOfIntroduction = modesOfIntroduction;
    }

    public String getExploitationFactors() {
        return ExploitationFactors;
    }

    public void setExploitationFactors(String exploitationFactors) {
        ExploitationFactors = exploitationFactors;
    }

    public String getLikelihoodofExploit() {
        return LikelihoodofExploit;
    }

    public void setLikelihoodofExploit(String likelihoodofExploit) {
        LikelihoodofExploit = likelihoodofExploit;
    }

    public String getCommonConsequences() {
        return CommonConsequences;
    }

    public void setCommonConsequences(String commonConsequences) {
        CommonConsequences = commonConsequences;
    }

    public String getDetectionMethods() {
        return DetectionMethods;
    }

    public void setDetectionMethods(String detectionMethods) {
        DetectionMethods = detectionMethods;
    }

    public String getPotentialMitigations() {
        return PotentialMitigations;
    }

    public void setPotentialMitigations(String potentialMitigations) {
        PotentialMitigations = potentialMitigations;
    }

    public String getObservedExamples() {
        return ObservedExamples;
    }

    public void setObservedExamples(String observedExamples) {
        ObservedExamples = observedExamples;
    }

    public String getFunctionalAreas() {
        return FunctionalAreas;
    }

    public void setFunctionalAreas(String functionalAreas) {
        FunctionalAreas = functionalAreas;
    }

    public String getAffectedResources() {
        return AffectedResources;
    }

    public void setAffectedResources(String affectedResources) {
        AffectedResources = affectedResources;
    }

    public String getTaxonomyMappings() {
        return TaxonomyMappings;
    }

    public void setTaxonomyMappings(String taxonomyMappings) {
        TaxonomyMappings = taxonomyMappings;
    }

    public String getRelatedAttackPatterns() {
        return RelatedAttackPatterns;
    }

    public void setRelatedAttackPatterns(String relatedAttackPatterns) {
        RelatedAttackPatterns = relatedAttackPatterns;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
