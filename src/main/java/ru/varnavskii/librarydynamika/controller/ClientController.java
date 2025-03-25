package ru.varnavskii.librarydynamika.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ru.varnavskii.librarydynamika.controller.dto.ClientIn;
import ru.varnavskii.librarydynamika.controller.mapping.ClientMapping;
import ru.varnavskii.librarydynamika.repository.entity.ClientEntity;
import ru.varnavskii.librarydynamika.service.ClientService;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/client", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ClientController {

    private static final String CLIENT_LIST_VIEW = "clientList";
    private static final String CLIENT_DETAIL_VIEW = "clientDetail";

    private final ClientService clientService;
    private final ClientMapping clientMapping;

    @GetMapping("/list")
    public ModelAndView getAllClients(ModelAndView modelAndView) {
        List<ClientEntity> allClients = clientService.getAllClients();

        modelAndView.addObject("clients", allClients);
        modelAndView.addObject("newClient", new ClientIn(null, null, null, null));
        modelAndView.setViewName(CLIENT_LIST_VIEW);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getClient(@PathVariable int id, ModelAndView modelAndView) {
        ClientEntity client = clientService.getClientOrThrowException(id);

        modelAndView.setViewName(CLIENT_DETAIL_VIEW);
        modelAndView.addObject("client", clientMapping.toOut(client));
        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok(String.format("Client with id %d deleted", id));
    }

    @PostMapping
    public ModelAndView createClient(@Valid @ModelAttribute("newClient") ClientIn clientIn,
                                     BindingResult result,
                                     ModelAndView modelAndView) {
        if (result.hasErrors()) {
            modelAndView.setViewName(CLIENT_LIST_VIEW);
            return modelAndView;
        }

        ClientEntity clientEntity = clientMapping.toEntity(clientIn);
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

        ClientEntity clientEntity = clientMapping.toEntity(clientIn);
        clientEntity.setId((long) id);
        clientEntity = clientService.updateClient(clientEntity);

        modelAndView.setViewName(CLIENT_DETAIL_VIEW);
        modelAndView.addObject("client", clientMapping.toOut(clientEntity));
        return modelAndView;
    }

}
