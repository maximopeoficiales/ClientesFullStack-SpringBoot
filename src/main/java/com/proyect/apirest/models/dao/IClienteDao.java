package com.proyect.apirest.models.dao;

import com.proyect.apirest.models.entity.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface IClienteDao extends CrudRepository<Cliente,Long>{
           
}
