package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("tiers_id")
    @Expose
    private Double id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Raison")
    @Expose
    private String raison;

    public Client(Double id, String code, String raison) {
        this.id = id;
        this.code = code;
        this.raison = raison;
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

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

}
