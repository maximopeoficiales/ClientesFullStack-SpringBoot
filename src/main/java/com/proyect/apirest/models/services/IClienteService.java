package com.proyect.apirest.models.services;

import java.util.List;

import com.proyect.apirest.models.entity.Cliente;

public interface IClienteService {
     List<Cliente> findAll();

     Cliente findById(Long id);

     Cliente save(Cliente cliente);

     void delete(Long id);

}
