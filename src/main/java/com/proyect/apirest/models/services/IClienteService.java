package com.proyect.apirest.models.services;

import java.util.List;

import com.proyect.apirest.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {
     List<Cliente> findAll();

     Page<Cliente> findAll(Pageable pageable);

     Cliente findById(Long id);

     Cliente save(Cliente cliente);

     void delete(Long id);

}
