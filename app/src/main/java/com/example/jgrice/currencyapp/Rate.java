package com.example.jgrice.currencyapp;

public class Rate {
    private long id;
    private String refDate;
    private String currency;
    private String vector;
    private float value;

    public Rate() {
        refDate = "1950/10";
        currency = "ZZZ";
        vector = "invalid";
        value = 1;
    }

    public Rate(long id, String refDate, String currency, String vector, float value) {
        this.id = id;
        this.refDate = refDate;
        this.currency = currency;
        this.vector = vector;
        this.value = value;
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

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
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
