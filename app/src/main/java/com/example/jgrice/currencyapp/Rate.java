package com.example.jgrice.currencyapp;

import java.util.Date;

public class Rate {
    private long id;
    private Date refDate;
    private String currency;
    private String vector;
    private float value;

    public Rate() {
        refDate = new Date();
        currency = "ZZZ";
        vector = "invalid";
        value = 1;
    }

    public Rate(long id, Date refDate, String currency, String vector, float value) {
        this.id = id;
        this.refDate = refDate;
        this.refDate = refDate;
        this.currency = currency;
        this.vector = vector;
        this.value = value;
    }

    public void setDateString(String refDate) {
        this.refDate = new Date(Integer.valueOf(refDate.substring(0, 4))-1900,Integer.valueOf(refDate.substring(5, 7)), 0);
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", refDate='" + refDate + '\'' +
                ", currency='" + currency + '\'' +
                ", vector='" + vector + '\'' +
                ", value=" + value +
                '}';
    }

    public Date getRefDate() {
        return refDate;
    }

    public void setRefDate(Date refDate) {
        this.refDate = refDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
