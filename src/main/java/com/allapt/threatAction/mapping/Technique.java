package com.allapt.threatAction.mapping;

import java.util.LinkedHashMap;
import java.util.Map;

public class Technique {
    private String action;
    private String techId;
    private String technique;
    private String tactic;
    private String score;
    private int count;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTechId() {
        return techId;
    }

    public void setTechId(String techId) {
        this.techId = techId;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Technique{" +
                "action='" + action + '\'' +
                ", techId='" + techId + '\'' +
                ", technique='" + technique + '\'' +
                ", tactic='" + tactic + '\'' +
                ", socre='" + score + '\'' +
                '}';
    }


    public boolean equels(Technique technique){
        if(this.techId==technique.techId)
            return true;
        else return false;
    }

}
