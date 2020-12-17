package com.allapt.threatAction.identification;

public class Action {
    private String nsubj="";
    private String obj="";
    private String verb="";
    private String compound="";
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

    @Override
    public String toString() {
        return nsubj + " " +verb + " " + (compound==""?"":(compound+ " "))+obj;
    }
}
