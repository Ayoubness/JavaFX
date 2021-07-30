package com.ezzariy.dao;

import com.ezzariy.model.Vente;

import java.util.List;

public interface VenteDao {

    void addVente(Vente vente);

    void updateVente(Vente vente);

    void deleteVente(Vente vente);

    Vente findVenteById(long id);

    List<Vente> findAllVentes();

    List<Vente> findAllVentesByKeyword(String keyword);
}
