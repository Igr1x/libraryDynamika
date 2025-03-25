package ru.varnavskii.librarydynamika.service;

import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

import java.util.List;

public interface ClientService {

    ClientEntity createClient(ClientEntity client);

    ClientEntity getClientOrThrowException(long id);

    ClientEntity updateClient(ClientEntity client);

    List<ClientEntity> getAllClients();

    void deleteClient(long id);
}
