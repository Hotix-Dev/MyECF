package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("Forme Juridique")
    @Expose
    private String forme_juridique;
    @SerializedName("tiers_id")
    @Expose
    private Double id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Raison Social")
    @Expose
    private String raison_social;
    @SerializedName("responsable")
    @Expose
    private String responsable;
    @SerializedName("GSM")
    @Expose
    private String GSM;

    public Client() {
    }

    public Client(String forme_juridique, Double id, String code, String raison_social, String responsable, String GSM) {
        this.forme_juridique = forme_juridique;
        this.id = id;
        this.code = code;
        this.raison_social = raison_social;
        this.responsable = responsable;
        this.GSM = GSM;
    }

    public String getForme_juridique() {
        return forme_juridique;
    }

    public void setForme_juridique(String forme_juridique) {
        this.forme_juridique = forme_juridique;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRaison_social() {
        return raison_social;
    }

    public void setRaison_social(String raison_social) {
        this.raison_social = raison_social;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getGSM() {
        return GSM;
    }

    public void setGSM(String GSM) {
        this.GSM = GSM;
    }
}
