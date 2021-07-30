package com.ezzariy.model;

public class LigneCommande {

    private long id;

    private int qte;

    private double total;

    private Product product;

    public LigneCommande() {
    }

    public LigneCommande(long id, int qte, double total, Product product) {
        this.id = id;
        this.qte = qte;
        this.total = total;
        this.product = product;
    }

    public LigneCommande(int qte, Product product) {
        this.qte = qte;
        this.total = qte * product.getPrix();
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public double getTotal() {
        return qte * product.getPrix();
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
