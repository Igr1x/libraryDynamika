package ru.varnavskii.librarydynamika.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.varnavskii.librarydynamika.controller.dto.ClientIn;
import ru.varnavskii.librarydynamika.controller.dto.ClientOut;
import ru.varnavskii.librarydynamika.controller.mapping.ClientMapping;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapping clientMapping;

    @GetMapping("/{id}")
    public ClientOut getClient(@PathVariable int id) {
        ClientEntity client = clientService.getClientOrThrowException(id);
        return clientMapping.toOut(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(String.format("Client with id %d deleted", id));
    }

    @PostMapping
    public ClientOut createClient(@Valid @RequestBody ClientIn clientIn) {
        ClientEntity clientEntity = clientMapping.toEntity(clientIn);
        clientEntity = clientService.createClient(clientEntity);
        return clientMapping.toOut(clientEntity);
    }

    @PutMapping("/{id}")
    public ClientOut updateClient(@PathVariable int id,
                                  @Valid @RequestBody ClientIn clientIn) {
        ClientEntity clientEntity = clientMapping.toEntity(clientIn);
        clientEntity.setId((long) id);
        clientEntity = clientService.updateClient(clientEntity);
        return clientMapping.toOut(clientEntity);
    }

}
