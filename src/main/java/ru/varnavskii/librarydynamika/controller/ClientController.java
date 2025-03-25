package ru.varnavskii.librarydynamika.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.varnavskii.librarydynamika.common.utils.PaginationUtils;
import ru.varnavskii.librarydynamika.controller.dto.ClientIn;
import ru.varnavskii.librarydynamika.controller.mapping.ClientMapper;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientController {

    private static final String CLIENT_LIST_VIEW = "client/clientList";
    private static final String CLIENT_DETAIL_VIEW = "client/clientDetail";
    private static final String CLIENT_ATTRIBUTE_NAME = "client";
    private static final String CLIENTS_ATTRIBUTE_NAME = "clients";
    private static final String NEW_CLIENT_ATTRIBUTE_NAME = "newClient";

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping("/list")
    public ModelAndView getAllClients(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      ModelAndView modelAndView) {
        Page<ClientEntity> clients = clientService.getClients(PageRequest.of(page, size));

        modelAndView.addObject(CLIENTS_ATTRIBUTE_NAME, clients.getContent());
        modelAndView.addObject(PaginationUtils.CURRENT_PAGE_ATTRIBUTE, page);
        modelAndView.addObject(PaginationUtils.TOTAL_PAGES_ATTRIBUTE, clients.getTotalPages());
        modelAndView.addObject(NEW_CLIENT_ATTRIBUTE_NAME, new ClientIn(null, null, null, null));
        modelAndView.setViewName(CLIENT_LIST_VIEW);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getClient(@PathVariable int id, ModelAndView modelAndView) {
        ClientEntity client = clientService.getClientOrThrowException(id);

        modelAndView.setViewName(CLIENT_DETAIL_VIEW);
        modelAndView.addObject(CLIENT_ATTRIBUTE_NAME, clientMapper.toOut(client));
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(String.format("Client with id %d deleted", id));
    }

    @PostMapping("/delete")
    public ModelAndView deleteClientsByIds(@RequestParam List<Long> idsToDelete,
                                           @RequestParam int page,
                                           ModelAndView modelAndView) {
        clientService.deleteByIds(idsToDelete);
        modelAndView.setViewName("redirect:/client/list?page=" + page);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView createClient(@Valid @ModelAttribute(NEW_CLIENT_ATTRIBUTE_NAME) ClientIn clientIn,
                                     BindingResult result,
                                     ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName(CLIENT_LIST_VIEW);
            return modelAndView;
        }

        ClientEntity clientEntity = clientMapper.toEntity(clientIn);
        clientEntity = clientService.createClient(clientEntity);

        modelAndView.setViewName("redirect:/client/" + clientEntity.getId());
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView updateClient(@PathVariable int id,
                                     @Valid @ModelAttribute ClientIn clientIn,
                                     BindingResult result,
                                     ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName("redirect:/client/" + id);
            return modelAndView;
        }

        ClientEntity clientEntity = clientMapper.toEntity(clientIn);
        clientEntity.setId((long) id);
        clientEntity = clientService.updateClient(clientEntity);

        modelAndView.setViewName(CLIENT_DETAIL_VIEW);
        modelAndView.addObject(CLIENT_ATTRIBUTE_NAME, clientMapper.toOut(clientEntity));
        return modelAndView;
    }

}
