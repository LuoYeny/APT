package com.allapt.entity.nvd;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.List;

/**
 * @author 罗叶妮
 * @version 1.0
 * @date 2020/11/11 10:51
 */
@NodeEntity
public class Reference {
    @Id
    @GeneratedValue
    private Long id;
    private String lable="Reference";
    private String url;
    private String name;
    private String refsource;
    private List<String> tagList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefsource() {
        return refsource;
    }

    public void setRefsource(String refsource) {
        this.refsource = refsource;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}
