package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.varnavskii.librarydynamika.repository.ClientRepository;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientEntity createClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public ClientEntity getClientOrThrowException(long id) {
        return clientRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Client with id '%d' not found", id)));
    }

    @Override
    public ClientEntity updateClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public Page<ClientEntity> getClients(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest);
    }

    @Override
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        clientRepository.deleteAllById(ids);
    }
}
