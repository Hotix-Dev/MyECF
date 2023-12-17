package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatementsGroup {

    @SerializedName("GroupOrdre")
    @Expose
    private Integer groupOrdre;
    @SerializedName("GroupCode")
    @Expose
    private String groupCode;
    @SerializedName("groupName")
    @Expose
    private String groupName;
    @SerializedName("IsExpandable")
    @Expose
    private Boolean isExpandable;
    @SerializedName("Statements")
    @Expose
    private ArrayList<Statement> statements;

    public StatementsGroup() {
        this.statements = new ArrayList<>();
    }

    public StatementsGroup(Integer groupOrdre, String groupCode, String groupName, Boolean isExpandable) {
        this.groupOrdre = groupOrdre;
        this.groupCode = groupCode;
        this.groupName = groupName;
        this.isExpandable = isExpandable;
        this.statements = new ArrayList<>();
    }

    public Integer getGroupOrdre() {
        return groupOrdre;
    }

    public void setGroupOrdre(Integer groupOrdre) {
        this.groupOrdre = groupOrdre;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getExpandable() {
        return isExpandable;
    }

    public void setExpandable(Boolean expandable) {
        isExpandable = expandable;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Statement> statements) {
        this.statements = statements;
    }
}
