package com.proyect.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.proyect.apirest.models.entity.Cliente;
import com.proyect.apirest.models.services.IClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

     @GetMapping("/clientes/page/{page}")
     public Page<Cliente> indexPageable(@PathVariable Integer page) {
          return clienteService.findAll(PageRequest.of(page, 4));
     }

     @GetMapping("/clientes/{id}")
     public ResponseEntity<?> show(@Valid @PathVariable Long id) {
          Cliente cliente = null;
          Map<String, Object> response = new HashMap<>();
          try {
               cliente = clienteService.findById(id);
          } catch (DataAccessException e) {
               response.put("msg", "Error al realizar la consulta con la base de datos");
               response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
          }
          if (cliente == null) {
               response.put("msg", "El cliente ID:".concat(id.toString().concat(" no existe en la base de datos!")));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
          }
          return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
     }

     // request body porque viene del json
     @PostMapping("/clientes")
     // @ResponseStatus(HttpStatus.CREATED) // 202
     // agregar arroba valid para mostrar los errores definidos en los entitys
     public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
          Cliente clientNew = null;
          Map<String, Object> response = new HashMap<>();
          if (result.hasErrors()) {
               // List<String> errors = new ArrayList<>();
               // for (FieldError err : result.getFieldErrors()) {
               // errors.add("El campo '" + err.getField() + "' " + err.getDefaultMessage());
               // }
               List<String> errors = result.getFieldErrors().stream()
                         .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                         .collect(Collectors.toList());
               response.put("errors", errors);
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
          }

          try {
               clientNew = clienteService.save(cliente);
          } catch (DataAccessException e) {
               response.put("msg", "Error al realizar la consulta con la base de datos");
               response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
          }
          response.put("msg", "Cliente registrado correctamente");
          response.put("client", clientNew);
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
     }

     @PutMapping("/clientes/{id}")
     @ResponseStatus(HttpStatus.CREATED) // 20
     public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
          Cliente clienteActual = clienteService.findById(id);
          Cliente clienteUpdated = null;

          Map<String, Object> response = new HashMap<>();

          if (result.hasErrors()) {
               List<String> errors = result.getFieldErrors().stream()
                         .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                         .collect(Collectors.toList());
               response.put("errors", errors);
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
          }
          if (clienteActual == null) {
               response.put("msg", "No se pudo editar, El cliente ID:"
                         .concat(id.toString().concat(" no existe en la base de datos!")));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
          }
          try {
               clienteActual.setApellido(cliente.getApellido());
               clienteActual.setNombre(cliente.getNombre());
               clienteActual.setEmail(cliente.getEmail());
               clienteActual.setCreatedAt(cliente.getCreatedAt());
               clienteUpdated = clienteService.save(clienteActual);
          } catch (DataAccessException e) {
               response.put("msg", "Error al realizar la consulta con la base de datos");
               response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
          }
          response.put("msg", "Cliente actualizado correctamente");
          response.put("client", clienteUpdated);
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

          // return clienteService.save(clienteActual);
     }

     @DeleteMapping("/clientes/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT) // 202
     public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
          Map<String, Object> response = new HashMap<>();

          try {
               clienteService.delete(id);
          } catch (DataAccessException e) {
               response.put("msg", "Error al realizar la consulta con la base de datos");
               response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
               return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
          }
          response.put("msg", "El cliente ha sido eliminado con exito");
          return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
     }

}
