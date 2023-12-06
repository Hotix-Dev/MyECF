package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Client {

    @SerializedName("FormeJuridique")
    @Expose
    private String formeJuridique;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("responsable")
    @Expose
    private String responsable;
    @SerializedName("GSM")
    @Expose
    private String GSM;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("MyECFPwd")
    @Expose
    private String myECFPwd;
    @SerializedName("IsAdmin")
    @Expose
    private Boolean isAdmin;

    public Client() {
    }

    public Client(String formeJuridique, Integer id, String code, String name, String responsable, String GSM, String email, String myECFPwd) {
        this.formeJuridique = formeJuridique;
        this.id = id;
        this.code = code;
        this.name = name;
        this.responsable = responsable;
        this.GSM = GSM;
        this.email = email;
        this.myECFPwd = myECFPwd;
    }

    public Client(Integer id, String code, String name, String GSM, String email, String myECFPwd, Boolean isAdmin) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.GSM = GSM;
        this.email = email;
        this.myECFPwd = myECFPwd;
        this.isAdmin = isAdmin;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMyECFPwd() {
        return myECFPwd;
    }

    public void setMyECFPwd(String myECFPwd) {
        this.myECFPwd = myECFPwd;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
