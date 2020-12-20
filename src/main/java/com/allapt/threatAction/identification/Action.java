package com.allapt.threatAction.identification;

public class Action {
    private String nsubjCompound="";
    private String nsubj="";
    private String obj="";
    private String verb="";
    private String compound="";
    private String amod="";

    public String getNsubjCompound() {
        return nsubjCompound;
    }

    public void setNsubjCompound(String nsubjCompound) {
        this.nsubjCompound = nsubjCompound;
    }

    public String getNsubj() {
        return nsubj;
    }

    public void setNsubj(String nsubj) {
        this.nsubj = nsubj;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getCompound() {
        return compound;
    }

    public void setCompound(String compound) {
        this.compound = compound;
    }

    public String getAmod() {
        return amod;
    }

    public void setAmod(String amod) {
        this.amod = amod;
    }

    @Override
    public String toString() {
        return (nsubjCompound==""?"":(nsubjCompound+ " "))+nsubj + " " +verb + " " + (amod==""?"":(amod+ " "))+ (compound==""?"":(compound+ " "))+obj;
    }
}