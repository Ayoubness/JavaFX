package com.ezzariy.model;

import java.util.Date;

public class Product {

    private long id;

    private String designation;

    private int qte;

    private double prix;

    private Date date;

    public Product(long id, String designation, int qte, double prix, Date date) {
        this.id = id;
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.date = date;
    }

    public Product(String designation, int qte, double prix, Date date) {
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", qte=" + qte +
                ", prix=" + prix +
                ", date=" + date +
                '}';
    }
}
