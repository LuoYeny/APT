package com.allapt.ner;

import java.util.Set;

public class NerResult {
    private Set<String> group;
    private Set<String> industy;
    private Set<String> Location;
    private Set<String> url;
    private Set<String> tool;
    private Set<String> cve;
    private Set<String> ip;

    public Set<String> getGroup() {
        return group;
    }

    public void setGroup(Set<String> group) {
        this.group = group;
    }

    public Set<String> getIndusty() {
        return industy;
    }

    public void setIndusty(Set<String> industy) {
        this.industy = industy;
    }

    public Set<String> getLocation() {
        return Location;
    }

    public void setLocation(Set<String> location) {
        Location = location;
    }

    public Set<String> getUrl() {
        return url;
    }

    public void setUrl(Set<String> url) {
        this.url = url;
    }

    public Set<String> getTool() {
        return tool;
    }

    public void setTool(Set<String> tool) {
        this.tool = tool;
    }

    public Set<String> getCve() {
        return cve;
    }

    public void setCve(Set<String> cve) {
        this.cve = cve;
    }

    public Set<String> getIp() {
        return ip;
    }

    public void setIp(Set<String> ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "NerResult{" +
                "group=" + group +
                ", industy=" + industy +
                ", Location=" + Location +
                ", url=" + url +
                ", tool=" + tool +
                ", cve=" + cve +
                ", ip=" + ip +
                '}';
    }
}
