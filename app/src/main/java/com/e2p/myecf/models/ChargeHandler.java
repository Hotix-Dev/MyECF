package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargeHandler {

    @SerializedName("tiers_id")
    @Expose
    private Double id;
    @SerializedName("Employe")
    @Expose
    private String employee;
    @SerializedName("qualite_responsable_id")
    @Expose
    private Integer responsibleId;
    @SerializedName("Qualité")
    @Expose
    private String quality;
    @SerializedName("lblVoletC")
    @Expose
    private String voletc ;
    @SerializedName("Column1")
    @Expose
    private Double column1;
    @SerializedName("Responsable")
    @Expose
    private Boolean responsable;
    @SerializedName("Date debut")
    @Expose
    private String startDate;
    @SerializedName("Date fin")
    @Expose
    private String endDate;
    @SerializedName("Commentaire")
    @Expose
    private String comment;

    public ChargeHandler() {
    }

    public ChargeHandler(Double id, String employee, Integer responsibleId, String quality, String voletc, Double column1, Boolean responsable, String startDate, String endDate, String comment) {
        this.id = id;
        this.employee = employee;
        this.responsibleId = responsibleId;
        this.quality = quality;
        this.voletc = voletc;
        this.column1 = column1;
        this.responsable = responsable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Integer getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleId(Integer responsibleId) {
        this.responsibleId = responsibleId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getVoletc() {
        return voletc;
    }

    public void setVoletc(String voletc) {
        this.voletc = voletc;
    }

    public Double getColumn1() {
        return column1;
    }

    public void setColumn1(Double column1) {
        this.column1 = column1;
    }

    public Boolean getResponsable() {
        return responsable;
    }

    public void setResponsable(Boolean responsable) {
        this.responsable = responsable;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
