package com.e2p.myecf.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientCharge {

    @SerializedName("CODE")
    @Expose
    private String code;
    @SerializedName("Client")
    @Expose
    private String client;
    @SerializedName("Janvier")
    @Expose
    private Double month_1;
    @SerializedName("Février")
    @Expose
    private Double month_2;
    @SerializedName("Mars")
    @Expose
    private Double month_3;
    @SerializedName("Avril")
    @Expose
    private Double month_4;
    @SerializedName("Mai")
    @Expose
    private Double month_5;
    @SerializedName("Juin")
    @Expose
    private Double month_6;
    @SerializedName("Juillet")
    @Expose
    private Double month_7;
    @SerializedName("Août")
    @Expose
    private Double month_8;
    @SerializedName("Septembre")
    @Expose
    private Double month_9;
    @SerializedName("Octobre")
    @Expose
    private Double month_10;
    @SerializedName("Novembre")
    @Expose
    private Double month_11;
    @SerializedName("Décembre")
    @Expose
    private Double month_12;
    @SerializedName("Total")
    @Expose
    private Double total;

    public ClientCharge() {
    }

    public ClientCharge(String code, String client, Double month_1, Double month_2, Double month_3, Double month_4, Double month_5, Double month_6, Double month_7, Double month_8, Double month_9, Double month_10, Double month_11, Double month_12, Double total) {
        this.code = code;
        this.client = client;
        this.month_1 = month_1;
        this.month_2 = month_2;
        this.month_3 = month_3;
        this.month_4 = month_4;
        this.month_5 = month_5;
        this.month_6 = month_6;
        this.month_7 = month_7;
        this.month_8 = month_8;
        this.month_9 = month_9;
        this.month_10 = month_10;
        this.month_11 = month_11;
        this.month_12 = month_12;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Double getMonth_1() {
        return month_1;
    }

    public void setMonth_1(Double month_1) {
        this.month_1 = month_1;
    }

    public Double getMonth_2() {
        return month_2;
    }

    public void setMonth_2(Double month_2) {
        this.month_2 = month_2;
    }

    public Double getMonth_3() {
        return month_3;
    }

    public void setMonth_3(Double month_3) {
        this.month_3 = month_3;
    }

    public Double getMonth_4() {
        return month_4;
    }

    public void setMonth_4(Double month_4) {
        this.month_4 = month_4;
    }

    public Double getMonth_5() {
        return month_5;
    }

    public void setMonth_5(Double month_5) {
        this.month_5 = month_5;
    }

    public Double getMonth_6() {
        return month_6;
    }

    public void setMonth_6(Double month_6) {
        this.month_6 = month_6;
    }

    public Double getMonth_7() {
        return month_7;
    }

    public void setMonth_7(Double month_7) {
        this.month_7 = month_7;
    }

    public Double getMonth_8() {
        return month_8;
    }

    public void setMonth_8(Double month_8) {
        this.month_8 = month_8;
    }

    public Double getMonth_9() {
        return month_9;
    }

    public void setMonth_9(Double month_9) {
        this.month_9 = month_9;
    }

    public Double getMonth_10() {
        return month_10;
    }

    public void setMonth_10(Double month_10) {
        this.month_10 = month_10;
    }

    public Double getMonth_11() {
        return month_11;
    }

    public void setMonth_11(Double month_11) {
        this.month_11 = month_11;
    }

    public Double getMonth_12() {
        return month_12;
    }

    public void setMonth_12(Double month_12) {
        this.month_12 = month_12;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
