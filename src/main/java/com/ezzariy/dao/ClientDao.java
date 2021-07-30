package com.ezzariy.dao;

import com.ezzariy.model.Client;

import java.util.List;

public interface ClientDao {

    void addClient(Client client);

    void updateClient(Client client);

    void deleteClientById(long id);

    Client findClientById(long id);

    List<Client> findAllClients();

    List<Client> findAllClientsByKeyword(String keyword);
}
