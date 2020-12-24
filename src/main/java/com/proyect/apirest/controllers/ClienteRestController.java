package com.proyect.apirest.controllers;

import java.util.List;

import com.proyect.apirest.models.entity.Cliente;
import com.proyect.apirest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ClienteRestController {

     @Autowired
     private IClienteService clienteService;

     @GetMapping("/clientes")
     public List<Cliente> index() {
          return clienteService.findAll();
     }

     @GetMapping("/clientes/{id}")
     public Cliente show(@PathVariable Long id) {
          return clienteService.findById(id);
     }

     // request body porque viene del json
     @PostMapping("/clientes")
     @ResponseStatus(HttpStatus.CREATED) // 202
     public Cliente create(@RequestBody Cliente cliente) {
          return clienteService.save(cliente);
     }

     @PutMapping("/clientes/{id}")
     @ResponseStatus(HttpStatus.CREATED) // 202
     public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
          Cliente clienteActual = clienteService.findById(id);
          clienteActual.setApellido(cliente.getApellido());
          clienteActual.setNombre(cliente.getNombre());
          clienteActual.setEmail(cliente.getEmail());
          return clienteService.save(clienteActual);
     }

     @DeleteMapping("/clientes/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT) // 202
     public void delete(@PathVariable Long id) {
          clienteService.delete(id);
     }

}
