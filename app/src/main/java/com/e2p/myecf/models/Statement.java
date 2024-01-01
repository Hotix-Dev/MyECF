package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statement {

    @SerializedName("Ordre")
    @Expose
    private Integer ordre;
    @SerializedName("grpCode")
    @Expose
    private String grpCode;
    @SerializedName("grpName")
    @Expose
    private String grpName;
    @SerializedName("Column1")
    @Expose
    private String column1;
    @SerializedName("Column2")
    @Expose
    private String column2;
    @SerializedName("exercice_id")
    @Expose
    private Integer exercice_id;
    @SerializedName("Column3")
    @Expose
    private Integer column3;
    @SerializedName("Column4")
    @Expose
    private Boolean column4;
    @SerializedName("client_planning_a_p_ordre")
    @Expose
    private Integer client_planning_a_p_ordre;
    @SerializedName("Column5")
    @Expose
    private Double column5;
    @SerializedName("Column6")
    @Expose
    private String column6;
    @SerializedName("nbreDepot")
    @Expose
    private Integer nbreDepot;
    @SerializedName("Libelle1")
    @Expose
    private String libelle1;
    @SerializedName("Libelle2")
    @Expose
    private String libelle2;
    @SerializedName("Color")
    @Expose
    private String color;

    public Statement() {
    }

    public Statement(Integer ordre, String grpCode, String grpName, String column1, String column2, Integer exercice_id, Integer column3, Boolean column4, Integer client_planning_a_p_ordre, Double column5, String column6, Integer nbreDepot, String libelle1, String libelle2, String color) {
        this.ordre = ordre;
        this.grpCode = grpCode;
        this.grpName = grpName;
        this.column1 = column1;
        this.column2 = column2;
        this.exercice_id = exercice_id;
        this.column3 = column3;
        this.column4 = column4;
        this.client_planning_a_p_ordre = client_planning_a_p_ordre;
        this.column5 = column5;
        this.column6 = column6;
        this.nbreDepot = nbreDepot;
        this.libelle1 = libelle1;
        this.libelle2 = libelle2;
        this.color = color;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public String getGrpCode() {
        return grpCode;
    }

    public void setGrpCode(String grpCode) {
        this.grpCode = grpCode;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getColumn1() {
        return column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }

    public Integer getExercice_id() {
        return exercice_id;
    }

    public void setExercice_id(Integer exercice_id) {
        this.exercice_id = exercice_id;
    }

    public Integer getColumn3() {
        return column3;
    }

    public void setColumn3(Integer column3) {
        this.column3 = column3;
    }

    public Boolean getColumn4() {
        return column4;
    }

    public void setColumn4(Boolean column4) {
        this.column4 = column4;
    }

    public Integer getClient_planning_a_p_ordre() {
        return client_planning_a_p_ordre;
    }

    public void setClient_planning_a_p_ordre(Integer client_planning_a_p_ordre) {
        this.client_planning_a_p_ordre = client_planning_a_p_ordre;
    }

    public Double getColumn5() {
        return column5;
    }

    public void setColumn5(Double column5) {
        this.column5 = column5;
    }

    public String getColumn6() {
        return column6;
    }

    public void setColumn6(String column6) {
        this.column6 = column6;
    }

    public Integer getNbreDepot() {
        return nbreDepot;
    }

    public void setNbreDepot(Integer nbreDepot) {
        this.nbreDepot = nbreDepot;
    }
    public String getLibelle1() {
        return libelle1;
    }

    public void setLibelle1(String libelle1) {
        this.libelle1 = libelle1;
    }

    public String getLibelle2() {
        return libelle2;
    }

    public void setLibelle2(String libelle2) {
        this.libelle2 = libelle2;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
