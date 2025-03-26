package ru.varnavskii.librarydynamika.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ClientEntity createClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientEntity getClientOrThrowException(long id) {
        return clientRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Client with id '%d' not found", id)));
    }

    @Override
    @Transactional
    public ClientEntity updateClient(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientEntity> getClients(PageRequest pageRequest) {
        return clientRepository.findAll(pageRequest);
    }

    @Override
    @Transactional
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        clientRepository.deleteAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientEntity> getClients(Specification<ClientEntity> specification, PageRequest pageRequest) {
        return clientRepository.findAll(specification, pageRequest);
    }
}
