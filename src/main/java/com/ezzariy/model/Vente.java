package com.ezzariy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Vente {

    private long id;

    private Date date;

    private Client client;

    private List<LigneCommande> ligneCommandes;

    public Vente() {
    }

    public Vente(long id, Date date, Client client, List<LigneCommande> ligneCommandes) {
        this.id = id;
        this.date = date;
        this.client = client;
        this.ligneCommandes = ligneCommandes;
    }

    public Vente(Date date, Client client, List<LigneCommande> ligneCommandes) {
        this.date = date;
        this.client = client;
        this.ligneCommandes = ligneCommandes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public void addLigneCommande(LigneCommande ligneCommande) {
        if (Objects.isNull(this.ligneCommandes))
            this.ligneCommandes = new ArrayList<>();
        this.ligneCommandes.add(ligneCommande);
    }
}
