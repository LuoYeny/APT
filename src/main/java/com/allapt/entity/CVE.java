package com.allapt.entity;

import com.allapt.entity.nvd.Asset;
import com.allapt.entity.nvd.CVSS;
import com.allapt.entity.nvd.Reference;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/7/25 21:42
 */
@NodeEntity
public class CVE {
    @Id
    @GeneratedValue
    private Long id;
    private String CVE;
    private String Description;
    private String References;
    private String Phase;
    private String Votes;
    private String Comments;
    private String publishedDate;
    private String lastModifiedDate;
    @Relationship(type = "problemtype", direction = Relationship.INCOMING)
    private  CWE cwe;
    @Relationship(type = "influence", direction = Relationship.OUTGOING)
    private List<Asset> assetList;

    @Relationship(type = "impact", direction = Relationship.OUTGOING)
    private CVSS cvss2;
    @Relationship(type = "impact", direction = Relationship.OUTGOING)
    private CVSS cvss3;
    @Relationship(type = "associate", direction = Relationship.OUTGOING)
    private List<Reference> referenceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCVE() {
        return CVE;
    }

    public void setCVE(String CVE) {
        this.CVE = CVE;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getReferences() {
        return References;
    }

    public void setReferences(String references) {
        References = references;
    }

    public String getPhase() {
        return Phase;
    }

    public void setPhase(String phase) {
        Phase = phase;
    }

    public String getVotes() {
        return Votes;
    }

    public void setVotes(String votes) {
        Votes = votes;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public CWE getCwe() {
        return cwe;
    }

    public void setCwe(CWE cwe) {
        this.cwe = cwe;
    }

    public List<Asset> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<Asset> assetList) {
        this.assetList = assetList;
    }

    public CVSS getCvss2() {
        return cvss2;
    }

    public void setCvss2(CVSS cvss2) {
        this.cvss2 = cvss2;
    }

    public CVSS getCvss3() {
        return cvss3;
    }

    public void setCvss3(CVSS cvss3) {
        this.cvss3 = cvss3;
    }

    public List<Reference> getReferenceList() {
        return referenceList;
    }

    public void setReferenceList(List<Reference> referenceList) {
        this.referenceList = referenceList;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "CVE{" +
                "CVE='" + CVE + '\'' +
                '}';
    }
}
