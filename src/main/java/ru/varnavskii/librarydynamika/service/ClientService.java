package ru.varnavskii.librarydynamika.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;

import java.util.List;

public interface ClientService {

    ClientEntity createClient(ClientEntity client);

    ClientEntity getClientOrThrowException(long id);

    ClientEntity updateClient(ClientEntity client);

    Page<ClientEntity> getClients(PageRequest pageRequest);

    void deleteClient(long id);

    void deleteByIds(List<Long> ids);
}
